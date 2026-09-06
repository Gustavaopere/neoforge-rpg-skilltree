package dev.gustavopere.rpgskilltree.runtime.economy;

import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomySettlementBridge;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/** Server tick bridge for bounded discrete economic settlement. Registered only behind provider gate. */
public final class ColonyEconomyEvents {
    private static ColonyEconomyRuntime runtime;
    private static final MineColoniesEconomySettlementBridge BRIDGE = new MineColoniesEconomySettlementBridge();

    private ColonyEconomyEvents() {}

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        ColonyEconomyConfigSnapshot config = ColonyEconomyServerConfig.snapshot();
        runtime = new ColonyEconomyRuntime(config.settlementIntervalTicks());
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        runtime = null;
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        ColonyEconomyRuntime activeRuntime = runtime;
        if (server == null || activeRuntime == null) {
            return;
        }

        ColonyEconomyConfigSnapshot config = ColonyEconomyServerConfig.snapshot();
        if (!config.enabled()) {
            return;
        }

        long gameTime = server.overworld().getGameTime();
        activeRuntime.tryRun(true, gameTime, () -> BRIDGE.settleNextBatch(server, gameTime));
    }
}
