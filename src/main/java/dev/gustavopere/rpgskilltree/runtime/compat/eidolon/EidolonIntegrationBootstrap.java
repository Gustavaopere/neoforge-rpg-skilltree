package dev.gustavopere.rpgskilltree.runtime.compat.eidolon;

import java.util.Objects;

public final class EidolonIntegrationBootstrap {
    private EidolonIntegrationBootstrap() {
    }

    public static EidolonIntegrationState install(boolean providerLoaded, String providerVersion, Runnable registrar) {
        Objects.requireNonNull(registrar, "registrar");

        EidolonIntegrationState state = evaluate(providerLoaded, providerVersion);
        if (state != EidolonIntegrationState.ACTIVE) {
            return state;
        }

        try {
            registrar.run();
            return EidolonIntegrationState.ACTIVE;
        } catch (RuntimeException | LinkageError failure) {
            return EidolonIntegrationState.FAILED_CLOSED;
        }
    }

    public static EidolonIntegrationState evaluate(boolean providerLoaded, String providerVersion) {
        if (!providerLoaded) {
            return EidolonIntegrationState.ABSENT_PROVIDER;
        }
        if (!EidolonVersionContract.supports(providerVersion)) {
            return EidolonIntegrationState.UNSUPPORTED_VERSION;
        }
        return EidolonIntegrationState.ACTIVE;
    }
}
