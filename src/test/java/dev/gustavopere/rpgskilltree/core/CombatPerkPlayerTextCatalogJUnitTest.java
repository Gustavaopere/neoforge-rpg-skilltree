package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

final class CombatPerkPlayerTextCatalogJUnitTest {
    @Test
    void publishesExactlyTheApprovedPlayerFacingBatchThroughA0040() {
        Map<String, CombatPerkPlayerTextCatalog.PlayerText> entries = CombatPerkPlayerTextCatalog.all();

        assertEquals(40, entries.size());
        for (int number = 1; number <= 40; number++) {
            assertTrue(entries.containsKey("A%04d".formatted(number)), "missing A%04d".formatted(number));
        }
        assertFalse(entries.containsKey("A0041"));
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
    }
}
