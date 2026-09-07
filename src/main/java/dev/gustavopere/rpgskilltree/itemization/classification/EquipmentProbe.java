package dev.gustavopere.rpgskilltree.itemization.classification;

import java.util.Objects;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public record EquipmentProbe(
    ResourceLocation itemId,
    Set<ResourceLocation> tags,
    boolean durable,
    boolean blockItem,
    boolean consumable,
    boolean explicitEquipmentSignal,
    boolean potentiallyEquipment,
    Set<EquipmentCategory> structuralCategories
) {
    public EquipmentProbe {
        itemId = Objects.requireNonNull(itemId, "itemId");
        tags = Set.copyOf(Objects.requireNonNull(tags, "tags"));
        structuralCategories = Set.copyOf(Objects.requireNonNull(structuralCategories, "structuralCategories"));
    }
}
