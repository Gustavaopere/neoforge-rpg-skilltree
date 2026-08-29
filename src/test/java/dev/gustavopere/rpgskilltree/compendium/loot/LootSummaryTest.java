package dev.gustavopere.rpgskilltree.compendium.loot;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationTargetKind;
import dev.gustavopere.rpgskilltree.compendium.provider.loot.CompendiumLootParser;
import dev.gustavopere.rpgskilltree.compendium.provider.loot.CompendiumLootProvider;
import dev.gustavopere.rpgskilltree.compendium.provider.loot.LootEntrySummary;
import dev.gustavopere.rpgskilltree.compendium.provider.loot.LootResolution;
import dev.gustavopere.rpgskilltree.compendium.provider.loot.LootSummary;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class LootSummaryTest {
    public static void main(String[] args) {
        parsesFixedItemAndCount();
        parsesUniformCountRange();
        playerKillAndLootingRemainConditionalContext();
        unsupportedConditionDoesNotInventChance();
        unsupportedFunctionDoesNotInventCount();
        emptyTableProducesEmptySummary();
        providerTargetsDroppedItemWithoutCreatingEntryKind();
        System.out.println("LootSummaryTest: PASS");
    }

    private static void parsesFixedItemAndCount() {
        LootSummary summary = CompendiumLootParser.parse("minecraft:entities/cow", Map.of(
            "pools", List.of(Map.of(
                "rolls", 1,
                "entries", List.of(Map.of(
                    "type", "minecraft:item",
                    "name", "minecraft:beef",
                    "functions", List.of(Map.of("function", "minecraft:set_count", "count", 2))
                ))
            ))
        ));
        LootEntrySummary entry = only(summary.entries());
        eq("minecraft:beef", entry.itemId());
        eq(LootResolution.EXACT, entry.count().resolution());
        eq(2.0, entry.count().min());
        eq(2.0, entry.count().max());
        eq(LootResolution.EXACT, entry.chance().resolution());
        eq(1.0, entry.chance().min());
    }

    private static void parsesUniformCountRange() {
        LootEntrySummary entry = only(CompendiumLootParser.parse("minecraft:entities/cow", Map.of(
            "pools", List.of(Map.of(
                "rolls", 1,
                "entries", List.of(Map.of(
                    "type", "minecraft:item",
                    "name", "minecraft:beef",
                    "functions", List.of(Map.of(
                        "function", "minecraft:set_count",
                        "count", Map.of("type", "minecraft:uniform", "min", 1, "max", 3)
                    ))
                ))
            ))
        )).entries());
        eq(LootResolution.EXACT, entry.count().resolution());
        eq(1.0, entry.count().min());
        eq(3.0, entry.count().max());
    }

    private static void playerKillAndLootingRemainConditionalContext() {
        LootEntrySummary entry = only(CompendiumLootParser.parse("minecraft:entities/zombie", Map.of(
            "pools", List.of(Map.of(
                "rolls", 1,
                "conditions", List.of(Map.of("condition", "minecraft:killed_by_player")),
                "entries", List.of(Map.of(
                    "type", "minecraft:item",
                    "name", "minecraft:rotten_flesh",
                    "functions", List.of(Map.of("function", "minecraft:looting_enchant", "count", 1))
                ))
            ))
        )).entries());
        truth(entry.conditions().stream().anyMatch(condition -> condition.kind().equals("PLAYER_KILL")));
        truth(entry.conditions().stream().anyMatch(condition -> condition.kind().equals("LOOTING")));
        eq(LootResolution.CONDITIONAL, entry.chance().resolution());
        eq(LootResolution.CONDITIONAL, entry.count().resolution());
    }

    private static void unsupportedConditionDoesNotInventChance() {
        LootEntrySummary entry = only(CompendiumLootParser.parse("example:entities/test", Map.of(
            "pools", List.of(Map.of(
                "rolls", 1,
                "conditions", List.of(Map.of("condition", "example:opaque_condition")),
                "entries", List.of(Map.of("type", "minecraft:item", "name", "minecraft:diamond"))
            ))
        )).entries());
        eq(LootResolution.CONDITIONAL, entry.chance().resolution());
        truth(entry.conditions().stream().anyMatch(condition -> condition.kind().equals("UNSUPPORTED")));
    }

    private static void unsupportedFunctionDoesNotInventCount() {
        LootEntrySummary entry = only(CompendiumLootParser.parse("example:entities/test", Map.of(
            "pools", List.of(Map.of(
                "rolls", 1,
                "entries", List.of(Map.of(
                    "type", "minecraft:item",
                    "name", "minecraft:diamond",
                    "functions", List.of(Map.of("function", "example:opaque_function"))
                ))
            ))
        )).entries());
        eq(LootResolution.CONDITIONAL, entry.count().resolution());
    }

    private static void emptyTableProducesEmptySummary() {
        LootSummary summary = CompendiumLootParser.parse("minecraft:entities/empty", Map.of("pools", List.of()));
        truth(summary.entries().isEmpty());
    }

    private static void providerTargetsDroppedItemWithoutCreatingEntryKind() {
        LootSummary summary = CompendiumLootParser.parse("minecraft:entities/cow", Map.of(
            "pools", List.of(Map.of(
                "rolls", 1,
                "entries", List.of(Map.of("type", "minecraft:item", "name", "minecraft:beef"))
            ))
        ));
        CompendiumRelation relation = only(CompendiumLootProvider.relations(summary));
        eq(CompendiumRelationTargetKind.ITEM, relation.target().kind());
        eq("minecraft:beef", relation.target().resourceLocation());
    }

    private static <T> T only(List<T> values) {
        if (values.size() != 1) throw new AssertionError("expected one value, got " + values.size());
        return values.getFirst();
    }
    private static void truth(boolean value) { if (!value) throw new AssertionError("expected true"); }
    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
