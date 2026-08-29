package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.Objects;

public record CappedLinearScalingCurve(
    BigDecimal baseMultiplier,
    BigDecimal perLevelMultiplier,
    BigDecimal minimumMultiplier,
    BigDecimal maximumMultiplier
) {
    public CappedLinearScalingCurve {
        Objects.requireNonNull(baseMultiplier, "baseMultiplier");
        Objects.requireNonNull(perLevelMultiplier, "perLevelMultiplier");
        Objects.requireNonNull(minimumMultiplier, "minimumMultiplier");
        Objects.requireNonNull(maximumMultiplier, "maximumMultiplier");
        if (minimumMultiplier.compareTo(maximumMultiplier) > 0) {
            throw new IllegalArgumentException("minimumMultiplier must be <= maximumMultiplier");
        }
    }

    public static CappedLinearScalingCurve of(
        BigDecimal baseMultiplier,
        BigDecimal perLevelMultiplier,
        BigDecimal minimumMultiplier,
        BigDecimal maximumMultiplier
    ) {
        return new CappedLinearScalingCurve(baseMultiplier, perLevelMultiplier, minimumMultiplier, maximumMultiplier);
    }

    public BigDecimal multiplier(long progressionLevel) {
        if (progressionLevel < 0L) {
            throw new IllegalArgumentException("progressionLevel must be non-negative");
        }
        BigDecimal raw = baseMultiplier.add(perLevelMultiplier.multiply(BigDecimal.valueOf(progressionLevel)));
        if (raw.compareTo(minimumMultiplier) < 0) return minimumMultiplier;
        if (raw.compareTo(maximumMultiplier) > 0) return maximumMultiplier;
        return raw;
    }

    public BigDecimal apply(BigDecimal providerValue, long progressionLevel) {
        Objects.requireNonNull(providerValue, "providerValue");
        return providerValue.multiply(multiplier(progressionLevel));
    }
}
