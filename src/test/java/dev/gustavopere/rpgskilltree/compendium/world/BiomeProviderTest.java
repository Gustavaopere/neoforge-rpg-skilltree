package dev.gustavopere.rpgskilltree.compendium.world;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import java.util.Objects;
import java.util.Set;

public final class BiomeProviderTest {
    public static void main(String[] args) {
        vanillaBiomePublishesRegistryIdentityAndClimate();
        caveCategoryRequiresExplicitEvidence();
        unavailableClimateIsOmitted();
        System.out.println("BiomeProviderTest: PASS");
    }

    private static void vanillaBiomePublishesRegistryIdentityAndClimate() {
        BiomeDescriptor descriptor = new BiomeDescriptor(
            "minecraft:plains", "minecraft", "biome.minecraft.plains",
            new BiomeClimateFacts(0.8D, 0.4D),
            Set.of("minecraft:is_overworld"), Set.of("surface"),
            Set.of("minecraft:overworld"), Set.of("minecraft:village_plains")
        );
        CompendiumEntry entry = BiomeProvider.create(descriptor);
        eq(CompendiumEntryKind.BIOME, entry.id().kind());
        eq("minecraft:plains", entry.id().resourceLocation());
        truth(entry.categoryIds().contains("biome"));
        truth(entry.categoryIds().contains("surface"));
        eq(0.8D, fact(entry, "vanilla_temperature"));
        eq(0.4D, fact(entry, "vanilla_downfall"));
        truth(entry.relations().stream().anyMatch(r -> r.target().entryId() != null
            && r.target().entryId().kind() == CompendiumEntryKind.DIMENSION
            && r.target().entryId().resourceLocation().equals("minecraft:overworld")));
    }

    private static void caveCategoryRequiresExplicitEvidence() {
        CompendiumEntry generic = BiomeProvider.create(new BiomeDescriptor(
            "example:crystal_caves", "example", "biome.example.crystal_caves",
            new BiomeClimateFacts(null, null), Set.of(), Set.of(), Set.of(), Set.of()
        ));
        truth(!generic.categoryIds().contains("cave"));
        CompendiumEntry explicit = BiomeProvider.create(new BiomeDescriptor(
            "example:deep_crystal", "example", "biome.example.deep_crystal",
            new BiomeClimateFacts(null, null), Set.of("example:is_cave"), Set.of("cave"), Set.of(), Set.of()
        ));
        truth(explicit.categoryIds().contains("cave"));
    }

    private static void unavailableClimateIsOmitted() {
        CompendiumEntry entry = BiomeProvider.create(new BiomeDescriptor(
            "example:void", "example", "biome.example.void",
            new BiomeClimateFacts(null, null), Set.of(), Set.of(), Set.of(), Set.of()
        ));
        truth(entry.sections().stream().flatMap(s -> s.facts().stream())
            .noneMatch(f -> f.factKey().equals("vanilla_temperature") || f.factKey().equals("vanilla_downfall")));
    }

    private static Object fact(CompendiumEntry entry, String key) {
        return entry.sections().stream().flatMap(s -> s.facts().stream())
            .filter(f -> f.factKey().equals(key)).map(CompendiumFact::value).findFirst().orElseThrow();
    }
    private static void truth(boolean value) { if (!value) throw new AssertionError("expected true"); }
    private static void eq(Object expected, Object actual) { if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual); }
}
