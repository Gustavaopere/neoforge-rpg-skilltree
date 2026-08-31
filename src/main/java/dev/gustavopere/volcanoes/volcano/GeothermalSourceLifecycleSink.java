package dev.gustavopere.volcanoes.volcano;

import java.util.UUID;

/** Transient observer for persistent geothermal source creation and removal. */
public interface GeothermalSourceLifecycleSink {
    void upsert(GeothermalSource source);

    void remove(UUID persistenceId);
}
