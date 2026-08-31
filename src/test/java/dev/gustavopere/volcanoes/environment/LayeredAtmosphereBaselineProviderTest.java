package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class LayeredAtmosphereBaselineProviderTest {
    @Test
    void samplesHighestAltitudeLayerWithinEachDimensionWithoutOwningPressurePhysics() {
        AtmosphereState fallback = AtmosphereState.standardOverworld();
        AtmosphereState low = new AtmosphereState(1.0, 0.2095, 0.00042, 0, 0, 0, 0, 0.55, 0);
        AtmosphereState high = new AtmosphereState(0.62, 0.2095, 0.00042, 0, 0, 0, 0, 0.30, -7);
        AtmosphereState end = new AtmosphereState(0.35, 0.02, 0.01, 0, 0, 0, 0, 0.05, -12);

        LayeredAtmosphereBaselineProvider provider = new LayeredAtmosphereBaselineProvider(
                fallback,
                Map.of(
                        "minecraft:overworld", List.of(
                                new AtmosphereBaselineLayer(-64.0, low),
                                new AtmosphereBaselineLayer(192.0, high)),
                        "minecraft:the_end", List.of(new AtmosphereBaselineLayer(-64.0, end))));

        assertEquals(low, provider.sample("minecraft:overworld", 64.0));
        assertEquals(high, provider.sample("minecraft:overworld", 220.0));
        assertEquals(end, provider.sample("minecraft:the_end", 80.0));
        assertEquals(fallback, provider.sample("example:unknown", 300.0));
        assertEquals(fallback, provider.sample("minecraft:overworld", -100.0));
    }

    @Test
    void rejectsDuplicateLayerFloorsBecauseSelectionWouldBeAmbiguous() {
        AtmosphereBaselineLayer first = new AtmosphereBaselineLayer(64.0, AtmosphereState.standardOverworld());
        AtmosphereBaselineLayer second = new AtmosphereBaselineLayer(64.0, AtmosphereState.standardOverworld());
        assertThrows(IllegalArgumentException.class, () -> new LayeredAtmosphereBaselineProvider(
                AtmosphereState.standardOverworld(),
                Map.of("minecraft:overworld", List.of(first, second))));
    }

    @Test
    void signedZeroFloorsAreAlsoRejectedAsTheSameAltitudeBoundary() {
        AtmosphereBaselineLayer negativeZero =
                new AtmosphereBaselineLayer(-0.0, AtmosphereState.standardOverworld());
        AtmosphereBaselineLayer positiveZero =
                new AtmosphereBaselineLayer(0.0, AtmosphereState.standardOverworld());

        assertThrows(IllegalArgumentException.class, () -> new LayeredAtmosphereBaselineProvider(
                AtmosphereState.standardOverworld(),
                Map.of("minecraft:overworld", List.of(negativeZero, positiveZero))));
    }
}
