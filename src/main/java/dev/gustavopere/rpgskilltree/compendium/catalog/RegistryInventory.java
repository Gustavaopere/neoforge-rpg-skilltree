package dev.gustavopere.rpgskilltree.compendium.catalog;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class RegistryInventory {
    private final List<RegistryInventoryEntry> entries;

    public RegistryInventory(List<RegistryInventoryEntry> entries) {
        Objects.requireNonNull(entries, "entries");
        this.entries = List.copyOf(entries);
    }

    public List<RegistryInventoryEntry> entries() {
        return entries;
    }

    public List<RegistryInventoryEntry> byNamespace(String namespace) {
        String expected = namespace == null ? "" : namespace.trim();
        return entries.stream().filter(entry -> entry.namespace().equals(expected)).toList();
    }

    public Map<String, CoverageDecision> classifyCoverage(Map<String, CoverageOverride> overrides) {
        Map<String, CoverageOverride> safeOverrides = overrides == null ? Map.of() : Map.copyOf(overrides);
        LinkedHashMap<String, CoverageDecision> decisions = new LinkedHashMap<>();
        for (RegistryInventoryEntry entry : entries) {
            String key = entry.key();
            if (decisions.containsKey(key)) {
                throw new IllegalArgumentException("duplicate registry inventory key: " + key);
            }
            decisions.put(key, CoverageClassifier.classify(entry, safeOverrides.get(key)));
        }
        return Map.copyOf(decisions);
    }
}
