package dev.gustavopere.volcanoes.tectonics;

/** Server-applicable entity effect values derived from one seismic event and observer position. */
public record SeismicEntityEffectProfile(
        double intensity,
        double shakeAmplitude,
        double soundVolume,
        int movementInstabilityTicks
) {
    public SeismicEntityEffectProfile {
        intensity = requireUnit("intensity", intensity);
        shakeAmplitude = requireUnit("shakeAmplitude", shakeAmplitude);
        soundVolume = requireUnit("soundVolume", soundVolume);
        if (movementInstabilityTicks < 0) {
            throw new IllegalArgumentException("movementInstabilityTicks must be non-negative");
        }
    }

    public static SeismicEntityEffectProfile at(SeismicEvent event, double x, double z) {
        double intensity = event.intensityAt(x, z);
        if (intensity <= 0.0) {
            return new SeismicEntityEffectProfile(0.0, 0.0, 0.0, 0);
        }
        double shake = Math.min(1.0, intensity * 1.15);
        double sound = Math.min(1.0, 0.20 + intensity * 0.80);
        int instabilityTicks = Math.max(1, (int) Math.ceil(intensity * 40.0));
        return new SeismicEntityEffectProfile(intensity, shake, sound, instabilityTicks);
    }

    private static double requireUnit(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0, 1]");
        }
        return value;
    }
}
