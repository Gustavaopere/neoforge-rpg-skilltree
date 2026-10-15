package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.CanonicalPlayerAttachmentRuntime;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import io.redspace.ironsspellbooks.api.events.SpellCooldownAddedEvent;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;

/**
 * Iron's-specific cooldown event boundary. Registered only if the exact audited
 * provider is loaded; this class must never be loaded on provider-free servers.
 */
public final class IronsInvestmentCooldownEvents {
    private IronsInvestmentCooldownEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCooldownPre(SpellCooldownAddedEvent.Pre event) {
        if (event.isCanceled()) return;
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player instanceof FakePlayer || player.isCreative() || player.isSpectator()) return;
        if (!event.getCastSource().respectsCooldown()) return;
        if (!IronsInvestmentAttributePolicy.operational(
            OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS),
            OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS))) return;

        AttributeRanks invested = CanonicalPlayerAttachmentRuntime.observe(player)
            .coreProgression().state()
            .map(state -> state.attributeRanks())
            .orElse(AttributeRanks.empty());
        long rank = invested.rank(AttributeId.DETERMINATION);
        if (rank < 10L) return;

        int before = event.getEffectiveCooldown();
        if (before <= 1) return;
        int after = IronsCooldownInvestmentPolicy.adjustedCooldown(before, rank);
        if (after != before) event.setEffectiveCooldown(after);
    }
}
