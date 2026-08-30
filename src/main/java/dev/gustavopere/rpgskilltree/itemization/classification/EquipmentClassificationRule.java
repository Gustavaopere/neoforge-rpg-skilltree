package dev.gustavopere.rpgskilltree.itemization.classification;

import java.util.Objects;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public record EquipmentClassificationRule(
    ResourceLocation id,
    int priority,
    Set<ResourceLocation> items,
    Set<ResourceLocation> tags,
    EligibilityOverride eligibility,
    boolean replaceCategories,
    Set<EquipmentCategory> addCategories,
    Set<EquipmentCategory> removeCategories
) {
    public EquipmentClassificationRule {
        id = Objects.requireNonNull(id, "id");
        items = Set.copyOf(Objects.requireNonNull(items, "items"));
        tags = Set.copyOf(Objects.requireNonNull(tags, "tags"));
        eligibility = Objects.requireNonNull(eligibility, "eligibility");
        addCategories = Set.copyOf(Objects.requireNonNull(addCategories, "addCategories"));
        removeCategories = Set.copyOf(Objects.requireNonNull(removeCategories, "removeCategories"));
        if (items.isEmpty() && tags.isEmpty()) {
            throw new IllegalArgumentException("classification rule requires at least one item or tag target");
        }
    }

    public boolean matches(EquipmentProbe probe) {
        Objects.requireNonNull(probe, "probe");
        if (items.contains(probe.itemId())) {
            return true;
        }
        return probe.tags().stream().anyMatch(tags::contains);
    }
}
