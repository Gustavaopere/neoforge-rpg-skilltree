package dev.gustavopere.volcanoes.pressure;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

/** Process-wide pressure registry snapshot used by world-facing pressure consumers. */
public final class AtmosphericPressureRuntime {
    private static final AtmosphericPressureReloadState STATE =
            new AtmosphericPressureReloadState(AtmosphericPressureRegistry.empty());

    private AtmosphericPressureRuntime() {
    }

    public static AtmosphericPressureRegistry current() {
        return STATE.current();
    }

    public static double pressureAtm(String dimensionId, double altitudeY) {
        return current().pressureAtm(dimensionId, altitudeY);
    }

    static void reload(Map<ResourceLocation, JsonElement> definitions) {
        STATE.reload(definitions);
    }
}
