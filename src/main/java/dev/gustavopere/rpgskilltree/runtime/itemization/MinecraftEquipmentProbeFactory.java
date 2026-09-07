package dev.gustavopere.rpgskilltree.runtime.itemization;

import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentCategory;
import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentProbe;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;

public final class MinecraftEquipmentProbeFactory {
    private MinecraftEquipmentProbeFactory() {}

    public static EquipmentProbe from(ItemStack stack) {
        Objects.requireNonNull(stack, "stack");
        Item item = stack.getItem();
        EnumSet<EquipmentCategory> categories = EnumSet.noneOf(EquipmentCategory.class);
        classifyStructural(item, categories);

        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
        Set<ResourceLocation> tags = stack.getTags()
            .map(TagKey::location)
            .collect(Collectors.toUnmodifiableSet());
        boolean durable = stack.isDamageableItem();
        boolean blockItem = item instanceof BlockItem;
        boolean consumable = stack.get(DataComponents.FOOD) != null;
        boolean explicitEquipmentSignal = !categories.isEmpty();
        boolean potentiallyEquipment = durable || explicitEquipmentSignal;

        return new EquipmentProbe(
            itemId,
            tags,
            durable,
            blockItem,
            consumable,
            explicitEquipmentSignal,
            potentiallyEquipment,
            categories
        );
    }

    private static void classifyStructural(Item item, EnumSet<EquipmentCategory> categories) {
        if (item instanceof SwordItem) {
            categories.add(EquipmentCategory.MELEE_SWORD);
            categories.add(EquipmentCategory.MELEE_GENERIC);
        }
        if (item instanceof AxeItem) {
            categories.add(EquipmentCategory.MELEE_AXE);
            categories.add(EquipmentCategory.MELEE_GENERIC);
            categories.add(EquipmentCategory.UTILITY_TOOL);
        }
        if (item instanceof PickaxeItem || item instanceof ShovelItem) {
            categories.add(EquipmentCategory.UTILITY_TOOL);
            categories.add(EquipmentCategory.UTILITY_MINING);
        }
        if (item instanceof HoeItem) {
            categories.add(EquipmentCategory.UTILITY_TOOL);
            categories.add(EquipmentCategory.UTILITY_FARMING);
        }
        if (item instanceof BowItem) {
            categories.add(EquipmentCategory.RANGED_BOW);
            categories.add(EquipmentCategory.RANGED_GENERIC);
        }
        if (item instanceof CrossbowItem) {
            categories.add(EquipmentCategory.RANGED_CROSSBOW);
            categories.add(EquipmentCategory.RANGED_PROJECTILE);
            categories.add(EquipmentCategory.RANGED_GENERIC);
        }
        if (item instanceof ShieldItem) {
            categories.add(EquipmentCategory.UTILITY_SHIELD);
        }
        if (item instanceof ArmorItem armor) {
            categories.add(EquipmentCategory.ARMOR_GENERIC);
            EquipmentSlot slot = armor.getType().getSlot();
            if (slot == EquipmentSlot.HEAD) {
                categories.add(EquipmentCategory.ARMOR_HEAD);
            } else if (slot == EquipmentSlot.CHEST) {
                categories.add(EquipmentCategory.ARMOR_CHEST);
            } else if (slot == EquipmentSlot.LEGS) {
                categories.add(EquipmentCategory.ARMOR_LEGS);
            } else if (slot == EquipmentSlot.FEET) {
                categories.add(EquipmentCategory.ARMOR_FEET);
            }
        }
    }
}
