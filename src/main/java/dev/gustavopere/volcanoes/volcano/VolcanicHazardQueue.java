package dev.gustavopere.volcanoes.volcano;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Small bounded bridge from server-agnostic eruption dispatch into level-aware world effects.
 *
 * <p>Each entry already carries its partitioned {@link EruptionScheduler.WorkGrant}; the queue does
 * not create additional work tokens. Overflow is dropped rather than allowing an unbounded world
 * effect backlog.</p>
 */
public final class VolcanicHazardQueue implements EruptionSink {
    private final int capacity;
    private final ArrayDeque<HazardWork> queue;

    public VolcanicHazardQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.capacity = capacity;
        this.queue = new ArrayDeque<>(capacity);
    }

    @Override
    public synchronized void onEruption(EruptionSignal signal, EruptionScheduler.WorkGrant workGrant) {
        offer(signal, workGrant);
    }

    public synchronized boolean offer(EruptionSignal signal, EruptionScheduler.WorkGrant workGrant) {
        Objects.requireNonNull(signal, "signal");
        Objects.requireNonNull(workGrant, "workGrant");
        if (queue.size() >= capacity) {
            return false;
        }
        queue.addLast(new HazardWork(signal, workGrant));
        return true;
    }

    public synchronized List<HazardWork> drain(int maxEntries) {
        if (maxEntries < 0) {
            throw new IllegalArgumentException("maxEntries must be non-negative");
        }
        int count = Math.min(maxEntries, queue.size());
        List<HazardWork> drained = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            drained.add(queue.removeFirst());
        }
        return List.copyOf(drained);
    }

    public synchronized int size() {
        return queue.size();
    }

    public record HazardWork(EruptionSignal signal, EruptionScheduler.WorkGrant workGrant) {
        public HazardWork {
            signal = Objects.requireNonNull(signal, "signal");
            workGrant = Objects.requireNonNull(workGrant, "workGrant");
        }
    }
}
