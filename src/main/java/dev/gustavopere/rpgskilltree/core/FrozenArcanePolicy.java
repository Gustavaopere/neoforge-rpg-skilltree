package dev.gustavopere.rpgskilltree.core;

/** Pure provider-bound formulas for A0145-A0148. */
public final class FrozenArcanePolicy {
    private FrozenArcanePolicy() {}

    public static double manaCost(double nativeFinalManaCost, double providerMinimum, int rank, boolean manaLane) {
        requireNonNegative(nativeFinalManaCost, "nativeFinalManaCost");
        requireNonNegative(providerMinimum, "providerMinimum");
        requireRank(rank, 5);
        if (!manaLane || nativeFinalManaCost == 0.0D || rank == 0) return nativeFinalManaCost;
        return Math.max(providerMinimum, nativeFinalManaCost * (1.0D - 0.02D * rank));
    }

    public static ManaPool reconcileManaPool(
        double nativeMaximum,
        double currentMana,
        int previousRank,
        int newRank
    ) {
        requireNonNegative(nativeMaximum, "nativeMaximum");
        requireNonNegative(currentMana, "currentMana");
        requireRank(previousRank, 5);
        requireRank(newRank, 5);
        double maximum = nativeMaximum * (1.0D + 0.02D * newRank);
        return new ManaPool(maximum, Math.min(currentMana, maximum));
    }

    public static double manaRegen(double nativeRate, int rank, boolean providerAllowsRegen) {
        requireNonNegative(nativeRate, "nativeRate");
        requireRank(rank, 5);
        if (!providerAllowsRegen || nativeRate == 0.0D) return 0.0D;
        return nativeRate * (1.0D + 0.03D * rank);
    }

    public static double castTime(
        double nativeTime,
        int rank,
        boolean nonInstant,
        boolean modifiable,
        double providerFloor
    ) {
        requireNonNegative(nativeTime, "nativeTime");
        requireNonNegative(providerFloor, "providerFloor");
        requireRank(rank, 4);
        if (!nonInstant || !modifiable || nativeTime == 0.0D || rank == 0) return nativeTime;
        return Math.max(providerFloor, nativeTime / (1.0D + 0.02D * rank));
    }

    public record ManaPool(double maximum, double current) {}

    private static void requireRank(int rank, int max) {
        if (rank < 0 || rank > max) throw new IllegalArgumentException("invalid rank");
    }

    private static void requireNonNegative(double value, String field) {
        if (!Double.isFinite(value) || value < 0.0D) {
            throw new IllegalArgumentException(field + " must be finite and non-negative");
        }
    }
}
