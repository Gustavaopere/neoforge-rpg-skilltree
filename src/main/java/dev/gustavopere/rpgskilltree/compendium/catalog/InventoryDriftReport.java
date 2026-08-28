package dev.gustavopere.rpgskilltree.compendium.catalog;

import java.util.Set;

public record InventoryDriftReport(
    Set<String> addedMods,
    Set<String> removedMods,
    Set<String> addedRegistryEntries,
    Set<String> removedRegistryEntries
) {
    public InventoryDriftReport {
        addedMods = Set.copyOf(addedMods);
        removedMods = Set.copyOf(removedMods);
        addedRegistryEntries = Set.copyOf(addedRegistryEntries);
        removedRegistryEntries = Set.copyOf(removedRegistryEntries);
    }

    public boolean hasChanges() {
        return !(addedMods.isEmpty()
            && removedMods.isEmpty()
            && addedRegistryEntries.isEmpty()
            && removedRegistryEntries.isEmpty());
    }
}
