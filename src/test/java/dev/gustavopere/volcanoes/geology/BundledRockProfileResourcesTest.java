package dev.gustavopere.volcanoes.geology;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

final class BundledRockProfileResourcesTest {
    @Test
    void verifiedCreateAndBwgRocksHaveBundledOptionalProfiles() throws IOException {
        Map<ResourceLocation, JsonElement> definitions = new LinkedHashMap<>();
        definitions.put(id("volcanoes", "create_scoria"), resource("data/volcanoes/rock_profiles/create_scoria.json"));
        definitions.put(id("volcanoes", "create_limestone"), resource("data/volcanoes/rock_profiles/create_limestone.json"));
        definitions.put(id("volcanoes", "bwg_white_dacite"), resource("data/volcanoes/rock_profiles/bwg_white_dacite.json"));

        RockProfileRegistry registry = RockProfileDataLoader.load(definitions);

        assertEquals(
                RockCategory.IGNEOUS_EXTRUSIVE,
                registry.resolve(id("create", "scoria"), List.of()).category());
        assertEquals(
                RockCategory.SEDIMENTARY,
                registry.resolve(id("create", "limestone"), List.of()).category());
        assertEquals(
                RockCategory.IGNEOUS_EXTRUSIVE,
                registry.resolve(id("biomeswevegone", "white_dacite"), List.of()).category());
    }

    private static JsonElement resource(String path) throws IOException {
        ClassLoader classLoader = BundledRockProfileResourcesTest.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(path);
        assertNotNull(stream, "Missing bundled rock profile resource: " + path);
        try (stream; InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader);
        }
    }

    private static ResourceLocation id(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
