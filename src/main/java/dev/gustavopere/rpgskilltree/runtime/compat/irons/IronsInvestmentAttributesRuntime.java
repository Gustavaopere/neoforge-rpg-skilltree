package dev.gustavopere.rpgskilltree.runtime.compat.irons;

import com.mojang.logging.LogUtils;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses.Benefit;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics.Category;
import dev.gustavopere.rpgskilltree.runtime.effects.AttributeEffectDiagnostics;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.slf4j.Logger;

/**
 * Only modifies the audited Iron's attribute registry when the exact 3.16.3 mod
 * is loaded. No mana bar, mana capability, spell event, or duplicate mana tick is created.
 * Each transient modifier has a stable identity and can be removed on refund or adapter disable.
 */
public final class IronsInvestmentAttributesRuntime {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Benefit[] BENEFITS = {
        Benefit.INTELLIGENCE_MAX_MANA,
        Benefit.INTELLIGENCE_MANA_REGEN,
        Benefit.INTELLIGENCE_SPELL_POWER
    };

    private IronsInvestmentAttributesRuntime() {}

    public static void refresh(ServerPlayer player, AttributeRanks ranks) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(ranks, "ranks");
        if (player.level().isClientSide()) throw new IllegalArgumentException("server player required");
        boolean active = IronsInvestmentAttributePolicy.operational(
            OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS),
            OptionalIntegrations.version(OptionalIntegrations.Provider.IRONS_SPELLBOOKS)
        );

        // A registry lookup by ID avoids optional class loading even during disable/rollback.
        for (Benefit benefit : BENEFITS) {
            var target = ResourceLocation.parse(IronsInvestmentAttributePolicy.targets().get(benefit));
            var holder = BuiltInRegistries.ATTRIBUTE.getHolder(target).orElse(null);
            if (holder == null) {
                if (active) unavailable(benefit, target, AttributeEffectDiagnostics.Reason.MISSING_REGISTRY_TARGET);
                continue;
            }
            var instance = player.getAttribute(holder);
            if (instance == null) {
                if (active) unavailable(benefit, target, AttributeEffectDiagnostics.Reason.MISSING_PLAYER_ATTRIBUTE);
                continue;
            }

            ResourceLocation modifierId = modifierId(benefit);
            instance.removeModifier(modifierId);
            if (!active) continue;  // Fail closed on absent / other-version provider.

            double value = AttributeInvestmentBonuses.value(benefit, ranks.rank(benefit.attribute()));
            if (value <= 0.0) continue;
            // MAX_MANA is flat units; MANA_REGEN and SPELL_POWER are relative to
            // the provider's effective attribute, and Iron's reads both live.
            var operation = benefit == Benefit.INTELLIGENCE_MAX_MANA
                ? AttributeModifier.Operation.ADD_VALUE
                : AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL;
            instance.addOrUpdateTransientModifier(new AttributeModifier(modifierId, value, operation));
        }
    }

    private static ResourceLocation modifierId(Benefit benefit) {
        return ResourceLocation.parse("rpgskilltree:investment/irons/"
            + benefit.name().toLowerCase(Locale.ROOT));
    }

    private static void unavailable(
        Benefit benefit, ResourceLocation target, AttributeEffectDiagnostics.Reason reason
    ) {
        String effectId = modifierId(benefit).toString();
        if (AttributeEffectDiagnostics.report(effectId, target.toString(), reason)) {
            RuntimeDiagnostics.warn(
                LOGGER, Category.EFFECTS, "irons_investment_attribute_unavailable",
                "Iron's 3.16.3 investment modifier {} cannot target attribute {} ({}); effect disabled",
                effectId, target, reason
            );
        }
    }
}
