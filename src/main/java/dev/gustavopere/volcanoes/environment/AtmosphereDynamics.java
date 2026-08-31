package dev.gustavopere.volcanoes.environment;

public record AtmosphereDynamics(
        double retentionPerUpdate,
        double diffusionBlocksPerUpdate,
        double minimumStrength,
        double maximumRadiusBlocks
) {
    private static final double DEFAULT_MAXIMUM_RADIUS_BLOCKS = 1_024.0;

    public AtmosphereDynamics(double retentionPerUpdate, double diffusionBlocksPerUpdate, double minimumStrength) {
        this(retentionPerUpdate, diffusionBlocksPerUpdate, minimumStrength, DEFAULT_MAXIMUM_RADIUS_BLOCKS);
    }

    public AtmosphereDynamics {
        if (!Double.isFinite(retentionPerUpdate) || retentionPerUpdate < 0.0 || retentionPerUpdate > 1.0) {
            throw new IllegalArgumentException("retentionPerUpdate must be within [0, 1]");
        }
        if (!Double.isFinite(diffusionBlocksPerUpdate) || diffusionBlocksPerUpdate < 0.0) {
            throw new IllegalArgumentException("diffusionBlocksPerUpdate must be finite and non-negative");
        }
        if (!Double.isFinite(minimumStrength) || minimumStrength < 0.0 || minimumStrength > 1.0) {
            throw new IllegalArgumentException("minimumStrength must be within [0, 1]");
        }
        if (!Double.isFinite(maximumRadiusBlocks) || maximumRadiusBlocks <= 0.0) {
            throw new IllegalArgumentException("maximumRadiusBlocks must be finite and positive");
        }
    }

    public static AtmosphereDynamics defaults() {
        return new AtmosphereDynamics(0.985, 1.0, 0.01, DEFAULT_MAXIMUM_RADIUS_BLOCKS);
    }
}
