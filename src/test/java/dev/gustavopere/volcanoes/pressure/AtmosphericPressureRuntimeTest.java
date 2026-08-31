package dev.gustavopere.volcanoes.pressure;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

final class AtmosphericPressureRuntimeTest {
    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath("example", path);
    }

    @Test
    void successfulReloadAtomicallyChangesPressureSnapshot() {
        AtmosphericPressureReloadState state = new AtmosphericPressureReloadState(AtmosphericPressureRegistry.empty());
        assertEquals(1.0, state.current().pressureAtm("minecraft:overworld", 63.0), 1.0e-9);

        state.reload(Map.of(id("overworld"), JsonParser.parseString("""
                {
                  "dimensions": ["minecraft:overworld"],
                  "baseline_y": 63.0,
                  "baseline_atm": 0.95,
                  "control_points": [
                    {"y": 63.0, "pressure_atm": 0.95},
                    {"y": 256.0, "pressure_atm": 0.60}
                  ]
                }
                """)));

        assertEquals(0.95, state.current().pressureAtm("minecraft:overworld", 63.0), 1.0e-9);
    }

    @Test
    void failedReloadRetainsPreviousSnapshot() {
        AtmosphericPressureRegistry initial = AtmosphericPressureDataLoader.load(Map.of(id("valid"), JsonParser.parseString("""
                {
                  "dimensions": ["minecraft:overworld"],
                  "baseline_y": 63.0,
                  "baseline_atm": 1.0,
                  "control_points": [{"y": 63.0, "pressure_atm": 1.0}]
                }
                """)));
        AtmosphericPressureReloadState state = new AtmosphericPressureReloadState(initial);

        assertThrows(IllegalArgumentException.class, () -> state.reload(Map.of(id("bad"), JsonParser.parseString("""
                {
                  "dimensions": ["minecraft:overworld"],
                  "baseline_y": 63.0,
                  "baseline_atm": 1.0,
                  "control_points": [
                    {"y": 63.0, "pressure_atm": 1.0},
                    {"y": 128.0, "pressure_atm": 1.2}
                  ]
                }
                """))));

        assertSame(initial, state.current());
    }

    @Test
    void bundledDefinitionsCoverVanillaDimensionsAndOverworldSeaLevel() throws Exception {
        Map<ResourceLocation, JsonElement> definitions = new LinkedHashMap<>();
        definitions.put(ResourceLocation.fromNamespaceAndPath("volcanoes", "overworld"), resource("overworld"));
        definitions.put(ResourceLocation.fromNamespaceAndPath("volcanoes", "nether"), resource("nether"));
        definitions.put(ResourceLocation.fromNamespaceAndPath("volcanoes", "end"), resource("end"));

        AtmosphericPressureRegistry registry = AtmosphericPressureDataLoader.load(definitions);

        assertEquals(1.0, registry.pressureAtm("minecraft:overworld", 63.0), 0.02);
        assertTrue(registry.pressureAtm("minecraft:overworld", 256.0) < registry.pressureAtm("minecraft:overworld", 63.0));
        assertTrue(registry.pressureAtm("minecraft:nether", 64.0) > 0.0);
        assertTrue(registry.pressureAtm("minecraft:the_end", 64.0) > 0.0);
        assertEquals(1.0, registry.pressureAtm("example:custom", 64.0), 1.0e-9);
    }

    @Test
    void registryRejectsMalformedDimensionIdsInsteadOfSilentlyUsingFallback() {
        AtmosphericPressureRegistry registry = AtmosphericPressureRegistry.empty();

        assertThrows(IllegalArgumentException.class, () -> registry.pressureAtm("   ", 64.0));
        assertThrows(IllegalArgumentException.class, () -> registry.profile(""));
        assertThrows(IllegalArgumentException.class,
                () -> registry.pressureAtm(" minecraft:overworld ", 64.0));
        assertThrows(IllegalArgumentException.class,
                () -> registry.profile(" minecraft:the_end "));
    }

    private static JsonElement resource(String name) throws Exception {
        String path = "data/volcanoes/atmospheric_pressure/" + name + ".json";
        var stream = AtmosphericPressureRuntimeTest.class.getClassLoader().getResourceAsStream(path);
        assertNotNull(stream, "missing bundled pressure resource " + path);
        try (var reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader);
        }
    }
}
