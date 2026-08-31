package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final class AtmosphereFieldTest {
    @Test
    void baselineProviderControlsPressureWithoutOwningFuturePressureService() {
        AtmosphereBaselineProvider baseline = (dimensionId, y) -> y >= 200.0
                ? new AtmosphereState(0.60, 0.2095, 0.00042, 0.0, 0.0, 0.0, 0.0, 0.45, -4.0)
                : AtmosphereState.standardOverworld();
        AtmosphereField field = new AtmosphereField(
                baseline,
                new AtmosphericSourceIndex(64),
                AtmosphereDynamics.defaults());

        assertEquals(1.0, field.sample("minecraft:overworld", 0.0, 64.0, 0.0).totalPressureAtm(), 1.0e-9);
        assertEquals(0.60, field.sample("minecraft:overworld", 0.0, 220.0, 0.0).totalPressureAtm(), 1.0e-9);
    }

    @Test
    void carbonDioxideDisplacesOxygenWhileAshDoesNotChangePressure() {
        AtmosphereField field = new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                AtmosphereDynamics.defaults());
        field.register(new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "minecraft:overworld",
                0.0, 64.0, 0.0,
                32.0,
                new AtmosphereContribution(0.0, 0.0, 0.20, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.20),
                1.0,
                true));
        field.register(new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000002"),
                "minecraft:overworld",
                0.0, 64.0, 0.0,
                32.0,
                new AtmosphereContribution(0.0, 0.0, 0.0, 0.0, 0.0, 8.0, 3.0, 0.0, 0.0, 0.0),
                1.0,
                false));

        AtmosphereState state = field.sample("minecraft:overworld", 0.0, 64.0, 0.0);
        assertEquals(1.0, state.totalPressureAtm(), 1.0e-9);
        assertEquals(0.2095 * 0.80, state.oxygenFraction(), 1.0e-9);
        assertTrue(state.carbonDioxideFraction() > 0.20);
        assertEquals(8.0, state.particulatesMgM3(), 1.0e-9);
        assertEquals(3.0, state.smokeMgM3(), 1.0e-9);
    }

    @Test
    void boundedUpdateQueueDiffusesAndDecaysOnlyRequestedSources() {
        AtmosphereDynamics dynamics = new AtmosphereDynamics(0.50, 2.0, 0.05);
        AtmosphereField field = new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                dynamics);
        UUID firstId = UUID.fromString("00000000-0000-0000-0000-000000000011");
        UUID secondId = UUID.fromString("00000000-0000-0000-0000-000000000012");
        AtmosphereContribution ash = new AtmosphereContribution(0.0, 0.0, 0.0, 0.0, 0.0, 4.0, 0.0, 0.0, 0.0, 0.0);
        field.register(new AtmosphericSource(firstId, "minecraft:overworld", 0, 64, 0, 10, ash, 1.0, false));
        field.register(new AtmosphericSource(secondId, "minecraft:overworld", 128, 64, 0, 10, ash, 1.0, false));

        assertEquals(1, field.tick(1));
        AtmosphericSource first = field.source(firstId).orElseThrow();
        AtmosphericSource second = field.source(secondId).orElseThrow();
        assertEquals(12.0, first.radiusBlocks(), 1.0e-9);
        assertEquals(0.50, first.strength(), 1.0e-9);
        assertEquals(10.0, second.radiusBlocks(), 1.0e-9);
        assertEquals(1.0, second.strength(), 1.0e-9);
    }
}
