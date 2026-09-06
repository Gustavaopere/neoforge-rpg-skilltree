package dev.gustavopere.volcanoes.volcano;

import net.minecraft.nbt.CompoundTag;

import java.util.Objects;

/** Persisted coarse physical state of one volcano's magma chamber. */
public record MagmaChamber(
        MagmaComposition composition,
        double volumeCubicKilometers,
        double pressureMegapascals,
        double gasFraction,
        double temperatureKelvin,
        double replenishmentCubicKilometersPerDay
) {
    private static final String COMPOSITION = "composition";
    private static final String VOLUME = "volume_km3";
    private static final String PRESSURE = "pressure_mpa";
    private static final String GAS = "gas_fraction";
    private static final String TEMPERATURE = "temperature_kelvin";
    private static final String REPLENISHMENT = "replenishment_km3_per_day";

    public MagmaChamber {
        composition = Objects.requireNonNull(composition, "composition");
        volumeCubicKilometers = requireNonNegative("volumeCubicKilometers", volumeCubicKilometers);
        pressureMegapascals = requireNonNegative("pressureMegapascals", pressureMegapascals);
        gasFraction = requireUnit("gasFraction", gasFraction);
        if (!Double.isFinite(temperatureKelvin) || temperatureKelvin <= 0.0) {
            throw new IllegalArgumentException("temperatureKelvin must be finite and positive");
        }
        replenishmentCubicKilometersPerDay = requireNonNegative(
                "replenishmentCubicKilometersPerDay",
                replenishmentCubicKilometersPerDay);
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.put(COMPOSITION, composition.toTag());
        tag.putDouble(VOLUME, volumeCubicKilometers);
        tag.putDouble(PRESSURE, pressureMegapascals);
        tag.putDouble(GAS, gasFraction);
        tag.putDouble(TEMPERATURE, temperatureKelvin);
        tag.putDouble(REPLENISHMENT, replenishmentCubicKilometersPerDay);
        return tag;
    }

    public static MagmaChamber fromTag(CompoundTag tag) {
        Objects.requireNonNull(tag, "tag");
        return new MagmaChamber(
                MagmaComposition.fromTag(tag.getCompound(COMPOSITION)),
                tag.getDouble(VOLUME),
                tag.getDouble(PRESSURE),
                tag.getDouble(GAS),
                tag.getDouble(TEMPERATURE),
                tag.getDouble(REPLENISHMENT));
    }

    private static double requireUnit(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0, 1]");
        }
        return value;
    }

    private static double requireNonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
        return value;
    }
}
