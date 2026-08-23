package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Confirmed Eidolon crucible recipe completion attributed to a contributing player. */
public record EidolonAlchemyAction(
    ActionOrigin origin,
    String recipeId,
    boolean confirmedResult,
    boolean firstCompletion
) {
    public EidolonAlchemyAction {
        Objects.requireNonNull(origin);
        Objects.requireNonNull(recipeId);
        if (recipeId.isBlank()) throw new IllegalArgumentException("recipeId must not be blank");
    }
}
