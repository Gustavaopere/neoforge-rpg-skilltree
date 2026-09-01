package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class PressureEntityEffectPolicyTest {
    @Test
    void normalAndGraceDoNotApplyGameplayPenalties() {
        PressureEntityEffectPlan normal = PressureEntityEffectPolicy.plan(
                new PressureExposureResult(PressureExposureStage.NORMAL, 0, 1.0, 0.0, 0.0));
        PressureEntityEffectPlan grace = PressureEntityEffectPolicy.plan(
                new PressureExposureResult(PressureExposureStage.GRACE, 80, 1.0, 0.0, 0.0));

        assertFalse(normal.movementPenalty());
        assertFalse(normal.neurologicalPenalty());
        assertEquals(0.0, normal.damage(), 1.0e-9);
        assertEquals(normal, grace);
    }

    @Test
    void impairmentMapsContinuousModelToBoundedVanillaEffects() {
        PressureEntityEffectPlan plan = PressureEntityEffectPolicy.plan(
                new PressureExposureResult(PressureExposureStage.IMPAIRED, 101, 0.80, 0.50, 0.0));

        assertTrue(plan.movementPenalty());
        assertEquals(0, plan.movementAmplifier(), "0.80 target is closest to Slowness I");
        assertTrue(plan.neurologicalPenalty());
        assertEquals(0, plan.neurologicalAmplifier());
        assertEquals(0.0, plan.damage(), 1.0e-9);
    }

    @Test
    void barotraumaDamageIsPulsedOncePerSecondInsteadOfEveryTick() {
        PressureEntityEffectPlan pulse = PressureEntityEffectPolicy.plan(
                new PressureExposureResult(PressureExposureStage.BAROTRAUMA, 120, 0.80, 0.50, 2.0));
        PressureEntityEffectPlan betweenPulses = PressureEntityEffectPolicy.plan(
                new PressureExposureResult(PressureExposureStage.BAROTRAUMA, 121, 0.80, 0.50, 2.0));

        assertEquals(2.0, pulse.damage(), 1.0e-9);
        assertEquals(0.0, betweenPulses.damage(), 1.0e-9);
        assertTrue(pulse.movementPenalty());
        assertTrue(pulse.neurologicalPenalty());
    }

    @Test
    void effectAmplifiersAreCappedForExtremeConfigValues() {
        PressureEntityEffectPlan plan = PressureEntityEffectPolicy.plan(
                new PressureExposureResult(PressureExposureStage.IMPAIRED, 200, 0.0, 1.0, 0.0));

        assertEquals(3, plan.movementAmplifier());
        assertEquals(1, plan.neurologicalAmplifier());
    }
}
