package dev.gustavopere.rpgskilltree.runtime.compat.create;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

final class CreateIntegrationBootstrapJUnitTest {
    @Test
    void absentProviderDoesNotRegister() {
        AtomicInteger registrations = new AtomicInteger();

        CreateIntegrationState state = CreateIntegrationBootstrap.install(
            false,
            "absent",
            registrations::incrementAndGet
        );

        assertEquals(CreateIntegrationState.ABSENT_PROVIDER, state);
        assertEquals(0, registrations.get());
    }

    @Test
    void unsupportedVersionFailsClosedWithoutRegistration() {
        AtomicInteger registrations = new AtomicInteger();

        CreateIntegrationState state = CreateIntegrationBootstrap.install(
            true,
            "6.0.11",
            registrations::incrementAndGet
        );

        assertEquals(CreateIntegrationState.UNSUPPORTED_VERSION, state);
        assertEquals(0, registrations.get());
    }

    @Test
    void exactAuditedVersionRegistersExactlyOnce() {
        AtomicInteger registrations = new AtomicInteger();

        CreateIntegrationState state = CreateIntegrationBootstrap.install(
            true,
            CreateVersionContract.SUPPORTED_VERSION,
            registrations::incrementAndGet
        );

        assertEquals(CreateIntegrationState.ACTIVE, state);
        assertEquals(1, registrations.get());
    }

    @Test
    void registrarFailureFailsClosed() {
        CreateIntegrationState state = CreateIntegrationBootstrap.install(
            true,
            CreateVersionContract.SUPPORTED_VERSION,
            () -> {
                throw new LinkageError("simulated optional-provider linkage failure");
            }
        );

        assertEquals(CreateIntegrationState.FAILED_CLOSED, state);
    }
}
