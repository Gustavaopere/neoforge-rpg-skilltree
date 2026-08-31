package dev.gustavopere.volcanoes.volcano;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/** Thread-safe index of the current authoritative ash plume descriptor for each volcano. */
public final class AshEmissionIndex implements AshEmissionLifecycleSink {
    private final Map<UUID, AshPlumeEmission> bySourceId = new HashMap<>();
    private final Map<UUID, UUID> sourceByVolcano = new HashMap<>();
    private final Set<AshEmissionLifecycleSink> lifecycleSinks = new LinkedHashSet<>();

    @Override
    public void upsert(AshPlumeEmission emission) {
        Objects.requireNonNull(emission, "emission");
        List<AshEmissionLifecycleSink> sinks;
        synchronized (this) {
            UUID previousSource = sourceByVolcano.put(emission.volcanoId(), emission.sourceId());
            if (previousSource != null && !previousSource.equals(emission.sourceId())) {
                bySourceId.remove(previousSource);
            }
            bySourceId.put(emission.sourceId(), emission);
            sinks = List.copyOf(lifecycleSinks);
        }
        for (AshEmissionLifecycleSink sink : sinks) {
            try {
                sink.upsert(emission);
            } catch (RuntimeException | LinkageError ignored) {
                // Optional observers must never veto authoritative Stage-03 state.
            }
        }
    }

    @Override
    public void remove(UUID sourceId) {
        Objects.requireNonNull(sourceId, "sourceId");
        List<AshEmissionLifecycleSink> sinks;
        synchronized (this) {
            AshPlumeEmission removed = bySourceId.remove(sourceId);
            if (removed == null) {
                return;
            }
            sourceByVolcano.remove(removed.volcanoId(), sourceId);
            sinks = List.copyOf(lifecycleSinks);
        }
        for (AshEmissionLifecycleSink sink : sinks) {
            try {
                sink.remove(sourceId);
            } catch (RuntimeException | LinkageError ignored) {
                // Optional observers must never veto authoritative Stage-03 state.
            }
        }
    }

    public boolean registerLifecycleSink(AshEmissionLifecycleSink sink) {
        Objects.requireNonNull(sink, "sink");
        List<AshPlumeEmission> replay;
        synchronized (this) {
            if (!lifecycleSinks.add(sink)) {
                return false;
            }
            replay = bySourceId.values().stream()
                    .sorted(Comparator.comparing(emission -> emission.sourceId().toString()))
                    .toList();
        }
        try {
            for (AshPlumeEmission emission : replay) {
                sink.upsert(emission);
            }
            return true;
        } catch (RuntimeException | LinkageError failure) {
            synchronized (this) {
                lifecycleSinks.remove(sink);
            }
            return false;
        }
    }

    public synchronized boolean unregisterLifecycleSink(AshEmissionLifecycleSink sink) {
        Objects.requireNonNull(sink, "sink");
        return lifecycleSinks.remove(sink);
    }

    public synchronized Optional<AshPlumeEmission> forVolcano(UUID volcanoId) {
        Objects.requireNonNull(volcanoId, "volcanoId");
        UUID sourceId = sourceByVolcano.get(volcanoId);
        return sourceId == null ? Optional.empty() : Optional.ofNullable(bySourceId.get(sourceId));
    }

    public synchronized Optional<AshPlumeEmission> bySourceId(UUID sourceId) {
        Objects.requireNonNull(sourceId, "sourceId");
        return Optional.ofNullable(bySourceId.get(sourceId));
    }

    public synchronized int size() {
        return bySourceId.size();
    }
}
