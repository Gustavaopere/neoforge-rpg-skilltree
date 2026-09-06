package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import com.minecolonies.api.colony.ICitizenData;
import com.minecolonies.api.colony.IColony;
import com.minecolonies.api.colony.buildings.IBuilding;
import com.minecolonies.api.colony.buildings.workerbuildings.IWareHouse;
import com.minecolonies.api.colony.permissions.Action;
import dev.gustavopere.rpgskilltree.core.economy.ColonyEconomicInputs;
import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;

/** Read-only boundary over the audited public MineColonies 1.1.1375 API. */
public final class MineColoniesEconomyAdapter {
    private MineColoniesEconomyAdapter() {}

    public static Optional<NativeColonyBinding> binding(IColony colony) {
        if (colony == null) {
            return Optional.empty();
        }
        try {
            var townHall = colony.getServerBuildingManager().getTownHall();
            if (townHall == null) {
                return Optional.empty();
            }
            return Optional.of(new NativeColonyBinding(
                colony.getDimension().location(),
                colony.getID(),
                colony.getPermissions().getOwner(),
                townHall.getPosition()
            ));
        } catch (RuntimeException | LinkageError failure) {
            return Optional.empty();
        }
    }

    public static Optional<ColonyEconomicInputs> economicInputs(IColony colony) {
        if (colony == null) {
            return Optional.empty();
        }
        try {
            int adultWorkers = 0;
            for (ICitizenData citizen : colony.getCitizenManager().getCitizens()) {
                if (citizen != null
                    && !citizen.isChild()
                    && citizen.getJob() != null
                    && citizen.getWorkBuilding() != null) {
                    adultWorkers = Math.incrementExact(adultWorkers);
                }
            }

            int builtLevelPoints = 0;
            for (IBuilding building : colony.getServerBuildingManager().getBuildings().values()) {
                if (building == null || !building.isBuilt()) {
                    continue;
                }
                int level = building.getBuildingLevel();
                if (level < 0) {
                    return Optional.empty();
                }
                int boundedLevel = Math.max(1, Math.min(5, level));
                builtLevelPoints = Math.addExact(builtLevelPoints, boundedLevel);
            }

            int warehouseCount = 0;
            for (IWareHouse warehouse : colony.getServerBuildingManager().getWareHouses()) {
                if (warehouse != null && warehouse.isBuilt()) {
                    warehouseCount = Math.incrementExact(warehouseCount);
                }
            }
            return Optional.of(new ColonyEconomicInputs(adultWorkers, builtLevelPoints, warehouseCount));
        } catch (RuntimeException | LinkageError failure) {
            return Optional.empty();
        }
    }

    public static boolean mayManageEconomy(ServerPlayer player, IColony colony) {
        if (player == null || colony == null) {
            return false;
        }
        try {
            return colony.getPermissions().hasPermission(player, Action.MANAGE_HUTS);
        } catch (RuntimeException | LinkageError failure) {
            return false;
        }
    }
}
