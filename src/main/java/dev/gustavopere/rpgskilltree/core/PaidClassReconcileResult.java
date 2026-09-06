package dev.gustavopere.rpgskilltree.core;

import java.util.Set;

public record PaidClassReconcileResult(
    ProgressionState state,
    Set<String> removedClassIds,
    int bridgePointsRefunded
) {
    public PaidClassReconcileResult {
        if (state == null) throw new NullPointerException("state");
        removedClassIds = Set.copyOf(removedClassIds);
        if (bridgePointsRefunded < 0) {
            throw new IllegalArgumentException("bridgePointsRefunded must be >= 0");
        }
    }
}
