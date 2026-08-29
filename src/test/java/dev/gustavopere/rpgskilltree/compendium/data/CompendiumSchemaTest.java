package dev.gustavopere.rpgskilltree.compendium.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class CompendiumSchemaTest {
    public static void main(String[] args) {
        validEntryDocumentPasses();
        legacyRelationDocumentPasses();
        typedRelationDocumentPasses();
        mixedRelationTargetFormatsFail();
        typedRelationRequiresBothKindAndTarget();
        missingSchemaVersionReportsFileAndField();
        unsupportedSchemaVersionFails();
        unknownDirectoryFailsClosed();
        System.out.println("CompendiumSchemaTest: PASS");
    }

    private static void validEntryDocumentPasses() {
        Map<String, Object> document = new LinkedHashMap<>();
        document.put("schema_version", 1);
        document.put("id", "ENTITY|minecraft:zombie");
        document.put("source_mod_id", "minecraft");
        document.put("translation_key", "entity.minecraft.zombie");
        document.put("content_version", 1);
        CompendiumSchemaValidator.validate(CompendiumDataKind.ENTRY, "rpgskilltree:entries/zombie", document);
    }

    private static void legacyRelationDocumentPasses() {
        CompendiumSchemaValidator.validate(
            CompendiumDataKind.RELATION,
            "rpgskilltree:relations/pig_overworld",
            Map.of(
                "schema_version", 1,
                "type", "BELONGS_TO_DIMENSION",
                "from", "ENTITY|minecraft:pig",
                "to", "DIMENSION|minecraft:overworld",
                "source", "REGISTRY"
            )
        );
    }

    private static void typedRelationDocumentPasses() {
        Map<String, Object> document = new LinkedHashMap<>();
        document.put("schema_version", 1);
        document.put("type", "BREEDS_WITH_ITEM");
        document.put("from", "ENTITY|minecraft:cow");
        document.put("target_kind", "ITEM");
        document.put("target", "minecraft:wheat");
        document.put("source", "CURATED_EDITORIAL");
        document.put("confidence", "EXACT");
        document.put("evidence_id", "minecraft:animal.isFood");
        CompendiumSchemaValidator.validate(CompendiumDataKind.RELATION, "rpgskilltree:relations/cow_wheat", document);
    }

    private static void mixedRelationTargetFormatsFail() {
        try {
            Map<String, Object> document = new LinkedHashMap<>();
            document.put("schema_version", 1);
            document.put("type", "EATS");
            document.put("from", "ENTITY|minecraft:cow");
            document.put("to", "ENTITY|minecraft:pig");
            document.put("target_kind", "ITEM");
            document.put("target", "minecraft:wheat");
            document.put("source", "REGISTRY");
            CompendiumSchemaValidator.validate(CompendiumDataKind.RELATION, "rpgskilltree:relations/invalid_mixed", document);
            throw new AssertionError("expected mixed relation target failure");
        } catch (CompendiumSchemaException expected) {
            truth(expected.getMessage().contains("target"));
        }
    }

    private static void typedRelationRequiresBothKindAndTarget() {
        try {
            CompendiumSchemaValidator.validate(
                CompendiumDataKind.RELATION,
                "rpgskilltree:relations/invalid_typed",
                Map.of(
                    "schema_version", 1,
                    "type", "EATS",
                    "from", "ENTITY|minecraft:cow",
                    "target_kind", "ITEM",
                    "source", "REGISTRY"
                )
            );
            throw new AssertionError("expected incomplete typed relation failure");
        } catch (CompendiumSchemaException expected) {
            truth(expected.getMessage().contains("target"));
        }
    }

    private static void missingSchemaVersionReportsFileAndField() {
        try {
            CompendiumSchemaValidator.validate(
                CompendiumDataKind.CATEGORY,
                "rpgskilltree:categories/fauna",
                Map.of("id", "fauna", "translation_key", "compendium.category.fauna")
            );
            throw new AssertionError("expected schema failure");
        } catch (CompendiumSchemaException expected) {
            truth(expected.getMessage().contains("rpgskilltree:categories/fauna"));
            truth(expected.getMessage().contains("schema_version"));
        }
    }

    private static void unsupportedSchemaVersionFails() {
        try {
            CompendiumSchemaValidator.validate(
                CompendiumDataKind.DISCOVERY,
                "rpgskilltree:discovery/zombie",
                Map.of("schema_version", 2, "id", "zombie", "entry", "ENTITY|minecraft:zombie", "trigger", "OBSERVATION")
            );
            throw new AssertionError("expected unsupported schema failure");
        } catch (CompendiumSchemaException expected) {
            truth(expected.getMessage().contains("schema_version"));
        }
    }

    private static void unknownDirectoryFailsClosed() {
        try {
            CompendiumDataKind.fromDirectory("mystery");
            throw new AssertionError("expected unknown directory failure");
        } catch (IllegalArgumentException expected) {
            eq("unknown compendium data directory: mystery", expected.getMessage());
        }
    }

    private static void truth(boolean value) { if (!value) throw new AssertionError("expected true"); }
    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
