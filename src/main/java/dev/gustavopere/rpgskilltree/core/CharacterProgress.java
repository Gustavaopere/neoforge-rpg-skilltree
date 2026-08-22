package dev.gustavopere.rpgskilltree.core;

public record CharacterProgress(long totalXp, int level, int levelPointsEarned, long xpIntoLevel, long xpToNextLevel) {
    public CharacterProgress {
        if (totalXp < 0 || level < 1 || levelPointsEarned < 0 || xpIntoLevel < 0 || xpToNextLevel < 0) {
            throw new IllegalArgumentException("character progress values must be non-negative");
        }
    }

    public static CharacterProgress fromTotalXp(CharacterLevelCurve curve, long totalXp) {
        int level = curve.levelForTotalXp(totalXp);
        long floor = curve.xpRequiredForLevel(level);
        return new CharacterProgress(totalXp, level, level - 1, totalXp - floor, curve.xpToNextLevel(level));
    }
}
