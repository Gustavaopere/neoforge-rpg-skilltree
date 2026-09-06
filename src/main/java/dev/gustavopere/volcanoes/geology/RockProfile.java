package dev.gustavopere.volcanoes.geology;

import java.util.Objects;

/** Immutable physical properties used by lava, erosion, seismic and geothermal systems. */
public record RockProfile(
        String id,
        RockCategory category,
        double hardness,
        double permeability,
        double thermalConductivity,
        double lavaFlowMultiplier,
        double erosionResistance,
        double hydrothermalReactivity
) {
    public static final RockProfile GENERIC = new RockProfile(
            "generic", RockCategory.GENERIC,
            0.55, 0.35, 2.5, 1.0, 0.5, 0.45);

    /** Canonical safe fallback for unknown vanilla or modded blocks. */
    public static final RockProfile GENERIC_STONE = GENERIC;

    /**
     * Compatibility constructor retained for Foundation callers. New code should provide all physical properties.
     */
    public RockProfile(
            String id,
            RockCategory category,
            double permeability,
            double thermalConductivity,
            double lavaFlowMultiplier,
            double erosionResistance
    ) {
        this(
                id,
                category,
                erosionResistance,
                permeability,
                thermalConductivity,
                lavaFlowMultiplier,
                erosionResistance,
                permeability);
    }

    public RockProfile {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }
        category = Objects.requireNonNull(category, "category");
        hardness = requireRange("hardness", hardness, 0.0, 1.0);
        permeability = requireRange("permeability", permeability, 0.0, 1.0);
        thermalConductivity = requireRange("thermalConductivity", thermalConductivity, 0.0, 100.0);
        lavaFlowMultiplier = requireRange("lavaFlowMultiplier", lavaFlowMultiplier, 0.01, 100.0);
        erosionResistance = requireRange("erosionResistance", erosionResistance, 0.0, 1.0);
        hydrothermalReactivity = requireRange("hydrothermalReactivity", hydrothermalReactivity, 0.0, 1.0);
    }

    private static double requireRange(String name, double value, double min, double max) {
        if (!Double.isFinite(value) || value < min || value > max) {
            throw new IllegalArgumentException(name + " must be within [" + min + ", " + max + "]");
        }
        return value;
    }
}
