package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Relative attribute formulas for A0088-A0090. */
public final class FrozenVitalityAttributePolicy {
    private FrozenVitalityAttributePolicy() {}
    public static double maxHealthMultiplier(FrozenCombatPerkRanks ranks) { return multiplier(ranks, "A0088"); }
    public static double armorMultiplier(FrozenCombatPerkRanks ranks) { return multiplier(ranks, "A0089"); }
    public static double toughnessMultiplier(FrozenCombatPerkRanks ranks) { return multiplier(ranks, "A0090"); }
    public static double applyRelative(double eligibleBase, double multiplier) {
        if (!Double.isFinite(eligibleBase) || eligibleBase < 0.0D || !Double.isFinite(multiplier) || multiplier < 0.0D) {
            throw new IllegalArgumentException("invalid relative attribute inputs");
        }
        return eligibleBase * multiplier;
    }
    private static double multiplier(FrozenCombatPerkRanks ranks, String code) {
        Objects.requireNonNull(ranks);
        return 1.0D + 0.02D * ranks.rank(code);
    }
}
