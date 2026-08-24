package dev.gustavopere.rpgskilltree.runtime.effects;

import dev.gustavopere.rpgskilltree.core.ModifierOperation;
import dev.gustavopere.rpgskilltree.core.NodeEffectResolver;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.data.NodeEffectCatalog;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public final class AttributeNodeEffectRuntime {
    private static final String A0088 = "rpgskilltree:combat/a0088";
    private static final Map<UUID, Integer> A0088_RANKS = new ConcurrentHashMap<>();
    private AttributeNodeEffectRuntime() {}

    public static void refresh(ServerPlayer player, ProgressionState state) {
        int nextHealthRank = state.passiveNodes().rank(A0088);
        Integer previousHealthRank = A0088_RANKS.put(player.getUUID(), nextHealthRank);
        boolean preserveHealthRatio = previousHealthRank == null ? nextHealthRank > 0 : previousHealthRank != nextHealthRank;
        double healthRatio = player.getMaxHealth() <= 0.0F ? 0.0D : player.getHealth() / player.getMaxHealth();
        for (var effect : NodeEffectCatalog.clearableAttributeEffects()) {
            var attributeId = ResourceLocation.parse(effect.attributeId());
            var holder = BuiltInRegistries.ATTRIBUTE.getHolder(attributeId).orElse(null);
            if (holder == null) continue;
            var instance = player.getAttribute(holder);
            if (instance == null) continue;
            instance.removeModifier(ResourceLocation.parse(effect.effectId()));
        }

        for (var effect : NodeEffectResolver.resolveAttributes(state.passiveNodes(), NodeEffectCatalog.attributeEffects())) {
            var attributeId = ResourceLocation.parse(effect.attributeId());
            var holder = BuiltInRegistries.ATTRIBUTE.getHolder(attributeId).orElse(null);
            if (holder == null) continue;
            var instance = player.getAttribute(holder);
            if (instance == null) continue;
            instance.addOrUpdateTransientModifier(new AttributeModifier(
                ResourceLocation.parse(effect.effectId()),
                effect.amount(),
                operation(effect.operation())
            ));
        }
        if (preserveHealthRatio) {
            player.setHealth((float)Math.min(player.getMaxHealth(), player.getMaxHealth() * healthRatio));
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
