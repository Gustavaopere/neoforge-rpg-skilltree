package dev.gustavopere.rpgskilltree.core;

public record BossRewardResult(BossProgress progress, int pointsAwarded, boolean firstDefeat) {
    public BossRewardResult {
        if (progress == null) throw new NullPointerException("progress");
        if (pointsAwarded < 0) throw new IllegalArgumentException("pointsAwarded must be >= 0");
    }
}
