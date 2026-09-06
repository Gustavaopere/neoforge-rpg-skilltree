package dev.gustavopere.volcanoes.pressure;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** Bounded, chunk-aware water-column surface lookup with short-lived exact-surface caching. */
public final class BoundedWaterDepthLookup {
    public static final int DEFAULT_MAX_CACHE_ENTRIES = 4096;

    private final int maxUpwardSteps;
    private final long ttlTicks;
    private final int maxCacheEntries;
    private final LinkedHashMap<ColumnKey, CacheEntry> cache = new LinkedHashMap<>(64, 0.75f, true);

    public BoundedWaterDepthLookup(int maxUpwardSteps, long ttlTicks) {
        this(maxUpwardSteps, ttlTicks, DEFAULT_MAX_CACHE_ENTRIES);
    }

    public BoundedWaterDepthLookup(int maxUpwardSteps, long ttlTicks, int maxCacheEntries) {
        if (maxUpwardSteps <= 0) {
            throw new IllegalArgumentException("maxUpwardSteps must be positive");
        }
        if (ttlTicks < 0) {
            throw new IllegalArgumentException("ttlTicks must be non-negative");
        }
        if (maxCacheEntries <= 0) {
            throw new IllegalArgumentException("maxCacheEntries must be positive");
        }
        this.maxUpwardSteps = maxUpwardSteps;
        this.ttlTicks = ttlTicks;
        this.maxCacheEntries = maxCacheEntries;
    }

    public WaterDepthSample sample(
            WaterColumnProbe probe,
            String dimensionId,
            int blockX,
            int blockY,
            int blockZ,
            long gameTick
    ) {
        Objects.requireNonNull(probe, "probe");
        Objects.requireNonNull(dimensionId, "dimensionId");

        if (!probe.isColumnLoaded(dimensionId, blockX, blockZ)) {
            return new WaterDepthSample(0.0, false);
        }
        if (!probe.isWater(dimensionId, blockX, blockY, blockZ)) {
            return new WaterDepthSample(0.0, true);
        }

        ColumnKey key = new ColumnKey(dimensionId, blockX, blockZ);
        CacheEntry cached = cache.get(key);
        if (cached != null) {
            if (gameTick <= cached.expiresAtTick && blockY < cached.firstDryY) {
                return new WaterDepthSample(cached.firstDryY - blockY, true);
            }
            cache.remove(key);
        }

        for (int step = 1; step <= maxUpwardSteps; step++) {
            int candidateY = blockY + step;
            if (!probe.isWater(dimensionId, blockX, candidateY, blockZ)) {
                put(key, new CacheEntry(candidateY, saturatingAdd(gameTick, ttlTicks)));
                return new WaterDepthSample(step, true);
            }
        }

        return new WaterDepthSample(maxUpwardSteps, false);
    }

    public void invalidateChunk(String dimensionId, int chunkX, int chunkZ) {
        Objects.requireNonNull(dimensionId, "dimensionId");
        Iterator<Map.Entry<ColumnKey, CacheEntry>> iterator = cache.entrySet().iterator();
        while (iterator.hasNext()) {
            ColumnKey key = iterator.next().getKey();
            if (key.dimensionId.equals(dimensionId)
                    && Math.floorDiv(key.blockX, 16) == chunkX
                    && Math.floorDiv(key.blockZ, 16) == chunkZ) {
                iterator.remove();
            }
        }
    }

    public void clear() {
        cache.clear();
    }

    int cachedColumnCount() {
        return cache.size();
    }

    private void put(ColumnKey key, CacheEntry entry) {
        cache.put(key, entry);
        while (cache.size() > maxCacheEntries) {
            Iterator<ColumnKey> iterator = cache.keySet().iterator();
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }
    }

    private static long saturatingAdd(long value, long increment) {
        if (increment > 0 && value > Long.MAX_VALUE - increment) {
            return Long.MAX_VALUE;
        }
        return value + increment;
    }

    private record ColumnKey(String dimensionId, int blockX, int blockZ) {
    }

    private record CacheEntry(int firstDryY, long expiresAtTick) {
    }
}
