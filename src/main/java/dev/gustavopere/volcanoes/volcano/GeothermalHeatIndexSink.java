package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;
import java.util.UUID;

/** Projects the persistent geothermal-source lifecycle into the shared bounded heat index. */
public final class GeothermalHeatIndexSink implements GeothermalSourceLifecycleSink {
    private final VolcanicHeatSourceIndex index;

    public GeothermalHeatIndexSink(VolcanicHeatSourceIndex index) {
        this.index = Objects.requireNonNull(index, "index");
    }

    @Override
    public void upsert(GeothermalSource source) {
        index.upsert(Objects.requireNonNull(source, "source").toHeatSource());
    }

    @Override
    public void remove(UUID persistenceId) {
        index.remove(Objects.requireNonNull(persistenceId, "persistenceId"));
    }
}
