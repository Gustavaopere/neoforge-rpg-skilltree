package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class BoundedWaterDepthLookupTest {
    @Test
    void resolvesSurfaceDepthAndReusesCachedColumn() {
        CountingProbe probe = new CountingProbe(80, true);
        BoundedWaterDepthLookup lookup = new BoundedWaterDepthLookup(64, 20);

        WaterDepthSample first = lookup.sample(probe, "minecraft:overworld", 3, 70, 9, 100);
        int afterFirst = probe.waterQueries;
        WaterDepthSample cached = lookup.sample(probe, "minecraft:overworld", 3, 72, 9, 105);

        assertTrue(first.surfaceResolved());
        assertEquals(10.0, first.depthMeters(), 1.0e-9);
        assertEquals(8.0, cached.depthMeters(), 1.0e-9);
        assertTrue(probe.waterQueries - afterFirst <= 1, "cache hit must not repeat a vertical scan");
    }

    @Test
    void searchIsBoundedAndConservativeWhenSurfaceCannotBeProven() {
        CountingProbe probe = new CountingProbe(Integer.MAX_VALUE, true);
        BoundedWaterDepthLookup lookup = new BoundedWaterDepthLookup(12, 20);

        WaterDepthSample sample = lookup.sample(probe, "minecraft:overworld", 0, 20, 0, 0);

        assertFalse(sample.surfaceResolved());
        assertEquals(12.0, sample.depthMeters(), 1.0e-9);
        assertTrue(probe.waterQueries <= 13, "bounded lookup must not scan toward world top");
    }

    @Test
    void ttlAndChunkInvalidationForceARescan() {
        CountingProbe probe = new CountingProbe(40, true);
        BoundedWaterDepthLookup lookup = new BoundedWaterDepthLookup(64, 10);

        lookup.sample(probe, "minecraft:overworld", 17, 30, 1, 0);
        int initialQueries = probe.waterQueries;
        lookup.sample(probe, "minecraft:overworld", 17, 31, 1, 20);
        assertTrue(probe.waterQueries > initialQueries);

        int afterExpiry = probe.waterQueries;
        lookup.invalidateChunk("minecraft:overworld", 1, 0);
        lookup.sample(probe, "minecraft:overworld", 17, 32, 1, 21);
        assertTrue(probe.waterQueries > afterExpiry);
    }

    @Test
    void unloadedOrDryColumnsDoNotPretendToHaveResolvedWaterDepth() {
        BoundedWaterDepthLookup lookup = new BoundedWaterDepthLookup(64, 20);
        WaterDepthSample unloaded = lookup.sample(new CountingProbe(80, false), "minecraft:overworld", 0, 50, 0, 0);
        WaterDepthSample dry = lookup.sample(new CountingProbe(40, true), "minecraft:overworld", 0, 50, 0, 0);

        assertFalse(unloaded.surfaceResolved());
        assertEquals(0.0, unloaded.depthMeters(), 1.0e-9);
        assertTrue(dry.surfaceResolved());
        assertEquals(0.0, dry.depthMeters(), 1.0e-9);
    }

    private static final class CountingProbe implements WaterColumnProbe {
        private final int firstDryY;
        private final boolean loaded;
        private int waterQueries;

        private CountingProbe(int firstDryY, boolean loaded) {
            this.firstDryY = firstDryY;
            this.loaded = loaded;
        }

        @Override
        public boolean isColumnLoaded(String dimensionId, int blockX, int blockZ) {
            return loaded;
        }

        @Override
        public boolean isWater(String dimensionId, int blockX, int blockY, int blockZ) {
            waterQueries++;
            return blockY < firstDryY;
        }
    }
}
