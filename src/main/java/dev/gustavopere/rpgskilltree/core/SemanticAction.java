package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Normalized gameplay fact consumed by authorship, anti-farm and XP policy layers. */
public record SemanticAction(
    SemanticActionType type,
    String subjectId,
    ActionOrigin origin,
    SemanticActionAuthorship authorship,
    SemanticActionContext context
) {
    public SemanticAction {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(subjectId, "subjectId");
        Objects.requireNonNull(origin, "origin");
        Objects.requireNonNull(authorship, "authorship");
        Objects.requireNonNull(context, "context");
        if (subjectId.isBlank()) throw new IllegalArgumentException("semantic action subjectId must not be blank");
    }

    public String stableKey() {
        return type.id() + "/" + subjectId;
    }
}
