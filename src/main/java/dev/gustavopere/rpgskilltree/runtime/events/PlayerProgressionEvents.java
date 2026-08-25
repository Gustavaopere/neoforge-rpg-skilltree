package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.CombatPerkLifecyclePolicy;
import dev.gustavopere.rpgskilltree.runtime.CombatPerkRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public final class PlayerProgressionEvents {
    private PlayerProgressionEvents() {}

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            applyLifecycle(player, CombatPerkLifecyclePolicy.Boundary.LOGIN);
            PlayerProgressionRuntime.reconcilePlayerState(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            applyLifecycle(player, CombatPerkLifecyclePolicy.Boundary.LOGOUT);
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            applyLifecycle(player, CombatPerkLifecyclePolicy.Boundary.DEATH);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            applyLifecycle(player, CombatPerkLifecyclePolicy.Boundary.PLAYER_RECREATION);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            applyLifecycle(player, CombatPerkLifecyclePolicy.Boundary.RESPAWN);
            PlayerProgressionRuntime.reconcilePlayerState(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            applyLifecycle(player, CombatPerkLifecyclePolicy.Boundary.DIMENSION_CHANGE);
            PlayerProgressionRuntime.reconcilePlayerState(player);
        }
    }

    private static void applyLifecycle(ServerPlayer player, CombatPerkLifecyclePolicy.Boundary boundary) {
        switch (CombatPerkLifecyclePolicy.cleanupMode(boundary)) {
            case TRANSIENT_PRESERVE_GUARDS -> CombatPerkRuntimeState.clearTransientPreservingGuards(player);
            case FULL_SESSION -> CombatPerkRuntimeState.clear(player);
        }
    }
}
