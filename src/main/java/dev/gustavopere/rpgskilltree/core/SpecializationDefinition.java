package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record SpecializationDefinition(
    String specializationId,
    String providerId,
    Set<String> eligibleClassIds,
    Map<String, Integer> minimumMasteryExperience,
    Set<String> requiredTags
) {
    public SpecializationDefinition {
        Objects.requireNonNull(specializationId);
        Objects.requireNonNull(providerId);
        Objects.requireNonNull(eligibleClassIds);
        Objects.requireNonNull(minimumMasteryExperience);
        Objects.requireNonNull(requiredTags);
        if (specializationId.isBlank()) {
            throw new IllegalArgumentException("specializationId must not be blank");
        }
        if (providerId.isBlank()) {
            throw new IllegalArgumentException("providerId must not be blank");
        }
        eligibleClassIds = Set.copyOf(eligibleClassIds);
        minimumMasteryExperience = Map.copyOf(minimumMasteryExperience);
        requiredTags = Set.copyOf(requiredTags);
        if (minimumMasteryExperience.values().stream().anyMatch(value -> value == null || value < 0)) {
            throw new IllegalArgumentException("mastery requirements must be >= 0");
        }
    }

    /** Compatibility constructor for provider-agnostic core definitions. */
    public SpecializationDefinition(
        String specializationId,
        Set<String> eligibleClassIds,
        Map<String, Integer> minimumMasteryExperience,
        Set<String> requiredTags
    ) {
        this(specializationId, "rpgskilltree", eligibleClassIds, minimumMasteryExperience, requiredTags);
    }

    public boolean requiresClass() {
        return !eligibleClassIds.isEmpty();
    }
}
