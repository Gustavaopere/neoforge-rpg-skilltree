package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class VolcanicSourceProfilesTest {
    @Test
    void denseCarbonDioxideDisplacesOxygenWithoutBecomingParticulate() {
        AtmosphereState state = VolcanicSourceProfiles.carbonDioxide(0.25)
                .contribution()
                .applyTo(AtmosphereState.standardOverworld());

        assertEquals(0.2095 * 0.75, state.oxygenFraction(), 1.0e-9);
        assertTrue(state.carbonDioxideFraction() > 0.25);
        assertEquals(0.0, state.particulatesMgM3(), 1.0e-9);
        assertEquals(0.0, state.smokeMgM3(), 1.0e-9);
    }

    @Test
    void sulfurDioxideAddsAcidifyingLoadWithoutErasingOxygen() {
        AtmosphereState state = VolcanicSourceProfiles.acidGas(40.0)
                .contribution()
                .applyTo(AtmosphereState.standardOverworld());

        assertEquals(0.2095, state.oxygenFraction(), 1.0e-9);
        assertEquals(40.0, state.sulfurDioxidePpm(), 1.0e-9);
        assertEquals(0.2095, state.oxygenPartialPressureAtm(), 1.0e-9);
    }

    @Test
    void ashAddsParticlesAndSmogButDoesNotSimulateLowPressure() {
        AtmosphereState state = VolcanicSourceProfiles.ash(15.0, 4.0)
                .contribution()
                .applyTo(AtmosphereState.standardOverworld());

        assertEquals(1.0, state.totalPressureAtm(), 1.0e-9);
        assertEquals(0.2095, state.oxygenFraction(), 1.0e-9);
        assertEquals(15.0, state.particulatesMgM3(), 1.0e-9);
        assertEquals(4.0, state.smokeMgM3(), 1.0e-9);
    }

    @Test
    void geothermalToxicProfileIsConfigurableAndSeparateFromAcidGas() {
        AtmosphereState state = VolcanicSourceProfiles.geothermalToxic(75.0)
                .contribution()
                .applyTo(AtmosphereState.standardOverworld());
        assertEquals(75.0, state.toxicGasPpm(), 1.0e-9);
        assertEquals(0.0, state.sulfurDioxidePpm(), 1.0e-9);
    }
}
