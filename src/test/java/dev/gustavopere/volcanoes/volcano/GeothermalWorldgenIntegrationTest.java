package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.level.levelgen.feature.Feature;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalWorldgenIntegrationTest {
    @Test
    void defaultResolverCanBeConstructedIndependently() {
        assertNotNull(GeothermalWorldgenResolver.createDefault(2_048));
    }

    @Test
    void featureUsesBoundedOwnerChunkMutationWithoutBootstrappingNeoForgeInPureJUnit() {
        assertTrue(Feature.class.isAssignableFrom(GeothermalWorldgenFeature.class),
                "worldgen implementation must remain a vanilla Feature; full bootstrap is verified by server smoke");
        int maximumProfileRadius = Arrays.stream(GeothermalFeatureType.values())
                .map(GeothermalFeatureProfile::defaults)
                .mapToInt(GeothermalFeatureProfile::radiusBlocks)
                .max()
                .orElseThrow();
        assertEquals(4, maximumProfileRadius,
                "all default geothermal mutations must fit the chunk-centered radius contract");
    }

    @Test
    void datapackAddsGeothermalFeatureOnlyToOverworldAfterVolcanoTerrainShaping() throws IOException {
        String configured = resource("data/volcanoes/worldgen/configured_feature/geothermal_features.json");
        String placed = resource("data/volcanoes/worldgen/placed_feature/geothermal_features.json");
        String modifier = resource("data/volcanoes/neoforge/biome_modifier/add_geothermal_features.json");

        assertTrue(configured.contains("\"type\": \"volcanoes:geothermal_features\""));
        assertTrue(placed.contains("\"feature\": \"volcanoes:geothermal_features\""));
        assertTrue(modifier.contains("\"type\": \"neoforge:add_features\""));
        assertTrue(modifier.contains("\"biomes\": \"#c:is_overworld\""));
        assertTrue(modifier.contains("\"features\": \"volcanoes:geothermal_features\""));
        assertTrue(modifier.contains("\"step\": \"surface_structures\""));
        assertFalse(modifier.contains("tfc:"),
                "geothermal worldgen must remain independent from the TFC generator");
    }

    private static String resource(String path) throws IOException {
        try (InputStream stream = GeothermalWorldgenIntegrationTest.class.getClassLoader().getResourceAsStream(path)) {
            assertNotNull(stream, "missing classpath resource: " + path);
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
