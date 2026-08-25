package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.runtime.CombatPerkRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.FrozenCombatRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.FrozenSurvivalRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public final class PlayerProgressionEvents {
    private PlayerProgressionEvents() {}

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CombatPerkRuntimeState.clear(player);
            FrozenCombatRuntimeState.clearTransient(player);
            FrozenSurvivalRuntimeState.clearTransient(player);
            PlayerProgressionRuntime.reconcilePlayerState(player);
            FrozenCombatRuntimeState.revalidateStance(player);
            FrozenSurvivalRuntimeState.revalidate(player, PlayerProgressionRuntime.get(player));
            FrozenSurvivalRuntimeState.beginSession(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CombatPerkRuntimeState.clear(player);
            FrozenCombatRuntimeState.clearTransient(player);
            FrozenSurvivalRuntimeState.clearTransient(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CombatPerkRuntimeState.clear(player);
            FrozenCombatRuntimeState.clearTransient(player);
            FrozenSurvivalRuntimeState.clearTransient(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            CombatPerkRuntimeState.clear(player);
            FrozenCombatRuntimeState.clearTransient(player);
            FrozenSurvivalRuntimeState.clearTransient(player);
            PlayerProgressionRuntime.reconcilePlayerState(player);
            FrozenCombatRuntimeState.revalidateStance(player);
            FrozenSurvivalRuntimeState.revalidate(player, PlayerProgressionRuntime.get(player));
            FrozenSurvivalRuntimeState.beginSession(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            FrozenCombatRuntimeState.clearTransient(player);
            FrozenSurvivalRuntimeState.clearTransient(player);
            PlayerProgressionRuntime.reconcilePlayerState(player);
            FrozenCombatRuntimeState.revalidateStance(player);
            FrozenSurvivalRuntimeState.revalidate(player, PlayerProgressionRuntime.get(player));
        }
    }
}
