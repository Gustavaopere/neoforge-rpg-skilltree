package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Pure deterministic planner for sparse geothermal features.
 *
 * <p>The candidate lattice uses at least the largest minimum spacing required by any configured
 * profile and is rounded up to a whole number of chunks. Candidate centers are therefore always
 * chunk-centered, so bounded geothermal profiles can be written entirely by their owning chunk.
 * Worldgen callers supply already-evaluated terrain/water predicates and geothermal potential;
 * this class never reads world state or persistence.</p>
 */
public final class GeothermalFeaturePlanner {
    private static final long X_PHASE_SALT = 0xA24BAED4963EE407L;
    private static final long Z_PHASE_SALT = 0x9FB21C651E98DF25L;
    private static final long SELECTION_SALT = 0xC13FA9A902A6328FL;
    private static final int CHUNK_SIZE_BLOCKS = 16;
    private static final int CHUNK_CENTER_OFFSET = 8;

    private final List<GeothermalFeatureProfile> profiles;
    private final int cellSizeBlocks;

    public GeothermalFeaturePlanner(List<GeothermalFeatureProfile> profiles) {
        Objects.requireNonNull(profiles, "profiles");
        if (profiles.isEmpty()) {
            throw new IllegalArgumentException("profiles must not be empty");
        }
        this.profiles = List.copyOf(profiles);
        int minimumCellSize = this.profiles.stream()
                .mapToInt(GeothermalFeatureProfile::minimumSpacingBlocks)
                .max()
                .orElseThrow();
        this.cellSizeBlocks = roundUpToChunk(minimumCellSize);
    }

    public static GeothermalFeaturePlanner defaults() {
        return new GeothermalFeaturePlanner(Arrays.stream(GeothermalFeatureType.values())
                .map(GeothermalFeatureProfile::defaults)
                .toList());
    }

    public BlockPos candidateCenter(long worldSeed, long cellX, long cellZ) {
        int phaseX = phase(worldSeed ^ X_PHASE_SALT);
        int phaseZ = phase(worldSeed ^ Z_PHASE_SALT);
        long x = Math.addExact(Math.multiplyExact(cellX, (long) cellSizeBlocks), phaseX);
        long z = Math.addExact(Math.multiplyExact(cellZ, (long) cellSizeBlocks), phaseZ);
        return new BlockPos(Math.toIntExact(x), 0, Math.toIntExact(z));
    }

    /**
     * Plans at most one feature for the chunk that owns a lattice candidate.
     *
     * @param geothermalPotential causal potential already evaluated by {@link GeothermalActivityService}
     * @param hasWater whether the local surface predicate found usable water
     * @param terrainSuitable whether slope/material/clearance predicates accepted the site
     */
    public Optional<GeothermalFeaturePlacement> plan(
            long worldSeed,
            ChunkPos chunk,
            double geothermalPotential,
            boolean hasWater,
            boolean terrainSuitable
    ) {
        Objects.requireNonNull(chunk, "chunk");
        requireUnit(geothermalPotential, "geothermalPotential");
        if (!terrainSuitable) {
            return Optional.empty();
        }

        Optional<BlockPos> candidate = candidateOwnedByChunk(worldSeed, chunk);
        if (candidate.isEmpty()) {
            return Optional.empty();
        }

        List<GeothermalFeatureProfile> eligible = profiles.stream()
                .filter(profile -> geothermalPotential >= profile.minimumPotential())
                .filter(profile -> !profile.requiresWater() || hasWater)
                .toList();
        if (eligible.isEmpty()) {
            return Optional.empty();
        }

        BlockPos center = candidate.orElseThrow();
        long selection = mix64(
                worldSeed
                        ^ SELECTION_SALT
                        ^ ((long) center.getX() * 0x9E3779B97F4A7C15L)
                        ^ ((long) center.getZ() * 0xD1B54A32D192ED03L));
        int index = (int) Math.floorMod(selection, (long) eligible.size());
        return Optional.of(GeothermalFeaturePlacement.fromProfile(center, eligible.get(index)));
    }

    public int cellSizeBlocks() {
        return cellSizeBlocks;
    }

    /**
     * Returns the deterministic lattice candidate owned by {@code chunk}, before any environmental
     * predicates are evaluated. Worldgen uses this to sample potential, surface and nearby water at
     * the exact candidate position without consulting persistence or guessing from the chunk center.
     */
    public Optional<BlockPos> candidateOwnedByChunk(long worldSeed, ChunkPos chunk) {
        Objects.requireNonNull(chunk, "chunk");
        int phaseX = phase(worldSeed ^ X_PHASE_SALT);
        int phaseZ = phase(worldSeed ^ Z_PHASE_SALT);
        long minCellX = Math.floorDiv((long) chunk.getMinBlockX() - phaseX, (long) cellSizeBlocks);
        long maxCellX = Math.floorDiv((long) chunk.getMaxBlockX() - phaseX, (long) cellSizeBlocks);
        long minCellZ = Math.floorDiv((long) chunk.getMinBlockZ() - phaseZ, (long) cellSizeBlocks);
        long maxCellZ = Math.floorDiv((long) chunk.getMaxBlockZ() - phaseZ, (long) cellSizeBlocks);

        for (long cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (long cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                BlockPos candidate = candidateCenter(worldSeed, cellX, cellZ);
                if (chunk.equals(new ChunkPos(candidate))) {
                    return Optional.of(candidate);
                }
            }
        }
        return Optional.empty();
    }

    private int phase(long seed) {
        long chunkSlots = cellSizeBlocks / (long) CHUNK_SIZE_BLOCKS;
        long chunkOffset = Math.floorMod(mix64(seed), chunkSlots);
        return Math.toIntExact(chunkOffset * CHUNK_SIZE_BLOCKS + CHUNK_CENTER_OFFSET);
    }

    private static int roundUpToChunk(int minimumBlocks) {
        if (minimumBlocks <= 0) {
            throw new IllegalArgumentException("minimumBlocks must be positive");
        }
        long chunks = Math.floorDiv((long) minimumBlocks + CHUNK_SIZE_BLOCKS - 1L, CHUNK_SIZE_BLOCKS);
        return Math.toIntExact(Math.multiplyExact(chunks, (long) CHUNK_SIZE_BLOCKS));
    }

    private static void requireUnit(double value, String name) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0, 1]");
        }
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
