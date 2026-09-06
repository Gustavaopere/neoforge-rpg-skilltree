package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;

import java.util.Objects;

/** Immutable pure planning result for one geothermal feature candidate. */
public record GeothermalFeaturePlacement(
        GeothermalFeatureType type,
        BlockPos center,
        int radiusBlocks,
        double heatSeverity,
        double gasSeverity,
        double hydrothermalDepositChance
) {
    public GeothermalFeaturePlacement {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(center, "center");
        if (radiusBlocks <= 0) {
            throw new IllegalArgumentException("radiusBlocks must be positive");
        }
        requireUnit(heatSeverity, "heatSeverity");
        requireUnit(gasSeverity, "gasSeverity");
        requireUnit(hydrothermalDepositChance, "hydrothermalDepositChance");
    }

    public static GeothermalFeaturePlacement fromProfile(
            BlockPos center,
            GeothermalFeatureProfile profile
    ) {
        Objects.requireNonNull(profile, "profile");
        return new GeothermalFeaturePlacement(
                profile.type(),
                Objects.requireNonNull(center, "center"),
                profile.radiusBlocks(),
                profile.heatSeverity(),
                profile.gasSeverity(),
                profile.hydrothermalDepositChance());
    }

    private static void requireUnit(double value, String name) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0, 1]");
        }
    }
}
