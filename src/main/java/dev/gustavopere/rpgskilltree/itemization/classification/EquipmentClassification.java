package dev.gustavopere.rpgskilltree.itemization.classification;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public record EquipmentClassification(
    boolean eligible,
    Set<EquipmentCategory> categories,
    ResourceLocation providerId,
    boolean fallbackUsed,
    List<ResourceLocation> matchedOverrideIds
) {
    public EquipmentClassification {
        categories = Set.copyOf(Objects.requireNonNull(categories, "categories"));
        providerId = Objects.requireNonNull(providerId, "providerId");
        matchedOverrideIds = List.copyOf(Objects.requireNonNull(matchedOverrideIds, "matchedOverrideIds"));
    }
}
