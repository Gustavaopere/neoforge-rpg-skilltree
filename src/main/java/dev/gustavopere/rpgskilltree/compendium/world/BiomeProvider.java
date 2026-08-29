package dev.gustavopere.rpgskilltree.compendium.world;

import dev.gustavopere.rpgskilltree.compendium.api.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class BiomeProvider {
    private BiomeProvider() {}

    public static CompendiumEntry create(BiomeDescriptor descriptor) {
        LinkedHashSet<String> categories = new LinkedHashSet<>();
        categories.add("biome");
        categories.addAll(descriptor.categoryIds());

        List<CompendiumFact<?>> identity = List.of(
            fact("resource_location", descriptor.resourceLocation(), null),
            fact("source_mod_id", descriptor.sourceModId(), null)
        );
        ArrayList<CompendiumFact<?>> climate = new ArrayList<>();
        if (descriptor.climate().vanillaTemperature() != null) {
            climate.add(fact("vanilla_temperature", descriptor.climate().vanillaTemperature(), "vanilla_climate"));
        }
        if (descriptor.climate().vanillaDownfall() != null) {
            climate.add(fact("vanilla_downfall", descriptor.climate().vanillaDownfall(), "vanilla_climate"));
        }
        ArrayList<CompendiumFact<?>> tags = new ArrayList<>();
        descriptor.tagIds().stream().sorted().forEach(id -> tags.add(fact("tag:" + id, true, null)));

        ArrayList<CompendiumSection> sections = new ArrayList<>();
        sections.add(new CompendiumSection("identity", identity));
        if (!climate.isEmpty()) sections.add(new CompendiumSection("vanilla_climate", climate));
        if (!tags.isEmpty()) sections.add(new CompendiumSection("tags", tags));

        ArrayList<CompendiumRelation> relations = new ArrayList<>();
        descriptor.dimensionIds().stream().sorted().forEach(id -> relations.add(new CompendiumRelation(
            CompendiumRelationType.BELONGS_TO_DIMENSION,
            CompendiumEntryId.of(CompendiumEntryKind.DIMENSION, id), FactSource.REGISTRY, FactConfidence.EXACT
        )));
        descriptor.structureIds().stream().sorted().forEach(id -> relations.add(new CompendiumRelation(
            CompendiumRelationType.RELATED_ENTRY,
            CompendiumEntryId.of(CompendiumEntryKind.STRUCTURE, id), FactSource.REGISTRY, FactConfidence.EXACT
        )));

        return new CompendiumEntry(
            CompendiumEntryId.of(CompendiumEntryKind.BIOME, descriptor.resourceLocation()),
            descriptor.sourceModId(), descriptor.translationKey(), Set.copyOf(categories), sections, relations,
            DiscoveryPolicy.OBSERVATION, VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            new CompendiumProvenance(FactSource.REGISTRY, "runtime:biome_registry"), 1
        );
    }

    private static <T> CompendiumFact<T> fact(String key, T value, String unit) {
        return new CompendiumFact<>(key, value, unit, FactSource.REGISTRY, FactConfidence.EXACT, FactVisibility.DISCOVERED_ONLY, null);
    }
}
