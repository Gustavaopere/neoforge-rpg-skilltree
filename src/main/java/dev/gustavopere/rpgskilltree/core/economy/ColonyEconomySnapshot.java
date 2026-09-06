package dev.gustavopere.rpgskilltree.core.economy;

import java.util.Objects;

/** Immutable result of one deterministic economic settlement. */
public record ColonyEconomySnapshot(
    ColonyEconomyState state,
    long economicCapacity,
    long effectiveSupply,
    double targetPriceIndex,
    double periodInflationRate
) {
    public ColonyEconomySnapshot {
        Objects.requireNonNull(state, "state");
        if (economicCapacity <= 0L) {
            throw new IllegalArgumentException("economicCapacity must be positive");
        }
        if (effectiveSupply < 0L) {
            throw new IllegalArgumentException("effectiveSupply must be non-negative");
        }
        if (!Double.isFinite(targetPriceIndex) || targetPriceIndex <= 0.0D) {
            throw new IllegalArgumentException("targetPriceIndex must be finite and positive");
        }
        if (!Double.isFinite(periodInflationRate)) {
            throw new IllegalArgumentException("periodInflationRate must be finite");
        }
        if (state.currentEconomicCapacity() != economicCapacity) {
            throw new IllegalArgumentException("snapshot capacity must match state");
        }
        if (state.effectiveSupply() != effectiveSupply) {
            throw new IllegalArgumentException("snapshot supply must match state");
        }
    }
}
