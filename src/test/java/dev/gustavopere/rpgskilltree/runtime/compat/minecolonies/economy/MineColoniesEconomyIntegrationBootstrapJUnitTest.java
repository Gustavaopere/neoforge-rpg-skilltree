package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class MineColoniesEconomyIntegrationBootstrapJUnitTest {
    @Test
    void providerAbsenceAndUnsupportedVersionStayDisabled() {
        assertEquals(
            MineColoniesEconomyIntegrationState.ABSENT_PROVIDER,
            MineColoniesEconomyIntegrationBootstrap.evaluate(false, "absent")
        );
        assertEquals(
            MineColoniesEconomyIntegrationState.UNSUPPORTED_VERSION,
            MineColoniesEconomyIntegrationBootstrap.evaluate(true, "1.1.1374-1.21.1-snapshot")
        );
        assertEquals(
            MineColoniesEconomyIntegrationState.UNSUPPORTED_VERSION,
            MineColoniesEconomyIntegrationBootstrap.evaluate(true, "1.1.1377-1.21.1-snapshot")
        );
    }

    @Test
    void explicitlyAuditedVersionsActivateAndRegistrarFailureFailsClosed() {
        assertEquals(
            MineColoniesEconomyIntegrationState.ACTIVE,
            MineColoniesEconomyIntegrationBootstrap.evaluate(true, "1.1.1375-1.21.1-snapshot")
        );
        assertEquals(
            MineColoniesEconomyIntegrationState.ACTIVE,
            MineColoniesEconomyIntegrationBootstrap.evaluate(true, "1.1.1376-1.21.1-snapshot")
        );
        assertEquals(
            MineColoniesEconomyIntegrationState.FAILED_CLOSED,
            MineColoniesEconomyIntegrationBootstrap.install(
                true,
                "1.1.1376-1.21.1-snapshot",
                () -> { throw new IllegalStateException("registration failed"); }
            )
        );
    }
}
