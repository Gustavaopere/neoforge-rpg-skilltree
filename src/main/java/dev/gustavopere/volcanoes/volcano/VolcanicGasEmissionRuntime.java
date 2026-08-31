package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;

/** Zero-work metadata publisher invoked by the existing canonical volcanic hazard EruptionSink. */
public final class VolcanicGasEmissionRuntime {
    private final VolcanicGasEmissionLifecycleSink sink;

    public VolcanicGasEmissionRuntime(VolcanicGasEmissionLifecycleSink sink) {
        this.sink = Objects.requireNonNull(sink, "sink");
    }

    /**
     * Publishes lifecycle-owned metadata from the server-agnostic eruption consumer.
     * The source remains authoritative until the same eruption lifecycle explicitly updates or
     * removes it; restart replay reconstructs active metadata from persisted eruption state.
     */
    public void onEruption(EruptionSignal signal) {
        Objects.requireNonNull(signal, "signal");
        if (signal.phase() == EruptionPhase.DORMANT) {
            sink.remove(VolcanicGasEmissionProjector.sourceId(signal.volcanoId()));
            return;
        }
        VolcanicGasEmissionProjector.projectLifecycle(signal).ifPresent(sink::upsert);
    }

    /** Tick-aware projection retained for deterministic focused tests and diagnostic producers. */
    public void onEruption(EruptionSignal signal, long gameTick) {
        Objects.requireNonNull(signal, "signal");
        if (signal.phase() == EruptionPhase.DORMANT) {
            sink.remove(VolcanicGasEmissionProjector.sourceId(signal.volcanoId()));
            return;
        }
        VolcanicGasEmissionProjector.project(signal, gameTick).ifPresent(sink::upsert);
    }
}
