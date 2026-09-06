package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.performance.PerformanceProfiler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Objects;

/** Applies bounded ash deposition only to already-loaded columns. */
public final class AshDepositionWorldEffects {
    private static final AshDepositionPlanner PLANNER = new AshDepositionPlanner();

    private AshDepositionWorldEffects() {
    }

    public static int apply(
            ServerLevel level,
            AshPlumeEmission emission,
            int blockWork,
            long gameTick,
            VolcanicProtectionService protection
    ) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(emission, "emission");
        Objects.requireNonNull(protection, "protection");
        if (blockWork < 0) {
            throw new IllegalArgumentException("blockWork must be non-negative");
        }
        if (blockWork == 0 || !emission.active()) {
            return 0;
        }

        EruptionScheduler.WorkGrant grant = new EruptionScheduler.WorkGrant(blockWork, 0, 0, 0, 0, 0);
        int changed = 0;
        for (BlockPos column : PLANNER.candidates(emission, grant, gameTick)) {
            if (!level.hasChunkAt(column)) {
                continue;
            }
            int topY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, column.getX(), column.getZ());
            BlockPos target = new BlockPos(column.getX(), topY, column.getZ());
            BlockPos surface = target.below();
            if (protection.isProtected(level, surface) || protection.isProtected(level, target)) {
                continue;
            }

            BlockState surfaceState = level.getBlockState(surface);
            int existingLayers = surfaceState.is(VolcanoBlocks.ASH_LAYER.get())
                    ? surfaceState.getValue(SnowLayerBlock.LAYERS)
                    : 0;
            boolean taggedSurface = existingLayers == 0 && surfaceState.is(VolcanicHazardTags.ASH_REPLACEABLE_SURFACES);
            boolean hasBlockEntity = level.getBlockEntity(surface) != null || level.getBlockEntity(target) != null;
            BlockState targetState = level.getBlockState(target);
            boolean targetFreeOrReplaceable = targetState.isAir() || targetState.canBeReplaced();

            AshLayerPlacement.Action action = AshLayerPlacement.decide(
                    true,
                    taggedSurface,
                    false,
                    hasBlockEntity,
                    targetFreeOrReplaceable,
                    existingLayers);
            switch (action) {
                case NONE -> {
                }
                case PLACE_FIRST_LAYER -> {
                    if (level.setBlock(target, VolcanoBlocks.ASH_LAYER.get().defaultBlockState(), 3)) {
                        changed++;
                    }
                }
                case INCREMENT_EXISTING -> {
                    BlockState thicker = surfaceState.setValue(SnowLayerBlock.LAYERS, existingLayers + 1);
                    if (level.setBlock(surface, thicker, 3)) {
                        changed++;
                    }
                }
            }
        }

        PerformanceProfiler.recordBlockMutations(changed);
        if (changed > 0) {
            level.sendParticles(
                    ParticleTypes.ASH,
                    emission.source().getX() + 0.5,
                    emission.source().getY() + 2.0,
                    emission.source().getZ() + 0.5,
                    Math.min(24, 4 + changed),
                    Math.min(8.0, emission.plumeRadiusBlocks() * 0.08),
                    2.0,
                    Math.min(8.0, emission.plumeRadiusBlocks() * 0.08),
                    0.02);
        }
        return changed;
    }
}
