package dev.gustavopere.rpgskilltree.compendium.loot;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import dev.gustavopere.rpgskilltree.compendium.provider.loot.CompendiumLootEnricher;
import dev.gustavopere.rpgskilltree.compendium.provider.loot.CompendiumLootSnapshot;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class CompendiumLootEnricherTest {
    public static void main(String[] args) {
        entityPageUsesMatchingLootTable();
        resolvedLootFactsAreExposedOnPage();
        changedSnapshotChangesEnrichedPageWithoutRebuildingBaseCatalog();
        nonEntityPageIsUnchanged();
        System.out.println("CompendiumLootEnricherTest: PASS");
    }

    private static void entityPageUsesMatchingLootTable() {
        CompendiumEntry cow = entry(CompendiumEntryKind.ENTITY, "minecraft:cow");
        CompendiumLootSnapshot snapshot = CompendiumLootSnapshot.stage(Map.of(
            "minecraft:entities/cow", table("minecraft:beef")
        ));
        CompendiumEntry enriched = CompendiumLootEnricher.enrich(cow, snapshot);
        eq(1, enriched.relations().size());
        eq("ITEM|minecraft:beef", enriched.relations().getFirst().target().serializedTarget());
    }

    private static void resolvedLootFactsAreExposedOnPage() {
        CompendiumEntry cow = entry(CompendiumEntryKind.ENTITY, "minecraft:cow");
        CompendiumEntry enriched = CompendiumLootEnricher.enrich(cow, CompendiumLootSnapshot.stage(Map.of(
            "minecraft:entities/cow", table("minecraft:beef")
        )));
        CompendiumSection loot = enriched.sections().stream()
            .filter(section -> section.sectionId().equals("loot"))
            .findFirst()
            .orElseThrow(() -> new AssertionError("missing loot section"));
        eq("minecraft:beef", fact(loot, "drop.0.item").value());
        eq(1.0, fact(loot, "drop.0.count_min").value());
        eq(1.0, fact(loot, "drop.0.count_max").value());
        eq(1.0, fact(loot, "drop.0.chance_min").value());
        eq(1.0, fact(loot, "drop.0.chance_max").value());
    }

    private static void changedSnapshotChangesEnrichedPageWithoutRebuildingBaseCatalog() {
        CompendiumEntry cow = entry(CompendiumEntryKind.ENTITY, "minecraft:cow");
        CompendiumEntry first = CompendiumLootEnricher.enrich(cow, CompendiumLootSnapshot.stage(Map.of(
            "minecraft:entities/cow", table("minecraft:beef")
        )));
        CompendiumEntry second = CompendiumLootEnricher.enrich(cow, CompendiumLootSnapshot.stage(Map.of(
            "minecraft:entities/cow", table("minecraft:leather")
        )));
        eq("ITEM|minecraft:beef", first.relations().getFirst().target().serializedTarget());
        eq("ITEM|minecraft:leather", second.relations().getFirst().target().serializedTarget());
        truth(cow.relations().isEmpty());
    }

    private static void nonEntityPageIsUnchanged() {
        CompendiumEntry biome = entry(CompendiumEntryKind.BIOME, "minecraft:plains");
        CompendiumEntry enriched = CompendiumLootEnricher.enrich(biome, CompendiumLootSnapshot.empty());
        eq(biome, enriched);
    }

    private static CompendiumFact<?> fact(CompendiumSection section, String key) {
        return section.facts().stream()
            .filter(value -> value.factKey().equals(key))
            .findFirst()
            .orElseThrow(() -> new AssertionError("missing fact " + key));
    }

    private static CompendiumEntry entry(CompendiumEntryKind kind, String id) {
        return new CompendiumEntry(
            CompendiumEntryId.of(kind, id),
            "minecraft",
            "compendium.test",
            Set.of(),
            List.of(),
            List.of(),
            DiscoveryPolicy.AUTOMATIC,
            VisibilityPolicy.VISIBLE,
            new CompendiumProvenance(FactSource.REGISTRY, "test"),
            1
        );
    }

    private static Map<String, Object> table(String itemId) {
        return Map.of("pools", List.of(Map.of(
            "rolls", 1,
            "entries", List.of(Map.of("type", "minecraft:item", "name", itemId))
        )));
    }

    private static void truth(boolean value) { if (!value) throw new AssertionError("expected true"); }
    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
