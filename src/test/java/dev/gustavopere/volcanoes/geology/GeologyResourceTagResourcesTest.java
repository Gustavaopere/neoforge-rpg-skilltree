package dev.gustavopere.volcanoes.geology;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

final class GeologyResourceTagResourcesTest {
    @Test
    void metallicResourceTagAggregatesCanonicalCommonMetalOres() {
        JsonObject json = resource("data/volcanoes/tags/block/resources/metallic.json");

        assertFalse(json.get("replace").getAsBoolean());
        assertEquals(
                Set.of("#c:ores/copper", "#c:ores/iron", "#c:ores/gold"),
                values(json));
    }

    @Test
    void mineralResourceTagAggregatesAllCommonOres() {
        JsonObject json = resource("data/volcanoes/tags/block/resources/mineral.json");

        assertFalse(json.get("replace").getAsBoolean());
        assertEquals(Set.of("#c:ores"), values(json));
    }

    private static JsonObject resource(String path) {
        var stream = GeologyResourceTagResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(stream, "missing bundled tag resource: " + path);
        try (stream; var reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception exception) {
            throw new AssertionError("failed to read bundled tag resource: " + path, exception);
        }
    }

    private static Set<String> values(JsonObject json) {
        JsonArray values = json.getAsJsonArray("values");
        return StreamSupport.stream(values.spliterator(), false)
                .map(element -> element.getAsString())
                .collect(Collectors.toUnmodifiableSet());
    }
}
