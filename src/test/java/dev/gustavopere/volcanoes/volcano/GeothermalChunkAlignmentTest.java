package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalChunkAlignmentTest {
    @Test
    void candidateCentersStayAtChunkCentersSoProfilesNeverCrossChunkBoundaries() {
        GeothermalFeaturePlanner planner = GeothermalFeaturePlanner.defaults();
        int maximumRadius = java.util.Arrays.stream(GeothermalFeatureType.values())
                .map(GeothermalFeatureProfile::defaults)
                .mapToInt(GeothermalFeatureProfile::radiusBlocks)
                .max()
                .orElseThrow();
        assertTrue(maximumRadius <= 7,
                "chunk-centered candidates can contain only profiles with radius at most seven blocks");

        long[] seeds = {0L, 1L, -1L, 0x6A09E667F3BCC909L, Long.MAX_VALUE};
        for (long seed : seeds) {
            for (long cellX = -4L; cellX <= 4L; cellX++) {
                for (long cellZ = -4L; cellZ <= 4L; cellZ++) {
                    var center = planner.candidateCenter(seed, cellX, cellZ);
                    assertEquals(8, Math.floorMod(center.getX(), 16));
                    assertEquals(8, Math.floorMod(center.getZ(), 16));
                }
            }
        }
    }
}
