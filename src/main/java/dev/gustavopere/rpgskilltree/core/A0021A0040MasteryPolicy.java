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

    public static List<MasteryAward> forConfirmedDirectHit(
        WeaponFamily family,
        boolean direct,
        boolean hostile,
        double actualDamage,
        String actionId
    ) {
        Objects.requireNonNull(family);
        Objects.requireNonNull(actionId);
        if (actionId.isBlank() || !direct || !hostile || !Double.isFinite(actualDamage) || actualDamage <= 0.0D) {
            return List.of();
        }
        return canonicalGateMastery(family)
            .map(key -> List.of(new MasteryAward(key, CONFIRMED_HIT_XP, actionId)))
            .orElseGet(List::of);
    }
}
