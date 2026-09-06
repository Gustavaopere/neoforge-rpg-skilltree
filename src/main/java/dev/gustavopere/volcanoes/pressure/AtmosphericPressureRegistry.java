package dev.gustavopere.volcanoes.pressure;

import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Immutable dimension-to-pressure-profile snapshot. Missing dimensions use a neutral 1-atm fallback. */
public final class AtmosphericPressureRegistry {
    private static final AtmosphericPressureProfile SAFE_FALLBACK = new AtmosphericPressureProfile(
            ResourceLocation.fromNamespaceAndPath("volcanoes", "safe_fallback"),
            0.0,
            1.0,
            new PressureCurve(List.of(new PressureControlPoint(0.0, 1.0))));

    private final Map<String, AtmosphericPressureProfile> byDimension;

    AtmosphericPressureRegistry(Map<String, AtmosphericPressureProfile> byDimension) {
        Objects.requireNonNull(byDimension, "byDimension");
        this.byDimension = Map.copyOf(byDimension);
    }

    public AtmosphericPressureProfile profile(String dimensionId) {
        String checkedDimensionId = requireDimensionId(dimensionId);
        return byDimension.getOrDefault(checkedDimensionId, SAFE_FALLBACK);
    }

    public double pressureAtm(String dimensionId, double altitudeY) {
        return profile(dimensionId).pressureAtm(altitudeY);
    }

    public Map<String, AtmosphericPressureProfile> dimensionProfiles() {
        return byDimension;
    }

    public static AtmosphericPressureRegistry empty() {
        return new AtmosphericPressureRegistry(Map.of());
    }

    private static String requireDimensionId(String dimensionId) {
        String checked = Objects.requireNonNull(dimensionId, "dimensionId");
        if (checked.isBlank()) {
            throw new IllegalArgumentException("dimensionId must not be blank");
        }
        if (!checked.equals(checked.trim())) {
            throw new IllegalArgumentException("dimensionId must not contain leading or trailing whitespace");
        }
        return checked;
    }
}
