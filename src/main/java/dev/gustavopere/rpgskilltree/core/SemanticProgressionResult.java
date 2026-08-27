package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Pure result joining semantic XP evaluation with the resulting authoritative Core state. */
public record SemanticProgressionResult(
    CoreProgressionState state,
    SemanticXpResult semanticXp
) {
    public SemanticProgressionResult {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(semanticXp, "semanticXp");
    }
}
