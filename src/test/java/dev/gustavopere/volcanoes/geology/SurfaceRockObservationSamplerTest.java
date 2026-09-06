package dev.gustavopere.volcanoes.geology;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SurfaceRockObservationSamplerTest {
    @Test
    void boundedPatternSamplesExactlyTwentySevenNearbyPositions() {
        BlockPos origin = new BlockPos(100, 70, -200);
        int surfaceY = 96;
        RockProfile observed = new RockProfile(
                "observed:test",
                RockCategory.IGNEOUS_EXTRUSIVE,
                0.7,
                0.2,
                2.0,
                0.8,
                0.7,
                0.3);
        List<BlockPos> visited = new ArrayList<>();

        List<RockProfile> samples = SurfaceRockObservationSampler.sampleResolved(
                origin,
                surfaceY,
                pos -> {
                    visited.add(pos.immutable());
                    return observed;
                });

        assertEquals(27, samples.size());
        assertEquals(27, visited.size());
        assertEquals(27, new HashSet<>(visited).size());
        assertTrue(samples.stream().allMatch(observed::equals));

        Set<Integer> expectedY = Set.of(surfaceY - 4, surfaceY - 8, surfaceY - 12);
        for (BlockPos pos : visited) {
            assertTrue(Math.abs(pos.getX() - origin.getX()) <= 4);
            assertTrue(Math.abs(pos.getZ() - origin.getZ()) <= 4);
            assertTrue(expectedY.contains(pos.getY()));
        }
    }

    @Test
    void samplingPatternIsDeterministicAndIgnoresOriginY() {
        RockProfile observed = RockProfile.GENERIC_STONE;
        List<BlockPos> first = new ArrayList<>();
        List<BlockPos> second = new ArrayList<>();

        SurfaceRockObservationSampler.sampleResolved(
                new BlockPos(-33, -60, 71),
                120,
                pos -> {
                    first.add(pos.immutable());
                    return observed;
                });
        SurfaceRockObservationSampler.sampleResolved(
                new BlockPos(-33, 300, 71),
                120,
                pos -> {
                    second.add(pos.immutable());
                    return observed;
                });

        assertEquals(first, second);
    }
}
