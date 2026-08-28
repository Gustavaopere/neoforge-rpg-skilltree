package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Result of one privileged RPG XP rollback operation. */
public record CharacterXpRollbackResult(
    CharacterProgressionState before,
    CharacterProgressionState after,
    long xpRemoved,
    long levelsLost
) {
    public CharacterXpRollbackResult {
        Objects.requireNonNull(before, "before");
        Objects.requireNonNull(after, "after");
        if (xpRemoved < 0L) throw new IllegalArgumentException("xpRemoved must be non-negative");
        if (levelsLost < 0L) throw new IllegalArgumentException("levelsLost must be non-negative");
        if (after.level() > before.level()) {
            throw new IllegalArgumentException("XP rollback cannot increase level");
        }
        if (levelsLost != before.level() - after.level()) {
            throw new IllegalArgumentException("levelsLost does not match before/after state");
        }
    }
}
