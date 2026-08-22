package dev.gustavopere.rpgskilltree.core;

import java.util.HashSet;
import java.util.Set;

public final class SpecializationProgressionState {
    private final Set<String> unlockedSpecializationIds;

    private SpecializationProgressionState(Set<String> unlockedSpecializationIds) {
        this.unlockedSpecializationIds = Set.copyOf(unlockedSpecializationIds);
    }

    public static SpecializationProgressionState empty() {
        return new SpecializationProgressionState(Set.of());
    }

    public static SpecializationProgressionState of(Set<String> unlockedSpecializationIds) {
        if (unlockedSpecializationIds == null) throw new IllegalArgumentException("unlockedSpecializationIds must not be null");
        if (unlockedSpecializationIds.stream().anyMatch(id -> id == null || id.isBlank())) {
            throw new IllegalArgumentException("specialization ids must not be blank");
        }
        return new SpecializationProgressionState(unlockedSpecializationIds);
    }

    public boolean isUnlocked(String specializationId) {
        return unlockedSpecializationIds.contains(specializationId);
    }

    public Set<String> unlockedSpecializationIds() {
        return unlockedSpecializationIds;
    }

    public SpecializationProgressionState unlock(String specializationId) {
        if (specializationId == null || specializationId.isBlank()) throw new IllegalArgumentException("specializationId must not be blank");
        if (unlockedSpecializationIds.contains(specializationId)) return this;
        Set<String> next = new HashSet<>(unlockedSpecializationIds);
        next.add(specializationId);
        return new SpecializationProgressionState(next);
    }

    public SpecializationProgressionState without(String specializationId) {
        if (specializationId == null || specializationId.isBlank()) throw new IllegalArgumentException("specializationId must not be blank");
        if (!unlockedSpecializationIds.contains(specializationId)) return this;
        Set<String> next = new HashSet<>(unlockedSpecializationIds);
        next.remove(specializationId);
        return new SpecializationProgressionState(next);
    }
}
