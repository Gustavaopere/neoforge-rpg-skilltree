package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class AtmosphereContributionScalingTest {
    @Test
    void acceptedFiniteContributionAndScaleRemainFiniteAtNumericLimits() {
        AtmosphereContribution contribution = new AtmosphereContribution(
                Double.MAX_VALUE,
                -Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                -Double.MAX_VALUE,
                Double.MAX_VALUE,
                0.75);

        AtmosphereContribution scaled = assertDoesNotThrow(() -> contribution.scaled(2.0));

        assertEquals(Double.MAX_VALUE, scaled.pressureDeltaAtm());
        assertEquals(-Double.MAX_VALUE, scaled.oxygenFractionDelta());
        assertEquals(Double.MAX_VALUE, scaled.carbonDioxideFraction());
        assertEquals(Double.MAX_VALUE, scaled.sulfurDioxidePpm());
        assertEquals(Double.MAX_VALUE, scaled.toxicGasPpm());
        assertEquals(Double.MAX_VALUE, scaled.particulatesMgM3());
        assertEquals(Double.MAX_VALUE, scaled.smokeMgM3());
        assertEquals(-Double.MAX_VALUE, scaled.relativeHumidityDelta());
        assertEquals(Double.MAX_VALUE, scaled.thermalModifierDeltaC());
        assertEquals(1.0, scaled.oxygenDisplacementFraction());
    }
}
