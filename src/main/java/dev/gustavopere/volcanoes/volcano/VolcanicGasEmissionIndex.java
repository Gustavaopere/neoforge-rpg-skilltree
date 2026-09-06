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

/** Thread-safe authoritative index of the current Stage03 volcanic gas descriptor per volcano. */
public final class VolcanicGasEmissionIndex implements VolcanicGasEmissionLifecycleSink {
    private final Map<UUID, VolcanicGasEmission> bySourceId = new HashMap<>();
    private final Map<UUID, UUID> sourceByVolcano = new HashMap<>();
    private final Set<VolcanicGasEmissionLifecycleSink> lifecycleSinks = new LinkedHashSet<>();

    @Override
    public void upsert(VolcanicGasEmission emission) {
        Objects.requireNonNull(emission, "emission");
        List<VolcanicGasEmissionLifecycleSink> sinks;
        synchronized (this) {
            UUID previousSource = sourceByVolcano.put(emission.volcanoId(), emission.sourceId());
            if (previousSource != null && !previousSource.equals(emission.sourceId())) {
                bySourceId.remove(previousSource);
            }
            bySourceId.put(emission.sourceId(), emission);
            sinks = List.copyOf(lifecycleSinks);
        }
        for (VolcanicGasEmissionLifecycleSink sink : sinks) {
            try {
                sink.upsert(emission);
            } catch (RuntimeException | LinkageError ignored) {
                // Optional observers cannot veto authoritative Stage03 state.
            }
        }
    }

    @Override
    public void remove(UUID sourceId) {
        Objects.requireNonNull(sourceId, "sourceId");
        List<VolcanicGasEmissionLifecycleSink> sinks;
        synchronized (this) {
            VolcanicGasEmission removed = bySourceId.remove(sourceId);
            if (removed == null) return;
            sourceByVolcano.remove(removed.volcanoId(), sourceId);
            sinks = List.copyOf(lifecycleSinks);
        }
        for (VolcanicGasEmissionLifecycleSink sink : sinks) {
            try {
                sink.remove(sourceId);
            } catch (RuntimeException | LinkageError ignored) {
                // Optional observers cannot veto authoritative Stage03 state.
            }
        }
    }

    public boolean registerLifecycleSink(VolcanicGasEmissionLifecycleSink sink) {
        Objects.requireNonNull(sink, "sink");
        List<VolcanicGasEmission> replay;
        synchronized (this) {
            if (!lifecycleSinks.add(sink)) return false;
            replay = bySourceId.values().stream()
                    .sorted(Comparator.comparing(emission -> emission.sourceId().toString()))
                    .toList();
        }
        try {
            for (VolcanicGasEmission emission : replay) sink.upsert(emission);
            return true;
        } catch (RuntimeException | LinkageError failure) {
            synchronized (this) {
                lifecycleSinks.remove(sink);
            }
            return false;
        }
    }

    public synchronized boolean unregisterLifecycleSink(VolcanicGasEmissionLifecycleSink sink) {
        return lifecycleSinks.remove(Objects.requireNonNull(sink, "sink"));
    }

    public synchronized Optional<VolcanicGasEmission> bySourceId(UUID sourceId) {
        return Optional.ofNullable(bySourceId.get(Objects.requireNonNull(sourceId, "sourceId")));
    }

    public synchronized Optional<VolcanicGasEmission> forVolcano(UUID volcanoId) {
        UUID sourceId = sourceByVolcano.get(Objects.requireNonNull(volcanoId, "volcanoId"));
        return sourceId == null ? Optional.empty() : Optional.ofNullable(bySourceId.get(sourceId));
    }

    public synchronized int size() {
        return bySourceId.size();
    }
}
