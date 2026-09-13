package dev.gustavopere.rpgskilltree.core.economy;

import java.util.Objects;

/** Read-only projection for an administrative mint intent. */
public record EconomyPreflight(
    ColonyEconomyState sourceState,
    long currentEffectiveSupply,
    long projectedEffectiveSupply,
    long economicCapacity,
    double currentPriceIndex,
    double projectedTargetPriceIndex
) {
    public EconomyPreflight {
        Objects.requireNonNull(sourceState, "sourceState");
        if (currentEffectiveSupply < 0L || projectedEffectiveSupply < 0L) {
            throw new IllegalArgumentException("supply values must be non-negative");
        }
        if (projectedEffectiveSupply < currentEffectiveSupply) {
            throw new IllegalArgumentException("mint projection must not reduce supply");
        }
        if (economicCapacity <= 0L) {
            throw new IllegalArgumentException("economicCapacity must be positive");
        }
        if (!Double.isFinite(currentPriceIndex) || currentPriceIndex <= 0.0D
            || !Double.isFinite(projectedTargetPriceIndex) || projectedTargetPriceIndex <= 0.0D) {
            throw new IllegalArgumentException("price indexes must be finite and positive");
        }
        if (sourceState.effectiveSupply() != currentEffectiveSupply) {
            throw new IllegalArgumentException("current supply must match source state");
        }
        if (Double.compare(sourceState.priceIndex(), currentPriceIndex) != 0) {
            throw new IllegalArgumentException("current price index must match source state");
        }
    }
}
