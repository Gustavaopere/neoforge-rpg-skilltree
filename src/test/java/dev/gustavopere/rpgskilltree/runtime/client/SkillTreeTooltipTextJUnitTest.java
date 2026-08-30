package dev.gustavopere.rpgskilltree.runtime.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SkillTreeTooltipTextJUnitTest {
    @Test
    void auditedContentAndPurchaseStateBecomeSemanticTooltipLines() {
        List<SkillTreeTooltipText.Line> lines = SkillTreeTooltipText.lines(
            "Duelista — Ímpeto",
            1,
            3,
            2,
            Optional.of("+3% de dano"),
            Optional.of("Gateway `epic_sword` + A0001"),
            SkillTreeTooltipText.PurchaseState.PURCHASABLE,
            true
        );

        assertEquals(List.of(
            new SkillTreeTooltipText.Line("screen.rpgskilltree.tooltip.meta", List.of("Duelista — Ímpeto", "1", "3", "2")),
            new SkillTreeTooltipText.Line("screen.rpgskilltree.tooltip.effect", List.of("+3% de dano")),
            new SkillTreeTooltipText.Line("screen.rpgskilltree.tooltip.requirement", List.of("Gateway epic_sword + A0001")),
            new SkillTreeTooltipText.Line("screen.rpgskilltree.tooltip.purchase", List.of()),
            new SkillTreeTooltipText.Line("screen.rpgskilltree.tooltip.respec", List.of())
        ), lines);
    }

    @Test
    void unauditedContentStaysAbsentAndStatusRemainsExplicit() {
        assertEquals(List.of(
            new SkillTreeTooltipText.Line("screen.rpgskilltree.tooltip.meta", List.of("Marcial", "0", "1", "1")),
            new SkillTreeTooltipText.Line("screen.rpgskilltree.tooltip.locked", List.of())
        ), SkillTreeTooltipText.lines(
            "Marcial", 0, 1, 1, Optional.empty(), Optional.empty(),
            SkillTreeTooltipText.PurchaseState.LOCKED, false
        ));

        assertEquals("screen.rpgskilltree.tooltip.purchased",
            SkillTreeTooltipText.lines(
                "Marcial", 1, 1, 1, Optional.empty(), Optional.empty(),
                SkillTreeTooltipText.PurchaseState.PURCHASED, false
            ).get(1).translationKey());
    }

    @Test
    void realScreenUsesSemanticTooltipModelAndAuditedCombatText() throws IOException {
        String source = Files.readString(Path.of(
            "src/main/java/dev/gustavopere/rpgskilltree/runtime/client/RpgSkillTreeScreen.java"
        ));

        assertTrue(source.contains("SkillTreeTooltipText.lines("));
        assertTrue(source.contains("CombatPerkClientText.nodeEffect(node.id())"));
        assertTrue(source.contains("CombatPerkClientText.nodeGate(node.id())"));
        assertFalse(source.contains("LMB: purchase"));
        assertFalse(source.contains("RMB: respec"));
        assertFalse(source.contains("Locked by path or requirements"));
    }
}
