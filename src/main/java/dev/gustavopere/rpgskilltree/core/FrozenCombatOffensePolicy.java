package dev.gustavopere.rpgskilltree.core;

/** Pure formulas for A0051-A0060; adapters decide whether the required semantic provider fact exists. */
public final class FrozenCombatOffensePolicy {
    private FrozenCombatOffensePolicy() {}

    public static double crossbowCriticalChance(FrozenCombatPerkRanks ranks) {
        return 0.03D * ranks.rank("A0051");
    }

    public static double fistDamageMultiplier(FrozenCombatPerkRanks ranks) {
        return 1.0D + 0.03D * ranks.rank("A0055");
    }

    public static double fistAttackSpeedMultiplier(FrozenCombatPerkRanks ranks, boolean safeProvider) {
        return safeProvider ? 1.0D + 0.02D * ranks.rank("A0056") : 1.0D;
    }

    public static double fistCriticalChance(FrozenCombatPerkRanks ranks) {
        return 0.03D * ranks.rank("A0057");
    }
}
