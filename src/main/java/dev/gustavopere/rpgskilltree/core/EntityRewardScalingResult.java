package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.Objects;

/** Auditable decomposition of one bounded reward-risk multiplier. */
public record EntityRewardScalingResult(
    BigDecimal levelMultiplier,
    BigDecimal archetypeMultiplier,
    BigDecimal rarityMultiplier,
    BigDecimal uncappedMultiplier,
    BigDecimal finalMultiplier
) {
    public EntityRewardScalingResult {
        levelMultiplier = requireNonNegative(levelMultiplier, "levelMultiplier");
        archetypeMultiplier = requireNonNegative(archetypeMultiplier, "archetypeMultiplier");
        rarityMultiplier = requireNonNegative(rarityMultiplier, "rarityMultiplier");
        uncappedMultiplier = requireNonNegative(uncappedMultiplier, "uncappedMultiplier");
        finalMultiplier = requireNonNegative(finalMultiplier, "finalMultiplier");
        if (finalMultiplier.compareTo(uncappedMultiplier) > 0) {
            throw new IllegalArgumentException("finalMultiplier must not exceed uncappedMultiplier");
        }
    }

    public BigDecimal scale(BigDecimal baseReward) {
        Objects.requireNonNull(baseReward, "baseReward");
        if (baseReward.signum() < 0) {
            throw new IllegalArgumentException("baseReward must be non-negative");
        }
        return baseReward.multiply(finalMultiplier);
    }

    private static BigDecimal requireNonNegative(BigDecimal value, String label) {
        Objects.requireNonNull(value, label);
        if (value.signum() < 0) {
            throw new IllegalArgumentException(label + " must be non-negative");
        }
        return value;
    }
}
