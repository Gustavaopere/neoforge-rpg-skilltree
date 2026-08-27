package dev.gustavopere.rpgskilltree.core;

/**
 * Periodic Core Point award schedule for uncapped Character Level progression.
 *
 * @param firstAwardLevel first reached level that grants an award; must be >= 1
 * @param levelsPerAward distance in levels between awards; must be positive
 * @param pointsPerAward Core Progression Points granted at each award level; must be positive
 */
public record PeriodicLevelCorePointAwardPolicy(
    long firstAwardLevel,
    long levelsPerAward,
    long pointsPerAward
) implements LevelCorePointAwardPolicy {
    public PeriodicLevelCorePointAwardPolicy {
        if (firstAwardLevel < 1L) {
            throw new IllegalArgumentException("firstAwardLevel must be at least 1");
        }
        if (levelsPerAward <= 0L) {
            throw new IllegalArgumentException("levelsPerAward must be positive");
        }
        if (pointsPerAward <= 0L) {
            throw new IllegalArgumentException("pointsPerAward must be positive");
        }
    }

    @Override
    public long pointsAwarded(long beforeLevel, long afterLevel) {
        if (beforeLevel < 0L || afterLevel < 0L) {
            throw new IllegalArgumentException("levels must be non-negative");
        }
        if (afterLevel < beforeLevel) {
            throw new IllegalArgumentException("afterLevel must not be below beforeLevel");
        }

        long awardsBefore = awardsAtOrBelow(beforeLevel);
        long awardsAfter = awardsAtOrBelow(afterLevel);
        long awardCount = Math.subtractExact(awardsAfter, awardsBefore);
        return Math.multiplyExact(awardCount, pointsPerAward);
    }

    @Override
    public String canonicalForm() {
        return "periodic:first=" + firstAwardLevel
            + ",interval=" + levelsPerAward
            + ",points=" + pointsPerAward;
    }

    private long awardsAtOrBelow(long level) {
        if (level < firstAwardLevel) return 0L;
        return Math.addExact((level - firstAwardLevel) / levelsPerAward, 1L);
    }
}
