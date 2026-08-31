package dev.gustavopere.rpgskilltree.core;

import java.util.HashSet;
import java.util.Set;

public final class ClassProgressionState {
    private final Set<String> unlockedClassIds;
    private final Set<String> paidBridgeClassIds;

    private ClassProgressionState(Set<String> unlockedClassIds, Set<String> paidBridgeClassIds) {
        this.unlockedClassIds = Set.copyOf(unlockedClassIds);
        this.paidBridgeClassIds = Set.copyOf(paidBridgeClassIds);
    }

    public static ClassProgressionState empty() {
        return new ClassProgressionState(Set.of(), Set.of());
    }

    public static ClassProgressionState of(Set<String> unlockedClassIds) {
        return of(unlockedClassIds, Set.of());
    }

    public static ClassProgressionState of(Set<String> unlockedClassIds, Set<String> paidBridgeClassIds) {
        if (unlockedClassIds == null) throw new IllegalArgumentException("unlockedClassIds must not be null");
        if (paidBridgeClassIds == null) throw new IllegalArgumentException("paidBridgeClassIds must not be null");
        if (unlockedClassIds.stream().anyMatch(id -> id == null || id.isBlank())) {
            throw new IllegalArgumentException("class ids must not be blank");
        }
        if (paidBridgeClassIds.stream().anyMatch(id -> id == null || id.isBlank())) {
            throw new IllegalArgumentException("paid bridge class ids must not be blank");
        }
        if (!unlockedClassIds.containsAll(paidBridgeClassIds)) {
            throw new IllegalArgumentException("paid bridge classes must also be unlocked");
        }
        return new ClassProgressionState(unlockedClassIds, paidBridgeClassIds);
    }

    public boolean isUnlocked(String classId) {
        return unlockedClassIds.contains(classId);
    }

    public Set<String> unlockedClassIds() {
        return unlockedClassIds;
    }

    public boolean bridgePaid(String classId) {
        return paidBridgeClassIds.contains(classId);
    }

    public Set<String> paidBridgeClassIds() {
        return paidBridgeClassIds;
    }

    public ClassProgressionState without(String classId) {
        if (classId == null || classId.isBlank()) throw new IllegalArgumentException("classId must not be blank");
        if (!unlockedClassIds.contains(classId)) return this;
        Set<String> next = new HashSet<>(unlockedClassIds);
        next.remove(classId);
        Set<String> paid = new HashSet<>(paidBridgeClassIds);
        paid.remove(classId);
        return new ClassProgressionState(next, paid);
    }

    public ClassProgressionState unlock(String classId) {
        if (classId == null || classId.isBlank()) throw new IllegalArgumentException("classId must not be blank");
        if (unlockedClassIds.contains(classId)) return this;
        Set<String> next = new HashSet<>(unlockedClassIds);
        next.add(classId);
        return new ClassProgressionState(next, paidBridgeClassIds);
    }

    public ClassProgressionState unlockWithBridgePayment(String classId) {
        if (classId == null || classId.isBlank()) throw new IllegalArgumentException("classId must not be blank");
        Set<String> next = new HashSet<>(unlockedClassIds);
        next.add(classId);
        Set<String> paid = new HashSet<>(paidBridgeClassIds);
        paid.add(classId);
        return new ClassProgressionState(next, paid);
    }
}
