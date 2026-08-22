package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ClassChoiceState {
    private final Map<String, Set<String>> selections;

    private ClassChoiceState(Map<String, Set<String>> selections) {
        Map<String, Set<String>> copy = new HashMap<>();
        selections.forEach((group, values) -> copy.put(group, Set.copyOf(values)));
        this.selections = Map.copyOf(copy);
    }

    public static ClassChoiceState empty() {
        return new ClassChoiceState(Map.of());
    }

    public static ClassChoiceState of(Map<String, Set<String>> selections) {
        if (selections == null) throw new IllegalArgumentException("selections must not be null");
        selections.forEach((group, values) -> {
            if (group == null || group.isBlank()) throw new IllegalArgumentException("choice group must not be blank");
            if (values == null || values.stream().anyMatch(value -> value == null || value.isBlank())) {
                throw new IllegalArgumentException("choice ids must not be blank");
            }
        });
        return new ClassChoiceState(selections);
    }

    public Set<String> selectedInGroup(String groupId) {
        return selections.getOrDefault(groupId, Set.of());
    }

    ClassChoiceState withSelection(String groupId, String choiceId) {
        Map<String, Set<String>> next = new HashMap<>(selections);
        Set<String> values = new HashSet<>(next.getOrDefault(groupId, Set.of()));
        values.add(choiceId);
        next.put(groupId, Set.copyOf(values));
        return new ClassChoiceState(next);
    }

    public Map<String, Set<String>> selections() {
        return selections;
    }
}
