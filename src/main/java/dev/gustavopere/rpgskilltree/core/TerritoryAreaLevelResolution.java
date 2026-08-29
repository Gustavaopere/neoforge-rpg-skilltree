package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

/** Immutable, auditable result of one territory/area-level lookup. */
public record TerritoryAreaLevelResolution(
    TerritoryKey primaryTerritory,
    long resolvedLevel,
    List<TerritoryAreaLevelSample> samples,
    long totalWeight
) {
    public TerritoryAreaLevelResolution {
        Objects.requireNonNull(primaryTerritory, "primaryTerritory");
        Objects.requireNonNull(samples, "samples");
        if (resolvedLevel < 0L) throw new IllegalArgumentException("resolvedLevel must be non-negative");
        if (samples.isEmpty() || samples.size() > 4) {
            throw new IllegalArgumentException("area-level resolution must contain between one and four samples");
        }
        samples = List.copyOf(samples);
        long computedWeight = 0L;
        boolean foundPrimary = false;
        for (TerritoryAreaLevelSample sample : samples) {
            Objects.requireNonNull(sample, "sample");
            computedWeight = Math.addExact(computedWeight, sample.weight());
            foundPrimary |= sample.territory().equals(primaryTerritory);
        }
        if (!foundPrimary) throw new IllegalArgumentException("area-level samples must include primary territory");
        if (totalWeight <= 0L || computedWeight != totalWeight) {
            throw new IllegalArgumentException("totalWeight must equal the sum of sample weights");
        }
    }
}
