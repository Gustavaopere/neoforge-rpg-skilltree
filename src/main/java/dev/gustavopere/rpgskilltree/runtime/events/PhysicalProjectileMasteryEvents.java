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
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
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
 * Server-authoritative mastery producer for physical bow and crossbow shots.
 *
 * <p>A projectile is eligible only when it can be correlated to a real, non-cancelled player
 * {@link ArrowLooseEvent} for the same physical weapon category. The correlation remains valid
 * briefly so vanilla/modded multishot can associate every projectile from one release. Synthetic,
 * spell-derived, owner-only or otherwise uncorrelated arrows fail closed. The post-damage receipt
 * shares the persisted discovery identity used by the Epic Fight adapter.
 */
public final class PhysicalProjectileMasteryEvents {
    private static final long LAUNCH_CORRELATION_MILLIS = 250L;
    private static final Map<UUID, PendingRelease> PENDING_RELEASES = new HashMap<>();
    private static final WeakHashMap<AbstractArrow, String> PLAYER_LAUNCHED = new WeakHashMap<>();

    private PhysicalProjectileMasteryEvents() {}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onArrowLoose(ArrowLooseEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
            || !eligible(player)
            || event.isCanceled()
            || !event.hasAmmo()) return;
        String category = physicalCategory(event.getBow());
        if (category == null) return;
        PENDING_RELEASES.put(
            player.getUUID(),
            new PendingRelease(category, now(player) + LAUNCH_CORRELATION_MILLIS)
        );
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)
            || !eligible(player)) return;
        String category = physicalCategory(arrow.getWeaponItem());
        if (category == null) return;
        PendingRelease pending = PENDING_RELEASES.get(player.getUUID());
        if (pending == null || pending.expiresAt() < now(player) || !pending.category().equals(category)) return;
        synchronized (PLAYER_LAUNCHED) {
            PLAYER_LAUNCHED.put(arrow, category);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDamagePost(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)
            || !(arrow.getOwner() instanceof ServerPlayer player)
            || !eligible(player)
            || event.getNewDamage() <= 0.0F
            || !hostile(player, event.getEntity())) return;
        String category = confirmedPlayerLaunch(arrow);
        if (category == null) return;

        String targetType = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType()).toString();
        var milestone = WeaponMasteryMilestonePolicy.confirmedPhysicalProjectileHit(
            "minecraft:projectile_damage_post",
            "minecraft",
            category,
            targetType,
            event.getNewDamage(),
            true
        );
        WeaponMasteryMilestoneRuntime.awardIfNew(player, milestone);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onServerTick(ServerTickEvent.Post event) {
        long now = event.getServer().overworld().getGameTime() * 50L;
        PENDING_RELEASES.entrySet().removeIf(entry -> entry.getValue().expiresAt() < now);
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

    private static String confirmedPlayerLaunch(AbstractArrow arrow) {
        synchronized (PLAYER_LAUNCHED) {
            return PLAYER_LAUNCHED.get(arrow);
        }
    }

    private static String physicalCategory(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return null;
        if (stack.getItem() instanceof BowItem) return "bow";
        if (stack.getItem() instanceof CrossbowItem) return "crossbow";
        return null;
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

    private record PendingRelease(String category, long expiresAt) {}
}
