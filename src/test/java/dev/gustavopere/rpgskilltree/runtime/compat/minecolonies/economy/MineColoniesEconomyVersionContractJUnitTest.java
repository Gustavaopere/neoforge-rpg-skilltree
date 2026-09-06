package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class MineColoniesEconomyVersionContractJUnitTest {
    @Test
    void acceptsOnlyAuditedInstalledArtifactVersion() {
        assertTrue(MineColoniesEconomyVersionContract.supports("1.1.1375-1.21.1-snapshot"));
        assertTrue(MineColoniesEconomyVersionContract.supports(" 1.1.1375-1.21.1-snapshot "));

        assertFalse(MineColoniesEconomyVersionContract.supports("1.1.1375"));
        assertFalse(MineColoniesEconomyVersionContract.supports("1.1.1374-1.21.1-snapshot"));
        assertFalse(MineColoniesEconomyVersionContract.supports("1.1.1376-1.21.1-snapshot"));
        assertFalse(MineColoniesEconomyVersionContract.supports(null));
    }
}
