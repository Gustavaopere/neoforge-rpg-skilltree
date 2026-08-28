package dev.gustavopere.rpgskilltree.compendium.catalog;

import java.util.List;
import java.util.Objects;

public final class ModpackSnapshot {
    private final String snapshotHash;
    private final List<ModpackModEntry> topLevelMods;
    private final List<ModpackModEntry> embeddedDependencies;
    private final List<RegistryInventoryEntry> registryEntries;

    public ModpackSnapshot(
        String snapshotHash,
        List<ModpackModEntry> topLevelMods,
        List<ModpackModEntry> embeddedDependencies,
        List<RegistryInventoryEntry> registryEntries
    ) {
        Objects.requireNonNull(snapshotHash, "snapshotHash");
        Objects.requireNonNull(topLevelMods, "topLevelMods");
        Objects.requireNonNull(embeddedDependencies, "embeddedDependencies");
        Objects.requireNonNull(registryEntries, "registryEntries");
        if (snapshotHash.isBlank()) throw new IllegalArgumentException("snapshotHash cannot be blank");
        for (ModpackModEntry entry : topLevelMods) {
            if (!entry.topLevel()) throw new IllegalArgumentException("embedded dependency present in topLevelMods: " + entry.modId());
        }
        for (ModpackModEntry entry : embeddedDependencies) {
            if (entry.topLevel()) throw new IllegalArgumentException("top-level mod present in embeddedDependencies: " + entry.modId());
        }
        this.snapshotHash = snapshotHash.trim();
        this.topLevelMods = List.copyOf(topLevelMods);
        this.embeddedDependencies = List.copyOf(embeddedDependencies);
        this.registryEntries = List.copyOf(registryEntries);
    }

    public String snapshotHash() {
        return snapshotHash;
    }

    public List<ModpackModEntry> topLevelMods() {
        return topLevelMods;
    }

    public List<ModpackModEntry> embeddedDependencies() {
        return embeddedDependencies;
    }

    public List<RegistryInventoryEntry> registryEntries() {
        return registryEntries;
    }
}
