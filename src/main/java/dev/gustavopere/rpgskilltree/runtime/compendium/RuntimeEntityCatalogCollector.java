package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.entity.EntityBaseAttribute;
import dev.gustavopere.rpgskilltree.compendium.entity.EntityGameplayCategory;
import dev.gustavopere.rpgskilltree.compendium.entity.EntitySpeciesEntryFactory;
import dev.gustavopere.rpgskilltree.compendium.provider.entity.EntityRegistryDescriptor;
import dev.gustavopere.rpgskilltree.compendium.provider.entity.EntityRegistryProvider;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;

/**
 * Builds generic entity Compendium entries from finalized registries and default attributes.
 * This collector never constructs entity instances.
 */
public final class RuntimeEntityCatalogCollector {
    private RuntimeEntityCatalogCollector() {}

    public static List<CompendiumEntry> collectEntries() {
        ArrayList<CompendiumEntry> entries = new ArrayList<>();
        for (Map.Entry<ResourceKey<EntityType<?>>, EntityType<?>> registryEntry : BuiltInRegistries.ENTITY_TYPE.entrySet()) {
            ResourceLocation id = registryEntry.getKey().location();
            EntityType<?> type = registryEntry.getValue();
            EntityDimensions dimensions = type.getDimensions();
            EntityRegistryDescriptor descriptor = new EntityRegistryDescriptor(
                id.toString(),
                id.getNamespace(),
                type.getDescriptionId(),
                type.getCategory().getName(),
                dimensions.width(),
                dimensions.height(),
                categories(type.getCategory()),
                defaultAttributes(type)
            );
            entries.add(EntitySpeciesEntryFactory.create(EntityRegistryProvider.toSpeciesFacts(descriptor)));
        }
        entries.sort(Comparator.comparing(entry -> entry.id().serializedId()));
        return List.copyOf(entries);
    }

    private static Map<EntityBaseAttribute, Double> defaultAttributes(EntityType<?> type) {
        if (!DefaultAttributes.hasSupplier(type)) return Map.of();
        AttributeSupplier supplier = DefaultAttributes.getSupplier(asLivingType(type));
        if (supplier == null) return Map.of();

        EnumMap<EntityBaseAttribute, Double> values = new EnumMap<>(EntityBaseAttribute.class);
        addIfPresent(values, supplier, EntityBaseAttribute.MAX_HEALTH, Attributes.MAX_HEALTH);
        addIfPresent(values, supplier, EntityBaseAttribute.ARMOR, Attributes.ARMOR);
        addIfPresent(values, supplier, EntityBaseAttribute.ARMOR_TOUGHNESS, Attributes.ARMOR_TOUGHNESS);
        addIfPresent(values, supplier, EntityBaseAttribute.ATTACK_DAMAGE, Attributes.ATTACK_DAMAGE);
        addIfPresent(values, supplier, EntityBaseAttribute.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED);
        addIfPresent(values, supplier, EntityBaseAttribute.FLYING_SPEED, Attributes.FLYING_SPEED);
        addIfPresent(values, supplier, EntityBaseAttribute.KNOCKBACK_RESISTANCE, Attributes.KNOCKBACK_RESISTANCE);
        addIfPresent(values, supplier, EntityBaseAttribute.ATTACK_KNOCKBACK, Attributes.ATTACK_KNOCKBACK);
        addIfPresent(values, supplier, EntityBaseAttribute.FOLLOW_RANGE, Attributes.FOLLOW_RANGE);
        addIfPresent(values, supplier, EntityBaseAttribute.JUMP_STRENGTH, Attributes.JUMP_STRENGTH);
        return Map.copyOf(values);
    }

    private static void addIfPresent(
        Map<EntityBaseAttribute, Double> destination,
        AttributeSupplier supplier,
        EntityBaseAttribute key,
        Holder<Attribute> attribute
    ) {
        if (supplier.hasAttribute(attribute)) {
            destination.put(key, supplier.getBaseValue(attribute));
        }
    }

    @SuppressWarnings("unchecked")
    private static EntityType<? extends LivingEntity> asLivingType(EntityType<?> type) {
        return (EntityType<? extends LivingEntity>) type;
    }

    private static Set<EntityGameplayCategory> categories(MobCategory category) {
        return switch (category) {
            case MONSTER -> Set.of(EntityGameplayCategory.HOSTIL);
            case CREATURE -> Set.of(EntityGameplayCategory.PASSIVO);
            case WATER_CREATURE, WATER_AMBIENT, UNDERGROUND_WATER_CREATURE, AXOLOTLS ->
                Set.of(EntityGameplayCategory.AQUATICO);
            case AMBIENT -> Set.of(EntityGameplayCategory.NEUTRO);
            case MISC -> Set.of(EntityGameplayCategory.OUTRO);
        };
    }
}
