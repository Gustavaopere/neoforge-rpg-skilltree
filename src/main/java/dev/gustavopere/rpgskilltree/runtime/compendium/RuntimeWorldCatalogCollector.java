package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.world.BiomeClimateFacts;
import dev.gustavopere.rpgskilltree.compendium.world.BiomeDescriptor;
import dev.gustavopere.rpgskilltree.compendium.world.BiomeProvider;
import dev.gustavopere.rpgskilltree.compendium.world.DimensionDescriptor;
import dev.gustavopere.rpgskilltree.compendium.world.DimensionProvider;
import dev.gustavopere.rpgskilltree.compendium.world.StructureDescriptor;
import dev.gustavopere.rpgskilltree.compendium.world.StructurePlacementSummary;
import dev.gustavopere.rpgskilltree.compendium.world.StructureProvider;
import dev.gustavopere.rpgskilltree.compendium.world.WorldCatalogSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

/** Collects Stage 10.08 world facts from server-owned dynamic registries and loaded levels. */
public final class RuntimeWorldCatalogCollector {
    private RuntimeWorldCatalogCollector() {}

    public static WorldCatalogSnapshot collect(MinecraftServer server) {
        Registry<Biome> biomeRegistry = server.registryAccess().registryOrThrow(Registries.BIOME);
        Registry<Structure> structureRegistry = server.registryAccess().registryOrThrow(Registries.STRUCTURE);

        Map<String, Set<String>> biomesByDimension = collectDimensionBiomes(server, biomeRegistry);
        Map<String, Set<String>> dimensionsByBiome = invert(biomesByDimension);
        Map<String, Set<String>> biomesByStructure = collectStructureBiomes(structureRegistry, biomeRegistry);
        Map<String, Set<String>> structuresByBiome = invert(biomesByStructure);
        Map<String, Set<String>> dimensionsByStructure = deriveStructureDimensions(biomesByStructure, biomesByDimension);
        Map<String, Set<String>> structuresByDimension = invert(dimensionsByStructure);

        ArrayList<CompendiumEntry> entries = new ArrayList<>();
        biomeRegistry.entrySet().stream()
            .sorted(Map.Entry.comparingByKey((a, b) -> a.location().toString().compareTo(b.location().toString())))
            .forEach(entry -> {
                ResourceLocation id = entry.getKey().location();
                Biome biome = entry.getValue();
                entries.add(BiomeProvider.create(new BiomeDescriptor(
                    id.toString(), id.getNamespace(), translationKey("biome", id),
                    new BiomeClimateFacts(
                        (double) biome.getBaseTemperature(),
                        (double) biome.getModifiedClimateSettings().downfall()
                    ),
                    Set.of(), Set.of(),
                    dimensionsByBiome.getOrDefault(id.toString(), Set.of()),
                    structuresByBiome.getOrDefault(id.toString(), Set.of())
                )));
            });

        structureRegistry.entrySet().stream()
            .sorted(Map.Entry.comparingByKey((a, b) -> a.location().toString().compareTo(b.location().toString())))
            .forEach(entry -> {
                ResourceLocation id = entry.getKey().location();
                entries.add(StructureProvider.create(new StructureDescriptor(
                    id.toString(), id.getNamespace(), translationKey("structure", id),
                    biomesByStructure.getOrDefault(id.toString(), Set.of()),
                    dimensionsByStructure.getOrDefault(id.toString(), Set.of()),
                    Set.of(), new StructurePlacementSummary(null, null, null)
                )));
            });

        for (ServerLevel level : server.getAllLevels()) {
            ResourceLocation id = level.dimension().location();
            entries.add(DimensionProvider.create(new DimensionDescriptor(
                id.toString(), id.getNamespace(), translationKey("dimension", id),
                biomesByDimension.getOrDefault(id.toString(), Set.of()),
                structuresByDimension.getOrDefault(id.toString(), Set.of())
            )));
        }

        return WorldCatalogSnapshot.fromEntries(entries);
    }

    private static Map<String, Set<String>> collectDimensionBiomes(MinecraftServer server, Registry<Biome> biomeRegistry) {
        Map<String, Set<String>> result = new HashMap<>();
        for (ServerLevel level : server.getAllLevels()) {
            HashSet<String> ids = new HashSet<>();
            level.getChunkSource().getGenerator().getBiomeSource().possibleBiomes().forEach(holder -> {
                String id = holderId(biomeRegistry, holder);
                if (id != null) ids.add(id);
            });
            result.put(level.dimension().location().toString(), Set.copyOf(ids));
        }
        return result;
    }

    private static Map<String, Set<String>> collectStructureBiomes(Registry<Structure> structures, Registry<Biome> biomes) {
        Map<String, Set<String>> result = new HashMap<>();
        structures.entrySet().forEach(entry -> {
            HashSet<String> ids = new HashSet<>();
            entry.getValue().biomes().forEach(holder -> {
                String id = holderId(biomes, holder);
                if (id != null) ids.add(id);
            });
            result.put(entry.getKey().location().toString(), Set.copyOf(ids));
        });
        return result;
    }

    private static Map<String, Set<String>> deriveStructureDimensions(
        Map<String, Set<String>> biomesByStructure,
        Map<String, Set<String>> biomesByDimension
    ) {
        Map<String, Set<String>> result = new HashMap<>();
        biomesByStructure.forEach((structure, allowedBiomes) -> {
            HashSet<String> dimensions = new HashSet<>();
            biomesByDimension.forEach((dimension, possibleBiomes) -> {
                if (intersects(allowedBiomes, possibleBiomes)) dimensions.add(dimension);
            });
            result.put(structure, Set.copyOf(dimensions));
        });
        return result;
    }

    private static boolean intersects(Set<String> left, Set<String> right) {
        if (left.size() > right.size()) return intersects(right, left);
        for (String value : left) if (right.contains(value)) return true;
        return false;
    }

    private static Map<String, Set<String>> invert(Map<String, Set<String>> source) {
        Map<String, Set<String>> mutable = new HashMap<>();
        source.forEach((key, values) -> values.forEach(value -> mutable.computeIfAbsent(value, ignored -> new HashSet<>()).add(key)));
        Map<String, Set<String>> result = new HashMap<>();
        mutable.forEach((key, values) -> result.put(key, Set.copyOf(values)));
        return result;
    }

    private static String holderId(Registry<Biome> registry, Holder<Biome> holder) {
        return holder.unwrapKey().map(key -> key.location().toString())
            .orElseGet(() -> {
                ResourceLocation id = registry.getKey(holder.value());
                return id == null ? null : id.toString();
            });
    }

    private static String translationKey(String kind, ResourceLocation id) {
        return kind + "." + id.getNamespace() + "." + id.getPath().replace('/', '.');
    }
}
