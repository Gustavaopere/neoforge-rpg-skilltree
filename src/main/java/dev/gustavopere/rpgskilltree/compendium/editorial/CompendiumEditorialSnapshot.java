package dev.gustavopere.rpgskilltree.compendium.editorial;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class CompendiumEditorialSnapshot {
    private static final CompendiumEditorialSnapshot EMPTY = new CompendiumEditorialSnapshot(List.of(), Map.of());

    private final List<CompendiumEditorialContent> entries;
    private final Map<CompendiumEntryId, CompendiumEditorialContent> byId;

    private CompendiumEditorialSnapshot(
        List<CompendiumEditorialContent> entries,
        Map<CompendiumEntryId, CompendiumEditorialContent> byId
    ) {
        this.entries = entries;
        this.byId = byId;
    }

    public static CompendiumEditorialSnapshot empty() {
        return EMPTY;
    }

    public static CompendiumEditorialSnapshot fromEntries(Collection<CompendiumEditorialContent> entries) {
        Objects.requireNonNull(entries, "entries");
        ArrayList<CompendiumEditorialContent> ordered = new ArrayList<>(entries.size());
        for (CompendiumEditorialContent entry : entries) ordered.add(Objects.requireNonNull(entry, "entry"));
        ordered.sort((left, right) -> left.entryId().serializedId().compareTo(right.entryId().serializedId()));

        LinkedHashMap<CompendiumEntryId, CompendiumEditorialContent> index = new LinkedHashMap<>();
        for (CompendiumEditorialContent entry : ordered) {
            if (index.putIfAbsent(entry.entryId(), entry) != null) {
                throw new IllegalArgumentException("duplicate editorial entry id: " + entry.entryId().serializedId());
            }
        }
        if (ordered.isEmpty()) return empty();
        return new CompendiumEditorialSnapshot(List.copyOf(ordered), Map.copyOf(index));
    }

    public List<CompendiumEditorialContent> entries() {
        return entries;
    }

    public Optional<CompendiumEditorialContent> find(CompendiumEntryId id) {
        return Optional.ofNullable(byId.get(Objects.requireNonNull(id, "id")));
    }
}
