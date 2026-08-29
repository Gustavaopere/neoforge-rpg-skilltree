package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Pure O(1) territory/area-level lookup with optional deterministic border blending.
 *
 * <p>The blend radius is expressed in blocks. A radius of zero resolves only the
 * primary territory. A positive radius uses a square integer sampling kernel; the
 * constraint {@code 2 * radius + 1 <= cellSize} guarantees at most two cells per
 * horizontal axis and therefore at most four Native Area plan lookups.</p>
 */
public final class TerritoryAreaLevelResolver {
    private TerritoryAreaLevelResolver() {}

    public static TerritoryAreaLevelResolution resolve(
        String dimensionId,
        long blockX,
        long blockY,
        long blockZ,
        TerritoryGrid grid,
        long blendRadiusBlocks,
        NativeAreaLevelPlanProvider provider
    ) {
        Objects.requireNonNull(grid, "grid");
        Objects.requireNonNull(provider, "provider");
        if (blendRadiusBlocks < 0L) {
            throw new IllegalArgumentException("blendRadiusBlocks must be non-negative");
        }

        long diameter = Math.addExact(Math.multiplyExact(blendRadiusBlocks, 2L), 1L);
        if (diameter > grid.cellSizeBlocks()) {
            throw new IllegalArgumentException(
                "blend diameter must not exceed territory cell size; this preserves the four-sample bound"
            );
        }

        @SuppressWarnings("unused") long ignoredY = blockY;

        TerritoryKey primary = grid.key(dimensionId, blockX, blockZ);
        List<AxisPart> xParts = axisParts(blockX, grid.cellSizeBlocks(), blendRadiusBlocks, diameter);
        List<AxisPart> zParts = axisParts(blockZ, grid.cellSizeBlocks(), blendRadiusBlocks, diameter);

        ArrayList<TerritoryAreaLevelSample> samples = new ArrayList<>(xParts.size() * zParts.size());
        BigInteger weightedLevel = BigInteger.ZERO;
        long totalWeight = 0L;

        for (AxisPart xPart : xParts) {
            for (AxisPart zPart : zParts) {
                long weight = Math.multiplyExact(xPart.span(), zPart.span());
                TerritoryKey territory = TerritoryKey.of(dimensionId, xPart.cell(), zPart.cell());
                NativeAreaLevelPlan plan = Objects.requireNonNull(
                    provider.plan(territory),
                    "NativeAreaLevelPlanProvider returned null"
                );
                NativeAreaLevelBreakdown breakdown = NativeAreaThreatResolver.resolve(territory, plan);
                samples.add(new TerritoryAreaLevelSample(territory, breakdown, weight));
                totalWeight = Math.addExact(totalWeight, weight);
                weightedLevel = weightedLevel.add(
                    BigInteger.valueOf(breakdown.resolvedLevel()).multiply(BigInteger.valueOf(weight))
                );
            }
        }

        long resolvedLevel = weightedLevel.divide(BigInteger.valueOf(totalWeight)).longValueExact();
        return new TerritoryAreaLevelResolution(primary, resolvedLevel, samples, totalWeight);
    }

    private static List<AxisPart> axisParts(
        long coordinate,
        long cellSize,
        long radius,
        long diameter
    ) {
        long cell = Math.floorDiv(coordinate, cellSize);
        long local = Math.floorMod(coordinate, cellSize);
        long distanceToPositiveEdge = (cellSize - 1L) - local;
        long negativeSpan = radius > local ? radius - local : 0L;
        long positiveSpan = radius > distanceToPositiveEdge ? radius - distanceToPositiveEdge : 0L;
        long primarySpan = diameter - negativeSpan - positiveSpan;

        ArrayList<AxisPart> parts = new ArrayList<>(2);
        if (negativeSpan > 0L) {
            parts.add(new AxisPart(Math.subtractExact(cell, 1L), negativeSpan));
        }
        parts.add(new AxisPart(cell, primarySpan));
        if (positiveSpan > 0L) {
            parts.add(new AxisPart(Math.addExact(cell, 1L), positiveSpan));
        }
        if (parts.size() > 2) {
            throw new IllegalStateException("blend radius crossed more than one territory boundary on one axis");
        }
        return List.copyOf(parts);
    }

    private record AxisPart(long cell, long span) {
        private AxisPart {
            if (span <= 0L) throw new IllegalArgumentException("axis sample span must be positive");
        }
    }
}
