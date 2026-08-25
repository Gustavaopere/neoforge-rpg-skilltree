package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record SpecializationDefinition(
    String specializationId,
    Set<String> eligibleClassIds,
    Map<String, Integer> minimumMasteryExperience,
    Set<String> requiredTags,
    int minimumCharacterLevel,
    Map<ProgressionDomain, Integer> minimumDomainInvestment
) {
    public SpecializationDefinition {
        Objects.requireNonNull(specializationId);
        Objects.requireNonNull(eligibleClassIds);
        Objects.requireNonNull(minimumMasteryExperience);
        Objects.requireNonNull(requiredTags);
        Objects.requireNonNull(minimumDomainInvestment);
        if (specializationId.isBlank()) {
            throw new IllegalArgumentException("specializationId must not be blank");
        }
        eligibleClassIds = Set.copyOf(eligibleClassIds);
        minimumMasteryExperience = Map.copyOf(minimumMasteryExperience);
        requiredTags = Set.copyOf(requiredTags);
        minimumDomainInvestment = Map.copyOf(minimumDomainInvestment);
        if (minimumCharacterLevel < 1) {
            throw new IllegalArgumentException("minimumCharacterLevel must be >= 1");
        }
        if (minimumMasteryExperience.values().stream().anyMatch(value -> value == null || value < 0)) {
            throw new IllegalArgumentException("mastery requirements must be >= 0");
        }
        if (minimumDomainInvestment.entrySet().stream().anyMatch(entry ->
            entry.getKey() == null || entry.getValue() == null || entry.getValue() < 0)) {
            throw new IllegalArgumentException("domain investment requirements must be >= 0");
        }
    }

    public SpecializationDefinition(
        String specializationId,
        Set<String> eligibleClassIds,
        Map<String, Integer> minimumMasteryExperience,
        Set<String> requiredTags
    ) {
        this(specializationId, eligibleClassIds, minimumMasteryExperience, requiredTags, 1, Map.of());
    }

    public boolean requiresClass() {
        return !eligibleClassIds.isEmpty();
    }
}
