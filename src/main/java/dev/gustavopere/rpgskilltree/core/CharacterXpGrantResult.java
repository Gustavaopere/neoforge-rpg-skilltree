package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Result of one accepted RPG XP mutation. */
public record CharacterXpGrantResult(
    CharacterProgressionState before,
    CharacterProgressionState after,
    long xpGranted,
    long levelsGained
) {
    public CharacterXpGrantResult {
        Objects.requireNonNull(before);
        Objects.requireNonNull(after);
        if (xpGranted < 0) throw new IllegalArgumentException("xpGranted must be non-negative");
        if (levelsGained < 0) throw new IllegalArgumentException("levelsGained must be non-negative");
        if (after.level() < before.level()) throw new IllegalArgumentException("XP grants cannot reduce level");
        if (levelsGained != after.level() - before.level()) {
            throw new IllegalArgumentException("levelsGained does not match before/after state");
        }
    }
}
