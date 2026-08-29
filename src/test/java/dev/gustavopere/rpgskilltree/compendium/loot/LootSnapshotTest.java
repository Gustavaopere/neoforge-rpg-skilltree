package dev.gustavopere.rpgskilltree.compendium.loot;

import dev.gustavopere.rpgskilltree.compendium.provider.loot.CompendiumLootSnapshot;
import dev.gustavopere.rpgskilltree.compendium.provider.loot.LootSummary;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class LootSnapshotTest {
    public static void main(String[] args) {
        snapshotIsImmutable();
        validRestageReflectsChangedLoot();
        failedStagingLeavesPreviousSnapshotUsable();
        System.out.println("LootSnapshotTest: PASS");
    }

    private static void snapshotIsImmutable() {
        LinkedHashMap<String, Map<String, Object>> docs = new LinkedHashMap<>();
        docs.put("minecraft:entities/cow", table("minecraft:beef"));
        CompendiumLootSnapshot snapshot = CompendiumLootSnapshot.stage(docs);
        docs.clear();
        eq("minecraft:beef", snapshot.require("minecraft:entities/cow").entries().getFirst().itemId());
        try {
            snapshot.summaries().clear();
            throw new AssertionError("expected immutable snapshot");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    private static void validRestageReflectsChangedLoot() {
        CompendiumLootSnapshot first = CompendiumLootSnapshot.stage(Map.of(
            "minecraft:entities/cow", table("minecraft:beef")
        ));
        CompendiumLootSnapshot second = CompendiumLootSnapshot.stage(Map.of(
            "minecraft:entities/cow", table("minecraft:leather")
        ));
        eq("minecraft:beef", first.require("minecraft:entities/cow").entries().getFirst().itemId());
        eq("minecraft:leather", second.require("minecraft:entities/cow").entries().getFirst().itemId());
    }

    private static void failedStagingLeavesPreviousSnapshotUsable() {
        CompendiumLootSnapshot previous = CompendiumLootSnapshot.stage(Map.of(
            "minecraft:entities/cow", table("minecraft:beef")
        ));
        LinkedHashMap<String, Map<String, Object>> invalid = new LinkedHashMap<>();
        invalid.put("minecraft:entities/cow", null);
        try {
            CompendiumLootSnapshot.stage(invalid);
            throw new AssertionError("expected invalid staging failure");
        } catch (IllegalArgumentException expected) {
            truth(expected.getMessage().contains("minecraft:entities/cow"));
        }
        eq("minecraft:beef", previous.require("minecraft:entities/cow").entries().getFirst().itemId());
    }

    private static Map<String, Object> table(String itemId) {
        return Map.of(
            "pools", List.of(Map.of(
                "rolls", 1,
                "entries", List.of(Map.of("type", "minecraft:item", "name", itemId))
            ))
        );
    }

    private static void truth(boolean value) { if (!value) throw new AssertionError("expected true"); }
    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
