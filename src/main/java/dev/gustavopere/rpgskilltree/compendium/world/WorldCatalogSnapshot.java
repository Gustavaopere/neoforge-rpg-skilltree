package dev.gustavopere.rpgskilltree.compendium.world;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import java.util.*;

public final class WorldCatalogSnapshot {
    private final List<CompendiumEntry> entries;
    private final Map<CompendiumEntryId, CompendiumEntry> byId;
    private final Map<String, Set<String>> biomesByDimension;
    private final Map<String, Set<String>> structuresByBiome;
    private final Map<String, Set<String>> dimensionsByStructure;

    private WorldCatalogSnapshot(List<CompendiumEntry> entries, Map<CompendiumEntryId, CompendiumEntry> byId,
                                 Map<String, Set<String>> biomesByDimension,
                                 Map<String, Set<String>> structuresByBiome,
                                 Map<String, Set<String>> dimensionsByStructure) {
        this.entries = List.copyOf(entries);
        this.byId = Map.copyOf(byId);
        this.biomesByDimension = immutableMapOfSets(biomesByDimension);
        this.structuresByBiome = immutableMapOfSets(structuresByBiome);
        this.dimensionsByStructure = immutableMapOfSets(dimensionsByStructure);
    }

    public static WorldCatalogSnapshot empty() { return fromEntries(List.of()); }

    public static WorldCatalogSnapshot fromEntries(Collection<CompendiumEntry> source) {
        ArrayList<CompendiumEntry> ordered = new ArrayList<>(source == null ? List.of() : source);
        ordered.sort(Comparator.comparing(e -> e.id().serializedId()));
        LinkedHashMap<CompendiumEntryId, CompendiumEntry> byId = new LinkedHashMap<>();
        for (CompendiumEntry entry : ordered) {
            Objects.requireNonNull(entry, "world entry");
            if (entry.id().kind() != CompendiumEntryKind.BIOME && entry.id().kind() != CompendiumEntryKind.STRUCTURE
                && entry.id().kind() != CompendiumEntryKind.DIMENSION) {
                throw new IllegalArgumentException("unsupported world entry kind: " + entry.id().kind());
            }
            if (byId.putIfAbsent(entry.id(), entry) != null) {
                throw new IllegalArgumentException("duplicate world entry: " + entry.id().serializedId());
            }
        }

        Map<String, Set<String>> biomesByDimension = new HashMap<>();
        Map<String, Set<String>> structuresByBiome = new HashMap<>();
        Map<String, Set<String>> dimensionsByStructure = new HashMap<>();

        for (CompendiumEntry entry : ordered) {
            if (entry.id().kind() == CompendiumEntryKind.DIMENSION) {
                String dimension = entry.id().resourceLocation();
                for (CompendiumRelation relation : entry.relations()) {
                    CompendiumEntryId target = relation.target().entryId();
                    if (target == null) continue;
                    if (target.kind() == CompendiumEntryKind.BIOME) add(biomesByDimension, dimension, target.resourceLocation());
                    if (target.kind() == CompendiumEntryKind.STRUCTURE) add(dimensionsByStructure, target.resourceLocation(), dimension);
                }
            } else if (entry.id().kind() == CompendiumEntryKind.STRUCTURE) {
                String structure = entry.id().resourceLocation();
                for (CompendiumRelation relation : entry.relations()) {
                    CompendiumEntryId target = relation.target().entryId();
                    if (target == null) continue;
                    if (target.kind() == CompendiumEntryKind.BIOME) add(structuresByBiome, target.resourceLocation(), structure);
                    if (target.kind() == CompendiumEntryKind.DIMENSION) add(dimensionsByStructure, structure, target.resourceLocation());
                }
            } else if (entry.id().kind() == CompendiumEntryKind.BIOME) {
                String biome = entry.id().resourceLocation();
                for (CompendiumRelation relation : entry.relations()) {
                    CompendiumEntryId target = relation.target().entryId();
                    if (target == null) continue;
                    if (target.kind() == CompendiumEntryKind.DIMENSION) add(biomesByDimension, target.resourceLocation(), biome);
                    if (target.kind() == CompendiumEntryKind.STRUCTURE) add(structuresByBiome, biome, target.resourceLocation());
                }
            }
        }
        return new WorldCatalogSnapshot(ordered, byId, biomesByDimension, structuresByBiome, dimensionsByStructure);
    }

    public List<CompendiumEntry> entries() { return entries; }
    public Optional<CompendiumEntry> entry(CompendiumEntryId id) { return Optional.ofNullable(byId.get(id)); }
    public Set<String> biomeIdsForDimension(String id) { return biomesByDimension.getOrDefault(id, Set.of()); }
    public Set<String> structureIdsForBiome(String id) { return structuresByBiome.getOrDefault(id, Set.of()); }
    public Set<String> dimensionIdsForStructure(String id) { return dimensionsByStructure.getOrDefault(id, Set.of()); }

    private static void add(Map<String, Set<String>> map, String key, String value) {
        map.computeIfAbsent(key, ignored -> new TreeSet<>()).add(value);
    }
    private static Map<String, Set<String>> immutableMapOfSets(Map<String, Set<String>> source) {
        LinkedHashMap<String, Set<String>> result = new LinkedHashMap<>();
        source.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e -> result.put(e.getKey(), Set.copyOf(e.getValue())));
        return Collections.unmodifiableMap(result);
    }
}
