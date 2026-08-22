package dev.gustavopere.rpgskilltree.core;

import java.util.Set;

public final class ClassChoicePolicy {
    private ClassChoicePolicy() {}

    public static boolean canSelect(ClassChoiceState state, ClassChoiceDefinition definition, Set<String> unlockedClassIds, int effectiveGroupCapacity) {
        if (effectiveGroupCapacity <= 0) return false;
        if (!unlockedClassIds.contains(definition.requiredClassId())) return false;
        Set<String> selected = state.selectedInGroup(definition.groupId());
        if (selected.contains(definition.choiceId())) return true;
        return selected.size() < effectiveGroupCapacity;
    }

    public static ClassChoiceState select(ClassChoiceState state, ClassChoiceDefinition definition, Set<String> unlockedClassIds, int effectiveGroupCapacity) {
        if (!canSelect(state, definition, unlockedClassIds, effectiveGroupCapacity)) {
            throw new IllegalArgumentException("choice cannot be selected: " + definition.choiceId());
        }
        return state.withSelection(definition.groupId(), definition.choiceId());
    }
}
