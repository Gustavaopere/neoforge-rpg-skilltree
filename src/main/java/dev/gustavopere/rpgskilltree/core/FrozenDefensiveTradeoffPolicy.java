package dev.gustavopere.rpgskilltree.core;

/** Atomic benefit/cost policies for A0108 and A0109. */
public final class FrozenDefensiveTradeoffPolicy {
    private FrozenDefensiveTradeoffPolicy() {}

    public static Tradeoff stoneSkin(int rank, boolean physicalHookPresent, boolean movementCostHookPresent) {
        boolean active = rank > 0 && physicalHookPresent && movementCostHookPresent;
        return active ? new Tradeoff(true, 0.15D, -0.08D, 0.0D, 0.0D)
            : Tradeoff.inactive();
    }

    public static Tradeoff load(int rank, LoadStage stage, boolean staminaRegenCostHookPresent) {
        boolean active = rank > 0 && stage != LoadStage.NONE && staminaRegenCostHookPresent;
        if (!active) return Tradeoff.inactive();
        return switch (stage) {
            case HEAVY -> new Tradeoff(true, 0.04D, 0.0D, -0.10D, 0.10D);
            case EXTREME -> new Tradeoff(true, 0.08D, 0.0D, -0.20D, 0.20D);
            case NONE -> Tradeoff.inactive();
        };
    }

    public static LoadStage tfcStage(int overburdeningItems) {
        if (overburdeningItems < 0) throw new IllegalArgumentException("overburdeningItems must be non-negative");
        if (overburdeningItems >= 2) return LoadStage.EXTREME;
        return overburdeningItems == 1 ? LoadStage.HEAVY : LoadStage.NONE;
    }

    public enum LoadStage { NONE, HEAVY, EXTREME }

    public record Tradeoff(
        boolean active,
        double physicalReduction,
        double movementSpeedMultiplierDelta,
        double staminaRegenMultiplierDelta,
        double knockbackResistanceDelta
    ) {
        private static Tradeoff inactive() { return new Tradeoff(false, 0.0D, 0.0D, 0.0D, 0.0D); }
    }
}
