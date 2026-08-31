package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;

/**
 * Single eruption consumer for Stage 03 ash/bomb/pyroclastic work and zero-work gas metadata.
 *
 * <p>Ash/gas metadata publication is zero-token bookkeeping. The exact partitioned world-work grant
 * is then queued unchanged, preventing metadata and concrete hazards from competing as separate
 * dispatcher consumers.</p>
 */
public final class VolcanicHazardSink implements EruptionSink {
    private final AshEmissionRuntime ashEmission;
    private final VolcanicGasEmissionRuntime gasEmission;
    private final VolcanicHazardQueue queue;

    public VolcanicHazardSink(AshEmissionLifecycleSink ashSink, VolcanicHazardQueue queue) {
        this(ashSink, VolcanicGasAuthority.lifecycleSink(), queue);
    }

    public VolcanicHazardSink(
            AshEmissionLifecycleSink ashSink,
            VolcanicGasEmissionLifecycleSink gasSink,
            VolcanicHazardQueue queue
    ) {
        this.ashEmission = new AshEmissionRuntime(Objects.requireNonNull(ashSink, "ashSink"));
        this.gasEmission = new VolcanicGasEmissionRuntime(Objects.requireNonNull(gasSink, "gasSink"));
        this.queue = Objects.requireNonNull(queue, "queue");
    }

    @Override
    public void onEruption(EruptionSignal signal, EruptionScheduler.WorkGrant workGrant) {
        Objects.requireNonNull(signal, "signal");
        Objects.requireNonNull(workGrant, "workGrant");
        ashEmission.onEruption(signal, workGrant);
        gasEmission.onEruption(signal);
        queue.offer(signal, workGrant);
    }
}
