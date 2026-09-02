package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.A0081A0100CombatPolicy;
import dev.gustavopere.rpgskilltree.core.A0081A0100DefenseState;
import dev.gustavopere.rpgskilltree.core.A0101A0110DefenseState;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.DamageMitigationResolver;
import dev.gustavopere.rpgskilltree.core.DamageMitigationResolver.Contribution;
import dev.gustavopere.rpgskilltree.runtime.compat.A0079ForcedMovementCompat;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * Canonical incoming-defense runtime for A0092/A0096/A0097-A0099 and A0101-A0106.
 *
 * <p>All RPG-owned damage reducers enter {@link DamageMitigationResolver} from the single
 * {@link LivingDamageEvent.Pre} boundary. A0106 executes only after those contributions have been
 * composed, matching the frozen ordering contract and avoiding a second independent reducer
 * pipeline.</p>
 */
public final class A0101A0110DefenseRuntime {
    private static final TagKey<DamageType> PHYSICAL_DAMAGE = TagKey.create(
        Registries.DAMAGE_TYPE,
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "physical")
    );
    private static final TagKey<DamageType> ENVIRONMENTAL_DAMAGE = TagKey.create(
        Registries.DAMAGE_TYPE,
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "environmental")
    );

    private static final ResourceLocation A0105_ARMOR_ID =
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "temporary/a0105/armor");
    private static final ResourceLocation A0105_TOUGHNESS_ID =
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "temporary/a0105/armor_toughness");

    private A0101A0110DefenseRuntime() {}

    /** Applies the complete RPG-owned Pre mitigation pipeline and returns A0097 reservation state. */
    public static PreResult applyPre(ServerPlayer player, LivingDamageEvent.Pre event, String rootActionId) {
        CombatPerkRanks previousRanks = A0081A0100RuntimeState.ranks(player);
        CombatPerkRanks ranks = A0101A0110RuntimeState.ranks(player);
        DamageSource source = event.getSource();
        boolean physical = source.is(PHYSICAL_DAMAGE);
        boolean hostile = hostileSource(player, source);
        String actor = A0081A0100RuntimeState.actorId(player);
        long nowMillis = Math.multiplyExact(player.level().getGameTime(), 50L);
        double healthFraction = player.getMaxHealth() <= 0.0F
            ? 0.0D
            : Math.max(0.0D, Math.min(1.0D, player.getHealth() / player.getMaxHealth()));

        List<Contribution> contributions = new ArrayList<>();

        if (physical) {
            addReduction(
                contributions,
                "A0092",
                A0081A0100CombatPolicy.physicalResistanceReductionFraction(previousRanks)
            );
            if (hostile) {
                addReduction(
                    contributions,
                    "A0096",
                    A0081A0100CombatPolicy.lastBreathReductionFraction(previousRanks, healthFraction)
                );
            }
        }

        if (physical && source.is(DamageTypeTags.IS_PROJECTILE)) {
            addReduction(contributions, "A0101", 0.02D * ranks.rank("A0101"));
        }
        if (genericMagicEligible(player, source)) {
            addReduction(contributions, "A0102", 0.02D * ranks.rank("A0102"));
        }
        if (source.is(ENVIRONMENTAL_DAMAGE)) {
            addReduction(contributions, "A0103", 0.02D * ranks.rank("A0103"));
        }

        boolean openingReserved = false;
        if (hostile) {
            A0081A0100DefenseState defense = A0081A0100RuntimeState.defense();
            double openingMultiplier = A0081A0100CombatPolicy.openingDefenseMultiplier(
                actor,
                previousRanks,
                defense,
                nowMillis
            );
            if (Double.compare(openingMultiplier, 1.0D) != 0
                && defense.reserveOpeningDefense(actor, rootActionId, nowMillis)) {
                openingReserved = true;
                addReduction(contributions, "A0097", 1.0D - openingMultiplier);
            }

            double movingMultiplier = A0081A0100CombatPolicy.movingDefenseMultiplier(
                previousRanks,
                A0079ForcedMovementCompat.selfPropelledSprintEligible(player)
            );
            addReduction(contributions, "A0098", 1.0D - movingMultiplier);

            double stationaryMultiplier = A0081A0100CombatPolicy.stationaryDefenseMultiplier(
                previousRanks,
                A0061A0080RuntimeState.stationary().isStationary(actor)
                    && !A0079ForcedMovementCompat.forcedOrUnclassified(player)
            );
            addReduction(contributions, "A0099", 1.0D - stationaryMultiplier);
        }

        DamageMitigationResolver.Result resolved = DamageMitigationResolver.resolve(
            event.getNewDamage(),
            contributions
        );
        double damage = resolved.damage();

        A0101A0110DefenseState.EmergencyGuardResult emergency =
            A0101A0110RuntimeState.defense(player).applyEmergencyGuard(
                A0101A0110RuntimeState.actorId(player),
                player.level().getGameTime(),
                player.getHealth(),
                player.getMaxHealth(),
                damage,
                ranks.rank("A0106") > 0,
                directHostileDamage(player, source)
            );
        if (emergency.activated()) {
            A0101A0110RuntimeState.persistCooldowns(player);
        }
        event.setNewDamage((float) emergency.damage());
        return new PreResult(openingReserved);
    }

    /** Consumes confirmed health damage receipts for A0104/A0105 only after Post proves the hit. */
    public static void onConfirmedPost(
        ServerPlayer player,
        DamageSource source,
        String rootActionId,
        float healthDamage
    ) {
        if (healthDamage <= 0.0F || !directHostileDamage(player, source)) return;

        CombatPerkRanks ranks = A0101A0110RuntimeState.ranks(player);
        A0101A0110DefenseState state = A0101A0110RuntimeState.defense(player);
        String actor = A0101A0110RuntimeState.actorId(player);
        long nowTick = player.level().getGameTime();
        double maxHealth = player.getMaxHealth();
        if (!(maxHealth > 0.0D)) return;

        double postRatio = Math.max(0.0D, Math.min(1.0D, player.getHealth() / maxHealth));
        double preRatio = Math.max(
            0.0D,
            Math.min(1.0D, (player.getHealth() + healthDamage) / maxHealth)
        );
        boolean secondWindActivated = state.recordSecondWindHit(
            actor,
            rootActionId,
            nowTick,
            preRatio,
            postRatio,
            ranks.rank("A0104") > 0
        );
        boolean reactiveShellActivated = state.recordReactiveShellHit(
            actor,
            rootActionId,
            nowTick,
            ranks.rank("A0105") > 0
        );
        if (secondWindActivated || reactiveShellActivated) {
            A0101A0110RuntimeState.persistCooldowns(player);
        }
        syncReactiveShellModifiers(player, state.reactiveShellActive(
            actor,
            nowTick,
            ranks.rank("A0105") > 0
        ));
    }

    public static void tickPlayer(ServerPlayer player) {
        CombatPerkRanks ranks = A0101A0110RuntimeState.ranks(player);
        A0101A0110DefenseState state = A0101A0110RuntimeState.defense(player);
        String actor = A0101A0110RuntimeState.actorId(player);
        long nowTick = player.level().getGameTime();

        int pulses = state.claimSecondWindPulses(actor, nowTick, ranks.rank("A0104") > 0);
        for (int pulse = 0; pulse < pulses; pulse++) {
            player.heal((float) (player.getMaxHealth() * A0101A0110DefenseState.SECOND_WIND_PULSE_MAX_HEALTH_FRACTION));
        }

        syncReactiveShellModifiers(
            player,
            state.reactiveShellActive(actor, nowTick, ranks.rank("A0105") > 0)
        );
    }

    public static void reconcilePlayerBoundary(ServerPlayer player) {
        syncReactiveShellModifiers(player, false);
        A0101A0110RuntimeState.reconcilePlayerBoundary(player);
    }

    public static void clearAll() {
        A0101A0110RuntimeState.clearAll();
    }

    private static void syncReactiveShellModifiers(ServerPlayer player, boolean active) {
        syncModifier(player.getAttribute(Attributes.ARMOR), A0105_ARMOR_ID, active ? 0.15D : 0.0D);
        syncModifier(player.getAttribute(Attributes.ARMOR_TOUGHNESS), A0105_TOUGHNESS_ID, active ? 0.08D : 0.0D);
    }

    private static void syncModifier(
        AttributeInstance instance,
        ResourceLocation id,
        double amount
    ) {
        if (instance == null) return;
        if (!(amount > 0.0D)) {
            instance.removeModifier(id);
            return;
        }
        instance.addOrUpdateTransientModifier(new AttributeModifier(
            id,
            amount,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        ));
    }

    private static void addReduction(List<Contribution> contributions, String id, double fraction) {
        if (!(fraction > 0.0D)) return;
        contributions.add(new Contribution(id, fraction));
    }

    /**
     * Generic magic is intentionally narrower than provider Arcane Resistance: only a tagged magic
     * root with an explicit hostile living attacker is eligible. Self, attackerless terminal,
     * technical, resource-cost and unknown roots fail closed instead of being guessed from visuals.
     */
    private static boolean genericMagicEligible(ServerPlayer player, DamageSource source) {
        if (!source.is(Tags.DamageTypes.IS_MAGIC)) return false;
        if (source.is(Tags.DamageTypes.IS_TECHNICAL)) return false;
        return hostileSource(player, source);
    }

    /** Strict combat receipt used by A0104-A0106; periodic/environmental/technical roots fail closed. */
    private static boolean directHostileDamage(ServerPlayer player, DamageSource source) {
        return hostileSource(player, source)
            && source.getDirectEntity() != null
            && !source.is(Tags.DamageTypes.IS_TECHNICAL)
            && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
    }

    private static boolean hostileSource(ServerPlayer player, DamageSource source) {
        return source.getEntity() instanceof LivingEntity attacker
            && attacker != player
            && !player.isAlliedTo(attacker);
    }

    public record PreResult(boolean openingReserved) {}
}
