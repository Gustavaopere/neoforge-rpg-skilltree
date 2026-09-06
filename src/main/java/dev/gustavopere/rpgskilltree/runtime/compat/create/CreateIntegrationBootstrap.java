package dev.gustavopere.rpgskilltree.runtime.compat.create;

import java.util.Objects;

/** Provider-free decision boundary before the optional Create listener is registered. */
public final class CreateIntegrationBootstrap {
    private CreateIntegrationBootstrap() {}

    public static CreateIntegrationState evaluate(boolean createLoaded, String version) {
        if (!createLoaded) {
            return CreateIntegrationState.ABSENT_PROVIDER;
        }
        if (!CreateVersionContract.supportsVersion(version)) {
            return CreateIntegrationState.UNSUPPORTED_VERSION;
        }
        return CreateIntegrationState.ACTIVE;
    }

    public static CreateIntegrationState install(
        boolean createLoaded,
        String version,
        Runnable providerRegistrar
    ) {
        CreateIntegrationState state = evaluate(createLoaded, version);
        if (state != CreateIntegrationState.ACTIVE) {
            return state;
        }
        try {
            Objects.requireNonNull(providerRegistrar, "providerRegistrar").run();
            return CreateIntegrationState.ACTIVE;
        } catch (RuntimeException | LinkageError failure) {
            return CreateIntegrationState.FAILED_CLOSED;
        }
    }
}
