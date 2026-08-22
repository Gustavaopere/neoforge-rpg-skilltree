package dev.gustavopere.rpgskilltree.core;

import java.util.HashSet;
import java.util.Set;

public final class ClassProgressionState {
    private final Set<String> unlockedClassIds;

    private ClassProgressionState(Set<String> unlockedClassIds) {
        this.unlockedClassIds = Set.copyOf(unlockedClassIds);
    }

    public static ClassProgressionState empty() {
        return new ClassProgressionState(Set.of());
    }

    public static ClassProgressionState of(Set<String> unlockedClassIds) {
        if (unlockedClassIds == null) throw new IllegalArgumentException("unlockedClassIds must not be null");
        if (unlockedClassIds.stream().anyMatch(id -> id == null || id.isBlank())) {
            throw new IllegalArgumentException("class ids must not be blank");
        }
        return new ClassProgressionState(unlockedClassIds);
    }

    public boolean isUnlocked(String classId) {
        return unlockedClassIds.contains(classId);
    }

    public Set<String> unlockedClassIds() {
        return unlockedClassIds;
    }

    public ClassProgressionState without(String classId) {
        if (classId == null || classId.isBlank()) throw new IllegalArgumentException("classId must not be blank");
        if (!unlockedClassIds.contains(classId)) return this;
        Set<String> next = new HashSet<>(unlockedClassIds);
        next.remove(classId);
        return new ClassProgressionState(next);
    }

    public ClassProgressionState unlock(String classId) {
        if (classId == null || classId.isBlank()) throw new IllegalArgumentException("classId must not be blank");
        if (unlockedClassIds.contains(classId)) return this;
        Set<String> next = new HashSet<>(unlockedClassIds);
        next.add(classId);
        return new ClassProgressionState(next);
    }
}
