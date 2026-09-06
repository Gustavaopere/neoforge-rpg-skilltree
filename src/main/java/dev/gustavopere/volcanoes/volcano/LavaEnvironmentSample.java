package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.RockProfile;

import java.util.Objects;

/** Immutable, bounded geological modifiers applied to specialized volcanic lava behavior. */
public record LavaEnvironmentSample(
        RockProfile rockProfile,
        double spreadMultiplier,
        double coolingMultiplier,
        boolean usesVanillaFallback
) {
    public static final double MIN_SPREAD_MULTIPLIER = 0.50;
    public static final double MAX_SPREAD_MULTIPLIER = 1.75;
    public static final double MIN_COOLING_MULTIPLIER = 0.50;
    public static final double MAX_COOLING_MULTIPLIER = 2.00;

    public LavaEnvironmentSample {
        rockProfile = Objects.requireNonNull(rockProfile, "rockProfile");
        spreadMultiplier = requireRange(
                "spreadMultiplier",
                spreadMultiplier,
                MIN_SPREAD_MULTIPLIER,
                MAX_SPREAD_MULTIPLIER);
        coolingMultiplier = requireRange(
                "coolingMultiplier",
                coolingMultiplier,
                MIN_COOLING_MULTIPLIER,
                MAX_COOLING_MULTIPLIER);
    }

    private static double requireRange(String name, double value, double min, double max) {
        if (!Double.isFinite(value) || value < min || value > max) {
            throw new IllegalArgumentException(name + " must be within [" + min + ", " + max + "]");
        }
        return value;
    }
}
