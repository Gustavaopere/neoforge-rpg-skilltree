package dev.gustavopere.rpgskilltree.runtime.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CombatPerkAuditedPlayerTextJUnitTest {
    @Test
    void auditedA0001A0050RangeHasEffectAndGateText() {
        for (int index = 1; index <= 50; index++) {
            String nodeId = "rpgskilltree:combat/a%04d".formatted(index);
            assertTrue(CombatPerkClientText.nodeEffect(nodeId).isPresent(), nodeId + " effect");
            assertTrue(CombatPerkClientText.nodeGate(nodeId).isPresent(), nodeId + " gate");
        }
    }

    @Test
    void generatedTextPreservesApprovedAuditSnapshot() {
        assertEquals(
            "+3% de dano com espadas por rank, máximo +9%.",
            CombatPerkClientText.nodeEffect("rpgskilltree:combat/a0001").orElseThrow()
        );
        assertEquals(
            "nível 8 + `epicfight:sword` ≥60 + Gateway `epic_sword`.",
            CombatPerkClientText.nodeGate("rpgskilltree:combat/a0001").orElseThrow()
        );
        assertTrue(
            CombatPerkClientText.nodeEffect("rpgskilltree:combat/a0041").orElseThrow()
                .contains("commit pós-hit")
        );
        assertTrue(
            CombatPerkClientText.nodeEffect("rpgskilltree:combat/a0044").orElseThrow()
                .contains("INDISPONÍVEL/NÃO COMPRÁVEL")
        );
        assertTrue(
            CombatPerkClientText.nodeEffect("rpgskilltree:combat/a0047").orElseThrow()
                .contains("A0047 também fica indisponível/não comprável")
        );
        assertTrue(
            CombatPerkClientText.nodeEffect("rpgskilltree:combat/a0050").orElseThrow()
                .contains("INDISPONÍVEL/NÃO COMPRÁVEL")
        );
    }

    @Test
    void unauditedNextRangeRemainsFailClosed() {
        assertTrue(CombatPerkClientText.nodeEffect("rpgskilltree:combat/a0051").isEmpty());
        assertTrue(CombatPerkClientText.nodeGate("rpgskilltree:combat/a0051").isEmpty());
        assertTrue(CombatPerkClientText.nodeEffect("rpgskilltree:combat/a0100").isEmpty());
    }
}
