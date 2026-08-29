package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** One weighted territory contribution to a boundary-smoothed area-level query. */
public record TerritoryAreaLevelSample(
    TerritoryKey territory,
    NativeAreaLevelBreakdown breakdown,
    long weight
) {
    public TerritoryAreaLevelSample {
        Objects.requireNonNull(territory, "territory");
        Objects.requireNonNull(breakdown, "breakdown");
        if (!territory.equals(breakdown.territoryKey())) {
            throw new IllegalArgumentException("sample territory must match Native Area breakdown territory");
        }
        if (weight <= 0L) throw new IllegalArgumentException("sample weight must be positive");
    }
}
