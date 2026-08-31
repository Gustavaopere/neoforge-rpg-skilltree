package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.WeaponMasteryMilestonePolicy;
import dev.gustavopere.rpgskilltree.runtime.WeaponMasteryMilestoneRuntime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/**
 * Server-authoritative BOW Mastery producer for physical bow shots.
 *
 * <p>A projectile is eligible only when it can be correlated to a real, non-cancelled player
 * {@link ArrowLooseEvent}. Synthetic/spell/derived arrows that merely carry an owner fail closed.
 * The confirmed post-damage receipt then uses the same persisted discovery identity as the Epic
 * Fight adapter, so the same semantic hit cannot award twice when both providers observe it.
 */
public final class BowMasteryProjectileEvents {
    private static final long LAUNCH_CORRELATION_MILLIS = 250L;
    private static final Map<UUID, Long> PENDING_RELEASES = new HashMap<>();
    private static final WeakHashMap<AbstractArrow, Boolean> PLAYER_LAUNCHED = new WeakHashMap<>();

    private BowMasteryProjectileEvents() {}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onArrowLoose(ArrowLooseEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
            || !eligible(player)
            || event.isCanceled()
            || !(event.getBow().getItem() instanceof BowItem)
            || !event.hasAmmo()) return;
        PENDING_RELEASES.put(player.getUUID(), now(player) + LAUNCH_CORRELATION_MILLIS);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)
            || !eligible(player)
            || !(arrow.getWeaponItem().getItem() instanceof BowItem)) return;
        Long expiresAt = PENDING_RELEASES.get(player.getUUID());
        if (expiresAt == null || expiresAt < now(player)) return;
        synchronized (PLAYER_LAUNCHED) {
            PLAYER_LAUNCHED.put(arrow, Boolean.TRUE);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDamagePost(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)
            || !eligible(player)
            || event.getNewDamage() <= 0.0F
            || !hostile(player, event.getEntity())
            || !confirmedPlayerLaunch(arrow)) return;

        String targetType = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType()).toString();
        var milestone = WeaponMasteryMilestonePolicy.confirmedHit(
            "minecraft:projectile_damage_post",
            "minecraft",
            "bow",
            targetType,
            event.getNewDamage()
        );
        WeaponMasteryMilestoneRuntime.awardIfNew(player, milestone);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onServerTick(ServerTickEvent.Post event) {
        long now = event.getServer().overworld().getGameTime() * 50L;
        PENDING_RELEASES.entrySet().removeIf(entry -> entry.getValue() < now);
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) PENDING_RELEASES.remove(player.getUUID());
    }

    @SubscribeEvent
    public static void onDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) PENDING_RELEASES.remove(player.getUUID());
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) PENDING_RELEASES.remove(player.getUUID());
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        PENDING_RELEASES.clear();
        synchronized (PLAYER_LAUNCHED) {
            PLAYER_LAUNCHED.clear();
        }
    }

    private static boolean confirmedPlayerLaunch(AbstractArrow arrow) {
        synchronized (PLAYER_LAUNCHED) {
            return PLAYER_LAUNCHED.containsKey(arrow);
        }
    }

    private static boolean eligible(ServerPlayer player) {
        return !player.level().isClientSide()
            && !player.isCreative()
            && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }

    private static boolean hostile(ServerPlayer player, LivingEntity target) {
        return target != player
            && target.isAlive()
            && !player.isAlliedTo(target)
            && !target.isInvulnerable()
            && (target instanceof Enemy || target instanceof Player);
    }

    private static long now(ServerPlayer player) {
        return player.level().getGameTime() * 50L;
    }
}
