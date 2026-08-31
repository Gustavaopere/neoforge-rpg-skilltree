package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class PollutionAtmosphereReadbackProviderTest {
    @Test
    void readbackSamplesExternalPollutionOnlyAndProjectsItIntoOneAtmosphereContribution() {
        AtomicInteger samples = new AtomicInteger();
        AtomicInteger publishes = new AtomicInteger();
        PollutionAdapter adapter = new PollutionAdapter() {
            @Override
            public boolean isAuthoritative() {
                return true;
            }

            @Override
            public void publish(PollutionEmission emission) {
                publishes.incrementAndGet();
            }

            @Override
            public boolean supportsExternalReadback() {
                return true;
            }

            @Override
            public Optional<PollutionLoad> sampleExternalOnly(String dimensionId, double x, double y, double z) {
                samples.incrementAndGet();
                return Optional.of(new PollutionLoad(2.0, 3.0, 4.0, 5.0, 6.0));
            }
        };
        PollutionCoordinator coordinator = new PollutionCoordinator(adapter);
        PollutionAtmosphereReadbackProvider provider = new PollutionAtmosphereReadbackProvider(
                coordinator,
                new AtmosphericPollutionFallback(10.0, 2.0, 3.0));

        AtmosphereContribution contribution = provider.sample("minecraft:overworld", 0.0, 64.0, 0.0);

        assertEquals(1, samples.get());
        assertEquals(0, publishes.get(), "readback must never republish or double-count an emission");
        assertEquals(20.0, contribution.sulfurDioxidePpm(), 1.0e-9);
        assertEquals(6.0, contribution.particulatesMgM3(), 1.0e-9);
        assertEquals(12.0, contribution.smokeMgM3(), 1.0e-9);
        assertEquals(0.0, contribution.thermalModifierDeltaC(), 0.0,
                "greenhouse load must remain available to external authority rather than being guessed into heat");
    }

    @Test
    void aggregateHostReadbackIsSkippedWhenExternalOnlyProvenanceCannotBeProven() {
        AtomicInteger samples = new AtomicInteger();
        PollutionAdapter adapter = new PollutionAdapter() {
            @Override
            public boolean isAuthoritative() {
                return true;
            }

            @Override
            public void publish(PollutionEmission emission) {
            }

            @Override
            public Optional<PollutionLoad> sampleExternalOnly(String dimensionId, double x, double y, double z) {
                samples.incrementAndGet();
                return Optional.of(new PollutionLoad(100.0, 100.0, 100.0, 100.0, 100.0));
            }
        };
        PollutionAtmosphereReadbackProvider provider = new PollutionAtmosphereReadbackProvider(
                new PollutionCoordinator(adapter),
                new AtmosphericPollutionFallback(10.0, 2.0, 3.0));

        assertEquals(
                AtmosphereContribution.none(),
                provider.sample("minecraft:overworld", 0.0, 64.0, 0.0));
        assertEquals(0, samples.get(), "unsafe aggregate readback must not be sampled at all");
    }

    @Test
    void absentExternalPollutionContributesNothing() {
        PollutionAtmosphereReadbackProvider provider = new PollutionAtmosphereReadbackProvider(
                new PollutionCoordinator(PollutionAdapter.none()),
                new AtmosphericPollutionFallback(10.0, 2.0, 3.0));

        assertEquals(
                AtmosphereContribution.none(),
                provider.sample("minecraft:overworld", 0.0, 64.0, 0.0));
    }
}
