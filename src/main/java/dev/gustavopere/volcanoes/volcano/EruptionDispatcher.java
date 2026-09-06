package dev.gustavopere.volcanoes.volcano;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/** Ordered, fail-isolated fanout for eruption consumers with aggregate work-budget preservation. */
public final class EruptionDispatcher {
    private final CopyOnWriteArrayList<EruptionSink> sinks = new CopyOnWriteArrayList<>();

    public boolean register(EruptionSink sink) {
        return sinks.addIfAbsent(Objects.requireNonNull(sink, "sink"));
    }

    public boolean unregister(EruptionSink sink) {
        return sinks.remove(Objects.requireNonNull(sink, "sink"));
    }

    public int size() {
        return sinks.size();
    }

    public DispatchResult dispatch(EruptionSignal signal, EruptionScheduler.WorkGrant workGrant) {
        Objects.requireNonNull(signal, "signal");
        Objects.requireNonNull(workGrant, "workGrant");
        List<EruptionSink> snapshot = List.copyOf(sinks);
        if (snapshot.isEmpty()) {
            return new DispatchResult(0, 0);
        }

        int delivered = 0;
        int failed = 0;
        for (int index = 0; index < snapshot.size(); index++) {
            EruptionSink sink = snapshot.get(index);
            EruptionScheduler.WorkGrant allocation = allocation(workGrant, index, snapshot.size());
            try {
                sink.onEruption(signal, allocation);
                delivered++;
            } catch (RuntimeException ignored) {
                failed++;
            }
        }
        return new DispatchResult(delivered, failed);
    }

    private static EruptionScheduler.WorkGrant allocation(
            EruptionScheduler.WorkGrant grant,
            int index,
            int consumerCount
    ) {
        return new EruptionScheduler.WorkGrant(
                share(grant.immediateBlocks(), index, consumerCount),
                share(grant.immediateEntities(), index, consumerCount),
                share(grant.queuedBlocks(), index, consumerCount),
                share(grant.queuedEntities(), index, consumerCount),
                share(grant.droppedBlocks(), index, consumerCount),
                share(grant.droppedEntities(), index, consumerCount));
    }

    private static int share(int total, int index, int consumerCount) {
        int base = total / consumerCount;
        return base + (index < total % consumerCount ? 1 : 0);
    }

    public record DispatchResult(int delivered, int failed) {
        public DispatchResult {
            if (delivered < 0 || failed < 0) {
                throw new IllegalArgumentException("dispatch counts must be non-negative");
            }
        }
    }
}
