package dev.gustavopere.volcanoes.environment;

/** Neutral atmospheric transport sample; adapters may supply wind/weather later. */
public record AtmosphereTransport(
        double deltaXBlocks,
        double deltaZBlocks,
        double diffusionMultiplier,
        double retentionMultiplier
) {
    public AtmosphereTransport {
        deltaXBlocks = finite("deltaXBlocks", deltaXBlocks);
        deltaZBlocks = finite("deltaZBlocks", deltaZBlocks);
        diffusionMultiplier = nonNegative("diffusionMultiplier", diffusionMultiplier);
        if (!Double.isFinite(retentionMultiplier) || retentionMultiplier < 0.0 || retentionMultiplier > 1.0) {
            throw new IllegalArgumentException("retentionMultiplier must be within [0, 1]");
        }
    }

    public static AtmosphereTransport stillAir() {
        return new AtmosphereTransport(0.0, 0.0, 1.0, 1.0);
    }

    private static double finite(String name, double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
        return value;
    }

    private static double nonNegative(String name, double value) {
        finite(name, value);
        if (value < 0.0) {
            throw new IllegalArgumentException(name + " must be non-negative");
        }
        return value;
    }
}
