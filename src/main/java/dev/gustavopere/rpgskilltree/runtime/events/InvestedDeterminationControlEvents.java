package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.CanonicalPlayerAttachmentRuntime;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Exact vanilla Shulker levitation only. NeoForge's Added event is a
 * notification, not a writable replacement of the incoming MobEffectInstance.
 * Keep the vanilla instance unchanged and expire it a few ticks earlier.
 *
 * This is intentionally NOT a global debuff/control reduction.
 */
public final class InvestedDeterminationControlEvents {
    private static final Map<ServerPlayer, ExpiryReceipt> PENDING =
        Collections.synchronizedMap(new WeakHashMap<>());

    private InvestedDeterminationControlEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onAdded(MobEffectEvent.Added event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
            || player.level().isClientSide()) return;
        MobEffectInstance incoming = event.getEffectInstance();
        if (incoming == null || !incoming.is(MobEffects.LEVITATION)) return;

        // A refresh (or a new effect from another source) must never keep the
        // previous shulker attribution, even when vanilla reuses its object.
        synchronized (PENDING) {
            PENDING.remove(player);
        }
        if (event.getOldEffectInstance() != null
            || !sourceIsVanillaShulker(event.getEffectSource(), player)
            || player instanceof FakePlayer || player.isCreative() || player.isSpectator()
            || incoming.isInfiniteDuration()) return;

        int originalDuration = incoming.getDuration();
        if (originalDuration <= 1) return;
        long rank = currentRank(player);
        if (rank < 8L) return;
        double reduction = AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.DETERMINATION_CONTROL_DURATION, rank);
        if (!(reduction > 0.0D) || !Double.isFinite(reduction) || reduction >= 1.0D) return;
        int shortened = (int) Math.max(1.0D, Math.floor(originalDuration * (1.0D - reduction)));
        int earlyExpiryRemaining = originalDuration - shortened;
        if (earlyExpiryRemaining <= 0) return;

        synchronized (PENDING) {
            PENDING.put(player, new ExpiryReceipt(incoming, earlyExpiryRemaining, rank));
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
            || player.level().isClientSide()) return;

        ExpiryReceipt receipt;
        synchronized (PENDING) {
            receipt = PENDING.get(player);
        }
        if (receipt == null) return;
        MobEffectInstance active = player.getEffect(MobEffects.LEVITATION);
        if (!player.isAlive() || active != receipt.original()
            || player instanceof FakePlayer || player.isCreative() || player.isSpectator()
            || currentRank(player) != receipt.rank()) {
            synchronized (PENDING) {
                PENDING.remove(player);
            }
            return;
        }
        if (active.getDuration() > receipt.expiryRemaining()) return;

        synchronized (PENDING) {
            PENDING.remove(player);
        }
        // Native removal keeps effect cleanup, packets, and attributes within
        // Minecraft's original pipeline. A third-party cancellation fails
        // closed (it leaves the effect at its original duration).
        player.removeEffect(MobEffects.LEVITATION);
    }

    private static long currentRank(ServerPlayer player) {
        AttributeRanks ranks = CanonicalPlayerAttachmentRuntime.observe(player)
            .coreProgression().state()
            .map(state -> state.attributeRanks())
            .orElse(AttributeRanks.empty());
        return ranks.rank(AttributeId.DETERMINATION);
    }

    private static boolean sourceIsVanillaShulker(Entity source, ServerPlayer player) {
        if (source == null || source.level() != player.level()) return false;
        if (source.getClass() == Shulker.class) return true;
        return source.getClass() == ShulkerBullet.class
            && ((ShulkerBullet) source).getOwner() instanceof Shulker shulker
            && shulker.getClass() == Shulker.class
            && shulker.level() == player.level();
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        synchronized (PENDING) {
            PENDING.clear();
        }
    }

    private record ExpiryReceipt(MobEffectInstance original, int expiryRemaining, long rank) {}
}
