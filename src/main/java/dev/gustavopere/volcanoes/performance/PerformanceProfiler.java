package dev.gustavopere.volcanoes.performance;

import java.util.concurrent.atomic.LongAdder;

/** Low-overhead monotonic counters for Stage 07 hot-path profiling. */
public final class PerformanceProfiler {
    private static final LongAdder ATMOSPHERE_SAMPLES = new LongAdder();
    private static final LongAdder ATMOSPHERE_SOURCE_CANDIDATES = new LongAdder();
    private static final LongAdder ACTIVE_VOLCANO_UPDATES = new LongAdder();
    private static final LongAdder PLATE_SAMPLES = new LongAdder();
    private static final LongAdder PRESSURE_DEPTH_QUERIES = new LongAdder();
    private static final LongAdder PRESSURE_DEPTH_CACHE_HITS = new LongAdder();
    private static final LongAdder BLOCK_MUTATIONS = new LongAdder();

    private PerformanceProfiler() {
    }

    public static void recordAtmosphereSample(int sourceCandidates) {
        requireNonNegative("sourceCandidates", sourceCandidates);
        ATMOSPHERE_SAMPLES.increment();
        ATMOSPHERE_SOURCE_CANDIDATES.add(sourceCandidates);
    }

    public static void recordVolcanoUpdates(int updates) {
        requireNonNegative("updates", updates);
        ACTIVE_VOLCANO_UPDATES.add(updates);
    }

    public static void recordPlateSample() {
        PLATE_SAMPLES.increment();
    }

    public static void recordPressureDepthQuery(boolean cacheHit) {
        PRESSURE_DEPTH_QUERIES.increment();
        if (cacheHit) {
            PRESSURE_DEPTH_CACHE_HITS.increment();
        }
    }

    public static void recordBlockMutations(int mutations) {
        requireNonNegative("mutations", mutations);
        BLOCK_MUTATIONS.add(mutations);
    }

    public static Snapshot snapshot() {
        return new Snapshot(
                ATMOSPHERE_SAMPLES.sum(),
                ATMOSPHERE_SOURCE_CANDIDATES.sum(),
                ACTIVE_VOLCANO_UPDATES.sum(),
                PLATE_SAMPLES.sum(),
                PRESSURE_DEPTH_QUERIES.sum(),
                PRESSURE_DEPTH_CACHE_HITS.sum(),
                BLOCK_MUTATIONS.sum());
    }

    public static void reset() {
        ATMOSPHERE_SAMPLES.reset();
        ATMOSPHERE_SOURCE_CANDIDATES.reset();
        ACTIVE_VOLCANO_UPDATES.reset();
        PLATE_SAMPLES.reset();
        PRESSURE_DEPTH_QUERIES.reset();
        PRESSURE_DEPTH_CACHE_HITS.reset();
        BLOCK_MUTATIONS.reset();
    }

    private static void requireNonNegative(String name, int value) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " must be non-negative");
        }
    }

    public record Snapshot(
            long atmosphereSamples,
            long atmosphereSourceCandidates,
            long activeVolcanoUpdates,
            long plateSamples,
            long pressureDepthQueries,
            long pressureDepthCacheHits,
            long blockMutations
    ) {
        public static final Snapshot ZERO = new Snapshot(0L, 0L, 0L, 0L, 0L, 0L, 0L);
    }
}
