package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.CanonicalPlayerAttachmentRuntime;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import io.redspace.ironsspellbooks.api.events.SpellHealEvent;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/**
 * Correlates an authored Iron's spell heal with the immediately following
 * native LivingHealEvent, then modifies only that existing native heal.
 *
 * The exact Iron's release fires SpellHealEvent immediately before target.heal
 * from HealSpell, GreaterHealSpell and HealingAoe; SpellHealEvent itself has no
 * mutable amount. A one-use, same-tick, exact-float receipt is therefore needed.
 * On ambiguous or reordered events the safe result is no Charisma bonus.
 *
 * Only load after the exact audited provider version has passed its gate.
 */
public final class IronsAllyHealingEvents {
    private static final Map<ServerPlayer, PendingHeal> PENDING =
        Collections.synchronizedMap(new WeakHashMap<>());

    private IronsAllyHealingEvents() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpellHeal(SpellHealEvent event) {
        if (!(event.getTargetEntity() instanceof ServerPlayer target)) return;
        // A fresh provider event for this target invalidates any unused receipt.
        synchronized (PENDING) {
            PENDING.remove(target);
        }
        if (!(event.getEntity() instanceof ServerPlayer caster)
            || caster == target
            || caster.level() != target.level()
            || caster.level().isClientSide()
            || caster instanceof FakePlayer || target instanceof FakePlayer
            || caster.isCreative() || caster.isSpectator()
            || target.isCreative() || target.isSpectator() || !target.isAlive()
            || (caster.getTeam() != null && !caster.isAlliedTo(target))
            || event.getSchoolType() != SchoolRegistry.HOLY.get()
            || !IronsInvestmentAttributePolicy.operational(
                OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS),
                OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS))) return;

        float base = event.getHealAmount();
        float missing = target.getMaxHealth() - target.getHealth();
        if (!(base > 0.0F) || !Float.isFinite(base)
            || !(missing > base) || !Float.isFinite(missing)) return;

        AttributeRanks ranks = CanonicalPlayerAttachmentRuntime.observe(caster)
            .coreProgression().state()
            .map(state -> state.attributeRanks())
            .orElse(AttributeRanks.empty());
        long rank = ranks.rank(AttributeId.CHARISMA);
        if (rank < 10L) return;
        double bonus = AttributeInvestmentBonuses.value(
            AttributeInvestmentBonuses.Benefit.CHARISMA_ALLY_HEALING, rank);
        if (!(bonus > 0.0D) || !Double.isFinite(bonus)) return;

        synchronized (PENDING) {
            PENDING.put(target, new PendingHeal(
                Float.floatToIntBits(base), target.level().getGameTime(), bonus));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingHeal(LivingHealEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer target)
            || target.level().isClientSide()) return;
        PendingHeal receipt;
        synchronized (PENDING) {
            // Consume even if cancelled or mismatched: never credit a later heal.
            receipt = PENDING.remove(target);
        }
        if (receipt == null || event.isCanceled()
            || receipt.gameTick() != target.level().getGameTime()) return;

        float base = event.getAmount();
        if (!(base > 0.0F) || !Float.isFinite(base)
            || Float.floatToIntBits(base) != receipt.amountBits()) return;

        double missing = target.getMaxHealth() - target.getHealth();
        if (!Double.isFinite(missing) || !(missing > base)) return;
        double adjusted = Math.min(missing, base * (1.0D + receipt.bonus()));
        if (adjusted > base && Double.isFinite(adjusted) && adjusted <= Float.MAX_VALUE) {
            event.setAmount((float) adjusted);
        }
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        synchronized (PENDING) {
            PENDING.clear();
        }
    }

    private record PendingHeal(int amountBits, long gameTick, double bonus) {}
}
