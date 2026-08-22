package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

public record AutomaticClassReconcileResult(
    ProgressionState state,
    Set<String> newlyUnlocked,
    Set<String> removed
) {
    public AutomaticClassReconcileResult {
        Objects.requireNonNull(state);
        Objects.requireNonNull(newlyUnlocked);
        Objects.requireNonNull(removed);
        newlyUnlocked = Set.copyOf(newlyUnlocked);
        removed = Set.copyOf(removed);
    }
}
