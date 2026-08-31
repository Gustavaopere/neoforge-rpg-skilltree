package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanoWorldgenIntegrationTest {
    @Test
    void resolverUsesOneCanonicalSiteAcrossOwningAndAffectedChunks() {
        long seed = 91L;
        VolcanoCandidateField field = new VolcanoCandidateField(4_096, 1_024);
        TectonicService tectonics = (worldSeed, x, z) -> new dev.gustavopere.volcanoes.tectonics.TectonicSample(
                11L,
                22L,
                TectonicContext.CONVERGENT,
                0.0,
                0.90,
                128.0,
                1.0,
                0.0);
        VolcanoWorldgenResolver resolver = new VolcanoWorldgenResolver(
                field,
                tectonics,
                new VolcanoSitePlanner(2_048.0, 0.55),
                320);

        BlockPos center = field.centerForCell(seed, 3L, -4L);
        ChunkPos owner = new ChunkPos(center);
        VolcanoSite owned = resolver.siteOwnedByChunk(seed, owner).orElseThrow();
        ChunkPos affectedNeighbor = new ChunkPos(owner.x + 10, owner.z);

        assertEquals(center, owned.center());
        assertTrue(resolver.sitesAffectingChunk(seed, affectedNeighbor).contains(owned),
                "neighboring chunks inside the bounded footprint must resolve the same site identity");
    }

    @Test
    void deferredRegistrationQueueWaitsForNextTickBoundaryAndIsBounded() {
        VolcanoRegistrationQueue<String> queue = new VolcanoRegistrationQueue<>(2);
        queue.enqueue(10L, "first");
        queue.enqueue(10L, "second");
        queue.enqueue(10L, "third");

        assertTrue(queue.drainReady(10L).isEmpty());
        assertTrue(queue.drainReady(11L).isEmpty(),
                "ChunkEvent.Load work must not touch the level in the same game tick");
        assertEquals(List.of("first", "second"), queue.drainReady(12L));
        assertEquals(List.of("third"), queue.drainReady(12L));
        assertTrue(queue.isEmpty());
    }

    @Test
    void featureAndDatapackResourcesWireOnlyIntoOverworldLocalModifications() throws IOException {
        assertNotNull(VolcanoWorldgenResolver.createDefault(VolcanoWorldgenFeature.MAX_FOOTPRINT_RADIUS_BLOCKS));
        assertTrue(Feature.class.isAssignableFrom(VolcanoWorldgenFeature.class),
                "worldgen implementation must remain a vanilla Feature; construction is integration-tested by server smoke");
        assertEquals(320, VolcanoWorldgenFeature.MAX_FOOTPRINT_RADIUS_BLOCKS);

        String configured = resource("data/volcanoes/worldgen/configured_feature/volcano_sites.json");
        String placed = resource("data/volcanoes/worldgen/placed_feature/volcano_sites.json");
        String modifier = resource("data/volcanoes/neoforge/biome_modifier/add_volcano_sites.json");

        assertTrue(configured.contains("\"type\": \"volcanoes:volcano_sites\""));
        assertTrue(placed.contains("\"feature\": \"volcanoes:volcano_sites\""));
        assertTrue(modifier.contains("\"type\": \"neoforge:add_features\""));
        assertTrue(modifier.contains("\"biomes\": \"#c:is_overworld\""));
        assertTrue(modifier.contains("\"features\": \"volcanoes:volcano_sites\""));
        assertTrue(modifier.contains("\"step\": \"local_modifications\""));
        assertFalse(modifier.contains("tfc:overworld"),
                "Volcanoes must never replace or require the TFC world generator");
    }

    private static String resource(String path) throws IOException {
        try (InputStream stream = VolcanoWorldgenIntegrationTest.class.getClassLoader().getResourceAsStream(path)) {
            assertNotNull(stream, "missing classpath resource: " + path);
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
