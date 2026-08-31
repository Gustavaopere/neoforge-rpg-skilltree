package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class AtmosphereTransportTest {
    @Test
    void transportPortCanAdvectAndModifyDiffusionWithoutWeatherDependency() {
        AtmosphericSource source = new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000401"),
                "minecraft:overworld", 0.0, 64.0, 0.0, 10.0,
                VolcanicSourceProfiles.ash(4.0, 1.0).contribution(), 1.0, false);
        AtmosphereDynamics dynamics = new AtmosphereDynamics(0.50, 2.0, 0.01);
        AtmosphereTransport transport = new AtmosphereTransport(4.0, -2.0, 1.5, 0.8);

        AtmosphericSource evolved = source.evolve(dynamics, transport).orElseThrow();
        assertEquals(4.0, evolved.x(), 1.0e-9);
        assertEquals(-2.0, evolved.z(), 1.0e-9);
        assertEquals(13.0, evolved.radiusBlocks(), 1.0e-9);
        assertEquals(0.40, evolved.strength(), 1.0e-9);
        assertEquals(AtmosphereTransport.stillAir(), AtmosphereTransportProvider.stillAir().sample(source));
    }

    @Test
    void invalidTransportForOneSourceDoesNotAbortOtherBoundedUpdates() {
        UUID invalidId = UUID.fromString("00000000-0000-0000-0000-000000000402");
        UUID healthyId = UUID.fromString("00000000-0000-0000-0000-000000000403");
        AtmosphericSource invalid = source(invalidId, 0.0);
        AtmosphericSource healthy = source(healthyId, 128.0);
        AtmosphereTransportProvider provider = source -> source.id().equals(invalidId)
                ? new AtmosphereTransport(Double.MAX_VALUE, 0.0, 1.0, 1.0)
                : AtmosphereTransport.stillAir();
        AtmosphereField field = new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                new AtmosphereDynamics(0.50, 2.0, 0.01),
                provider,
                AtmosphericSourceLifecycleSink.none());
        field.register(invalid);
        field.register(healthy);

        assertEquals(2, field.tick(2));
        assertEquals(invalid, field.source(invalidId).orElseThrow(),
                "invalid advection must retain the last valid source for retry");
        assertEquals(0.50, field.source(healthyId).orElseThrow().strength(), 1.0e-9,
                "one bad transport sample must not starve other sources in the bounded batch");
    }

    @Test
    void optionalTransportLinkageFailureIsIsolatedToItsSource() {
        UUID failingId = UUID.fromString("00000000-0000-0000-0000-000000000404");
        UUID healthyId = UUID.fromString("00000000-0000-0000-0000-000000000405");
        AtmosphericSource failing = source(failingId, 0.0);
        AtmosphericSource healthy = source(healthyId, 128.0);
        AtmosphereTransportProvider provider = source -> {
            if (source.id().equals(failingId)) {
                throw new LinkageError("simulated optional weather integration mismatch");
            }
            return AtmosphereTransport.stillAir();
        };
        AtmosphereField field = new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                new AtmosphereDynamics(0.50, 2.0, 0.01),
                provider,
                AtmosphericSourceLifecycleSink.none());
        field.register(failing);
        field.register(healthy);

        assertDoesNotThrow(() -> assertEquals(2, field.tick(2)));
        assertEquals(failing, field.source(failingId).orElseThrow(),
                "optional transport linkage failure must preserve the last valid source for retry");
        assertEquals(0.50, field.source(healthyId).orElseThrow().strength(), 1.0e-9,
                "one broken optional transport adapter must not abort healthy bounded updates");
    }

    private static AtmosphericSource source(UUID id, double x) {
        return new AtmosphericSource(
                id,
                "minecraft:overworld",
                x,
                64.0,
                0.0,
                10.0,
                VolcanicSourceProfiles.ash(4.0, 1.0).contribution(),
                1.0,
                false);
    }
}
