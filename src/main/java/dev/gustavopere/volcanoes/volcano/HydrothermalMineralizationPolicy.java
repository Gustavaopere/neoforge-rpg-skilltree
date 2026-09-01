package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.GeologyResourceTags;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.Optional;

/**
 * Canonical geology policy for assigning an exact Stage 01 metal family to a hydrothermal system.
 *
 * <p>Only volcano-caused systems receive an exact metal identity. Purely tectonic geothermal
 * systems remain generic until a future geology contract supplies a stronger mineral signal.</p>
 */
public final class HydrothermalMineralizationPolicy {
    private HydrothermalMineralizationPolicy() {
    }

    public static ResourceLocation resourceFor(Optional<VolcanoType> causalVolcanoType) {
        Objects.requireNonNull(causalVolcanoType, "causalVolcanoType");
        if (causalVolcanoType.isEmpty()) {
            return GeologyResourceTags.MINERAL_RESOURCES.location();
        }
        return switch (causalVolcanoType.orElseThrow()) {
            case SHIELD, FISSURE -> GeologyResourceTags.IRON_ORES.location();
            case STRATOVOLCANO -> GeologyResourceTags.COPPER_ORES.location();
            case CALDERA -> GeologyResourceTags.GOLD_ORES.location();
        };
    }
}
