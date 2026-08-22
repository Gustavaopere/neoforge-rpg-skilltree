package dev.gustavopere.rpgskilltree.core;

import java.util.Arrays;

public final class CharacterLevelCurve {
    private final int maxLevel;
    private final long[] totalXpAtLevel;

    private CharacterLevelCurve(int maxLevel, long[] totalXpAtLevel) {
        this.maxLevel = maxLevel;
        this.totalXpAtLevel = Arrays.copyOf(totalXpAtLevel, totalXpAtLevel.length);
    }

    public static CharacterLevelCurve defaultCurve() {
        int max = 100;
        long[] totals = new long[max + 1];
        totals[1] = 0;
        for (int level = 1; level < max; level++) {
            totals[level + 1] = Math.addExact(totals[level], xpToNext(level));
        }
        return new CharacterLevelCurve(max, totals);
    }

    private static long xpToNext(int level) {
        if (level < 20) return 100L + 35L * (level - 1);
        if (level < 60) return 800L + 60L * (level - 20);
        if (level < 90) return 3_200L + 140L * (level - 60);
        return 7_600L + 400L * (level - 90);
    }

    public int maxLevel() {
        return maxLevel;
    }

    public long xpRequiredForLevel(int level) {
        if (level < 1 || level > maxLevel) throw new IllegalArgumentException("level outside 1.." + maxLevel);
        return totalXpAtLevel[level];
    }

    public int levelForTotalXp(long totalXp) {
        if (totalXp < 0) throw new IllegalArgumentException("total XP must be >= 0");
        int low = 1;
        int high = maxLevel;
        while (low < high) {
            int mid = (low + high + 1) >>> 1;
            if (totalXpAtLevel[mid] <= totalXp) low = mid;
            else high = mid - 1;
        }
        return low;
    }

    public long xpToNextLevel(int level) {
        if (level >= maxLevel) return 0;
        return totalXpAtLevel[level + 1] - totalXpAtLevel[level];
    }
}
