package dev.gustavopere.rpgskilltree.compendium.catalog;

import java.util.Objects;

public record RegistryInventoryEntry(
    InventoryKind kind,
    String resourceLocation,
    String namespace,
    String translationKey,
    String modDisplayName,
    String registrySource,
    boolean presentAtRuntime
) {
    public RegistryInventoryEntry {
        Objects.requireNonNull(kind, "kind");
        resourceLocation = normalize(resourceLocation);
        namespace = normalize(namespace);
        translationKey = normalize(translationKey);
        modDisplayName = normalize(modDisplayName);
        registrySource = normalize(registrySource);
    }

    public String key() {
        return kind.name() + "|" + resourceLocation;
    }

    public boolean hasRequiredMetadata() {
        return !resourceLocation.isBlank()
            && resourceLocation.indexOf(':') > 0
            && !namespace.isBlank()
            && !translationKey.isBlank()
            && !modDisplayName.isBlank()
            && !registrySource.isBlank();
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}
