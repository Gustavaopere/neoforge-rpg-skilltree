package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import dev.gustavopere.rpgskilltree.runtime.A0081A0090SustainRuntime;
import dev.gustavopere.rpgskilltree.runtime.A0081A0100RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/**
 * A0083 direct-magic adapter for the exact audited Iron's 3.16.3 SpellDamageSource contract.
 * No provider implementation type is linked at compile time; version/API drift disables A0083
 * through CombatPerkAvailabilityRuntime rather than falling back to namespace heuristics.
 */
public final class IronsSustainEvents {
    private static final WeakHashMap<DamageSource, Map<UUID, PendingMagicHit>> PENDING = new WeakHashMap<>();
    private static final WeakHashMap<DamageSource, String> ROOTS = new WeakHashMap<>();
    private static final AtomicLong ACTION_SEQUENCE = new AtomicLong();

    private IronsSustainEvents() {}

    public static boolean operational() {
        return OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            && IronsSustainVersionContract.supportsVersion(
                OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
            )
            && IronsSustainVersionContract.runtimeContractPresent();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (!operational() || event.isCanceled() || event.getAmount() <= 0.0F) return;
        DamageSource source = event.getSource();
        if (!IronsSustainVersionContract.isSpellDamageSource(source) || !source.isDirect()) return;
        if (!(source.getEntity() instanceof ServerPlayer player)
            || !eligible(player)
            || !hostile(player, event.getEntity())) return;

        // Formula support alone is not enough. effectiveRanks makes this branch zero when the
        // provider version/API contract is absent or the node is otherwise unavailable.
        if (A0081A0100RuntimeState.ranks(player).rank("A0083") <= 0) return;

        Float nativeLifesteal = IronsSustainVersionContract.lifestealPercent(source);
        boolean nativeAmbiguous = nativeLifesteal == null || nativeLifesteal > 0.0F;
        PendingMagicHit pending = new PendingMagicHit(
            player,
            rootFor(source, player),
            event.getEntity().getHealth(),
            nativeAmbiguous
        );
        synchronized (PENDING) {
            PENDING.computeIfAbsent(source, ignored -> new HashMap<>())
                .put(event.getEntity().getUUID(), pending);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDamagePost(LivingDamageEvent.Post event) {
        if (!IronsSustainVersionContract.isSpellDamageSource(event.getSource())) return;
        PendingMagicHit pending = take(event.getSource(), event.getEntity().getUUID());
        if (pending == null || event.getNewDamage() <= 0.0F || !eligible(pending.player())) return;

        A0081A0090SustainRuntime.resolveDirectMagicHit(
            pending.player(),
            pending.rootActionId(),
            pending.targetHealthBefore(),
            event.getNewDamage(),
            pending.nativeLifestealAmbiguous()
        );
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearPlayer(player);
    }

    @SubscribeEvent
    public static void onDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearPlayer(player);
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearPlayer(player);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        synchronized (PENDING) {
            PENDING.clear();
            ROOTS.clear();
        }
    }

    private static String rootFor(DamageSource source, ServerPlayer player) {
        synchronized (PENDING) {
            return ROOTS.computeIfAbsent(
                source,
                ignored -> "irons-direct-magic/" + player.level().getGameTime() + "/" + ACTION_SEQUENCE.incrementAndGet()
            );
        }
    }

    private static PendingMagicHit take(DamageSource source, UUID targetId) {
        synchronized (PENDING) {
            Map<UUID, PendingMagicHit> byTarget = PENDING.get(source);
            if (byTarget == null) return null;
            PendingMagicHit pending = byTarget.remove(targetId);
            if (byTarget.isEmpty()) PENDING.remove(source);
            return pending;
        }
    }

    private static void clearPlayer(ServerPlayer player) {
        synchronized (PENDING) {
            PENDING.values().forEach(byTarget -> byTarget.values().removeIf(pending -> pending.player() == player));
            PENDING.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        }
    }

    private static boolean hostile(ServerPlayer player, LivingEntity target) {
        return target != player
            && !player.isAlliedTo(target)
            && !target.isInvulnerable()
            && (target instanceof Enemy || target instanceof Player);
    }

    private static boolean eligible(ServerPlayer player) {
        return !player.level().isClientSide()
            && player.isAlive()
            && !player.isCreative()
            && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }

    private record PendingMagicHit(
        ServerPlayer player,
        String rootActionId,
        double targetHealthBefore,
        boolean nativeLifestealAmbiguous
    ) {}
}
