package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.VolcanicGasAuthority;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

public final class VolcanicGasAtmosphereRuntime {
    private static final VolcanicGasAtmosphereBridge BRIDGE = new VolcanicGasAtmosphereBridge();
    private static boolean registered;

    private VolcanicGasAtmosphereRuntime() {
    }

    public static synchronized void register() {
        if (!registered) {
            registered = VolcanicGasAuthority.registerLifecycleSink(BRIDGE);
        }
    }

    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level) || !Level.OVERWORLD.equals(level.dimension())) {
            return;
        }
        VolcanicGasAuthority.hydrate(level);
        if (AtmosphereRuntime.shouldProcessDiffusion(level.getGameTime())) {
            BRIDGE.flush(level, AtmosphereRuntime.MAX_SOURCE_UPDATES_PER_INTERVAL);
        }
    }

    public static void onLevelUnload(LevelEvent.Unload event) {
        if (!(event.getLevel() instanceof ServerLevel level) || !Level.OVERWORLD.equals(level.dimension())) {
            return;
        }
        BRIDGE.forgetLevel(level);
        VolcanicGasAuthority.forget(level);
    }
}
