package dev.gustavopere.rpgskilltree.compendium.world;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.HashSet;
import java.util.Set;

public record WorldCatalogCoverage(
    Set<String> missingBiomeIds,
    Set<String> missingStructureIds,
    Set<String> missingDimensionIds,
    Set<String> unexpectedBiomeIds,
    Set<String> unexpectedStructureIds,
    Set<String> unexpectedDimensionIds
) {
    public WorldCatalogCoverage {
        missingBiomeIds = Set.copyOf(missingBiomeIds);
        missingStructureIds = Set.copyOf(missingStructureIds);
        missingDimensionIds = Set.copyOf(missingDimensionIds);
        unexpectedBiomeIds = Set.copyOf(unexpectedBiomeIds);
        unexpectedStructureIds = Set.copyOf(unexpectedStructureIds);
        unexpectedDimensionIds = Set.copyOf(unexpectedDimensionIds);
    }

    public static WorldCatalogCoverage compare(Set<String> biomeIds, Set<String> structureIds, Set<String> dimensionIds,
                                                WorldCatalogSnapshot snapshot) {
        Set<String> actualBiomes = new HashSet<>();
        Set<String> actualStructures = new HashSet<>();
        Set<String> actualDimensions = new HashSet<>();
        snapshot.entries().forEach(entry -> {
            if (entry.id().kind() == CompendiumEntryKind.BIOME) actualBiomes.add(entry.id().resourceLocation());
            if (entry.id().kind() == CompendiumEntryKind.STRUCTURE) actualStructures.add(entry.id().resourceLocation());
            if (entry.id().kind() == CompendiumEntryKind.DIMENSION) actualDimensions.add(entry.id().resourceLocation());
        });
        return new WorldCatalogCoverage(
            difference(biomeIds, actualBiomes), difference(structureIds, actualStructures), difference(dimensionIds, actualDimensions),
            difference(actualBiomes, biomeIds), difference(actualStructures, structureIds), difference(actualDimensions, dimensionIds)
        );
    }

    public boolean complete() {
        return missingBiomeIds.isEmpty() && missingStructureIds.isEmpty() && missingDimensionIds.isEmpty()
            && unexpectedBiomeIds.isEmpty() && unexpectedStructureIds.isEmpty() && unexpectedDimensionIds.isEmpty();
    }

    private static Set<String> difference(Set<String> left, Set<String> right) {
        HashSet<String> result = new HashSet<>(left == null ? Set.of() : left);
        result.removeAll(right == null ? Set.of() : right);
        return Set.copyOf(result);
    }
}
