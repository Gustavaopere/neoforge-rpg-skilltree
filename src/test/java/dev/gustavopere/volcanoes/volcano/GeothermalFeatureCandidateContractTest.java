package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalFeatureCandidateContractTest {
    @Test
    void owningChunkCanResolveCandidateBeforeEvaluatingEnvironment() {
        long seed = 0x3C6EF372FE94F82BL;
        GeothermalFeaturePlanner planner = GeothermalFeaturePlanner.defaults();
        BlockPos expected = planner.candidateCenter(seed, 4L, -3L);
        ChunkPos owner = new ChunkPos(expected);

        assertEquals(expected, planner.candidateOwnedByChunk(seed, owner).orElseThrow());
    }

    @Test
    void chunkWithoutLatticeCenterHasNoCandidate() {
        long seed = 0xA54FF53A5F1D36F1L;
        GeothermalFeaturePlanner planner = GeothermalFeaturePlanner.defaults();
        BlockPos expected = planner.candidateCenter(seed, 1L, 2L);
        ChunkPos owner = new ChunkPos(expected);
        ChunkPos neighbor = new ChunkPos(owner.x + 1, owner.z);

        assertTrue(planner.candidateOwnedByChunk(seed, neighbor).isEmpty());
    }
}
