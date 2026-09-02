package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.A0061A0080RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.compat.A0079ForcedMovementCompat;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/**
 * Canonical forced-movement invalidation shared by A0079 and A0099, and explicit receipts used
 * by A0098. This class never samples position and therefore does not create a second stationary
 * detector; it only invalidates the shared StationaryStateService when authority proves movement
 * is forced/passive or cannot be safely classified.
 */
@EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID)
public final class A0079A0099ForcedMovementEvents {
    private A0079A0099ForcedMovementEvents() {}

    @SubscribeEvent(priority = EventPriority.MONITOR)
    public static void onServerTick(ServerTickEvent.Post event) {
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            if (A0079ForcedMovementCompat.forcedOrUnclassified(player)) {
                A0061A0080RuntimeState.stationary().invalidate(A0061A0080RuntimeState.actorId(player));
            }
        }
    }

    @SubscribeEvent
    public static void onTeleport(EntityTeleportEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) markForced(player);
    }

    @SubscribeEvent
    public static void onKnockback(LivingKnockBackEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) markForced(player);
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clear(player);
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clear(player);
    }

    @SubscribeEvent
    public static void onDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clear(player);
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clear(player);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        A0079ForcedMovementCompat.clearAll();
    }

    private static void markForced(ServerPlayer player) {
        A0079ForcedMovementCompat.markForcedTransition(player);
        A0061A0080RuntimeState.stationary().invalidate(A0061A0080RuntimeState.actorId(player));
    }

    private static void clear(ServerPlayer player) {
        A0079ForcedMovementCompat.clearPlayer(player);
    }
}
