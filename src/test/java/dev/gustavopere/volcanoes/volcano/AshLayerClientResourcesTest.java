package dev.gustavopere.volcanoes.volcano;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class AshLayerClientResourcesTest {
    @Test
    void ashLayerHasVisibleModelsForEveryStackHeight() {
        JsonObject variants = resource("assets/volcanoes/blockstates/ash_layer.json")
                .getAsJsonObject("variants");

        for (int layers = 1; layers <= 8; layers++) {
            assertTrue(variants.has("layers=" + layers), "missing ash model for layer count " + layers);
        }

        JsonObject item = resource("assets/volcanoes/models/item/ash_layer.json");
        assertEquals("volcanoes:block/ash_height2", item.get("parent").getAsString());
    }

    @Test
    void ashLayerHasEnglishAndBrazilianPortugueseNames() {
        assertEquals(
                "Ash Layer",
                resource("assets/volcanoes/lang/en_us.json").get("block.volcanoes.ash_layer").getAsString());
        assertEquals(
                "Camada de Cinzas",
                resource("assets/volcanoes/lang/pt_br.json").get("block.volcanoes.ash_layer").getAsString());
    }

    private static JsonObject resource(String path) {
        var stream = AshLayerClientResourcesTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(stream, "missing bundled client resource: " + path);
        try (stream; var reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception exception) {
            throw new AssertionError("failed to read bundled client resource: " + path, exception);
        }
    }
}
