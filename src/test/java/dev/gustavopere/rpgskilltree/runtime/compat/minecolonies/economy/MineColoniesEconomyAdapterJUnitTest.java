package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Plain-JUnit coverage for the classloading-safe adapter surface only.
 *
 * <p>Provider graph behavior (identity fingerprint, citizens, buildings and permissions) belongs
 * exclusively in the provider-present GameTest lane because MineColonies is intentionally absent
 * from the plain JUnit runtime classpath.</p>
 */
final class MineColoniesEconomyAdapterJUnitTest {
    @Test
    void nullProviderContextAlwaysFailsClosed() {
        assertTrue(MineColoniesEconomyAdapter.binding(null).isEmpty());
        assertTrue(MineColoniesEconomyAdapter.economicInputs(null).isEmpty());
        assertFalse(MineColoniesEconomyAdapter.mayManageEconomy(null, null));
    }
}
