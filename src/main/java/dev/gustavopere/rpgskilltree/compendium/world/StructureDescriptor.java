package dev.gustavopere.rpgskilltree.compendium.world;

import java.util.Set;

public record StructureDescriptor(
    String resourceLocation,
    String sourceModId,
    String translationKey,
    Set<String> biomeIds,
    Set<String> dimensionIds,
    Set<String> categoryIds,
    StructurePlacementSummary placement
) {
    public StructureDescriptor {
        resourceLocation = WorldDescriptorSupport.resource(resourceLocation, "resourceLocation");
        sourceModId = WorldDescriptorSupport.text(sourceModId, "sourceModId");
        translationKey = WorldDescriptorSupport.text(translationKey, "translationKey");
        biomeIds = WorldDescriptorSupport.resources(biomeIds);
        dimensionIds = WorldDescriptorSupport.resources(dimensionIds);
        categoryIds = WorldDescriptorSupport.texts(categoryIds);
        placement = placement == null ? new StructurePlacementSummary(null, null, null) : placement;
    }
}
