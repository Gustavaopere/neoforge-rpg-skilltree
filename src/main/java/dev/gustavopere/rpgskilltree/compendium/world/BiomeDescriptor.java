package dev.gustavopere.rpgskilltree.compendium.world;

import java.util.Set;

public record BiomeDescriptor(
    String resourceLocation,
    String sourceModId,
    String translationKey,
    BiomeClimateFacts climate,
    Set<String> tagIds,
    Set<String> categoryIds,
    Set<String> dimensionIds,
    Set<String> structureIds
) {
    public BiomeDescriptor {
        resourceLocation = WorldDescriptorSupport.resource(resourceLocation, "resourceLocation");
        sourceModId = WorldDescriptorSupport.text(sourceModId, "sourceModId");
        translationKey = WorldDescriptorSupport.text(translationKey, "translationKey");
        climate = climate == null ? new BiomeClimateFacts(null, null) : climate;
        tagIds = WorldDescriptorSupport.resources(tagIds);
        categoryIds = WorldDescriptorSupport.texts(categoryIds);
        dimensionIds = WorldDescriptorSupport.resources(dimensionIds);
        structureIds = WorldDescriptorSupport.resources(structureIds);
    }
}
