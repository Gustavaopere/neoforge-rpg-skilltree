package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class AtmosphereExternalContributionProviderTest {
    @Test
    void externalReadbackIsCombinedOnceIntoTheSameAtmosphereStateWithoutMirroredSources() {
        AtomicInteger samples = new AtomicInteger();
        AtmosphereExternalContributionProvider external = (dimensionId, x, y, z) -> {
            samples.incrementAndGet();
            assertEquals("minecraft:overworld", dimensionId);
            return new AtmosphereContribution(
                    0.0,
                    0.0,
                    0.0,
                    12.0,
                    0.0,
                    3.0,
                    2.0,
                    0.0,
                    0.0,
                    0.0);
        };
        AtmosphereField field = new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                AtmosphereDynamics.defaults(),
                AtmosphereTransportProvider.stillAir(),
                AtmosphericSourceLifecycleSink.none(),
                external);
        field.register(new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000901"),
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                16.0,
                VolcanicSourceProfiles.ash(4.0, 1.0).contribution(),
                1.0,
                false));

        AtmosphereState sampled = field.sample("minecraft:overworld", 0.0, 64.0, 0.0);

        assertEquals(1, samples.get(), "external authority must be sampled once per atmosphere sample");
        assertEquals(7.0, sampled.particulatesMgM3(), 1.0e-9);
        assertEquals(3.0, sampled.smokeMgM3(), 1.0e-9);
        assertEquals(12.0, sampled.sulfurDioxidePpm(), 1.0e-9);
        assertEquals(1, field.sourceCount(),
                "external readback must not be mirrored into a second persistent/indexed source system");
    }

    @Test
    void replacingExternalAuthorityDoesNotComposeOldAndNewReadbackProviders() {
        AtomicInteger firstSamples = new AtomicInteger();
        AtomicInteger secondSamples = new AtomicInteger();
        AtmosphereExternalContributionProvider first = (dimensionId, x, y, z) -> {
            firstSamples.incrementAndGet();
            return new AtmosphereContribution(0.0, 0.0, 0.0, 10.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        };
        AtmosphereExternalContributionProvider second = (dimensionId, x, y, z) -> {
            secondSamples.incrementAndGet();
            return new AtmosphereContribution(0.0, 0.0, 0.0, 25.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        };
        AtmosphereField field = new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                AtmosphereDynamics.defaults(),
                AtmosphereTransportProvider.stillAir(),
                AtmosphericSourceLifecycleSink.none(),
                first);

        assertEquals(10.0, field.sample("minecraft:overworld", 0.0, 64.0, 0.0).sulfurDioxidePpm(), 1.0e-9);
        field.replaceExternalContributionProvider(second);
        assertEquals(25.0, field.sample("minecraft:overworld", 0.0, 64.0, 0.0).sulfurDioxidePpm(), 1.0e-9);
        assertEquals(1, firstSamples.get(), "replaced authority must stop participating in samples");
        assertEquals(1, secondSamples.get());
        assertEquals(0, field.sourceCount(), "readback replacement must not create mirrored sources");
    }

    @Test
    void failingOptionalReadbackFallsBackToLocalAtmosphere() {
        AtmosphereExternalContributionProvider failing = (dimensionId, x, y, z) -> {
            throw new LinkageError("simulated optional integration mismatch");
        };
        AtmosphereField field = new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                AtmosphereDynamics.defaults(),
                AtmosphereTransportProvider.stillAir(),
                AtmosphericSourceLifecycleSink.none(),
                failing);
        field.register(new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000902"),
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                16.0,
                VolcanicSourceProfiles.ash(4.0, 1.0).contribution(),
                1.0,
                false));

        AtmosphereState sampled = assertDoesNotThrow(
                () -> field.sample("minecraft:overworld", 0.0, 64.0, 0.0),
                "optional readback linkage failure must fail closed to the internal atmosphere");

        assertEquals(4.0, sampled.particulatesMgM3(), 1.0e-9);
        assertEquals(1.0, sampled.smokeMgM3(), 1.0e-9);
        assertEquals(0.0, sampled.sulfurDioxidePpm(), 1.0e-9);
    }

    @Test
    void defaultExternalProviderContributesNothing() {
        AtmosphereContribution contribution = AtmosphereExternalContributionProvider.none()
                .sample("minecraft:overworld", 0.0, 64.0, 0.0);

        assertEquals(AtmosphereContribution.none(), contribution);
    }
}
