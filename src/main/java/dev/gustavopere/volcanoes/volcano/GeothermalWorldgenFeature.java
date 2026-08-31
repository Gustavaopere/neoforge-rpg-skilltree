package dev.gustavopere.volcanoes.volcano;

import com.mojang.serialization.Codec;
import dev.gustavopere.volcanoes.geology.GeologicalDeposit;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Sparse bounded worldgen for visible geothermal surface features. */
public final class GeothermalWorldgenFeature extends Feature<NoneFeatureConfiguration> {
    public static final int MAGMA_INFLUENCE_RADIUS_BLOCKS = 2_048;
    public static final int MAX_FEATURE_RADIUS_BLOCKS = Arrays.stream(GeothermalFeatureType.values())
            .map(GeothermalFeatureProfile::defaults)
            .mapToInt(GeothermalFeatureProfile::radiusBlocks)
            .max()
            .orElseThrow();

    private static final int MAX_FLOOR_DELTA_BLOCKS = 2;
    private static final int MAX_SHALLOW_WATER_DEPTH_BLOCKS = 2;
    private static final HydrothermalDepositProjector DEPOSIT_PROJECTOR =
            new HydrothermalDepositProjector(RuntimeDependencies.RESOLVER);
    private static final HydrothermalOreWorldgenProducer ORE_PRODUCER =
            new HydrothermalOreWorldgenProducer();

    public GeothermalWorldgenFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        ChunkPos chunk = new ChunkPos(context.origin());
        long worldSeed = level.getSeed();
        GeothermalFeaturePlanner planner = RuntimeDependencies.PLANNER;

        Optional<BlockPos> candidate = planner.candidateOwnedByChunk(worldSeed, chunk);
        if (candidate.isEmpty()) {
            return false;
        }

        SurfaceProbe surface = probeSurface(level, chunk, candidate.orElseThrow());
        if (!surface.terrainSuitable()) {
            return false;
        }

        BlockPos sampledPosition = new BlockPos(
                candidate.orElseThrow().getX(),
                surface.floorY(),
                candidate.orElseThrow().getZ());
        double potential = RuntimeDependencies.RESOLVER.potentialAt(worldSeed, sampledPosition);
        Optional<GeothermalFeaturePlacement> planned = planner.plan(
                worldSeed,
                chunk,
                potential,
                surface.hasShallowWater(),
                true);
        if (planned.isEmpty()) {
            return false;
        }

        GeothermalFeaturePlacement placement = anchorToSurface(planned.orElseThrow(), sampledPosition);
        Optional<GeothermalPendingQueue.Reservation> reserved =
                GeothermalWorldgenRuntime.reserveGenerated(level.getLevel(), worldSeed, placement);
        if (reserved.isEmpty()) {
            return false;
        }

        GeothermalPendingQueue.Reservation reservation = reserved.orElseThrow();
        ChunkAccess ownerChunk = level.getChunk(chunk.x, chunk.z);
        UUID sourceId = GeothermalSource.fromPlacement(worldSeed, placement).persistenceId();
        List<GeothermalChunkHandoff> durable = ownerChunk.hasData(VolcanoAttachments.GEOTHERMAL_HANDOFFS)
                ? ownerChunk.getData(VolcanoAttachments.GEOTHERMAL_HANDOFFS)
                : List.of();
        Optional<GeothermalChunkHandoff> existingReceipt = durable.stream()
                .filter(existing -> existing.sourceId().equals(sourceId))
                .findFirst();
        if (existingReceipt.isPresent()) {
            GeothermalChunkHandoff existing = existingReceipt.orElseThrow();
            if (existing.worldSeed() != worldSeed || !existing.placement().equals(placement)) {
                GeothermalWorldgenRuntime.cancelGenerated(level.getLevel(), reservation);
                throw new IllegalStateException("conflicting geothermal handoff for source " + sourceId);
            }
        }
        if (existingReceipt.isEmpty()
                && durable.size() >= VolcanoAttachments.MAX_DURABLE_HANDOFFS_PER_CHUNK) {
            GeothermalWorldgenRuntime.cancelGenerated(level.getLevel(), reservation);
            return false;
        }

        try {
            boolean changed = applyCurrentChunk(level, chunk, placement);
            if (!changed) {
                GeothermalWorldgenRuntime.cancelGenerated(level.getLevel(), reservation);
                return false;
            }

            boolean hydrothermalDepositPhysicallyRealized = existingReceipt
                    .map(GeothermalChunkHandoff::hydrothermalDepositPhysicallyRealized)
                    .orElse(false);
            Optional<GeologicalDeposit> projected = DEPOSIT_PROJECTOR.project(worldSeed, placement);
            if (!hydrothermalDepositPhysicallyRealized && projected.isPresent()) {
                GeologicalDeposit projectedDeposit = projected.orElseThrow();
                Optional<HydrothermalOreWorldgenProducer.PreparedPlacement> preparedPlacement =
                        ORE_PRODUCER.prepare(level, chunk, projectedDeposit);
                if (preparedPlacement.isPresent()) {
                    HydrothermalOreWorldgenProducer.PreparedPlacement prepared = preparedPlacement.orElseThrow();
                    hydrothermalDepositPhysicallyRealized = ORE_PRODUCER.apply(level, prepared);
                }
            }

            GeothermalChunkHandoff handoff = GeothermalChunkHandoff.generated(
                    worldSeed, placement, hydrothermalDepositPhysicallyRealized);
            if (existingReceipt.isEmpty()) {
                List<GeothermalChunkHandoff> updated = new ArrayList<>(durable.size() + 1);
                updated.addAll(durable);
                updated.add(handoff);
                ownerChunk.setData(VolcanoAttachments.GEOTHERMAL_HANDOFFS, List.copyOf(updated));
            } else if (!existingReceipt.orElseThrow().equals(handoff)) {
                List<GeothermalChunkHandoff> updated = new ArrayList<>(durable.size());
                for (GeothermalChunkHandoff existing : durable) {
                    updated.add(existing.sourceId().equals(sourceId) ? handoff : existing);
                }
                ownerChunk.setData(VolcanoAttachments.GEOTHERMAL_HANDOFFS, List.copyOf(updated));
            }

            if (!GeothermalWorldgenRuntime.commitGenerated(
                    level.getLevel(), reservation, hydrothermalDepositPhysicallyRealized)) {
                throw new IllegalStateException("lost geothermal worldgen reservation before commit");
            }
            return true;
        } catch (RuntimeException | Error failure) {
            GeothermalWorldgenRuntime.cancelGenerated(level.getLevel(), reservation);
            throw failure;
        }
    }

    static GeothermalFeaturePlacement anchorToSurface(
            GeothermalFeaturePlacement planned,
            BlockPos sampledSurface
    ) {
        return GeothermalSurfaceAnchor.anchor(planned, sampledSurface);
    }

    private static SurfaceProbe probeSurface(WorldGenLevel level, ChunkPos chunk, BlockPos candidate) {
        if (!chunk.equals(new ChunkPos(candidate))) {
            return new SurfaceProbe(level.getMinBuildHeight(), false, false);
        }
        int centerFloorY = floorY(level, candidate.getX(), candidate.getZ());
        if (centerFloorY <= level.getMinBuildHeight()
                || centerFloorY >= level.getMaxBuildHeight() - 2) {
            return new SurfaceProbe(centerFloorY, false, false);
        }

        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int dx = -MAX_FEATURE_RADIUS_BLOCKS; dx <= MAX_FEATURE_RADIUS_BLOCKS; dx++) {
            for (int dz = -MAX_FEATURE_RADIUS_BLOCKS; dz <= MAX_FEATURE_RADIUS_BLOCKS; dz++) {
                if (dx * dx + dz * dz > MAX_FEATURE_RADIUS_BLOCKS * MAX_FEATURE_RADIUS_BLOCKS) {
                    continue;
                }
                int x = candidate.getX() + dx;
                int z = candidate.getZ() + dz;
                if (x < chunk.getMinBlockX() || x > chunk.getMaxBlockX()
                        || z < chunk.getMinBlockZ() || z > chunk.getMaxBlockZ()) {
                    return new SurfaceProbe(centerFloorY, false, false);
                }
                int localFloorY = floorY(level, x, z);
                if (localFloorY <= level.getMinBuildHeight()
                        || Math.abs(localFloorY - centerFloorY) > MAX_FLOOR_DELTA_BLOCKS) {
                    return new SurfaceProbe(centerFloorY, false, false);
                }
                BlockState floor = level.getBlockState(cursor.set(x, localFloorY, z));
                if (!isNaturalFloor(floor)) {
                    return new SurfaceProbe(centerFloorY, false, false);
                }
            }
        }

        return new SurfaceProbe(
                centerFloorY,
                hasShallowWater(level, candidate.getX(), candidate.getZ(), centerFloorY),
                true);
    }

    private static boolean hasShallowWater(WorldGenLevel level, int x, int z, int floorY) {
        int worldSurfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z) - 1;
        int waterDepth = worldSurfaceY - floorY;
        if (waterDepth < 1 || waterDepth > MAX_SHALLOW_WATER_DEPTH_BLOCKS) {
            return false;
        }
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int y = floorY + 1; y <= worldSurfaceY; y++) {
            if (!level.getFluidState(cursor.set(x, y, z)).is(FluidTags.WATER)) {
                return false;
            }
        }
        return true;
    }

    private static boolean applyCurrentChunk(
            WorldGenLevel level,
            ChunkPos chunk,
            GeothermalFeaturePlacement placement
    ) {
        boolean changed = false;
        int radius = placement.radiusBlocks();
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                int distanceSquared = dx * dx + dz * dz;
                if (distanceSquared > radius * radius) {
                    continue;
                }
                int x = placement.center().getX() + dx;
                int z = placement.center().getZ() + dz;
                if (x < chunk.getMinBlockX() || x > chunk.getMaxBlockX()
                        || z < chunk.getMinBlockZ() || z > chunk.getMaxBlockZ()) {
                    continue;
                }
                int y = floorY(level, x, z);
                if (y <= level.getMinBuildHeight() || y >= level.getMaxBuildHeight()) {
                    continue;
                }
                cursor.set(x, y, z);
                BlockState current = level.getBlockState(cursor);
                if (!isNaturalFloor(current)) {
                    continue;
                }
                BlockState replacement = replacementFor(placement.type(), dx, dz, radius);
                if (!current.equals(replacement)) {
                    changed |= level.setBlock(cursor, replacement, 2);
                }
            }
        }
        return changed;
    }

    private static BlockState replacementFor(
            GeothermalFeatureType type,
            int dx,
            int dz,
            int radius
    ) {
        boolean center = dx == 0 && dz == 0;
        int distanceSquared = dx * dx + dz * dz;
        return switch (type) {
            case HOT_SPRING -> center
                    ? Blocks.MAGMA_BLOCK.defaultBlockState()
                    : Blocks.CALCITE.defaultBlockState();
            case GEYSER -> center
                    ? Blocks.MAGMA_BLOCK.defaultBlockState()
                    : distanceSquared <= 1
                            ? Blocks.CALCITE.defaultBlockState()
                            : Blocks.TUFF.defaultBlockState();
            case FUMAROLE -> center
                    ? Blocks.MAGMA_BLOCK.defaultBlockState()
                    : ((dx + dz) & 1) == 0
                            ? Blocks.TUFF.defaultBlockState()
                            : Blocks.BASALT.defaultBlockState();
            case SULFUROUS_VENT -> center
                    ? Blocks.MAGMA_BLOCK.defaultBlockState()
                    : distanceSquared <= Math.max(1, (radius - 1) * (radius - 1))
                            ? Blocks.CALCITE.defaultBlockState()
                            : Blocks.BASALT.defaultBlockState();
            case MUD_POT -> Blocks.MUD.defaultBlockState();
        };
    }

    private static int floorY(WorldGenLevel level, int x, int z) {
        return level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z) - 1;
    }

    private static boolean isNaturalFloor(BlockState state) {
        return state.is(VolcanicHazardTags.NATURAL_TERRAIN)
                || state.is(Blocks.GRASS_BLOCK)
                || state.is(Blocks.PODZOL)
                || state.is(Blocks.MYCELIUM)
                || state.is(Blocks.COARSE_DIRT)
                || state.is(Blocks.ROOTED_DIRT)
                || state.is(Blocks.CLAY)
                || state.is(Blocks.MUD);
    }

    private static final class RuntimeDependencies {
        private static final GeothermalFeaturePlanner PLANNER = GeothermalFeaturePlanner.defaults();
        private static final GeothermalWorldgenResolver RESOLVER =
                GeothermalWorldgenResolver.createDefault(MAGMA_INFLUENCE_RADIUS_BLOCKS);

        private RuntimeDependencies() {
        }
    }

    private record SurfaceProbe(int floorY, boolean hasShallowWater, boolean terrainSuitable) {
    }
}
