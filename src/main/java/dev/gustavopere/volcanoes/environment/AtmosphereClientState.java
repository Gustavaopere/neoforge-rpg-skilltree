package dev.gustavopere.volcanoes.environment;

import java.util.Objects;

public final class AtmosphereClientState {
    private static final AtmosphereSnapshot DEFAULT_SNAPSHOT =
            AtmosphereSnapshot.from(AtmosphereState.standardOverworld());

    private AtmosphereSnapshot snapshot = DEFAULT_SNAPSHOT;

    public void accept(AtmosphereSyncPayload payload) {
        snapshot = Objects.requireNonNull(payload, "payload").snapshot();
    }

    public void reset() {
        snapshot = DEFAULT_SNAPSHOT;
    }

    public AtmosphereSnapshot snapshot() {
        return snapshot;
    }
}
