package dev.gustavopere.rpgskilltree.compendium.world;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class StructureProviderTest {
    public static void main(String[] args) {
        structurePublishesOnlyVerifiableRelations();
        placementDoesNotInventChance();
        duplicateRegistryIdentityCannotCreateTwoPages();
        System.out.println("StructureProviderTest: PASS");
    }

    private static void structurePublishesOnlyVerifiableRelations() {
        StructureDescriptor descriptor = new StructureDescriptor(
            "minecraft:village_plains", "minecraft", "structure.minecraft.village_plains",
            Set.of("minecraft:plains"), Set.of("minecraft:overworld"), Set.of("settlement"),
            new StructurePlacementSummary("random_spread", 34, 8)
        );
        CompendiumEntry entry = StructureProvider.create(descriptor);
        eq(CompendiumEntryKind.STRUCTURE, entry.id().kind());
        truth(entry.relations().stream().anyMatch(r -> r.target().entryId() != null
            && r.target().entryId().kind() == CompendiumEntryKind.BIOME
            && r.target().entryId().resourceLocation().equals("minecraft:plains")));
        truth(entry.relations().stream().anyMatch(r -> r.target().entryId() != null
            && r.target().entryId().kind() == CompendiumEntryKind.DIMENSION));
    }

    private static void placementDoesNotInventChance() {
        CompendiumEntry entry = StructureProvider.create(new StructureDescriptor(
            "example:tower", "example", "structure.example.tower",
            Set.of(), Set.of(), Set.of(), new StructurePlacementSummary(null, null, null)
        ));
        truth(entry.sections().stream().flatMap(s -> s.facts().stream()).noneMatch(f -> f.factKey().contains("chance")));
    }

    private static void duplicateRegistryIdentityCannotCreateTwoPages() {
        CompendiumEntry a = StructureProvider.create(new StructureDescriptor(
            "minecraft:mineshaft", "minecraft", "structure.minecraft.mineshaft",
            Set.of(), Set.of(), Set.of(), new StructurePlacementSummary(null, null, null)
        ));
        CompendiumEntry replacement = StructureProvider.create(new StructureDescriptor(
            "minecraft:mineshaft", "minecraft", "structure.minecraft.mineshaft",
            Set.of("minecraft:badlands"), Set.of("minecraft:overworld"), Set.of(), new StructurePlacementSummary(null, null, null)
        ));
        try {
            WorldCatalogSnapshot.fromEntries(List.of(a, replacement));
            throw new AssertionError("expected duplicate identity rejection");
        } catch (IllegalArgumentException expected) {
            truth(expected.getMessage().contains("duplicate world entry"));
        }
    }

    private static void truth(boolean value) { if (!value) throw new AssertionError("expected true"); }
    private static void eq(Object expected, Object actual) { if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual); }
}
