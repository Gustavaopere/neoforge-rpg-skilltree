package dev.gustavopere.rpgskilltree.compendium.catalog;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CompendiumCatalogBuilder {
    private static final Comparator<CompendiumEntry> ORDER = Comparator.comparing(entry -> entry.id().serializedId());

    private final List<CompendiumEntry> stagedEntries = new ArrayList<>();
    private final Map<CompendiumEntryId, CompendiumEntryId> stagedAliases = new LinkedHashMap<>();

    public CompendiumCatalogBuilder add(CompendiumEntry entry) {
        stagedEntries.add(Objects.requireNonNull(entry, "entry"));
        return this;
    }

    public CompendiumCatalogBuilder addAlias(CompendiumEntryId alias, CompendiumEntryId canonicalId) {
        Objects.requireNonNull(alias, "alias");
        Objects.requireNonNull(canonicalId, "canonicalId");
        CompendiumEntryId previous = stagedAliases.putIfAbsent(alias, canonicalId);
        if (previous != null && !previous.equals(canonicalId)) {
            throw new IllegalArgumentException("alias already points to another canonical id: " + alias.serializedId());
        }
        return this;
    }

    public CompendiumCatalogSnapshot build() {
        ArrayList<CompendiumEntry> ordered = new ArrayList<>(stagedEntries);
        ordered.sort(ORDER);

        LinkedHashMap<CompendiumEntryId, CompendiumEntry> byId = new LinkedHashMap<>();
        for (CompendiumEntry entry : ordered) {
            CompendiumEntry previous = byId.putIfAbsent(entry.id(), entry);
            if (previous != null) {
                throw new IllegalArgumentException("duplicate compendium entry id: " + entry.id().serializedId());
            }
        }

        LinkedHashMap<CompendiumEntryId, CompendiumEntryId> aliases = new LinkedHashMap<>();
        stagedAliases.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(Comparator.comparing(CompendiumEntryId::serializedId)))
            .forEach(alias -> {
                if (byId.containsKey(alias.getKey())) {
                    throw new IllegalArgumentException("alias collides with canonical id: " + alias.getKey().serializedId());
                }
                if (!byId.containsKey(alias.getValue())) {
                    throw new IllegalArgumentException("alias target is not present: " + alias.getValue().serializedId());
                }
                aliases.put(alias.getKey(), alias.getValue());
            });

        LinkedHashMap<String, List<CompendiumEntry>> byNamespace = new LinkedHashMap<>();
        LinkedHashMap<String, List<CompendiumEntry>> bySourceMod = new LinkedHashMap<>();
        LinkedHashMap<String, List<CompendiumEntry>> byCategory = new LinkedHashMap<>();
        for (CompendiumEntry entry : ordered) {
            addIndex(byNamespace, entry.id().namespace(), entry);
            addIndex(bySourceMod, entry.sourceModId(), entry);
            for (String category : entry.categoryIds()) addIndex(byCategory, category, entry);
        }

        return new CompendiumCatalogSnapshot(ordered, byId, byNamespace, bySourceMod, byCategory, aliases);
    }

    private static void addIndex(Map<String, List<CompendiumEntry>> index, String key, CompendiumEntry entry) {
        index.computeIfAbsent(key, ignored -> new ArrayList<>()).add(entry);
    }
}
