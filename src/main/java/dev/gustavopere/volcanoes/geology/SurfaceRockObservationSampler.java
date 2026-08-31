package dev.gustavopere.volcanoes.geology;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Bounded sampler for shallow, observed geology.
 *
 * <p>The pattern is fixed at 27 positions: a 3x3 horizontal grid with four-block spacing, sampled
 * four, eight and twelve blocks below the supplied terrain surface. It never scans a column or a
 * chunk. The world-facing adapter checks chunk availability before reading block state, so geology
 * queries do not force neighboring chunks to load.</p>
 */
public final class SurfaceRockObservationSampler {
    public static final int SAMPLE_COUNT = 27;

    private static final int[] HORIZONTAL_OFFSETS = {-4, 0, 4};
    private static final int[] DEPTHS = {4, 8, 12};

    private SurfaceRockObservationSampler() {
    }

    /**
     * Samples the fixed pattern using an already-resolved profile function.
     * This pure entry point exists so sampling geometry is testable without bootstrapping registries.
     */
    public static List<RockProfile> sampleResolved(
            BlockPos origin,
            int surfaceY,
            Function<BlockPos, RockProfile> profileResolver
    ) {
        Objects.requireNonNull(origin, "origin");
        Objects.requireNonNull(profileResolver, "profileResolver");

        List<RockProfile> observations = new ArrayList<>(SAMPLE_COUNT);
        for (int depth : DEPTHS) {
            int y = surfaceY - depth;
            for (int dx : HORIZONTAL_OFFSETS) {
                for (int dz : HORIZONTAL_OFFSETS) {
                    BlockPos samplePos = new BlockPos(origin.getX() + dx, y, origin.getZ() + dz);
                    observations.add(Objects.requireNonNull(
                            profileResolver.apply(samplePos),
                            "profileResolver returned null"));
                }
            }
        }
        return List.copyOf(observations);
    }

    /**
     * Samples actual blocks from already-loaded chunks and resolves them through RockProfileRuntime.
     * Unloaded/out-of-height samples become GENERIC and therefore cannot create a false surface bias.
     */
    public static List<RockProfile> sampleLoaded(LevelReader level, BlockPos origin, int surfaceY) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(origin, "origin");

        return sampleResolved(origin, surfaceY, samplePos -> {
            if (samplePos.getY() < level.getMinBuildHeight()
                    || samplePos.getY() >= level.getMaxBuildHeight()
                    || !level.hasChunkAt(samplePos)) {
                return RockProfile.GENERIC_STONE;
            }
            return RockProfileRuntime.resolve(level.getBlockState(samplePos));
        });
    }
}
