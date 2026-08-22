package dev.gustavopere.rpgskilltree.core;

public record ClassUnlockMutationResult(ProgressionState state, boolean unlockedNow, int bridgePointsSpent) {
    public ClassUnlockMutationResult {
        if (state == null) throw new NullPointerException("state");
        if (bridgePointsSpent < 0) throw new IllegalArgumentException("bridgePointsSpent must be >= 0");
    }
}
