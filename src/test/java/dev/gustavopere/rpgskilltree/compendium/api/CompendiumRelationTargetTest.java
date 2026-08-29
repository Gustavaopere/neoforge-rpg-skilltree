package dev.gustavopere.rpgskilltree.compendium.api;

import java.util.Objects;

public final class CompendiumRelationTargetTest {
    public static void main(String[] args) {
        typedTargetsSerializeDeterministically();
        legacyEntryConstructorRemainsCompatible();
        curatedExactRelationRequiresEvidence();
        technicalExactRelationMayOmitEvidence();
        invalidResourceTargetFailsClosed();
        System.out.println("CompendiumRelationTargetTest: PASS");
    }

    private static void typedTargetsSerializeDeterministically() {
        eq("ITEM|minecraft:wheat", CompendiumRelationTarget.item("minecraft:wheat").serializedTarget());
        eq("ITEM_TAG|minecraft:flowers", CompendiumRelationTarget.itemTag("minecraft:flowers").serializedTarget());
        eq("BLOCK|minecraft:bee_nest", CompendiumRelationTarget.block("minecraft:bee_nest").serializedTarget());
        eq("BLOCK_TAG|minecraft:flowers", CompendiumRelationTarget.blockTag("minecraft:flowers").serializedTarget());
        eq(
            "ENTRY|ENTITY|minecraft:cow",
            CompendiumRelationTarget.entry(CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:cow")).serializedTarget()
        );
    }

    private static void legacyEntryConstructorRemainsCompatible() {
        CompendiumEntryId cow = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:cow");
        CompendiumRelation relation = new CompendiumRelation(
            CompendiumRelationType.PREDATOR_OF,
            cow,
            FactSource.REGISTRY,
            FactConfidence.EXACT
        );
        eq(CompendiumRelationTargetKind.ENTRY, relation.target().kind());
        eq("ENTRY|ENTITY|minecraft:cow", relation.target().serializedTarget());
    }

    private static void curatedExactRelationRequiresEvidence() {
        try {
            new CompendiumRelation(
                CompendiumRelationType.EATS,
                CompendiumRelationTarget.item("minecraft:wheat"),
                FactSource.CURATED_EDITORIAL,
                FactConfidence.EXACT,
                null
            );
            throw new AssertionError("expected evidence validation failure");
        } catch (IllegalArgumentException expected) {
            truth(expected.getMessage().contains("evidence"));
        }
    }

    private static void technicalExactRelationMayOmitEvidence() {
        CompendiumRelation relation = new CompendiumRelation(
            CompendiumRelationType.DROPS,
            CompendiumRelationTarget.item("minecraft:beef"),
            FactSource.LOOT_TABLE,
            FactConfidence.EXACT,
            null
        );
        eq(FactConfidence.EXACT, relation.confidence());
        eq(null, relation.evidenceId());
    }

    private static void invalidResourceTargetFailsClosed() {
        try {
            CompendiumRelationTarget.item("Minecraft:Wheat");
            throw new AssertionError("expected resource target validation failure");
        } catch (IllegalArgumentException expected) {
            truth(expected.getMessage().contains("resource"));
        }
    }

    private static void truth(boolean value) { if (!value) throw new AssertionError("expected true"); }
    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
