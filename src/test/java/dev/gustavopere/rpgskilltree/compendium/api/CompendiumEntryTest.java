package dev.gustavopere.rpgskilltree.compendium.api;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class CompendiumEntryTest {
    public static void main(String[] args) {
        collectionsAreDefensivelyCopied();
        categoryIdsAreNormalized();
        translatedMetadataDoesNotChangeIdentity();
        contentVersionMustBePositive();
        relationRequiresAvailableEvidence();
        System.out.println("CompendiumEntryTest: PASS");
    }

    private static void collectionsAreDefensivelyCopied() {
        Set<String> categories = new LinkedHashSet<>(Set.of("fauna"));
        List<CompendiumSection> sections = new ArrayList<>();
        sections.add(new CompendiumSection("overview", List.of()));
        CompendiumEntry entry = entry("entity.minecraft.zombie", categories, sections, List.of());
        categories.add("hostile");
        sections.clear();
        eq(Set.of("fauna"), entry.categoryIds());
        eq(1, entry.sections().size());
        throwsUnsupported(() -> entry.categoryIds().add("x"));
    }

    private static void categoryIdsAreNormalized() {
        CompendiumEntry entry = entry(
            "entity.minecraft.zombie",
            new LinkedHashSet<>(List.of(" fauna ", "hostile", "fauna")),
            List.of(),
            List.of()
        );
        eq(Set.of("fauna", "hostile"), entry.categoryIds());
        throwsIllegal(() -> entry("entity.minecraft.zombie", Set.of("   "), List.of(), List.of()));
    }

    private static void translatedMetadataDoesNotChangeIdentity() {
        CompendiumEntry first = entry("entity.minecraft.zombie", Set.of(), List.of(), List.of());
        CompendiumEntry second = new CompendiumEntry(
            first.id(), first.sourceModId(), "nome.traduzido.novo", Set.of(), List.of(), List.of(),
            DiscoveryPolicy.OBSERVATION, VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            first.provenance(), 2
        );
        eq(first.id(), second.id());
    }

    private static void contentVersionMustBePositive() {
        throwsIllegal(() -> new CompendiumEntry(
            CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:zombie"),
            "minecraft", "entity.minecraft.zombie", Set.of(), List.of(), List.of(),
            DiscoveryPolicy.OBSERVATION, VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            new CompendiumProvenance(FactSource.REGISTRY, "minecraft:entity_type"), 0
        ));
    }

    private static void relationRequiresAvailableEvidence() {
        throwsIllegal(() -> new CompendiumRelation(
            CompendiumRelationType.SPAWNS_IN,
            CompendiumEntryId.of(CompendiumEntryKind.BIOME, "minecraft:plains"),
            FactSource.UNKNOWN,
            FactConfidence.UNAVAILABLE
        ));
    }

    private static CompendiumEntry entry(
        String translationKey,
        Set<String> categories,
        List<CompendiumSection> sections,
        List<CompendiumRelation> relations
    ) {
        return new CompendiumEntry(
            CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:zombie"),
            "minecraft", translationKey, categories, sections, relations,
            DiscoveryPolicy.OBSERVATION, VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            new CompendiumProvenance(FactSource.REGISTRY, "minecraft:entity_type"), 1
        );
    }

    private static void throwsIllegal(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void throwsUnsupported(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
