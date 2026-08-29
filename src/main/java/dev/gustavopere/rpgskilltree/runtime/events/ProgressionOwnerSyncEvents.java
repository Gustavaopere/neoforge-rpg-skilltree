package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.runtime.ProgressionOwnerSyncRuntime;
import dev.gustavopere.rpgskilltree.runtime.RelevantPlayerCandidateRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/** NeoForge lifecycle boundary for the per-tick owner progression sync queue. */
public final class ProgressionOwnerSyncEvents {
    private ProgressionOwnerSyncEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onServerTickPost(ServerTickEvent.Post event) {
        ProgressionOwnerSyncRuntime.flush(event.getServer());
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ProgressionOwnerSyncRuntime.clear(player.getUUID());
            RelevantPlayerCandidateRuntime.invalidateAll();
        }
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        ProgressionOwnerSyncRuntime.clearAll();
        RelevantPlayerCandidateRuntime.invalidateAll();
    }
}
