package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.CombatPerkLifecyclePolicy;
import dev.gustavopere.rpgskilltree.core.CombatPerkTransitionPolicy;
import dev.gustavopere.rpgskilltree.runtime.CombatPerkRuntimeState;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public final class PlayerProgressionEvents {
    private static final double RELEVANT_HORIZONTAL_MOTION_SQUARED = 1.0E-6D;

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

    /** A0022 may never treat provider-confirmed knockback as voluntary flank/rear repositioning. */
    @SubscribeEvent
    public static void onPlayerKnockback(LivingKnockBackEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && !event.isCanceled()) {
            quarantineFlowReposition(player);
        }
    }

    /** Every NeoForge teleport subtype is forced movement for A0022 positional fallback purposes. */
    @SubscribeEvent
    public static void onPlayerTeleport(EntityTeleportEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && !event.isCanceled()) {
            quarantineFlowReposition(player);
        }
    }

    /**
     * ServerPlayer#getKnownMovement is the server's last known client movement. If the server observes
     * horizontal motion without corresponding client-known horizontal motion, or while the player is a
     * passenger, the displacement is not accepted as voluntary A0022 movement. Explicit knockback and
     * teleport callbacks above remain authoritative and quarantine even if client motion also exists.
     */
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (CombatPerkRuntimeState.ranks(player).rank("A0022") <= 0) return;

        Vec3 actual = player.getDeltaMovement();
        if (!hasRelevantHorizontalMotion(actual)) return;

        Vec3 knownClientMovement = player.getKnownMovement();
        if (player.isPassenger() || !hasRelevantHorizontalMotion(knownClientMovement)) {
            quarantineFlowReposition(player);
        }
    }

    private static boolean hasRelevantHorizontalMotion(Vec3 movement) {
        return movement.x * movement.x + movement.z * movement.z > RELEVANT_HORIZONTAL_MOTION_SQUARED;
    }

    private static void quarantineFlowReposition(ServerPlayer player) {
        if (CombatPerkRuntimeState.ranks(player).rank("A0022") <= 0) return;
        long nowMillis = Math.multiplyExact(player.level().getGameTime(), 50L);
        CombatPerkRuntimeState.state().blockFlowReposition(
            CombatPerkRuntimeState.actorId(player),
            nowMillis,
            CombatPerkTransitionPolicy.FLOW_WINDOW_MILLIS
        );
    }

    private static void applyLifecycle(ServerPlayer player, CombatPerkLifecyclePolicy.Boundary boundary) {
        switch (CombatPerkLifecyclePolicy.cleanupMode(boundary)) {
            case TRANSIENT_PRESERVE_GUARDS -> CombatPerkRuntimeState.clearTransientPreservingGuards(player);
            case FULL_SESSION -> CombatPerkRuntimeState.clear(player);
        }
    }
}
