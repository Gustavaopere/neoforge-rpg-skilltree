package dev.gustavopere.volcanoes.volcano;

import java.util.Objects;

/**
 * Immutable physical/worldgen constraints for one geothermal feature family.
 *
 * <p>Defaults are owned by Stage 03 and require no optional integration mod. Later adapters may
 * consume heat/gas/deposit outputs but do not redefine placement physics.</p>
 */
public record GeothermalFeatureProfile(
        GeothermalFeatureType type,
        double minimumPotential,
        int minimumSpacingBlocks,
        int radiusBlocks,
        double heatSeverity,
        double gasSeverity,
        boolean requiresWater,
        double hydrothermalDepositChance
) {
    public GeothermalFeatureProfile {
        Objects.requireNonNull(type, "type");
        requireUnit(minimumPotential, "minimumPotential", false);
        if (minimumPotential <= 0.0) {
            throw new IllegalArgumentException("minimumPotential must be positive");
        }
        if (minimumSpacingBlocks <= 0) {
            throw new IllegalArgumentException("minimumSpacingBlocks must be positive");
        }
        if (radiusBlocks <= 0) {
            throw new IllegalArgumentException("radiusBlocks must be positive");
        }
        requireUnit(heatSeverity, "heatSeverity", true);
        requireUnit(gasSeverity, "gasSeverity", true);
        requireUnit(hydrothermalDepositChance, "hydrothermalDepositChance", true);
    }

    public static GeothermalFeatureProfile defaults(GeothermalFeatureType type) {
        return switch (Objects.requireNonNull(type, "type")) {
            case HOT_SPRING -> new GeothermalFeatureProfile(type, 0.45, 192, 4, 0.68, 0.12, true, 0.18);
            case GEYSER -> new GeothermalFeatureProfile(type, 0.66, 320, 2, 0.90, 0.36, true, 0.16);
            case FUMAROLE -> new GeothermalFeatureProfile(type, 0.50, 192, 1, 0.56, 0.52, false, 0.12);
            case SULFUROUS_VENT -> new GeothermalFeatureProfile(type, 0.58, 256, 2, 0.52, 0.78, false, 0.30);
            case MUD_POT -> new GeothermalFeatureProfile(type, 0.52, 192, 3, 0.46, 0.30, true, 0.22);
        };
    }

    private static void requireUnit(double value, String name, boolean allowZero) {
        if (!Double.isFinite(value)
                || value < 0.0
                || value > 1.0
                || (!allowZero && value == 0.0)) {
            throw new IllegalArgumentException(name + " must be "
                    + (allowZero ? "within [0, 1]" : "within (0, 1]"));
        }
    }
}
