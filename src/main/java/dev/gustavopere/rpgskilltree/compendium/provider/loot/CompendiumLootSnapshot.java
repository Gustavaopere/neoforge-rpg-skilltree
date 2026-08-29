package dev.gustavopere.rpgskilltree.compendium.provider.loot;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

public final class CompendiumLootSnapshot {
    private final Map<String, LootSummary> summaries;

    public CompendiumLootSnapshot(Map<String, LootSummary> summaries) {
        TreeMap<String, LootSummary> ordered = new TreeMap<>();
        for (Map.Entry<String, LootSummary> entry : (summaries == null ? Map.<String, LootSummary>of() : summaries).entrySet()) {
            String tableId = requireTableId(entry.getKey());
            LootSummary summary = Objects.requireNonNull(entry.getValue(), "loot summary for " + tableId);
            if (!tableId.equals(summary.tableId())) {
                throw new IllegalArgumentException("loot summary id mismatch: " + tableId + " != " + summary.tableId());
            }
            ordered.put(tableId, summary);
        }
        this.summaries = Map.copyOf(ordered);
    }

    public static CompendiumLootSnapshot empty() {
        return new CompendiumLootSnapshot(Map.of());
    }

    public static CompendiumLootSnapshot stage(Map<String, ? extends Map<String, Object>> documents) {
        Objects.requireNonNull(documents, "documents");
        LinkedHashMap<String, LootSummary> staged = new LinkedHashMap<>();
        documents.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                String tableId = requireTableId(entry.getKey());
                Map<String, Object> document = entry.getValue();
                if (document == null) {
                    throw new IllegalArgumentException("loot document must not be null: " + tableId);
                }
                staged.put(tableId, CompendiumLootParser.parse(tableId, document));
            });
        return new CompendiumLootSnapshot(staged);
    }

    public Map<String, LootSummary> summaries() {
        return summaries;
    }

    public Optional<LootSummary> find(String tableId) {
        return Optional.ofNullable(summaries.get(requireTableId(tableId)));
    }

    public LootSummary require(String tableId) {
        return find(tableId).orElseThrow(() -> new IllegalArgumentException("unknown loot summary: " + tableId));
    }

    private static String requireTableId(String value) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException("loot table id must not be blank");
        return value.trim();
    }
}
