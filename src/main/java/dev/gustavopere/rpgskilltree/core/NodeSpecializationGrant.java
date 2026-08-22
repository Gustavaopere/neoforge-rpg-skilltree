package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public record NodeSpecializationGrant(
    String nodeId,
    String specializationId,
    int requiredRank
) {
    public NodeSpecializationGrant {
        Objects.requireNonNull(nodeId);
        Objects.requireNonNull(specializationId);
        if (nodeId.isBlank()) throw new IllegalArgumentException("nodeId must not be blank");
        if (specializationId.isBlank()) throw new IllegalArgumentException("specializationId must not be blank");
        if (requiredRank < 1) throw new IllegalArgumentException("requiredRank must be >= 1");
    }
}
