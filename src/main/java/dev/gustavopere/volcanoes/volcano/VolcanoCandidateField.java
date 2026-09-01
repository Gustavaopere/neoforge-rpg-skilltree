package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Sparse deterministic lattice of potential volcano centers.
 *
 * <p>The seed selects one global phase for the lattice. Every coarse cell therefore has exactly
 * one candidate and neighboring candidates remain a full cell apart; enumeration can inspect only
 * the cells whose bounded footprints can reach the chunk currently being generated.</p>
 */
public final class VolcanoCandidateField {
    private static final long X_PHASE_SALT = 0xD1B54A32D192ED03L;

    private final int cellSizeBlocks;
    private final int minimumSpacingBlocks;

    public VolcanoCandidateField(int cellSizeBlocks, int minimumSpacingBlocks) {
        if (cellSizeBlocks <= 0) {
            throw new IllegalArgumentException("cellSizeBlocks must be positive");
        }
        if (minimumSpacingBlocks <= 0 || cellSizeBlocks < minimumSpacingBlocks * 2L) {
            throw new IllegalArgumentException(
                    "minimumSpacingBlocks must be positive and at most half the cell size");
        }
        this.cellSizeBlocks = cellSizeBlocks;
        this.minimumSpacingBlocks = minimumSpacingBlocks;
    }

    public BlockPos centerForCell(long worldSeed, long cellX, long cellZ) {
        int phaseX = phase(worldSeed ^ X_PHASE_SALT);
        int phaseZ = phase(worldSeed);
        long x = Math.addExact(Math.multiplyExact(cellX, (long) cellSizeBlocks), phaseX);
        long z = Math.addExact(Math.multiplyExact(cellZ, (long) cellSizeBlocks), phaseZ);
        return new BlockPos(Math.toIntExact(x), 0, Math.toIntExact(z));
    }

    public boolean ownsCenter(ChunkPos chunk, BlockPos center) {
        Objects.requireNonNull(chunk, "chunk");
        Objects.requireNonNull(center, "center");
        return chunk.equals(new ChunkPos(center));
    }

    public List<BlockPos> centersAffectingChunk(long worldSeed, ChunkPos chunk, int footprintRadiusBlocks) {
        Objects.requireNonNull(chunk, "chunk");
        if (footprintRadiusBlocks < 0) {
            throw new IllegalArgumentException("footprintRadiusBlocks must be non-negative");
        }

        int phaseX = phase(worldSeed ^ X_PHASE_SALT);
        int phaseZ = phase(worldSeed);
        long minX = (long) chunk.getMinBlockX() - footprintRadiusBlocks;
        long maxX = (long) chunk.getMaxBlockX() + footprintRadiusBlocks;
        long minZ = (long) chunk.getMinBlockZ() - footprintRadiusBlocks;
        long maxZ = (long) chunk.getMaxBlockZ() + footprintRadiusBlocks;

        long minCellX = Math.floorDiv(minX - phaseX, (long) cellSizeBlocks);
        long maxCellX = Math.floorDiv(maxX - phaseX, (long) cellSizeBlocks);
        long minCellZ = Math.floorDiv(minZ - phaseZ, (long) cellSizeBlocks);
        long maxCellZ = Math.floorDiv(maxZ - phaseZ, (long) cellSizeBlocks);

        List<BlockPos> centers = new ArrayList<>();
        for (long cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (long cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                BlockPos center = centerForCell(worldSeed, cellX, cellZ);
                if (horizontalDistanceToChunk(center, chunk) <= footprintRadiusBlocks) {
                    centers.add(center);
                }
            }
        }
        centers.sort(Comparator
                .comparingInt((BlockPos pos) -> pos.getX())
                .thenComparingInt(pos -> pos.getZ()));
        return List.copyOf(centers);
    }

    public static double horizontalDistanceToChunk(BlockPos center, ChunkPos chunk) {
        Objects.requireNonNull(center, "center");
        Objects.requireNonNull(chunk, "chunk");
        int nearestX = Math.max(chunk.getMinBlockX(), Math.min(chunk.getMaxBlockX(), center.getX()));
        int nearestZ = Math.max(chunk.getMinBlockZ(), Math.min(chunk.getMaxBlockZ(), center.getZ()));
        return Math.hypot((double) center.getX() - nearestX, (double) center.getZ() - nearestZ);
    }

    public int cellSizeBlocks() {
        return cellSizeBlocks;
    }

    public int minimumSpacingBlocks() {
        return minimumSpacingBlocks;
    }

    private int phase(long seed) {
        return (int) Math.floorMod(mix64(seed), (long) cellSizeBlocks);
    }

    private static long mix64(long value) {
        value ^= value >>> 33;
        value *= 0xff51afd7ed558ccdL;
        value ^= value >>> 33;
        value *= 0xc4ceb9fe1a85ec53L;
        value ^= value >>> 33;
        return value;
    }
}
