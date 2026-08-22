package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public record ResolvedNodeAttributeEffect(
    String effectId,
    String nodeId,
    String attributeId,
    ModifierOperation operation,
    double amount
) {
    public ResolvedNodeAttributeEffect {
        Objects.requireNonNull(effectId);
        Objects.requireNonNull(nodeId);
        Objects.requireNonNull(attributeId);
        Objects.requireNonNull(operation);
        if (!Double.isFinite(amount)) throw new IllegalArgumentException("amount must be finite");
    }
}
