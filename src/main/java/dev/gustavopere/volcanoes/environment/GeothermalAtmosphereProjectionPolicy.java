package dev.gustavopere.volcanoes.environment;

/**
 * Atmosphere-owned conversion from normalized Stage-03 geothermal gas severity into Atmosphere
 * concentration units. These are local source maxima, not claims about physical vent flow rates.
 */
public record GeothermalAtmosphereProjectionPolicy(
        double maxToxicGasPpm,
        double maxSulfurDioxidePpm
) {
    public GeothermalAtmosphereProjectionPolicy {
        requirePositive("maxToxicGasPpm", maxToxicGasPpm);
        requirePositive("maxSulfurDioxidePpm", maxSulfurDioxidePpm);
    }

    public static GeothermalAtmosphereProjectionPolicy defaults() {
        RespirationThresholds thresholds = RespirationThresholds.defaults();
        return new GeothermalAtmosphereProjectionPolicy(
                thresholds.toxicGasPpm() * 2.0,
                thresholds.acidGasPpm() * 2.0);
    }

    private static void requirePositive(String name, double value) {
        if (!Double.isFinite(value) || value <= 0.0) {
            throw new IllegalArgumentException(name + " must be finite and positive");
        }
    }
}
