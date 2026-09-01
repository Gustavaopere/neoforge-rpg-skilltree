package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.runtime.A0061A0080RuntimeState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/**
 * POST commit boundary for A0073/A0074/A0080 when a vanilla physical projectile is the
 * canonical direct action. PRE reservation is produced by A0041A0060ProjectileEvents through
 * A0061A0080CombatPolicy; only a positive post-mitigation hit may make that state irreversible.
 */
@EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID)
public final class A0073A0080ProjectileCommitEvents {
    private A0073A0080ProjectileCommitEvents() {}

    /** Roll back reservations when a later listener cancels the incoming projectile damage. */
    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public static void onCanceledIncoming(LivingIncomingDamageEvent event) {
        if (!event.isCanceled()
            || !(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)) return;
        rollback(player, event.getEntity());
    }

    /** Positive post-mitigation damage is the sole commit authority for the projectile consumer. */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDamagePost(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)) return;

        if (!eligible(player) || event.getNewDamage() <= 0.0F) {
            rollback(player, event.getEntity());
            return;
        }

        A0061A0080CombatState state = A0061A0080RuntimeState.state();
        CombatPerkRanks ranks = A0061A0080RuntimeState.ranks(player);
        String actor = A0061A0080RuntimeState.actorId(player);
        String target = event.getEntity().getUUID().toString();
        long now = now(player);

        state.commitPendingExecution(actor, target, ranks.rank("A0073") > 0, now);
        state.commitPendingFirstBlood(actor, target, ranks.rank("A0074") > 0, now);
        state.commitPendingOpportunity(actor, ranks.rank("A0080") > 0, now);
    }

    /** Target-scoped windows/history cannot survive death. */
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        A0061A0080RuntimeState.state().clearTarget(event.getEntity().getUUID().toString());
        if (event.getEntity() instanceof ServerPlayer player) A0061A0080RuntimeState.clear(player);
    }

    /** Target-scoped windows/history cannot survive entity removal/unload. */
    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity() instanceof LivingEntity living) {
            A0061A0080RuntimeState.state().clearTarget(living.getUUID().toString());
        }
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) A0061A0080RuntimeState.clear(player);
    }

    @SubscribeEvent
    public static void onDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) A0061A0080RuntimeState.clear(player);
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) A0061A0080RuntimeState.clear(player);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        A0061A0080RuntimeState.clearAll();
    }

    private static void rollback(ServerPlayer player, LivingEntity target) {
        if (player == null || target == null) return;
        A0061A0080RuntimeState.state().rollbackPendingPhysicalHit(
            A0061A0080RuntimeState.actorId(player),
            target.getUUID().toString()
        );
    }

    private static boolean eligible(ServerPlayer player) {
        return player != null
            && !player.level().isClientSide()
            && !player.isCreative()
            && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }

    private static long now(ServerPlayer player) {
        return Math.multiplyExact(player.level().getGameTime(), 50L);
    }
}
