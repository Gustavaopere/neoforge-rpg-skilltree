package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanoWorldgenRuntimeTest {
    @Test
    void featureRegistryContractExistsWithoutBootstrappingVanillaRegistries() throws Exception {
        Class<?> registryClass = Class.forName(
                "dev.gustavopere.volcanoes.volcano.VolcanoWorldgenRegistry",
                false,
                VolcanoWorldgenRuntimeTest.class.getClassLoader());
        assertNotNull(registryClass.getDeclaredField("VOLCANO_SITES_ID"));
        assertNotNull(registryClass.getDeclaredField("FEATURES"));
        assertNotNull(registryClass.getDeclaredField("VOLCANO_SITES"));
    }

    @Test
    void onlyNewOverworldChunksAreEligibleForDeferredPersistence() {
        assertTrue(VolcanoWorldgenRuntime.shouldQueueSiteRegistration(true, Level.OVERWORLD));
        assertFalse(VolcanoWorldgenRuntime.shouldQueueSiteRegistration(false, Level.OVERWORLD));
        assertFalse(VolcanoWorldgenRuntime.shouldQueueSiteRegistration(true, Level.NETHER));
        assertFalse(VolcanoWorldgenRuntime.shouldQueueSiteRegistration(true, Level.END));
    }

    @Test
    void ownedSitePersistenceIsIdempotentAndSpacingAware() {
        long seed = 314159L;
        VolcanoCandidateField field = new VolcanoCandidateField(4_096, 1_024);
        TectonicService tectonics = (worldSeed, x, z) -> new dev.gustavopere.volcanoes.tectonics.TectonicSample(
                41L,
                42L,
                TectonicContext.CONVERGENT,
                0.0,
                0.92,
                128.0,
                1.0,
                0.0);
        VolcanoWorldgenResolver resolver = new VolcanoWorldgenResolver(
                field,
                tectonics,
                new VolcanoSitePlanner(2_048.0, 0.55),
                320);
        BlockPos center = field.centerForCell(seed, 2L, 3L);
        ChunkPos owner = new ChunkPos(center);

        VolcanoSavedData data = new VolcanoSavedData();
        assertTrue(VolcanoWorldgenRuntime.persistOwnedSite(data, resolver, seed, owner));
        assertFalse(VolcanoWorldgenRuntime.persistOwnedSite(data, resolver, seed, owner));
        assertEquals(1, data.size());

        VolcanoSavedData blocked = new VolcanoSavedData();
        blocked.register(new VolcanoSite(
                UUID.fromString("00000000-0000-0000-0000-000000000123"),
                center.offset(128, 0, 0),
                VolcanoType.SHIELD,
                VolcanoState.DORMANT,
                TectonicContext.HOTSPOT,
                90L,
                91L,
                0.80));
        assertFalse(VolcanoWorldgenRuntime.persistOwnedSite(blocked, resolver, seed, owner),
                "persisted spacing must reject a second physical site even though worldgen resolution is stateless");
        assertEquals(1, blocked.size());
    }
}
