package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CanonicalStatKey;
import dev.gustavopere.rpgskilltree.core.EntityEffectiveStatsSnapshot;
import dev.gustavopere.rpgskilltree.core.EntityScalingState;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/** Applies the persisted vanilla Effective Stats layer without rerolling or mutating provider base values. */
public final class EntityEffectiveStatsRuntime {
    private static final Map<CanonicalStatKey, Holder<Attribute>> VANILLA_ATTRIBUTES = vanillaAttributes();

    private EntityEffectiveStatsRuntime() {}

    /**
     * Reconciles all common vanilla scaling modifiers from the persisted canonical state.
     *
     * <p>Every known modifier is removed first and then recreated with the same stable id. This makes
     * repeated refreshes idempotent and also clears stale RPG scaling from legacy/changed states.
     * Permanent modifiers are used because entity attributes are themselves persisted by Minecraft;
     * the canonical state remains the authority and this method deterministically reconciles them on load.</p>
     */
    public static void refresh(LivingEntity entity, EntityScalingState state) {
        Objects.requireNonNull(entity, "entity");
        Objects.requireNonNull(state, "state");

        EntityEffectiveStatsSnapshot snapshot = state.effectiveStats().orElse(null);
        for (Map.Entry<CanonicalStatKey, Holder<Attribute>> mapping : VANILLA_ATTRIBUTES.entrySet()) {
            CanonicalStatKey statKey = mapping.getKey();
            var instance = entity.getAttribute(mapping.getValue());
            if (instance == null) continue;

            ResourceLocation modifierId = modifierId(statKey);
            instance.removeModifier(modifierId);

            if (snapshot == null) continue;
            BigDecimal target = snapshot.values().get(statKey);
            if (target == null) continue;

            double targetValue = finiteDouble(target, statKey, "target");
            double delta = targetValue - instance.getBaseValue();
            if (!Double.isFinite(delta)) {
                throw new IllegalArgumentException(
                    "effective stat delta is not finite for " + statKey.serializedId()
                );
            }

            instance.addOrReplacePermanentModifier(new AttributeModifier(
                modifierId,
                delta,
                AttributeModifier.Operation.ADD_VALUE
            ));
        }
    }

    public static ResourceLocation modifierId(CanonicalStatKey statKey) {
        Objects.requireNonNull(statKey, "statKey");
        return ResourceLocation.fromNamespaceAndPath(
            "rpgskilltree",
            "entity_scaling/" + statKey.path()
        );
    }

    private static double finiteDouble(BigDecimal value, CanonicalStatKey statKey, String label) {
        double converted = value.doubleValue();
        if (!Double.isFinite(converted)) {
            throw new IllegalArgumentException(
                "effective stat " + label + " is not finite for " + statKey.serializedId()
            );
        }
        return converted;
    }

    private static Map<CanonicalStatKey, Holder<Attribute>> vanillaAttributes() {
        LinkedHashMap<CanonicalStatKey, Holder<Attribute>> mappings = new LinkedHashMap<>();
        mappings.put(CanonicalStatKey.of("minecraft:max_health"), Attributes.MAX_HEALTH);
        mappings.put(CanonicalStatKey.of("minecraft:armor"), Attributes.ARMOR);
        mappings.put(CanonicalStatKey.of("minecraft:armor_toughness"), Attributes.ARMOR_TOUGHNESS);
        mappings.put(CanonicalStatKey.of("minecraft:attack_damage"), Attributes.ATTACK_DAMAGE);
        mappings.put(CanonicalStatKey.of("minecraft:attack_speed"), Attributes.ATTACK_SPEED);
        mappings.put(CanonicalStatKey.of("minecraft:movement_speed"), Attributes.MOVEMENT_SPEED);
        mappings.put(CanonicalStatKey.of("minecraft:knockback_resistance"), Attributes.KNOCKBACK_RESISTANCE);
        mappings.put(CanonicalStatKey.of("minecraft:luck"), Attributes.LUCK);
        return Map.copyOf(mappings);
    }
}
