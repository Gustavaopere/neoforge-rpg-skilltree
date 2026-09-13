package dev.gustavopere.rpgskilltree.runtime.compat;

import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.MineColoniesVersionContract;
import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomyVersionContract;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class OptionalIntegrationMineColoniesVersionGateJUnitTest {
    @Test
    void current1376RemainsAvailableThroughEconomyRouteEvenWhenBattleMageRouteIsClosed() {
        String version = MineColoniesEconomyVersionContract.CURRENT_SUPPORTED_ARTIFACT_VERSION;

        assertFalse(MineColoniesVersionContract.supports(version));
        assertTrue(MineColoniesEconomyVersionContract.supports(version));
        assertTrue(anyMineColoniesRouteSupports(version));
    }

    @Test
    void audited1375RemainsAvailableBecauseBothHistoricalRoutesRecognizeIt() {
        String version = MineColoniesVersionContract.SUPPORTED_ARTIFACT_VERSION;

        assertTrue(MineColoniesVersionContract.supports(version));
        assertTrue(MineColoniesEconomyVersionContract.supports(version));
        assertTrue(anyMineColoniesRouteSupports(version));
    }

    @Test
    void unknownMineColoniesVersionHasNoAdvertisedCommonRoute() {
        String version = "1.1.9999-1.21.1-snapshot";

        assertFalse(MineColoniesVersionContract.supports(version));
        assertFalse(MineColoniesEconomyVersionContract.supports(version));
        assertFalse(anyMineColoniesRouteSupports(version));
    }

    private static boolean anyMineColoniesRouteSupports(String version) {
        return MineColoniesVersionContract.supports(version)
            || MineColoniesEconomyVersionContract.supports(version);
    }
}
