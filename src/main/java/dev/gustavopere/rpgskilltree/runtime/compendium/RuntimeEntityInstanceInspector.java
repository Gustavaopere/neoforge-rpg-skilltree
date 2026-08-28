package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.entity.EntityEffectSnapshot;
import dev.gustavopere.rpgskilltree.compendium.entity.EntityFactKeys;
import dev.gustavopere.rpgskilltree.compendium.entity.EntityInstanceSnapshot;
import dev.gustavopere.rpgskilltree.compendium.provider.entity.EntityInspectionPolicy;
import dev.gustavopere.rpgskilltree.compendium.provider.entity.EntityInstanceInspector;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;

/** Server/common adapter that projects one already-existing entity into the safe inspection DTO. */
public final class RuntimeEntityInstanceInspector {
    private RuntimeEntityInstanceInspector() {}

    public static Optional<EntityInstanceSnapshot> inspect(
        Entity entity,
        EntityInspectionPolicy policy,
        double distanceSquared,
        boolean hasLineOfSight
    ) {
        if (entity == null || policy == null || !policy.allows(distanceSquared, hasLineOfSight)) {
            return Optional.empty();
        }

        ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (entityId == null) return Optional.empty();

        Double currentHealth = null;
        Double maxHealth = null;
        Map<String, Double> attributes = Map.of();
        List<EntityEffectSnapshot> effects = List.of();
        if (entity instanceof LivingEntity living) {
            currentHealth = (double) living.getHealth();
            maxHealth = (double) living.getMaxHealth();
            attributes = currentAttributes(living);
            effects = activeEffects(living);
        }

        Integer ageTicks = entity instanceof AgeableMob ageable ? ageable.getAge() : null;
        Boolean tame = null;
        String ownerId = null;
        Boolean sitting = null;
        if (entity instanceof TamableAnimal tamable) {
            tame = tamable.isTame();
            ownerId = tamable.getOwnerUUID() == null ? null : tamable.getOwnerUUID().toString();
            sitting = tamable.isInSittingPose();
        }

        Boolean breedReady = entity instanceof Animal animal ? animal.isInLove() : null;
        boolean noAi = entity instanceof Mob mob && mob.isNoAi();
        boolean leashed = entity instanceof Mob mob && mob.isLeashed();

        EntityInstanceInspector.Input input = new EntityInstanceInspector.Input(
            entityId.toString(),
            entity.getBbWidth(),
            entity.getBbHeight(),
            currentHealth,
            maxHealth,
            attributes,
            ageTicks,
            effects,
            tame,
            ownerId,
            sitting,
            breedReady,
            noAi,
            entity.isInvulnerable(),
            entity.isSilent(),
            leashed
        );
        return Optional.of(EntityInstanceInspector.inspect(input));
    }

    private static Map<String, Double> currentAttributes(LivingEntity living) {
        LinkedHashMap<String, Double> values = new LinkedHashMap<>();
        addIfPresent(values, living, EntityFactKeys.MAX_HEALTH, Attributes.MAX_HEALTH);
        addIfPresent(values, living, EntityFactKeys.ARMOR, Attributes.ARMOR);
        addIfPresent(values, living, EntityFactKeys.ARMOR_TOUGHNESS, Attributes.ARMOR_TOUGHNESS);
        addIfPresent(values, living, EntityFactKeys.ATTACK_DAMAGE, Attributes.ATTACK_DAMAGE);
        addIfPresent(values, living, EntityFactKeys.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED);
        addIfPresent(values, living, EntityFactKeys.FLYING_SPEED, Attributes.FLYING_SPEED);
        addIfPresent(values, living, EntityFactKeys.KNOCKBACK_RESISTANCE, Attributes.KNOCKBACK_RESISTANCE);
        addIfPresent(values, living, EntityFactKeys.ATTACK_KNOCKBACK, Attributes.ATTACK_KNOCKBACK);
        addIfPresent(values, living, EntityFactKeys.FOLLOW_RANGE, Attributes.FOLLOW_RANGE);
        addIfPresent(values, living, EntityFactKeys.JUMP_STRENGTH, Attributes.JUMP_STRENGTH);
        return Map.copyOf(values);
    }

    private static void addIfPresent(
        Map<String, Double> destination,
        LivingEntity living,
        String factKey,
        Holder<Attribute> attribute
    ) {
        if (living.getAttribute(attribute) != null) {
            destination.put(factKey, living.getAttributeValue(attribute));
        }
    }

    private static List<EntityEffectSnapshot> activeEffects(LivingEntity living) {
        ArrayList<EntityEffectSnapshot> effects = new ArrayList<>();
        for (MobEffectInstance effect : living.getActiveEffects()) {
            ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey(effect.getEffect().value());
            if (id == null) continue;
            effects.add(new EntityEffectSnapshot(
                id.toString(),
                effect.getAmplifier(),
                Math.max(0L, effect.getDuration()),
                effect.isAmbient(),
                effect.isVisible()
            ));
        }
        effects.sort((left, right) -> left.effectId().compareTo(right.effectId()));
        return List.copyOf(effects);
    }
}
