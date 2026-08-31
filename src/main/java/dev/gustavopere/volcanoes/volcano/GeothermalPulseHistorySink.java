package dev.gustavopere.volcanoes.volcano;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** Keeps transient native-geyser dedupe state aligned with the persistent source lifecycle. */
final class GeothermalPulseHistorySink implements GeothermalSourceLifecycleSink {
    private final Map<UUID, Long> lastPulseTicks;

    GeothermalPulseHistorySink(Map<UUID, Long> lastPulseTicks) {
        this.lastPulseTicks = Objects.requireNonNull(lastPulseTicks, "lastPulseTicks");
    }

    @Override
    public void upsert(GeothermalSource source) {
        Objects.requireNonNull(source, "source");
    }

    @Override
    public void remove(UUID persistenceId) {
        lastPulseTicks.remove(Objects.requireNonNull(persistenceId, "persistenceId"));
    }
}
