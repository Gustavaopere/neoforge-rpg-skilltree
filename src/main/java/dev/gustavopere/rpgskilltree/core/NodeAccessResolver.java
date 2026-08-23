package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;

public final class NodeAccessResolver {
    private NodeAccessResolver() {}

    public static boolean satisfied(
        ProgressionState state,
        NodeAccessRequirement requirement,
        CharacterLevelCurve curve
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(requirement);
        Objects.requireNonNull(curve);
        if (state.characterProgress(curve).level() < requirement.minCharacterLevel()) return false;
        for (String classId : requirement.requiredClassIds()) {
            if (!state.classProgression().isUnlocked(classId)) return false;
        }
        for (var entry : requirement.requiredMastery().entrySet()) {
            if (state.mastery().experience(entry.getKey()) < entry.getValue()) return false;
        }
        for (String specializationId : requirement.requiredSpecializationIds()) {
            if (!state.specializations().isUnlocked(specializationId)) return false;
        }
        if (!requirement.requiredClassChoiceIds().isEmpty()) {
            java.util.Set<String> selectedChoices = new java.util.HashSet<>();
            state.classChoices().selections().values().forEach(selectedChoices::addAll);
            for (String choiceId : requirement.requiredClassChoiceIds()) {
                if (!selectedChoices.contains(choiceId)) return false;
            }
        }
        for (String nodeId : requirement.requiredNodeIds()) {
            if (!state.passiveNodes().learned(nodeId)) return false;
        }
        for (Map.Entry<String, Integer> rankedRequirement : requirement.requiredNodeRanks().entrySet()) {
            if (state.passiveNodes().rank(rankedRequirement.getKey()) < rankedRequirement.getValue()) return false;
        }
        for (Map<String, Integer> alternatives : requirement.anyRequiredNodeRankGroups()) {
            boolean groupSatisfied = alternatives.entrySet().stream().anyMatch(
                entry -> state.passiveNodes().rank(entry.getKey()) >= entry.getValue()
            );
            if (!groupSatisfied) return false;
        }
        for (String discoveryKey : requirement.requiredDiscoveryKeys()) {
            if (!state.discoveries().contains(discoveryKey)) return false;
        }
        return true;
    }
}
