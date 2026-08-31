package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * Bounded X/Z spatial index for volcanic heat sources.
 *
 * <p>Sources are inserted into every cell touched by their radius, so nearby queries never need to
 * scan volcanic blocks or the full source population. Constructor bounds cap both source and query
 * cell fan-out. Dynamic sources are indexed by expiry deadline; permanent geothermal sources never
 * participate in per-tick expiry work.</p>
 */
public final class VolcanicHeatSourceIndex {
    private static final int MAX_CELLS_PER_SOURCE = 1_024;
    private static final int MAX_CELLS_PER_QUERY = 4_096;

    private final int cellSizeBlocks;
    private final double maxSourceRadiusBlocks;
    private final double maxQueryRadiusBlocks;
    private final int maxSources;
    private final Map<UUID, VolcanicHeatSource> sources = new LinkedHashMap<>();
    private final Map<Long, LinkedHashSet<UUID>> byCell = new HashMap<>();
    private final Map<UUID, List<Long>> cellsBySource = new HashMap<>();
    private final NavigableMap<Long, LinkedHashSet<UUID>> expirationsByTick = new TreeMap<>();
    private final Map<UUID, Long> expiryTickBySource = new HashMap<>();

    public VolcanicHeatSourceIndex(
            int cellSizeBlocks,
            double maxSourceRadiusBlocks,
            double maxQueryRadiusBlocks,
            int maxSources
    ) {
        if (cellSizeBlocks <= 0) {
            throw new IllegalArgumentException("cellSizeBlocks must be positive");
        }
        if (!Double.isFinite(maxSourceRadiusBlocks) || maxSourceRadiusBlocks <= 0.0) {
            throw new IllegalArgumentException("maxSourceRadiusBlocks must be finite and positive");
        }
        if (!Double.isFinite(maxQueryRadiusBlocks) || maxQueryRadiusBlocks < 0.0) {
            throw new IllegalArgumentException("maxQueryRadiusBlocks must be finite and non-negative");
        }
        if (maxSources <= 0) {
            throw new IllegalArgumentException("maxSources must be positive");
        }
        this.cellSizeBlocks = cellSizeBlocks;
        this.maxSourceRadiusBlocks = maxSourceRadiusBlocks;
        this.maxQueryRadiusBlocks = maxQueryRadiusBlocks;
        this.maxSources = maxSources;
        requireBoundedFanOut(maxSourceRadiusBlocks, MAX_CELLS_PER_SOURCE, "source");
        requireBoundedFanOut(maxQueryRadiusBlocks, MAX_CELLS_PER_QUERY, "query");
    }

    /** Adds or replaces one source. New sources are rejected fail-closed when capacity is full. */
    public synchronized boolean upsert(VolcanicHeatSource source) {
        Objects.requireNonNull(source, "source");
        if (source.radiusBlocks() > maxSourceRadiusBlocks) {
            throw new IllegalArgumentException("source radius exceeds configured maximum");
        }
        VolcanicHeatSource existing = sources.get(source.sourceId());
        if (source.equals(existing)) {
            return false;
        }
        if (existing == null && sources.size() >= maxSources) {
            return false;
        }
        if (existing != null) {
            removeMemberships(existing.sourceId());
            unscheduleExpiry(existing.sourceId());
        }
        sources.put(source.sourceId(), source);
        addMemberships(source);
        scheduleExpiry(source);
        return true;
    }

    public synchronized boolean remove(UUID sourceId) {
        Objects.requireNonNull(sourceId, "sourceId");
        VolcanicHeatSource removed = sources.remove(sourceId);
        if (removed == null) {
            return false;
        }
        removeMemberships(sourceId);
        unscheduleExpiry(sourceId);
        return true;
    }

    public synchronized int size() {
        return sources.size();
    }

    /** Number of temporary sources participating in expiry scheduling. */
    synchronized int scheduledExpiryCount() {
        return expiryTickBySource.size();
    }

    /**
     * Returns at most {@code maxResults} live sources whose influence radius intersects the query.
     * Ordering is nearest-center first, then stable source UUID.
     */
    public synchronized List<VolcanicHeatSource> nearby(
            BlockPos center,
            double queryRadiusBlocks,
            int maxResults,
            long gameTick
    ) {
        return nearbyMatching(center, queryRadiusBlocks, maxResults, gameTick, source -> true);
    }

    /**
     * Kind-filtered nearby query. Filtering occurs before the result cap, preventing unrelated heat
     * producers from starving a bounded consumer while preserving the same spatial candidate bound.
     */
    public synchronized List<VolcanicHeatSource> nearbyOfKind(
            BlockPos center,
            double queryRadiusBlocks,
            int maxResults,
            long gameTick,
            VolcanicHeatSource.Kind kind
    ) {
        Objects.requireNonNull(kind, "kind");
        return nearbyMatching(center, queryRadiusBlocks, maxResults, gameTick, source -> source.kind() == kind);
    }

    /** Package-local filtered query for consumers that need subtype admission before the result cap. */
    synchronized List<VolcanicHeatSource> nearbyMatching(
            BlockPos center,
            double queryRadiusBlocks,
            int maxResults,
            long gameTick,
            Predicate<VolcanicHeatSource> filter
    ) {
        Objects.requireNonNull(center, "center");
        Objects.requireNonNull(filter, "filter");
        if (!Double.isFinite(queryRadiusBlocks)
                || queryRadiusBlocks < 0.0
                || queryRadiusBlocks > maxQueryRadiusBlocks) {
            throw new IllegalArgumentException("query radius is outside configured bounds");
        }
        if (maxResults < 0) {
            throw new IllegalArgumentException("maxResults must be non-negative");
        }
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        if (maxResults == 0 || sources.isEmpty()) {
            return List.of();
        }

        int minCellX = cell(center.getX() - ceilRadius(queryRadiusBlocks));
        int maxCellX = cell(center.getX() + ceilRadius(queryRadiusBlocks));
        int minCellZ = cell(center.getZ() - ceilRadius(queryRadiusBlocks));
        int maxCellZ = cell(center.getZ() + ceilRadius(queryRadiusBlocks));
        Set<UUID> candidateIds = new HashSet<>();
        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                Set<UUID> ids = byCell.get(cellKey(cellX, cellZ));
                if (ids != null) {
                    candidateIds.addAll(ids);
                }
            }
        }

        return candidateIds.stream()
                .map(sources::get)
                .filter(Objects::nonNull)
                .filter(source -> !source.isExpired(gameTick))
                .filter(filter)
                .filter(source -> intersects(center, queryRadiusBlocks, source))
                .sorted(Comparator
                        .comparingDouble((VolcanicHeatSource source) -> distanceSquared(center, source.center()))
                        .thenComparing(source -> source.sourceId().toString()))
                .limit(maxResults)
                .toList();
    }

    /** Removes at most {@code maxRemovals} expired dynamic sources without scanning permanent sources. */
    public synchronized int expire(long gameTick, int maxRemovals) {
        if (gameTick < 0L) {
            throw new IllegalArgumentException("gameTick must be non-negative");
        }
        if (maxRemovals < 0) {
            throw new IllegalArgumentException("maxRemovals must be non-negative");
        }
        if (maxRemovals == 0) {
            return 0;
        }

        int removed = 0;
        while (removed < maxRemovals) {
            Map.Entry<Long, LinkedHashSet<UUID>> first = expirationsByTick.firstEntry();
            if (first == null || first.getKey() > gameTick) {
                break;
            }
            UUID sourceId = first.getValue().iterator().next();
            if (remove(sourceId)) {
                removed++;
            } else {
                unscheduleExpiry(sourceId);
            }
        }
        return removed;
    }

    private void addMemberships(VolcanicHeatSource source) {
        int radius = ceilRadius(source.radiusBlocks());
        int minCellX = cell(source.center().getX() - radius);
        int maxCellX = cell(source.center().getX() + radius);
        int minCellZ = cell(source.center().getZ() - radius);
        int maxCellZ = cell(source.center().getZ() + radius);
        List<Long> memberships = new ArrayList<>();
        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                long key = cellKey(cellX, cellZ);
                byCell.computeIfAbsent(key, ignored -> new LinkedHashSet<>()).add(source.sourceId());
                memberships.add(key);
            }
        }
        if (memberships.size() > MAX_CELLS_PER_SOURCE) {
            throw new IllegalStateException("source membership exceeded configured hard bound");
        }
        cellsBySource.put(source.sourceId(), List.copyOf(memberships));
    }

    private void removeMemberships(UUID sourceId) {
        List<Long> memberships = cellsBySource.remove(sourceId);
        if (memberships == null) {
            return;
        }
        for (long key : memberships) {
            LinkedHashSet<UUID> ids = byCell.get(key);
            if (ids == null) {
                continue;
            }
            ids.remove(sourceId);
            if (ids.isEmpty()) {
                byCell.remove(key);
            }
        }
    }

    private void scheduleExpiry(VolcanicHeatSource source) {
        if (source.expiresAtTick() == Long.MAX_VALUE) {
            return;
        }
        expiryTickBySource.put(source.sourceId(), source.expiresAtTick());
        expirationsByTick
                .computeIfAbsent(source.expiresAtTick(), ignored -> new LinkedHashSet<>())
                .add(source.sourceId());
    }

    private void unscheduleExpiry(UUID sourceId) {
        Long expiryTick = expiryTickBySource.remove(sourceId);
        if (expiryTick == null) {
            return;
        }
        LinkedHashSet<UUID> ids = expirationsByTick.get(expiryTick);
        if (ids == null) {
            return;
        }
        ids.remove(sourceId);
        if (ids.isEmpty()) {
            expirationsByTick.remove(expiryTick);
        }
    }

    private void requireBoundedFanOut(double radius, int hardLimit, String label) {
        int cellsPerAxis = 2 * (int) Math.ceil(radius / cellSizeBlocks) + 3;
        long cells = (long) cellsPerAxis * cellsPerAxis;
        if (cells > hardLimit) {
            throw new IllegalArgumentException(label + " radius/cell-size combination exceeds hard spatial bound");
        }
    }

    private int cell(int blockCoordinate) {
        return Math.floorDiv(blockCoordinate, cellSizeBlocks);
    }

    private static int ceilRadius(double radius) {
        return (int) Math.ceil(radius);
    }

    private static long cellKey(int cellX, int cellZ) {
        return ((long) cellX << 32) ^ (cellZ & 0xffffffffL);
    }

    private static boolean intersects(BlockPos center, double queryRadius, VolcanicHeatSource source) {
        double combinedRadius = queryRadius + source.radiusBlocks();
        return distanceSquared(center, source.center()) <= combinedRadius * combinedRadius;
    }

    private static double distanceSquared(BlockPos first, BlockPos second) {
        double dx = (double) first.getX() - second.getX();
        double dz = (double) first.getZ() - second.getZ();
        return dx * dx + dz * dz;
    }
}
