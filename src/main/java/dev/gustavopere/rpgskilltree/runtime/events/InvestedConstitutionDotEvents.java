package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.CanonicalPlayerAttachmentRuntime;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/**
 * Narrow Constitution DoT adapter for the two vanilla periodic damage sources
 * whose damage-type identity is unambiguous: ON_FIRE and WITHER.
 * Poison shares a damage type with unrelated magic, so it is deliberately excluded.
 */
public final class InvestedConstitutionDotEvents {
    private static final Set<LivingIncomingDamageEvent> APPLIED =
        Collections.newSetFromMap(new WeakHashMap<>());

    private InvestedConstitutionDotEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.isCanceled() || !(event.getEntity() instanceof ServerPlayer player)
            || player.level().isClientSide()) return;
        DamageSource source = event.getSource();
        if (source.getClass() != DamageSource.class
            || source.getDirectEntity() != null || source.getEntity() != null
            || (!source.is(DamageTypes.ON_FIRE) && !source.is(DamageTypes.WITHER))) return;

        float amount = event.getAmount();
        if (!(amount > 0.0F) || !Float.isFinite(amount)) return;
        AttributeRanks ranks = CanonicalPlayerAttachmentRuntime.observe(player)
            .coreProgression().state()
            .map(state -> state.attributeRanks())
            .orElse(AttributeRanks.empty());
        long rank = ranks.rank(AttributeId.CONSTITUTION);
        if (rank < 12L) return;
        double reduction = AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.CONSTITUTION_DAMAGE_OVER_TIME, rank);
        if (!(reduction > 0.0D) || !Double.isFinite(reduction)) return;
        float adjusted = (float) (amount * (1.0D - reduction));
        if (!(adjusted >= 0.0F) || !Float.isFinite(adjusted)) return;
        synchronized (APPLIED) {
            if (!APPLIED.add(event)) return;
            event.setAmount(adjusted);
        }
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        synchronized (APPLIED) {
            APPLIED.clear();
        }
    }
}
