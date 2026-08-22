package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;

public record NodeAccessReconcileResult(
    ProgressionState state,
    Map<String, Integer> removedRanks,
    int pointsRefunded
) {
    public NodeAccessReconcileResult {
        Objects.requireNonNull(state);
        Objects.requireNonNull(removedRanks);
        removedRanks = Map.copyOf(removedRanks);
        if (pointsRefunded < 0) throw new IllegalArgumentException("pointsRefunded must be >= 0");
        if (removedRanks.values().stream().anyMatch(rank -> rank <= 0)) {
            throw new IllegalArgumentException("removed ranks must be positive");
        }
        if (removedRanks.isEmpty() != (pointsRefunded == 0)) {
            throw new IllegalArgumentException("empty removal must refund zero points and removals must refund points");
        }
    }
}
