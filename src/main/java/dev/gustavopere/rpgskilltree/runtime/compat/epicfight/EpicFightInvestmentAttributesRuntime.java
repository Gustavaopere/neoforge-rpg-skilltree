package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import com.mojang.logging.LogUtils;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentBonuses;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics.Category;
import dev.gustavopere.rpgskilltree.runtime.effects.AttributeEffectDiagnostics;
import java.util.Objects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.slf4j.Logger;

/**
 * Version-gated, server-authoritative Epic Fight native stamina regeneration
 * modifier. Does not generate stamina, modify a capability or touch ParCool.
 */
public final class EpicFightInvestmentAttributesRuntime {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ResourceLocation TARGET =
        ResourceLocation.parse(EpicFightInvestmentAttributePolicy.target());
    private static final ResourceLocation MODIFIER_ID =
        ResourceLocation.parse(EpicFightInvestmentAttributePolicy.modifierId());

    private EpicFightInvestmentAttributesRuntime() {}

    public static void refresh(ServerPlayer player, AttributeRanks ranks) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(ranks, "ranks");
        if (player.level().isClientSide()) throw new IllegalArgumentException("server player required");

        boolean active = EpicFightInvestmentAttributePolicy.operational(
            OptionalIntegrations.isLoaded(OptionalIntegrations.Provider.EPIC_FIGHT),
            OptionalIntegrations.version(OptionalIntegrations.Provider.EPIC_FIGHT)
        );

        var holder = BuiltInRegistries.ATTRIBUTE.getHolder(TARGET).orElse(null);
        if (holder == null) {
            if (active) unavailable(AttributeEffectDiagnostics.Reason.MISSING_REGISTRY_TARGET);
            return;
        }

        var instance = player.getAttribute(holder);
        if (instance == null) {
            if (active) unavailable(AttributeEffectDiagnostics.Reason.MISSING_PLAYER_ATTRIBUTE);
            return;
        }

        // Remove even when version is now unsupported or the invested rank is zero.
        instance.removeModifier(MODIFIER_ID);
        if (!active) return;

        double bonus = AttributeInvestmentBonuses.value(
            EpicFightInvestmentAttributePolicy.benefit(),
            ranks.rank(EpicFightInvestmentAttributePolicy.benefit().attribute()));
        if (bonus <= 0.0) return;

        instance.addOrUpdateTransientModifier(new AttributeModifier(
            MODIFIER_ID, bonus, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    private static void unavailable(AttributeEffectDiagnostics.Reason reason) {
        if (AttributeEffectDiagnostics.report(MODIFIER_ID.toString(), TARGET.toString(), reason)) {
            RuntimeDiagnostics.warn(LOGGER, Category.EFFECTS, "epicfight_stamina_investment_unavailable",
                "Epic Fight 21.17.3.1 stamina investment cannot target {} ({}); effect disabled",
                TARGET, reason);
        }
    }
}
