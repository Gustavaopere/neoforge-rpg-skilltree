package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.VolcanoesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/** Datapack-extensible tags used by bounded volcanic world effects. */
public final class VolcanicHazardTags {
    public static final TagKey<Block> ASH_REPLACEABLE_SURFACES = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(VolcanoesMod.MOD_ID, "ash_deposition/replaceable_surfaces"));

    public static final TagKey<Block> NATURAL_TERRAIN = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(VolcanoesMod.MOD_ID, "hazards/natural_terrain"));

    private VolcanicHazardTags() {
    }
}
