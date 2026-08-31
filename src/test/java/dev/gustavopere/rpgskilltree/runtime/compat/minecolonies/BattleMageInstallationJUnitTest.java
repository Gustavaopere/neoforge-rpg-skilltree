package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class BattleMageInstallationJUnitTest {
    @Test
    void installerRunsOnlyForAcceptedProviderPairAndVersion() {
        AtomicInteger calls = new AtomicInteger();

        assertEquals(
            BattleMageIntegrationState.ABSENT_PROVIDER,
            BattleMageIntegrationBootstrap.install(false, true, "1.1.1375", calls::incrementAndGet)
        );
        assertEquals(
            BattleMageIntegrationState.UNSUPPORTED_VERSION,
            BattleMageIntegrationBootstrap.install(true, true, "1.1.1376", calls::incrementAndGet)
        );
        assertEquals(0, calls.get());

        assertEquals(
            BattleMageIntegrationState.ACTIVE,
            BattleMageIntegrationBootstrap.install(true, true, "1.1.1375", calls::incrementAndGet)
        );
        assertEquals(1, calls.get());
    }

    @Test
    void installationFailureIsFailClosed() {
        assertEquals(
            BattleMageIntegrationState.FAILED_CLOSED,
            BattleMageIntegrationBootstrap.install(true, true, "1.1.1375", () -> {
                throw new LinkageError("provider API changed");
            })
        );
        assertEquals(
            BattleMageIntegrationState.FAILED_CLOSED,
            BattleMageIntegrationBootstrap.install(true, true, "1.1.1375", () -> {
                throw new IllegalStateException("registration rejected");
            })
        );
    }
}
