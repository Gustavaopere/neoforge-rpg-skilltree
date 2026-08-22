package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

public record AutomaticClassUnlockResult(
    ProgressionState state,
    Set<String> newlyUnlocked
) {
    public AutomaticClassUnlockResult {
        Objects.requireNonNull(state);
        Objects.requireNonNull(newlyUnlocked);
        newlyUnlocked = Set.copyOf(newlyUnlocked);
    }
}
