package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Regression coverage for deterministic local atmosphere composition. */
final class AtmosphereContributionCompositionTest {
    @Test
    void combineIsCommutativeAndDefersClampingUntilTheAggregateIsApplied() {
        AtmosphereContribution oxygenAndHumidity = new AtmosphereContribution(
                0.0, 0.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.60, 0.0, 0.0);
        AtmosphereContribution co2DisplacementAndDrying = new AtmosphereContribution(
                0.0, 0.0, 0.25, 0.0, 0.0, 0.0, 0.0, -0.40, 0.0, 0.25);

        AtmosphereState leftFirst = oxygenAndHumidity
                .combine(co2DisplacementAndDrying)
                .applyTo(AtmosphereState.standardOverworld());
        AtmosphereState rightFirst = co2DisplacementAndDrying
                .combine(oxygenAndHumidity)
                .applyTo(AtmosphereState.standardOverworld());

        assertEquals(leftFirst, rightFirst);
        assertEquals((0.2095 + 0.05) * 0.75, leftFirst.oxygenFraction(), 1.0e-12);
        assertEquals(0.70, leftFirst.relativeHumidity(), 1.0e-12);
        assertEquals(0.25042, leftFirst.carbonDioxideFraction(), 1.0e-12);
    }

    @Test
    void fieldAggregatesAllLocalContributionsBeforeApplyingBaselineNormalization() {
        AtmosphereField field = new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                AtmosphereDynamics.defaults());

        field.register(new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000701"),
                "minecraft:overworld",
                0.0, 64.0, 0.0,
                32.0,
                new AtmosphereContribution(
                        0.0, 0.05, 0.0, 0.0, 0.0, 0.0, 0.0, 0.60, 0.0, 0.0),
                1.0,
                false));
        field.register(new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000702"),
                "minecraft:overworld",
                0.0, 64.0, 0.0,
                32.0,
                new AtmosphereContribution(
                        0.0, 0.0, 0.25, 0.0, 0.0, 0.0, 0.0, -0.40, 0.0, 0.25),
                1.0,
                false));

        AtmosphereState sampled = field.sample("minecraft:overworld", 0.0, 64.0, 0.0);
        assertEquals((0.2095 + 0.05) * 0.75, sampled.oxygenFraction(), 1.0e-12);
        assertEquals(0.70, sampled.relativeHumidity(), 1.0e-12);
        assertEquals(0.25042, sampled.carbonDioxideFraction(), 1.0e-12);
    }

    @Test
    void fieldSamplingIsDeterministicAcrossRegistrationOrdersForNonAssociativeSums() {
        UUID positiveId = UUID.fromString("00000000-0000-0000-0000-000000000711");
        UUID negativeId = UUID.fromString("00000000-0000-0000-0000-000000000712");
        UUID unitId = UUID.fromString("00000000-0000-0000-0000-000000000713");
        AtmosphericSource positive = pressureSource(positiveId, 1.0e16);
        AtmosphericSource negative = pressureSource(negativeId, -1.0e16);
        AtmosphericSource unit = pressureSource(unitId, 1.0);

        AtmosphereField canonicalOrder = field();
        canonicalOrder.register(positive);
        canonicalOrder.register(negative);
        canonicalOrder.register(unit);

        AtmosphereField differentArrivalOrder = field();
        differentArrivalOrder.register(positive);
        differentArrivalOrder.register(unit);
        differentArrivalOrder.register(negative);

        AtmosphereState expected = canonicalOrder.sample("minecraft:overworld", 0.0, 64.0, 0.0);
        AtmosphereState actual = differentArrivalOrder.sample("minecraft:overworld", 0.0, 64.0, 0.0);

        assertEquals(expected, actual,
                "source replay/arrival order must not change the sampled atmosphere");
        assertEquals(2.0, expected.totalPressureAtm(), 0.0,
                "UUID order deliberately exposes floating-point non-associativity in this regression");
    }

    @Test
    void finiteSourceContributionsRemainComposableWithoutOverflowingTheSample() {
        AtmosphereContribution extreme = new AtmosphereContribution(
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                0.0);
        AtmosphereField field = field();

        field.register(new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000703"),
                "minecraft:overworld", 0.0, 64.0, 0.0, 32.0, extreme, 1.0, false));
        field.register(new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000704"),
                "minecraft:overworld", 0.0, 64.0, 0.0, 32.0, extreme, 1.0, false));

        AtmosphereState sampled = assertDoesNotThrow(
                () -> field.sample("minecraft:overworld", 0.0, 64.0, 0.0),
                "composition of individually finite sources must remain a valid finite atmosphere");

        assertTrue(Double.isFinite(sampled.totalPressureAtm()));
        assertTrue(Double.isFinite(sampled.sulfurDioxidePpm()));
        assertTrue(Double.isFinite(sampled.toxicGasPpm()));
        assertTrue(Double.isFinite(sampled.particulatesMgM3()));
        assertTrue(Double.isFinite(sampled.smokeMgM3()));
        assertTrue(Double.isFinite(sampled.thermalModifierC()));
        assertEquals(1.0, sampled.oxygenFraction(), 0.0);
        assertEquals(1.0, sampled.carbonDioxideFraction(), 0.0);
        assertEquals(1.0, sampled.relativeHumidity(), 0.0);
    }

    private static AtmosphereField field() {
        return new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                AtmosphereDynamics.defaults());
    }

    private static AtmosphericSource pressureSource(UUID id, double pressureDeltaAtm) {
        return new AtmosphericSource(
                id,
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                32.0,
                new AtmosphereContribution(
                        pressureDeltaAtm, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                1.0,
                false);
    }
}
