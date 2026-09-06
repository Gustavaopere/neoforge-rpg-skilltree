package dev.gustavopere.volcanoes.volcano;

import net.minecraft.nbt.CompoundTag;

import java.util.Objects;

/**
 * Immutable eruption shape derived from the current magma chamber.
 *
 * <p>The buildup/plateau/fade concept is adapted from TFC Volcanoes 2.2.1
 * {@code TremorEvent}; the implementation is original and consumes only Volcanoes-owned
 * magma contracts. See {@code docs/upstream/TFC_VOLCANOES.md}.</p>
 */
public record EruptionProfile(
        double peakIntensity,
        int innerRadiusBlocks,
        int outerRadiusBlocks,
        long precursorsTicks,
        long openingTicks,
        long sustainedTicks,
        long waningTicks
) {
    private static final String PEAK_INTENSITY = "peak_intensity";
    private static final String INNER_RADIUS = "inner_radius_blocks";
    private static final String OUTER_RADIUS = "outer_radius_blocks";
    private static final String PRECURSORS = "precursors_ticks";
    private static final String OPENING = "opening_ticks";
    private static final String SUSTAINED = "sustained_ticks";
    private static final String WANING = "waning_ticks";

    public EruptionProfile {
        if (!Double.isFinite(peakIntensity) || peakIntensity < 0.0 || peakIntensity > 1.0) {
            throw new IllegalArgumentException("peakIntensity must be within [0, 1]");
        }
        if (innerRadiusBlocks <= 0) {
            throw new IllegalArgumentException("innerRadiusBlocks must be positive");
        }
        if (outerRadiusBlocks <= innerRadiusBlocks) {
            throw new IllegalArgumentException("outerRadiusBlocks must exceed innerRadiusBlocks");
        }
        if (precursorsTicks <= 0L || openingTicks <= 0L || sustainedTicks <= 0L || waningTicks <= 0L) {
            throw new IllegalArgumentException("active eruption phase durations must be positive");
        }
    }

    public static EruptionProfile fromChamber(MagmaChamber chamber) {
        Objects.requireNonNull(chamber, "chamber");
        MagmaComposition composition = chamber.composition();

        double pressure = clamp(chamber.pressureMegapascals() / 400.0);
        double gas = chamber.gasFraction();
        double chemistry = (composition.silicaFraction() + composition.volatileRichness()) * 0.5;
        double thermal = clamp((chamber.temperatureKelvin() - 900.0) / 500.0);
        double peak = clamp(0.15
                + pressure * 0.35
                + gas * 0.25
                + chemistry * 0.20
                + thermal * 0.05);

        int innerRadius = 48 + (int) Math.round(peak * 80.0);
        int outerRadius = 192 + (int) Math.round(peak * 512.0);
        long precursors = 600L + Math.round(peak * 1_200.0);
        long opening = 200L + Math.round(peak * 400.0);
        long sustained = 2_400L + Math.round(peak * 7_200.0);
        long waning = 800L + Math.round(peak * 2_400.0);
        return new EruptionProfile(
                peak,
                innerRadius,
                outerRadius,
                precursors,
                opening,
                sustained,
                waning);
    }

    public long durationTicks(EruptionPhase phase) {
        Objects.requireNonNull(phase, "phase");
        return switch (phase) {
            case PRECURSORS -> precursorsTicks;
            case OPENING -> openingTicks;
            case SUSTAINED -> sustainedTicks;
            case WANING -> waningTicks;
            case DORMANT -> 0L;
        };
    }

    public long totalDurationTicks() {
        return precursorsTicks + openingTicks + sustainedTicks + waningTicks;
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble(PEAK_INTENSITY, peakIntensity);
        tag.putInt(INNER_RADIUS, innerRadiusBlocks);
        tag.putInt(OUTER_RADIUS, outerRadiusBlocks);
        tag.putLong(PRECURSORS, precursorsTicks);
        tag.putLong(OPENING, openingTicks);
        tag.putLong(SUSTAINED, sustainedTicks);
        tag.putLong(WANING, waningTicks);
        return tag;
    }

    public static EruptionProfile fromTag(CompoundTag tag) {
        Objects.requireNonNull(tag, "tag");
        return new EruptionProfile(
                tag.getDouble(PEAK_INTENSITY),
                tag.getInt(INNER_RADIUS),
                tag.getInt(OUTER_RADIUS),
                tag.getLong(PRECURSORS),
                tag.getLong(OPENING),
                tag.getLong(SUSTAINED),
                tag.getLong(WANING));
    }

    private static double clamp(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
