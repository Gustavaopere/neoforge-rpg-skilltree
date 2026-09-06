package dev.gustavopere.volcanoes.volcano;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * Bounded additive terrain feature for deterministic physical volcano sites.
 *
 * <p>The feature writes only inside the chunk currently being generated. It never consults
 * SavedData and never rewrites an existing world outside normal chunk worldgen.</p>
 */
public final class VolcanoWorldgenFeature extends Feature<NoneFeatureConfiguration> {
    public static final int MAX_FOOTPRINT_RADIUS_BLOCKS = 320;
    private static final VolcanoWorldgenResolver RESOLVER =
            VolcanoWorldgenResolver.createDefault(MAX_FOOTPRINT_RADIUS_BLOCKS);

    public VolcanoWorldgenFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        ChunkPos chunk = new ChunkPos(context.origin());
        VolcanicTerrainHintProvider terrainHints = VolcanoWorldgenTerrainHints.forLevel(level);
        boolean changed = false;
        for (VolcanoSite site : RESOLVER.sitesAffectingChunk(level.getSeed(), chunk, terrainHints)) {
            changed |= shapeCurrentChunk(level, chunk, site);
        }
        return changed;
    }

    private static boolean shapeCurrentChunk(WorldGenLevel level, ChunkPos chunk, VolcanoSite site) {
        VolcanoTerrainProfile profile = VolcanoTerrainProfile.forType(site.type());
        double shapeMultiplier = localShapeMultiplier(level, chunk);
        boolean changed = false;
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int x = chunk.getMinBlockX(); x <= chunk.getMaxBlockX(); x++) {
            for (int z = chunk.getMinBlockZ(); z <= chunk.getMaxBlockZ(); z++) {
                double delta = profile.heightDelta(
                        (double) x - site.center().getX(),
                        (double) z - site.center().getZ()) * shapeMultiplier;
                int rise = (int) Math.floor(Math.max(0.0, delta));
                if (rise <= 0) {
                    continue;
                }

                int surfaceY = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z) - 1;
                if (surfaceY < level.getMinBuildHeight()) {
                    continue;
                }
                int sampleY = Math.max(level.getMinBuildHeight(), surfaceY - 4);
                BlockState material = level.getBlockState(cursor.set(x, sampleY, z));
                if (material.isAir() || !material.getFluidState().isEmpty()) {
                    material = Blocks.STONE.defaultBlockState();
                }

                int targetY = Math.min(level.getMaxBuildHeight() - 1, surfaceY + rise);
                for (int y = surfaceY + 1; y <= targetY; y++) {
                    cursor.set(x, y, z);
                    BlockState current = level.getBlockState(cursor);
                    if (!current.isAir() && current.getFluidState().isEmpty()) {
                        break;
                    }
                    level.setBlock(cursor, material, 2);
                    changed = true;
                }
            }
        }
        return changed;
    }

    private static double localShapeMultiplier(WorldGenLevel level, ChunkPos chunk) {
        int sampleX = chunk.getMinBlockX() + 8;
        int sampleZ = chunk.getMinBlockZ() + 8;
        int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, sampleX, sampleZ) - 1;
        int sampleY = Math.max(
                level.getMinBuildHeight(),
                Math.min(level.getMaxBuildHeight() - 1, surfaceY));
        boolean volcanicTerrainHint = VolcanicTerrainHints.isVolcanic(
                level.getBiome(new BlockPos(sampleX, sampleY, sampleZ)));
        return VolcanicTerrainHints.shapeMultiplier(volcanicTerrainHint);
    }
}
