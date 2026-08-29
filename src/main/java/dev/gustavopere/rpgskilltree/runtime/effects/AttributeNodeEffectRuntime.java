package dev.gustavopere.rpgskilltree.runtime.effects;

import com.mojang.logging.LogUtils;
import dev.gustavopere.rpgskilltree.core.ModifierOperation;
import dev.gustavopere.rpgskilltree.core.NodeEffectResolver;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.data.NodeEffectCatalog;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics;
import dev.gustavopere.rpgskilltree.runtime.diagnostics.RuntimeDiagnostics.Category;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.slf4j.Logger;

public final class AttributeNodeEffectRuntime {
    private static final Logger LOGGER = LogUtils.getLogger();

    private AttributeNodeEffectRuntime() {}

    public static void refresh(ServerPlayer player, ProgressionState state) {
        for (var effect : NodeEffectCatalog.clearableAttributeEffects()) {
            var attributeId = ResourceLocation.parse(effect.attributeId());
            var holder = BuiltInRegistries.ATTRIBUTE.getHolder(attributeId).orElse(null);
            if (holder == null) {
                reportUnavailable(
                    effect.effectId(),
                    effect.attributeId(),
                    AttributeEffectDiagnostics.Reason.MISSING_REGISTRY_TARGET
                );
                continue;
            }
            var instance = player.getAttribute(holder);
            if (instance == null) {
                reportUnavailable(
                    effect.effectId(),
                    effect.attributeId(),
                    AttributeEffectDiagnostics.Reason.MISSING_PLAYER_ATTRIBUTE
                );
                continue;
            }
            instance.removeModifier(ResourceLocation.parse(effect.effectId()));
        }

        for (var effect : NodeEffectResolver.resolveAttributes(state.passiveNodes(), NodeEffectCatalog.attributeEffects())) {
            var attributeId = ResourceLocation.parse(effect.attributeId());
            var holder = BuiltInRegistries.ATTRIBUTE.getHolder(attributeId).orElse(null);
            if (holder == null) {
                reportUnavailable(
                    effect.effectId(),
                    effect.attributeId(),
                    AttributeEffectDiagnostics.Reason.MISSING_REGISTRY_TARGET
                );
                continue;
            }
            var instance = player.getAttribute(holder);
            if (instance == null) {
                reportUnavailable(
                    effect.effectId(),
                    effect.attributeId(),
                    AttributeEffectDiagnostics.Reason.MISSING_PLAYER_ATTRIBUTE
                );
                continue;
            }
            instance.addOrUpdateTransientModifier(new AttributeModifier(
                ResourceLocation.parse(effect.effectId()),
                effect.amount(),
                operation(effect.operation())
            ));
        }
    }

    private static void reportUnavailable(
        String effectId,
        String attributeId,
        AttributeEffectDiagnostics.Reason reason
    ) {
        if (AttributeEffectDiagnostics.report(effectId, attributeId, reason)) {
            RuntimeDiagnostics.warn(
                LOGGER,
                Category.EFFECTS,
                "attribute_effect_unavailable",
                "Node effect {} targets unavailable attribute {} ({}); the effect will not be applied",
                effectId,
                attributeId,
                reason
            );
        }
    }

    private static AttributeModifier.Operation operation(ModifierOperation operation) {
        return switch (operation) {
            case ADD_FLAT -> AttributeModifier.Operation.ADD_VALUE;
            case ADD_PERCENT_BASE -> AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
            case MULTIPLY_TOTAL -> AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL;
            case OVERRIDE -> throw new IllegalArgumentException("OVERRIDE is not supported for attribute node effects");
        };
    }
}
