package dev.gustavopere.rpgskilltree.compendium.provider.loot;

import java.util.List;

public record LootSummary(String tableId, List<LootEntrySummary> entries) {
    public LootSummary {
        if (tableId == null || tableId.trim().isEmpty()) throw new IllegalArgumentException("tableId must not be blank");
        tableId = tableId.trim();
        entries = List.copyOf(entries == null ? List.of() : entries);
    }
}
