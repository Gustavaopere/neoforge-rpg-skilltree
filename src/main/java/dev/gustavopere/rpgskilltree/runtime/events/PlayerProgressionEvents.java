package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.runtime.CombatPerkRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public final class PlayerProgressionEvents {
    private PlayerProgressionEvents() {}

    /** Login starts a fresh session; any stale in-memory guards from a prior disconnected session are discarded. */
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CombatPerkRuntimeState.clear(player);
            PlayerProgressionRuntime.reconcilePlayerState(player);
        }
    }

    /** Logout is the only normal lifecycle boundary that performs a full teardown. */
    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CombatPerkRuntimeState.clear(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CombatPerkRuntimeState.clearTransientPreservingGuards(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CombatPerkRuntimeState.clearTransientPreservingGuards(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CombatPerkRuntimeState.clearTransientPreservingGuards(player);
            PlayerProgressionRuntime.reconcilePlayerState(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CombatPerkRuntimeState.clearTransientPreservingGuards(player);
            PlayerProgressionRuntime.reconcilePlayerState(player);
        }
    }
}
