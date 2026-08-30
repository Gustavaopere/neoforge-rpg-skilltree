package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

final class CombatPerkPlayerTextCatalogJUnitTest {
    @Test
    void publishesExactlyTheApprovedPlayerFacingBatchThroughA0050() {
        Map<String, CombatPerkPlayerTextCatalog.PlayerText> entries = CombatPerkPlayerTextCatalog.all();

        assertEquals(50, entries.size());
        for (int number = 1; number <= 50; number++) {
            assertTrue(entries.containsKey("A%04d".formatted(number)), "missing A%04d".formatted(number));
        }
        assertFalse(entries.containsKey("A0051"));
    }

    @Test
    void preservesApprovedProviderBoundariesInsteadOfInventingFallbackEffects() {
        CombatPerkPlayerTextCatalog.PlayerText a0021 = CombatPerkPlayerTextCatalog.entry("A0021").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0028 = CombatPerkPlayerTextCatalog.entry("A0028").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0029 = CombatPerkPlayerTextCatalog.entry("A0029").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0030 = CombatPerkPlayerTextCatalog.entry("A0030").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0031 = CombatPerkPlayerTextCatalog.entry("A0031").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0035 = CombatPerkPlayerTextCatalog.entry("A0035").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0036 = CombatPerkPlayerTextCatalog.entry("A0036").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0037 = CombatPerkPlayerTextCatalog.entry("A0037").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0040 = CombatPerkPlayerTextCatalog.entry("A0040").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0041 = CombatPerkPlayerTextCatalog.entry("A0041").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0042 = CombatPerkPlayerTextCatalog.entry("A0042").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0043 = CombatPerkPlayerTextCatalog.entry("A0043").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0044 = CombatPerkPlayerTextCatalog.entry("A0044").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0047 = CombatPerkPlayerTextCatalog.entry("A0047").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0049 = CombatPerkPlayerTextCatalog.entry("A0049").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0050 = CombatPerkPlayerTextCatalog.entry("A0050").orElseThrow();

        assertTrue(a0021.effect().contains("+3% de chance crítica com adagas por rank, máximo +9%."));
        assertTrue(a0028.effect().contains("benefício fica inativo"));
        assertTrue(a0028.effect().contains("não é convertido em dano, impacto, knockback, crítico ou Armor Negation"));
        assertTrue(a0029.effect().contains("Sem receipt inequívoco de heavy"));
        assertTrue(a0030.effect().contains("Sem receipt nativo de guard-break"));
        assertTrue(a0030.effect().contains("capstone fica indisponível"));
        assertTrue(a0031.effect().contains("Sem classificação MACE segura, a disciplina permanece fail-closed"));
        assertTrue(a0035.effect().contains("commit pós-hit confirmado"));
        assertTrue(a0035.effect().contains("implementação completa permanece não confirmada"));
        assertTrue(a0036.effect().contains("Sem heavy receipt provider-native"));
        assertTrue(a0036.effect().contains("capstone permanece fail-closed"));
        assertTrue(a0037.effect().contains("Sem classificação SCYTHE segura, a disciplina permanece fail-closed"));
        assertTrue(a0040.effect().contains("cleanup bounded em unload/despawn ainda não está confirmado"));
        assertTrue(a0041.effect().contains("commit pós-hit"));
        assertTrue(a0041.effect().contains("implementação permanece parcial"));
        assertTrue(a0042.effect().contains("eligible_kill"));
        assertTrue(a0042.effect().contains("refund de Stamina permanece 0"));
        assertTrue(a0043.gate().contains("`epicfight:bow` ≥ 60"));
        assertTrue(a0043.effect().contains("producer de Mastery BOW"));
        assertTrue(a0044.effect().contains("INDISPONÍVEL/NÃO COMPRÁVEL"));
        assertTrue(a0047.effect().contains("A0047 também fica indisponível/não comprável"));
        assertTrue(a0049.gate().contains("`epicfight:crossbow` ≥ 60"));
        assertTrue(a0049.effect().contains("producer de Mastery CROSSBOW"));
        assertTrue(a0050.effect().contains("INDISPONÍVEL/NÃO COMPRÁVEL"));
    }
}
