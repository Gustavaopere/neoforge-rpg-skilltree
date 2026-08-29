package dev.gustavopere.rpgskilltree.core;

/**
 * Pure deterministic mapping from provider/world block coordinates into a stable TerritoryKey.
 *
 * <p>Cell size is an explicit external policy. This resolver deliberately does not choose a
 * gameplay territory size, threat formula, spawn origin, biome rule, structure rule or noise
 * function.</p>
 */
public final class TerritoryGridResolver {
    private TerritoryGridResolver() {}

    public static TerritoryKey resolve(
        String dimensionId,
        long blockX,
        long blockZ,
        long cellSizeBlocks
    ) {
        if (cellSizeBlocks <= 0L) {
            throw new IllegalArgumentException("cellSizeBlocks must be positive");
        }

        return TerritoryKey.of(
            dimensionId,
            Math.floorDiv(blockX, cellSizeBlocks),
            Math.floorDiv(blockZ, cellSizeBlocks)
        );
    }
}
