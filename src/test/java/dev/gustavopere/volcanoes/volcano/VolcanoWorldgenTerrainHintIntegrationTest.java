package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanoWorldgenTerrainHintIntegrationTest {
    @Test
    void optionalVolcanicBiomeHintCanPromoteAnOtherwiseSubthresholdCandidate() {
        long seed = 91L;
        VolcanoCandidateField field = new VolcanoCandidateField(1_024, 256);
        BlockPos center = field.centerForCell(seed, 0, 0);
        ChunkPos owner = new ChunkPos(center);
        TectonicService marginalInterior = (worldSeed, x, z) -> new TectonicSample(
                11L,
                11L,
                TectonicContext.INTERIOR,
                0.0,
                0.70,
                8_000.0,
                1.0,
                0.0);
        VolcanoWorldgenResolver resolver = new VolcanoWorldgenResolver(
                field,
                marginalInterior,
                new VolcanoSitePlanner(512.0, 0.55),
                320);

        assertTrue(resolver.siteOwnedByChunk(seed, owner).isEmpty(),
                "marginal tectonics should stay below admission without a terrain hint");
        assertTrue(resolver.siteOwnedByChunk(seed, owner, ignored -> true).isPresent(),
                "a tagged volcanic biome must act as a positive site hint rather than shape-only decoration");
    }
}
