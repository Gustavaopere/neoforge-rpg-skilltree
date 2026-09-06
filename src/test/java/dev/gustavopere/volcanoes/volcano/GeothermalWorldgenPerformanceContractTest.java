package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.level.ChunkPos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalWorldgenPerformanceContractTest {
    @Test
    void magmaInfluenceSearchHasSmallHardCandidateFanout() {
        VolcanoCandidateField field = new VolcanoCandidateField(
                VolcanoWorldgenResolver.DEFAULT_CELL_SIZE_BLOCKS,
                VolcanoWorldgenResolver.DEFAULT_LATTICE_SPACING_BLOCKS);

        int influenceRadius = 2_048;
        for (long seed = 0L; seed < 128L; seed++) {
            for (ChunkPos chunk : new ChunkPos[]{
                    new ChunkPos(0, 0),
                    new ChunkPos(127, -91),
                    new ChunkPos(-512, 384),
                    new ChunkPos(8_192, -8_192)}) {
                int candidates = field.centersAffectingChunk(seed, chunk, influenceRadius).size();
                assertTrue(candidates <= 9,
                        "geothermal magma lookup must remain bounded to at most 3x3 coarse cells");
            }
        }
    }

    @Test
    void configuredSurfaceProfilesRemainSmallCurrentChunkMutations() {
        int maxRadius = 0;
        for (GeothermalFeatureType type : GeothermalFeatureType.values()) {
            maxRadius = Math.max(maxRadius, GeothermalFeatureProfile.defaults(type).radiusBlocks());
        }
        assertTrue(maxRadius <= 4,
                "geothermal surface profiles must remain bounded to the chunk-centered radius contract");
    }
}
