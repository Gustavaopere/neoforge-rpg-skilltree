package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import dev.gustavopere.rpgskilltree.core.ActionOrigin;
import dev.gustavopere.rpgskilltree.core.IronStudyPolicy;
import dev.gustavopere.rpgskilltree.core.MasteryPolicies;
import dev.gustavopere.rpgskilltree.core.SpellAction;
import dev.gustavopere.rpgskilltree.core.SustainResolver;
import dev.gustavopere.rpgskilltree.runtime.CanonicalCombatRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.CanonicalSustainRuntime;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.compat.MagicAccessRuntime;
import io.redspace.ironsspellbooks.api.events.InscribeSpellEvent;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import java.util.Locale;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/** Optional Iron's Spells 'n Spellbooks adapter. Loaded only when Iron's is present. */
public final class IronsSpellbookProgressionEvents {
    private static final Set<String> ELEMENTAL_SCHOOLS = Set.of("fire", "ice", "lightning", "nature");

    private IronsSpellbookProgressionEvents() {}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onSpellPreCast(SpellPreCastEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer) return;
        if (event.getCastSource() == CastSource.COMMAND) return;
        if (MagicAccessRuntime.requireArcaneAccess(player)) return;
        event.setCanceled(true);
    }

    /**
     * Makes permanent spellbook inscription a learned-magic progression mechanic.
     * Scrolls intentionally remain usable after Arcane Awakening so they form the practice
     * path needed to qualify for advanced inscription instead of creating a progression deadlock.
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onInscribeSpell(InscribeSpellEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer || player.isCreative()) return;
        if (!MagicAccessRuntime.requireArcaneAccess(player)) {
            event.setCanceled(true);
            return;
        }

        var spellData = event.getSpellData();
        ResourceLocation schoolId = spellData.getSpell().getSchoolType().getId();
        String discipline = normalizeSchool(schoolId);
        var state = PlayerProgressionRuntime.get(player);
        var evaluation = IronStudyPolicy.evaluate(
            state.mastery(),
            "irons:" + discipline,
            spellData.getLevel(),
            state.classProgression().isUnlocked("mage")
        );
        if (evaluation.allowed()) return;

        event.setCanceled(true);
        var requirement = evaluation.requirement();
        if (!evaluation.mageSatisfied()) {
            player.displayClientMessage(Component.literal(
                "Inscrição bloqueada: magias de nível " + spellData.getLevel()
                    + "+ exigem a identidade Mago. Pratique Iron's Spells com scrolls e desenvolva o tronco Arcano."
            ), true);
            return;
        }

        player.displayClientMessage(Component.literal(
            "Inscrição bloqueada: domínio Iron's " + evaluation.currentCastingMastery() + "/"
                + requirement.castingMastery() + " e " + discipline + " "
                + evaluation.currentSchoolMastery() + "/" + requirement.schoolMastery()
                + ". Pratique a magia com scrolls antes de catalogá-la permanentemente."
        ), true);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpellCast(SpellOnCastEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer) return;
        if (!countsForMastery(event.getCastSource())) return;

        String discipline = normalizeSchool(event.getSchoolType().getId());
        SpellAction action = new SpellAction(
            new ActionOrigin("irons:" + event.getCastSource().name().toLowerCase(Locale.ROOT), 0),
            "irons",
            event.getSpellId(),
            discipline,
            Set.of(),
            Math.max(0, event.getManaCost())
        );
        PlayerProgressionRuntime.awardMastery(player, MasteryPolicies.forIron(action));
    }

    /** Runs before Iron's native post-hit lifesteal and replays it through the shared capped resolver. */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onSpellDamagePost(LivingDamageEvent.Post event) {
        var source = event.getSource();
        if (!isIronSpellDamageSource(source)
            || !(source.getEntity() instanceof ServerPlayer owner) || !eligible(owner)
            || !validHostileTarget(owner, event.getEntity()) || !directSpellOwner(source.getDirectEntity(), owner)) {
            return;
        }
        CanonicalSustainRuntime.markProviderClassifiedNonWeapon(source);
        var known = CanonicalCombatRuntimeState.damageAction(
            source, event.getEntity().getUUID().toString());
        if (known.isEmpty()) return;
        String school = ironSchool(source);
        CanonicalSustainRuntime.Classification classification = new CanonicalSustainRuntime.Classification(
            false, true, ELEMENTAL_SCHOOLS.contains(school), false, true);
        if (!CanonicalSustainRuntime.hasEligibleCandidate(owner, classification)) return;

        float nativeCoefficient = ironLifesteal(source);
        if (!Float.isFinite(nativeCoefficient)) {
            CanonicalSustainRuntime.markAmbiguousNativeHealing(source);
            CanonicalSustainRuntime.resolve(
                owner, known.get().action(), classification, event.getNewDamage(),
                known.get().targetHealthBefore(), SustainResolver.NativeCorrelation.AMBIGUOUS, 0.0D);
            return;
        }
        nativeCoefficient = Math.max(0.0F, nativeCoefficient);
        double interceptedNativeHealing = nativeCoefficient * event.getNewDamage();
        if (nativeCoefficient > 0.0F && !clearIronLifesteal(source)) {
            CanonicalSustainRuntime.markAmbiguousNativeHealing(source);
            CanonicalSustainRuntime.resolve(
                owner, known.get().action(), classification, event.getNewDamage(),
                known.get().targetHealthBefore(), SustainResolver.NativeCorrelation.AMBIGUOUS, 0.0D);
            return;
        }
        CanonicalSustainRuntime.resolve(
            owner,
            known.get().action(),
            classification,
            event.getNewDamage(),
            known.get().targetHealthBefore(),
            nativeCoefficient > 0.0F
                ? SustainResolver.NativeCorrelation.EXACT_INTERCEPTED
                : SustainResolver.NativeCorrelation.NONE,
            interceptedNativeHealing
        );
    }

    static boolean countsForMastery(CastSource source) {
        return source == CastSource.SPELLBOOK || source == CastSource.SCROLL;
    }

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer) && !player.isCreative() && !player.isSpectator();
    }

    private static boolean directSpellOwner(net.minecraft.world.entity.Entity direct, ServerPlayer owner) {
        return direct == owner || direct instanceof Projectile projectile && projectile.getOwner() == owner;
    }

    private static boolean isIronSpellDamageSource(Object source) {
        return source != null && source.getClass().getName()
            .equals("io.redspace.ironsspellbooks.damage.SpellDamageSource");
    }

    private static float ironLifesteal(Object source) {
        try {
            Object value = source.getClass().getMethod("getLifestealPercent").invoke(source);
            return value instanceof Number number ? number.floatValue() : Float.NaN;
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            return Float.NaN;
        }
    }

    private static boolean clearIronLifesteal(Object source) {
        try {
            source.getClass().getMethod("setLifestealPercent", float.class).invoke(source, 0.0F);
            return true;
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            return false;
        }
    }

    private static String ironSchool(Object source) {
        try {
            Object spell = source.getClass().getMethod("spell").invoke(source);
            Object school = spell.getClass().getMethod("getSchoolType").invoke(spell);
            Object id = school.getClass().getMethod("getId").invoke(school);
            return id instanceof ResourceLocation location ? normalizeSchool(location) : "";
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            return "";
        }
    }

    private static boolean validHostileTarget(ServerPlayer owner, LivingEntity target) {
        return target != owner && !owner.isAlliedTo(target)
            && (target instanceof Enemy || target instanceof Player);
    }

    static String normalizeSchool(ResourceLocation schoolId) {
        return schoolId.getNamespace().equals("irons_spellbooks")
            ? schoolId.getPath()
            : schoolId.getNamespace() + "/" + schoolId.getPath();
    }
}
