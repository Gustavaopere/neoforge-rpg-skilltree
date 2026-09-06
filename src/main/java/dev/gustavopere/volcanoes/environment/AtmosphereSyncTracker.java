package dev.gustavopere.volcanoes.environment;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class AtmosphereSyncTracker {
    private final Map<UUID, AtmosphereSnapshot> lastSent = new ConcurrentHashMap<>();

    /**
     * Checks whether the snapshot differs from the last successfully delivered snapshot.
     * This method is intentionally side-effect free so a failed transport remains retryable.
     */
    public boolean needsSend(UUID playerId, AtmosphereSnapshot snapshot) {
        UUID id = Objects.requireNonNull(playerId, "playerId");
        AtmosphereSnapshot value = Objects.requireNonNull(snapshot, "snapshot");
        return !value.equals(lastSent.get(id));
    }

    /** Records a snapshot only after the transport has accepted it. */
    public void markSent(UUID playerId, AtmosphereSnapshot snapshot) {
        lastSent.put(
                Objects.requireNonNull(playerId, "playerId"),
                Objects.requireNonNull(snapshot, "snapshot"));
    }

    public void forget(UUID playerId) {
        lastSent.remove(Objects.requireNonNull(playerId, "playerId"));
    }
}
