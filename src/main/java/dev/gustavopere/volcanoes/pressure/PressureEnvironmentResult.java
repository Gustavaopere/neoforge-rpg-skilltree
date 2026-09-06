package dev.gustavopere.volcanoes.pressure;

/**
 * One resolved pressure view for an entity update.
 *
 * <p>Physical pressure values remain separate from gameplay protection. Equipment reduces
 * {@code protectedOverpressureAtm}; it never rewrites the external or experienced pressure.
 */
public record PressureEnvironmentResult(
        boolean sealedInterior,
        double externalPressureAtm,
        double experiencedPressureAtm,
        double unprotectedOverpressureAtm,
        double protectedOverpressureAtm,
        double appliedPressureRatingAtm
) {
    public PressureEnvironmentResult {
        requireNonNegative("externalPressureAtm", externalPressureAtm);
        requireNonNegative("experiencedPressureAtm", experiencedPressureAtm);
        requireNonNegative("unprotectedOverpressureAtm", unprotectedOverpressureAtm);
        requireNonNegative("protectedOverpressureAtm", protectedOverpressureAtm);
        requireNonNegative("appliedPressureRatingAtm", appliedPressureRatingAtm);
        if (protectedOverpressureAtm > unprotectedOverpressureAtm) {
            throw new IllegalArgumentException("protected overpressure cannot exceed unprotected overpressure");
        }
        if (appliedPressureRatingAtm > unprotectedOverpressureAtm) {
            throw new IllegalArgumentException("applied pressure rating cannot exceed unprotected overpressure");
        }
    }

    private static void requireNonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
    }
}
