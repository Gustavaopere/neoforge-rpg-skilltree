package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;

public record NodeRespecResult(
    ProgressionState state,
    Map<String, Integer> removedRanks,
    int pointsRefunded
) {
    public NodeRespecResult {
        Objects.requireNonNull(state);
        Objects.requireNonNull(removedRanks);
        removedRanks = Map.copyOf(removedRanks);
        if (pointsRefunded <= 0) throw new IllegalArgumentException("pointsRefunded must be positive");
        if (removedRanks.isEmpty()) throw new IllegalArgumentException("removedRanks must not be empty");
        if (removedRanks.values().stream().anyMatch(rank -> rank <= 0)) {
            throw new IllegalArgumentException("removed ranks must be positive");
        }
    }
}
