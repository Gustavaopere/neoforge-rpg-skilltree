package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import org.junit.jupiter.api.Test;

final class BattleMageIntegrationBootstrapJUnitTest {
    @Test
    void mineColoniesIsAFirstClassOptionalProvider() {
        assertEquals("minecolonies", OptionalIntegrations.Provider.MINECOLONIES.modId());
    }

    @Test
    void bootstrapDecisionIsFailClosed() {
        assertEquals(
            BattleMageIntegrationState.ABSENT_PROVIDER,
            BattleMageIntegrationBootstrap.evaluate(false, true, "absent")
        );
        assertEquals(
            BattleMageIntegrationState.ABSENT_PROVIDER,
            BattleMageIntegrationBootstrap.evaluate(true, false, "1.1.1375")
        );
        assertEquals(
            BattleMageIntegrationState.UNSUPPORTED_VERSION,
            BattleMageIntegrationBootstrap.evaluate(true, true, "1.1.1376-1.21.1-snapshot")
        );
        assertEquals(
            BattleMageIntegrationState.ACTIVE,
            BattleMageIntegrationBootstrap.evaluate(true, true, "1.1.1375-1.21.1-snapshot")
        );
    }
}
