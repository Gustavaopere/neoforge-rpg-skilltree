package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.GeothermalFeatureType;
import dev.gustavopere.volcanoes.volcano.GeothermalSource;

import java.util.Objects;
import java.util.Optional;

/** Stateless Atmosphere-owned projection of gas-emitting geothermal source families. */
public final class GeothermalAtmosphereProjection {
    private GeothermalAtmosphereProjection() {
    }

    public static Optional<AtmosphericSource> project(
            String dimensionId,
            GeothermalSource source,
            GeothermalAtmosphereProjectionPolicy policy
    ) {
        Objects.requireNonNull(dimensionId, "dimensionId");
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(policy, "policy");

        AtmosphereContribution contribution = switch (source.type()) {
            case FUMAROLE -> new AtmosphereContribution(
                    0.0, 0.0, 0.0, 0.0,
                    policy.maxToxicGasPpm() * source.gasSeverity(),
                    0.0, 0.0, 0.0, 0.0, 0.0);
            case SULFUROUS_VENT -> new AtmosphereContribution(
                    0.0, 0.0, 0.0,
                    policy.maxSulfurDioxidePpm() * source.gasSeverity(),
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
            case HOT_SPRING, GEYSER, MUD_POT -> null;
        };
        if (contribution == null || source.gasSeverity() <= 0.0) {
            return Optional.empty();
        }

        return Optional.of(new AtmosphericSource(
                source.persistenceId(),
                dimensionId,
                source.center().getX(),
                source.center().getY(),
                source.center().getZ(),
                source.radiusBlocks(),
                contribution,
                1.0,
                false,
                AtmosphericSourceEvolution.EXTERNAL));
    }
}
