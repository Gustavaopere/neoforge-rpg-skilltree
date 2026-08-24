package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/** Clears causal receipt state at every player lifecycle boundary that can invalidate an action. */
public final class EpicFightExactStaminaReceiptLifecycleEvents {
    private EpicFightExactStaminaReceiptLifecycleEvents() {}

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            EpicFightExactStaminaReceiptBridge.clear(player);
        }
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            EpicFightExactStaminaReceiptBridge.clear(player);
        }
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            EpicFightExactStaminaReceiptBridge.clear(player);
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            EpicFightExactStaminaReceiptBridge.clear(player);
        }
    }
}
