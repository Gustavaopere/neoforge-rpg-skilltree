package dev.gustavopere.rpgskilltree.core;

import java.util.Map;
import java.util.Objects;

/** Additional data-driven requirements layered on top of final-triad class requirements. */
public final class ClassRequirementPolicy {
    private ClassRequirementPolicy() {}

    public static boolean satisfied(ProgressionState state, ClassUnlockDefinition definition) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(definition);

        for (String nodeId : definition.requiredNodeIds()) {
            if (!state.passiveNodes().learned(nodeId)) return false;
        }
        for (Map.Entry<String, Integer> requirement : definition.minimumMasteryExperience().entrySet()) {
            if (state.mastery().experience(requirement.getKey()) < requirement.getValue()) return false;
        }
        return true;
    }
}
