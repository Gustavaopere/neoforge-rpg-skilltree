package dev.gustavopere.rpgskilltree.runtime.compat.goety;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

final class GoetyIntegrationBootstrapJUnitTest {
    @Test
    void absentProviderDoesNotRegister() {
        AtomicInteger registrations = new AtomicInteger();

        GoetyIntegrationState state = GoetyIntegrationBootstrap.install(
            false,
            "absent",
            registrations::incrementAndGet
        );

        assertEquals(GoetyIntegrationState.ABSENT_PROVIDER, state);
        assertEquals(0, registrations.get());
    }

    @Test
    void unsupportedVersionFailsClosedWithoutRegistration() {
        AtomicInteger registrations = new AtomicInteger();

        GoetyIntegrationState state = GoetyIntegrationBootstrap.install(
            true,
            "3.1.5",
            registrations::incrementAndGet
        );

        assertEquals(GoetyIntegrationState.UNSUPPORTED_VERSION, state);
        assertEquals(0, registrations.get());
    }

    @Test
    void exactAuditedVersionRegistersExactlyOnce() {
        AtomicInteger registrations = new AtomicInteger();

        GoetyIntegrationState state = GoetyIntegrationBootstrap.install(
            true,
            GoetyVersionContract.SUPPORTED_VERSION,
            registrations::incrementAndGet
        );

        assertEquals(GoetyIntegrationState.ACTIVE, state);
        assertEquals(1, registrations.get());
    }

    @Test
    void registrarFailureFailsClosed() {
        GoetyIntegrationState state = GoetyIntegrationBootstrap.install(
            true,
            GoetyVersionContract.SUPPORTED_VERSION,
            () -> {
                throw new LinkageError("simulated optional-provider linkage failure");
            }
        );

        assertEquals(GoetyIntegrationState.FAILED_CLOSED, state);
    }
}
