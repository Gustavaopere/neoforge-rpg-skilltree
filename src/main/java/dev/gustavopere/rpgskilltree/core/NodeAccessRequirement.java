package dev.gustavopere.rpgskilltree.core;

import java.util.List;
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
    List<Map<String, Integer>> anyRequiredNodeRankGroups,
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
        Objects.requireNonNull(anyRequiredNodeRankGroups);
        Objects.requireNonNull(requiredDiscoveryKeys);
        validateIds(requiredClassIds, "required class ids");
        validateThresholdMap(requiredMastery, "required mastery", true);
        validateIds(requiredSpecializationIds, "required specialization ids");
        validateIds(requiredClassChoiceIds, "required class choice ids");
        validateIds(requiredNodeIds, "required node ids");
        validateThresholdMap(requiredNodeRanks, "required node ranks", false);
        if (anyRequiredNodeRankGroups.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("required node rank groups must not contain null");
        }
        for (Map<String, Integer> group : anyRequiredNodeRankGroups) {
            if (group.isEmpty()) throw new IllegalArgumentException("required node rank groups must not be empty");
            validateThresholdMap(group, "required node rank group", false);
        }
        validateIds(requiredDiscoveryKeys, "required discovery keys");

        requiredClassIds = Set.copyOf(requiredClassIds);
        requiredMastery = Map.copyOf(requiredMastery);
        requiredSpecializationIds = Set.copyOf(requiredSpecializationIds);
        requiredClassChoiceIds = Set.copyOf(requiredClassChoiceIds);
        requiredNodeIds = Set.copyOf(requiredNodeIds);
        requiredNodeRanks = Map.copyOf(requiredNodeRanks);
        anyRequiredNodeRankGroups = anyRequiredNodeRankGroups.stream().map(Map::copyOf).toList();
        requiredDiscoveryKeys = Set.copyOf(requiredDiscoveryKeys);
    }

    /** Backwards-compatible constructor for requirements created before ranked prerequisites existed. */
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
            List.of(),
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
        this(minCharacterLevel, requiredClassIds, requiredMastery, requiredSpecializationIds, requiredClassChoiceIds, Set.of(), Map.of(), List.of(), Set.of());
    }

    public NodeAccessRequirement(
        int minCharacterLevel,
        Set<String> requiredClassIds,
        Map<String, Integer> requiredMastery,
        Set<String> requiredSpecializationIds
    ) {
        this(minCharacterLevel, requiredClassIds, requiredMastery, requiredSpecializationIds, Set.of(), Set.of(), Map.of(), List.of(), Set.of());
    }

    public static NodeAccessRequirement none() {
        return new NodeAccessRequirement(1, Set.of(), Map.of(), Set.of(), Set.of(), Set.of(), Map.of(), List.of(), Set.of());
    }

    private static void validateIds(Set<String> ids, String label) {
        if (ids.stream().anyMatch(NodeAccessRequirement::blank)) {
            throw new IllegalArgumentException(label + " must not be blank");
        }
    }

    private static void validateThresholdMap(Map<String, Integer> thresholds, String label, boolean allowZero) {
        if (thresholds.keySet().stream().anyMatch(NodeAccessRequirement::blank)) {
            throw new IllegalArgumentException(label + " ids must not be blank");
        }
        int minimum = allowZero ? 0 : 1;
        if (thresholds.values().stream().anyMatch(value -> value == null || value < minimum)) {
            throw new IllegalArgumentException(label + " values must be >= " + minimum);
        }
    }

    private static boolean blank(String id) {
        return id == null || id.isBlank();
    }
}
