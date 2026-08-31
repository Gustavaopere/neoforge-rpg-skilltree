package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Canonical mastery aliases needed to make the A0025/A0031/A0037 gates reachable.
 * Generic Epic Fight mastery remains owned by MasteryPolicies.forEpicFight.
 */
public final class A0021A0040MasteryPolicy {
    public static final int CONFIRMED_HIT_XP = 3;
    public static final int A0025_DISTINCT_HOSTILE_TYPE_XP = 10;

    private A0021A0040MasteryPolicy() {}

    public static Optional<String> canonicalGateMastery(WeaponFamily family) {
        Objects.requireNonNull(family);
        return switch (family) {
            case HAMMER -> Optional.of("epicfight:heavy");
            case MACE -> Optional.of("combat:mace");
            case SCYTHE -> Optional.of("combat:scythe");
            default -> Optional.empty();
        };
    }

    /**
     * Legacy repeatable alias path. HAMMER is deliberately excluded because A0025 is a finite
     * discovery ledger: repeating damage against an already-known entity type must grant 0 XP.
     * MACE/SCYTHE remain untouched until their own exact Chat 2 lots are implemented.
     */
    public static List<MasteryAward> forConfirmedDirectHit(
        WeaponFamily family,
        boolean direct,
        boolean hostile,
        double actualDamage,
        String actionId
    ) {
        Objects.requireNonNull(family);
        Objects.requireNonNull(actionId);
        if (family == WeaponFamily.HAMMER
            || actionId.isBlank()
            || !direct
            || !hostile
            || !Double.isFinite(actualDamage)
            || actualDamage <= 0.0D) {
            return List.of();
        }
        return canonicalGateMastery(family)
            .map(key -> List.of(new MasteryAward(key, CONFIRMED_HIT_XP, actionId)))
            .orElseGet(List::of);
    }

    public static Optional<String> discoveryKey(WeaponFamily family, String entityTypeId) {
        Objects.requireNonNull(family);
        Objects.requireNonNull(entityTypeId);
        if (entityTypeId.isBlank() || family != WeaponFamily.HAMMER) return Optional.empty();
        return Optional.of("mastery/epicfight:heavy/entity_type/" + entityTypeId);
    }

    /** A0025: +10 epicfight:heavy exactly once per distinct hostile entity type. */
    public static List<MasteryAward> forDistinctHostileTypeDiscovery(
        WeaponFamily family,
        boolean direct,
        boolean hostile,
        double actualDamage,
        String entityTypeId,
        boolean newlyDiscovered
    ) {
        Objects.requireNonNull(family);
        Objects.requireNonNull(entityTypeId);
        if (!newlyDiscovered
            || family != WeaponFamily.HAMMER
            || !direct
            || !hostile
            || !Double.isFinite(actualDamage)
            || actualDamage <= 0.0D) {
            return List.of();
        }
        Optional<String> discoveryKey = discoveryKey(family, entityTypeId);
        if (discoveryKey.isEmpty()) return List.of();
        String key = discoveryKey.get();
        return List.of(MasteryAward.replaySafe(
            "epicfight:heavy",
            A0025_DISTINCT_HOSTILE_TYPE_XP,
            "a0025-distinct-hostile-type/" + entityTypeId,
            key
        ));
    }
}
