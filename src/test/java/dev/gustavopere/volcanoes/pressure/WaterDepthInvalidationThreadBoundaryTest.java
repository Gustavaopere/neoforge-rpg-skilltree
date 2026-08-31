package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class WaterDepthInvalidationThreadBoundaryTest {
    @Test
    void chunkEventInvalidationIsDeferredUntilTheNextSamplingPass() {
        CountingProbe probe = new CountingProbe();
        BoundedConnectedWaterDepthLookup lookup = new BoundedConnectedWaterDepthLookup(32, 8, 200);

        lookup.sample(probe, "minecraft:overworld", 15, 10, 0, 0);
        int firstQueryCount = probe.cellQueries;
        assertEquals(1, lookup.cachedSampleCount());

        lookup.invalidateChunk("minecraft:overworld", 1, 0);

        assertEquals(
                1,
                lookup.cachedSampleCount(),
                "chunk-event callbacks must enqueue invalidation instead of mutating the tick-owned LRU directly");

        lookup.sample(probe, "minecraft:overworld", 15, 10, 0, 1);

        assertTrue(
                probe.cellQueries > firstQueryCount,
                "the next sampling pass must drain queued invalidations before reading the cache");
        assertEquals(1, lookup.cachedSampleCount());
    }

    @Test
    void tooManyDistinctPendingInvalidationsCollapseToBoundedFullClear() {
        CountingProbe probe = new CountingProbe();
        BoundedConnectedWaterDepthLookup lookup = new BoundedConnectedWaterDepthLookup(32, 8, 200, 2);

        lookup.sample(probe, "minecraft:overworld", 15, 10, 0, 0);
        int firstQueryCount = probe.cellQueries;

        lookup.invalidateChunk("minecraft:overworld", 100, 0);
        lookup.invalidateChunk("minecraft:overworld", 101, 0);
        lookup.invalidateChunk("minecraft:overworld", 102, 0);

        lookup.sample(probe, "minecraft:overworld", 15, 10, 0, 1);

        assertTrue(
                probe.cellQueries > firstQueryCount,
                "overflowing the bounded invalidation set must conservatively clear the LRU on the next sample");
        assertEquals(1, lookup.cachedSampleCount());
    }

    private static final class CountingProbe implements WaterVolumeProbe {
        private int cellQueries;

        @Override
        public boolean isColumnLoaded(String dimensionId, int blockX, int blockZ) {
            return true;
        }

        @Override
        public WaterCellKind cellAt(String dimensionId, int blockX, int blockY, int blockZ) {
            cellQueries++;
            if (blockX == 15 && blockY == 10 && blockZ == 0) {
                return WaterCellKind.WATER;
            }
            if (blockX == 16 && blockY == 10 && blockZ == 0) {
                return WaterCellKind.WATER;
            }
            if (blockX == 17 && blockY == 10 && blockZ == 0) {
                return WaterCellKind.OPEN_AIR;
            }
            return WaterCellKind.BLOCKED;
        }
    }
}
