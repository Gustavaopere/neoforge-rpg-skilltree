package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/** Fail-closed cleanup for transient synchronous heavy-impact correlation state. */
public final class EpicFightHeavyImpactReceiptLifecycleEvents {
    private EpicFightHeavyImpactReceiptLifecycleEvents() {}

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer) clear();
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer) clear();
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer) clear();
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer) clear();
    }

    /**
     * Damage correlation is synchronous and must never legitimately survive a server tick.
     * Clearing here prevents an exceptional/unbalanced provider path from becoming evidence for a later hit.
     */
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        clear();
    }

    private static void clear() {
        EpicFightHeavyImpactReceiptBridge.clearTransientState();
    }
}
