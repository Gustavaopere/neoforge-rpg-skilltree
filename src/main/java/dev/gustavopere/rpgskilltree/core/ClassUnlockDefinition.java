package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record ClassUnlockDefinition(
    String classId,
    Set<ProgressionDomain> requiredCompletedDomains,
    boolean adjacentConfluence,
    int nonAdjacentBridgeCost,
    Map<String, Integer> minimumMasteryExperience,
    Set<String> requiredNodeIds
) {
    public ClassUnlockDefinition(
        String classId,
        Set<ProgressionDomain> requiredCompletedDomains,
        boolean adjacentConfluence,
        int nonAdjacentBridgeCost
    ) {
        this(classId, requiredCompletedDomains, adjacentConfluence, nonAdjacentBridgeCost, Map.of(), Set.of());
    }

    public ClassUnlockDefinition {
        Objects.requireNonNull(classId);
        Objects.requireNonNull(requiredCompletedDomains);
        Objects.requireNonNull(minimumMasteryExperience);
        Objects.requireNonNull(requiredNodeIds);
        if (classId.isBlank()) throw new IllegalArgumentException("classId must not be blank");
        requiredCompletedDomains = Set.copyOf(requiredCompletedDomains);
        minimumMasteryExperience = Map.copyOf(minimumMasteryExperience);
        requiredNodeIds = Set.copyOf(requiredNodeIds);
        if (requiredCompletedDomains.isEmpty() && minimumMasteryExperience.isEmpty() && requiredNodeIds.isEmpty()) {
            throw new IllegalArgumentException("class requires at least one progression requirement");
        }
        if (minimumMasteryExperience.entrySet().stream().anyMatch(entry ->
            entry.getKey() == null || entry.getKey().isBlank() || entry.getValue() == null || entry.getValue() <= 0)) {
            throw new IllegalArgumentException("mastery requirements must use non-blank lanes and positive experience");
        }
        if (requiredNodeIds.stream().anyMatch(id -> id == null || id.isBlank())) {
            throw new IllegalArgumentException("required node ids must not be blank");
        }
        if (nonAdjacentBridgeCost < 0) throw new IllegalArgumentException("bridge cost must be >= 0");
        if (adjacentConfluence && nonAdjacentBridgeCost != 0) {
            throw new IllegalArgumentException("adjacent confluence cannot have an abnormal bridge surcharge");
        }
        if (requiredCompletedDomains.size() <= 1 && nonAdjacentBridgeCost != 0) {
            throw new IllegalArgumentException("pure/provider class cannot have an abnormal bridge surcharge");
        }
    }
}
