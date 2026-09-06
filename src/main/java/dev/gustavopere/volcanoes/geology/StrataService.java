package dev.gustavopere.volcanoes.geology;

import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Read-only query facade for deterministic virtual strata.
 *
 * <p>Columns are cached by the sampler's low-frequency geology region. The cache is bounded and
 * access-ordered, while profile IDs are resolved against the current supplied registry snapshot on
 * every profile query. This lets datapack reloads change physical properties without regenerating
 * deterministic columns.</p>
 */
public final class StrataService {
    private static final int SURFACE_BIAS_DEPTH_BLOCKS = 24;
    private static final int MIN_RECOGNIZED_SURFACE_SAMPLES = 3;
    private static final int MAX_SURFACE_SAMPLES = 32;

    private final long worldSeed;
    private final DeterministicStrataSampler sampler;
    private final Supplier<RockProfileRegistry> registrySupplier;
    private final int maxCacheEntries;
    private final Map<RegionKey, GeologyColumn> cache = new LinkedHashMap<>(16, 0.75F, true);

    public StrataService(
            long worldSeed,
            DeterministicStrataSampler sampler,
            Supplier<RockProfileRegistry> registrySupplier,
            int maxCacheEntries
    ) {
        this.worldSeed = worldSeed;
        this.sampler = Objects.requireNonNull(sampler, "sampler");
        this.registrySupplier = Objects.requireNonNull(registrySupplier, "registrySupplier");
        if (maxCacheEntries <= 0) {
            throw new IllegalArgumentException("maxCacheEntries must be positive");
        }
        this.maxCacheEntries = maxCacheEntries;
    }

    /** Creates a world-facing service that always resolves profile IDs against RockProfileRuntime. */
    public static StrataService usingRuntime(
            long worldSeed,
            int minY,
            int maxYExclusive,
            int maxCacheEntries
    ) {
        return new StrataService(
                worldSeed,
                new DeterministicStrataSampler(minY, maxYExclusive),
                RockProfileRuntime::current,
                maxCacheEntries);
    }

    public GeologyColumn columnAt(int x, int z) {
        RegionKey key = RegionKey.fromBlockCoordinates(x, z);
        synchronized (cache) {
            GeologyColumn cached = cache.get(key);
            if (cached != null) {
                return cached;
            }

            GeologyColumn sampled = sampler.sample(worldSeed, x, z);
            cache.put(key, sampled);
            evictEldestIfNeeded();
            return sampled;
        }
    }

    public RockProfile profileAt(BlockPos pos) {
        Objects.requireNonNull(pos, "pos");
        String profileId = columnAt(pos.getX(), pos.getZ()).profileIdAt(pos.getY());
        RockProfileRegistry registry = currentRegistry();
        RockProfile profile = registry.profile(profileId);
        return profile != null ? profile : RockProfile.GENERIC_STONE;
    }

    /**
     * Applies a conservative surface-only bias from already-resolved nearby rock observations.
     *
     * <p>This method never scans chunks or reads block registries. A world adapter may resolve a
     * bounded set of actual nearby block states through {@link RockProfileRuntime} and supply those
     * profiles here. Only recognized non-generic profiles participate, at least three recognized
     * samples are required, and a strict majority is needed. Queries deeper than 24 blocks below
     * the supplied terrain surface always retain the deterministic virtual profile.</p>
     */
    public RockProfile profileAtWithSurfaceObservations(
            BlockPos pos,
            int surfaceY,
            Iterable<RockProfile> observedNearbyProfiles
    ) {
        Objects.requireNonNull(pos, "pos");
        Objects.requireNonNull(observedNearbyProfiles, "observedNearbyProfiles");

        RockProfile virtualProfile = profileAt(pos);
        long depth = (long) surfaceY - pos.getY();
        if (depth < 0L || depth > SURFACE_BIAS_DEPTH_BLOCKS) {
            return virtualProfile;
        }

        Map<String, Integer> counts = new HashMap<>();
        Map<String, RockProfile> profiles = new HashMap<>();
        int recognizedSamples = 0;
        int inspectedSamples = 0;

        for (RockProfile observed : observedNearbyProfiles) {
            if (inspectedSamples >= MAX_SURFACE_SAMPLES) {
                break;
            }
            inspectedSamples++;
            observed = Objects.requireNonNull(observed, "observed rock profile");
            if (observed.category() == RockCategory.GENERIC) {
                continue;
            }
            recognizedSamples++;
            profiles.putIfAbsent(observed.id(), observed);
            counts.merge(observed.id(), 1, Integer::sum);
        }

        if (recognizedSamples < MIN_RECOGNIZED_SURFACE_SAMPLES) {
            return virtualProfile;
        }

        String majorityId = null;
        int majorityCount = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > majorityCount) {
                majorityId = entry.getKey();
                majorityCount = entry.getValue();
            }
        }

        if (majorityId == null || majorityCount * 2 <= recognizedSamples) {
            return virtualProfile;
        }
        return profiles.get(majorityId);
    }

    int cachedRegionCount() {
        synchronized (cache) {
            return cache.size();
        }
    }

    private RockProfileRegistry currentRegistry() {
        return Objects.requireNonNull(registrySupplier.get(), "registrySupplier returned null");
    }

    private void evictEldestIfNeeded() {
        if (cache.size() <= maxCacheEntries) {
            return;
        }
        var iterator = cache.entrySet().iterator();
        iterator.next();
        iterator.remove();
    }

    private record RegionKey(long x, long z) {
        static RegionKey fromBlockCoordinates(int x, int z) {
            return new RegionKey(
                    Math.floorDiv(x, DeterministicStrataSampler.REGION_SIZE_BLOCKS),
                    Math.floorDiv(z, DeterministicStrataSampler.REGION_SIZE_BLOCKS));
        }
    }
}
