package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.CanonicalPlayerAttachmentRuntime;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.animal.Wolf;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/**
 * Conservative Charisma companion damage adapter: only genuine vanilla tamed
 * wolves, melee hits on hostile mobs and an online same-dimension owner.
 * Modded summons, projectiles, indirect attacks and PvP fail closed.
 */
public final class InvestedCharismaCompanionDamageEvents {
    private static final Set<LivingIncomingDamageEvent> APPLIED =
        Collections.newSetFromMap(new WeakHashMap<>());

    private InvestedCharismaCompanionDamageEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Wolf wolf)
            || !(wolf.level() instanceof ServerLevel level)) return;
        var ownerId = wolf.getOwnerUUID();
        if (ownerId == null) return;
        ServerPlayer owner = level.getServer().getPlayerList().getPlayer(ownerId);
        if (owner != null) applyForOwner(event, owner);
    }

    // Package-private seam: tests construct the real NeoForge event and a
    // detached ServerPlayer fixture; production resolves the online owner above.
    static void applyForOwner(LivingIncomingDamageEvent event, ServerPlayer owner) {
        if (event.isCanceled() || !(event.getSource().getEntity() instanceof Wolf wolf)
            || wolf.getClass() != Wolf.class
            || wolf.getType() != EntityType.WOLF
            || !wolf.isTame()
            || !wolf.isAlive()
            || !owner.getUUID().equals(wolf.getOwnerUUID())
            || owner.level() != wolf.level()
            || owner.level().isClientSide()
            || owner.isCreative() || owner.isSpectator() || owner instanceof FakePlayer
            || !event.getSource().is(DamageTypes.MOB_ATTACK)
            || event.getSource().getDirectEntity() != wolf
            || !(event.getEntity() instanceof Enemy)
            || !event.getEntity().isAlive()
            || event.getEntity().isInvulnerable()
            || event.getEntity() == wolf
            || event.getEntity() == owner
            || owner.isAlliedTo(event.getEntity())
            || wolf.isAlliedTo(event.getEntity())) return;

        float amount = event.getAmount();
        if (!(amount > 0.0F) || !Float.isFinite(amount)) return;
        AttributeRanks ranks = CanonicalPlayerAttachmentRuntime.observe(owner)
            .coreProgression().state()
            .map(state -> state.attributeRanks())
            .orElse(AttributeRanks.empty());
        long rank = ranks.rank(AttributeId.CHARISMA);
        if (rank < 8L) return;

        double bonus = AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.CHARISMA_COMPANION_DAMAGE, rank);
        double adjusted = amount * (1.0D + bonus);
        if (!(bonus > 0.0D) || !Double.isFinite(adjusted)
            || adjusted > Float.MAX_VALUE) return;
        synchronized (APPLIED) {
            if (!APPLIED.add(event)) return;
            event.setAmount((float) adjusted);
        }
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        synchronized (APPLIED) {
            APPLIED.clear();
        }
    }
}
