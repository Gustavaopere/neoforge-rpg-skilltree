package dev.gustavopere.volcanoes.environment;

import net.minecraft.nbt.CompoundTag;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public record AtmosphericSource(
        UUID id,
        String dimensionId,
        double x,
        double y,
        double z,
        double radiusBlocks,
        AtmosphereContribution contribution,
        double strength,
        boolean persistent,
        AtmosphericSourceEvolution evolution
) {
    public AtmosphericSource(
            UUID id,
            String dimensionId,
            double x,
            double y,
            double z,
            double radiusBlocks,
            AtmosphereContribution contribution,
            double strength,
            boolean persistent
    ) {
        this(
                id,
                dimensionId,
                x,
                y,
                z,
                radiusBlocks,
                contribution,
                strength,
                persistent,
                AtmosphericSourceEvolution.DYNAMIC);
    }

    public AtmosphericSource {
        id = Objects.requireNonNull(id, "id");
        dimensionId = Objects.requireNonNull(dimensionId, "dimensionId");
        if (dimensionId.isBlank()) {
            throw new IllegalArgumentException("dimensionId must not be blank");
        }
        x = finite("x", x);
        y = finite("y", y);
        z = finite("z", z);
        radiusBlocks = nonNegative("radiusBlocks", radiusBlocks);
        contribution = Objects.requireNonNull(contribution, "contribution");
        if (!Double.isFinite(strength) || strength < 0.0 || strength > 1.0) {
            throw new IllegalArgumentException("strength must be within [0, 1]");
        }
        evolution = Objects.requireNonNull(evolution, "evolution");
        if (evolution == AtmosphericSourceEvolution.EXTERNAL && persistent) {
            throw new IllegalArgumentException(
                    "externally managed atmospheric sources must not duplicate upstream persistence");
        }
    }

    public Optional<AtmosphereContribution> contributionAt(double sampleX, double sampleY, double sampleZ) {
        return Optional.ofNullable(localContributionAt(sampleX, sampleY, sampleZ));
    }

    AtmosphereContribution localContributionAt(double sampleX, double sampleY, double sampleZ) {
        finite("sampleX", sampleX);
        finite("sampleY", sampleY);
        finite("sampleZ", sampleZ);
        double dx = sampleX - x;
        double dy = sampleY - y;
        double dz = sampleZ - z;
        double distanceSquared = dx * dx + dy * dy + dz * dz;
        double radiusSquared = radiusBlocks * radiusBlocks;
        if (distanceSquared > radiusSquared) {
            return null;
        }
        double attenuation;
        if (radiusBlocks == 0.0) {
            attenuation = distanceSquared == 0.0 ? 1.0 : 0.0;
        } else {
            attenuation = Math.max(0.0, 1.0 - Math.sqrt(distanceSquared) / radiusBlocks);
        }
        return contribution.scaled(strength * attenuation);
    }

    public Optional<AtmosphericSource> evolve(AtmosphereDynamics dynamics) {
        return evolve(dynamics, AtmosphereTransport.stillAir());
    }

    public Optional<AtmosphericSource> evolve(AtmosphereDynamics dynamics, AtmosphereTransport transport) {
        Objects.requireNonNull(dynamics, "dynamics");
        Objects.requireNonNull(transport, "transport");
        if (evolution == AtmosphericSourceEvolution.EXTERNAL) {
            return Optional.of(this);
        }
        double nextStrength = strength
                * dynamics.retentionPerUpdate()
                * transport.retentionMultiplier();
        if (nextStrength < dynamics.minimumStrength()) {
            return Optional.empty();
        }
        double nextRadius = Math.min(
                dynamics.maximumRadiusBlocks(),
                radiusBlocks + dynamics.diffusionBlocksPerUpdate() * transport.diffusionMultiplier());
        return Optional.of(new AtmosphericSource(
                id,
                dimensionId,
                x + transport.deltaXBlocks(),
                y,
                z + transport.deltaZBlocks(),
                nextRadius,
                contribution,
                nextStrength,
                persistent,
                evolution));
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("id", id);
        tag.putString("dimension", dimensionId);
        tag.putDouble("x", x);
        tag.putDouble("y", y);
        tag.putDouble("z", z);
        tag.putDouble("radius", radiusBlocks);
        tag.putDouble("strength", strength);
        tag.putBoolean("persistent", persistent);
        tag.putString("evolution_mode", evolution.name());
        tag.putDouble("pressure_delta", contribution.pressureDeltaAtm());
        tag.putDouble("oxygen_delta", contribution.oxygenFractionDelta());
        tag.putDouble("co2", contribution.carbonDioxideFraction());
        tag.putDouble("so2", contribution.sulfurDioxidePpm());
        tag.putDouble("toxic", contribution.toxicGasPpm());
        tag.putDouble("particulates", contribution.particulatesMgM3());
        tag.putDouble("smoke", contribution.smokeMgM3());
        tag.putDouble("humidity_delta", contribution.relativeHumidityDelta());
        tag.putDouble("thermal_delta", contribution.thermalModifierDeltaC());
        tag.putDouble("oxygen_displacement", contribution.oxygenDisplacementFraction());
        return tag;
    }

    public static AtmosphericSource fromTag(CompoundTag tag) {
        Objects.requireNonNull(tag, "tag");
        String serializedEvolution = tag.getString("evolution_mode");
        AtmosphericSourceEvolution evolution = serializedEvolution.isBlank()
                ? AtmosphericSourceEvolution.DYNAMIC
                : AtmosphericSourceEvolution.valueOf(serializedEvolution);
        return new AtmosphericSource(
                tag.getUUID("id"),
                tag.getString("dimension"),
                tag.getDouble("x"),
                tag.getDouble("y"),
                tag.getDouble("z"),
                tag.getDouble("radius"),
                new AtmosphereContribution(
                        tag.getDouble("pressure_delta"),
                        tag.getDouble("oxygen_delta"),
                        tag.getDouble("co2"),
                        tag.getDouble("so2"),
                        tag.getDouble("toxic"),
                        tag.getDouble("particulates"),
                        tag.getDouble("smoke"),
                        tag.getDouble("humidity_delta"),
                        tag.getDouble("thermal_delta"),
                        tag.getDouble("oxygen_displacement")),
                tag.getDouble("strength"),
                tag.getBoolean("persistent"),
                evolution);
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
