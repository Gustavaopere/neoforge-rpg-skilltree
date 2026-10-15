package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public final class NodeAccessResolver {
    private NodeAccessResolver() {}

    public static boolean satisfied(ProgressionState state, NodeAccessRequirement requirement, CharacterLevelCurve curve) {
        return satisfied(state, requirement, curve, AttributeRanks.empty());
    }

    /** A minimum rank is a prerequisite only: no attribute rank or Core Point is debited. */
    public static boolean satisfied(
        ProgressionState state, NodeAccessRequirement requirement, CharacterLevelCurve curve,
        AttributeRanks investedRanks
    ) {
        Objects.requireNonNull(state); Objects.requireNonNull(requirement); Objects.requireNonNull(curve);
        Objects.requireNonNull(investedRanks);
        if (state.characterProgress(curve).level() < requirement.minCharacterLevel()) return false;
        for (String classId : requirement.requiredClassIds()) if (!state.classProgression().isUnlocked(classId)) return false;
        for (var entry : requirement.requiredMastery().entrySet()) if (state.mastery().experience(entry.getKey()) < entry.getValue()) return false;
        for (String specializationId : requirement.requiredSpecializationIds()) if (!state.specializations().isUnlocked(specializationId)) return false;
        if (!requirement.requiredClassChoiceIds().isEmpty()) {
            java.util.Set<String> selectedChoices = new java.util.HashSet<>();
            state.classChoices().selections().values().forEach(selectedChoices::addAll);
            for (String choiceId : requirement.requiredClassChoiceIds()) if (!selectedChoices.contains(choiceId)) return false;
        }
        for (String nodeId : requirement.requiredNodeIds()) if (!state.passiveNodes().learned(nodeId)) return false;
        for (var entry : requirement.requiredNodeRanks().entrySet()) if (state.passiveNodes().rank(entry.getKey()) < entry.getValue()) return false;
        for (String discoveryKey : requirement.requiredDiscoveryKeys()) if (!state.discoveries().contains(discoveryKey)) return false;
        for (var entry : requirement.requiredAttributeRanks().entrySet()) {
            if (investedRanks.rank(entry.getKey()) < entry.getValue()) return false;
        }
        return true;
    }
}
