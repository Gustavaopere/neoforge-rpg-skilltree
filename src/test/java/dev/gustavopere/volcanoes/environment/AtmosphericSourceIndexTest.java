package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.performance.PerformanceProfiler;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class AtmosphericSourceIndexTest {
    @Test
    void hundredsOfFarSourcesDoNotExpandOneLocalLookup() {
        AtmosphericSourceIndex index = new AtmosphericSourceIndex(64);
        AtmosphereContribution ash = new AtmosphereContribution(0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0);
        AtmosphericSource local = new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000101"),
                "minecraft:overworld", 0.0, 64.0, 0.0, 20.0, ash, 1.0, false);
        index.register(local);
        for (int i = 1; i <= 500; i++) {
            index.register(new AtmosphericSource(
                    new UUID(0L, 1_000L + i),
                    "minecraft:overworld",
                    i * 256.0,
                    64.0,
                    0.0,
                    20.0,
                    ash,
                    1.0,
                    false));
        }

        List<AtmosphericSource> candidates = index.candidatesAt("minecraft:overworld", 0.0, 0.0);
        assertEquals(List.of(local), candidates);
    }

    @Test
    void profiledReducerCountsOnlyTheIndexedLocalBucket() {
        AtmosphericSourceIndex index = new AtmosphericSourceIndex(64);
        AtmosphereContribution ash = new AtmosphereContribution(0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0);
        AtmosphericSource local = new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000105"),
                "minecraft:overworld", 0.0, 64.0, 0.0, 20.0, ash, 1.0, false);
        index.register(local);
        for (int i = 1; i <= 500; i++) {
            index.register(new AtmosphericSource(
                    new UUID(0L, 2_000L + i),
                    "minecraft:overworld",
                    i * 256.0,
                    64.0,
                    0.0,
                    20.0,
                    ash,
                    1.0,
                    false));
        }

        PerformanceProfiler.reset();
        index.combinedContributionAt("minecraft:overworld", 0.0, 64.0, 0.0);
        PerformanceProfiler.Snapshot snapshot = PerformanceProfiler.snapshot();

        assertEquals(1, snapshot.atmosphereSamples());
        assertEquals(1, snapshot.atmosphereSourceCandidates(),
                "500 distant sources must not enter one breathing lookup candidate set");
    }

    @Test
    void internalReducerKeepsThreeDimensionalAttenuationWhileAvoidingCandidateCopies() {
        AtmosphericSourceIndex index = new AtmosphericSourceIndex(64);
        AtmosphereContribution ash = new AtmosphereContribution(
                0.0, 0.0, 0.0, 0.0, 0.0, 2.0, 1.0, 0.0, 0.0, 0.0);
        AtmosphericSource local = new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000103"),
                "minecraft:overworld", 0.0, 64.0, 0.0, 20.0, ash, 1.0, false);
        AtmosphericSource verticallyFar = new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000104"),
                "minecraft:overworld", 0.0, 200.0, 0.0, 20.0, ash, 1.0, false);
        index.register(local);
        index.register(verticallyFar);

        assertEquals(2, index.candidatesAt("minecraft:overworld", 0.0, 0.0).size(),
                "the X/Z index may conservatively return both sources");
        assertEquals(
                local.contributionAt(0.0, 64.0, 0.0).orElseThrow(),
                index.combinedContributionAt("minecraft:overworld", 0.0, 64.0, 0.0),
                "the allocation-free reducer must retain the exact 3D source attenuation semantics");
    }

    @Test
    void reindexingAfterDiffusionDoesNotDuplicateSourceInCandidateBucket() {
        AtmosphericSourceIndex index = new AtmosphericSourceIndex(64);
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000102");
        AtmosphereContribution gas = new AtmosphereContribution(0.0, 0.0, 0.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.05);
        AtmosphericSource source = new AtmosphericSource(id, "minecraft:overworld", 63.0, 64.0, 0.0, 4.0, gas, 1.0, false);
        index.register(source);
        index.replace(source.evolve(new AtmosphereDynamics(0.9, 8.0, 0.01)).orElseThrow());

        assertEquals(1, index.candidatesAt("minecraft:overworld", 63.0, 0.0).size());
        assertEquals(1, index.candidatesAt("minecraft:overworld", 68.0, 0.0).size());
    }
}
