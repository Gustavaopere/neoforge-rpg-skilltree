package dev.gustavopere.rpgskilltree.runtime.volcanoes;

import dev.gustavopere.volcanoes.environment.AtmosphereRuntime;
import dev.gustavopere.volcanoes.environment.AtmosphereState;
import dev.gustavopere.volcanoes.geology.DepositRegistry;
import dev.gustavopere.volcanoes.geology.GeologicalDepositSource;
import dev.gustavopere.volcanoes.pressure.AtmosphericPressureRuntime;
import dev.gustavopere.volcanoes.tectonics.TectonicRuntime;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import dev.gustavopere.volcanoes.volcano.VolcanicRegionService;
import dev.gustavopere.volcanoes.volcano.VolcanoSavedData;
import net.minecraft.server.level.ServerLevel;

import java.util.Objects;

/**
 * Native read-only bridge from RPG systems/perks to the consolidated Volcanoes subsystem.
 *
 * <p>Volcanoes is part of the same artifact and lifecycle as RPG Skill Tree. This facade therefore
 * performs no optional-mod discovery and owns no duplicate state. Persistent simulation remains in
 * the original Volcanoes services; RPG consumers receive their authoritative read surfaces.</p>
 */
public final class NativeVolcanoesServices {
    private NativeVolcanoesServices() {
    }

    public static GeologicalDepositSource geologicalDeposits(ServerLevel level) {
        return DepositRegistry.get(requireLevel(level));
    }

    public static VolcanicRegionService volcanicRegions(ServerLevel level) {
        return VolcanoSavedData.get(requireLevel(level));
    }

    public static TectonicService tectonics(ServerLevel level) {
        return TectonicRuntime.serviceForLevel(requireLevel(level));
    }

    public static AtmosphereState atmosphereAt(
            ServerLevel level,
            double x,
            double y,
            double z
    ) {
        return AtmosphereRuntime.sample(requireLevel(level), x, y, z);
    }

    public static double atmosphericPressureAtm(ServerLevel level, double altitudeY) {
        ServerLevel checkedLevel = requireLevel(level);
        return AtmosphericPressureRuntime.pressureAtm(
                checkedLevel.dimension().location().toString(),
                altitudeY);
    }

    private static ServerLevel requireLevel(ServerLevel level) {
        return Objects.requireNonNull(level, "level");
    }
}
