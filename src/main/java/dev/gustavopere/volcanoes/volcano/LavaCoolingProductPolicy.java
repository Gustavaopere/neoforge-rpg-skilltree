package dev.gustavopere.volcanoes.volcano;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/** Selects cooling product tags without hard dependencies on optional rock mods. */
public final class LavaCoolingProductPolicy {
    private static final ResourceLocation BASALTIC_TAG =
            ResourceLocation.fromNamespaceAndPath("volcanoes", "lava_cooling/basaltic");
    private static final ResourceLocation GLASSY_TAG =
            ResourceLocation.fromNamespaceAndPath("volcanoes", "lava_cooling/glassy");
    private static final ResourceLocation RUBBLE_TAG =
            ResourceLocation.fromNamespaceAndPath("volcanoes", "lava_cooling/rubble");

    private static final ResourceLocation BASALT =
            ResourceLocation.fromNamespaceAndPath("minecraft", "basalt");
    private static final ResourceLocation OBSIDIAN =
            ResourceLocation.fromNamespaceAndPath("minecraft", "obsidian");
    private static final ResourceLocation COBBLESTONE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "cobblestone");

    public Product select(LavaEnvironmentSample environment, boolean waterContact, boolean sourceBlock) {
        Objects.requireNonNull(environment, "environment");

        if (waterContact) {
            return sourceBlock
                    ? new Product(GLASSY_TAG, OBSIDIAN)
                    : new Product(RUBBLE_TAG, COBBLESTONE);
        }

        if (environment.coolingMultiplier() > 1.0) {
            return new Product(GLASSY_TAG, OBSIDIAN);
        }
        return new Product(BASALTIC_TAG, BASALT);
    }

    public record Product(ResourceLocation tagId, ResourceLocation vanillaFallback) {
        public Product {
            tagId = Objects.requireNonNull(tagId, "tagId");
            vanillaFallback = Objects.requireNonNull(vanillaFallback, "vanillaFallback");
        }
    }
}
