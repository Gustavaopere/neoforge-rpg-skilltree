package dev.gustavopere.volcanoes.pressure;

import java.util.Objects;

/** Converts the continuous exposure model into bounded vanilla-facing effects. */
public final class PressureEntityEffectPolicy {
    private static final int DAMAGE_PULSE_TICKS = 20;
    private static final int MAX_MOVEMENT_AMPLIFIER = 3;
    private static final int MAX_NEUROLOGICAL_AMPLIFIER = 1;

    private PressureEntityEffectPolicy() {
    }

    public static PressureEntityEffectPlan plan(PressureExposureResult exposure) {
        Objects.requireNonNull(exposure, "exposure");

        if (exposure.stage() == PressureExposureStage.NORMAL
                || exposure.stage() == PressureExposureStage.GRACE
                || exposure.stage() == PressureExposureStage.DISCOMFORT) {
            return PressureEntityEffectPlan.none();
        }

        int movementAmplifier = movementAmplifier(exposure.movementMultiplier());
        int neurologicalAmplifier = neurologicalAmplifier(exposure.neurologicalPenalty());
        double damage = exposure.stage() == PressureExposureStage.BAROTRAUMA
                && exposure.exposureTicks() % DAMAGE_PULSE_TICKS == 0
                ? exposure.damage()
                : 0.0;

        return new PressureEntityEffectPlan(
                exposure.movementMultiplier() < 1.0,
                movementAmplifier,
                exposure.neurologicalPenalty() > 0.0,
                neurologicalAmplifier,
                damage);
    }

    private static int movementAmplifier(double movementMultiplier) {
        if (movementMultiplier >= 1.0) {
            return 0;
        }

        int closest = 0;
        double closestDistance = Double.POSITIVE_INFINITY;
        for (int amplifier = 0; amplifier <= MAX_MOVEMENT_AMPLIFIER; amplifier++) {
            double vanillaMultiplier = Math.max(0.0, 1.0 - 0.15 * (amplifier + 1));
            double distance = Math.abs(vanillaMultiplier - movementMultiplier);
            if (distance < closestDistance) {
                closestDistance = distance;
                closest = amplifier;
            }
        }
        return closest;
    }

    private static int neurologicalAmplifier(double penalty) {
        if (penalty <= 0.0) {
            return 0;
        }
        return Math.min(MAX_NEUROLOGICAL_AMPLIFIER, Math.max(0, (int) Math.ceil(penalty * 2.0) - 1));
    }
}
