package dev.gustavopere.rpgskilltree.compendium.catalog;

import java.util.Set;
import java.util.TreeSet;

public final class InventoryDrift {
    private InventoryDrift() {}

    public static InventoryDriftReport compare(ModpackSnapshot before, ModpackSnapshot after) {
        Set<String> beforeMods = new TreeSet<>();
        for (ModpackModEntry entry : before.topLevelMods()) beforeMods.add(entry.modId());
        Set<String> afterMods = new TreeSet<>();
        for (ModpackModEntry entry : after.topLevelMods()) afterMods.add(entry.modId());

        Set<String> addedMods = difference(afterMods, beforeMods);
        Set<String> removedMods = difference(beforeMods, afterMods);

        Set<String> beforeEntries = new TreeSet<>();
        for (RegistryInventoryEntry entry : before.registryEntries()) beforeEntries.add(entry.key());
        Set<String> afterEntries = new TreeSet<>();
        for (RegistryInventoryEntry entry : after.registryEntries()) afterEntries.add(entry.key());

        return new InventoryDriftReport(
            addedMods,
            removedMods,
            difference(afterEntries, beforeEntries),
            difference(beforeEntries, afterEntries)
        );
    }

    private static Set<String> difference(Set<String> left, Set<String> right) {
        TreeSet<String> result = new TreeSet<>(left);
        result.removeAll(right);
        return Set.copyOf(result);
    }
}
