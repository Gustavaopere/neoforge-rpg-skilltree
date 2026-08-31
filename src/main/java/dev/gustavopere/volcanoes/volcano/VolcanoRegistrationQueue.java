package dev.gustavopere.volcanoes.volcano;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Small FIFO delay queue used to move chunk-load work onto a later server tick.
 * A logical value may have at most one pending registration at a time.
 */
public final class VolcanoRegistrationQueue<T> {
    private static final long DELAY_TICKS = 2L;

    private final int maximumPerDrain;
    private final Deque<Pending<T>> pending = new ArrayDeque<>();
    private final Set<T> pendingValues = new HashSet<>();

    public VolcanoRegistrationQueue(int maximumPerDrain) {
        if (maximumPerDrain <= 0) {
            throw new IllegalArgumentException("maximumPerDrain must be positive");
        }
        this.maximumPerDrain = maximumPerDrain;
    }

    public synchronized void enqueue(long currentTick, T value) {
        Objects.requireNonNull(value, "value");
        long readyTick = Math.addExact(currentTick, DELAY_TICKS);
        if (!pendingValues.add(value)) {
            return;
        }
        pending.addLast(new Pending<>(readyTick, value));
    }

    public synchronized List<T> drainReady(long currentTick) {
        List<T> drained = new ArrayList<>(maximumPerDrain);
        while (drained.size() < maximumPerDrain) {
            Pending<T> next = pending.peekFirst();
            if (next == null || next.readyTick() > currentTick) {
                break;
            }
            Pending<T> removed = pending.removeFirst();
            pendingValues.remove(removed.value());
            drained.add(removed.value());
        }
        return List.copyOf(drained);
    }

    public synchronized boolean isEmpty() {
        return pending.isEmpty();
    }

    private record Pending<T>(long readyTick, T value) {
    }
}
