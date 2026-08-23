package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record NodeAccessRequirement(
    int minCharacterLevel,
    Set<String> requiredClassIds,
    Map<String, Integer> requiredMastery,
    Set<String> requiredSpecializationIds,
    Set<String> requiredClassChoiceIds
) {
    public NodeAccessRequirement {
        if (minCharacterLevel < 1) throw new IllegalArgumentException("minCharacterLevel must be >= 1");
        Objects.requireNonNull(requiredClassIds);
        Objects.requireNonNull(requiredMastery);
        Objects.requireNonNull(requiredSpecializationIds);
        Objects.requireNonNull(requiredClassChoiceIds);
        if (requiredClassIds.stream().anyMatch(id -> id == null || id.isBlank())) {
            throw new IllegalArgumentException("required class ids must not be blank");
        }
        if (requiredMastery.keySet().stream().anyMatch(id -> id == null || id.isBlank())) {
            throw new IllegalArgumentException("required mastery ids must not be blank");
        }
        if (requiredMastery.values().stream().anyMatch(value -> value == null || value < 0)) {
            throw new IllegalArgumentException("required mastery must be >= 0");
        }
        if (requiredSpecializationIds.stream().anyMatch(id -> id == null || id.isBlank())) {
            throw new IllegalArgumentException("required specialization ids must not be blank");
        }
        if (requiredClassChoiceIds.stream().anyMatch(id -> id == null || id.isBlank())) {
            throw new IllegalArgumentException("required class choice ids must not be blank");
        }
        requiredClassIds = Set.copyOf(requiredClassIds);
        requiredMastery = Map.copyOf(requiredMastery);
        requiredSpecializationIds = Set.copyOf(requiredSpecializationIds);
        requiredClassChoiceIds = Set.copyOf(requiredClassChoiceIds);
    }

    public NodeAccessRequirement(
        int minCharacterLevel,
        Set<String> requiredClassIds,
        Map<String, Integer> requiredMastery,
        Set<String> requiredSpecializationIds
    ) {
        this(minCharacterLevel, requiredClassIds, requiredMastery, requiredSpecializationIds, Set.of());
    }

    public static NodeAccessRequirement none() {
        return new NodeAccessRequirement(1, Set.of(), Map.of(), Set.of(), Set.of());
    }
}
