package dev.gustavopere.volcanoes.geology;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

/** Canonical block tags used by geological deposit metadata and integrations. */
public final class GeologyResourceTags {
    public static final TagKey<Block> COPPER_ORES = Tags.Blocks.ORES_COPPER;
    public static final TagKey<Block> IRON_ORES = Tags.Blocks.ORES_IRON;
    public static final TagKey<Block> GOLD_ORES = Tags.Blocks.ORES_GOLD;
    public static final TagKey<Block> ALL_ORES = Tags.Blocks.ORES;

    public static final TagKey<Block> METALLIC_RESOURCES = volcanoesTag("resources/metallic");
    public static final TagKey<Block> MINERAL_RESOURCES = volcanoesTag("resources/mineral");

    private GeologyResourceTags() {
    }

    private static TagKey<Block> volcanoesTag(String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("volcanoes", path));
    }
}
