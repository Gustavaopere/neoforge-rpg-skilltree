package dev.gustavopere.volcanoes.pressure;

/**
 * Water depth in gameplay meters (one block = one meter) and whether the connected free surface was proven.
 * When {@code surfaceResolved} is false, {@code depthMeters} is only the vertical water head proven by the
 * bounded search; graph/path distance must never be substituted for hydrostatic depth.
 */
public record WaterDepthSample(double depthMeters, boolean surfaceResolved) {
    public WaterDepthSample {
        if (!Double.isFinite(depthMeters) || depthMeters < 0.0) {
            throw new IllegalArgumentException("depthMeters must be finite and non-negative");
        }
    }
}
