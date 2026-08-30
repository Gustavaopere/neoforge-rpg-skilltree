package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSnapshot;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Builds the protocol-agnostic client snapshot from canonical entries, an already-authorized client
 * entry projection, and an optional editorial overlay.
 *
 * <p>This type does not own transport or client installation. It only composes the immutable payload
 * that a later synchronization layer can carry.</p>
 */
public final class CompendiumClientSnapshotFactory {
    private CompendiumClientSnapshotFactory() {}

    public static CompendiumClientSnapshot create(
        Collection<CompendiumEntry> canonicalEntries,
        List<CompendiumClientEntry> clientEntries,
        CompendiumEditorialSnapshot editorialSnapshot,
        boolean admin
    ) {
        Objects.requireNonNull(canonicalEntries, "canonicalEntries");
        Objects.requireNonNull(clientEntries, "clientEntries");
        Objects.requireNonNull(editorialSnapshot, "editorialSnapshot");

        LinkedHashMap<CompendiumEntryId, CompendiumEntry> canonicalById = new LinkedHashMap<>();
        for (CompendiumEntry entry : canonicalEntries) {
            Objects.requireNonNull(entry, "canonicalEntry");
            if (canonicalById.putIfAbsent(entry.id(), entry) != null) {
                throw new IllegalArgumentException("duplicate canonical Compendium entry: " + entry.id());
            }
        }

        LinkedHashSet<CompendiumEntryId> authorizedIds = new LinkedHashSet<>();
        for (CompendiumClientEntry clientEntry : clientEntries) {
            Objects.requireNonNull(clientEntry, "clientEntry");
            authorizedIds.add(clientEntry.id());
        }
        Set<CompendiumEntryId> authorized = Set.copyOf(authorizedIds);

        ArrayList<CompendiumPageModel> pages = new ArrayList<>();
        for (CompendiumClientEntry clientEntry : clientEntries) {
            CompendiumEntry canonical = canonicalById.get(clientEntry.id());
            if (canonical == null) {
                throw new IllegalArgumentException(
                    "client projection has no canonical Compendium entry: " + clientEntry.id()
                );
            }
            CompendiumPageModelFactory.create(
                canonical,
                clientEntry,
                admin,
                editorialSnapshot.find(clientEntry.id()),
                authorized
            ).ifPresent(pages::add);
        }

        return new CompendiumClientSnapshot(clientEntries, List.copyOf(pages));
    }
}
