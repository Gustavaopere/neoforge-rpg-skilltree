package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.PlateField;
import dev.gustavopere.volcanoes.tectonics.PlateId;
import dev.gustavopere.volcanoes.tectonics.PlateSample;
import dev.gustavopere.volcanoes.tectonics.PlateVector;
import dev.gustavopere.volcanoes.tectonics.StaticTectonicService;
import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanoWorldgenRuntimeContractTest {
    @Test
    void staticSamplerClassifiesBoundaryWithoutPersistedStress() {
        PlateField convergentField = (seed, x, z) -> new PlateSample(
                new PlateId(11L),
                0.0,
                0.0,
                new PlateVector(1.0, 0.0),
                new PlateId(22L),
                new PlateVector(-1.0, 0.0),
                new PlateVector(1.0, 0.0),
                128.0,
                0.0);

        var sample = new StaticTectonicService(convergentField).sample(123L, 640.0, -320.0);

        assertEquals(TectonicContext.CONVERGENT, sample.context());
        assertEquals(0.0, sample.stress(), 0.0,
                "worldgen tectonics must not read or create persisted stress");
        assertEquals(0.85, sample.volcanicPotential(), 1.0e-12);
        assertEquals(11L, sample.plateId());
        assertEquals(22L, sample.neighborPlateId());
    }

    @Test
    void staticSamplerPromotesInteriorHotspotWithoutDynamicState() {
        PlateField hotspotField = (seed, x, z) -> new PlateSample(
                new PlateId(31L),
                0.0,
                0.0,
                new PlateVector(1.0, 0.0),
                new PlateId(32L),
                new PlateVector(1.0, 0.0),
                new PlateVector(0.0, 1.0),
                8_000.0,
                0.80);

        var sample = new StaticTectonicService(hotspotField).sample(999L, 12_000.0, 8_000.0);

        assertEquals(TectonicContext.HOTSPOT, sample.context());
        assertEquals(0.0, sample.stress(), 0.0);
        assertEquals(0.81, sample.volcanicPotential(), 1.0e-12);
    }

    @Test
    void persistencePolicyRequiresNewOwningChunk() {
        BlockPos center = new BlockPos(1_040, 72, -2_032);
        ChunkPos owner = new ChunkPos(center);
        VolcanoSite site = new VolcanoSite(
                UUID.fromString("91df47cc-8a84-4e10-98a5-c03723ea1482"),
                center,
                VolcanoType.STRATOVOLCANO,
                VolcanoState.DORMANT,
                TectonicContext.CONVERGENT,
                11L,
                22L,
                0.85);

        assertTrue(VolcanoWorldgenPolicy.shouldRegisterSite(true, owner, site));
        assertFalse(VolcanoWorldgenPolicy.shouldRegisterSite(false, owner, site),
                "chunks loaded from disk must never retroactively create volcano sites");
        assertFalse(VolcanoWorldgenPolicy.shouldRegisterSite(
                true,
                new ChunkPos(owner.x + 1, owner.z),
                site),
                "only the chunk owning the deterministic center may persist the site");
    }
}
