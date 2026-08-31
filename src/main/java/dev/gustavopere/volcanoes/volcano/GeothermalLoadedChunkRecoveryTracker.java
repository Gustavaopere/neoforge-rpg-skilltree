package dev.gustavopere.volcanoes.volcano;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Tracks loaded receipt-bearing chunks without imposing a second fixed recovery capacity. */
public final class GeothermalLoadedChunkRecoveryTracker {
    private final Set<Long> tracked = new LinkedHashSet<>();

    public synchronized void track(long chunkPos) {
        tracked.add(chunkPos);
    }

    public synchronized boolean untrack(long chunkPos) {
        return tracked.remove(chunkPos);
    }

    public synchronized int size() {
        return tracked.size();
    }

    /** Returns a bounded rotating batch while retaining unresolved chunks for future turns. */
    public synchronized List<Long> nextBatch(int maxChunks) {
        if (maxChunks <= 0 || tracked.isEmpty()) {
            return List.of();
        }
        int limit = Math.min(maxChunks, tracked.size());
        List<Long> batch = new ArrayList<>(limit);
        var iterator = tracked.iterator();
        while (iterator.hasNext() && batch.size() < limit) {
            long chunk = iterator.next();
            batch.add(chunk);
            iterator.remove();
        }
        tracked.addAll(batch);
        return List.copyOf(batch);
    }
}
