package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState;
import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState.FirstBloodReservation;
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
 * canonical direct action. PRE stores the exact arrow/target/root reservation in
 * A0041A0060ProjectileEvents; only that reservation may be committed or rolled back here.
 */
@EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID)
public final class A0073A0080ProjectileCommitEvents {
    private A0073A0080ProjectileCommitEvents() {}

    /** Roll back only the reservation created by this exact projectile hit. */
    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public static void onCanceledIncoming(LivingIncomingDamageEvent event) {
        if (!event.isCanceled()
            || !(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)) return;
        A0041A0060ProjectileEvents.PendingPerkHit pending = takePending(arrow, event.getEntity());
        if (pending != null) rollback(player, event.getEntity(), pending);
    }

    /** Positive post-mitigation damage is the sole commit authority for the exact projectile root. */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDamagePost(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)) return;

        A0041A0060ProjectileEvents.PendingPerkHit pending = takePending(arrow, event.getEntity());
        if (pending == null) return;
        if (!eligible(player) || event.getNewDamage() <= 0.0F) {
            rollback(player, event.getEntity(), pending);
            return;
        }

        A0061A0080CombatState state = A0061A0080RuntimeState.state();
        CombatPerkRanks ranks = A0061A0080RuntimeState.ranks(player);
        String actor = A0061A0080RuntimeState.actorId(player);
        String target = event.getEntity().getUUID().toString();
        String root = pending.rootActionId();
        long now = now(player);

        if (pending.executionReserved()) {
            if (ranks.rank("A0073") > 0) state.commitExecution(actor, target, root, now);
            else state.rollbackExecution(actor, target, root);
        } else if (pending.executionArmCandidate()) {
            if (ranks.rank("A0073") > 0) state.armExecutionConfirmed(actor, target, root, now);
            else state.rollbackExecution(actor, target, root);
        }

        if (pending.firstBloodTracked()) {
            FirstBloodReservation reservation = pending.firstBloodReservation();
            if (ranks.rank("A0074") <= 0) {
                state.rollbackFirstBlood(actor, target, root);
            } else if (reservation != FirstBloodReservation.NONE) {
                boolean committed = state.commitFirstBlood(actor, target, root, reservation, now);
                if (!committed) {
                    state.rollbackFirstBlood(actor, target, root);
                    state.recordConfirmedAttack(actor, target, now);
                }
            } else {
                state.rollbackFirstBlood(actor, target, root);
                state.recordConfirmedAttack(actor, target, now);
            }
        }

        if (pending.opportunityReserved()) {
            if (ranks.rank("A0080") > 0) state.commitOpportunity(actor, root, now);
            else state.rollbackOpportunity(actor, root);
        }
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

    private static A0041A0060ProjectileEvents.PendingPerkHit takePending(
        AbstractArrow arrow,
        LivingEntity target
    ) {
        return target == null
            ? null
            : A0041A0060ProjectileEvents.takePendingPerkHit(arrow, target.getUUID().toString());
    }

    private static void rollback(
        ServerPlayer player,
        LivingEntity target,
        A0041A0060ProjectileEvents.PendingPerkHit pending
    ) {
        if (player == null || target == null || pending == null) return;
        A0061A0080CombatState state = A0061A0080RuntimeState.state();
        String actor = A0061A0080RuntimeState.actorId(player);
        String targetId = target.getUUID().toString();
        String root = pending.rootActionId();

        if (pending.executionReserved() || pending.executionArmCandidate()) {
            state.rollbackExecution(actor, targetId, root);
        }
        if (pending.firstBloodTracked()) {
            state.rollbackFirstBlood(actor, targetId, root);
        }
        if (pending.opportunityReserved()) {
            state.rollbackOpportunity(actor, root);
        }
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
