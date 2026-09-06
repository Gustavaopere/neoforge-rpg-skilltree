package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.SeismicEvent;
import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import dev.gustavopere.volcanoes.tectonics.TectonicService;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** Deterministic coarse magma lifecycle and hysteretic volcano state transitions. */
public final class VolcanoManager {
    private static final double ACTIVATE_PRESSURE_MPA = 180.0;
    private static final double DEACTIVATE_PRESSURE_MPA = 120.0;
    private static final double ERUPTION_PRESSURE_MPA = 275.0;
    private static final double ERUPTION_GAS_FRACTION = 0.12;
    private static final double RELAXED_PRESSURE_MPA = 160.0;
    private static final double RELAXED_GAS_FRACTION = 0.065;
    private static final double TICKS_PER_DAY = 24_000.0;

    private final VolcanoSavedData data;
    private final TectonicService tectonics;

    public VolcanoManager(VolcanoSavedData data, TectonicService tectonics) {
        this.data = Objects.requireNonNull(data, "data");
        this.tectonics = Objects.requireNonNull(tectonics, "tectonics");
    }

    public VolcanoState nextState(VolcanoState current, MagmaChamber chamber) {
        Objects.requireNonNull(current, "current");
        Objects.requireNonNull(chamber, "chamber");
        return switch (current) {
            case EXTINCT -> VolcanoState.EXTINCT;
            case DORMANT -> chamber.pressureMegapascals() >= ACTIVATE_PRESSURE_MPA
                    ? VolcanoState.ACTIVE
                    : VolcanoState.DORMANT;
            case ACTIVE -> {
                if (chamber.pressureMegapascals() >= ERUPTION_PRESSURE_MPA
                        && chamber.gasFraction() >= ERUPTION_GAS_FRACTION) {
                    yield VolcanoState.ERUPTING;
                }
                if (chamber.pressureMegapascals() <= DEACTIVATE_PRESSURE_MPA) {
                    yield VolcanoState.DORMANT;
                }
                yield VolcanoState.ACTIVE;
            }
            case ERUPTING -> chamber.pressureMegapascals() <= RELAXED_PRESSURE_MPA
                    && chamber.gasFraction() <= RELAXED_GAS_FRACTION
                    ? VolcanoState.ACTIVE
                    : VolcanoState.ERUPTING;
        };
    }

    public MagmaChamber evolve(
            MagmaChamber chamber,
            TectonicSample sample,
            double seismicIntensity,
            long elapsedTicks,
            VolcanoState state
    ) {
        Objects.requireNonNull(chamber, "chamber");
        Objects.requireNonNull(sample, "sample");
        Objects.requireNonNull(state, "state");
        if (!Double.isFinite(seismicIntensity) || seismicIntensity < 0.0 || seismicIntensity > 1.0) {
            throw new IllegalArgumentException("seismicIntensity must be within [0, 1]");
        }
        if (elapsedTicks < 0L) {
            throw new IllegalArgumentException("elapsedTicks must be non-negative");
        }

        double days = elapsedTicks / TICKS_PER_DAY;
        double contextBoost = contextBoost(sample.context());
        double supplyMultiplier = 0.35
                + sample.volcanicPotential() * 1.10
                + sample.stress() * 0.55
                + contextBoost;
        double effectiveSupply = chamber.replenishmentCubicKilometersPerDay() * supplyMultiplier;
        double targetReplenishment = 0.05
                + sample.volcanicPotential() * 0.45
                + sample.stress() * 0.15
                + contextBoost * 0.15;
        double relaxation = Math.min(1.0, days * 0.05);
        double replenishment = chamber.replenishmentCubicKilometersPerDay()
                + (targetReplenishment - chamber.replenishmentCubicKilometersPerDay()) * relaxation
                + seismicIntensity * 0.01;

        double volume = chamber.volumeCubicKilometers() + effectiveSupply * days;
        double pressure = chamber.pressureMegapascals()
                + (effectiveSupply * 7.0
                + sample.stress() * 1.5
                + sample.volcanicPotential()) * days
                + seismicIntensity * 25.0;
        double gas = chamber.gasFraction()
                + (0.003
                + chamber.composition().volatileRichness() * 0.006
                + sample.volcanicPotential() * 0.003) * days
                + seismicIntensity * 0.01;
        double targetTemperature = 1_120.0
                + chamber.composition().silicaFraction() * 120.0
                + sample.volcanicPotential() * 80.0;
        double temperature = chamber.temperatureKelvin()
                + (targetTemperature - chamber.temperatureKelvin()) * Math.min(1.0, days * 0.03);

        if (state == VolcanoState.ERUPTING && days > 0.0) {
            double ventedVolume = Math.max(0.75, chamber.volumeCubicKilometers() * 0.08) * days;
            volume = Math.max(0.0, volume - ventedVolume);
            pressure = Math.max(0.0, pressure - 55.0 * days);
            gas = Math.max(0.0, gas - 0.07 * days);
            temperature = Math.max(700.0, temperature - 8.0 * days);
        }

        return new MagmaChamber(
                chamber.composition(),
                Math.max(0.0, volume),
                Math.max(0.0, pressure),
                clampUnit(gas),
                Math.max(1.0, temperature),
                Math.max(0.0, replenishment));
    }

    public MagmaChamber ensureChamber(UUID persistenceId) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        Optional<MagmaChamber> existing = data.chamber(persistenceId);
        if (existing.isPresent()) {
            return existing.get();
        }
        VolcanoSite site = data.get(persistenceId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown volcano site " + persistenceId));
        MagmaChamber chamber = MagmaChamberFactory.initialFor(site);
        data.updateLifecycle(persistenceId, site.state(), chamber);
        return chamber;
    }

    public VolcanoState advance(long worldSeed, UUID persistenceId, long elapsedTicks, double seismicIntensity) {
        Objects.requireNonNull(persistenceId, "persistenceId");
        VolcanoSite site = data.get(persistenceId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown volcano site " + persistenceId));
        MagmaChamber chamber = ensureChamber(persistenceId);
        TectonicSample sample = tectonics.sample(worldSeed, site.center().getX(), site.center().getZ());
        MagmaChamber evolved = evolve(chamber, sample, seismicIntensity, elapsedTicks, site.state());
        VolcanoState next = nextState(site.state(), evolved);
        data.updateLifecycle(persistenceId, next, evolved);
        return next;
    }

    public int onSeismicEvent(SeismicEvent event) {
        Objects.requireNonNull(event, "event");
        int affected = 0;
        for (VolcanoSite site : data.all()) {
            double intensity = event.intensityAt(site.center().getX(), site.center().getZ());
            if (intensity <= 0.0) {
                continue;
            }
            MagmaChamber chamber = ensureChamber(site.persistenceId());
            MagmaChamber perturbed = new MagmaChamber(
                    chamber.composition(),
                    chamber.volumeCubicKilometers(),
                    chamber.pressureMegapascals() + intensity * 25.0,
                    clampUnit(chamber.gasFraction() + intensity * 0.01),
                    chamber.temperatureKelvin() + intensity * 3.0,
                    chamber.replenishmentCubicKilometersPerDay() + intensity * 0.01);
            data.updateLifecycle(site.persistenceId(), site.state(), perturbed);
            affected++;
        }
        return affected;
    }

    private static double contextBoost(TectonicContext context) {
        return switch (context) {
            case CONVERGENT -> 0.25;
            case HOTSPOT -> 0.35;
            case DIVERGENT -> 0.12;
            case TRANSFORM -> 0.08;
            case INTERIOR -> 0.0;
        };
    }

    private static double clampUnit(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
