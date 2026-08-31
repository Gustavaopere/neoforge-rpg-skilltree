package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class AtmosphericPollutionFallbackTest {
    @Test
    void standalonePollutionMapsOnlyEnvironmentalLoadsIntoAtmosphere() {
        PollutionLoad load = new PollutionLoad(20.0, 4.0, 3.0, 2.0, 1.0);
        AtmosphericPollutionFallback fallback = new AtmosphericPollutionFallback(0.5, 1.0, 2.0);

        AtmosphereState state = fallback.contributionFor(load).applyTo(AtmosphereState.standardOverworld());
        assertEquals(10.0, state.sulfurDioxidePpm(), 1.0e-9);
        assertEquals(4.0, state.particulatesMgM3(), 1.0e-9);
        assertEquals(6.0, state.smokeMgM3(), 1.0e-9);
        assertEquals(1.0, state.totalPressureAtm(), 1.0e-9);
        assertEquals(0.2095, state.oxygenFraction(), 1.0e-9);
        assertEquals(2.0, load.greenhouseLoad(), 1.0e-9);
        assertEquals(1.0, load.ozoneAffectingLoad(), 1.0e-9);
    }

    @Test
    void acceptedFiniteLoadsAndScalesRemainFiniteDuringFallbackConversion() {
        PollutionLoad load = new PollutionLoad(
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                0.0,
                0.0);
        AtmosphericPollutionFallback fallback = new AtmosphericPollutionFallback(2.0, 2.0, 2.0);

        AtmosphereContribution contribution = assertDoesNotThrow(() -> fallback.contributionFor(load),
                "valid finite pollution inputs and conversion scales must not overflow the atmosphere contract");

        assertEquals(Double.MAX_VALUE, contribution.sulfurDioxidePpm());
        assertEquals(Double.MAX_VALUE, contribution.particulatesMgM3());
        assertEquals(Double.MAX_VALUE, contribution.smokeMgM3());
        assertTrue(Double.isFinite(contribution.sulfurDioxidePpm()));
        assertTrue(Double.isFinite(contribution.particulatesMgM3()));
        assertTrue(Double.isFinite(contribution.smokeMgM3()));
    }
}
