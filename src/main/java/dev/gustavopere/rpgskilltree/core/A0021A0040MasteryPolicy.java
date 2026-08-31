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
     * A0025/A0031/A0037 use finite discovery ledgers. None of their gate Masteries may be farmed
     * by repeating damage against the same hostile type, so the old +3 XP/hit alias is disabled.
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
        if (canonicalGateMastery(family).isPresent()) return List.of();
        if (actionId.isBlank()
            || !direct
            || !hostile
            || !Double.isFinite(actualDamage)
            || actualDamage <= 0.0D) {
            return List.of();
        }
        return List.of();
    }

    public static Optional<String> discoveryKey(WeaponFamily family, String entityTypeId) {
        Objects.requireNonNull(family);
        Objects.requireNonNull(entityTypeId);
        if (entityTypeId.isBlank()) return Optional.empty();
        return canonicalGateMastery(family)
            .map(lane -> "mastery/" + lane + "/entity_type/" + entityTypeId);
    }

    /** A0025/A0031/A0037: +10 gate Mastery exactly once per distinct hostile entity type. */
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
        Optional<String> lane = canonicalGateMastery(family);
        if (!newlyDiscovered
            || lane.isEmpty()
            || !direct
            || !hostile
            || !Double.isFinite(actualDamage)
            || actualDamage <= 0.0D) {
            return List.of();
        }
        Optional<String> discoveryKey = discoveryKey(family, entityTypeId);
        if (discoveryKey.isEmpty()) return List.of();
        return List.of(MasteryAward.replaySafe(
            lane.get(),
            A0025_DISTINCT_HOSTILE_TYPE_XP,
            "distinct-hostile-type/" + lane.get() + "/" + entityTypeId,
            discoveryKey.get()
        ));
    }
}
