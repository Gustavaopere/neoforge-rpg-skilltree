package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.minecolonies.api.entity.citizen.AbstractEntityCitizen;
import com.minecolonies.core.entity.ai.workers.guard.AbstractEntityAIGuard;
import com.minecolonies.core.entity.citizen.EntityCitizen;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/** Thin tactical bridge between MineColonies target selection and Iron's spell lifecycle. */
public final class BattleMageCombatController {
    private static final float CRITICAL_HEALTH_RATIO = 0.40f;
    private static final double FALLBACK_ATTACK_DISTANCE = 8.0;
    private static final double MAX_OPERATIONAL_DISTANCE = 32.0;

    private BattleMageCombatController() {}

    public record Candidate(BattleMageSpellProfile profile, int bookIndex) {
        public Candidate {
            Objects.requireNonNull(profile, "profile");
            if (bookIndex < 0) throw new IllegalArgumentException("bookIndex must be >= 0");
        }
    }

    static List<Candidate> orderCandidates(List<Candidate> candidates, boolean selfCritical) {
        Objects.requireNonNull(candidates, "candidates");
        return candidates.stream()
            .filter(Objects::nonNull)
            .sorted(
                Comparator.comparingInt((Candidate candidate) -> tacticalLane(candidate.profile(), selfCritical))
                    .thenComparing(Comparator.comparingInt((Candidate candidate) -> candidate.profile().priority()).reversed())
                    .thenComparingInt(Candidate::bookIndex)
                    .thenComparing(candidate -> candidate.profile().spellId())
            )
            .toList();
    }

    static boolean inRange(BattleMageSpellProfile profile, double distance) {
        return profile != null
            && Double.isFinite(distance)
            && distance >= profile.minRange()
            && distance <= profile.maxRange();
    }

    static boolean isRuntimeSupported(BattleMageSpellProfile profile) {
        if (profile == null || profile.worldEffect()) return false;
        return switch (profile.targetMode()) {
            case SELF, HOSTILE_ENTITY, HOSTILE_AREA -> true;
            case ALLY_ENTITY -> false;
        };
    }

    public static boolean tryBeginCast(EntityCitizen caster, LivingEntity hostileTarget) {
        if (caster == null || caster.level().isClientSide || !caster.isAlive()) return false;
        if (IronsCitizenMagicBridge.magicData(caster).isCasting()) return false;

        BattleMageLoadoutResolver.Loadout loadout = BattleMageLoadoutResolver.resolve(caster).orElse(null);
        if (loadout == null || !loadout.isStillUsable()) return false;

        List<SpellData> spells = loadout.activeSpells();
        List<Candidate> candidates = candidatesFor(spells);
        if (candidates.isEmpty()) return false;

        boolean selfCritical = caster.getMaxHealth() > 0.0f
            && caster.getHealth() / caster.getMaxHealth() <= CRITICAL_HEALTH_RATIO;

        for (Candidate candidate : orderCandidates(candidates, selfCritical)) {
            BattleMageSpellProfile profile = candidate.profile();
            SpellData spellData = spells.get(candidate.bookIndex());
            if (!canUseProfile(caster, hostileTarget, spellData, profile, selfCritical)) continue;

            if (profile.targetMode() != BattleMageTargetMode.SELF && hostileTarget != null) {
                caster.getLookControl().setLookAt(hostileTarget);
                caster.setTarget(hostileTarget);
            }

            if (IronsCitizenMagicBridge.beginCast(caster, spellData)) {
                BattleMageCastTracker.markStarted(caster, loadout, spellData);
                return true;
            }
        }
        return false;
    }

    /** Revalidates range/target/friendly-fire during an in-progress provider cast. */
    static boolean canContinueCast(
        EntityCitizen caster,
        LivingEntity hostileTarget,
        BattleMageSpellProfile profile
    ) {
        if (caster == null || !caster.isAlive() || !isRuntimeSupported(profile)) return false;
        if (profile.targetMode() == BattleMageTargetMode.SELF) return true;
        SpellData castingSpell = IronsCitizenMagicBridge.magicData(caster).getCastingSpell();
        return hostileContextSafe(caster, hostileTarget, castingSpell, profile);
    }

    public static double preferredAttackDistance(EntityCitizen caster) {
        if (caster == null) return FALLBACK_ATTACK_DISTANCE;
        BattleMageLoadoutResolver.Loadout loadout = BattleMageLoadoutResolver.resolve(caster).orElse(null);
        if (loadout == null) return FALLBACK_ATTACK_DISTANCE;

        double max = candidatesFor(loadout.activeSpells()).stream()
            .map(Candidate::profile)
            .filter(BattleMageCombatController::isRuntimeSupported)
            .filter(profile -> profile.targetMode() == BattleMageTargetMode.HOSTILE_ENTITY
                || profile.targetMode() == BattleMageTargetMode.HOSTILE_AREA)
            .mapToDouble(BattleMageSpellProfile::maxRange)
            .max()
            .orElse(FALLBACK_ATTACK_DISTANCE);
        return Math.max(2.0, Math.min(MAX_OPERATIONAL_DISTANCE, max));
    }

    public static boolean hasSupportedSpell(EntityCitizen caster) {
        if (caster == null) return false;
        BattleMageLoadoutResolver.Loadout loadout = BattleMageLoadoutResolver.resolve(caster).orElse(null);
        return loadout != null
            && candidatesFor(loadout.activeSpells()).stream().anyMatch(candidate -> isRuntimeSupported(candidate.profile()));
    }

    private static List<Candidate> candidatesFor(List<SpellData> spells) {
        List<Candidate> candidates = new ArrayList<>();
        for (int index = 0; index < spells.size(); index++) {
            SpellData spellData = spells.get(index);
            if (spellData == null || spellData.getSpell() == null) continue;
            String spellId = spellData.getSpell().getSpellId();
            int bookIndex = index;
            BattleMageSpellProfileCatalog.find(spellId)
                .filter(BattleMageCombatController::isRuntimeSupported)
                .ifPresent(profile -> candidates.add(new Candidate(profile, bookIndex)));
        }
        return List.copyOf(candidates);
    }

    private static boolean canUseProfile(
        EntityCitizen caster,
        LivingEntity hostileTarget,
        SpellData spellData,
        BattleMageSpellProfile profile,
        boolean selfCritical
    ) {
        if (!isRuntimeSupported(profile)) return false;
        if (profile.targetMode() == BattleMageTargetMode.SELF) {
            return selfCritical && inRange(profile, 0.0);
        }
        return hostileContextSafe(caster, hostileTarget, spellData, profile);
    }

    private static boolean hostileContextSafe(
        EntityCitizen caster,
        LivingEntity hostileTarget,
        SpellData spellData,
        BattleMageSpellProfile profile
    ) {
        if (hostileTarget == null || !hostileTarget.isAlive()) return false;
        if (!AbstractEntityAIGuard.isAttackableTarget(caster, hostileTarget)) return false;
        if (!caster.getSensing().hasLineOfSight(hostileTarget)) return false;
        if (!inRange(profile, caster.distanceTo(hostileTarget))) return false;
        return profile.targetMode() != BattleMageTargetMode.HOSTILE_AREA
            || isAreaSafe(caster, hostileTarget, spellData, profile);
    }

    private static boolean isAreaSafe(
        EntityCitizen caster,
        LivingEntity target,
        SpellData spellData,
        BattleMageSpellProfile profile
    ) {
        if (profile.allySafe()) return true;
        double radius = BattleMageSpellRuntimeSafety.friendlyFireRadius(caster, spellData, profile);
        if (!Double.isFinite(radius) || radius <= 0.0) return false;
        AABB danger = target.getBoundingBox().inflate(radius);
        return caster.level().getEntitiesOfClass(LivingEntity.class, danger, entity -> isProtectedAlly(caster, entity)).isEmpty();
    }

    private static boolean isProtectedAlly(EntityCitizen caster, LivingEntity entity) {
        if (entity == null || !entity.isAlive()) return false;
        if (entity == caster) return true;
        if (!(entity instanceof AbstractEntityCitizen) && !(entity instanceof Player)) return false;
        return !AbstractEntityAIGuard.isAttackableTarget(caster, entity);
    }

    private static int tacticalLane(BattleMageSpellProfile profile, boolean selfCritical) {
        if (selfCritical && profile.targetMode() == BattleMageTargetMode.SELF) return 0;
        if (profile.targetMode() == BattleMageTargetMode.HOSTILE_ENTITY
            || profile.targetMode() == BattleMageTargetMode.HOSTILE_AREA) return 1;
        if (profile.targetMode() == BattleMageTargetMode.ALLY_ENTITY) return 2;
        return 3;
    }
}
