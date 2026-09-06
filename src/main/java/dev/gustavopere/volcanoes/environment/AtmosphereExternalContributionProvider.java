package dev.gustavopere.volcanoes.environment;

import java.util.Objects;

/**
 * Neutral readback port for externally authoritative environmental contributions.
 *
 * <p>This is intentionally a sampled contribution, not a second source registry. Optional adapters
 * such as Destroy may project external pollution into the single Atmosphere state without mirroring
 * that authority into Atmosphere persistence or indexed source lifecycle.</p>
 */
@FunctionalInterface
public interface AtmosphereExternalContributionProvider {
    AtmosphereContribution sample(String dimensionId, double x, double y, double z);

    static AtmosphereExternalContributionProvider none() {
        return (dimensionId, x, y, z) -> {
            Objects.requireNonNull(dimensionId, "dimensionId");
            return AtmosphereContribution.none();
        };
    }
}
