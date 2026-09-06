package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import java.util.Objects;

/** Provider-free decision boundary before any MineColonies economy class is installed. */
public final class MineColoniesEconomyIntegrationBootstrap {
    private MineColoniesEconomyIntegrationBootstrap() {}

    public static MineColoniesEconomyIntegrationState evaluate(boolean mineColoniesLoaded, String version) {
        if (!mineColoniesLoaded) {
            return MineColoniesEconomyIntegrationState.ABSENT_PROVIDER;
        }
        if (!MineColoniesEconomyVersionContract.supports(version)) {
            return MineColoniesEconomyIntegrationState.UNSUPPORTED_VERSION;
        }
        return MineColoniesEconomyIntegrationState.ACTIVE;
    }

    public static MineColoniesEconomyIntegrationState install(
        boolean mineColoniesLoaded,
        String version,
        Runnable providerRegistrar
    ) {
        MineColoniesEconomyIntegrationState state = evaluate(mineColoniesLoaded, version);
        if (state != MineColoniesEconomyIntegrationState.ACTIVE) {
            return state;
        }
        try {
            Objects.requireNonNull(providerRegistrar, "providerRegistrar").run();
            return MineColoniesEconomyIntegrationState.ACTIVE;
        } catch (RuntimeException | LinkageError failure) {
            return MineColoniesEconomyIntegrationState.FAILED_CLOSED;
        }
    }
}
