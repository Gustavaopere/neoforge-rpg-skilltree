package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelReader;

import java.util.Objects;

/** Builds the shared level-backed biome hint authority used by generation and persistence. */
public final class VolcanoWorldgenTerrainHints {
    private VolcanoWorldgenTerrainHints() {
    }

    public static VolcanicTerrainHintProvider forLevel(LevelReader level) {
        Objects.requireNonNull(level, "level");
        int sampleY = Math.max(
                level.getMinBuildHeight(),
                Math.min(level.getMaxBuildHeight() - 1, level.getSeaLevel()));
        return center -> {
            Objects.requireNonNull(center, "center");
            BlockPos sample = new BlockPos(center.getX(), sampleY, center.getZ());
            if (level instanceof WorldGenRegion worldGenRegion) {
                return VolcanicTerrainHints.isVolcanic(worldGenRegion.getUncachedNoiseBiome(
                        QuartPos.fromBlock(sample.getX()),
                        QuartPos.fromBlock(sample.getY()),
                        QuartPos.fromBlock(sample.getZ())));
            }
            if (!level.hasChunkAt(sample)) {
                return false;
            }
            return VolcanicTerrainHints.isVolcanic(level.getBiome(sample));
        };
    }
}
