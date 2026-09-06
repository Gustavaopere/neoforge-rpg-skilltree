package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;

/**
 * Eruption consumer that publishes one stable authoritative ash plume source per volcano.
 *
 * <p>The shared eruption work grant is intentionally not spent here: atmospheric source publication
 * is metadata, while bounded deposition, projectile and terrain/entity work are separate consumers.</p>
 */
public final class AshEmissionRuntime implements EruptionSink {
    private final AshEmissionLifecycleSink sink;

    public AshEmissionRuntime(AshEmissionLifecycleSink sink) {
        this.sink = Objects.requireNonNull(sink, "sink");
    }

    @Override
    public void onEruption(EruptionSignal signal, EruptionScheduler.WorkGrant workGrant) {
        Objects.requireNonNull(signal, "signal");
        Objects.requireNonNull(workGrant, "workGrant");

        AshPlumeEmission emission = AshPlumeEmission.from(signal);
        if (signal.phase() == EruptionPhase.DORMANT) {
            sink.remove(emission.sourceId());
            return;
        }
        if (emission.active()) {
            sink.upsert(emission);
        }
    }
}
