package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState;
import dev.gustavopere.rpgskilltree.core.A0061A0080CombatState.FirstBloodReservation;
import dev.gustavopere.rpgskilltree.core.A0061A0080ReservationReceiptContext;
import dev.gustavopere.rpgskilltree.core.A0061A0080ReservationReceiptContext.PendingHitReceipt;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.runtime.A0061A0080RuntimeState;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
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
 * Exact projectile PRE/POST correlation for A0073/A0074/A0080.
 *
 * <p>The A0041-A0060 bridge remains the owner of the canonical projectile root and performs the
 * high-priority damage calculation. The pure combat policy publishes a per-thread reservation
 * receipt containing that exact root. At LOWEST this subscriber binds the receipt to arrow+target;
 * only positive post-mitigation damage may commit it.</p>
 */
@EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID)
public final class A0073A0080ProjectileCommitEvents {
    private static final WeakHashMap<AbstractArrow, Map<String, PendingHitReceipt>> PENDING = new WeakHashMap<>();

    private A0073A0080ProjectileCommitEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public static void onIncomingFinal(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)
            || !(event.getEntity() instanceof LivingEntity target)) {
            A0061A0080ReservationReceiptContext.clear();
            return;
        }

        String actor = A0061A0080RuntimeState.actorId(player);
        String targetId = target.getUUID().toString();
        PendingHitReceipt receipt = A0061A0080ReservationReceiptContext.take(actor, targetId);
        if (receipt == null) return;

        if (event.isCanceled() || event.getAmount() <= 0.0F || !eligible(player)) {
            rollback(player, target, receipt);
            return;
        }

        synchronized (PENDING) {
            PENDING.computeIfAbsent(arrow, ignored -> new HashMap<>()).put(targetId, receipt);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDamagePost(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)
            || !(event.getEntity() instanceof LivingEntity target)) return;

        PendingHitReceipt receipt = take(arrow, target.getUUID().toString());
        if (receipt == null) return;
        if (!eligible(player) || event.getNewDamage() <= 0.0F) {
            rollback(player, target, receipt);
            return;
        }

        A0061A0080CombatState state = A0061A0080RuntimeState.state();
        CombatPerkRanks ranks = A0061A0080RuntimeState.ranks(player);
        String actor = A0061A0080RuntimeState.actorId(player);
        String targetId = target.getUUID().toString();
        String root = receipt.rootActionId();
        long now = now(player);

        if (receipt.executionReserved()) {
            if (ranks.rank("A0073") > 0) state.commitExecution(actor, targetId, root, now);
            else state.rollbackExecution(actor, targetId, root);
        } else if (receipt.executionArmCandidate()) {
            if (ranks.rank("A0073") > 0) state.armExecutionConfirmed(actor, targetId, root, now);
            else state.rollbackExecution(actor, targetId, root);
        }

        if (receipt.firstBloodTracked()) {
            FirstBloodReservation reservation = receipt.firstBloodReservation();
            if (ranks.rank("A0074") <= 0) {
                state.rollbackFirstBlood(actor, targetId, root);
            } else if (reservation != FirstBloodReservation.NONE) {
                boolean committed = state.commitFirstBlood(actor, targetId, root, reservation, now);
                if (!committed) {
                    state.rollbackFirstBlood(actor, targetId, root);
                    state.recordConfirmedAttack(actor, targetId, now);
                }
            } else {
                state.rollbackFirstBlood(actor, targetId, root);
                state.recordConfirmedAttack(actor, targetId, now);
            }
        }

        if (receipt.opportunityReserved()) {
            if (ranks.rank("A0080") > 0) state.commitOpportunity(actor, root, now);
            else state.rollbackOpportunity(actor, root);
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        A0061A0080RuntimeState.state().clearTarget(event.getEntity().getUUID().toString());
        if (event.getEntity() instanceof ServerPlayer player) A0061A0080RuntimeState.clear(player);
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity() instanceof LivingEntity living) {
            A0061A0080RuntimeState.state().clearTarget(living.getUUID().toString());
        }
        if (event.getEntity() instanceof AbstractArrow arrow) {
            synchronized (PENDING) { PENDING.remove(arrow); }
        }
    }

    @SubscribeEvent public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) { if (event.getEntity() instanceof ServerPlayer player) A0061A0080RuntimeState.clear(player); }
    @SubscribeEvent public static void onDimension(PlayerEvent.PlayerChangedDimensionEvent event) { if (event.getEntity() instanceof ServerPlayer player) A0061A0080RuntimeState.clear(player); }
    @SubscribeEvent public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) { if (event.getEntity() instanceof ServerPlayer player) A0061A0080RuntimeState.clear(player); }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        A0061A0080ReservationReceiptContext.clear();
        synchronized (PENDING) { PENDING.clear(); }
        A0061A0080RuntimeState.clearAll();
    }

    private static PendingHitReceipt take(AbstractArrow arrow, String targetId) {
        synchronized (PENDING) {
            Map<String, PendingHitReceipt> byTarget = PENDING.get(arrow);
            if (byTarget == null) return null;
            PendingHitReceipt receipt = byTarget.remove(targetId);
            if (byTarget.isEmpty()) PENDING.remove(arrow);
            return receipt;
        }
    }

    private static void rollback(ServerPlayer player, LivingEntity target, PendingHitReceipt receipt) {
        A0061A0080CombatState state = A0061A0080RuntimeState.state();
        String actor = A0061A0080RuntimeState.actorId(player);
        String targetId = target.getUUID().toString();
        String root = receipt.rootActionId();
        if (receipt.executionReserved() || receipt.executionArmCandidate()) state.rollbackExecution(actor, targetId, root);
        if (receipt.firstBloodTracked()) state.rollbackFirstBlood(actor, targetId, root);
        if (receipt.opportunityReserved()) state.rollbackOpportunity(actor, root);
    }

    private static boolean eligible(ServerPlayer player) {
        return !player.level().isClientSide()
            && !player.isCreative()
            && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }

    private static long now(ServerPlayer player) {
        return Math.multiplyExact(player.level().getGameTime(), 50L);
    }
}
