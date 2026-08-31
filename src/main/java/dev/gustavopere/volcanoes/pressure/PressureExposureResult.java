package dev.gustavopere.volcanoes.pressure;

/** Pure gameplay output; world hooks decide how to apply movement, neurological effects and barotrauma. */
public record PressureExposureResult(
        PressureExposureStage stage,
        int exposureTicks,
        double movementMultiplier,
        double neurologicalPenalty,
        double damage
) {
    public PressureExposureResult {
        if (stage == null) {
            throw new NullPointerException("stage");
        }
        if (exposureTicks < 0) {
            throw new IllegalArgumentException("exposureTicks must be non-negative");
        }
        if (!Double.isFinite(movementMultiplier) || movementMultiplier < 0.0 || movementMultiplier > 1.0) {
            throw new IllegalArgumentException("movementMultiplier must be within [0,1]");
        }
        if (!Double.isFinite(neurologicalPenalty) || neurologicalPenalty < 0.0 || neurologicalPenalty > 1.0) {
            throw new IllegalArgumentException("neurologicalPenalty must be within [0,1]");
        }
        if (!Double.isFinite(damage) || damage < 0.0) {
            throw new IllegalArgumentException("damage must be finite and non-negative");
        }
    }
}
