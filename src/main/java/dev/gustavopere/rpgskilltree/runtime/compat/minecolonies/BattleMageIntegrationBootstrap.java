package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import java.util.Objects;

/** Pure provider/version decision boundary for the MineColonies battle-mage integration. */
public final class BattleMageIntegrationBootstrap {
    private BattleMageIntegrationBootstrap() {}

    public static BattleMageIntegrationState evaluate(
        boolean mineColoniesLoaded,
        boolean ironsSpellbooksLoaded,
        String mineColoniesVersion
    ) {
        if (!mineColoniesLoaded || !ironsSpellbooksLoaded) {
            return BattleMageIntegrationState.ABSENT_PROVIDER;
        }
        if (!MineColoniesVersionContract.supports(mineColoniesVersion)) {
            return BattleMageIntegrationState.UNSUPPORTED_VERSION;
        }
        return BattleMageIntegrationState.ACTIVE;
    }

    /**
     * Executes a provider-local registrar only after the pure compatibility gate accepts the
     * installed provider pair. Provider linkage/registration failures are contained and reported as
     * {@link BattleMageIntegrationState#FAILED_CLOSED}.
     */
    public static BattleMageIntegrationState install(
        boolean mineColoniesLoaded,
        boolean ironsSpellbooksLoaded,
        String mineColoniesVersion,
        Runnable providerRegistrar
    ) {
        BattleMageIntegrationState state = evaluate(
            mineColoniesLoaded,
            ironsSpellbooksLoaded,
            mineColoniesVersion
        );
        if (state != BattleMageIntegrationState.ACTIVE) {
            return state;
        }

        try {
            Objects.requireNonNull(providerRegistrar, "providerRegistrar").run();
            return BattleMageIntegrationState.ACTIVE;
        } catch (RuntimeException | LinkageError failure) {
            return BattleMageIntegrationState.FAILED_CLOSED;
        }
    }
}
