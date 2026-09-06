package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.performance.PerformanceProfiler;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

public final class AtmosphericSourceIndex {
    private static final int DEFAULT_MAX_CELLS_PER_SOURCE = 4_096;
    static final int DEFAULT_MAX_SOURCES = 16_384;
    static final int DEFAULT_MAX_EXTERNAL_SOURCES = 16_384;

    private final int cellSizeBlocks;
    private final int maxCellsPerSource;
    private final int maxDynamicSources;
    private final int maxExternalSources;
    private final int maxTotalSources;
    private final Map<String, Map<Long, TreeMap<UUID, AtmosphericSource>>> buckets = new HashMap<>();
    private final Map<UUID, AtmosphericSource> sources = new HashMap<>();
    private final Map<UUID, Set<CellRef>> memberships = new HashMap<>();
    private int dynamicSourceCount;
    private int externalSourceCount;

    public AtmosphericSourceIndex(int cellSizeBlocks) {
        this(cellSizeBlocks, DEFAULT_MAX_CELLS_PER_SOURCE, DEFAULT_MAX_SOURCES, DEFAULT_MAX_EXTERNAL_SOURCES);
    }

    public AtmosphericSourceIndex(int cellSizeBlocks, int maxCellsPerSource) {
        this(cellSizeBlocks, maxCellsPerSource, DEFAULT_MAX_SOURCES, DEFAULT_MAX_EXTERNAL_SOURCES);
    }

    public AtmosphericSourceIndex(int cellSizeBlocks, int maxCellsPerSource, int maxSources) {
        this(cellSizeBlocks, maxCellsPerSource, maxSources, maxSources, maxSources);
    }

    public AtmosphericSourceIndex(
            int cellSizeBlocks,
            int maxCellsPerSource,
            int maxDynamicSources,
            int maxExternalSources
    ) {
        this(cellSizeBlocks, maxCellsPerSource, maxDynamicSources, maxExternalSources,
                summedCapacity(maxDynamicSources, maxExternalSources));
    }

    private AtmosphericSourceIndex(
            int cellSizeBlocks,
            int maxCellsPerSource,
            int maxDynamicSources,
            int maxExternalSources,
            int maxTotalSources
    ) {
        if (cellSizeBlocks <= 0) {
            throw new IllegalArgumentException("cellSizeBlocks must be positive");
        }
        if (maxCellsPerSource <= 0) {
            throw new IllegalArgumentException("maxCellsPerSource must be positive");
        }
        if (maxDynamicSources <= 0) {
            throw new IllegalArgumentException("maxDynamicSources must be positive");
        }
        if (maxExternalSources <= 0) {
            throw new IllegalArgumentException("maxExternalSources must be positive");
        }
        if (maxTotalSources <= 0) {
            throw new IllegalArgumentException("maxTotalSources must be positive");
        }
        this.cellSizeBlocks = cellSizeBlocks;
        this.maxCellsPerSource = maxCellsPerSource;
        this.maxDynamicSources = maxDynamicSources;
        this.maxExternalSources = maxExternalSources;
        this.maxTotalSources = maxTotalSources;
    }

    public void register(AtmosphericSource source) {
        AtmosphericSource value = Objects.requireNonNull(source, "source");
        if (!tryRegister(value)) {
            throw capacityException(value.evolution());
        }
    }

    boolean tryRegister(AtmosphericSource source) {
        AtmosphericSource value = Objects.requireNonNull(source, "source");
        if (sources.containsKey(value.id())) {
            throw new IllegalArgumentException("Atmospheric source already registered: " + value.id());
        }
        CellSpan span = spanFor(value);
        if (!hasCapacityForNewSource(value.evolution())) {
            return false;
        }
        Set<CellRef> refs = membershipsFor(value, span);
        sources.put(value.id(), value);
        incrementOwnership(value.evolution());
        index(value, refs);
        return true;
    }

    public void replace(AtmosphericSource source) {
        AtmosphericSource value = Objects.requireNonNull(source, "source");
        CellSpan span = spanFor(value);
        AtmosphericSource existing = sources.get(value.id());
        if (existing == null) {
            ensureCapacityForNewSource(value.evolution());
        } else if (existing.evolution() != value.evolution()) {
            ensureOwnershipCapacity(value.evolution());
        }
        Set<CellRef> refs = membershipsFor(value, span);
        if (existing != null) {
            remove(value.id());
        }
        sources.put(value.id(), value);
        incrementOwnership(value.evolution());
        index(value, refs);
    }

    public boolean remove(UUID id) {
        Objects.requireNonNull(id, "id");
        AtmosphericSource removed = sources.remove(id);
        if (removed != null) {
            decrementOwnership(removed.evolution());
        }
        Set<CellRef> refs = memberships.remove(id);
        if (refs != null) {
            for (CellRef ref : refs) {
                Map<Long, TreeMap<UUID, AtmosphericSource>> dimensionBuckets = buckets.get(ref.dimensionId());
                if (dimensionBuckets == null) {
                    continue;
                }
                TreeMap<UUID, AtmosphericSource> bucket = dimensionBuckets.get(ref.key());
                if (bucket == null) {
                    continue;
                }
                bucket.remove(id);
                if (bucket.isEmpty()) {
                    dimensionBuckets.remove(ref.key());
                }
                if (dimensionBuckets.isEmpty()) {
                    buckets.remove(ref.dimensionId());
                }
            }
        }
        return removed != null;
    }

    public Optional<AtmosphericSource> source(UUID id) {
        return Optional.ofNullable(sources.get(Objects.requireNonNull(id, "id")));
    }

    public List<AtmosphericSource> candidatesAt(String dimensionId, double x, double z) {
        TreeMap<UUID, AtmosphericSource> bucket = bucketAt(dimensionId, x, z);
        return bucket == null ? List.of() : List.copyOf(bucket.values());
    }

    AtmosphereContribution combinedContributionAt(String dimensionId, double x, double y, double z) {
        if (!Double.isFinite(y)) {
            throw new IllegalArgumentException("sample coordinates must be finite");
        }
        TreeMap<UUID, AtmosphericSource> bucket = bucketAt(dimensionId, x, z);
        PerformanceProfiler.recordAtmosphereSample(bucket == null ? 0 : bucket.size());
        if (bucket == null) {
            return AtmosphereContribution.none();
        }
        AtmosphereContribution combined = AtmosphereContribution.none();
        for (AtmosphericSource source : bucket.values()) {
            AtmosphereContribution local = source.localContributionAt(x, y, z);
            if (local != null) {
                combined = combined.combine(local);
            }
        }
        return combined;
    }

    public int size() {
        return sources.size();
    }

    public List<AtmosphericSource> all() {
        return List.copyOf(sources.values());
    }

    private TreeMap<UUID, AtmosphericSource> bucketAt(String dimensionId, double x, double z) {
        Objects.requireNonNull(dimensionId, "dimensionId");
        if (!Double.isFinite(x) || !Double.isFinite(z)) {
            throw new IllegalArgumentException("sample coordinates must be finite");
        }
        Map<Long, TreeMap<UUID, AtmosphericSource>> dimensionBuckets = buckets.get(dimensionId);
        if (dimensionBuckets == null) {
            return null;
        }
        return dimensionBuckets.get(cellKey(cell(x), cell(z)));
    }

    private boolean hasCapacityForNewSource(AtmosphericSourceEvolution evolution) {
        return sources.size() < maxTotalSources && hasOwnershipCapacity(evolution);
    }

    private boolean hasOwnershipCapacity(AtmosphericSourceEvolution evolution) {
        return switch (evolution) {
            case DYNAMIC -> dynamicSourceCount < maxDynamicSources;
            case EXTERNAL -> externalSourceCount < maxExternalSources;
        };
    }

    private void ensureCapacityForNewSource(AtmosphericSourceEvolution evolution) {
        if (!hasCapacityForNewSource(evolution)) {
            throw capacityException(evolution);
        }
    }

    private void ensureOwnershipCapacity(AtmosphericSourceEvolution evolution) {
        if (!hasOwnershipCapacity(evolution)) {
            throw capacityException(evolution);
        }
    }

    private IllegalStateException capacityException(AtmosphericSourceEvolution evolution) {
        return new IllegalStateException(
                "Atmospheric source capacity reached for " + evolution
                        + " (dynamic=" + maxDynamicSources
                        + ", external=" + maxExternalSources
                        + ", total=" + maxTotalSources + ")");
    }

    private void incrementOwnership(AtmosphericSourceEvolution evolution) {
        switch (evolution) {
            case DYNAMIC -> dynamicSourceCount++;
            case EXTERNAL -> externalSourceCount++;
        }
    }

    private void decrementOwnership(AtmosphericSourceEvolution evolution) {
        switch (evolution) {
            case DYNAMIC -> dynamicSourceCount--;
            case EXTERNAL -> externalSourceCount--;
        }
    }

    private CellSpan spanFor(AtmosphericSource source) {
        int minX = cell(source.x() - source.radiusBlocks());
        int maxX = cell(source.x() + source.radiusBlocks());
        int minZ = cell(source.z() - source.radiusBlocks());
        int maxZ = cell(source.z() + source.radiusBlocks());
        long width = (long) maxX - minX + 1L;
        long depth = (long) maxZ - minZ + 1L;
        if (width <= 0L || depth <= 0L
                || width > maxCellsPerSource
                || depth > maxCellsPerSource
                || width > maxCellsPerSource / depth) {
            throw new IllegalArgumentException(
                    "Atmospheric source spans more than " + maxCellsPerSource + " index cells: " + source.id());
        }
        return new CellSpan(minX, maxX, minZ, maxZ, Math.toIntExact(width * depth));
    }

    private Set<CellRef> membershipsFor(AtmosphericSource source, CellSpan span) {
        Set<CellRef> refs = new LinkedHashSet<>(span.expectedMemberships());
        for (long cellX = span.minX(); cellX <= span.maxX(); cellX++) {
            for (long cellZ = span.minZ(); cellZ <= span.maxZ(); cellZ++) {
                refs.add(new CellRef(source.dimensionId(), cellKey((int) cellX, (int) cellZ)));
            }
        }
        return refs;
    }

    private void index(AtmosphericSource source, Set<CellRef> refs) {
        Map<Long, TreeMap<UUID, AtmosphericSource>> dimensionBuckets =
                buckets.computeIfAbsent(source.dimensionId(), ignored -> new HashMap<>());
        for (CellRef ref : refs) {
            dimensionBuckets.computeIfAbsent(ref.key(), ignored -> new TreeMap<>()).put(source.id(), source);
        }
        memberships.put(source.id(), refs);
    }

    private int cell(double coordinate) {
        if (!Double.isFinite(coordinate)) {
            throw new IllegalArgumentException("index coordinate must be finite");
        }
        double cell = Math.floor(coordinate / cellSizeBlocks);
        if (cell < Integer.MIN_VALUE || cell > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("index coordinate is outside supported range");
        }
        return (int) cell;
    }

    private static int summedCapacity(int maxDynamicSources, int maxExternalSources) {
        if (maxDynamicSources <= 0) {
            throw new IllegalArgumentException("maxDynamicSources must be positive");
        }
        if (maxExternalSources <= 0) {
            throw new IllegalArgumentException("maxExternalSources must be positive");
        }
        try {
            return Math.addExact(maxDynamicSources, maxExternalSources);
        } catch (ArithmeticException overflow) {
            throw new IllegalArgumentException("combined atmospheric source capacity exceeds integer range", overflow);
        }
    }

    private static long cellKey(int x, int z) {
        return ((long) x << 32) ^ (z & 0xffffffffL);
    }

    private record CellSpan(int minX, int maxX, int minZ, int maxZ, int expectedMemberships) {
    }

    private record CellRef(String dimensionId, long key) {
    }
}
