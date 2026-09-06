package dev.gustavopere.volcanoes.environment;

/** Atmosphere-owned conversion from normalized Stage03 gas emission into canonical field units. */
public record VolcanicGasAtmosphereProjectionPolicy(
        double maxCarbonDioxideFraction,
        double maxSulfurDioxidePpm
) {
    public VolcanicGasAtmosphereProjectionPolicy {
        if (!Double.isFinite(maxCarbonDioxideFraction)
                || maxCarbonDioxideFraction <= 0.0
                || maxCarbonDioxideFraction > 1.0) {
            throw new IllegalArgumentException("maxCarbonDioxideFraction must be within (0, 1]");
        }
        if (!Double.isFinite(maxSulfurDioxidePpm) || maxSulfurDioxidePpm <= 0.0) {
            throw new IllegalArgumentException("maxSulfurDioxidePpm must be finite and positive");
        }
    }

    public static VolcanicGasAtmosphereProjectionPolicy defaults() {
        return new VolcanicGasAtmosphereProjectionPolicy(0.12, 50.0);
    }
}
