package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

public record ClassUnlockDefinition(
    String classId,
    Set<ProgressionDomain> requiredCompletedDomains,
    boolean adjacentConfluence,
    int nonAdjacentBridgeCost
) {
    public ClassUnlockDefinition {
        Objects.requireNonNull(classId);
        Objects.requireNonNull(requiredCompletedDomains);
        if (classId.isBlank()) throw new IllegalArgumentException("classId must not be blank");
        requiredCompletedDomains = Set.copyOf(requiredCompletedDomains);
        if (requiredCompletedDomains.isEmpty()) throw new IllegalArgumentException("class requires at least one completed domain");
        if (nonAdjacentBridgeCost < 0) throw new IllegalArgumentException("bridge cost must be >= 0");
        if (adjacentConfluence && nonAdjacentBridgeCost != 0) {
            throw new IllegalArgumentException("adjacent confluence cannot have an abnormal bridge surcharge");
        }
        if (requiredCompletedDomains.size() == 1 && nonAdjacentBridgeCost != 0) {
            throw new IllegalArgumentException("pure class cannot have an abnormal bridge surcharge");
        }
    }
}
