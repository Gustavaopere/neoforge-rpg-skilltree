package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.RelevantPlayerCandidateRuntime;
import dev.gustavopere.rpgskilltree.runtime.effects.NodeEffectRuntime;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/** Lifecycle invalidation boundary for bounded server-side player caches. */
public final class RelevantPlayerCacheEvents {
    private RelevantPlayerCacheEvents() {}

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        RelevantPlayerCandidateRuntime.invalidateAll();
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        RelevantPlayerCandidateRuntime.invalidateAll();
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        RelevantPlayerCandidateRuntime.invalidateAll();
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        RelevantPlayerCandidateRuntime.invalidateAll();
        PlayerProgressionRuntime.clearNodePurchaseRequests(event.getEntity().getUUID());
        NodeEffectRuntime.clearPlayer(event.getEntity().getUUID());
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        RelevantPlayerCandidateRuntime.invalidateAll();
        PlayerProgressionRuntime.clearAllNodePurchaseRequests();
        NodeEffectRuntime.clearAll();
    }
}
