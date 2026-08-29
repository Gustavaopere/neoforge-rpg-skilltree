package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.runtime.CorePlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.RelevantPlayerCandidateRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public final class PlayerProgressionEvents {
    private PlayerProgressionEvents() {}

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerProgressionRuntime.reconcilePlayerState(player);
            CorePlayerProgressionRuntime.syncToOwnerIfInitialized(player);
            RelevantPlayerCandidateRuntime.invalidateAll();
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerProgressionRuntime.reconcilePlayerState(player);
            CorePlayerProgressionRuntime.syncToOwnerIfInitialized(player);
            RelevantPlayerCandidateRuntime.invalidateAll();
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerProgressionRuntime.syncToOwner(player);
            CorePlayerProgressionRuntime.syncToOwnerIfInitialized(player);
            RelevantPlayerCandidateRuntime.invalidateAll();
        }
    }
}
