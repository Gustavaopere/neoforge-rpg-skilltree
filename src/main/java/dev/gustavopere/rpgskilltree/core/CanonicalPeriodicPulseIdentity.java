package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Canonical identity shared by every target callback from one persistent periodic pulse. */
public final class CanonicalPeriodicPulseIdentity {
    private CanonicalPeriodicPulseIdentity() {}

    public static CanonicalActionIdentity forPulse(
        String actorId,
        String providerId,
        String persistentOriginId,
        long serverTick
    ) {
        Objects.requireNonNull(actorId);
        Objects.requireNonNull(providerId);
        Objects.requireNonNull(persistentOriginId);
        if (actorId.isBlank()) throw new IllegalArgumentException("actorId");
        if (providerId.isBlank()) throw new IllegalArgumentException("providerId");
        if (persistentOriginId.isBlank()) throw new IllegalArgumentException("persistentOriginId");
        if (serverTick < 0L) throw new IllegalArgumentException("serverTick");
        return CanonicalActionIdentity.root(
            actorId,
            "periodic/" + providerId + "/" + persistentOriginId + "/" + serverTick,
            "canonical:periodic_pulse"
        );
    }
}
