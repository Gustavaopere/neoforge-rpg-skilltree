package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanoWorldgenPlanningTest {
    @Test
    void coarseCandidateCentersAreDeterministicAndPreserveMinimumSpacingAcrossNeighborCells() {
        VolcanoCandidateField field = new VolcanoCandidateField(4_096, 1_024);
        long seed = 0x6A09E667F3BCC909L;

        BlockPos center = field.centerForCell(seed, 17L, -23L);
        assertEquals(center, field.centerForCell(seed, 17L, -23L));
        assertTrue(field.ownsCenter(new ChunkPos(center), center));

        for (long dx = -1L; dx <= 1L; dx++) {
            for (long dz = -1L; dz <= 1L; dz++) {
                if (dx == 0L && dz == 0L) {
                    continue;
                }
                BlockPos neighbor = field.centerForCell(seed, 17L + dx, -23L + dz);
                double distance = Math.hypot(
                        (double) center.getX() - neighbor.getX(),
                        (double) center.getZ() - neighbor.getZ());
                assertTrue(distance >= 2_048.0,
                        () -> "neighboring candidate cells must preserve site spacing, got " + distance);
            }
        }
    }

    @Test
    void nearbyCandidateEnumerationIsStableAndOnlyReturnsCentersWhoseFootprintsCanReachChunk() {
        VolcanoCandidateField field = new VolcanoCandidateField(4_096, 1_024);
        ChunkPos chunk = new ChunkPos(400, -270);

        List<BlockPos> first = field.centersAffectingChunk(12345L, chunk, 320);
        List<BlockPos> second = field.centersAffectingChunk(12345L, chunk, 320);

        assertEquals(first, second);
        assertFalse(first.isEmpty());
        for (BlockPos center : first) {
            assertTrue(VolcanoCandidateField.horizontalDistanceToChunk(center, chunk) <= 320.0);
        }
    }

    @Test
    void knownVolcanicBiomeTagsAreHintsOnlyAndRemainOptional() {
        Set<ResourceLocation> ids = VolcanicTerrainHints.knownBiomeTagIds();

        assertTrue(ids.contains(ResourceLocation.fromNamespaceAndPath("volcanoes", "is_volcanic")));
        assertTrue(ids.contains(ResourceLocation.fromNamespaceAndPath("tfc", "has_stratovolcanoes")));
        assertTrue(ids.contains(ResourceLocation.fromNamespaceAndPath("tfc", "has_cinder_cones")));
        assertTrue(ids.contains(ResourceLocation.fromNamespaceAndPath("tfc", "has_tuff_cones")));
        assertTrue(ids.contains(ResourceLocation.fromNamespaceAndPath("tfc", "has_tuyas")));
        assertTrue(ids.contains(ResourceLocation.fromNamespaceAndPath("tfc", "is_rift")));
        assertTrue(ids.contains(ResourceLocation.fromNamespaceAndPath("tfc", "is_shield_volcano")));

        VolcanoSitePlanner planner = new VolcanoSitePlanner(2_048.0, 0.55);
        var tectonic = new dev.gustavopere.volcanoes.tectonics.TectonicSample(
                1L, 2L,
                dev.gustavopere.volcanoes.tectonics.TectonicContext.CONVERGENT,
                0.0, 0.90, 128.0, 1.0, 0.0);
        assertTrue(planner.placementScore(tectonic, false) > 0.0,
                "volcanic biome tags must not be mandatory for a tectonically valid site");
        assertTrue(planner.placementScore(tectonic, true) > planner.placementScore(tectonic, false));
    }

    @Test
    void terrainProfilesAreBoundedAndMorphologicallyDistinct() {
        VolcanoTerrainProfile strat = VolcanoTerrainProfile.forType(VolcanoType.STRATOVOLCANO);
        VolcanoTerrainProfile shield = VolcanoTerrainProfile.forType(VolcanoType.SHIELD);
        VolcanoTerrainProfile fissure = VolcanoTerrainProfile.forType(VolcanoType.FISSURE);
        VolcanoTerrainProfile caldera = VolcanoTerrainProfile.forType(VolcanoType.CALDERA);

        assertTrue(strat.heightDelta(0.0, 0.0) > shield.heightDelta(0.0, 0.0));
        assertTrue(shield.radiusBlocks() > strat.radiusBlocks());
        assertTrue(fissure.heightDelta(80.0, 0.0) > fissure.heightDelta(0.0, 80.0));
        assertTrue(caldera.heightDelta(caldera.radiusBlocks() * 0.55, 0.0)
                > caldera.heightDelta(0.0, 0.0));

        for (VolcanoType type : VolcanoType.values()) {
            VolcanoTerrainProfile profile = VolcanoTerrainProfile.forType(type);
            assertEquals(0.0, profile.heightDelta(profile.radiusBlocks() + 1.0, 0.0), 0.0);
            assertEquals(0.0, profile.heightDelta(0.0, profile.radiusBlocks() + 1.0), 0.0);
            assertTrue(profile.maxRiseBlocks() <= 96,
                    "new-chunk shaping must remain vertically bounded");
        }
    }
}
