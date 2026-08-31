package dev.gustavopere.volcanoes.pressure;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class AtmosphericPressureDataLoaderTest {
    private static final ResourceLocation PROFILE_ID =
            ResourceLocation.fromNamespaceAndPath("example", "thin_air");

    @Test
    void datapackProfileBindsDimensionsAndPreservesConfiguredBaseline() {
        AtmosphericPressureRegistry registry = AtmosphericPressureDataLoader.load(Map.of(
                PROFILE_ID,
                json("""
                        {
                          "dimensions": ["example:sky"],
                          "baseline_y": 63.0,
                          "baseline_atm": 1.0,
                          "control_points": [
                            {"y": -64.0, "pressure_atm": 1.15},
                            {"y": 63.0, "pressure_atm": 1.0},
                            {"y": 128.0, "pressure_atm": 0.8},
                            {"y": 256.0, "pressure_atm": 0.5}
                          ]
                        }
                        """)));

        AtmosphericPressureProfile profile = registry.profile("example:sky");
        assertEquals(1.0, profile.baselineAtm(), 1.0e-9);
        assertEquals(63.0, profile.baselineY(), 1.0e-9);
        assertEquals(1.0, registry.pressureAtm("example:sky", 63.0), 1.0e-9);
        assertEquals(0.9, registry.pressureAtm("example:sky", 95.5), 1.0e-9);
    }

    @Test
    void dimensionsWithoutDefinitionUseNeutralSafeFallback() {
        AtmosphericPressureRegistry registry = AtmosphericPressureDataLoader.load(Map.of());

        assertEquals(1.0, registry.pressureAtm("minecraft:the_nether", 32.0), 1.0e-9);
        assertEquals(1.0, registry.pressureAtm("minecraft:the_end", 128.0), 1.0e-9);
        assertEquals(1.0, registry.pressureAtm("example:custom_dimension", 512.0), 1.0e-9);
    }

    @Test
    void malformedDefinitionsAreRejectedWithoutInventingAUsableCurve() {
        assertThrows(IllegalArgumentException.class, () -> AtmosphericPressureDataLoader.load(Map.of(
                PROFILE_ID,
                json("""
                        {
                          "dimensions": ["example:sky"],
                          "baseline_y": 63.0,
                          "baseline_atm": 1.0,
                          "control_points": [
                            {"y": 63.0, "pressure_atm": 1.0},
                            {"y": 128.0, "pressure_atm": 1.2}
                          ]
                        }
                        """))));

        assertThrows(IllegalArgumentException.class, () -> AtmosphericPressureDataLoader.load(Map.of(
                PROFILE_ID,
                json("""
                        {
                          "dimensions": ["example:sky"],
                          "baseline_y": 63.0,
                          "baseline_atm": 0.9,
                          "control_points": [
                            {"y": 63.0, "pressure_atm": 1.0},
                            {"y": 128.0, "pressure_atm": 0.8}
                          ]
                        }
                        """))));
    }

    @Test
    void unknownProfileOrControlPointFieldsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> AtmosphericPressureDataLoader.load(Map.of(
                PROFILE_ID,
                json("""
                        {
                          "dimensions": ["example:sky"],
                          "baseline_y": 63.0,
                          "baseline_atm": 1.0,
                          "baseline_atmm": 1.0,
                          "control_points": [
                            {"y": 63.0, "pressure_atm": 1.0}
                          ]
                        }
                        """))));

        assertThrows(IllegalArgumentException.class, () -> AtmosphericPressureDataLoader.load(Map.of(
                PROFILE_ID,
                json("""
                        {
                          "dimensions": ["example:sky"],
                          "baseline_y": 63.0,
                          "baseline_atm": 1.0,
                          "control_points": [
                            {"y": 63.0, "pressure_atm": 1.0, "pressure_kpa": 101.325}
                          ]
                        }
                        """))));
    }

    @Test
    void numericFieldsAndDimensionIdsRequireCorrectJsonPrimitiveTypes() {
        assertThrows(IllegalArgumentException.class, () -> AtmosphericPressureDataLoader.load(Map.of(
                PROFILE_ID,
                json("""
                        {
                          "dimensions": ["example:sky"],
                          "baseline_y": "63.0",
                          "baseline_atm": 1.0,
                          "control_points": [
                            {"y": 63.0, "pressure_atm": 1.0}
                          ]
                        }
                        """))));

        assertThrows(IllegalArgumentException.class, () -> AtmosphericPressureDataLoader.load(Map.of(
                PROFILE_ID,
                json("""
                        {
                          "dimensions": [123],
                          "baseline_y": 63.0,
                          "baseline_atm": 1.0,
                          "control_points": [
                            {"y": 63.0, "pressure_atm": 1.0}
                          ]
                        }
                        """))));
    }

    private static JsonElement json(String value) {
        return JsonParser.parseString(value);
    }
}
