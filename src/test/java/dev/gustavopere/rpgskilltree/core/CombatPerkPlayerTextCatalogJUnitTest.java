package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

final class CombatPerkPlayerTextCatalogJUnitTest {
    @Test
    void publishesExactlyTheApprovedPlayerFacingBatchThroughA0030() {
        Map<String, CombatPerkPlayerTextCatalog.PlayerText> entries = CombatPerkPlayerTextCatalog.all();

        assertEquals(30, entries.size());
        for (int number = 1; number <= 30; number++) {
            assertTrue(entries.containsKey("A%04d".formatted(number)), "missing A%04d".formatted(number));
        }
        assertFalse(entries.containsKey("A0031"));
    }

    @Test
    void preservesApprovedProviderBoundariesInsteadOfInventingFallbackEffects() {
        CombatPerkPlayerTextCatalog.PlayerText a0021 = CombatPerkPlayerTextCatalog.entry("A0021").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0028 = CombatPerkPlayerTextCatalog.entry("A0028").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0029 = CombatPerkPlayerTextCatalog.entry("A0029").orElseThrow();
        CombatPerkPlayerTextCatalog.PlayerText a0030 = CombatPerkPlayerTextCatalog.entry("A0030").orElseThrow();

        assertTrue(a0021.effect().contains("+3% de chance crítica com adagas por rank, máximo +9%."));
        assertTrue(a0028.effect().contains("benefício fica inativo"));
        assertTrue(a0028.effect().contains("não é convertido em dano, impacto, knockback, crítico ou Armor Negation"));
        assertTrue(a0029.effect().contains("Sem receipt inequívoco de heavy"));
        assertTrue(a0030.effect().contains("Sem receipt nativo de guard-break"));
        assertTrue(a0030.effect().contains("capstone fica indisponível"));
    }
}