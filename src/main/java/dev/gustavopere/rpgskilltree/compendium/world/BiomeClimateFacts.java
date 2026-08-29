package dev.gustavopere.rpgskilltree.compendium.world;

public record BiomeClimateFacts(Double vanillaTemperature, Double vanillaDownfall) {
    public BiomeClimateFacts {
        validate(vanillaTemperature, "vanillaTemperature");
        validate(vanillaDownfall, "vanillaDownfall");
    }

    private static void validate(Double value, String field) {
        if (value != null && !Double.isFinite(value)) throw new IllegalArgumentException(field + " must be finite");
    }
}
