package dev.gustavopere.rpgskilltree.runtime.compat.malum;

import java.util.Objects;

public final class MalumIntegrationBootstrap {
    private MalumIntegrationBootstrap() {
    }

    public static MalumIntegrationState install(boolean providerLoaded, String providerVersion, Runnable registrar) {
        Objects.requireNonNull(registrar, "registrar");

        MalumIntegrationState state = evaluate(providerLoaded, providerVersion);
        if (state != MalumIntegrationState.ACTIVE) {
            return state;
        }

        try {
            registrar.run();
            return MalumIntegrationState.ACTIVE;
        } catch (RuntimeException | LinkageError failure) {
            return MalumIntegrationState.FAILED_CLOSED;
        }
    }

    static MalumIntegrationState evaluate(boolean providerLoaded, String providerVersion) {
        if (!providerLoaded) {
            return MalumIntegrationState.ABSENT_PROVIDER;
        }
        if (!MalumVersionContract.supports(providerVersion)) {
            return MalumIntegrationState.UNSUPPORTED_VERSION;
        }
        return MalumIntegrationState.ACTIVE;
    }
}
