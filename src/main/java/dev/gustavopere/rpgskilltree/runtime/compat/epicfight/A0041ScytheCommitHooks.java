package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.EpicFightWeaponCategory;
import dev.gustavopere.rpgskilltree.runtime.A0021A0040RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.A0041A0060RuntimeState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.FakePlayer;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

/**
 * Causal POST owner for A0041 Corte de Ceifa.
 *
 * <p>The existing A0041-A0060 PRE bridge only reserves a mature Reaping Mark. This hook consumes
 * that reservation after Epic Fight confirms positive modified damage. Zero-damage outcomes roll
 * back by discarding the reservation, and lethal hits remain eligible because target liveness is
 * deliberately not part of the POST classification.</p>
 */
public final class A0041ScytheCommitHooks {
    private static final String POST_ID = "rpgskilltree:a0041/commit";
    private static boolean registered;

    private A0041ScytheCommitHooks() {}

    public static synchronized void register() {
        if (registered) return;
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_POST.registerEvent(A0041ScytheCommitHooks::onDamagePost, POST_ID);
        registered = true;
    }

    private static void onDamagePost(DealDamageEvent.Post event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        LivingEntity target = event.getTarget();
        EpicFightDamageSource source = event.getDamageSource();
        if (source.getDirectEntity() != player || !scythe(source)) return;

        String actorId = player.getUUID().toString();
        String targetId = target.getUUID().toString();
        long now = player.level().getGameTime() * 50L;

        if (event.getModifiedDamage() <= 0.0F || !hostileIdentity(player, target)) {
            A0041A0060RuntimeState.state().discardScytheCutReservationForTarget(actorId, targetId);
            return;
        }

        String rootActionId = A0041A0060RuntimeState.state()
            .takeScytheCutReservationForTarget(actorId, targetId, now);
        if (rootActionId == null) return;

        // The reservation proves that PRE observed an already-mature mark for this actor+target.
        // POST performs the irreversible consume only after positive provider-confirmed damage.
        A0021A0040RuntimeState.state().consumeMatureReap(
            actorId,
            targetId,
            healthFraction(target),
            now
        );
    }

    private static boolean scythe(EpicFightDamageSource source) {
        var capability = EpicFightCapabilities.getItemStackCapability(source.getUsedItem());
        if (capability == null || capability.isEmpty()) return false;
        String category = EpicFightWeaponCategory.normalize(capability.getWeaponCategory().toString());
        int slash = category.lastIndexOf('/');
        if (slash >= 0 && slash + 1 < category.length()) category = category.substring(slash + 1);
        return "scythe".equals(category);
    }

    private static boolean eligible(ServerPlayer player) {
        return !player.level().isClientSide()
            && !player.isCreative()
            && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }

    private static boolean hostileIdentity(ServerPlayer player, LivingEntity target) {
        return target != player
            && !player.isAlliedTo(target)
            && !target.isInvulnerable()
            && (target instanceof Enemy || target instanceof Player);
    }

    private static double healthFraction(LivingEntity target) {
        return target.getMaxHealth() <= 0.0F
            ? 0.0D
            : Math.max(0.0D, Math.min(1.0D, target.getHealth() / target.getMaxHealth()));
    }
}
