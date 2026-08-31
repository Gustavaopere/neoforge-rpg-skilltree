package dev.gustavopere.volcanoes.pressure;

import dev.gustavopere.volcanoes.performance.PerformanceProfiler;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/** Bounded connected-water search for a free surface with dependency-aware LRU caching. */
public final class BoundedConnectedWaterDepthLookup {
    public static final int DEFAULT_MAX_CACHE_ENTRIES = 4096;

    private static final int[][] DIRECTIONS = {
            {0, 1, 0},
            {1, 0, 0}, {-1, 0, 0},
            {0, 0, 1}, {0, 0, -1},
            {0, -1, 0}
    };

    private final int maxVisitedWaterCells;
    private final int maxGraphDistance;
    private final long ttlTicks;
    private final int maxCacheEntries;
    private final LinkedHashMap<SampleKey, CacheEntry> cache = new LinkedHashMap<>(64, 0.75f, true);
    private final Set<ChunkKey> pendingChunkInvalidations = ConcurrentHashMap.newKeySet();
    private final AtomicBoolean fullInvalidationPending = new AtomicBoolean();

    public BoundedConnectedWaterDepthLookup(int maxVisitedWaterCells, int maxGraphDistance, long ttlTicks) {
        this(maxVisitedWaterCells, maxGraphDistance, ttlTicks, DEFAULT_MAX_CACHE_ENTRIES);
    }

    public BoundedConnectedWaterDepthLookup(
            int maxVisitedWaterCells,
            int maxGraphDistance,
            long ttlTicks,
            int maxCacheEntries
    ) {
        if (maxVisitedWaterCells <= 0) {
            throw new IllegalArgumentException("maxVisitedWaterCells must be positive");
        }
        if (maxGraphDistance <= 0) {
            throw new IllegalArgumentException("maxGraphDistance must be positive");
        }
        if (ttlTicks < 0) {
            throw new IllegalArgumentException("ttlTicks must be non-negative");
        }
        if (maxCacheEntries <= 0) {
            throw new IllegalArgumentException("maxCacheEntries must be positive");
        }
        this.maxVisitedWaterCells = maxVisitedWaterCells;
        this.maxGraphDistance = maxGraphDistance;
        this.ttlTicks = ttlTicks;
        this.maxCacheEntries = maxCacheEntries;
    }

    public WaterDepthSample sample(
            WaterVolumeProbe probe,
            String dimensionId,
            int blockX,
            int blockY,
            int blockZ,
            long gameTick
    ) {
        Objects.requireNonNull(probe, "probe");
        Objects.requireNonNull(dimensionId, "dimensionId");
        drainPendingInvalidations();

        SampleKey key = new SampleKey(dimensionId, blockX, blockY, blockZ);
        CacheEntry cached = cache.get(key);
        if (cached != null) {
            if (gameTick <= cached.expiresAtTick) {
                PerformanceProfiler.recordPressureDepthQuery(true);
                return cached.sample;
            }
            cache.remove(key);
        }
        PerformanceProfiler.recordPressureDepthQuery(false);

        Set<ChunkKey> dependencies = new HashSet<>();
        dependencies.add(chunkKey(dimensionId, blockX, blockZ));
        if (!probe.isColumnLoaded(dimensionId, blockX, blockZ)) {
            return cache(key, new WaterDepthSample(0.0, false), dependencies, gameTick);
        }

        Cell origin = new Cell(blockX, blockY, blockZ);
        WaterCellKind originKind = requireKind(probe.cellAt(dimensionId, blockX, blockY, blockZ));
        if (originKind != WaterCellKind.WATER) {
            return cache(key, new WaterDepthSample(0.0, true), dependencies, gameTick);
        }

        Map<Cell, WaterCellKind> classified = new HashMap<>();
        classified.put(origin, WaterCellKind.WATER);
        double provenVerticalDepthMeters = 0.0;
        for (int offset = 1; offset <= maxGraphDistance; offset++) {
            Cell above = new Cell(blockX, blockY + offset, blockZ);
            WaterCellKind kind = requireKind(probe.cellAt(dimensionId, above.x, above.y, above.z));
            classified.put(above, kind);
            if (kind == WaterCellKind.OPEN_AIR) {
                return cache(key, new WaterDepthSample(offset, true), dependencies, gameTick);
            }
            if (kind == WaterCellKind.BLOCKED) {
                break;
            }
            provenVerticalDepthMeters = offset;
        }

        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.addLast(new Node(origin, 0));
        Set<Cell> enqueuedWater = new HashSet<>();
        enqueuedWater.add(origin);

        int expandedWaterCells = 0;
        while (!queue.isEmpty() && expandedWaterCells < maxVisitedWaterCells) {
            Node node = queue.removeFirst();
            expandedWaterCells++;
            for (int[] direction : DIRECTIONS) {
                Cell neighbor = node.cell.offset(direction[0], direction[1], direction[2]);
                int distance = node.distance + 1;
                if (distance > maxGraphDistance) {
                    continue;
                }
                WaterCellKind kind = classified.get(neighbor);
                if (kind == null) {
                    dependencies.add(chunkKey(dimensionId, neighbor.x, neighbor.z));
                    if (!probe.isColumnLoaded(dimensionId, neighbor.x, neighbor.z)) {
                        continue;
                    }
                    kind = requireKind(probe.cellAt(dimensionId, neighbor.x, neighbor.y, neighbor.z));
                    classified.put(neighbor, kind);
                }
                if (kind == WaterCellKind.OPEN_AIR) {
                    if (neighbor.y < blockY) {
                        continue;
                    }
                    return cache(key, new WaterDepthSample(neighbor.y - blockY, true), dependencies, gameTick);
                }
                if (kind == WaterCellKind.WATER) {
                    provenVerticalDepthMeters = Math.max(
                            provenVerticalDepthMeters,
                            Math.max(0.0, neighbor.y - blockY));
                    if (enqueuedWater.add(neighbor)) {
                        queue.addLast(new Node(neighbor, distance));
                    }
                }
            }
        }
        return cache(key, new WaterDepthSample(provenVerticalDepthMeters, false), dependencies, gameTick);
    }

    public void invalidateChunk(String dimensionId, int chunkX, int chunkZ) {
        Objects.requireNonNull(dimensionId, "dimensionId");
        if (fullInvalidationPending.get()) {
            return;
        }
        pendingChunkInvalidations.add(new ChunkKey(dimensionId, chunkX, chunkZ));
        if (pendingChunkInvalidations.size() > maxCacheEntries) {
            fullInvalidationPending.set(true);
            pendingChunkInvalidations.clear();
        }
    }

    public void clear() {
        cache.clear();
        pendingChunkInvalidations.clear();
        fullInvalidationPending.set(false);
    }

    int cachedSampleCount() {
        return cache.size();
    }

    private void drainPendingInvalidations() {
        if (fullInvalidationPending.getAndSet(false)) {
            cache.clear();
        }
        for (ChunkKey changed : pendingChunkInvalidations) {
            if (!pendingChunkInvalidations.remove(changed)) {
                continue;
            }
            removeEntriesDependingOn(changed);
        }
        if (fullInvalidationPending.getAndSet(false)) {
            cache.clear();
        }
    }

    private void removeEntriesDependingOn(ChunkKey changed) {
        Iterator<Map.Entry<SampleKey, CacheEntry>> iterator = cache.entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue().dependencies.contains(changed)) {
                iterator.remove();
            }
        }
    }

    private WaterDepthSample cache(
            SampleKey key,
            WaterDepthSample sample,
            Set<ChunkKey> dependencies,
            long gameTick
    ) {
        cache.put(key, new CacheEntry(sample, Set.copyOf(dependencies), saturatingAdd(gameTick, ttlTicks)));
        while (cache.size() > maxCacheEntries) {
            Iterator<SampleKey> iterator = cache.keySet().iterator();
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }
        return sample;
    }

    private static WaterCellKind requireKind(WaterCellKind kind) {
        return Objects.requireNonNull(kind, "WaterVolumeProbe.cellAt must not return null");
    }

    private static ChunkKey chunkKey(String dimensionId, int blockX, int blockZ) {
        return new ChunkKey(dimensionId, Math.floorDiv(blockX, 16), Math.floorDiv(blockZ, 16));
    }

    private static long saturatingAdd(long value, long increment) {
        if (increment > 0 && value > Long.MAX_VALUE - increment) {
            return Long.MAX_VALUE;
        }
        return value + increment;
    }

    private record Cell(int x, int y, int z) {
        private Cell offset(int dx, int dy, int dz) {
            return new Cell(x + dx, y + dy, z + dz);
        }
    }

    private record Node(Cell cell, int distance) {
    }

    private record SampleKey(String dimensionId, int blockX, int blockY, int blockZ) {
    }

    private record ChunkKey(String dimensionId, int chunkX, int chunkZ) {
    }

    private record CacheEntry(WaterDepthSample sample, Set<ChunkKey> dependencies, long expiresAtTick) {
    }
}
