package dev.gustavopere.rpgskilltree.compendium.provider;

import java.util.Map;
import java.util.Set;

public record ProviderContext(Set<String> loadedMods, Map<String, String> attributes) {
    public ProviderContext {
        loadedMods = Set.copyOf(loadedMods == null ? Set.of() : loadedMods);
        attributes = Map.copyOf(attributes == null ? Map.of() : attributes);
    }

    public boolean isModLoaded(String modId) {
        return modId != null && loadedMods.contains(modId.trim());
    }

    public static ProviderContext empty() {
        return new ProviderContext(Set.of(), Map.of());
    }
}
