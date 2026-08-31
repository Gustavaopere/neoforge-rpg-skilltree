package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.GeothermalSource;
import dev.gustavopere.volcanoes.volcano.GeothermalSourceLifecycleSink;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Level-scoped lifecycle bridge from canonical geothermal source metadata into Atmosphere.
 * Pending upserts are bounded, latest-wins and retry in rotating order under capacity pressure.
 */
public final class GeothermalAtmosphereBridge implements GeothermalSourceLifecycleSink {
    private final String dimensionId;
    private final AtmosphericSourceSink sink;
    private final GeothermalAtmosphereProjectionPolicy policy;
    private final int maxPending;
    private final Map<UUID, GeothermalSource> pending = new HashMap<>();
    private final ArrayDeque<UUID> retryOrder = new ArrayDeque<>();

    public GeothermalAtmosphereBridge(
            String dimensionId,
            AtmosphericSourceSink sink,
            GeothermalAtmosphereProjectionPolicy policy,
            int maxPending
    ) {
        this.dimensionId = Objects.requireNonNull(dimensionId, "dimensionId");
        if (dimensionId.isBlank()) {
            throw new IllegalArgumentException("dimensionId must not be blank");
        }
        this.sink = Objects.requireNonNull(sink, "sink");
        this.policy = Objects.requireNonNull(policy, "policy");
        if (maxPending <= 0) {
            throw new IllegalArgumentException("maxPending must be positive");
        }
        this.maxPending = maxPending;
    }

    @Override
    public void upsert(GeothermalSource source) {
        Objects.requireNonNull(source, "source");
        if (GeothermalAtmosphereProjection.project(dimensionId, source, policy).isEmpty()) {
            remove(source.persistenceId());
            return;
        }

        synchronized (this) {
            UUID id = source.persistenceId();
            if (pending.containsKey(id)) {
                pending.put(id, source);
                return;
            }
            if (pending.size() >= maxPending) {
                return;
            }
            pending.put(id, source);
            retryOrder.addLast(id);
        }
    }

    @Override
    public void remove(UUID persistenceId) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        synchronized (this) {
            pending.remove(persistenceId);
            retryOrder.removeIf(persistenceId::equals);
        }
        try {
            sink.remove(persistenceId);
        } catch (RuntimeException | LinkageError ignored) {
            // Atmosphere is a derived optional observer and never geothermal authority.
        }
    }

    /** Attempts at most {@code budget} pending source projections. */
    public int flush(int budget) {
        if (budget <= 0) {
            return 0;
        }
        int attempts = 0;
        while (attempts < budget) {
            UUID id;
            GeothermalSource source;
            synchronized (this) {
                id = retryOrder.pollFirst();
                if (id == null) {
                    break;
                }
                source = pending.get(id);
                if (source == null) {
                    continue;
                }
            }

            attempts++;
            AtmosphericSource projected = GeothermalAtmosphereProjection.project(dimensionId, source, policy)
                    .orElse(null);
            if (projected == null) {
                remove(id);
                continue;
            }

            AtmosphericSourceAdmission admission;
            try {
                admission = sink.tryUpsert(projected);
            } catch (RuntimeException | LinkageError failure) {
                admission = AtmosphericSourceAdmission.REJECTED_CAPACITY;
            }

            synchronized (this) {
                GeothermalSource latest = pending.get(id);
                if (latest == null) {
                    continue;
                }
                if (admission == AtmosphericSourceAdmission.ACCEPTED && latest.equals(source)) {
                    pending.remove(id);
                } else {
                    retryOrder.addLast(id);
                }
            }
        }
        return attempts;
    }

    public synchronized int pendingCount() {
        return pending.size();
    }

    public int maxPending() {
        return maxPending;
    }
}
