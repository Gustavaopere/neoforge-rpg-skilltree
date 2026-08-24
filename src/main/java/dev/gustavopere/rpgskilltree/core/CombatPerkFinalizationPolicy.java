package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Objects;
import java.util.Optional;

/** Pure server-authoritative policies that finalize cross-cutting A0001-A0050 capstones. */
public final class CombatPerkFinalizationPolicy {
    private CombatPerkFinalizationPolicy() {}

    public record BoneBreakerEffect(
        double outgoingPhysicalDamageMultiplier,
        double movementSpeedMultiplier,
        long expiresAtMillis
    ) {
        public BoneBreakerEffect {
            if (!Double.isFinite(outgoingPhysicalDamageMultiplier)
                || outgoingPhysicalDamageMultiplier <= 0.0D
                || outgoingPhysicalDamageMultiplier > 1.0D) {
                throw new IllegalArgumentException("invalid outgoing physical damage multiplier");
            }
            if (!Double.isFinite(movementSpeedMultiplier)
                || movementSpeedMultiplier <= 0.0D
                || movementSpeedMultiplier > 1.0D) {
                throw new IllegalArgumentException("invalid movement speed multiplier");
            }
        }
    }

    public static Optional<BoneBreakerEffect> activateBoneBreaker(
        CanonicalActionIdentity action,
        String actorId,
        String targetId,
        WeaponFamily weaponFamily,
        boolean direct,
        boolean hostile,
        boolean heavyAttack,
        boolean targetIsBoss,
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        int weaponMastery,
        long nowMillis
    ) {
        validateAction(action, actorId, targetId, weaponFamily, ranks, state, weaponMastery);
        if (weaponFamily != WeaponFamily.MACE
            || !direct
            || !hostile
            || !heavyAttack
            || !ranks.learned("A0036")
            || !state.hasTargetFlag(actorId, targetId, NotionCombatPerkState.TargetFlag.ARMOR_CRACKED, nowMillis)
            || !state.cooldownReady(actorId, targetId, "A0036", nowMillis)) {
            return Optional.empty();
        }
        if (!state.claimPrimaryOnce(action, "A0036:bone-breaker", nowMillis)) return Optional.empty();

        long cooldownMillis = weaponMastery >= 100 ? 10_000L : weaponMastery >= 90 ? 11_000L : 12_000L;
        state.startCooldown(actorId, targetId, "A0036", nowMillis, cooldownMillis);
        return Optional.of(new BoneBreakerEffect(
            targetIsBoss ? 0.96D : 0.92D,
            targetIsBoss ? 0.95D : 0.90D,
            Math.addExact(nowMillis, 3_000L)
        ));
    }

    public static boolean activateBattleHarvest(
        CanonicalActionIdentity action,
        String actorId,
        String victimId,
        WeaponFamily weaponFamily,
        boolean direct,
        boolean hostile,
        boolean legitimateKill,
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        int weaponMastery,
        long nowMillis
    ) {
        validateAction(action, actorId, victimId, weaponFamily, ranks, state, weaponMastery);
        if (weaponFamily != WeaponFamily.SCYTHE
            || !direct
            || !hostile
            || !legitimateKill
            || !ranks.learned("A0042")
            || !state.hasTargetFlag(actorId, victimId, NotionCombatPerkState.TargetFlag.REAPING_MATURE, nowMillis)
            || !state.actorCooldownReady(actorId, "A0042", nowMillis)) {
            return false;
        }
        if (!state.claimPrimaryOnce(action, "A0042:battle-harvest-kill", nowMillis)) return false;

        state.armBattleHarvest(actorId, victimId, Math.addExact(nowMillis, 6_000L));
        long cooldownMillis = weaponMastery >= 100 ? 8_000L : weaponMastery >= 90 ? 9_000L : 10_000L;
        state.startActorCooldown(actorId, "A0042", nowMillis, cooldownMillis);
        return true;
    }

    /**
     * Consumes an armed Harvest only on the next legitimate direct scythe hit against a different target.
     * The exact 10% stamina refund remains outside this policy until the provider exposes a post-consumption receipt.
     */
    public static boolean consumeBattleHarvestOnHit(
        CanonicalActionIdentity action,
        String actorId,
        String targetId,
        WeaponFamily weaponFamily,
        boolean direct,
        boolean hostile,
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        long nowMillis
    ) {
        validateAction(action, actorId, targetId, weaponFamily, ranks, state, 0);
        if (weaponFamily != WeaponFamily.SCYTHE || !direct || !hostile || !ranks.learned("A0042")) return false;
        if (!state.hasBattleHarvest(actorId, nowMillis)) return false;
        if (!state.claimPrimaryOnce(action, "A0042:battle-harvest-hit", nowMillis)) return false;
        if (!state.consumeBattleHarvestForDifferentTarget(actorId, targetId, nowMillis)) return false;

        int markRank = ranks.rank("A0040");
        long markDuration = markRank >= 2 ? 10_000L : 8_000L;
        state.setTargetFlag(
            actorId,
            targetId,
            NotionCombatPerkState.TargetFlag.REAPING_MARK,
            Math.addExact(nowMillis, markDuration)
        );
        return true;
    }

    private static void validateAction(
        CanonicalActionIdentity action,
        String actorId,
        String targetId,
        WeaponFamily weaponFamily,
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        int weaponMastery
    ) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(actorId);
        Objects.requireNonNull(targetId);
        Objects.requireNonNull(weaponFamily);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        if (actorId.isBlank() || targetId.isBlank()) throw new IllegalArgumentException("ids must not be blank");
        if (!action.actorId().equals(actorId)) throw new IllegalArgumentException("action actor must match actorId");
        if (weaponMastery < 0) throw new IllegalArgumentException("weaponMastery must be non-negative");
    }
}
