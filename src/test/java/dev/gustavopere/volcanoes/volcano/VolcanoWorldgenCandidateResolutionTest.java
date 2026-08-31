package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanoWorldgenCandidateResolutionTest {
    @Test
    void candidateResolutionIsIndependentOfPersistedRegionState() {
        VolcanoSitePlanner planner = new VolcanoSitePlanner(2_048.0, 0.55);
        BlockPos center = new BlockPos(4_096, 0, -8_192);
        TectonicSample tectonic = new TectonicSample(
                101L,
                202L,
                TectonicContext.CONVERGENT,
                0.0,
                0.90,
                128.0,
                1.0,
                0.0);

        VolcanoSite candidate = planner.candidate(77L, center, tectonic, false).orElseThrow();
        VolcanoSavedData persisted = new VolcanoSavedData();
        assertTrue(persisted.register(candidate));

        assertEquals(candidate, planner.candidate(77L, center, tectonic, false).orElseThrow(),
                "worldgen candidate identity must not depend on SavedData contents");
        assertFalse(planner.plan(77L, center, tectonic, false, persisted).isPresent(),
                "the persistence-aware path must still enforce minimum spacing");
    }

    @Test
    void candidateResolutionStillRejectsSubthresholdTectonics() {
        VolcanoSitePlanner planner = new VolcanoSitePlanner(2_048.0, 0.55);
        TectonicSample quietInterior = new TectonicSample(
                1L,
                2L,
                TectonicContext.INTERIOR,
                0.0,
                0.05,
                8_000.0,
                1.0,
                0.0);

        assertTrue(planner.candidate(5L, BlockPos.ZERO, quietInterior, false).isEmpty());
    }
}
