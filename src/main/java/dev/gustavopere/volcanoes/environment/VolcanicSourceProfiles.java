package dev.gustavopere.volcanoes.environment;

public final class VolcanicSourceProfiles {
    private VolcanicSourceProfiles() {
    }

    public static VolcanicSourceProfile carbonDioxide(double fraction) {
        requireUnit("fraction", fraction);
        return new VolcanicSourceProfile(
                "volcanoes:carbon_dioxide",
                new AtmosphereContribution(0.0, 0.0, fraction, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, fraction));
    }

    public static VolcanicSourceProfile acidGas(double sulfurDioxidePpm) {
        requireNonNegative("sulfurDioxidePpm", sulfurDioxidePpm);
        return new VolcanicSourceProfile(
                "volcanoes:acid_gas",
                new AtmosphereContribution(0.0, 0.0, 0.0, sulfurDioxidePpm, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
    }

    public static VolcanicSourceProfile ash(double particulatesMgM3, double smokeMgM3) {
        requireNonNegative("particulatesMgM3", particulatesMgM3);
        requireNonNegative("smokeMgM3", smokeMgM3);
        return new VolcanicSourceProfile(
                "volcanoes:ash",
                new AtmosphereContribution(0.0, 0.0, 0.0, 0.0, 0.0, particulatesMgM3, smokeMgM3, 0.0, 0.0, 0.0));
    }

    public static VolcanicSourceProfile geothermalToxic(double toxicGasPpm) {
        requireNonNegative("toxicGasPpm", toxicGasPpm);
        return new VolcanicSourceProfile(
                "volcanoes:geothermal_toxic",
                new AtmosphereContribution(0.0, 0.0, 0.0, 0.0, toxicGasPpm, 0.0, 0.0, 0.0, 0.0, 0.0));
    }

    private static void requireUnit(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0, 1]");
        }
    }

    private static void requireNonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
    }
}
