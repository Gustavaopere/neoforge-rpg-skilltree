package dev.gustavopere.rpgskilltree.gametest;

import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentCategory;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentClassification;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentClassifier;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentOverrideCatalog;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentProbe;
import dev.gustavopere.rpgskilltree.runtime.itemization.MinecraftEquipmentProbeFactory;
import java.util.List;
import java.util.Set;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
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
        EquipmentClassifier classifier = new EquipmentClassifier(EquipmentOverrideCatalog.empty(), List.of());

        EquipmentClassification stone = classifier.classify(MinecraftEquipmentProbeFactory.from(new ItemStack(Items.STONE)));
        EquipmentClassification apple = classifier.classify(MinecraftEquipmentProbeFactory.from(new ItemStack(Items.APPLE)));
        EquipmentClassification flintAndSteel = classifier.classify(
            MinecraftEquipmentProbeFactory.from(new ItemStack(Items.FLINT_AND_STEEL))
        );

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

    private static void assertCategories(GameTestHelper helper, net.minecraft.world.item.Item item, Set<EquipmentCategory> expected) {
        EquipmentProbe probe = MinecraftEquipmentProbeFactory.from(new ItemStack(item));
        helper.assertTrue(probe.explicitEquipmentSignal(), "known vanilla equipment must expose an explicit structural signal");
        helper.assertTrue(probe.structuralCategories().equals(expected), "unexpected categories for " + item);
    }
}
