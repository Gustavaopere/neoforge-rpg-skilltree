package dev.gustavopere.rpgskilltree.compendium.world;

import dev.gustavopere.rpgskilltree.compendium.api.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class DimensionProvider {
    private DimensionProvider() {}

    public static CompendiumEntry create(DimensionDescriptor descriptor) {
        List<CompendiumFact<?>> identity = List.of(
            fact("resource_location", descriptor.resourceLocation()),
            fact("source_mod_id", descriptor.sourceModId())
        );
        ArrayList<CompendiumRelation> relations = new ArrayList<>();
        descriptor.biomeIds().stream().sorted().forEach(id -> relations.add(new CompendiumRelation(
            CompendiumRelationType.RELATED_ENTRY,
            CompendiumEntryId.of(CompendiumEntryKind.BIOME, id), FactSource.REGISTRY, FactConfidence.EXACT
        )));
        descriptor.structureIds().stream().sorted().forEach(id -> relations.add(new CompendiumRelation(
            CompendiumRelationType.RELATED_ENTRY,
            CompendiumEntryId.of(CompendiumEntryKind.STRUCTURE, id), FactSource.REGISTRY, FactConfidence.DERIVED
        )));
        return new CompendiumEntry(
            CompendiumEntryId.of(CompendiumEntryKind.DIMENSION, descriptor.resourceLocation()),
            descriptor.sourceModId(), descriptor.translationKey(), Set.of("dimension"),
            List.of(new CompendiumSection("identity", identity)), relations,
            DiscoveryPolicy.OBSERVATION, VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            new CompendiumProvenance(FactSource.REGISTRY, "runtime:server_levels"), 1
        );
    }

    private static <T> CompendiumFact<T> fact(String key, T value) {
        return new CompendiumFact<>(key, value, null, FactSource.REGISTRY, FactConfidence.EXACT, FactVisibility.DISCOVERED_ONLY, null);
    }
}
