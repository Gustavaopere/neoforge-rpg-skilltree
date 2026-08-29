package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.runtime.RelevantPlayerCandidateRuntime;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/** Lifecycle invalidation boundary for cached relevant-player presence snapshots. */
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
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        RelevantPlayerCandidateRuntime.invalidateAll();
    }
}
