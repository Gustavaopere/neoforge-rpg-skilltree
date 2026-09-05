package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.minecolonies.api.colony.IColony;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.Test;

/**
 * Plain-JUnit coverage for the classloading-safe adapter surface only.
 *
 * <p>Provider graph behavior (citizens, buildings and permissions) belongs in the provider-present
 * GameTest lane because MineColonies intentionally is not on the plain JUnit runtime classpath.</p>
 */
final class MineColoniesEconomyAdapterJUnitTest {
    @Test
    void exposesNativeBindingWithoutUsingItAsMonetaryIdentity() {
        IColony colony = mock(IColony.class);
        when(colony.getID()).thenReturn(42);
        when(colony.getDimension()).thenReturn(Level.OVERWORLD);

        NativeColonyBinding binding = MineColoniesEconomyAdapter.binding(colony).orElseThrow();

        assertEquals(42, binding.colonyId());
        assertEquals(Level.OVERWORLD.location(), binding.dimensionId());
    }

    @Test
    void malformedProviderDataFailsClosed() {
        IColony colony = mock(IColony.class);
        when(colony.getDimension()).thenThrow(new IllegalStateException("provider unavailable"));

        assertTrue(MineColoniesEconomyAdapter.binding(colony).isEmpty());
        assertTrue(MineColoniesEconomyAdapter.economicInputs(null).isEmpty());
        assertFalse(MineColoniesEconomyAdapter.mayManageEconomy(null, colony));
    }
}
