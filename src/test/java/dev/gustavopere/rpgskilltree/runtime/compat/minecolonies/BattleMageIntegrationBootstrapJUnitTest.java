package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import org.junit.jupiter.api.Test;

final class BattleMageIntegrationBootstrapJUnitTest {
    @Test
    void mineColoniesIsAFirstClassOptionalProvider() {
        assertEquals("minecolonies", OptionalIntegrations.Provider.MINECOLONIES.modId());
    }

    @Test
    void exactProviderVersionsArePinned() {
        assertTrue(MineColoniesVersionContract.supports("1.1.1375-1.21.1-snapshot"));
        assertTrue(IronsBattleMageVersionContract.supports("1.21.1-3.16.3"));
        assertTrue(IronsBattleMageVersionContract.supports("3.16.3"));
        assertFalse(IronsBattleMageVersionContract.supports("3.16.4"));
    }

    @Test
    void bootstrapDecisionIsFailClosed() {
        assertEquals(
            BattleMageIntegrationState.ABSENT_PROVIDER,
            BattleMageIntegrationBootstrap.evaluate(false, true, "absent", "3.16.3")
        );
        assertEquals(
            BattleMageIntegrationState.ABSENT_PROVIDER,
            BattleMageIntegrationBootstrap.evaluate(true, false, "1.1.1375", "absent")
        );
        assertEquals(
            BattleMageIntegrationState.UNSUPPORTED_VERSION,
            BattleMageIntegrationBootstrap.evaluate(true, true, "1.1.1376-1.21.1-snapshot", "3.16.3")
        );
        assertEquals(
            BattleMageIntegrationState.UNSUPPORTED_VERSION,
            BattleMageIntegrationBootstrap.evaluate(true, true, "1.1.1375", "3.16.4")
        );
        assertEquals(
            BattleMageIntegrationState.ACTIVE,
            BattleMageIntegrationBootstrap.evaluate(true, true, "1.1.1375-1.21.1-snapshot", "3.16.3")
        );
    }
}
