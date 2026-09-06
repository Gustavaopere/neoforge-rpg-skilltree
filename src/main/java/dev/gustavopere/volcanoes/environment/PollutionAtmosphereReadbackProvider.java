package dev.gustavopere.volcanoes.environment;

import java.util.Objects;

/**
 * Projects pollution owned by an external authority back into the single Atmosphere vector.
 *
 * <p>The coordinator contract samples only external pollution. This provider never publishes an
 * emission and never creates mirrored atmospheric sources, so the readback path cannot itself
 * double-count Volcanoes emissions.</p>
 */
public final class PollutionAtmosphereReadbackProvider implements AtmosphereExternalContributionProvider {
    private final PollutionCoordinator coordinator;
    private final AtmosphericPollutionFallback projection;

    public PollutionAtmosphereReadbackProvider(
            PollutionCoordinator coordinator,
            AtmosphericPollutionFallback projection
    ) {
        this.coordinator = Objects.requireNonNull(coordinator, "coordinator");
        this.projection = Objects.requireNonNull(projection, "projection");
    }

    @Override
    public AtmosphereContribution sample(String dimensionId, double x, double y, double z) {
        return projection.contributionFor(coordinator.sampleExternalOnly(dimensionId, x, y, z));
    }
}
