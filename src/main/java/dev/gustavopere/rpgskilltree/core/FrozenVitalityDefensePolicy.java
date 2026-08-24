package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Frozen semantic provider boundaries for A0091/A0093-A0095/A0100. */
public final class FrozenVitalityDefensePolicy {
    private FrozenVitalityDefensePolicy() {}

    public static double knockbackResistance(FrozenCombatPerkRanks ranks) {
        return 0.03D * require(ranks).rank("A0091");
    }

    public static double guardCostMultiplier(
        FrozenCombatPerkRanks ranks,
        boolean recognizedGuard,
        boolean exactCostProvider
    ) {
        if (!recognizedGuard || !exactCostProvider) return 1.0D;
        return 1.0D - 0.02D * require(ranks).rank("A0093");
    }

    public static double guardBreakRecoveryMultiplier(FrozenCombatPerkRanks ranks, boolean confirmedProvider) {
        return confirmedProvider ? 1.0D + 0.03D * require(ranks).rank("A0094") : 1.0D;
    }

    public static double interruptionResistance(FrozenCombatPerkRanks ranks, boolean exactProvider) {
        return exactProvider ? 0.03D * require(ranks).rank("A0095") : 0.0D;
    }

    public static double antiCriticalDamage(
        double baseDamage,
        double criticalPortion,
        FrozenCombatPerkRanks ranks,
        boolean exactProvider
    ) {
        finiteNonNegative(baseDamage, "baseDamage");
        finiteNonNegative(criticalPortion, "criticalPortion");
        int rank = require(ranks).rank("A0100");
        if (!exactProvider || criticalPortion == 0.0D || rank == 0) return baseDamage + criticalPortion;
        return baseDamage + criticalPortion * (1.0D - 0.04D * rank);
    }

    private static FrozenCombatPerkRanks require(FrozenCombatPerkRanks ranks) {
        return Objects.requireNonNull(ranks);
    }

    private static void finiteNonNegative(double value, String name) {
        if (!Double.isFinite(value) || value < 0.0D) throw new IllegalArgumentException(name);
    }
}
