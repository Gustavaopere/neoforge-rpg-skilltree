package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.performance.PerformanceProfiler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

/** Executes at most one natural-terrain mutation per supplied terrain-work token. */
public final class VolcanicTerrainWorldEffects {
    private static final VolcanicBombImpactPolicy BOMB_POLICY = VolcanicBombImpactPolicy.safeDefaults();
    private static final PyroclasticTerrainPolicy FLOW_POLICY = PyroclasticTerrainPolicy.safeDefaults();

    private VolcanicTerrainWorldEffects() {
    }

    public static int applyBombImpact(
            ServerLevel level,
            BlockPos impactPos,
            int blockWork,
            VolcanicProtectionService protection
    ) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(impactPos, "impactPos");
        Objects.requireNonNull(protection, "protection");
        requireNonNegativeWork(blockWork);
        if (blockWork == 0 || !level.hasChunkAt(impactPos)) {
            return 0;
        }
        BlockState state = level.getBlockState(impactPos);
        if (!BOMB_POLICY.canMutate(
                protection.allowsTerrainMutation(),
                true,
                state.is(VolcanicHazardTags.NATURAL_TERRAIN),
                protection.isProtected(level, impactPos),
                level.getBlockEntity(impactPos) != null)) {
            return 0;
        }
        int changed = level.setBlock(impactPos, Blocks.AIR.defaultBlockState(), 3) ? 1 : 0;
        PerformanceProfiler.recordBlockMutations(changed);
        return changed;
    }

    public static int applyPyroclasticSurface(
            ServerLevel level,
            BlockPos surfacePos,
            int blockWork,
            VolcanicProtectionService protection
    ) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(surfacePos, "surfacePos");
        Objects.requireNonNull(protection, "protection");
        requireNonNegativeWork(blockWork);
        if (blockWork == 0 || !level.hasChunkAt(surfacePos)) {
            return 0;
        }
        BlockState state = level.getBlockState(surfacePos);
        if (!FLOW_POLICY.canMutate(
                protection.allowsTerrainMutation(),
                true,
                state.is(VolcanicHazardTags.NATURAL_TERRAIN),
                protection.isProtected(level, surfacePos),
                level.getBlockEntity(surfacePos) != null)) {
            return 0;
        }
        BlockState replacement = scorchedReplacement(state);
        if (state.equals(replacement)) {
            return 0;
        }
        int changed = level.setBlock(surfacePos, replacement, 3) ? 1 : 0;
        PerformanceProfiler.recordBlockMutations(changed);
        return changed;
    }

    private static BlockState scorchedReplacement(BlockState state) {
        if (state.is(Blocks.DIRT)
                || state.is(Blocks.GRASS_BLOCK)
                || state.is(Blocks.PODZOL)
                || state.is(Blocks.MYCELIUM)
                || state.is(Blocks.COARSE_DIRT)) {
            return Blocks.COARSE_DIRT.defaultBlockState();
        }
        return Blocks.TUFF.defaultBlockState();
    }

    private static void requireNonNegativeWork(int blockWork) {
        if (blockWork < 0) {
            throw new IllegalArgumentException("blockWork must be non-negative");
        }
    }
}
