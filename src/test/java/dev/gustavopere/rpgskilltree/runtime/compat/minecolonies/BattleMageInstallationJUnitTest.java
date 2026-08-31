package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

final class BattleMageInstallationJUnitTest {
    @Test
    void installerRunsOnlyForAcceptedProviderPairAndVersions() {
        AtomicInteger calls = new AtomicInteger();

        assertEquals(
            BattleMageIntegrationState.ABSENT_PROVIDER,
            BattleMageIntegrationBootstrap.install(false, true, "1.1.1375", "3.16.3", calls::incrementAndGet)
        );
        assertEquals(
            BattleMageIntegrationState.UNSUPPORTED_VERSION,
            BattleMageIntegrationBootstrap.install(true, true, "1.1.1376", "3.16.3", calls::incrementAndGet)
        );
        assertEquals(
            BattleMageIntegrationState.UNSUPPORTED_VERSION,
            BattleMageIntegrationBootstrap.install(true, true, "1.1.1375", "3.16.4", calls::incrementAndGet)
        );
        assertEquals(0, calls.get());

        assertEquals(
            BattleMageIntegrationState.ACTIVE,
            BattleMageIntegrationBootstrap.install(true, true, "1.1.1375", "3.16.3", calls::incrementAndGet)
        );
        assertEquals(1, calls.get());
    }

    @Test
    void installationFailureIsFailClosed() {
        assertEquals(
            BattleMageIntegrationState.FAILED_CLOSED,
            BattleMageIntegrationBootstrap.install(true, true, "1.1.1375", "3.16.3", () -> {
                throw new LinkageError("provider API changed");
            })
        );
        assertEquals(
            BattleMageIntegrationState.FAILED_CLOSED,
            BattleMageIntegrationBootstrap.install(true, true, "1.1.1375", "3.16.3", () -> {
                throw new IllegalStateException("registration rejected");
            })
        );
    }
}
