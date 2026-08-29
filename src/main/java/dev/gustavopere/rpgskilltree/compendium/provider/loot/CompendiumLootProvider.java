package dev.gustavopere.rpgskilltree.compendium.provider.loot;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationTarget;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationType;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class CompendiumLootProvider {
    private CompendiumLootProvider() {}

    public static List<CompendiumRelation> relations(LootSummary summary) {
        ArrayList<CompendiumRelation> relations = new ArrayList<>();
        for (LootEntrySummary entry : summary.entries()) {
            FactConfidence confidence = entry.count().resolution() == LootResolution.EXACT
                && entry.chance().resolution() == LootResolution.EXACT
                && entry.conditions().isEmpty()
                ? FactConfidence.EXACT
                : FactConfidence.CONTEXTUAL;
            relations.add(new CompendiumRelation(
                CompendiumRelationType.DROPS,
                CompendiumRelationTarget.item(entry.itemId()),
                FactSource.LOOT_TABLE,
                confidence,
                summary.tableId()
            ));
        }
        relations.sort(Comparator.comparing(relation -> relation.target().serializedTarget()));
        return List.copyOf(relations);
    }
}
