package dev.gustavopere.volcanoes.compat.coldsweat;

/**
 * Conversion policy from Stage03 normalized volcanic heat severity into Cold Sweat WORLD units.
 *
 * <p>Cold Sweat's MC temperature unit is its environmental/world-temperature unit. A full-strength
 * isolated Volcanoes source contributes 0.5 MC, while overlapping sources saturate at 1.0 MC.
 * The source count is separately bounded so an adapter never turns one player sample into an
 * unbounded reduction.</p>
 */
public record ColdSweatHeatProjectionPolicy(
        double fullSeverityDeltaMc,
        double maxWorldDeltaMc,
        int maxSourcesPerSample
) {
    public ColdSweatHeatProjectionPolicy {
        if (!Double.isFinite(fullSeverityDeltaMc) || fullSeverityDeltaMc <= 0.0) {
            throw new IllegalArgumentException("fullSeverityDeltaMc must be finite and positive");
        }
        if (!Double.isFinite(maxWorldDeltaMc) || maxWorldDeltaMc < fullSeverityDeltaMc) {
            throw new IllegalArgumentException(
                    "maxWorldDeltaMc must be finite and at least fullSeverityDeltaMc");
        }
        if (maxSourcesPerSample <= 0 || maxSourcesPerSample > 64) {
            throw new IllegalArgumentException("maxSourcesPerSample must be within [1, 64]");
        }
    }

    public static ColdSweatHeatProjectionPolicy defaults() {
        return new ColdSweatHeatProjectionPolicy(0.5, 1.0, 32);
    }
}
