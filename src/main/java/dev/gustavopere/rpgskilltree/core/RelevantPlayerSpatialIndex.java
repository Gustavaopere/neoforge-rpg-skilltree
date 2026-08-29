package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable two-dimensional spatial index for cached player presences.
 *
 * <p>The index is intentionally provider-neutral. It never scans a Minecraft level and
 * never reads progression state; the runtime supplies one immutable presence snapshot.
 * Queries visit only cells intersecting the configured candidate radius.</p>
 */
public final class RelevantPlayerSpatialIndex {
    private static final long SAFE_SQUARE_ROOT = 3_037_000_499L;

    private final int cellSizeBlocks;
    private final int indexedPlayers;
    private final Map<CellKey, List<RelevantPlayerPresence>> buckets;

    private RelevantPlayerSpatialIndex(
        int cellSizeBlocks,
        int indexedPlayers,
        Map<CellKey, List<RelevantPlayerPresence>> buckets
    ) {
        this.cellSizeBlocks = cellSizeBlocks;
        this.indexedPlayers = indexedPlayers;
        this.buckets = Map.copyOf(buckets);
    }

    public static RelevantPlayerSpatialIndex build(
        List<RelevantPlayerPresence> presences,
        int cellSizeBlocks
    ) {
        Objects.requireNonNull(presences, "presences");
        if (cellSizeBlocks <= 0) {
            throw new IllegalArgumentException("cellSizeBlocks must be positive");
        }

        HashSet<String> seenIds = new HashSet<>();
        HashMap<CellKey, ArrayList<RelevantPlayerPresence>> mutable = new HashMap<>();
        for (RelevantPlayerPresence raw : presences) {
            RelevantPlayerPresence presence = Objects.requireNonNull(raw, "presence");
            if (!seenIds.add(presence.playerId())) {
                throw new IllegalArgumentException("duplicate indexed player id: " + presence.playerId());
            }
            CellKey key = new CellKey(
                Math.floorDiv((long) presence.blockX(), cellSizeBlocks),
                Math.floorDiv((long) presence.blockZ(), cellSizeBlocks)
            );
            mutable.computeIfAbsent(key, ignored -> new ArrayList<>()).add(presence);
        }

        HashMap<CellKey, List<RelevantPlayerPresence>> frozen = new HashMap<>();
        mutable.forEach((key, value) -> {
            value.sort(Comparator.comparing(RelevantPlayerPresence::playerId));
            frozen.put(key, List.copyOf(value));
        });
        return new RelevantPlayerSpatialIndex(cellSizeBlocks, presences.size(), frozen);
    }

    public RelevantPlayerSpatialQuery query(
        int blockX,
        int blockY,
        int blockZ,
        RelevantPlayerSearchPolicy policy
    ) {
        Objects.requireNonNull(policy, "policy");
        if (policy.cellSizeBlocks() != cellSizeBlocks) {
            throw new IllegalArgumentException(
                "search policy cell size " + policy.cellSizeBlocks()
                    + " does not match index cell size " + cellSizeBlocks
            );
        }

        long radius = policy.candidateRadiusBlocks();
        long minCellX = Math.floorDiv((long) blockX - radius, cellSizeBlocks);
        long maxCellX = Math.floorDiv((long) blockX + radius, cellSizeBlocks);
        long minCellZ = Math.floorDiv((long) blockZ - radius, cellSizeBlocks);
        long maxCellZ = Math.floorDiv((long) blockZ + radius, cellSizeBlocks);

        long visitedCells = Math.multiplyExact(
            Math.addExact(Math.subtractExact(maxCellX, minCellX), 1L),
            Math.addExact(Math.subtractExact(maxCellZ, minCellZ), 1L)
        );
        if (visitedCells > RelevantPlayerSearchPolicy.MAX_QUERY_CELLS) {
            throw new IllegalStateException("query exceeded bounded cell budget: " + visitedCells);
        }

        long candidateRadiusSquared = policy.candidateRadiusSquared();
        long engagementRadiusSquared = policy.engagementRadiusSquared();
        ArrayList<RelevantPlayerCandidate> selected = new ArrayList<>();
        int scannedPlayers = 0;

        for (long cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (long cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                List<RelevantPlayerPresence> bucket = buckets.get(new CellKey(cellX, cellZ));
                if (bucket == null) continue;
                for (RelevantPlayerPresence presence : bucket) {
                    scannedPlayers = Math.addExact(scannedPlayers, 1);
                    long distanceSquared = distanceSquaredSaturated(
                        blockX,
                        blockY,
                        blockZ,
                        presence.blockX(),
                        presence.blockY(),
                        presence.blockZ()
                    );
                    if (distanceSquared > candidateRadiusSquared) continue;
                    selected.add(new RelevantPlayerCandidate(
                        presence.playerId(),
                        presence.level(),
                        distanceSquared,
                        distanceSquared <= engagementRadiusSquared,
                        false
                    ));
                }
            }
        }

        selected.sort(
            Comparator.comparingLong(RelevantPlayerCandidate::distanceSquared)
                .thenComparing(RelevantPlayerCandidate::playerId)
        );
        if (selected.size() > policy.maxCandidates()) {
            selected.subList(policy.maxCandidates(), selected.size()).clear();
        }

        return new RelevantPlayerSpatialQuery(
            selected,
            indexedPlayers,
            scannedPlayers,
            visitedCells
        );
    }

    public int indexedPlayers() {
        return indexedPlayers;
    }

    public int cellSizeBlocks() {
        return cellSizeBlocks;
    }

    private static long distanceSquaredSaturated(
        int leftX,
        int leftY,
        int leftZ,
        int rightX,
        int rightY,
        int rightZ
    ) {
        long dx = (long) leftX - rightX;
        long dy = (long) leftY - rightY;
        long dz = (long) leftZ - rightZ;
        return addSaturated(addSaturated(squareSaturated(dx), squareSaturated(dy)), squareSaturated(dz));
    }

    private static long squareSaturated(long value) {
        if (value > SAFE_SQUARE_ROOT || value < -SAFE_SQUARE_ROOT) {
            return Long.MAX_VALUE;
        }
        return value * value;
    }

    private static long addSaturated(long left, long right) {
        if (left == Long.MAX_VALUE || right == Long.MAX_VALUE || Long.MAX_VALUE - left < right) {
            return Long.MAX_VALUE;
        }
        return left + right;
    }

    private record CellKey(long x, long z) {}
}
