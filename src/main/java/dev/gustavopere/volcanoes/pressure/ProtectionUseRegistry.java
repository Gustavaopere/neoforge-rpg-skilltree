package dev.gustavopere.volcanoes.pressure;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Bounded transaction registry allowing independent server callbacks in the same entity tick to
 * reuse one {@link ProtectionUseSession}. This makes physical {@code resourceDebitKey} ownership
 * effective across Pressure and Respiration without extending a transaction beyond one game tick.
 */
public final class ProtectionUseRegistry {
    private final int capacity;
    private final LinkedHashMap<Key, ProtectionUseSession> sessions = new LinkedHashMap<>();

    public ProtectionUseRegistry(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.capacity = capacity;
    }

    public synchronized ProtectionUseSession session(
            UUID entityId,
            long gameTick,
            Supplier<ProtectionSnapshot> snapshotSupplier
    ) {
        Objects.requireNonNull(entityId, "entityId");
        Objects.requireNonNull(snapshotSupplier, "snapshotSupplier");
        Key key = new Key(entityId, gameTick);
        ProtectionUseSession existing = sessions.get(key);
        if (existing != null) {
            return existing;
        }

        ProtectionSnapshot snapshot = Objects.requireNonNull(
                snapshotSupplier.get(),
                "snapshotSupplier returned null");
        ProtectionUseSession created = snapshot.beginUpdate();
        sessions.put(key, created);
        evictOldestToCapacity();
        return created;
    }

    public synchronized void clear(UUID entityId) {
        Objects.requireNonNull(entityId, "entityId");
        sessions.keySet().removeIf(key -> key.entityId().equals(entityId));
    }

    public synchronized void clear() {
        sessions.clear();
    }

    public synchronized int size() {
        return sessions.size();
    }

    private void evictOldestToCapacity() {
        while (sessions.size() > capacity) {
            Iterator<Map.Entry<Key, ProtectionUseSession>> iterator = sessions.entrySet().iterator();
            if (!iterator.hasNext()) {
                return;
            }
            iterator.next();
            iterator.remove();
        }
    }

    private record Key(UUID entityId, long gameTick) {
        private Key {
            Objects.requireNonNull(entityId, "entityId");
        }
    }
}
