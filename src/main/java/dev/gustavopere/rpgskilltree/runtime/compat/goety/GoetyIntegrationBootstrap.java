package dev.gustavopere.rpgskilltree.runtime.compat.goety;

import java.util.Objects;

/** Provider-free decision boundary before the optional Goety listener is registered. */
public final class GoetyIntegrationBootstrap {
    private GoetyIntegrationBootstrap() {}

    public static GoetyIntegrationState evaluate(boolean goetyLoaded, String version) {
        if (!goetyLoaded) {
            return GoetyIntegrationState.ABSENT_PROVIDER;
        }
        if (!GoetyVersionContract.supportsVersion(version)) {
            return GoetyIntegrationState.UNSUPPORTED_VERSION;
        }
        return GoetyIntegrationState.ACTIVE;
    }

    public static GoetyIntegrationState install(
        boolean goetyLoaded,
        String version,
        Runnable providerRegistrar
    ) {
        GoetyIntegrationState state = evaluate(goetyLoaded, version);
        if (state != GoetyIntegrationState.ACTIVE) {
            return state;
        }
        try {
            Objects.requireNonNull(providerRegistrar, "providerRegistrar").run();
            return GoetyIntegrationState.ACTIVE;
        } catch (RuntimeException | LinkageError failure) {
            return GoetyIntegrationState.FAILED_CLOSED;
        }
    }
}
