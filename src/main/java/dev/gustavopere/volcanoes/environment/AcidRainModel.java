package dev.gustavopere.volcanoes.environment;

import java.util.Objects;

public final class AcidRainModel {
    private final double minimumAcidifyingLoad;
    private final double minimumPrecipitationIntensity;

    public AcidRainModel(double minimumAcidifyingLoad, double minimumPrecipitationIntensity) {
        if (!Double.isFinite(minimumAcidifyingLoad) || minimumAcidifyingLoad < 0.0) {
            throw new IllegalArgumentException("minimumAcidifyingLoad must be finite and non-negative");
        }
        if (!Double.isFinite(minimumPrecipitationIntensity)
                || minimumPrecipitationIntensity < 0.0
                || minimumPrecipitationIntensity > 1.0) {
            throw new IllegalArgumentException("minimumPrecipitationIntensity must be within [0, 1]");
        }
        this.minimumAcidifyingLoad = minimumAcidifyingLoad;
        this.minimumPrecipitationIntensity = minimumPrecipitationIntensity;
    }

    public boolean isAcidRain(PollutionLoad load, double precipitationIntensity) {
        Objects.requireNonNull(load, "load");
        if (!Double.isFinite(precipitationIntensity) || precipitationIntensity < 0.0 || precipitationIntensity > 1.0) {
            throw new IllegalArgumentException("precipitationIntensity must be within [0, 1]");
        }
        return load.acidifyingLoad() > 0.0
                && precipitationIntensity > 0.0
                && load.acidifyingLoad() >= minimumAcidifyingLoad
                && precipitationIntensity >= minimumPrecipitationIntensity;
    }
}
