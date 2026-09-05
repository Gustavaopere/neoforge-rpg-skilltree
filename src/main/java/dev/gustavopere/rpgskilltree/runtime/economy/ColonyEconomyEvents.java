package dev.gustavopere.rpgskilltree.runtime.economy;

import dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy.MineColoniesEconomySettlementBridge;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/** Server tick bridge for bounded discrete economic settlement. Registered only behind provider gate. */
public final class ColonyEconomyEvents {
    public static final long DEFAULT_SETTLEMENT_INTERVAL_TICKS = 1_200L;

    private static final ColonyEconomyRuntime RUNTIME = new ColonyEconomyRuntime(DEFAULT_SETTLEMENT_INTERVAL_TICKS);
    private static final MineColoniesEconomySettlementBridge BRIDGE = new MineColoniesEconomySettlementBridge();

    private ColonyEconomyEvents() {}

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return;
        }
        long gameTime = server.overworld().getGameTime();
        RUNTIME.tryRun(true, gameTime, () -> BRIDGE.settleNextBatch(server, gameTime));
    }
}
