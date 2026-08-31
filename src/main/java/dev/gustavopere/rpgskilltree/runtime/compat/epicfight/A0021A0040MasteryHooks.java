package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.A0021A0040MasteryPolicy;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.EpicFightWeaponCategory;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

/**
 * Feeds the finite canonical mastery lanes used by A0025/A0031/A0037 gates.
 * Epic Fight's generic family mastery remains untouched; these aliases only bridge the canonical gates.
 */
public final class A0021A0040MasteryHooks {
    private static final String EPIC_POST_ID = "rpgskilltree:a0021_a0040/mastery";
    private static final WeakHashMap<DamageSource, Map<String, WeaponFamily>> VANILLA_PENDING = new WeakHashMap<>();
    private static boolean registered;

    private A0021A0040MasteryHooks() {}

    public static synchronized void register() {
        if (registered) return;
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_POST.registerEvent(
            A0021A0040MasteryHooks::onEpicFightDamagePost,
            EPIC_POST_ID
        );
        registered = true;
    }

    private static void onEpicFightDamagePost(DealDamageEvent.Post event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player)
            || !eligible(player)
            || event.getModifiedDamage() <= 0.0F
            || event.getDamageSource().getDirectEntity() != player
            || !hostile(player, event.getTarget())) return;

        Optional<WeaponFamily> family = providerFamily(
            EpicFightCapabilities.getItemStackCapability(event.getDamageSource().getUsedItem())
        );
        if (family.isEmpty()) return;
        award(player, event.getTarget(), family.get(), event.getModifiedDamage());
    }

    /**
     * NeoForge fallback is intentionally exact: only vanilla minecraft:mace is accepted without
     * provider classification. External MACE/SCYTHE items remain fail-closed.
     */
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onVanillaIncoming(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof ServerPlayer player)
            || !eligible(player)
            || !hostile(player, event.getEntity())) return;

        ItemStack stack = player.getMainHandItem();
        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(stack);
        if (providerFamily(capability).isPresent()) return;
        Optional<WeaponFamily> family = vanillaFallbackFamily(stack);
        if (family.isEmpty()) return;
        synchronized (VANILLA_PENDING) {
            VANILLA_PENDING.computeIfAbsent(event.getSource(), ignored -> new HashMap<>())
                .put(event.getEntity().getUUID().toString(), family.get());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onVanillaDamagePost(LivingDamageEvent.Post event) {
        if (!(event.getSource().getDirectEntity() instanceof ServerPlayer player) || !eligible(player)) return;
        String targetId = event.getEntity().getUUID().toString();
        WeaponFamily family;
        synchronized (VANILLA_PENDING) {
            Map<String, WeaponFamily> byTarget = VANILLA_PENDING.get(event.getSource());
            if (byTarget == null) return;
            family = byTarget.remove(targetId);
            if (byTarget.isEmpty()) VANILLA_PENDING.remove(event.getSource());
        }
        if (family == null || event.getNewDamage() <= 0.0F || !hostile(player, event.getEntity())) return;
        award(player, event.getEntity(), family, event.getNewDamage());
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        clearTarget(event.getEntity().getUUID().toString());
        if (event.getEntity() instanceof ServerPlayer player) clearActor(player);
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearActor(player);
    }

    @SubscribeEvent
    public static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearActor(player);
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearActor(player);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        synchronized (VANILLA_PENDING) {
            VANILLA_PENDING.clear();
        }
    }

    private static void award(
        ServerPlayer player,
        LivingEntity target,
        WeaponFamily family,
        double damage
    ) {
        String entityTypeId = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType()).toString();
        Optional<String> discoveryKey = A0021A0040MasteryPolicy.discoveryKey(family, entityTypeId);
        if (discoveryKey.isEmpty()) return;
        String key = discoveryKey.get();
        boolean newlyDiscovered = !PlayerProgressionRuntime.get(player).discoveries().contains(key);
        var awards = A0021A0040MasteryPolicy.forDistinctHostileTypeDiscovery(
            family, true, true, damage, entityTypeId, newlyDiscovered
        );
        if (!awards.isEmpty()) {
            PlayerProgressionRuntime.awardMasteryAndDiscoveries(player, awards, List.of(key));
        }
    }

    private static Optional<WeaponFamily> providerFamily(CapabilityItem capability) {
        if (capability == null || capability.isEmpty()) return Optional.empty();
        String category = EpicFightWeaponCategory.normalize(capability.getWeaponCategory().toString());
        int slash = category.lastIndexOf('/');
        if (slash >= 0 && slash + 1 < category.length()) category = category.substring(slash + 1);
        return switch (category) {
            case "hammer" -> Optional.of(WeaponFamily.HAMMER);
            case "mace" -> Optional.of(WeaponFamily.MACE);
            case "scythe" -> Optional.of(WeaponFamily.SCYTHE);
            default -> Optional.empty();
        };
    }

    private static Optional<WeaponFamily> vanillaFallbackFamily(ItemStack stack) {
        return stack.is(Items.MACE) ? Optional.of(WeaponFamily.MACE) : Optional.empty();
    }

    private static boolean hostile(ServerPlayer player, LivingEntity target) {
        return target != player
            && !player.isAlliedTo(target)
            && !target.isInvulnerable()
            && (target instanceof Enemy || target instanceof Player);
    }

    private static boolean eligible(ServerPlayer player) {
        return !(player instanceof FakePlayer)
            && !player.isCreative()
            && !player.isSpectator()
            && !player.level().isClientSide();
    }

    private static void clearTarget(String targetId) {
        synchronized (VANILLA_PENDING) {
            VANILLA_PENDING.values().forEach(byTarget -> byTarget.remove(targetId));
            VANILLA_PENDING.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        }
    }

    private static void clearActor(ServerPlayer player) {
        synchronized (VANILLA_PENDING) {
            VANILLA_PENDING.entrySet().removeIf(entry -> entry.getKey().getDirectEntity() == player);
        }
    }
}
