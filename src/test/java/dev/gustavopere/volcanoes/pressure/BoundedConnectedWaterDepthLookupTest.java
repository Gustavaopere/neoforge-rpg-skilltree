package dev.gustavopere.volcanoes.pressure;

import dev.gustavopere.volcanoes.performance.PerformanceProfiler;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

final class BoundedConnectedWaterDepthLookupTest {
    @Test
    void findsAConnectedFreeSurfaceAroundASolidCeiling() {
        GridProbe probe = new GridProbe();
        probe.water(0, 10, 0)
                .water(1, 10, 0)
                .water(2, 10, 0)
                .water(2, 11, 0)
                .water(2, 12, 0)
                .water(2, 13, 0)
                .water(2, 14, 0)
                .air(2, 15, 0);

        BoundedConnectedWaterDepthLookup lookup = new BoundedConnectedWaterDepthLookup(64, 12, 20);
        WaterDepthSample sample = lookup.sample(probe, "minecraft:overworld", 0, 10, 0, 100);

        assertTrue(sample.surfaceResolved());
        assertEquals(5.0, sample.depthMeters(), 1.0e-9);
    }

    @Test
    void profilerDistinguishesPressureDepthMissesFromCacheHits() {
        GridProbe probe = new GridProbe().water(0, 10, 0).air(0, 11, 0);
        BoundedConnectedWaterDepthLookup lookup = new BoundedConnectedWaterDepthLookup(32, 8, 20);
        PerformanceProfiler.reset();

        lookup.sample(probe, "minecraft:overworld", 0, 10, 0, 100);
        lookup.sample(probe, "minecraft:overworld", 0, 10, 0, 105);

        PerformanceProfiler.Snapshot snapshot = PerformanceProfiler.snapshot();
        assertEquals(2, snapshot.pressureDepthQueries());
        assertEquals(1, snapshot.pressureDepthCacheHits());
    }

    @Test
    void deepOpenColumnUsesVerticalFastPathBeforeGraphBudget() {
        WaterVolumeProbe openColumn = new WaterVolumeProbe() {
            @Override
            public boolean isColumnLoaded(String dimensionId, int blockX, int blockZ) {
                return true;
            }

            @Override
            public WaterCellKind cellAt(String dimensionId, int blockX, int blockY, int blockZ) {
                if (blockX != 0 || blockZ != 0) {
                    return WaterCellKind.WATER;
                }
                return blockY <= 40 ? WaterCellKind.WATER : WaterCellKind.OPEN_AIR;
            }
        };
        CountingProbe counting = new CountingProbe(openColumn);
        BoundedConnectedWaterDepthLookup lookup = new BoundedConnectedWaterDepthLookup(16, 64, 20);

        WaterDepthSample sample = lookup.sample(counting, "minecraft:overworld", 0, 0, 0, 0);

        assertTrue(sample.surfaceResolved(), "ordinary deep water must not exhaust a lateral BFS budget");
        assertEquals(41.0, sample.depthMeters(), 1.0e-9);
        assertTrue(counting.cellQueries <= 42, "open columns should be essentially O(depth), not O(volume)");
    }

    @Test
    void solidCeilingDoesNotInventDepthFromSearchRadius() {
        GridProbe probe = new GridProbe().water(0, 10, 0);
        BoundedConnectedWaterDepthLookup lookup = new BoundedConnectedWaterDepthLookup(32, 8, 20);

        WaterDepthSample sample = lookup.sample(probe, "minecraft:overworld", 0, 10, 0, 0);

        assertFalse(sample.surfaceResolved());
        assertEquals(0.0, sample.depthMeters(), 1.0e-9,
                "an unresolved solid ceiling proves no vertical water head above the sample");
    }

    @Test
    void unresolvedHorizontalConnectivityDoesNotBecomeHydrostaticDepth() {
        WaterVolumeProbe horizontalTunnel = new WaterVolumeProbe() {
            @Override
            public boolean isColumnLoaded(String dimensionId, int blockX, int blockZ) {
                return true;
            }

            @Override
            public WaterCellKind cellAt(String dimensionId, int blockX, int blockY, int blockZ) {
                return blockY == 10 ? WaterCellKind.WATER : WaterCellKind.BLOCKED;
            }
        };
        BoundedConnectedWaterDepthLookup lookup = new BoundedConnectedWaterDepthLookup(20, 20, 20);

        WaterDepthSample sample = lookup.sample(horizontalTunnel, "minecraft:overworld", 0, 10, 0, 0);

        assertFalse(sample.surfaceResolved());
        assertEquals(0.0, sample.depthMeters(), 1.0e-9,
                "horizontal graph distance is not a vertical pressure head");
    }

    @Test
    void freeAirBelowSampleDoesNotMasqueradeAsHydrostaticSurface() {
        GridProbe probe = new GridProbe()
                .water(0, 10, 0)
                .water(0, 9, 0)
                .air(0, 8, 0);
        BoundedConnectedWaterDepthLookup lookup = new BoundedConnectedWaterDepthLookup(32, 8, 20);

        WaterDepthSample sample = lookup.sample(probe, "minecraft:overworld", 0, 10, 0, 0);

        assertFalse(sample.surfaceResolved(),
                "a free-air outlet below the entity is not an upper hydrostatic free surface");
        assertEquals(0.0, sample.depthMeters(), 1.0e-9);
    }

    @Test
    void nodeBudgetBoundsPathologicalConnectedWaterSearches() {
        WaterVolumeProbe endlessWater = new WaterVolumeProbe() {
            @Override
            public boolean isColumnLoaded(String dimensionId, int blockX, int blockZ) {
                return true;
            }

            @Override
            public WaterCellKind cellAt(String dimensionId, int blockX, int blockY, int blockZ) {
                return WaterCellKind.WATER;
            }
        };
        CountingProbe counting = new CountingProbe(endlessWater);
        BoundedConnectedWaterDepthLookup lookup = new BoundedConnectedWaterDepthLookup(20, 20, 20);

        WaterDepthSample sample = lookup.sample(counting, "minecraft:overworld", 0, 0, 0, 0);

        assertFalse(sample.surfaceResolved());
        assertEquals(20.0, sample.depthMeters(), 1.0e-9,
                "a continuous vertical water column proves at least this much vertical head");
        assertTrue(counting.cellQueries <= 121, "each expanded water node may classify at most six new neighbors");
    }

    @Test
    void cacheTracksRemoteChunkDependenciesForInvalidation() {
        GridProbe probe = new GridProbe()
                .water(15, 10, 0)
                .water(16, 10, 0)
                .air(17, 10, 0);
        CountingProbe counting = new CountingProbe(probe);
        BoundedConnectedWaterDepthLookup lookup = new BoundedConnectedWaterDepthLookup(32, 8, 20);

        WaterDepthSample first = lookup.sample(counting, "minecraft:overworld", 15, 10, 0, 0);
        int afterFirst = counting.cellQueries;
        WaterDepthSample cached = lookup.sample(counting, "minecraft:overworld", 15, 10, 0, 5);
        assertEquals(afterFirst, counting.cellQueries);
        assertEquals(first, cached);

        lookup.invalidateChunk("minecraft:overworld", 1, 0);
        lookup.sample(counting, "minecraft:overworld", 15, 10, 0, 6);
        assertTrue(counting.cellQueries > afterFirst, "changing a traversed remote chunk must invalidate the cached result");
    }

    @Test
    void newlyLoadedDependencyCanInvalidateUnresolvedResultBeforeTtlExpires() {
        MutableLoadedProbe probe = new MutableLoadedProbe()
                .loadedChunk(0, 0)
                .water(15, 10, 0)
                .water(16, 10, 0)
                .air(17, 10, 0);
        BoundedConnectedWaterDepthLookup lookup = new BoundedConnectedWaterDepthLookup(32, 8, 200);

        WaterDepthSample unresolved = lookup.sample(probe, "minecraft:overworld", 15, 10, 0, 0);
        assertFalse(unresolved.surfaceResolved(), "missing neighboring chunk must fail closed");

        probe.loadedChunk(1, 0);
        WaterDepthSample stillCached = lookup.sample(probe, "minecraft:overworld", 15, 10, 0, 1);
        assertFalse(stillCached.surfaceResolved(), "without invalidation the conservative result is still cached");

        lookup.invalidateChunk("minecraft:overworld", 1, 0);
        WaterDepthSample refreshed = lookup.sample(probe, "minecraft:overworld", 15, 10, 0, 1);
        assertTrue(refreshed.surfaceResolved(), "chunk-load invalidation must immediately re-evaluate connectivity");
        assertEquals(0.0, refreshed.depthMeters(), 1.0e-9,
                "horizontal path to a same-height free surface does not create hydrostatic head");
    }

    private static class GridProbe implements WaterVolumeProbe {
        protected final Map<Cell, WaterCellKind> cells = new HashMap<>();

        GridProbe water(int x, int y, int z) {
            cells.put(new Cell(x, y, z), WaterCellKind.WATER);
            return this;
        }

        GridProbe air(int x, int y, int z) {
            cells.put(new Cell(x, y, z), WaterCellKind.OPEN_AIR);
            return this;
        }

        @Override
        public boolean isColumnLoaded(String dimensionId, int blockX, int blockZ) {
            return true;
        }

        @Override
        public WaterCellKind cellAt(String dimensionId, int blockX, int blockY, int blockZ) {
            return cells.getOrDefault(new Cell(blockX, blockY, blockZ), WaterCellKind.BLOCKED);
        }
    }

    private static final class MutableLoadedProbe extends GridProbe {
        private final Set<Chunk> loadedChunks = new HashSet<>();

        MutableLoadedProbe loadedChunk(int chunkX, int chunkZ) {
            loadedChunks.add(new Chunk(chunkX, chunkZ));
            return this;
        }

        @Override
        MutableLoadedProbe water(int x, int y, int z) {
            super.water(x, y, z);
            return this;
        }

        @Override
        MutableLoadedProbe air(int x, int y, int z) {
            super.air(x, y, z);
            return this;
        }

        @Override
        public boolean isColumnLoaded(String dimensionId, int blockX, int blockZ) {
            return loadedChunks.contains(new Chunk(Math.floorDiv(blockX, 16), Math.floorDiv(blockZ, 16)));
        }
    }

    private static final class CountingProbe implements WaterVolumeProbe {
        private final WaterVolumeProbe delegate;
        private int cellQueries;

        private CountingProbe(WaterVolumeProbe delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean isColumnLoaded(String dimensionId, int blockX, int blockZ) {
            return delegate.isColumnLoaded(dimensionId, blockX, blockZ);
        }

        @Override
        public WaterCellKind cellAt(String dimensionId, int blockX, int blockY, int blockZ) {
            cellQueries++;
            return delegate.cellAt(dimensionId, blockX, blockY, blockZ);
        }
    }

    private record Cell(int x, int y, int z) {
    }

    private record Chunk(int x, int z) {
    }
}
