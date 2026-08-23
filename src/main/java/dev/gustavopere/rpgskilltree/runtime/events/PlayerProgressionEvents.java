package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public final class PlayerProgressionEvents {
    private PlayerProgressionEvents() {}

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerProgressionRuntime.reconcilePlayerState(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerProgressionRuntime.reconcilePlayerState(player);
        }
    }
}
