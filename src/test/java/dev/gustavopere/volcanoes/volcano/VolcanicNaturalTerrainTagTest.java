package dev.gustavopere.volcanoes.volcano;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicNaturalTerrainTagTest {
    @Test
    void bombsAndFlowsShareOneDatapackExtensibleNaturalTerrainTag() {
        assertEquals(
                ResourceLocation.fromNamespaceAndPath("volcanoes", "hazards/natural_terrain"),
                VolcanicHazardTags.NATURAL_TERRAIN.location());

        JsonObject json = resource("data/volcanoes/tags/block/hazards/natural_terrain.json");
        assertFalse(json.get("replace").getAsBoolean());
        Set<String> values = values(json);
        assertTrue(values.contains("#minecraft:base_stone_overworld"));
        assertTrue(values.contains("minecraft:dirt"));
        assertTrue(values.contains("minecraft:sand"));
        assertTrue(values.contains("minecraft:gravel"));
    }

    private static JsonObject resource(String path) {
        var stream = VolcanicNaturalTerrainTagTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(stream, "missing bundled hazard tag: " + path);
        try (stream; var reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception exception) {
            throw new AssertionError("failed to read bundled hazard tag: " + path, exception);
        }
    }

    private static Set<String> values(JsonObject json) {
        JsonArray values = json.getAsJsonArray("values");
        return StreamSupport.stream(values.spliterator(), false)
                .map(element -> element.getAsString())
                .collect(Collectors.toUnmodifiableSet());
    }
}
