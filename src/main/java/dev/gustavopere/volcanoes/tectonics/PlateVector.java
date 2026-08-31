package dev.gustavopere.volcanoes.tectonics;

/** Normalized horizontal plate-motion vector. */
public record PlateVector(double x, double z) {
    private static final double UNIT_TOLERANCE = 1.0e-9;

    public PlateVector {
        if (!Double.isFinite(x) || !Double.isFinite(z)) {
            throw new IllegalArgumentException("plate motion components must be finite");
        }
        double length = Math.hypot(x, z);
        if (Math.abs(length - 1.0) > UNIT_TOLERANCE) {
            throw new IllegalArgumentException("plate motion vector must be normalized");
        }
    }

    public double length() {
        return Math.hypot(x, z);
    }

    public static PlateVector fromAngle(double radians) {
        if (!Double.isFinite(radians)) {
            throw new IllegalArgumentException("plate motion angle must be finite");
        }
        return new PlateVector(Math.cos(radians), Math.sin(radians));
    }
}
