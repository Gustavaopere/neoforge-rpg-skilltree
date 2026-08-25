package dev.gustavopere.rpgskilltree.core;

import java.util.Set;

/** Canonical node-ID gate and atomic stamina tradeoff for A0139. */
public final class EfficientMetabolismPolicy {
    private static final Set<String> METABOLIC = Set.of(
        "A0115", "A0117", "A0119", "A0121", "A0123", "A0125",
        "A0127", "A0129", "A0131", "A0133", "A0135", "A0137");
    private static final Set<String> PROFESSIONAL_OR_CLIMATIC = Set.of(
        "A0123", "A0125", "A0127", "A0129", "A0131", "A0135", "A0137");

    private EfficientMetabolismPolicy() {}

    public static Result evaluate(
        FrozenSurvivalPerkRanks ranks,
        boolean naturalStaminaRegenHookPresent,
        boolean hydrationProviderPresent
    ) {
        if (ranks.rank("A0139") <= 0 || !naturalStaminaRegenHookPresent) return Result.inactive();
        long distinct = METABOLIC.stream().filter(code -> ranks.rank(code) > 0).count();
        boolean route = PROFESSIONAL_OR_CLIMATIC.stream().anyMatch(code -> ranks.rank(code) > 0);
        if (distinct < 3 || !route) return Result.inactive();
        return new Result(true, 0.12D, hydrationProviderPresent ? 0.12D : 0.0D, -0.08D);
    }

    public record Result(
        boolean active,
        double metabolicSaving,
        double hydrationSaving,
        double naturalStaminaRegenMultiplierDelta
    ) {
        private static Result inactive() { return new Result(false, 0.0D, 0.0D, 0.0D); }

        public double saving(BodyCostResolver.Channel channel, BodyCostResolver.Cause cause) {
            if (!active || cause == BodyCostResolver.Cause.BASAL
                || cause == BodyCostResolver.Cause.UNATTRIBUTED) return 0.0D;
            return channel == BodyCostResolver.Channel.METABOLIC ? metabolicSaving : hydrationSaving;
        }
    }
}
