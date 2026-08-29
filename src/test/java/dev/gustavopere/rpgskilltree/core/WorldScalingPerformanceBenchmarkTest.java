package dev.gustavopere.rpgskilltree.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Reproducible acceptance benchmark for world-scaling hot paths.
 *
 * <p>Elapsed times are reported for diagnostics only. CI gates on deterministic algorithmic
 * budgets instead of machine-dependent millisecond thresholds.</p>
 */
public final class WorldScalingPerformanceBenchmarkTest {
    private static final int SPAWN_ITERATIONS = 2_000;
    private static final int SPATIAL_QUERY_ITERATIONS = 5_000;
    private static final int PERSISTED_RESUME_ITERATIONS = 10_000;

    public static void main(String[] args) {
        BenchmarkResult result = benchmarkHotPaths();
        if (result.maxSpatialVisitedCells() > result.spatialCellBudget()) {
            throw new AssertionError("spatial query exceeded its policy cell budget");
        }
        if (result.maxSpatialScannedPlayers() >= result.indexedPlayers()) {
            throw new AssertionError("localized spatial query degraded to a global player scan");
        }
        if (result.persistedInitializerCalls() != 0) {
            throw new AssertionError("persisted entity state triggered scaling recomputation");
        }
        if (result.checksum() <= 0L) {
            throw new AssertionError("benchmark did not exercise resolved world-scaling results");
        }

        System.out.printf(
            "WorldScalingPerformanceBenchmarkTest: PASS spawn=%dns spatial=%dns persisted-resume=%dns "
                + "indexed=%d maxScanned=%d maxVisitedCells=%d cellBudget=%d%n",
            result.spawnElapsedNanos(),
            result.spatialElapsedNanos(),
            result.persistedResumeElapsedNanos(),
            result.indexedPlayers(),
            result.maxSpatialScannedPlayers(),
            result.maxSpatialVisitedCells(),
            result.spatialCellBudget()
        );
    }

    private static BenchmarkResult benchmarkHotPaths() {
        WorldEntityScalingRequest spawnRequest = spawnRequest();
        long checksum = 0L;
        long spawnStart = System.nanoTime();
        for (int index = 0; index < SPAWN_ITERATIONS; index++) {
            WorldEntityScalingResult resolved = WorldEntityScalingService.resolve(spawnRequest);
            checksum = Math.addExact(checksum, resolved.entityLevel().finalLevel());
        }
        long spawnElapsed = System.nanoTime() - spawnStart;

        RelevantPlayerSearchPolicy searchPolicy = new RelevantPlayerSearchPolicy(32, 96, 48, 8, 10L);
        RelevantPlayerSpatialIndex spatialIndex = RelevantPlayerSpatialIndex.build(spatialPresences(), 32);
        int maxScanned = 0;
        long maxVisited = 0L;
        long spatialStart = System.nanoTime();
        for (int index = 0; index < SPATIAL_QUERY_ITERATIONS; index++) {
            RelevantPlayerSpatialQuery query = spatialIndex.query(0, 64, 0, searchPolicy);
            maxScanned = Math.max(maxScanned, query.scannedPlayers());
            maxVisited = Math.max(maxVisited, query.visitedCells());
            checksum = Math.addExact(checksum, query.candidates().size());
        }
        long spatialElapsed = System.nanoTime() - spatialStart;

        EntityScalingState persisted = persistedState();
        AtomicInteger initializerCalls = new AtomicInteger();
        long resumeStart = System.nanoTime();
        for (int index = 0; index < PERSISTED_RESUME_ITERATIONS; index++) {
            EntityScalingState resumed = EntityScalingBootstrap.resumeOrInitialize(
                Optional.of(persisted),
                () -> {
                    initializerCalls.incrementAndGet();
                    return persisted;
                }
            );
            if (resumed != persisted) {
                throw new AssertionError("persisted resume must return the existing state instance");
            }
            checksum = Math.addExact(checksum, resumed.entityLevel());
        }
        long resumeElapsed = System.nanoTime() - resumeStart;

        return new BenchmarkResult(
            spawnElapsed,
            spatialElapsed,
            resumeElapsed,
            spatialIndex.indexedPlayers(),
            maxScanned,
            maxVisited,
            searchPolicy.worstCaseVisitedCells(),
            initializerCalls.get(),
            checksum
        );
    }

    private static WorldEntityScalingRequest spawnRequest() {
        CanonicalStatKey health = CanonicalStatKey.of("rpgskilltree:max_health");
        return new WorldEntityScalingRequest(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            NativeAreaLevelPlan.of(12L, List.of()),
            List.of(
                new RelevantPlayerCandidate("local", 24L, 16L, true, false),
                new RelevantPlayerCandidate("unrelated", 4_000L, 25L, false, false)
            ),
            RelevantPlayerCandidate::engaged,
            relevant -> OptionalLong.of(relevant.stream()
                .mapToLong(RelevantPlayerCandidate::level)
                .max()
                .orElseThrow()),
            EntityArchetype.HOSTILE,
            EntityLevelAdjustment.NONE,
            CanonicalStatSnapshot.of(Map.of(health, new BigDecimal("20"))),
            Map.of(EntityArchetype.HOSTILE, context -> Map.of(
                health, stat -> stat.providerValue()
            ))
        );
    }

    private static List<RelevantPlayerPresence> spatialPresences() {
        ArrayList<RelevantPlayerPresence> presences = new ArrayList<>(4_096);
        for (int z = 0; z < 64; z++) {
            for (int x = 0; x < 64; x++) {
                int id = z * 64 + x;
                presences.add(new RelevantPlayerPresence(
                    "player-%04d".formatted(id),
                    id,
                    x * 256,
                    64,
                    z * 256
                ));
            }
        }
        return List.copyOf(presences);
    }

    private static EntityScalingState persistedState() {
        return new EntityScalingState(
            TerritoryKey.of("minecraft:overworld", 0L, 0L),
            new EntityLevelResolution(
                EntityArchetype.HOSTILE,
                12L,
                OptionalLong.of(24L),
                24L,
                24L,
                24L
            ),
            0L,
            Optional.empty(),
            0x1020304050607080L
        );
    }

    private record BenchmarkResult(
        long spawnElapsedNanos,
        long spatialElapsedNanos,
        long persistedResumeElapsedNanos,
        int indexedPlayers,
        int maxSpatialScannedPlayers,
        long maxSpatialVisitedCells,
        long spatialCellBudget,
        int persistedInitializerCalls,
        long checksum
    ) {
        private BenchmarkResult {
            if (spawnElapsedNanos < 0L || spatialElapsedNanos < 0L || persistedResumeElapsedNanos < 0L) {
                throw new IllegalArgumentException("elapsed benchmark times must be non-negative");
            }
            if (indexedPlayers <= 0 || maxSpatialScannedPlayers < 0 || maxSpatialVisitedCells < 0L) {
                throw new IllegalArgumentException("invalid benchmark counters");
            }
            if (spatialCellBudget <= 0L || persistedInitializerCalls < 0) {
                throw new IllegalArgumentException("invalid benchmark budgets");
            }
        }
    }
}
