package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.A0021A0040CombatPolicy;
import dev.gustavopere.rpgskilltree.core.A0021A0040CombatState;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.EpicFightWeaponCategory;
import dev.gustavopere.rpgskilltree.runtime.A0021A0040RuntimeState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.entity.StunnedEvent;
import yesman.epicfight.api.event.types.player.TickPlayerEpicFightModeEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

/** Missing provider-native/lifecycle receipts for the closed A0022 contract. */
public final class A0022RuntimeHooks {
    private static final String STUN_ID = "rpgskilltree:a0022/stunned";
    private static final String TICK_ID = "rpgskilltree:a0022/reposition";
    private static boolean registered;

    private A0022RuntimeHooks() {}

    public static synchronized void register() {
        if (registered) return;
        EpicFightEventHooks.Entity.ON_STUNNED.registerEvent(A0022RuntimeHooks::onStunned, STUN_ID);
        EpicFightEventHooks.Player.TICK_EPICFIGHT_MODE.registerEvent(A0022RuntimeHooks::onEpicFightTick, TICK_ID);
        registered = true;
    }

    private static void onStunned(StunnedEvent event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        StunType type = event.getStunType();
        if (type != StunType.LONG && type != StunType.KNOCKDOWN && type != StunType.NEUTRALIZE) return;
        EpicFightDamageSource source = event.getDamageSource();
        if (source == null || !(source.getEntity() instanceof LivingEntity attacker) || !hostile(player, attacker)) return;

        CombatPerkRanks ranks = A0021A0040RuntimeState.ranks(player);
        A0021A0040CombatPolicy.onConfirmedHeavyStagger(
            A0021A0040RuntimeState.actorId(player),
            ranks,
            A0021A0040RuntimeState.state(),
            now(player)
        );
    }

    private static void onEpicFightTick(TickPlayerEpicFightModeEvent event) {
        if (!(event.getPlayerPatch() instanceof ServerPlayerPatch patch)) return;
        ServerPlayer player = patch.getOriginal();
        if (!eligible(player)) return;

        A0021A0040CombatState state = A0021A0040RuntimeState.state();
        String actor = A0021A0040RuntimeState.actorId(player);
        var motion = player.getDeltaMovement();
        state.updateForcedRepositionSuppression(actor, motion.x * motion.x + motion.z * motion.z);

        if (providerFamily(player) != WeaponFamily.DAGGER) return;
        CombatPerkRanks ranks = A0021A0040RuntimeState.ranks(player);
        if (ranks.rank("A0022") <= 0 && !ranks.learned("A0024")) return;
        LivingEntity target = patch.getTarget();
        if (target == null || !target.isAlive() || !hostile(player, target)) {
            state.invalidateFallbackReposition(actor);
            return;
        }

        state.sampleFallbackReposition(
            actor,
            target.getUUID().toString(),
            player.getX(),
            player.getZ(),
            target.getX(),
            target.getZ(),
            now(player)
        );
    }

    @SubscribeEvent
    public static void onTeleport(EntityTeleportEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && eligible(player)) {
            A0021A0040RuntimeState.state().invalidateFallbackReposition(A0021A0040RuntimeState.actorId(player));
        }
    }

    @SubscribeEvent
    public static void onKnockback(LivingKnockBackEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && eligible(player)) {
            A0021A0040RuntimeState.state().beginForcedRepositionSuppression(A0021A0040RuntimeState.actorId(player));
        }
    }

    private static WeaponFamily providerFamily(ServerPlayer player) {
        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(player.getMainHandItem());
        if (capability == null || capability.isEmpty()) return null;
        String category = EpicFightWeaponCategory.normalize(capability.getWeaponCategory().toString());
        int slash = category.lastIndexOf('/');
        if (slash >= 0 && slash + 1 < category.length()) category = category.substring(slash + 1);
        return category.equals("dagger") ? WeaponFamily.DAGGER : null;
    }

    private static boolean hostile(ServerPlayer player, LivingEntity target) {
        return target != player
            && !player.isAlliedTo(target)
            && !target.isInvulnerable()
            && (target instanceof Enemy || target instanceof Player);
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
