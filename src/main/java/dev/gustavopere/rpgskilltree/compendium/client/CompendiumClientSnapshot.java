package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Immutable, protocol-agnostic client snapshot consumed by Compendium UI models.
 *
 * <p>The snapshot contains only data already projected for client visibility. Transport versioning,
 * hashes, deltas and persistence remain outside this type and are owned by the later sync layer.</p>
 */
public final class CompendiumClientSnapshot {
    private final List<CompendiumClientEntry> entries;
    private final Map<CompendiumEntryId, CompendiumPageModel> pagesById;

    public CompendiumClientSnapshot(
        List<CompendiumClientEntry> entries,
        List<CompendiumPageModel> pages
    ) {
        Objects.requireNonNull(entries, "entries");
        Objects.requireNonNull(pages, "pages");

        this.entries = List.copyOf(entries);
        Set<CompendiumEntryId> entryIds = new LinkedHashSet<>();
        for (CompendiumClientEntry entry : this.entries) {
            Objects.requireNonNull(entry, "entry");
            if (!entryIds.add(entry.id())) {
                throw new IllegalArgumentException("duplicate client entry id: " + entry.id());
            }
        }

        LinkedHashMap<CompendiumEntryId, CompendiumPageModel> indexedPages = new LinkedHashMap<>();
        for (CompendiumPageModel page : List.copyOf(pages)) {
            Objects.requireNonNull(page, "page");
            if (!entryIds.contains(page.id())) {
                throw new IllegalArgumentException("page has no matching client entry: " + page.id());
            }
            if (indexedPages.putIfAbsent(page.id(), page) != null) {
                throw new IllegalArgumentException("duplicate client page id: " + page.id());
            }
        }
        this.pagesById = Map.copyOf(indexedPages);
    }

    public List<CompendiumClientEntry> entries() {
        return entries;
    }

    public Optional<CompendiumPageModel> page(CompendiumEntryId id) {
        return Optional.ofNullable(pagesById.get(Objects.requireNonNull(id, "id")));
    }

    public CompendiumBrowserModel newBrowserModel() {
        return new CompendiumBrowserModel(entries);
    }
}
