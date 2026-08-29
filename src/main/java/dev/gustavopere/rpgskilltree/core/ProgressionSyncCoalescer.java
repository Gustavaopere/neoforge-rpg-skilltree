package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Coalesces multiple dirty progression mutations into one pending entry per player.
 *
 * <p>The class is deliberately transport-agnostic. Runtime code may mark several
 * mutations during one server tick and drain one final immutable dirty set later.</p>
 */
public final class ProgressionSyncCoalescer {
    private final LinkedHashMap<UUID, ProgressionDirtySet> pending = new LinkedHashMap<>();

    /**
     * Marks dirty work for one player.
     *
     * @return true when the pending state changed; false for empty/no-op duplicate marks.
     */
    public synchronized boolean mark(UUID playerId, ProgressionDirtySet dirty) {
        Objects.requireNonNull(playerId, "playerId");
        Objects.requireNonNull(dirty, "dirty");
        if (dirty.isEmpty()) return false;

        ProgressionDirtySet current = pending.get(playerId);
        if (current == null) {
            pending.put(playerId, dirty);
            return true;
        }

        ProgressionDirtySet merged = current.merge(dirty);
        if (merged.equals(current)) return false;
        pending.put(playerId, merged);
        return true;
    }

    public synchronized Optional<ProgressionDirtySet> pending(UUID playerId) {
        Objects.requireNonNull(playerId, "playerId");
        return Optional.ofNullable(pending.get(playerId));
    }

    public synchronized Optional<ProgressionDirtySet> drain(UUID playerId) {
        Objects.requireNonNull(playerId, "playerId");
        return Optional.ofNullable(pending.remove(playerId));
    }

    public synchronized Map<UUID, ProgressionDirtySet> drainAll() {
        if (pending.isEmpty()) return Map.of();
        Map<UUID, ProgressionDirtySet> drained = Map.copyOf(pending);
        pending.clear();
        return drained;
    }

    public synchronized boolean clear(UUID playerId) {
        Objects.requireNonNull(playerId, "playerId");
        return pending.remove(playerId) != null;
    }

    public synchronized void clear() {
        pending.clear();
    }

    public synchronized int pendingPlayers() {
        return pending.size();
    }
}
