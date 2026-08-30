package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Behavioral effect resolved against the player's current node rank. */
public record ResolvedNodeBehaviorEffect(
    String effectId,
    String nodeId,
    String handlerId,
    int rank
) {
    public ResolvedNodeBehaviorEffect {
        Objects.requireNonNull(effectId);
        Objects.requireNonNull(nodeId);
        Objects.requireNonNull(handlerId);
        if (effectId.isBlank() || nodeId.isBlank() || handlerId.isBlank()) {
            throw new IllegalArgumentException("effectId/nodeId/handlerId must not be blank");
        }
        if (rank <= 0) throw new IllegalArgumentException("rank must be positive");
    }
}
