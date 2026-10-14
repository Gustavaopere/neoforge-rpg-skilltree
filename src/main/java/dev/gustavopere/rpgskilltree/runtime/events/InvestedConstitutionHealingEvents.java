package dev.gustavopere.rpgskilltree.runtime.events;

import dev.gustavopere.rpgskilltree.core.AttributeInvestmentHealingPolicy;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.CanonicalPlayerAttachmentRuntime;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

/**
 * Applies Constitution's incoming-heal contribution to one native heal event.
 * Intentionally does not call LivingEntity.heal(), mutate an entity capability
 * or grant separate regeneration ticks.
 */
public final class InvestedConstitutionHealingEvents {
    private InvestedConstitutionHealingEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHeal(LivingHealEvent event) {
        if (event.isCanceled() || !(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.level().isClientSide()) return;
        float amount = event.getAmount();
        if (!Float.isFinite(amount) || amount <= 0.0F) return;
        AttributeRanks ranks = CanonicalPlayerAttachmentRuntime.observe(player)
            .coreProgression().state()
            .map(state -> state.attributeRanks())
            .orElse(AttributeRanks.empty());
        if (ranks.rank(dev.gustavopere.rpgskilltree.core.AttributeId.CONSTITUTION) < 8L) return;
        event.setAmount(AttributeInvestmentHealingPolicy.adjust(amount, ranks));
    }
}
