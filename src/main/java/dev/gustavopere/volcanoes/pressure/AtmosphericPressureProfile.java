package dev.gustavopere.volcanoes.pressure;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/** A named atmospheric-pressure definition and its documented dimension baseline. */
public record AtmosphericPressureProfile(
        ResourceLocation id,
        double baselineY,
        double baselineAtm,
        PressureCurve curve
) {
    private static final double BASELINE_EPSILON = 1.0e-9;

    public AtmosphericPressureProfile {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(curve, "curve");
        if (!Double.isFinite(baselineY)) {
            throw new IllegalArgumentException("baselineY must be finite");
        }
        if (!Double.isFinite(baselineAtm) || baselineAtm < 0.0) {
            throw new IllegalArgumentException("baselineAtm must be finite and non-negative");
        }
        if (Math.abs(curve.pressureAtm(baselineY) - baselineAtm) > BASELINE_EPSILON) {
            throw new IllegalArgumentException("baselineAtm must match the configured pressure curve at baselineY");
        }
    }

    public double pressureAtm(double altitudeY) {
        return curve.pressureAtm(altitudeY);
    }
}
