package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.CanonicalPlayerAttachmentRuntime;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/**
 * Minimal vanilla-control boundary: fresh Shulker levitation only.
 * Does not touch effect refresh/merge, hidden effects, forced applications,
 * commands, other mobs, beneficial effects or modded control systems.
 */
public final class InvestedDeterminationControlEvents {
    private static final Set<MobEffectEvent.Added> APPLIED =
        Collections.newSetFromMap(new WeakHashMap<>());

    private InvestedDeterminationControlEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onAdded(MobEffectEvent.Added event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
            || player.level().isClientSide()
            || event.getOldEffectInstance() != null
            || !vanillaShulkerSource(event.getEffectSource(), player)) return;

        MobEffectInstance applied = event.getEffectInstance();
        if (applied == null || !applied.is(MobEffects.LEVITATION)
            || applied.isInfiniteDuration()) return;
        int duration = applied.getDuration();
        if (duration <= 1) return;

        AttributeRanks ranks = CanonicalPlayerAttachmentRuntime.observe(player)
            .coreProgression().state()
            .map(state -> state.attributeRanks())
            .orElse(AttributeRanks.empty());
        long rank = ranks.rank(AttributeId.DETERMINATION);
        if (rank < 8L) return;
        double reduction = AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.DETERMINATION_CONTROL_DURATION, rank);
        if (!(reduction > 0.0D) || !Double.isFinite(reduction)) return;
        int shortened = (int) Math.max(1.0D, Math.floor(duration * (1.0D - reduction)));
        if (shortened >= duration) return;

        // Added is fired with the incoming instance before the vanilla merge.
        // Never alter an already installed effect or an existing hidden chain.
        synchronized (APPLIED) {
            if (!APPLIED.add(event)) return;
            applied.mapDuration(ignored -> shortened);
        }
    }

    private static boolean vanillaShulkerSource(Entity source, ServerPlayer player) {
        if (source == null || source.level() != player.level()) return false;
        if (source.getClass() == Shulker.class) return true;
        return source.getClass() == ShulkerBullet.class
            && ((ShulkerBullet) source).getOwner() instanceof Shulker shulker
            && shulker.getClass() == Shulker.class
            && shulker.level() == player.level();
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        synchronized (APPLIED) {
            APPLIED.clear();
        }
    }
}
