package dev.gustavopere.volcanoes.performance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class PerformanceProfilerTest {
    @Test
    void snapshotCapturesAllRequiredHotPathCountersAndResetIsExplicit() {
        PerformanceProfiler.reset();

        PerformanceProfiler.recordAtmosphereSample(3);
        PerformanceProfiler.recordVolcanoUpdates(2);
        PerformanceProfiler.recordPlateSample();
        PerformanceProfiler.recordPressureDepthQuery(false);
        PerformanceProfiler.recordPressureDepthQuery(true);
        PerformanceProfiler.recordBlockMutations(4);

        PerformanceProfiler.Snapshot snapshot = PerformanceProfiler.snapshot();
        assertEquals(1, snapshot.atmosphereSamples());
        assertEquals(3, snapshot.atmosphereSourceCandidates());
        assertEquals(2, snapshot.activeVolcanoUpdates());
        assertEquals(1, snapshot.plateSamples());
        assertEquals(2, snapshot.pressureDepthQueries());
        assertEquals(1, snapshot.pressureDepthCacheHits());
        assertEquals(4, snapshot.blockMutations());

        PerformanceProfiler.reset();
        assertEquals(PerformanceProfiler.Snapshot.ZERO, PerformanceProfiler.snapshot());
    }

    @Test
    void performanceBudgetsArePositiveAndPreserveConfiguredValues() {
        PerformanceConfig.Budgets budgets = PerformanceConfig.budgets(12, 24, 6);

        assertEquals(12, budgets.ashDepositionBlocksPerTick());
        assertEquals(24, budgets.lavaSpecializationBlocksPerTick());
        assertEquals(6, budgets.eruptionTerrainMutationsPerTick());

        assertThrows(IllegalArgumentException.class, () -> PerformanceConfig.budgets(0, 24, 6));
        assertThrows(IllegalArgumentException.class, () -> PerformanceConfig.budgets(12, 0, 6));
        assertThrows(IllegalArgumentException.class, () -> PerformanceConfig.budgets(12, 24, 0));
    }
}
