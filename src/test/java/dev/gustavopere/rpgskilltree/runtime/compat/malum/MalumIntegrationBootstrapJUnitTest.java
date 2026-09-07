package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

final class MalumIntegrationBootstrapJUnitTest {
    @Test
    void absentProviderDoesNotRegister() {
        AtomicInteger registrations = new AtomicInteger();

        MalumIntegrationState state = MalumIntegrationBootstrap.install(
            false,
            "absent",
            registrations::incrementAndGet
        );

        assertEquals(MalumIntegrationState.ABSENT_PROVIDER, state);
        assertEquals(0, registrations.get());
    }

    @Test
    void unsupportedVersionFailsClosedWithoutRegistration() {
        AtomicInteger registrations = new AtomicInteger();

        MalumIntegrationState state = MalumIntegrationBootstrap.install(
            true,
            "1.8.3",
            registrations::incrementAndGet
        );

        assertEquals(MalumIntegrationState.UNSUPPORTED_VERSION, state);
        assertEquals(0, registrations.get());
    }

    @Test
    void exactAuditedVersionRegistersExactlyOnce() {
        AtomicInteger registrations = new AtomicInteger();

        MalumIntegrationState state = MalumIntegrationBootstrap.install(
            true,
            MalumVersionContract.SUPPORTED_VERSION,
            registrations::incrementAndGet
        );

        assertEquals(MalumIntegrationState.ACTIVE, state);
        assertEquals(1, registrations.get());
    }

    @Test
    void registrarFailureFailsClosed() {
        MalumIntegrationState state = MalumIntegrationBootstrap.install(
            true,
            MalumVersionContract.SUPPORTED_VERSION,
            () -> {
                throw new LinkageError("simulated optional-provider linkage failure");
            }
        );

        assertEquals(MalumIntegrationState.FAILED_CLOSED, state);
    }
}
