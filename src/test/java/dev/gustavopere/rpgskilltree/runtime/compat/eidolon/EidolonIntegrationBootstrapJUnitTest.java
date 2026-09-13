package dev.gustavopere.rpgskilltree.runtime.compat.eidolon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

final class EidolonIntegrationBootstrapJUnitTest {
    @Test
    void absentProviderDoesNotRegister() {
        AtomicInteger registrations = new AtomicInteger();

        EidolonIntegrationState state = EidolonIntegrationBootstrap.install(
            false,
            "absent",
            registrations::incrementAndGet
        );

        assertEquals(EidolonIntegrationState.ABSENT_PROVIDER, state);
        assertEquals(0, registrations.get());
    }

    @Test
    void unsupportedVersionFailsClosedWithoutRegistration() {
        AtomicInteger registrations = new AtomicInteger();

        EidolonIntegrationState state = EidolonIntegrationBootstrap.install(
            true,
            "0.5.0.3",
            registrations::incrementAndGet
        );

        assertEquals(EidolonIntegrationState.UNSUPPORTED_VERSION, state);
        assertEquals(0, registrations.get());
    }

    @Test
    void exactAuditedVersionRegistersExactlyOnce() {
        AtomicInteger registrations = new AtomicInteger();

        EidolonIntegrationState state = EidolonIntegrationBootstrap.install(
            true,
            EidolonVersionContract.SUPPORTED_VERSION,
            registrations::incrementAndGet
        );

        assertEquals(EidolonIntegrationState.ACTIVE, state);
        assertEquals(1, registrations.get());
    }

    @Test
    void registrarFailureFailsClosed() {
        EidolonIntegrationState state = EidolonIntegrationBootstrap.install(
            true,
            EidolonVersionContract.SUPPORTED_VERSION,
            () -> {
                throw new LinkageError("simulated optional-provider linkage failure");
            }
        );

        assertEquals(EidolonIntegrationState.FAILED_CLOSED, state);
    }
}
