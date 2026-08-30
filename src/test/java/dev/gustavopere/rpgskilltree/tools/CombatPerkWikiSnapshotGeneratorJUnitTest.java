package dev.gustavopere.rpgskilltree.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

final class CombatPerkWikiSnapshotGeneratorJUnitTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void rendersExactlyTheCanonicalA0001A0100SemanticSnapshotWithoutInventingTextBeyondTheApprovedBatch() {
        String json = CombatPerkWikiSnapshotGenerator.renderJson();

        assertTrue(json.contains("\"treeId\": \"rpgskilltree:runtime/combat_perks\""));
        assertEquals(100, occurrences(json, "\"id\": \"rpgskilltree:combat/a"));
        assertTrue(json.contains("\"code\": \"A0001\""));
        assertTrue(json.contains("\"name\": \"Treino com Espadas I\""));
        assertTrue(json.contains("+3% de dano com espadas por rank, máximo +9%."));

        assertTrue(json.contains("\"code\": \"A0021\""));
        assertTrue(json.contains("\"name\": \"Precisão com Adagas\""));
        assertTrue(json.contains("+3% de chance crítica com adagas por rank, máximo +9%."));
        assertTrue(json.contains("\"code\": \"A0030\""));
        assertTrue(json.contains("Sem receipt nativo de guard-break"));

        assertTrue(json.contains("\"code\": \"A0031\""));
        assertTrue(json.contains("\"code\": \"A0031\",\n      \"name\": \"Treino com Maças I\",\n      \"description\": null"));
        assertFalse(json.contains("\"code\": \"A0031\"\n      \"description\": \""));
    }

    @Test
    void writesAndChecksDerivedSnapshotWithoutRequiringACommittedIntermediateFile() throws Exception {
        Path snapshot = temporaryDirectory.resolve("combat-perks.json");
        String expected = CombatPerkWikiSnapshotGenerator.renderJson();

        CombatPerkWikiSnapshotGenerator.write(snapshot);
        assertEquals(expected, Files.readString(snapshot, StandardCharsets.UTF_8));
        CombatPerkWikiSnapshotGenerator.check(snapshot);

        Files.writeString(snapshot, expected.replace("Treino com Espadas I", "DRIFT"), StandardCharsets.UTF_8);
        assertThrows(IllegalStateException.class, () -> CombatPerkWikiSnapshotGenerator.check(snapshot));
    }

    private static int occurrences(String value, String needle) {
        int count = 0;
        int offset = 0;
        while ((offset = value.indexOf(needle, offset)) >= 0) {
            count++;
            offset += needle.length();
        }
        return count;
    }
}