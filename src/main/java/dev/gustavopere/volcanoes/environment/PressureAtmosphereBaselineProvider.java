package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.pressure.AtmosphericPressureRuntime;

import java.util.Objects;

/**
 * Composes Stage04 atmosphere chemistry with the canonical Stage05 atmospheric-pressure authority.
 * Pressure physics remains owned by Stage05; this adapter only replaces totalPressureAtm in the
 * sampled Stage04 baseline state.
 */
public final class PressureAtmosphereBaselineProvider implements AtmosphereBaselineProvider {
    @FunctionalInterface
    public interface PressureLookup {
        double pressureAtm(String dimensionId, double altitudeY);
    }

    private final AtmosphereBaselineProvider chemistryBaseline;
    private final PressureLookup pressureLookup;

    public PressureAtmosphereBaselineProvider(
            AtmosphereBaselineProvider chemistryBaseline,
            PressureLookup pressureLookup
    ) {
        this.chemistryBaseline = Objects.requireNonNull(chemistryBaseline, "chemistryBaseline");
        this.pressureLookup = Objects.requireNonNull(pressureLookup, "pressureLookup");
    }

    public static PressureAtmosphereBaselineProvider canonical(
            AtmosphereBaselineProvider chemistryBaseline
    ) {
        return new PressureAtmosphereBaselineProvider(
                chemistryBaseline,
                AtmosphericPressureRuntime::pressureAtm);
    }

    @Override
    public AtmosphereState sample(String dimensionId, double y) {
        Objects.requireNonNull(dimensionId, "dimensionId");
        if (!Double.isFinite(y)) {
            throw new IllegalArgumentException("y must be finite");
        }

        AtmosphereState chemistry = Objects.requireNonNull(
                chemistryBaseline.sample(dimensionId, y),
                "chemistryBaseline returned null");
        double pressureAtm = pressureLookup.pressureAtm(dimensionId, y);
        if (!Double.isFinite(pressureAtm) || pressureAtm < 0.0) {
            throw new IllegalStateException("canonical pressure must be finite and non-negative");
        }

        return new AtmosphereState(
                pressureAtm,
                chemistry.oxygenFraction(),
                chemistry.carbonDioxideFraction(),
                chemistry.sulfurDioxidePpm(),
                chemistry.toxicGasPpm(),
                chemistry.particulatesMgM3(),
                chemistry.smokeMgM3(),
                chemistry.relativeHumidity(),
                chemistry.thermalModifierC());
    }
}
