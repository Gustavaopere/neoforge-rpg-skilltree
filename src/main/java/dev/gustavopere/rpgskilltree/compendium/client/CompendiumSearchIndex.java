package dev.gustavopere.rpgskilltree.compendium.client;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/** Accent-insensitive local search index over an already available client snapshot. */
public final class CompendiumSearchIndex {
    private static final Comparator<IndexedEntry> ORDER = Comparator
        .comparing((IndexedEntry entry) -> normalize(entry.entry().displayName()))
        .thenComparing(entry -> entry.entry().id().serializedId());

    private final List<IndexedEntry> entries;

    public CompendiumSearchIndex(List<CompendiumClientEntry> entries) {
        List<IndexedEntry> indexed = new ArrayList<>();
        if (entries != null) {
            for (CompendiumClientEntry entry : entries) {
                if (entry == null) throw new IllegalArgumentException("entry must not be null");
                indexed.add(new IndexedEntry(entry, searchableText(entry)));
            }
        }
        indexed.sort(ORDER);
        this.entries = List.copyOf(indexed);
    }

    public List<CompendiumClientEntry> search(String query, int limit) {
        if (limit <= 0) throw new IllegalArgumentException("limit must be positive");
        String normalizedQuery = normalize(query == null ? "" : query).trim();
        String[] tokens = normalizedQuery.isEmpty() ? new String[0] : normalizedQuery.split("\\s+");
        List<CompendiumClientEntry> matches = new ArrayList<>(Math.min(limit, entries.size()));
        for (IndexedEntry indexed : entries) {
            if (!matchesAll(indexed.searchableText(), tokens)) continue;
            matches.add(indexed.entry());
            if (matches.size() == limit) break;
        }
        return List.copyOf(matches);
    }

    static String normalize(String value) {
        String decomposed = Normalizer.normalize(value, Normalizer.Form.NFD);
        StringBuilder result = new StringBuilder(decomposed.length());
        for (int i = 0; i < decomposed.length(); i++) {
            char character = decomposed.charAt(i);
            if (Character.getType(character) != Character.NON_SPACING_MARK) result.append(character);
        }
        return result.toString().toLowerCase(Locale.ROOT);
    }

    private static boolean matchesAll(String searchableText, String[] tokens) {
        for (String token : tokens) if (!searchableText.contains(token)) return false;
        return true;
    }

    private static String searchableText(CompendiumClientEntry entry) {
        StringBuilder text = new StringBuilder();
        append(text, entry.displayName());
        append(text, entry.sourceModId());
        append(text, entry.id().resourceLocation());
        append(text, entry.id().serializedId());
        for (String alias : entry.aliases()) append(text, alias);
        return normalize(text.toString());
    }

    private static void append(StringBuilder target, String value) {
        if (!target.isEmpty()) target.append(' ');
        target.append(value);
    }

    private record IndexedEntry(CompendiumClientEntry entry, String searchableText) {}
}
