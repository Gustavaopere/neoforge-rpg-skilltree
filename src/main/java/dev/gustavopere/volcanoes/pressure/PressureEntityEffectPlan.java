package dev.gustavopere.volcanoes.pressure;

/** Bounded vanilla-facing effects derived from one pressure exposure update. */
public record PressureEntityEffectPlan(
        boolean movementPenalty,
        int movementAmplifier,
        boolean neurologicalPenalty,
        int neurologicalAmplifier,
        double damage
) {
    public PressureEntityEffectPlan {
        if (movementAmplifier < 0) {
            throw new IllegalArgumentException("movementAmplifier must be non-negative");
        }
        if (neurologicalAmplifier < 0) {
            throw new IllegalArgumentException("neurologicalAmplifier must be non-negative");
        }
        if (!Double.isFinite(damage) || damage < 0.0) {
            throw new IllegalArgumentException("damage must be finite and non-negative");
        }
        if (!movementPenalty) {
            movementAmplifier = 0;
        }
        if (!neurologicalPenalty) {
            neurologicalAmplifier = 0;
        }
    }

    public static PressureEntityEffectPlan none() {
        return new PressureEntityEffectPlan(false, 0, false, 0, 0.0);
    }
}
