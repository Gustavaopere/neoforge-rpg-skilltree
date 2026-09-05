package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.minecolonies.api.colony.ICitizenData;
import com.minecolonies.api.colony.IColony;
import com.minecolonies.api.colony.buildings.IBuilding;
import com.minecolonies.api.colony.buildings.workerbuildings.IWareHouse;
import com.minecolonies.api.colony.jobs.IJob;
import com.minecolonies.api.colony.managers.interfaces.ICitizenManager;
import com.minecolonies.api.colony.managers.interfaces.IRegisteredStructureManager;
import com.minecolonies.api.colony.permissions.Action;
import com.minecolonies.api.colony.permissions.IPermissions;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomicInputs;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.Test;

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
    void economicInputsCountOnlyAdultEmployedCitizensAndBuiltLevels() {
        IColony colony = mock(IColony.class);
        ICitizenManager citizens = mock(ICitizenManager.class);
        IRegisteredStructureManager buildings = mock(IRegisteredStructureManager.class);

        ICitizenData adultWorker = citizen(false, true);
        ICitizenData adultJobless = citizen(false, false);
        ICitizenData childWithJob = citizen(true, true);
        when(citizens.getCitizens()).thenReturn(List.of(adultWorker, adultJobless, childWithJob));

        IBuilding levelThree = building(3);
        IBuilding levelFive = building(5);
        IBuilding unbuilt = building(0);
        when(buildings.getBuildings()).thenReturn(Map.of(
            new BlockPos(1, 1, 1), levelThree,
            new BlockPos(2, 2, 2), levelFive,
            new BlockPos(3, 3, 3), unbuilt
        ));
        when(buildings.getWareHouses()).thenReturn(List.of(mock(IWareHouse.class), mock(IWareHouse.class)));

        when(colony.getCitizenManager()).thenReturn(citizens);
        when(colony.getServerBuildingManager()).thenReturn(buildings);

        ColonyEconomicInputs inputs = MineColoniesEconomyAdapter.economicInputs(colony).orElseThrow();

        assertEquals(new ColonyEconomicInputs(1, 8, 2), inputs);
    }

    @Test
    void managementAuthorizationDelegatesToNativeManageHutsPermission() {
        IColony colony = mock(IColony.class);
        IPermissions permissions = mock(IPermissions.class);
        ServerPlayer player = mock(ServerPlayer.class);
        when(colony.getPermissions()).thenReturn(permissions);
        when(permissions.hasPermission(player, Action.MANAGE_HUTS)).thenReturn(true);

        assertTrue(MineColoniesEconomyAdapter.mayManageEconomy(player, colony));

        when(permissions.hasPermission(player, Action.MANAGE_HUTS)).thenReturn(false);
        assertFalse(MineColoniesEconomyAdapter.mayManageEconomy(player, colony));
    }

    @Test
    void malformedProviderDataFailsClosed() {
        IColony colony = mock(IColony.class);
        when(colony.getDimension()).thenThrow(new IllegalStateException("provider unavailable"));

        assertTrue(MineColoniesEconomyAdapter.binding(colony).isEmpty());
        assertTrue(MineColoniesEconomyAdapter.economicInputs(null).isEmpty());
        assertFalse(MineColoniesEconomyAdapter.mayManageEconomy(null, colony));
    }

    private static ICitizenData citizen(boolean child, boolean employed) {
        ICitizenData citizen = mock(ICitizenData.class);
        when(citizen.isChild()).thenReturn(child);
        when(citizen.getJob()).thenReturn(employed ? mock(IJob.class) : null);
        return citizen;
    }

    private static IBuilding building(int level) {
        IBuilding building = mock(IBuilding.class);
        when(building.getBuildingLevel()).thenReturn(level);
        return building;
    }
}
