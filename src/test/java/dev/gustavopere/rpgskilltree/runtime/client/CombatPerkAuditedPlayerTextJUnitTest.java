package dev.gustavopere.rpgskilltree.runtime.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CombatPerkAuditedPlayerTextJUnitTest {
    @Test
    void auditedA0001A0020RangeHasEffectAndGateText() {
        for (int index = 1; index <= 20; index++) {
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
        assertEquals(
            "+2% de velocidade/ritmo efetivo com adagas por rank, até +6%, respeitando o moveset/provider.",
            CombatPerkClientText.nodeEffect("rpgskilltree:combat/a0020").orElseThrow()
        );
        assertEquals(
            "Gateway `epic_dagger` acessível + A0019 ≥ 2 ranks; gateway da Árvore Exterior.",
            CombatPerkClientText.nodeGate("rpgskilltree:combat/a0020").orElseThrow()
        );
    }

    @Test
    void unauditedNextRangeRemainsFailClosed() {
        assertTrue(CombatPerkClientText.nodeEffect("rpgskilltree:combat/a0021").isEmpty());
        assertTrue(CombatPerkClientText.nodeGate("rpgskilltree:combat/a0021").isEmpty());
        assertTrue(CombatPerkClientText.nodeEffect("rpgskilltree:combat/a0100").isEmpty());
    }
}
