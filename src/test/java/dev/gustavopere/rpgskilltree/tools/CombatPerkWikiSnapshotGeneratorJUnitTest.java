package dev.gustavopere.rpgskilltree.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

final class CombatPerkWikiSnapshotGeneratorJUnitTest {
    private static final Path SNAPSHOT = Path.of("wiki/generated/combat-perks.json");

    @Test
    void rendersExactlyTheCanonicalA0001A0100SemanticSnapshotWithoutInventingUnauditedText() {
        String json = CombatPerkWikiSnapshotGenerator.renderJson();

        assertTrue(json.contains("\"treeId\": \"rpgskilltree:runtime/combat_perks\""));
        assertEquals(100, occurrences(json, "\"id\": \"rpgskilltree:combat/a"));
        assertTrue(json.contains("\"code\": \"A0001\""));
        assertTrue(json.contains("\"name\": \"Treino com Espadas I\""));
        assertTrue(json.contains("+3% de dano com espadas por rank, máximo +9%."));
        assertTrue(json.contains("\"code\": \"A0021\""));
        assertTrue(json.contains("\"name\": \"Precisão com Adagas\""));
        assertFalse(json.contains("\"code\": \"A0021\"\n      \"description\": \"chance de crítico"));
    }

    @Test
    void checkedInSnapshotMatchesTheCanonicalGeneratorExactly() throws Exception {
        String expected = CombatPerkWikiSnapshotGenerator.renderJson();
        if (!Files.isRegularFile(SNAPSHOT)) {
            throw new AssertionError(
                "semantic combat wiki snapshot is missing: " + SNAPSHOT + "\n"
                    + "--- GENERATED SNAPSHOT START ---\n"
                    + expected
                    + "--- GENERATED SNAPSHOT END ---"
            );
        }
        String actual = Files.readString(SNAPSHOT, StandardCharsets.UTF_8);
        assertEquals(expected, actual, "semantic combat wiki snapshot drifted from canonical model");
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
