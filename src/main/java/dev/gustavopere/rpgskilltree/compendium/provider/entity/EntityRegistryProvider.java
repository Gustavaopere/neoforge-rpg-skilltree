package dev.gustavopere.rpgskilltree.compendium.provider.entity;

import dev.gustavopere.rpgskilltree.compendium.entity.EntitySpeciesFacts;

public final class EntityRegistryProvider {
    private EntityRegistryProvider() {}

    public static EntitySpeciesFacts toSpeciesFacts(EntityRegistryDescriptor descriptor) {
        return new EntitySpeciesFacts(
            descriptor.resourceLocation(),
            descriptor.sourceModId(),
            descriptor.translationKey(),
            descriptor.gameplayCategories(),
            descriptor.mobCategory(),
            descriptor.hitboxWidth(),
            descriptor.hitboxHeight(),
            LivingEntityAttributeProvider.toFactValues(descriptor.defaultAttributes())
        );
    }
}
