package dev.gustavopere.volcanoes.compat.create;

import dev.gustavopere.volcanoes.environment.AtmosphereState;
import dev.gustavopere.volcanoes.environment.AtmosphericHazard;
import dev.gustavopere.volcanoes.environment.RespirationModel;
import dev.gustavopere.volcanoes.environment.RespirationProtection;
import dev.gustavopere.volcanoes.environment.RespirationThresholds;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CreateRespirationDecisionContractTest {
    @Test
    void safeAtmosphereDoesNotSpendBacktankAir() {
        CreateRespirationDecision decision = CreateRespirationDecision.evaluate(
                AtmosphereState.standardOverworld(), true, true, 20L);

        assertEquals(RespirationProtection.NONE, decision.protection());
        assertEquals(0, decision.airDebitUnits());
    }

    @Test
    void lowOxygenWithHelmetAndBacktankSuppliesOxygenOnly() {
        AtmosphereState thinAir = state(0.30, 0.21, 0.00042, 0.0, 0.0, 0.0);
        CreateRespirationDecision decision = CreateRespirationDecision.evaluate(thinAir, true, true, 20L);

        assertEquals(0.0, decision.protection().particulateFilterEfficiency(), 1.0e-12);
        assertEquals(0.0, decision.protection().acidGasFilterEfficiency(), 1.0e-12);
        assertEquals(0.0, decision.protection().toxicGasFilterEfficiency(), 1.0e-12);
        assertEquals(CreateRespirationDecision.BREATHABLE_OXYGEN_PARTIAL_PRESSURE_ATM,
                decision.protection().oxygenSupplyPartialPressureAtm(), 1.0e-12);
        assertEquals(1, decision.airDebitUnits());

        var outcome = new RespirationModel(RespirationThresholds.defaults())
                .evaluate(thinAir, decision.protection());
        assertFalse(outcome.hazards().contains(AtmosphericHazard.HYPOXIA));
    }

    @Test
    void debitUsesCreateTwentyTickCadenceExactlyOnce() {
        AtmosphereState thinAir = state(0.30, 0.21, 0.00042, 0.0, 0.0, 0.0);

        assertEquals(0, CreateRespirationDecision.evaluate(thinAir, true, true, 19L).airDebitUnits());
        assertEquals(1, CreateRespirationDecision.evaluate(thinAir, true, true, 20L).airDebitUnits());
        assertEquals(0, CreateRespirationDecision.evaluate(thinAir, true, true, 21L).airDebitUnits());
        assertEquals(1, CreateRespirationDecision.evaluate(thinAir, true, true, 40L).airDebitUnits());
    }

    @Test
    void missingHelmetOrDepletedBacktankProvidesNothing() {
        AtmosphereState thinAir = state(0.30, 0.21, 0.00042, 0.0, 0.0, 0.0);

        assertEquals(RespirationProtection.NONE,
                CreateRespirationDecision.evaluate(thinAir, false, true, 20L).protection());
        assertEquals(RespirationProtection.NONE,
                CreateRespirationDecision.evaluate(thinAir, true, false, 20L).protection());
        assertEquals(0, CreateRespirationDecision.evaluate(thinAir, false, true, 20L).airDebitUnits());
        assertEquals(0, CreateRespirationDecision.evaluate(thinAir, true, false, 20L).airDebitUnits());
    }

    @Test
    void oxygenSupplyNeverInventsContaminantFiltration() {
        AtmosphereState contaminatedThinAir = state(0.30, 0.21, 0.02, 100.0, 100.0, 100.0);
        CreateRespirationDecision decision = CreateRespirationDecision.evaluate(
                contaminatedThinAir, true, true, 20L);
        var outcome = new RespirationModel(RespirationThresholds.defaults())
                .evaluate(contaminatedThinAir, decision.protection());

        assertFalse(outcome.hazards().contains(AtmosphericHazard.HYPOXIA));
        assertTrue(outcome.hazards().contains(AtmosphericHazard.CARBON_DIOXIDE));
        assertTrue(outcome.hazards().contains(AtmosphericHazard.ACID_GAS));
        assertTrue(outcome.hazards().contains(AtmosphericHazard.TOXIC_GAS));
        assertTrue(outcome.hazards().contains(AtmosphericHazard.PARTICULATES));
    }

    private static AtmosphereState state(
            double pressure,
            double oxygen,
            double carbonDioxide,
            double sulfurDioxide,
            double toxicGas,
            double particulates
    ) {
        return new AtmosphereState(
                pressure, oxygen, carbonDioxide, sulfurDioxide, toxicGas,
                particulates, particulates, 0.5, 0.0);
    }
}
