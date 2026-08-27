package dev.gustavopere.rpgskilltree.core;

/** Configurable deterministic mapping from block coordinates to stable territory cells. */
public record TerritoryGrid(long cellSizeBlocks) {
    public TerritoryGrid {
        if (cellSizeBlocks <= 0L) {
            throw new IllegalArgumentException("cellSizeBlocks must be positive");
        }
    }

    public TerritoryKey key(String dimensionId, long blockX, long blockZ) {
        long cellX = Math.floorDiv(blockX, cellSizeBlocks);
        long cellZ = Math.floorDiv(blockZ, cellSizeBlocks);
        return TerritoryKey.of(dimensionId, cellX, cellZ);
    }
}
