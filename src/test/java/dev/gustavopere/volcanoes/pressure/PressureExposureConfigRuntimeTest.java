package dev.gustavopere.volcanoes.pressure;

import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class PressureExposureConfigRuntimeTest {
    private static final ResourceLocation DEFAULT_ID =
            ResourceLocation.fromNamespaceAndPath("volcanoes", "default");

    @AfterEach
    void restoreDefaults() {
        PressureExposureConfigRuntime.resetToDefaults();
    }

    @Test
    void canonicalDefaultResourceReplacesRuntimeSnapshotAtomically() {
        PressureExposureConfigRuntime.reload(Map.of(DEFAULT_ID, JsonParser.parseString("""
                {
                  "grace_ticks": 12,
                  "discomfort_overpressure_atm": 0.4,
                  "impairment_overpressure_atm": 0.9,
                  "barotrauma_overpressure_atm": 1.8,
                  "impaired_movement_multiplier": 0.7,
                  "neurological_penalty": 0.6,
                  "barotrauma_damage_per_update": 4.0
                }
                """)));

        PressureExposureConfig current = PressureExposureConfigRuntime.current();
        assertEquals(12, current.graceTicks());
        assertEquals(1.8, current.barotraumaOverpressureAtm(), 1.0e-9);
        assertEquals(4.0, current.barotraumaDamagePerUpdate(), 1.0e-9);
    }

    @Test
    void absentDefaultRestoresBuiltInDefaultsAndMalformedReloadRetainsPreviousSnapshot() {
        PressureExposureConfigRuntime.reload(Map.of(DEFAULT_ID, JsonParser.parseString("""
                {
                  "grace_ticks": 7,
                  "discomfort_overpressure_atm": 0.25,
                  "impairment_overpressure_atm": 0.75,
                  "barotrauma_overpressure_atm": 1.5,
                  "impaired_movement_multiplier": 0.75,
                  "neurological_penalty": 0.5,
                  "barotrauma_damage_per_update": 1.5
                }
                """)));
        PressureExposureConfig accepted = PressureExposureConfigRuntime.current();

        assertThrows(IllegalArgumentException.class, () -> PressureExposureConfigRuntime.reload(Map.of(
                DEFAULT_ID,
                JsonParser.parseString("{\"grace_ticks\": -1}"))));
        assertEquals(accepted, PressureExposureConfigRuntime.current());

        PressureExposureConfigRuntime.reload(Map.of());
        assertEquals(PressureExposureConfig.defaults(), PressureExposureConfigRuntime.current());
    }

    @Test
    void foreignNamespaceDefinitionsDoNotCompeteWithCanonicalVolcanoesConfig() {
        ResourceLocation other = ResourceLocation.fromNamespaceAndPath("example", "override");
        PressureExposureConfigRuntime.reload(Map.of(
                DEFAULT_ID, JsonParser.parseString(validJson()),
                other, JsonParser.parseString("{\"not\": \"our schema\"}")));

        assertEquals(PressureExposureConfig.defaults(), PressureExposureConfigRuntime.current());
    }

    private static String validJson() {
        return """
                {
                  "grace_ticks": 100,
                  "discomfort_overpressure_atm": 0.75,
                  "impairment_overpressure_atm": 1.5,
                  "barotrauma_overpressure_atm": 2.5,
                  "impaired_movement_multiplier": 0.8,
                  "neurological_penalty": 0.5,
                  "barotrauma_damage_per_update": 2.0
                }
                """;
    }
}
