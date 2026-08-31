package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.VolcanicGasEmission;

import java.util.Objects;

/**
 * Stateless Stage04 projection from species-neutral Stage03 gas metadata into Atmosphere hazards.
 *
 * <p>The projection caps are local-air gameplay/hazard policy, not a claim about raw volcanic-gas
 * molar composition. CO2 occupies both its concentration channel and the oxygen-displacement
 * channel; SO2 remains an independent acid-gas channel.</p>
 */
public final class VolcanicGasAtmosphereProjection {
    private VolcanicGasAtmosphereProjection() {
    }

    public static AtmosphericSource project(
            String dimensionId,
            VolcanicGasEmission emission,
            VolcanicGasAtmosphereProjectionPolicy policy
    ) {
        Objects.requireNonNull(dimensionId, "dimensionId");
        Objects.requireNonNull(emission, "emission");
        Objects.requireNonNull(policy, "policy");

        double strength = emission.normalizedEmissionStrength();
        double carbonDioxide = policy.maxCarbonDioxideFraction() * strength;
        double sulfurDioxide = policy.maxSulfurDioxidePpm() * strength;
        AtmosphereContribution contribution = new AtmosphereContribution(
                0.0,
                0.0,
                carbonDioxide,
                sulfurDioxide,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0,
                carbonDioxide);
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
