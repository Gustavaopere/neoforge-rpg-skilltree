package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.A0041A0060CombatPolicy;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.EpicFightWeaponCategory;
import dev.gustavopere.rpgskilltree.runtime.A0021A0040RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.A0041A0060RuntimeState;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

/** Preserves the pre-hit mature-mark receipt so A0042 still works when A0041 consumes that mark on the killing blow. */
public final class A0042ScytheKillHooks {
    private static final String PRE_ID = "rpgskilltree:a0042/mature_kill_receipt";
    private static final long RECEIPT_MILLIS = 1_000L;
    private static final Map<String, Long> MATURE_HIT_RECEIPTS = new HashMap<>();
    private static boolean registered;

    private A0042ScytheKillHooks() {}

    public static synchronized void register() {
        if (registered) return;
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_PRE.registerEvent(A0042ScytheKillHooks::onDamagePre, PRE_ID);
        registered = true;
    }

    private static void onDamagePre(DealDamageEvent.Pre event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        LivingEntity target = event.getTarget();
        EpicFightDamageSource source = event.getDamageSource();
        if (source.getDirectEntity() != player || !hostileLive(player, target) || !scythe(source.getUsedItem())) return;
        CombatPerkRanks ranks = A0041A0060RuntimeState.ranks(player);
        if (!ranks.learned("A0042")) return;
        long now = now(player);
        String actor = player.getUUID().toString();
        String targetId = target.getUUID().toString();
        if (A0021A0040RuntimeState.state().reapMature(actor, targetId, healthFraction(target), now)) {
            synchronized (A0042ScytheKillHooks.class) {
                MATURE_HIT_RECEIPTS.entrySet().removeIf(entry -> entry.getValue() <= now);
                MATURE_HIT_RECEIPTS.put(actor + '\0' + targetId, Math.addExact(now, RECEIPT_MILLIS));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onDeath(LivingDeathEvent event) {
        if (!(event.getSource() instanceof EpicFightDamageSource source)
            || !(source.getDirectEntity() instanceof ServerPlayer player)
            || !eligible(player)
            || !scythe(source.getUsedItem())) return;
        LivingEntity target = event.getEntity();
        if (!legitimateDeadTarget(player, target)) return;
        long now = now(player);
        String actor = player.getUUID().toString();
        String targetId = target.getUUID().toString();
        if (!claim(actor, targetId, now)) return;

        CombatPerkRanks ranks = A0041A0060RuntimeState.ranks(player);
        int mastery = A0041A0060RuntimeState.mastery(player, "combat:scythe");
        if (!ranks.learned("A0040") || !ranks.learned("A0041") || !ranks.learned("A0042") || mastery < 80) return;
        A0041A0060RuntimeState.state().armBattleHarvest(
            actor, targetId, A0041A0060CombatPolicy.battleHarvestCooldownMillis(mastery), now
        );
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        synchronized (A0042ScytheKillHooks.class) {
            MATURE_HIT_RECEIPTS.clear();
        }
    }

    private static synchronized boolean claim(String actor, String target, long now) {
        MATURE_HIT_RECEIPTS.entrySet().removeIf(entry -> entry.getValue() <= now);
        return MATURE_HIT_RECEIPTS.remove(actor + '\0' + target) != null;
    }

    private static boolean scythe(net.minecraft.world.item.ItemStack stack) {
        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(stack);
        if (capability == null || capability.isEmpty()) return false;
        String category = EpicFightWeaponCategory.normalize(capability.getWeaponCategory().toString());
        int slash = category.lastIndexOf('/');
        if (slash >= 0 && slash + 1 < category.length()) category = category.substring(slash + 1);
        return category.equals("scythe");
    }

    private static boolean eligible(ServerPlayer player) {
        return !player.level().isClientSide() && !player.isCreative() && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }

    private static boolean hostileLive(ServerPlayer player, LivingEntity target) {
        return target != player && target.isAlive() && !player.isAlliedTo(target) && !target.isInvulnerable()
            && (target instanceof Enemy || target instanceof Player);
    }

    private static boolean legitimateDeadTarget(ServerPlayer player, LivingEntity target) {
        return target != player && !player.isAlliedTo(target) && (target instanceof Enemy || target instanceof Player);
    }

    private static double healthFraction(LivingEntity target) {
        return target.getMaxHealth() <= 0.0F ? 0.0D
            : Math.max(0.0D, Math.min(1.0D, target.getHealth() / target.getMaxHealth()));
    }

    private static long now(ServerPlayer player) {
        return player.level().getGameTime() * 50L;
    }
}
