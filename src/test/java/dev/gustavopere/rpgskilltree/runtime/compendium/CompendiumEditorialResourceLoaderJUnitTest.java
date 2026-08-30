package dev.gustavopere.rpgskilltree.runtime.compendium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumProvenance;
import dev.gustavopere.rpgskilltree.compendium.api.DiscoveryPolicy;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.api.VisibilityPolicy;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialValidationException;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialAvailability;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class CompendiumEditorialResourceLoaderJUnitTest {
    private static final CompendiumEntryId WOLF_ID = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:wolf");
    private static final CompendiumEntryId TAIGA_ID = CompendiumEntryId.of(CompendiumEntryKind.BIOME, "minecraft:taiga");
    private static final ResourceLocation MINECRAFT_ENTITIES = resource("minecraft/entities.json");

    @Test
    void emptyCorpusIsValidAndCanonicalEntryIdIsDecodedExplicitly() {
        assertTrue(CompendiumEditorialResourceLoader.prepare(Map.of(), List.of(wolf())).entries().isEmpty());

        var snapshot = CompendiumEditorialResourceLoader.prepare(
            Map.of(MINECRAFT_ENTITIES, json(packageJson("minecraft", "ENTITY", validWolfEntry()))),
            List.of(wolf())
        );

        assertEquals(WOLF_ID, snapshot.entries().getFirst().entryId());
        assertEquals("Lobo", snapshot.entries().getFirst().title());
        assertEquals(EditorialAvailability.RUNTIME, snapshot.entries().getFirst().availability());
    }

    @Test
    void schemaLanguageAndPhysicalNamespaceAreFailClosed() {
        assertFailureContains(packageJson(2, "pt_br", "minecraft", "ENTITY", validWolfEntry()), List.of(wolf()), "schema");
        assertFailureContains(packageJson(1, "en_us", "minecraft", "ENTITY", validWolfEntry()), List.of(wolf()), "language");

        Map<ResourceLocation, JsonElement> mismatchedDirectory = Map.of(
            resource("wrong/entities.json"),
            json(packageJson("minecraft", "ENTITY", validWolfEntry()))
        );
        assertTrue(assertThrows(
            CompendiumEditorialValidationException.class,
            () -> CompendiumEditorialResourceLoader.prepare(mismatchedDirectory, List.of(wolf()))
        ).getMessage().contains("namespace"));
    }

    @Test
    void kindEntryIdAndNamespaceMustAgreeWithThePackage() {
        assertFailureContains(packageJson("minecraft", "BLOCK_FEATURE", validWolfEntry()), List.of(wolf()), "kind");
        assertFailureContains(
            packageJson("minecraft", "BIOME", validWolfEntry()),
            List.of(wolf()),
            "kind"
        );
        assertFailureContains(
            packageJson("minecraft", "ENTITY", validWolfEntry().replace("ENTITY:minecraft:wolf", "ENTITY:example:wolf")),
            List.of(wolf()),
            "namespace"
        );
        assertFailureContains(
            packageJson("minecraft", "ENTITY", validWolfEntry().replace("ENTITY:minecraft:wolf", "ENTITY|minecraft:wolf")),
            List.of(wolf()),
            "KIND:namespace:path"
        );
    }

    @Test
    void duplicateIdsAcrossResourcesAreRejected() {
        LinkedHashMap<ResourceLocation, JsonElement> resources = new LinkedHashMap<>();
        resources.put(resource("minecraft/a.json"), json(packageJson("minecraft", "ENTITY", validWolfEntry())));
        resources.put(resource("minecraft/b.json"), json(packageJson("minecraft", "ENTITY", validWolfEntry())));

        CompendiumEditorialValidationException failure = assertThrows(
            CompendiumEditorialValidationException.class,
            () -> CompendiumEditorialResourceLoader.prepare(resources, List.of(wolf()))
        );
        assertTrue(failure.getMessage().contains("duplicate"));
        assertTrue(failure.getMessage().contains("ENTITY:minecraft:wolf"));
    }

    @Test
    void proseSectionsAndSourcesRejectEmptyOrPlaceholderContent() {
        assertEntryMutationFails("\"title\":\"Lobo\"", "\"title\":\"TODO\"", "placeholder");
        assertEntryMutationFails("\"text\":\"Resumo válido.\"", "\"text\":\"   \"", "summary");
        assertEntryMutationFails("\"text\":\"Comportamento confirmado.\"", "\"text\":\"FIXME\"", "placeholder");
        assertEntryMutationFails("\"type\":\"RUNTIME\"", "\"type\":\"UNKNOWN_SOURCE\"", "source type");
        assertEntryMutationFails("\"ref\":\"minecraft:entity_type/minecraft:wolf\"", "\"ref\":\"...\"", "placeholder");
        assertEntryMutationFails("\"note\":\"fonte direta\"", "\"note\":\"TBD\"", "placeholder");
        assertEntryMutationFails("\"behavior\":", "\"Behavior Space\":", "section");
    }

    @Test
    void availabilityIsMandatoryAndValidatedAgainstTechnicalCatalog() {
        String missingAvailability = validWolfEntry().replace(",\"availability\":\"RUNTIME\"", "");
        assertFailureContains(packageJson("minecraft", "ENTITY", missingAvailability), List.of(wolf()), "availability");

        assertFailureContains(packageJson("minecraft", "ENTITY", validWolfEntry()), List.of(), "absent");

        String optional = validWolfEntry()
            .replace("ENTITY:minecraft:wolf", "ENTITY:minecraft:future_wolf")
            .replace("\"availability\":\"RUNTIME\"", "\"availability\":\"OPTIONAL\",\"availability_reason\":\"Addon não instalado.\"");
        var optionalSnapshot = CompendiumEditorialResourceLoader.prepare(
            Map.of(MINECRAFT_ENTITIES, json(packageJson("minecraft", "ENTITY", optional))),
            List.of(wolf())
        );
        assertEquals(EditorialAvailability.OPTIONAL, optionalSnapshot.entries().getFirst().availability());

        String maskedPresent = validWolfEntry().replace(
            "\"availability\":\"RUNTIME\"",
            "\"availability\":\"OPTIONAL\",\"availability_reason\":\"Não deveria mascarar runtime.\""
        );
        assertFailureContains(packageJson("minecraft", "ENTITY", maskedPresent), List.of(wolf()), "present");

        String legacyWithoutReason = validWolfEntry().replace("\"availability\":\"RUNTIME\"", "\"availability\":\"LEGACY\"");
        legacyWithoutReason = legacyWithoutReason.replace("ENTITY:minecraft:wolf", "ENTITY:minecraft:old_wolf");
        assertFailureContains(packageJson("minecraft", "ENTITY", legacyWithoutReason), List.of(wolf()), "availability_reason");
    }

    @Test
    void referencesResolveAgainstRuntimeOrValidEditorialEntries() {
        String runtimeReference = validWolfEntry().replace("\"references\":[]", "\"references\":[\"BIOME:minecraft:taiga\"]");
        var runtimeResolved = CompendiumEditorialResourceLoader.prepare(
            Map.of(MINECRAFT_ENTITIES, json(packageJson("minecraft", "ENTITY", runtimeReference))),
            List.of(wolf(), taiga())
        );
        assertEquals(List.of(TAIGA_ID), runtimeResolved.entries().getFirst().references());

        String unresolved = validWolfEntry().replace("\"references\":[]", "\"references\":[\"ENTITY:example:ghost\"]");
        assertFailureContains(packageJson("minecraft", "ENTITY", unresolved), List.of(wolf()), "unresolved reference");

        LinkedHashMap<ResourceLocation, JsonElement> resources = new LinkedHashMap<>();
        resources.put(
            MINECRAFT_ENTITIES,
            json(packageJson("minecraft", "ENTITY", unresolved))
        );
        resources.put(
            resource("example/entities.json"),
            json(packageJson("example", "ENTITY", optionalGhostEntry()))
        );
        var editorialResolved = CompendiumEditorialResourceLoader.prepare(resources, List.of(wolf()));
        assertEquals(2, editorialResolved.entries().size());
        assertEquals(CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "example:ghost"), editorialResolved.entries().getFirst().references().getFirst());
    }

    private static void assertEntryMutationFails(String target, String replacement, String expectedMessage) {
        String mutated = validWolfEntry().replace(target, replacement);
        assertFailureContains(packageJson("minecraft", "ENTITY", mutated), List.of(wolf()), expectedMessage);
    }

    private static void assertFailureContains(String payload, List<CompendiumEntry> technical, String expectedMessage) {
        CompendiumEditorialValidationException failure = assertThrows(
            CompendiumEditorialValidationException.class,
            () -> CompendiumEditorialResourceLoader.prepare(Map.of(MINECRAFT_ENTITIES, json(payload)), technical)
        );
        assertTrue(
            failure.getMessage().toLowerCase().contains(expectedMessage.toLowerCase()),
            () -> "expected message containing '" + expectedMessage + "', got: " + failure.getMessage()
        );
    }

    private static ResourceLocation resource(String relative) {
        return ResourceLocation.parse("rpgskilltree:compendium/editorial/pt_br/" + relative);
    }

    private static JsonElement json(String text) {
        return JsonParser.parseString(text);
    }

    private static String packageJson(String namespace, String kind, String entry) {
        return packageJson(1, "pt_br", namespace, kind, entry);
    }

    private static String packageJson(int schema, String language, String namespace, String kind, String entry) {
        return "{\"schema\":" + schema
            + ",\"language\":\"" + language + "\""
            + ",\"namespace\":\"" + namespace + "\""
            + ",\"kind\":\"" + kind + "\""
            + ",\"entries\":[" + entry + "]}";
    }

    private static String validWolfEntry() {
        return """
            {"entry_id":"ENTITY:minecraft:wolf","title":"Lobo",
             "summary":{"text":"Resumo válido.","sources":[{"type":"RUNTIME","ref":"minecraft:entity_type/minecraft:wolf","note":"fonte direta"}]},
             "sections":{"behavior":{"text":"Comportamento confirmado.","sources":[{"type":"OFFICIAL_CODE","ref":"net/minecraft/world/entity/animal/Wolf.java"}]}},
             "references":[],"review_status":"REVIEWED","availability":"RUNTIME"}
            """;
    }

    private static String optionalGhostEntry() {
        return """
            {"entry_id":"ENTITY:example:ghost","title":"Fantasma opcional",
             "summary":{"text":"Conteúdo opcional confirmado.","sources":[{"type":"OFFICIAL_DOCS","ref":"https://example.invalid/ghost"}]},
             "sections":{},"references":[],"review_status":"DRAFT","availability":"OPTIONAL",
             "availability_reason":"Provider opcional ausente do runtime atual."}
            """;
    }

    private static CompendiumEntry wolf() {
        return technical(WOLF_ID, "minecraft", "entity.minecraft.wolf");
    }

    private static CompendiumEntry taiga() {
        return technical(TAIGA_ID, "minecraft", "biome.minecraft.taiga");
    }

    private static CompendiumEntry technical(CompendiumEntryId id, String sourceModId, String translationKey) {
        return new CompendiumEntry(
            id,
            sourceModId,
            translationKey,
            Set.of(),
            List.of(),
            List.of(),
            DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.VISIBLE,
            new CompendiumProvenance(FactSource.REGISTRY, "test"),
            1
        );
    }
}
