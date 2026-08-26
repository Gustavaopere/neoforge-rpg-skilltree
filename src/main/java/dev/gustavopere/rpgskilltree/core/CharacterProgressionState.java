package dev.gustavopere.rpgskilltree.core;

/** Server-authoritative Level 0+ character progression position. */
public record CharacterProgressionState(long level, long xpIntoLevel) {
    public CharacterProgressionState {
        if (level < 0) throw new IllegalArgumentException("level must be non-negative");
        if (xpIntoLevel < 0) throw new IllegalArgumentException("xpIntoLevel must be non-negative");
    }

    public static CharacterProgressionState empty() {
        return new CharacterProgressionState(0L, 0L);
    }
}
