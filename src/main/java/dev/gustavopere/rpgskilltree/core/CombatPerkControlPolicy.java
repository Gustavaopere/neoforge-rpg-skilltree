package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.Objects;
import java.util.OptionalDouble;

/** Pure handling for provider-confirmed combat-control outcomes such as a real posture break. */
public final class CombatPerkControlPolicy {
    private CombatPerkControlPolicy() {}

    public static boolean onSpearRangeUpdate(
        String actorId,
        String targetId,
        WeaponFamily weaponFamily,
        double distance,
        double effectiveReach,
        boolean targetAdvancing,
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        int weaponMastery,
        long nowMillis
    ) {
        Objects.requireNonNull(actorId);
        Objects.requireNonNull(targetId);
        Objects.requireNonNull(weaponFamily);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank");
        if (targetId.isBlank()) throw new IllegalArgumentException("targetId must not be blank");
        if (!Double.isFinite(distance) || distance < 0.0D) {
            throw new IllegalArgumentException("distance must be finite and non-negative");
        }
        if (!Double.isFinite(effectiveReach) || effectiveReach <= 0.0D) {
            throw new IllegalArgumentException("effectiveReach must be finite and positive");
        }
        if (weaponMastery < 0) throw new IllegalArgumentException("weaponMastery must be non-negative");
        if (weaponFamily != WeaponFamily.SPEAR) return false;

        OptionalDouble previousDistance = state.recordTargetDistance(actorId, targetId, distance);
        if (previousDistance.isEmpty()
            || !ranks.learned("A0018")
            || state.distanceControl(actorId, nowMillis) < 3
            || !targetAdvancing
            || previousDistance.getAsDouble() <= effectiveReach
            || !CombatPositionPolicy.isIdealSpearRange(distance, effectiveReach, 0.0D)
            || !state.cooldownReady(actorId, targetId, "A0018", nowMillis)) {
            return false;
        }

        long window = weaponMastery >= 100 ? 4_000L : weaponMastery >= 90 ? 3_500L : 3_000L;
        state.setTargetFlag(
            actorId,
            targetId,
            NotionCombatPerkState.TargetFlag.INTERCEPTION_WINDOW,
            Math.addExact(nowMillis, window)
        );
        state.startCooldown(actorId, targetId, "A0018", nowMillis, 8_000L);
        return true;
    }

    public static boolean onConfirmedPostureBreak(
        String actorId,
        String targetId,
        WeaponFamily weaponFamily,
        CombatPerkRanks ranks,
        NotionCombatPerkState state,
        int weaponMastery,
        long nowMillis
    ) {
        Objects.requireNonNull(actorId);
        Objects.requireNonNull(targetId);
        Objects.requireNonNull(weaponFamily);
        Objects.requireNonNull(ranks);
        Objects.requireNonNull(state);
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId must not be blank");
        if (targetId.isBlank()) throw new IllegalArgumentException("targetId must not be blank");
        if (weaponMastery < 0) throw new IllegalArgumentException("weaponMastery must be non-negative");
        if (weaponFamily != WeaponFamily.HAMMER || !ranks.learned("A0030")) return false;
        if (!state.cooldownReady(actorId, targetId, "A0030", nowMillis)) return false;

        state.setTargetFlag(
            actorId,
            targetId,
            NotionCombatPerkState.TargetFlag.DEMOLISH_WINDOW,
            Math.addExact(nowMillis, 4_000L)
        );
        long cooldown = weaponMastery >= 100 ? 10_000L : weaponMastery >= 90 ? 11_000L : 12_000L;
        state.startCooldown(actorId, targetId, "A0030", nowMillis, cooldown);
        return true;
    }
}
