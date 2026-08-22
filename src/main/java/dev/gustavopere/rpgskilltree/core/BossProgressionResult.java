package dev.gustavopere.rpgskilltree.core;

public record BossProgressionResult(ProgressionState state, int pointsAwarded, boolean firstDefeat) {
    public BossProgressionResult {
        if (state == null) throw new NullPointerException("state");
        if (pointsAwarded < 0) throw new IllegalArgumentException("pointsAwarded must be >= 0");
    }
}
