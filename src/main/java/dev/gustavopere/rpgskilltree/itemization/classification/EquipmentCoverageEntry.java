package dev.gustavopere.rpgskilltree.itemization.classification;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

public record EquipmentCoverageEntry(
    ResourceLocation itemId,
    EquipmentClassification classification
) {
    public EquipmentCoverageEntry {
        itemId = Objects.requireNonNull(itemId, "itemId");
        classification = Objects.requireNonNull(classification, "classification");
    }
}
