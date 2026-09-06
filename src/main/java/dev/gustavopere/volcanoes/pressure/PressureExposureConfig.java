package dev.gustavopere.volcanoes.pressure;

/** Configurable staged response to unprotected gauge pressure, expressed in atmospheres. */
public record PressureExposureConfig(
        int graceTicks,
        double discomfortOverpressureAtm,
        double impairmentOverpressureAtm,
        double barotraumaOverpressureAtm,
        double impairedMovementMultiplier,
        double neurologicalPenalty,
        double barotraumaDamagePerUpdate
) {
    public PressureExposureConfig {
        if (graceTicks < 0) {
            throw new IllegalArgumentException("graceTicks must be non-negative");
        }
        requireNonNegative("discomfortOverpressureAtm", discomfortOverpressureAtm);
        requireNonNegative("impairmentOverpressureAtm", impairmentOverpressureAtm);
        requireNonNegative("barotraumaOverpressureAtm", barotraumaOverpressureAtm);
        if (impairmentOverpressureAtm < discomfortOverpressureAtm
                || barotraumaOverpressureAtm < impairmentOverpressureAtm) {
            throw new IllegalArgumentException("pressure thresholds must be monotonic");
        }
        requireUnit("impairedMovementMultiplier", impairedMovementMultiplier);
        requireUnit("neurologicalPenalty", neurologicalPenalty);
        requireNonNegative("barotraumaDamagePerUpdate", barotraumaDamagePerUpdate);
    }

    public static PressureExposureConfig defaults() {
        return new PressureExposureConfig(100, 0.75, 1.5, 2.5, 0.80, 0.50, 2.0);
    }

    private static void requireNonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
    }

    private static void requireUnit(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0,1]");
        }
    }
}
