package dev.gustavopere.rpgskilltree.compendium.catalog;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class CompendiumCatalogSnapshot {
    private final List<CompendiumEntry> entries;
    private final Map<CompendiumEntryId, CompendiumEntry> byId;
    private final Map<String, List<CompendiumEntry>> byNamespace;
    private final Map<String, List<CompendiumEntry>> bySourceMod;
    private final Map<String, List<CompendiumEntry>> byCategory;
    private final Map<CompendiumEntryId, CompendiumEntryId> aliases;

    CompendiumCatalogSnapshot(
        List<CompendiumEntry> entries,
        Map<CompendiumEntryId, CompendiumEntry> byId,
        Map<String, List<CompendiumEntry>> byNamespace,
        Map<String, List<CompendiumEntry>> bySourceMod,
        Map<String, List<CompendiumEntry>> byCategory,
        Map<CompendiumEntryId, CompendiumEntryId> aliases
    ) {
        this.entries = List.copyOf(entries);
        this.byId = Map.copyOf(byId);
        this.byNamespace = immutableIndex(byNamespace);
        this.bySourceMod = immutableIndex(bySourceMod);
        this.byCategory = immutableIndex(byCategory);
        this.aliases = Map.copyOf(aliases);
    }

    public static CompendiumCatalogSnapshot empty() {
        return new CompendiumCatalogSnapshot(List.of(), Map.of(), Map.of(), Map.of(), Map.of(), Map.of());
    }

    public List<CompendiumEntry> entries() {
        return entries;
    }

    public Optional<CompendiumEntry> find(CompendiumEntryId id) {
        return Optional.ofNullable(byId.get(id));
    }

    public CompendiumEntry require(CompendiumEntryId id) {
        CompendiumEntry entry = byId.get(id);
        if (entry == null) throw new IllegalArgumentException("unknown compendium entry id: " + id.serializedId());
        return entry;
    }

    public List<CompendiumEntry> byNamespace(String namespace) {
        return byNamespace.getOrDefault(normalize(namespace), List.of());
    }

    public List<CompendiumEntry> bySourceMod(String sourceModId) {
        return bySourceMod.getOrDefault(normalize(sourceModId), List.of());
    }

    public List<CompendiumEntry> byCategory(String categoryId) {
        return byCategory.getOrDefault(normalize(categoryId), List.of());
    }

    public Optional<CompendiumEntryId> resolveAlias(CompendiumEntryId alias) {
        return Optional.ofNullable(aliases.get(alias));
    }

    private static Map<String, List<CompendiumEntry>> immutableIndex(Map<String, List<CompendiumEntry>> source) {
        java.util.LinkedHashMap<String, List<CompendiumEntry>> copy = new java.util.LinkedHashMap<>();
        source.forEach((key, value) -> copy.put(key, List.copyOf(value)));
        return Map.copyOf(copy);
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}
