package dev.gustavopere.rpgskilltree.compendium.world;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class DimensionProviderTest {
    public static void main(String[] args) {
        moddedDimensionIsDataDrivenAndOptional();
        snapshotBuildsCrossIndexes();
        coverageSeparatesMissingKinds();
        System.out.println("DimensionProviderTest: PASS");
    }

    private static void moddedDimensionIsDataDrivenAndOptional() {
        CompendiumEntry entry = DimensionProvider.create(new DimensionDescriptor(
            "aether:the_aether", "aether", "dimension.aether.the_aether",
            Set.of("aether:aether_highlands"), Set.of("aether:bronze_dungeon")
        ));
        eq(CompendiumEntryKind.DIMENSION, entry.id().kind());
        eq("aether", entry.sourceModId());
        truth(entry.relations().stream().anyMatch(r -> r.target().entryId() != null
            && r.target().entryId().resourceLocation().equals("aether:aether_highlands")));
    }

    private static void snapshotBuildsCrossIndexes() {
        CompendiumEntry biome = BiomeProvider.create(new BiomeDescriptor(
            "minecraft:plains", "minecraft", "biome.minecraft.plains", new BiomeClimateFacts(0.8D, 0.4D),
            Set.of(), Set.of(), Set.of("minecraft:overworld"), Set.of("minecraft:village_plains")
        ));
        CompendiumEntry structure = StructureProvider.create(new StructureDescriptor(
            "minecraft:village_plains", "minecraft", "structure.minecraft.village_plains",
            Set.of("minecraft:plains"), Set.of("minecraft:overworld"), Set.of(), new StructurePlacementSummary(null, null, null)
        ));
        CompendiumEntry dimension = DimensionProvider.create(new DimensionDescriptor(
            "minecraft:overworld", "minecraft", "dimension.minecraft.overworld",
            Set.of("minecraft:plains"), Set.of("minecraft:village_plains")
        ));
        WorldCatalogSnapshot snapshot = WorldCatalogSnapshot.fromEntries(List.of(biome, structure, dimension));
        truth(snapshot.biomeIdsForDimension("minecraft:overworld").contains("minecraft:plains"));
        truth(snapshot.structureIdsForBiome("minecraft:plains").contains("minecraft:village_plains"));
        truth(snapshot.dimensionIdsForStructure("minecraft:village_plains").contains("minecraft:overworld"));
    }

    private static void coverageSeparatesMissingKinds() {
        WorldCatalogSnapshot snapshot = WorldCatalogSnapshot.fromEntries(List.of(
            DimensionProvider.create(new DimensionDescriptor("minecraft:overworld", "minecraft", "dimension.minecraft.overworld", Set.of(), Set.of()))
        ));
        WorldCatalogCoverage coverage = WorldCatalogCoverage.compare(
            Set.of("minecraft:plains"), Set.of("minecraft:village_plains"), Set.of("minecraft:overworld"), snapshot
        );
        truth(!coverage.complete());
        eq(Set.of("minecraft:plains"), coverage.missingBiomeIds());
        eq(Set.of("minecraft:village_plains"), coverage.missingStructureIds());
        truth(coverage.missingDimensionIds().isEmpty());
    }

    private static void truth(boolean value) { if (!value) throw new AssertionError("expected true"); }
    private static void eq(Object expected, Object actual) { if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual); }
}
