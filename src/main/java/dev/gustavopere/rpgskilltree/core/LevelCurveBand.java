package dev.gustavopere.rpgskilltree.core;

/** One segment of an uncapped character XP curve. */
public record LevelCurveBand(long startLevel, long baseXp, long growthPerLevel) {
    public LevelCurveBand {
        if (startLevel < 0L) throw new IllegalArgumentException("startLevel must be non-negative");
        if (baseXp <= 0L) throw new IllegalArgumentException("baseXp must be positive");
        if (growthPerLevel < 0L) throw new IllegalArgumentException("growthPerLevel must be non-negative");
    }
}
