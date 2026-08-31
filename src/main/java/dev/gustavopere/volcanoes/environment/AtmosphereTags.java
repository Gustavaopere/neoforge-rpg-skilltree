package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.VolcanoesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

/** Stable, data-pack-extensible tags owned by the Atmosphere core. */
public final class AtmosphereTags {
    public static final TagKey<Item> PARTICULATE_FILTERS = item("respiration/particulate_filters");
    public static final TagKey<Item> ACID_GAS_FILTERS = item("respiration/acid_gas_filters");
    public static final TagKey<Item> TOXIC_GAS_FILTERS = item("respiration/toxic_gas_filters");
    public static final TagKey<EntityType<?>> DOES_NOT_BREATHE = entityType("respiration/does_not_breathe");

    private AtmosphereTags() {
    }

    private static TagKey<Item> item(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(VolcanoesMod.MOD_ID, path));
    }

    private static TagKey<EntityType<?>> entityType(String path) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(VolcanoesMod.MOD_ID, path));
    }
}
