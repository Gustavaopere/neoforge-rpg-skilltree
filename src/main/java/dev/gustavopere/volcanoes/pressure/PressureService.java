package dev.gustavopere.volcanoes.pressure;

@FunctionalInterface
public interface PressureService {
    double STANDARD_GRAVITY_M_S2 = 9.80665;
    double PASCALS_PER_ATMOSPHERE = 101_325.0;

    PressureSample sample(double atmosphericAtm, double depthMeters, double fluidDensityKgM3);

    static PressureService fallback() {
        return withGravity(STANDARD_GRAVITY_M_S2);
    }

    static PressureService withGravity(double gravityMS2) {
        if (!Double.isFinite(gravityMS2) || gravityMS2 < 0.0) {
            throw new IllegalArgumentException("gravityMS2 must be finite and non-negative");
        }
        return (atmosphericAtm, depthMeters, fluidDensityKgM3) -> {
            if (!Double.isFinite(atmosphericAtm) || atmosphericAtm < 0.0) {
                throw new IllegalArgumentException("atmosphericAtm must be finite and non-negative");
            }
            if (!Double.isFinite(depthMeters)) {
                throw new IllegalArgumentException("depthMeters must be finite");
            }
            if (!Double.isFinite(fluidDensityKgM3) || fluidDensityKgM3 < 0.0) {
                throw new IllegalArgumentException("fluidDensityKgM3 must be finite and non-negative");
            }
            double depth = Math.max(0.0, depthMeters);
            double hydrostaticAtm = fluidDensityKgM3 * gravityMS2 * depth / PASCALS_PER_ATMOSPHERE;
            return new PressureSample(atmosphericAtm, hydrostaticAtm);
        };
    }
}
