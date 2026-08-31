package dev.gustavopere.volcanoes.pressure;

import dev.gustavopere.volcanoes.VolcanoesMod;

import java.util.Map;

/**
 * Canonical passive respiration capabilities shared with the Atmosphere datapack tags.
 *
 * <p>These mappings intentionally expose filtration only. A filter tag never implies breathable
 * oxygen, pressure resistance or thermal protection.</p>
 */
public final class CanonicalRespirationProtectionAdapter {
    public static final String PARTICULATE_FILTER_TAG =
            VolcanoesMod.MOD_ID + ":respiration/particulate_filters";
    public static final String ACID_GAS_FILTER_TAG =
            VolcanoesMod.MOD_ID + ":respiration/acid_gas_filters";
    public static final String TOXIC_GAS_FILTER_TAG =
            VolcanoesMod.MOD_ID + ":respiration/toxic_gas_filters";

    private CanonicalRespirationProtectionAdapter() {
    }

    public static EquipmentProtectionAdapter create() {
        return new TagProtectionAdapter(Map.of(
                PARTICULATE_FILTER_TAG,
                Map.of(ProtectionCapability.PARTICULATE_FILTER, 1.0),
                ACID_GAS_FILTER_TAG,
                Map.of(ProtectionCapability.ACID_GAS_FILTER, 1.0),
                TOXIC_GAS_FILTER_TAG,
                Map.of(ProtectionCapability.TOXIC_GAS_FILTER, 1.0)));
    }
}
