package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;
import java.util.UUID;

/** Metadata-only observer boundary for authoritative Stage03 volcanic gas emissions. */
public interface VolcanicGasEmissionLifecycleSink {
    void upsert(VolcanicGasEmission emission);

    void remove(UUID sourceId);

    static VolcanicGasEmissionLifecycleSink none() {
        return new VolcanicGasEmissionLifecycleSink() {
            @Override
            public void upsert(VolcanicGasEmission emission) {
                Objects.requireNonNull(emission, "emission");
            }

            @Override
            public void remove(UUID sourceId) {
                Objects.requireNonNull(sourceId, "sourceId");
            }
        };
    }
}
