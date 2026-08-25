package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public record NodeAccessRequirement(
    int minCharacterLevel,
    Set<String> requiredClassIds,
    Map<String, Integer> requiredMastery,
    Set<String> requiredSpecializationIds,
    Set<String> requiredClassChoiceIds,
    Set<String> requiredNodeIds,
    Map<String, Integer> requiredNodeRanks,
    Set<String> requiredDiscoveryKeys
) {
    public NodeAccessRequirement {
        if (minCharacterLevel < 1) throw new IllegalArgumentException("minCharacterLevel must be >= 1");
        Objects.requireNonNull(requiredClassIds);
        Objects.requireNonNull(requiredMastery);
        Objects.requireNonNull(requiredSpecializationIds);
        Objects.requireNonNull(requiredClassChoiceIds);
        Objects.requireNonNull(requiredNodeIds);
        Objects.requireNonNull(requiredNodeRanks);
        Objects.requireNonNull(requiredDiscoveryKeys);
        if (requiredClassIds.stream().anyMatch(NodeAccessRequirement::blank)) {
            throw new IllegalArgumentException("required class ids must not be blank");
        }
        if (requiredMastery.keySet().stream().anyMatch(NodeAccessRequirement::blank)) {
            throw new IllegalArgumentException("required mastery ids must not be blank");
        }
        if (requiredMastery.values().stream().anyMatch(value -> value == null || value < 0)) {
            throw new IllegalArgumentException("required mastery must be >= 0");
        }
        if (requiredSpecializationIds.stream().anyMatch(NodeAccessRequirement::blank)) {
            throw new IllegalArgumentException("required specialization ids must not be blank");
        }
        if (requiredClassChoiceIds.stream().anyMatch(NodeAccessRequirement::blank)) {
            throw new IllegalArgumentException("required class choice ids must not be blank");
        }
        if (requiredNodeIds.stream().anyMatch(NodeAccessRequirement::blank)) {
            throw new IllegalArgumentException("required node ids must not be blank");
        }
        if (requiredNodeRanks.keySet().stream().anyMatch(NodeAccessRequirement::blank)) {
            throw new IllegalArgumentException("required ranked-node ids must not be blank");
        }
        if (requiredNodeRanks.values().stream().anyMatch(value -> value == null || value <= 0)) {
            throw new IllegalArgumentException("required node ranks must be positive");
        }
        if (requiredDiscoveryKeys.stream().anyMatch(NodeAccessRequirement::blank)) {
            throw new IllegalArgumentException("required discovery keys must not be blank");
        }
        requiredClassIds = Set.copyOf(requiredClassIds);
        requiredMastery = Map.copyOf(requiredMastery);
        requiredSpecializationIds = Set.copyOf(requiredSpecializationIds);
        requiredClassChoiceIds = Set.copyOf(requiredClassChoiceIds);
        requiredNodeIds = Set.copyOf(requiredNodeIds);
        requiredNodeRanks = Map.copyOf(requiredNodeRanks);
        requiredDiscoveryKeys = Set.copyOf(requiredDiscoveryKeys);
    }

    public NodeAccessRequirement(
        int minCharacterLevel,
        Set<String> requiredClassIds,
        Map<String, Integer> requiredMastery,
        Set<String> requiredSpecializationIds,
        Set<String> requiredClassChoiceIds,
        Set<String> requiredNodeIds,
        Set<String> requiredDiscoveryKeys
    ) {
        this(
            minCharacterLevel,
            requiredClassIds,
            requiredMastery,
            requiredSpecializationIds,
            requiredClassChoiceIds,
            requiredNodeIds,
            Map.of(),
            requiredDiscoveryKeys
        );
    }

    public NodeAccessRequirement(
        int minCharacterLevel,
        Set<String> requiredClassIds,
        Map<String, Integer> requiredMastery,
        Set<String> requiredSpecializationIds,
        Set<String> requiredClassChoiceIds
    ) {
        this(minCharacterLevel, requiredClassIds, requiredMastery, requiredSpecializationIds, requiredClassChoiceIds, Set.of(), Map.of(), Set.of());
    }

    public NodeAccessRequirement(
        int minCharacterLevel,
        Set<String> requiredClassIds,
        Map<String, Integer> requiredMastery,
        Set<String> requiredSpecializationIds
    ) {
        this(minCharacterLevel, requiredClassIds, requiredMastery, requiredSpecializationIds, Set.of(), Set.of(), Map.of(), Set.of());
    }

    public static NodeAccessRequirement none() {
        return new NodeAccessRequirement(1, Set.of(), Map.of(), Set.of(), Set.of(), Set.of(), Map.of(), Set.of());
    }

    private static boolean blank(String id) {
        return id == null || id.isBlank();
    }
}
