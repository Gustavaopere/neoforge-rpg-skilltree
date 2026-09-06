package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class AshLayerRegistrationTest {
    @Test
    void ashLayerUsesConfigurableReplaceableSurfaceTag() throws IOException {
        assertTrue(VolcanicHazardTags.ASH_REPLACEABLE_SURFACES.location().toString()
                .equals("volcanoes:ash_deposition/replaceable_surfaces"));

        String resource = "data/volcanoes/tags/block/ash_deposition/replaceable_surfaces.json";
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(resource)) {
            assertNotNull(stream, resource + " must be packaged");
            String json = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            assertTrue(json.contains("minecraft:dirt"));
            assertTrue(json.contains("minecraft:grass_block"));
            assertTrue(json.contains("#minecraft:base_stone_overworld"));
        }
    }
}
