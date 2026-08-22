package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public record NodeAttributeEffect(
    String effectId,
    String nodeId,
    String attributeId,
    ModifierOperation operation,
    double amountPerRank
) {
    public NodeAttributeEffect {
        Objects.requireNonNull(effectId);
        Objects.requireNonNull(nodeId);
        Objects.requireNonNull(attributeId);
        Objects.requireNonNull(operation);
        if (effectId.isBlank() || nodeId.isBlank() || attributeId.isBlank()) {
            throw new IllegalArgumentException("effectId/nodeId/attributeId must not be blank");
        }
        if (!Double.isFinite(amountPerRank)) throw new IllegalArgumentException("amountPerRank must be finite");
        if (operation == ModifierOperation.OVERRIDE) {
            throw new IllegalArgumentException("node attribute effects do not support OVERRIDE");
        }
    }
}
