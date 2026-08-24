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
        Objects.requireNonNull(action);
        Objects.requireNonNull(actorId);
        Objects.requireNonNull(targetId);
        Objects.requireNonNull(weaponFamily);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        if (actorId.isBlank() || targetId.isBlank()) throw new IllegalArgumentException("ids must not be blank");
        if (!action.actorId().equals(actorId)) throw new IllegalArgumentException("action actor must match actorId");
        if (weaponMastery < 0) throw new IllegalArgumentException("weaponMastery must be non-negative");
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
}
