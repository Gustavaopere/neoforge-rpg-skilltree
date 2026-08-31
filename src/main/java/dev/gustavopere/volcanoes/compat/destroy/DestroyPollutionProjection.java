package dev.gustavopere.volcanoes.compat.destroy;

import dev.gustavopere.volcanoes.environment.PollutionLoad;

import java.util.Objects;

/**
 * Host-neutral projection onto the four Destroy 0.4.1 pollution types with a defensible Volcanoes mapping.
 * Particulate load intentionally remains outside Destroy because 0.4.1 has no distinct particulate type.
 */
public record DestroyPollutionProjection(
        double acidRain,
        double smog,
        double greenhouse,
        double ozoneDepletion
) {
    public DestroyPollutionProjection {
        acidRain = requireNonNegativeFinite("acidRain", acidRain);
        smog = requireNonNegativeFinite("smog", smog);
        greenhouse = requireNonNegativeFinite("greenhouse", greenhouse);
        ozoneDepletion = requireNonNegativeFinite("ozoneDepletion", ozoneDepletion);
    }

    public static DestroyPollutionProjection from(PollutionLoad load) {
        PollutionLoad value = Objects.requireNonNull(load, "load");
        return new DestroyPollutionProjection(
                value.acidifyingLoad(),
                value.smogLoad(),
                value.greenhouseLoad(),
                value.ozoneAffectingLoad());
    }

    public boolean hasSupportedLoad() {
        return acidRain > 0.0 || smog > 0.0 || greenhouse > 0.0 || ozoneDepletion > 0.0;
    }

    public boolean mapsParticulates() {
        return false;
    }

    private static double requireNonNegativeFinite(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
        return value;
    }
}
