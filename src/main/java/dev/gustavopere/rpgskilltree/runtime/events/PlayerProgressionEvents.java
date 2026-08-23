package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.network.ModNetworking;
import dev.gustavopere.rpgskilltree.runtime.effects.AttributeNodeEffectRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public final class PlayerProgressionEvents {
    private PlayerProgressionEvents() {}

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            var state = PlayerProgressionRuntime.get(player);
            AttributeNodeEffectRuntime.refresh(player, state);
            ModNetworking.syncToOwner(player, state);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            var state = PlayerProgressionRuntime.get(player);
            AttributeNodeEffectRuntime.refresh(player, state);
            ModNetworking.syncToOwner(player, state);
        }
    }
}
