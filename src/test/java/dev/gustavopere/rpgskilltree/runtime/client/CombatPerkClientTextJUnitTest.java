package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.core.NotionCombatPerkCatalog;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CombatPerkClientTextJUnitTest {
    @Test
    void canonicalCombatNamesComeFromTheVersionedNotionCatalog() {
        String expectedFirst = NotionCombatPerkCatalog.definition("A0001").orElseThrow().name();
        String expectedLast = NotionCombatPerkCatalog.definition("A0100").orElseThrow().name();

        assertEquals(expectedFirst, CombatPerkClientText.nodeDisplayName("rpgskilltree:combat/a0001").orElseThrow());
        assertEquals(expectedLast, CombatPerkClientText.nodeDisplayName("rpgskilltree:combat/a0100").orElseThrow());
    }

    @Test
    void nonCombatNodesDoNotReceiveInventedNames() {
        assertTrue(CombatPerkClientText.nodeDisplayName("rpgskilltree:martial_000").isEmpty());
        assertTrue(CombatPerkClientText.nodeDisplayName("minecraft:stone").isEmpty());
    }

    @Test
    void canonicalCombatTreeHasAnExplicitPortuguesePresentationTitle() {
        assertEquals(
            "Perks de Combate",
            CombatPerkClientText.treeDisplayName("rpgskilltree:runtime/combat_perks").orElseThrow()
        );
        assertTrue(CombatPerkClientText.treeDisplayName("rpgskilltree:main").isEmpty());
    }
}
