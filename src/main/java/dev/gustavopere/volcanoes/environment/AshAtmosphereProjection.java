package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.AshPlumeEmission;

import java.util.Objects;

/** Stateless mapping from the authoritative Stage-03 ash descriptor into one Atmosphere source. */
public final class AshAtmosphereProjection {
    private AshAtmosphereProjection() {
    }

    public static AtmosphericSource project(
            String dimensionId,
            AshPlumeEmission emission,
            AshAtmosphereProjectionPolicy policy
    ) {
        Objects.requireNonNull(dimensionId, "dimensionId");
        Objects.requireNonNull(emission, "emission");
        Objects.requireNonNull(policy, "policy");

        AtmosphereContribution contribution = new AtmosphereContribution(
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                policy.maxParticulatesMgM3() * emission.particulateStrength(),
                policy.maxSmokeMgM3() * emission.smokeStrength(),
                0.0,
                0.0,
                0.0);
        return new AtmosphericSource(
                emission.sourceId(),
                dimensionId,
                emission.source().getX(),
                emission.source().getY(),
                emission.source().getZ(),
                emission.radiusBlocks(),
                contribution,
                1.0,
                false,
                AtmosphericSourceEvolution.EXTERNAL);
    }
}
