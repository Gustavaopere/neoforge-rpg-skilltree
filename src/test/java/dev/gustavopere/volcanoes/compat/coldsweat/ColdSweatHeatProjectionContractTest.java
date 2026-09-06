package dev.gustavopere.volcanoes.compat.coldsweat;

import dev.gustavopere.volcanoes.volcano.VolcanicHeatSource;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ColdSweatHeatProjectionContractTest {
    private static final BlockPos SAMPLE = new BlockPos(0, 64, 0);

    @Test
    void defaultsAreFinitePositiveAndBounded() {
        ColdSweatHeatProjectionPolicy policy = ColdSweatHeatProjectionPolicy.defaults();

        assertTrue(Double.isFinite(policy.fullSeverityDeltaMc()));
        assertTrue(policy.fullSeverityDeltaMc() > 0.0);
        assertTrue(Double.isFinite(policy.maxWorldDeltaMc()));
        assertTrue(policy.maxWorldDeltaMc() >= policy.fullSeverityDeltaMc());
        assertTrue(policy.maxSourcesPerSample() > 0);
        assertTrue(policy.maxSourcesPerSample() <= 64);
    }

    @Test
    void noHeatProducesNoWorldTemperatureDelta() {
        assertEquals(
                0.0,
                ColdSweatHeatProjection.projectMcDelta(
                        SAMPLE,
                        List.of(),
                        ColdSweatHeatProjectionPolicy.defaults()),
                1.0e-12);
    }

    @Test
    void everyCanonicalHeatFamilyContributesAtTheSourceCenter() {
        ColdSweatHeatProjectionPolicy policy = ColdSweatHeatProjectionPolicy.defaults();

        for (VolcanicHeatSource.Kind kind : VolcanicHeatSource.Kind.values()) {
            double delta = ColdSweatHeatProjection.projectMcDelta(
                    SAMPLE,
                    List.of(source(kind, SAMPLE, 16.0, 1.0)),
                    policy);
            assertEquals(policy.fullSeverityDeltaMc(), delta, 1.0e-12, kind.name());
        }
    }

    @Test
    void contributionAttenuatesToZeroAtTheDeclaredThreeDimensionalRadius() {
        ColdSweatHeatProjectionPolicy policy = ColdSweatHeatProjectionPolicy.defaults();
        VolcanicHeatSource source = source(
                VolcanicHeatSource.Kind.GEOTHERMAL,
                new BlockPos(0, 64, 0),
                10.0,
                1.0);

        double halfway = ColdSweatHeatProjection.projectMcDelta(
                new BlockPos(0, 69, 0), List.of(source), policy);
        double edge = ColdSweatHeatProjection.projectMcDelta(
                new BlockPos(0, 74, 0), List.of(source), policy);
        double beyond = ColdSweatHeatProjection.projectMcDelta(
                new BlockPos(0, 75, 0), List.of(source), policy);

        assertEquals(policy.fullSeverityDeltaMc() * 0.5, halfway, 1.0e-12);
        assertEquals(0.0, edge, 1.0e-12);
        assertEquals(0.0, beyond, 1.0e-12);
    }

    @Test
    void overlappingHeatIsCappedAndNeverCreatesASecondDamageSystem() {
        ColdSweatHeatProjectionPolicy policy = ColdSweatHeatProjectionPolicy.defaults();
        List<VolcanicHeatSource> sources = List.of(
                source(VolcanicHeatSource.Kind.LAVA, SAMPLE, 16.0, 1.0),
                source(VolcanicHeatSource.Kind.PYROCLASTIC, SAMPLE, 16.0, 1.0),
                source(VolcanicHeatSource.Kind.GEOTHERMAL, SAMPLE, 16.0, 1.0));

        double delta = ColdSweatHeatProjection.projectMcDelta(SAMPLE, sources, policy);

        assertEquals(policy.maxWorldDeltaMc(), delta, 1.0e-12);
    }

    private static VolcanicHeatSource source(
            VolcanicHeatSource.Kind kind,
            BlockPos center,
            double radius,
            double severity
    ) {
        return new VolcanicHeatSource(
                UUID.nameUUIDFromBytes((kind.name() + center + radius + severity).getBytes()),
                kind,
                center,
                radius,
                severity,
                Long.MAX_VALUE);
    }
}
