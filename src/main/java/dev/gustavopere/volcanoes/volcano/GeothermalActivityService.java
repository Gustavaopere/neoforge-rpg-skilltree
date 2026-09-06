package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;

import java.util.Objects;

/**
 * Pure deterministic geothermal-potential model shared by worldgen and runtime adapters.
 *
 * <p>The service deliberately owns no world or persistence state. Static tectonic samples can
 * therefore be evaluated during chunk generation, while callers that have an authoritative
 * magma chamber may add its local contribution explicitly.</p>
 */
public final class GeothermalActivityService {
    private static final double MIN_THERMAL_KELVIN = 700.0;
    private static final double THERMAL_RANGE_KELVIN = 900.0;

    private final double influenceRadiusBlocks;

    public GeothermalActivityService(double influenceRadiusBlocks) {
        if (!Double.isFinite(influenceRadiusBlocks) || influenceRadiusBlocks <= 0.0) {
            throw new IllegalArgumentException("influenceRadiusBlocks must be finite and positive");
        }
        this.influenceRadiusBlocks = influenceRadiusBlocks;
    }

    /** Returns tectonic geothermal potential in the closed interval {@code [0, 1]}. */
    public double potential(TectonicSample sample) {
        Objects.requireNonNull(sample, "sample");

        double boundaryProximity = clampUnit(1.0 - sample.boundaryDistanceBlocks() / influenceRadiusBlocks);
        double context = contextContribution(sample.context());
        double boundary = switch (sample.context()) {
            case CONVERGENT, DIVERGENT, TRANSFORM -> boundaryProximity * 0.10;
            case HOTSPOT, INTERIOR -> 0.0;
        };

        return clampUnit(
                sample.volcanicPotential() * 0.45
                        + sample.stress() * 0.15
                        + context
                        + boundary);
    }

    /**
     * Adds a local magma contribution to the static tectonic potential.
     *
     * <p>The chamber contribution decays linearly to zero at the configured influence radius and
     * is combined saturatingly with the tectonic baseline, so additional heat can never push the
     * result above one.</p>
     */
    public double potential(TectonicSample sample, double magmaDistanceBlocks, MagmaChamber chamber) {
        Objects.requireNonNull(sample, "sample");
        Objects.requireNonNull(chamber, "chamber");
        if (!Double.isFinite(magmaDistanceBlocks) || magmaDistanceBlocks < 0.0) {
            throw new IllegalArgumentException("magmaDistanceBlocks must be finite and non-negative");
        }

        double tectonic = potential(sample);
        double proximity = clampUnit(1.0 - magmaDistanceBlocks / influenceRadiusBlocks);
        if (proximity <= 0.0) {
            return tectonic;
        }

        double thermal = clampUnit((chamber.temperatureKelvin() - MIN_THERMAL_KELVIN) / THERMAL_RANGE_KELVIN);
        double volume = clampUnit(chamber.volumeCubicKilometers() / 16.0);
        double pressure = clampUnit(chamber.pressureMegapascals() / 350.0);
        double gas = clampUnit(chamber.gasFraction());
        double replenishment = clampUnit(chamber.replenishmentCubicKilometersPerDay() / 0.60);

        double chamberActivity = clampUnit(
                thermal * 0.35
                        + volume * 0.20
                        + pressure * 0.15
                        + gas * 0.10
                        + replenishment * 0.20);
        double magma = proximity * chamberActivity;

        return clampUnit(1.0 - (1.0 - tectonic) * (1.0 - magma));
    }

    private static double contextContribution(TectonicContext context) {
        return switch (Objects.requireNonNull(context, "context")) {
            case INTERIOR -> 0.0;
            case TRANSFORM -> 0.08;
            case DIVERGENT -> 0.18;
            case CONVERGENT -> 0.24;
            case HOTSPOT -> 0.40;
        };
    }

    private static double clampUnit(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
