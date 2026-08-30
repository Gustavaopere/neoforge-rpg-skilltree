package dev.gustavopere.rpgskilltree.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkPlayerTextCatalog;
import dev.gustavopere.rpgskilltree.core.CombatPerkTreeModel;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkCatalog;
import java.util.List;
import org.junit.jupiter.api.Test;

final class CombatPerkWikiCatalogGeneratorJUnitTest {
    @Test
    void rendersExactlyTheServerAuthoritativeA0001A0100AcquisitionCatalog() {
        String document = CombatPerkWikiCatalogGenerator.renderDocument();
        List<String> rows = document.lines()
            .filter(line -> line.startsWith("| `rpgskilltree:combat/a"))
            .toList();

        assertEquals(100, rows.size());
        assertEquals(100, CombatPerkTreeModel.all().size());

        for (int number = 1; number <= 100; number++) {
            String code = "A%04d".formatted(number);
            CombatPerkTreeModel.Node node = CombatPerkTreeModel.node(code).orElseThrow();
            String row = rows.stream()
                .filter(line -> line.contains("`" + node.nodeId() + "`"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("missing wiki row for " + code));

            assertTrue(row.contains("| " + code + " |"), code + " code");
            assertTrue(row.contains("| " + NotionCombatPerkCatalog.definition(code).orElseThrow().name() + " |"), code + " name");
            assertTrue(row.contains("| " + node.maxRank() + " | " + node.costPerRank() + " |"), code + " ranks/cost");
        }

        String a0001 = row(document, "A0001");
        assertTrue(a0001.contains(CombatPerkPlayerTextCatalog.entry("A0001").orElseThrow().effect()));
        assertTrue(a0001.contains("Mastery `epicfight:sword` ≥ 60"));
        assertTrue(a0001.contains("Nó `rpgskilltree:martial_000` rank ≥ 1"));

        String a0021 = row(document, "A0021");
        assertTrue(a0021.contains("| Precisão com Adagas | — |"));
        assertFalse(a0021.toLowerCase().contains("chance de crítico"));
        assertTrue(CombatPerkPlayerTextCatalog.entry("A0021").isEmpty());
    }

    @Test
    void renderedIdsMatchTheCanonicalNodeBindingWithoutAliases() {
        String document = CombatPerkWikiCatalogGenerator.renderDocument();
        for (int number = 1; number <= 100; number++) {
            String code = "A%04d".formatted(number);
            String nodeId = CombatPerkNodeBinding.nodeIdUnchecked(code);
            assertTrue(document.contains("`" + nodeId + "`"), code);
        }
        assertFalse(document.contains("`rpgskilltree:martial_001` | A0001"));
    }

    private static String row(String document, String code) {
        return document.lines()
            .filter(line -> line.startsWith("| `rpgskilltree:combat/a"))
            .filter(line -> line.contains("| " + code + " |"))
            .findFirst()
            .orElseThrow(() -> new AssertionError("missing wiki row for " + code));
    }
}
