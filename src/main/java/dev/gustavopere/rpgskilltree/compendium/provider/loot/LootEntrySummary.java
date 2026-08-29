package dev.gustavopere.rpgskilltree.compendium.provider.loot;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationTarget;
import java.util.List;
import java.util.Objects;

public record LootEntrySummary(
    String itemId,
    LootNumberSummary count,
    LootNumberSummary chance,
    List<LootConditionSummary> conditions
) {
    public LootEntrySummary {
        CompendiumRelationTarget.item(itemId);
        itemId = itemId.trim();
        Objects.requireNonNull(count, "count");
        Objects.requireNonNull(chance, "chance");
        conditions = List.copyOf(conditions == null ? List.of() : conditions);
    }
}
