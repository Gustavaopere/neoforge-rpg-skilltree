package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import java.util.Objects;

/** Pure provider/version decision boundary for the MineColonies battle-mage integration. */
public final class BattleMageIntegrationBootstrap {
    private BattleMageIntegrationBootstrap() {}

    public static BattleMageIntegrationState evaluate(
        boolean mineColoniesLoaded,
        boolean ironsSpellbooksLoaded,
        String mineColoniesVersion,
        String ironsSpellbooksVersion
    ) {
        if (!mineColoniesLoaded || !ironsSpellbooksLoaded) {
            return BattleMageIntegrationState.ABSENT_PROVIDER;
        }
        if (!MineColoniesVersionContract.supports(mineColoniesVersion)
            || !IronsBattleMageVersionContract.supports(ironsSpellbooksVersion)) {
            return BattleMageIntegrationState.UNSUPPORTED_VERSION;
        }
        return BattleMageIntegrationState.ACTIVE;
    }

    /** Executes the provider-local registrar only after both exact compatibility gates pass. */
    public static BattleMageIntegrationState install(
        boolean mineColoniesLoaded,
        boolean ironsSpellbooksLoaded,
        String mineColoniesVersion,
        String ironsSpellbooksVersion,
        Runnable providerRegistrar
    ) {
        BattleMageIntegrationState state = evaluate(
            mineColoniesLoaded,
            ironsSpellbooksLoaded,
            mineColoniesVersion,
            ironsSpellbooksVersion
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
