package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

/** Confirmed Goety servant command normalized after provider state verification. */
public record GoetyCommandAction(
    ActionOrigin origin,
    String provider,
    String actionId,
    String targetId,
    Set<String> tags,
    int servantCount
) {
    public GoetyCommandAction {
        Objects.requireNonNull(origin);
        Objects.requireNonNull(provider);
        Objects.requireNonNull(actionId);
        Objects.requireNonNull(targetId);
        tags = Set.copyOf(tags);
        if (provider.isBlank() || actionId.isBlank() || targetId.isBlank() || servantCount < 0) {
            throw new IllegalArgumentException("invalid Goety command action");
        }
    }
}
