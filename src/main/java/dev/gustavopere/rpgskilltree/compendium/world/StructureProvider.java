package dev.gustavopere.rpgskilltree.compendium.world;

import dev.gustavopere.rpgskilltree.compendium.api.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class StructureProvider {
    private StructureProvider() {}

    public static CompendiumEntry create(StructureDescriptor descriptor) {
        LinkedHashSet<String> categories = new LinkedHashSet<>();
        categories.add("structure");
        categories.addAll(descriptor.categoryIds());

        List<CompendiumFact<?>> identity = List.of(
            fact("resource_location", descriptor.resourceLocation()),
            fact("source_mod_id", descriptor.sourceModId())
        );
        ArrayList<CompendiumSection> sections = new ArrayList<>();
        sections.add(new CompendiumSection("identity", identity));
        if (!descriptor.placement().empty()) {
            ArrayList<CompendiumFact<?>> placement = new ArrayList<>();
            if (descriptor.placement().placementType() != null) placement.add(fact("placement_type", descriptor.placement().placementType()));
            if (descriptor.placement().spacing() != null) placement.add(fact("spacing", descriptor.placement().spacing()));
            if (descriptor.placement().separation() != null) placement.add(fact("separation", descriptor.placement().separation()));
            sections.add(new CompendiumSection("placement", placement));
        }

        ArrayList<CompendiumRelation> relations = new ArrayList<>();
        descriptor.biomeIds().stream().sorted().forEach(id -> relations.add(new CompendiumRelation(
            CompendiumRelationType.RELATED_ENTRY,
            CompendiumEntryId.of(CompendiumEntryKind.BIOME, id), FactSource.REGISTRY, FactConfidence.EXACT
        )));
        descriptor.dimensionIds().stream().sorted().forEach(id -> relations.add(new CompendiumRelation(
            CompendiumRelationType.BELONGS_TO_DIMENSION,
            CompendiumEntryId.of(CompendiumEntryKind.DIMENSION, id), FactSource.REGISTRY, FactConfidence.DERIVED
        )));

        return new CompendiumEntry(
            CompendiumEntryId.of(CompendiumEntryKind.STRUCTURE, descriptor.resourceLocation()),
            descriptor.sourceModId(), descriptor.translationKey(), Set.copyOf(categories), sections, relations,
            DiscoveryPolicy.OBSERVATION, VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            new CompendiumProvenance(FactSource.REGISTRY, "runtime:structure_registry"), 1
        );
    }

    private static <T> CompendiumFact<T> fact(String key, T value) {
        return new CompendiumFact<>(key, value, null, FactSource.REGISTRY, FactConfidence.EXACT, FactVisibility.DISCOVERED_ONLY, null);
    }
}
