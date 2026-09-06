package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;
import java.util.Set;

/** Optional biome/tag hints that strengthen local volcanic terrain without becoming hard requirements. */
public final class VolcanicTerrainHints {
    private static final double VOLCANIC_SHAPE_MULTIPLIER = 1.08;

    private static final Set<ResourceLocation> KNOWN_BIOME_TAG_IDS = Set.of(
            ResourceLocation.fromNamespaceAndPath("volcanoes", "is_volcanic"),
            ResourceLocation.fromNamespaceAndPath("c", "is_volcanic"),
            ResourceLocation.fromNamespaceAndPath("tfc", "has_stratovolcanoes"),
            ResourceLocation.fromNamespaceAndPath("tfc", "has_cinder_cones"),
            ResourceLocation.fromNamespaceAndPath("tfc", "has_tuff_cones"),
            ResourceLocation.fromNamespaceAndPath("tfc", "has_tuyas"),
            ResourceLocation.fromNamespaceAndPath("tfc", "is_rift"),
            ResourceLocation.fromNamespaceAndPath("tfc", "is_shield_volcano")
    );

    /**
     * Exact external biome ids are intentionally not owned by the Java core. Optional integrations
     * contribute them through datapack tag entries so a host mod or identifier can disappear without
     * becoming a runtime dependency.
     */
    private static final Set<ResourceLocation> KNOWN_BIOME_IDS = Set.of();

    private VolcanicTerrainHints() {
    }

    public static Set<ResourceLocation> knownBiomeTagIds() {
        return KNOWN_BIOME_TAG_IDS;
    }

    public static Set<ResourceLocation> knownBiomeIds() {
        return KNOWN_BIOME_IDS;
    }

    public static boolean isKnownBiomeTagId(ResourceLocation tagId) {
        return KNOWN_BIOME_TAG_IDS.contains(Objects.requireNonNull(tagId, "tagId"));
    }

    public static boolean isKnownBiomeId(ResourceLocation biomeId) {
        return KNOWN_BIOME_IDS.contains(Objects.requireNonNull(biomeId, "biomeId"));
    }

    /**
     * Resolves optional compatibility hints without requiring any external mod classes or registries.
     * Missing tags simply do not match. Exact external biome ids are supplied through optional
     * datapack entries rather than Java constants.
     */
    public static boolean isVolcanic(Holder<Biome> biome) {
        Objects.requireNonNull(biome, "biome");
        for (ResourceLocation tagId : KNOWN_BIOME_TAG_IDS) {
            if (biome.is(TagKey.create(Registries.BIOME, tagId))) {
                return true;
            }
        }
        return biome.unwrapKey()
                .map(key -> isKnownBiomeId(key.location()))
                .orElse(false);
    }

    public static double shapeMultiplier(boolean volcanicTerrainHint) {
        return volcanicTerrainHint ? VOLCANIC_SHAPE_MULTIPLIER : 1.0;
    }
}
