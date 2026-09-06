package dev.gustavopere.volcanoes.environment;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

public final class AtmosphereField implements AtmosphereService, AtmosphericSourceSink {
    private final AtmosphereBaselineProvider baseline;
    private final AtmosphericSourceIndex index;
    private final AtmosphereDynamics dynamics;
    private final AtmosphereTransportProvider transportProvider;
    private final AtmosphericSourceLifecycleSink lifecycleSink;
    private volatile AtmosphereExternalContributionProvider externalContributionProvider;
    private final Queue<UUID> updateQueue = new ArrayDeque<>();
    private final Set<UUID> queued = new HashSet<>();

    public AtmosphereField(
            AtmosphereBaselineProvider baseline,
            AtmosphericSourceIndex index,
            AtmosphereDynamics dynamics
    ) {
        this(
                baseline,
                index,
                dynamics,
                AtmosphereTransportProvider.stillAir(),
                AtmosphericSourceLifecycleSink.none(),
                AtmosphereExternalContributionProvider.none());
    }

    public AtmosphereField(
            AtmosphereBaselineProvider baseline,
            AtmosphericSourceIndex index,
            AtmosphereDynamics dynamics,
            AtmosphereTransportProvider transportProvider,
            AtmosphericSourceLifecycleSink lifecycleSink
    ) {
        this(
                baseline,
                index,
                dynamics,
                transportProvider,
                lifecycleSink,
                AtmosphereExternalContributionProvider.none());
    }

    public AtmosphereField(
            AtmosphereBaselineProvider baseline,
            AtmosphericSourceIndex index,
            AtmosphereDynamics dynamics,
            AtmosphereTransportProvider transportProvider,
            AtmosphericSourceLifecycleSink lifecycleSink,
            AtmosphereExternalContributionProvider externalContributionProvider
    ) {
        this.baseline = Objects.requireNonNull(baseline, "baseline");
        this.index = Objects.requireNonNull(index, "index");
        this.dynamics = Objects.requireNonNull(dynamics, "dynamics");
        this.transportProvider = Objects.requireNonNull(transportProvider, "transportProvider");
        this.lifecycleSink = Objects.requireNonNull(lifecycleSink, "lifecycleSink");
        this.externalContributionProvider = Objects.requireNonNull(
                externalContributionProvider,
                "externalContributionProvider");
    }

    @Override
    public AtmosphereState sample(String dimensionId, long worldSeed, double x, double y, double z) {
        return sample(dimensionId, x, y, z);
    }

    public AtmosphereState sample(String dimensionId, double x, double y, double z) {
        AtmosphereState baselineState = baseline.sample(dimensionId, y);
        AtmosphereContribution local = index.combinedContributionAt(dimensionId, x, y, z);
        AtmosphereContribution external;
        try {
            external = Objects.requireNonNull(
                    externalContributionProvider.sample(dimensionId, x, y, z),
                    "externalContributionProvider returned null");
        } catch (RuntimeException | LinkageError optionalIntegrationFailure) {
            // Optional authority/readback failures must not make the core atmosphere unavailable.
            // Falling back to the internal vector is fail-closed: no external pollution is invented.
            external = AtmosphereContribution.none();
        }
        return local.combine(external).applyTo(baselineState);
    }

    /**
     * Replaces the one externally authoritative atmosphere readback provider for this field.
     * Providers are replaced rather than composed so optional integrations cannot accidentally
     * double-count the same external pollution authority.
     */
    public void replaceExternalContributionProvider(AtmosphereExternalContributionProvider provider) {
        externalContributionProvider = Objects.requireNonNull(provider, "provider");
    }

    public void register(AtmosphericSource source) {
        AtmosphericSource value = Objects.requireNonNull(source, "source");
        index.register(value);
        try {
            lifecycleSink.upsert(value);
        } catch (RuntimeException lifecycleFailure) {
            index.remove(value.id());
            throw lifecycleFailure;
        }
        schedule(value);
    }

    /**
     * Creates or atomically replaces one stable atmospheric source identity using the legacy
     * fail-closed contract. Cross-stage adapters that must retain/retry authoritative upstream
     * state should use {@link #tryUpsert(AtmosphericSource)} instead.
     */
    @Override
    public void upsert(AtmosphericSource source) {
        AtmosphericSourceAdmission admission = tryUpsert(source);
        if (admission == AtmosphericSourceAdmission.REJECTED_CAPACITY) {
            throw new IllegalStateException("Atmospheric source admission rejected by active capacity");
        }
    }

    /**
     * Attempts to create or atomically replace one stable atmospheric source identity.
     *
     * <p>Only ordinary active-index saturation is returned as explicit backpressure. Invalid source
     * data, invalid spatial footprints and lifecycle-sink failures remain exceptions so callers do
     * not mistake corruption or integration faults for capacity pressure.</p>
     */
    @Override
    public AtmosphericSourceAdmission tryUpsert(AtmosphericSource source) {
        AtmosphericSource value = Objects.requireNonNull(source, "source");
        Optional<AtmosphericSource> previous = index.source(value.id());
        if (previous.isEmpty()) {
            if (!index.tryRegister(value)) {
                return AtmosphericSourceAdmission.REJECTED_CAPACITY;
            }
            try {
                lifecycleSink.upsert(value);
            } catch (RuntimeException lifecycleFailure) {
                index.remove(value.id());
                throw lifecycleFailure;
            }
            schedule(value);
            return AtmosphericSourceAdmission.ACCEPTED;
        }

        index.replace(value);
        try {
            lifecycleSink.upsert(value);
        } catch (RuntimeException lifecycleFailure) {
            try {
                index.replace(previous.get());
            } catch (RuntimeException rollbackFailure) {
                lifecycleFailure.addSuppressed(rollbackFailure);
            }
            throw lifecycleFailure;
        }
        schedule(value);
        return AtmosphericSourceAdmission.ACCEPTED;
    }

    /**
     * Restores an already-persisted source without producing a redundant persistence write.
     * Sources that no longer satisfy current runtime spatial/capacity policy are purged from the
     * persistence sink instead of making a world fail to load after a policy upgrade.
     */
    void restore(AtmosphericSource source) {
        AtmosphericSource value = Objects.requireNonNull(source, "source");
        try {
            index.register(value);
            schedule(value);
        } catch (IllegalArgumentException | IllegalStateException invalidPersistedSource) {
            lifecycleSink.remove(value.id());
        }
    }

    @Override
    public boolean remove(UUID id) {
        UUID value = Objects.requireNonNull(id, "id");
        boolean existed = index.source(value).isPresent();

        // Lifecycle acknowledgement comes first even for an already-absent runtime source. This
        // keeps removal idempotent for upstream producers and purges any stale persistence state.
        // If the sink fails, runtime/index/queue state remains untouched so the caller can retry.
        lifecycleSink.remove(value);
        dequeue(value);
        if (!existed) {
            return false;
        }
        return index.remove(value);
    }

    public Optional<AtmosphericSource> source(UUID id) {
        return index.source(id);
    }

    public int sourceCount() {
        return index.size();
    }

    public int tick(int maxUpdates) {
        if (maxUpdates < 0) {
            throw new IllegalArgumentException("maxUpdates must be non-negative");
        }
        int eligible = Math.min(maxUpdates, updateQueue.size());
        int processed = 0;
        for (int i = 0; i < eligible; i++) {
            UUID id = updateQueue.poll();
            if (id == null) {
                break;
            }
            queued.remove(id);
            Optional<AtmosphericSource> current = index.source(id);
            if (current.isEmpty()) {
                continue;
            }
            AtmosphericSource previous = current.get();
            if (previous.evolution() == AtmosphericSourceEvolution.EXTERNAL) {
                continue;
            }

            Optional<AtmosphericSource> evolved;
            try {
                AtmosphereTransport transport = Objects.requireNonNull(
                        transportProvider.sample(previous),
                        "transportProvider returned null");
                evolved = previous.evolve(dynamics, transport);
                if (evolved.isPresent()) {
                    index.replace(evolved.get());
                } else {
                    index.remove(id);
                }
            } catch (RuntimeException | LinkageError invalidTransportOrSpatialUpdate) {
                // A future weather/wind adapter must not abort the whole bounded batch. Because
                // AtmosphericSourceIndex.replace validates before mutation, the last valid source
                // remains authoritative and can be retried on a later cadence.
                enqueue(id);
                processed++;
                continue;
            }

            try {
                if (evolved.isPresent()) {
                    lifecycleSink.upsert(evolved.get());
                    schedule(evolved.get());
                } else {
                    lifecycleSink.remove(id);
                }
            } catch (RuntimeException lifecycleFailure) {
                // The runtime/index change is only authoritative once its lifecycle sink accepts
                // the same state. Restore the last accepted source and retry it on a later cadence
                // rather than leaving an unpersisted evolution (or removal) stranded in memory.
                try {
                    index.replace(previous);
                } catch (RuntimeException rollbackFailure) {
                    lifecycleFailure.addSuppressed(rollbackFailure);
                    throw lifecycleFailure;
                }
                schedule(previous);
            }
            processed++;
        }
        return processed;
    }

    private void schedule(AtmosphericSource source) {
        if (source.evolution() == AtmosphericSourceEvolution.DYNAMIC) {
            enqueue(source.id());
        } else {
            dequeue(source.id());
        }
    }

    private void enqueue(UUID id) {
        if (queued.add(id)) {
            updateQueue.offer(id);
        }
    }

    private void dequeue(UUID id) {
        if (queued.remove(id)) {
            updateQueue.remove(id);
        }
    }
}
