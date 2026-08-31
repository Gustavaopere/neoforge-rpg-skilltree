package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Reproducible structural benchmark for the normal active-site scheduler hot path. */
final class VolcanoPerformanceBenchmarkTest {
    private static final int REPRESENTATIVE_LOADED_CHUNKS = 256;
    private static final int SIMULATED_TICKS = 24_000;
    private static final int MAX_DUE_PER_TICK = 8;
    private static final int[] SITE_COUNTS = {0, 1, 10, 50};

    @Test
    void benchmarkActiveSitesWithRepresentativeLoadedChunks() throws IOException {
        Path output = Path.of("build", "performance", "volcano-sites.csv");
        Files.createDirectories(output.getParent());
        List<String> lines = new ArrayList<>();
        lines.add("active_sites,representative_loaded_chunks,simulated_ticks,due_updates,elapsed_nanos,nanos_per_tick");

        Set<Long> loadedChunks = representativeLoadedChunks();
        assertEquals(REPRESENTATIVE_LOADED_CHUNKS, loadedChunks.size());

        for (int siteCount : SITE_COUNTS) {
            BenchmarkResult result = runScenario(siteCount, loadedChunks);
            lines.add(String.format(
                    Locale.ROOT,
                    "%d,%d,%d,%d,%d,%.3f",
                    siteCount,
                    loadedChunks.size(),
                    SIMULATED_TICKS,
                    result.dueUpdates(),
                    result.elapsedNanos(),
                    result.elapsedNanos() / (double) SIMULATED_TICKS));

            assertEquals(siteCount, result.finalSchedulerSize(),
                    "scheduler cardinality must remain equal to active site count");
            if (siteCount == 0) {
                assertEquals(0L, result.dueUpdates());
            } else {
                assertTrue(result.dueUpdates() > 0L,
                        "active sites must receive bounded scheduled updates during the benchmark window");
            }
            assertTrue(result.maxDueInOneTick() <= MAX_DUE_PER_TICK,
                    "benchmark must preserve the canonical per-tick update cap");
        }

        Files.write(output, lines);
    }

    private static BenchmarkResult runScenario(int siteCount, Set<Long> loadedChunks) {
        VolcanoTickScheduler scheduler = new VolcanoTickScheduler();
        MagmaChamber chamber = activeChamber();
        List<UUID> ids = new ArrayList<>(siteCount);

        for (int index = 0; index < siteCount; index++) {
            int chunkX = index & 15;
            int chunkZ = index >>> 4;
            long chunkKey = chunkKey(chunkX, chunkZ);
            assertTrue(loadedChunks.contains(chunkKey), "every benchmark site must be in a representative loaded chunk");
            UUID id = new UUID(0L, 50_000L + index);
            ids.add(id);
            scheduler.schedule(id, VolcanoState.ACTIVE, chamber, 0L);
        }

        long dueUpdates = 0L;
        int maxDueInOneTick = 0;
        long started = System.nanoTime();
        for (long tick = 0L; tick <= SIMULATED_TICKS; tick++) {
            List<UUID> due = scheduler.pollDue(tick, MAX_DUE_PER_TICK);
            maxDueInOneTick = Math.max(maxDueInOneTick, due.size());
            dueUpdates += due.size();
            for (UUID id : due) {
                scheduler.schedule(id, VolcanoState.ACTIVE, chamber, tick);
            }
        }
        long elapsed = System.nanoTime() - started;
        return new BenchmarkResult(dueUpdates, maxDueInOneTick, scheduler.size(), elapsed);
    }

    private static Set<Long> representativeLoadedChunks() {
        Set<Long> chunks = new HashSet<>();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunks.add(chunkKey(x, z));
            }
        }
        return Set.copyOf(chunks);
    }

    private static long chunkKey(int x, int z) {
        return ((long) x << 32) ^ (z & 0xffffffffL);
    }

    private static MagmaChamber activeChamber() {
        return new MagmaChamber(
                new MagmaComposition(0.62, 0.55),
                5.0,
                165.0,
                0.07,
                1_200.0,
                0.25);
    }

    private record BenchmarkResult(
            long dueUpdates,
            int maxDueInOneTick,
            int finalSchedulerSize,
            long elapsedNanos
    ) {
    }
}
