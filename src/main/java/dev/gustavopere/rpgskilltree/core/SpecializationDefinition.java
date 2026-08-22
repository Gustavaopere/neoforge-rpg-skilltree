package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record SpecializationDefinition(
    String specializationId,
    Set<String> eligibleClassIds,
    Map<String, Integer> minimumMasteryExperience,
    Set<String> requiredTags
) {
    public SpecializationDefinition {
        Objects.requireNonNull(specializationId);
        Objects.requireNonNull(eligibleClassIds);
        Objects.requireNonNull(minimumMasteryExperience);
        Objects.requireNonNull(requiredTags);
        if (specializationId.isBlank()) throw new IllegalArgumentException("specializationId must not be blank");
        eligibleClassIds = Set.copyOf(eligibleClassIds);
        minimumMasteryExperience = Map.copyOf(minimumMasteryExperience);
        requiredTags = Set.copyOf(requiredTags);
        if (eligibleClassIds.isEmpty()) throw new IllegalArgumentException("specialization requires at least one eligible class");
        if (minimumMasteryExperience.values().stream().anyMatch(v -> v == null || v < 0)) {
            throw new IllegalArgumentException("mastery requirements must be >= 0");
        }
    }
}
