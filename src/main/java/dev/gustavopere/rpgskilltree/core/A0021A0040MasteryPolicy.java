package dev.gustavopere.rpgskilltree.core;

import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Canonical finite-discovery mastery aliases needed to make the A0025/A0031/A0037 gates reachable.
 * Generic Epic Fight mastery remains owned by MasteryPolicies.forEpicFight.
 */
public final class A0021A0040MasteryPolicy {
    /** Historical constant retained for source compatibility; repeatable gate mastery no longer uses it. */
    @Deprecated
    public static final int CONFIRMED_HIT_XP = 3;
    public static final int DISTINCT_HOSTILE_TYPE_XP = 10;
    public static final int A0025_DISTINCT_HOSTILE_TYPE_XP = DISTINCT_HOSTILE_TYPE_XP;

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
     * Legacy repeatable alias path. The A0025/A0031/A0037 gate lanes are finite-discovery ledgers;
     * repeating damage against an already-known entity type must grant 0 XP.
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
        if (family == WeaponFamily.HAMMER || family == WeaponFamily.MACE || family == WeaponFamily.SCYTHE) {
            return List.of();
        }
        if (actionId.isBlank()
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
        if (entityTypeId.isBlank()) return Optional.empty();
        return canonicalGateMastery(family)
            .filter(ignored -> family == WeaponFamily.HAMMER || family == WeaponFamily.MACE || family == WeaponFamily.SCYTHE)
            .map(lane -> "mastery/" + lane + "/entity_type/" + entityTypeId);
    }

    /**
     * A0025/A0031/A0037: +10 gate mastery exactly once per distinct hostile entity type.
     * Six distinct types reach 60; eight distinct types reach 80.
     */
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
            || (family != WeaponFamily.HAMMER && family != WeaponFamily.MACE && family != WeaponFamily.SCYTHE)
            || !direct
            || !hostile
            || !Double.isFinite(actualDamage)
            || actualDamage <= 0.0D) {
            return List.of();
        }
        Optional<String> discoveryKey = discoveryKey(family, entityTypeId);
        Optional<String> lane = canonicalGateMastery(family);
        if (discoveryKey.isEmpty() || lane.isEmpty()) return List.of();
        String key = discoveryKey.get();
        return List.of(MasteryAward.replaySafe(
            lane.get(),
            DISTINCT_HOSTILE_TYPE_XP,
            "distinct-hostile-type/" + lane.get() + "/" + entityTypeId,
            key
        ));
    }
}
