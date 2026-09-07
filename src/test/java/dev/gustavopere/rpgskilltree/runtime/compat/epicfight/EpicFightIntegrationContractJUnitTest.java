package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EpicFightIntegrationContractJUnitTest {
    @Test
    void exactAuditedProviderOwnsTheHitPipeline() {
        assertTrue(EpicFightIntegrationContract.providerOwnsHitPipeline(true, "21.17.3.1"));
        assertFalse(EpicFightIntegrationContract.providerOwnsHitPipeline(false, "21.17.3.1"));
        assertFalse(EpicFightIntegrationContract.providerOwnsHitPipeline(true, "21.17.3.2"));
        assertFalse(EpicFightIntegrationContract.providerOwnsHitPipeline(true, null));
    }

    @Test
    void providerClassifiedWeaponsCannotUseVanillaFallbackSimultaneously() {
        assertFalse(EpicFightIntegrationContract.shouldUseVanillaFallback(true, true));
        assertFalse(EpicFightIntegrationContract.shouldUseVanillaFallback(true, false));
        assertTrue(EpicFightIntegrationContract.shouldUseVanillaFallback(false, true));
        assertFalse(EpicFightIntegrationContract.shouldUseVanillaFallback(false, false));
    }

    @Test
    void stageContractNamesTheThreeCanonicalEpicFightAttributes() {
        assertEquals(
            Set.of("epicfight:stamina", "epicfight:stamina_regen", "epicfight:impact"),
            EpicFightIntegrationContract.requiredAttributeIds()
        );
    }
}
