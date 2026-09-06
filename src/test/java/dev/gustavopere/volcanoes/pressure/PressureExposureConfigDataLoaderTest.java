package dev.gustavopere.volcanoes.pressure;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class PressureExposureConfigDataLoaderTest {
    @Test
    void parsesEveryGameplayThresholdFromData() {
        PressureExposureConfig config = PressureExposureConfigDataLoader.parse(JsonParser.parseString("""
                {
                  "grace_ticks": 40,
                  "discomfort_overpressure_atm": 0.5,
                  "impairment_overpressure_atm": 1.25,
                  "barotrauma_overpressure_atm": 2.75,
                  "impaired_movement_multiplier": 0.65,
                  "neurological_penalty": 0.8,
                  "barotrauma_damage_per_update": 3.5
                }
                """));

        assertEquals(40, config.graceTicks());
        assertEquals(0.5, config.discomfortOverpressureAtm(), 1.0e-9);
        assertEquals(1.25, config.impairmentOverpressureAtm(), 1.0e-9);
        assertEquals(2.75, config.barotraumaOverpressureAtm(), 1.0e-9);
        assertEquals(0.65, config.impairedMovementMultiplier(), 1.0e-9);
        assertEquals(0.8, config.neurologicalPenalty(), 1.0e-9);
        assertEquals(3.5, config.barotraumaDamagePerUpdate(), 1.0e-9);
    }

    @Test
    void rejectsMissingUnknownAndNonMonotonicConfiguration() {
        assertThrows(IllegalArgumentException.class, () -> PressureExposureConfigDataLoader.parse(
                JsonParser.parseString("{\"grace_ticks\": 10}")));

        assertThrows(IllegalArgumentException.class, () -> PressureExposureConfigDataLoader.parse(
                JsonParser.parseString("""
                        {
                          "grace_ticks": 10,
                          "discomfort_overpressure_atm": 1.0,
                          "impairment_overpressure_atm": 0.9,
                          "barotrauma_overpressure_atm": 2.0,
                          "impaired_movement_multiplier": 0.8,
                          "neurological_penalty": 0.5,
                          "barotrauma_damage_per_update": 2.0
                        }
                        """)));

        assertThrows(IllegalArgumentException.class, () -> PressureExposureConfigDataLoader.parse(
                JsonParser.parseString("""
                        {
                          "grace_ticks": 10,
                          "discomfort_overpressure_atm": 0.5,
                          "impairment_overpressure_atm": 1.0,
                          "barotrauma_overpressure_atm": 2.0,
                          "impaired_movement_multiplier": 0.8,
                          "neurological_penalty": 0.5,
                          "barotrauma_damage_per_update": 2.0,
                          "mystery": true
                        }
                        """)));
    }
}
