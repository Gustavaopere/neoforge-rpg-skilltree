package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

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
}
