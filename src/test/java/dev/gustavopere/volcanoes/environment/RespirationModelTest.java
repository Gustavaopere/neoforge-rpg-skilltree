package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class RespirationModelTest {
    private final RespirationModel model = new RespirationModel(RespirationThresholds.defaults());

    @Test
    void normalPressureAndOxygenRefillAir() {
        RespirationOutcome outcome = model.evaluate(AtmosphereState.standardOverworld(), RespirationProtection.NONE);
        assertTrue(outcome.canBreathe());
        assertTrue(outcome.refillAirAmount() > 0);
        assertEquals(0, outcome.consumeAirAmount());
        assertTrue(outcome.hazards().isEmpty());
    }

    @Test
    void highAltitudeLowPartialPressureCausesHypoxia() {
        AtmosphereState thin = new AtmosphereState(0.55, 0.2095, 0.00042, 0.0, 0.0, 0.0, 0.0, 0.35, -6.0);
        RespirationOutcome outcome = model.evaluate(thin, RespirationProtection.NONE);
        assertFalse(outcome.canBreathe());
        assertTrue(outcome.consumeAirAmount() > 0);
        assertTrue(outcome.hazards().contains(AtmosphericHazard.HYPOXIA));
    }

    @Test
    void particulateFilterProtectsAgainstAshButDoesNotSupplyOxygen() {
        AtmosphereState ash = new AtmosphereState(1.0, 0.2095, 0.00042, 0.0, 0.0, 12.0, 0.0, 0.5, 0.0);
        RespirationOutcome unfiltered = model.evaluate(ash, RespirationProtection.NONE);
        RespirationOutcome filtered = model.evaluate(ash, RespirationProtection.of(1.0, 0.0, 0.0, 0.0));
        assertTrue(unfiltered.hazards().contains(AtmosphericHazard.PARTICULATES));
        assertTrue(filtered.canBreathe());
        assertFalse(filtered.hazards().contains(AtmosphericHazard.PARTICULATES));

        AtmosphereState thin = new AtmosphereState(0.45, 0.2095, 0.00042, 0.0, 0.0, 12.0, 0.0, 0.5, 0.0);
        RespirationOutcome thinFiltered = model.evaluate(thin, RespirationProtection.of(1.0, 0.0, 0.0, 0.0));
        assertTrue(thinFiltered.hazards().contains(AtmosphericHazard.HYPOXIA));
        assertFalse(thinFiltered.canBreathe());
    }

    @Test
    void oxygenSupplyDoesNotActAsParticulateFilter() {
        AtmosphereState thinAsh = new AtmosphereState(0.45, 0.2095, 0.00042, 0.0, 0.0, 12.0, 0.0, 0.5, 0.0);
        RespirationOutcome outcome = model.evaluate(
                thinAsh,
                RespirationProtection.of(0.0, 0.0, 0.0, 0.21));
        assertFalse(outcome.hazards().contains(AtmosphericHazard.HYPOXIA));
        assertTrue(outcome.hazards().contains(AtmosphericHazard.PARTICULATES));
        assertFalse(outcome.canBreathe());
    }

    @Test
    void carbonDioxideDisplacementAcidGasAndToxicGasRemainDistinctHazards() {
        AtmosphereState co2 = new AtmosphereContribution(
                0.0, 0.0, 0.30, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.30)
                .applyTo(AtmosphereState.standardOverworld());
        RespirationOutcome co2Outcome = model.evaluate(co2, RespirationProtection.NONE);
        assertTrue(co2Outcome.hazards().contains(AtmosphericHazard.CARBON_DIOXIDE));
        assertTrue(co2Outcome.hazards().contains(AtmosphericHazard.HYPOXIA));
        assertFalse(co2Outcome.hazards().contains(AtmosphericHazard.PARTICULATES));

        AtmosphereState acid = new AtmosphereState(1.0, 0.2095, 0.00042, 25.0, 0.0, 0.0, 0.0, 0.5, 0.0);
        RespirationOutcome acidOutcome = model.evaluate(acid, RespirationProtection.NONE);
        assertTrue(acidOutcome.hazards().contains(AtmosphericHazard.ACID_GAS));
        assertFalse(acidOutcome.hazards().contains(AtmosphericHazard.HYPOXIA));

        AtmosphereState toxic = new AtmosphereState(1.0, 0.2095, 0.00042, 0.0, 60.0, 0.0, 0.0, 0.5, 0.0);
        RespirationOutcome toxicOutcome = model.evaluate(toxic, RespirationProtection.NONE);
        assertTrue(toxicOutcome.hazards().contains(AtmosphericHazard.TOXIC_GAS));
        assertFalse(toxicOutcome.hazards().contains(AtmosphericHazard.ACID_GAS));
    }
}
