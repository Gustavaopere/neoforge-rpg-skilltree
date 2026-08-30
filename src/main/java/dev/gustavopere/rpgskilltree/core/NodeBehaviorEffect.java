package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Server-authoritative binding from an allocated node to a behavioral handler. */
public record NodeBehaviorEffect(
    String effectId,
    String nodeId,
    String handlerId
) {
    public NodeBehaviorEffect {
        Objects.requireNonNull(effectId);
        Objects.requireNonNull(nodeId);
        Objects.requireNonNull(handlerId);
        if (effectId.isBlank() || nodeId.isBlank() || handlerId.isBlank()) {
            throw new IllegalArgumentException("effectId/nodeId/handlerId must not be blank");
        }
    }
}
