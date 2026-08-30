package dev.gustavopere.rpgskilltree.gametest;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentCategory;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentClassification;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentOverrideCatalog;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentProbe;
import dev.gustavopere.rpgskilltree.runtime.itemization.EquipmentClassificationOverrides;
import dev.gustavopere.rpgskilltree.runtime.itemization.EquipmentClassificationReloadService;
import dev.gustavopere.rpgskilltree.runtime.itemization.EquipmentClassificationService;
import dev.gustavopere.rpgskilltree.runtime.itemization.MinecraftEquipmentProbeFactory;
import java.util.Map;
import java.util.Set;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class EquipmentClassificationGameTests {
    private EquipmentClassificationGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void vanillaWeaponsToolsArmorAndShieldMapToCanonicalCategories(GameTestHelper helper) {
        assertCategories(
            helper,
            Items.DIAMOND_SWORD,
            Set.of(EquipmentCategory.MELEE_SWORD, EquipmentCategory.MELEE_GENERIC)
        );
        assertCategories(
            helper,
            Items.DIAMOND_AXE,
            Set.of(EquipmentCategory.MELEE_AXE, EquipmentCategory.MELEE_GENERIC, EquipmentCategory.UTILITY_TOOL)
        );
        assertCategories(
            helper,
            Items.DIAMOND_PICKAXE,
            Set.of(EquipmentCategory.UTILITY_TOOL, EquipmentCategory.UTILITY_MINING)
        );
        assertCategories(
            helper,
            Items.DIAMOND_HOE,
            Set.of(EquipmentCategory.UTILITY_TOOL, EquipmentCategory.UTILITY_FARMING)
        );
        assertCategories(
            helper,
            Items.BOW,
            Set.of(EquipmentCategory.RANGED_BOW, EquipmentCategory.RANGED_GENERIC)
        );
        assertCategories(
            helper,
            Items.CROSSBOW,
            Set.of(EquipmentCategory.RANGED_CROSSBOW, EquipmentCategory.RANGED_PROJECTILE, EquipmentCategory.RANGED_GENERIC)
        );
        assertCategories(
            helper,
            Items.DIAMOND_HELMET,
            Set.of(EquipmentCategory.ARMOR_HEAD, EquipmentCategory.ARMOR_GENERIC)
        );
        assertCategories(helper, Items.SHIELD, Set.of(EquipmentCategory.UTILITY_SHIELD));
        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void commonBlocksAndFoodRemainIneligibleButUnknownDurableItemsFallback(GameTestHelper helper) {
        EquipmentClassificationOverrides.replace(EquipmentOverrideCatalog.empty());
        EquipmentClassification stone = EquipmentClassificationService.classify(new ItemStack(Items.STONE));
        EquipmentClassification apple = EquipmentClassificationService.classify(new ItemStack(Items.APPLE));
        EquipmentClassification flintAndSteel = EquipmentClassificationService.classify(new ItemStack(Items.FLINT_AND_STEEL));

        helper.assertTrue(!stone.eligible(), "ordinary block items must not become RPG equipment");
        helper.assertTrue(!apple.eligible(), "ordinary food must not become RPG equipment");
        helper.assertTrue(flintAndSteel.eligible(), "unknown durable equipment-like items must remain covered");
        helper.assertTrue(
            flintAndSteel.categories().equals(Set.of(EquipmentCategory.GENERIC_EQUIPMENT)),
            "unknown durable equipment must use the generic fallback"
        );
        helper.assertTrue(flintAndSteel.fallbackUsed(), "unknown durable equipment must be diagnosed as fallback");
        helper.succeed();
    }

    @GameTest(template = "foundation_empty")
    public static void runtimeServiceConsumesActiveDatapackSnapshot(GameTestHelper helper) {
        EquipmentOverrideCatalog previous = EquipmentClassificationOverrides.snapshot();
        try {
            Map<ResourceLocation, JsonElement> rules = Map.of(
                ResourceLocation.fromNamespaceAndPath("rpgskilltree", "gametest_stick_focus"),
                JsonParser.parseString("""
                    {
                      "items": ["minecraft:stick"],
                      "eligibility": "WHITELIST",
                      "add_categories": ["MAGIC_FOCUS", "MAGIC_EQUIPMENT"]
                    }
                    """)
            );
            EquipmentClassificationReloadService.reload(rules);
            EquipmentClassification classification = EquipmentClassificationService.classify(new ItemStack(Items.STICK));

            helper.assertTrue(classification.eligible(), "published whitelist must make stick eligible");
            helper.assertTrue(
                classification.categories().equals(Set.of(EquipmentCategory.MAGIC_FOCUS, EquipmentCategory.MAGIC_EQUIPMENT)),
                "runtime service must consume categories from the active datapack snapshot"
            );
            helper.assertTrue(
                classification.providerId().equals(ResourceLocation.fromNamespaceAndPath("rpgskilltree", "override")),
                "datapack override must be reported as the responsible provider"
            );
            helper.succeed();
        } finally {
            EquipmentClassificationOverrides.replace(previous);
        }
    }

    private static void assertCategories(GameTestHelper helper, Item item, Set<EquipmentCategory> expected) {
        EquipmentProbe probe = MinecraftEquipmentProbeFactory.from(new ItemStack(item));
        EquipmentClassification classification = EquipmentClassificationService.classify(probe);
        helper.assertTrue(probe.explicitEquipmentSignal(), "known vanilla equipment must expose an explicit structural signal");
        helper.assertTrue(probe.structuralCategories().equals(expected), "unexpected structural categories for " + item);
        helper.assertTrue(classification.eligible(), "known vanilla equipment must be eligible: " + item);
        helper.assertTrue(classification.categories().equals(expected), "unexpected runtime categories for " + item);
    }
}
