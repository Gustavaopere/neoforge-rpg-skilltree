package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;
import java.util.UUID;

/** Lifecycle boundary for the authoritative Stage 03 ash plume descriptor. */
public interface AshEmissionLifecycleSink {
    void upsert(AshPlumeEmission emission);

    void remove(UUID sourceId);

    static AshEmissionLifecycleSink none() {
        return new AshEmissionLifecycleSink() {
            @Override
            public void upsert(AshPlumeEmission emission) {
                Objects.requireNonNull(emission, "emission");
            }

            @Override
            public void remove(UUID sourceId) {
                Objects.requireNonNull(sourceId, "sourceId");
            }
        };
    }
}
