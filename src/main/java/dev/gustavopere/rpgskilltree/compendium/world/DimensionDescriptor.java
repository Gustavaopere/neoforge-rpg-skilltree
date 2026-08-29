package dev.gustavopere.rpgskilltree.compendium.world;

import java.util.Set;

public record DimensionDescriptor(
    String resourceLocation,
    String sourceModId,
    String translationKey,
    Set<String> biomeIds,
    Set<String> structureIds
) {
    public DimensionDescriptor {
        resourceLocation = WorldDescriptorSupport.resource(resourceLocation, "resourceLocation");
        sourceModId = WorldDescriptorSupport.text(sourceModId, "sourceModId");
        translationKey = WorldDescriptorSupport.text(translationKey, "translationKey");
        biomeIds = WorldDescriptorSupport.resources(biomeIds);
        structureIds = WorldDescriptorSupport.resources(structureIds);
    }
}
