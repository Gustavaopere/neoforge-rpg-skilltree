package dev.gustavopere.rpgskilltree.compendium.discovery;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/** Derives current completion denominators from loaded, explicitly eligible catalog entries. */
public final class DiscoveryCompletionService {
    private DiscoveryCompletionService() {}

    public static DiscoveryCompletionSummary summarize(
        Collection<CompendiumEntry> loadedEntries,
        DiscoveryProgress progress,
        Set<CompendiumEntryId> excludedIds
    ) {
        Objects.requireNonNull(loadedEntries, "loadedEntries");
        Objects.requireNonNull(progress, "progress");
        Set<CompendiumEntryId> excluded = excludedIds == null ? Set.of() : Set.copyOf(excludedIds);

        int eligible = 0;
        int discovered = 0;
        HashMap<String, MutableCount> categories = new HashMap<>();
        HashMap<String, MutableCount> namespaces = new HashMap<>();
        HashSet<CompendiumEntryId> seenEntries = new HashSet<>();

        for (CompendiumEntry entry : loadedEntries) {
            Objects.requireNonNull(entry, "loaded entry");
            if (!seenEntries.add(entry.id())) {
                throw new IllegalArgumentException("duplicate loaded compendium entry: " + entry.id().serializedId());
            }
            if (excluded.contains(entry.id())) continue;

            boolean isDiscovered = progress.record(entry.id())
                .map(record -> record.state().atLeast(DiscoveryState.SEEN))
                .orElse(false);
            eligible++;
            if (isDiscovered) discovered++;

            increment(namespaces, entry.id().namespace(), isDiscovered);
            for (String categoryId : entry.categoryIds()) {
                increment(categories, categoryId, isDiscovered);
            }
        }

        return new DiscoveryCompletionSummary(
            new DiscoveryCompletionCount(eligible, discovered),
            freeze(categories),
            freeze(namespaces)
        );
    }

    private static void increment(Map<String, MutableCount> counts, String key, boolean discovered) {
        counts.computeIfAbsent(key, ignored -> new MutableCount()).add(discovered);
    }

    private static Map<String, DiscoveryCompletionCount> freeze(Map<String, MutableCount> counts) {
        TreeMap<String, DiscoveryCompletionCount> result = new TreeMap<>();
        counts.forEach((key, value) -> result.put(key, value.freeze()));
        return Map.copyOf(result);
    }

    private static final class MutableCount {
        private int eligible;
        private int discovered;

        private void add(boolean isDiscovered) {
            eligible++;
            if (isDiscovered) discovered++;
        }

        private DiscoveryCompletionCount freeze() {
            return new DiscoveryCompletionCount(eligible, discovered);
        }
    }
}
