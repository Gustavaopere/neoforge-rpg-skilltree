package dev.gustavopere.volcanoes.geology;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.Tags;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class GeologyResourceTagsTest {
    @Test
    void canonicalOreTagsReuseNeoForgeCommonTags() {
        assertEquals(Tags.Blocks.ORES_COPPER, GeologyResourceTags.COPPER_ORES);
        assertEquals(Tags.Blocks.ORES_IRON, GeologyResourceTags.IRON_ORES);
        assertEquals(Tags.Blocks.ORES_GOLD, GeologyResourceTags.GOLD_ORES);
        assertEquals(Tags.Blocks.ORES, GeologyResourceTags.ALL_ORES);
    }

    @Test
    void aggregateResourceTagsRemainVolcanoesOwnedAndStable() {
        assertEquals(
                ResourceLocation.parse("volcanoes:resources/metallic"),
                GeologyResourceTags.METALLIC_RESOURCES.location());
        assertEquals(
                ResourceLocation.parse("volcanoes:resources/mineral"),
                GeologyResourceTags.MINERAL_RESOURCES.location());
    }
}
