package dev.gustavopere.rpgskilltree.compendium.editorial;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class CompendiumEditorialModelTest {
    private static final CompendiumEntryId WOLF_ID = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:wolf");
    private static final CompendiumEntryId TAIGA_ID = CompendiumEntryId.of(CompendiumEntryKind.BIOME, "minecraft:taiga");

    public static void main(String[] args) {
        sourceAndBlocksNormalizeAndCopy();
        sectionIdsMatchOfflineContract();
        availabilityRulesAreExplicit();
        contentCopiesReferencesAndCanProjectThem();
        snapshotIsDeterministicIndexedAndRejectsDuplicates();
        System.out.println("CompendiumEditorialModelTest: PASS");
    }

    private static void sourceAndBlocksNormalizeAndCopy() {
        CompendiumEditorialSource source = new CompendiumEditorialSource(
            EditorialSourceType.OFFICIAL_CODE,
            "  src/main/java/example/Wolf.java  ",
            "  comportamento confirmado  "
        );
        eq("src/main/java/example/Wolf.java", source.ref());
        eq("comportamento confirmado", source.note());

        ArrayList<CompendiumEditorialSource> mutable = new ArrayList<>();
        mutable.add(source);
        CompendiumEditorialBlock block = new CompendiumEditorialBlock("  Texto editorial.  ", mutable);
        mutable.clear();

        eq("Texto editorial.", block.text());
        eq(List.of(source), block.sources());
        throwsUnsupported(() -> block.sources().add(source));
        throwsIllegal(() -> new CompendiumEditorialSource(EditorialSourceType.RUNTIME, "   ", null));
        throwsIllegal(() -> new CompendiumEditorialBlock("   ", List.of(source)));
        throwsIllegal(() -> new CompendiumEditorialBlock("texto", List.of()));
    }

    private static void sectionIdsMatchOfflineContract() {
        CompendiumEditorialBlock block = block("Seção válida.");
        eq("behavior.core", new CompendiumEditorialSection(" behavior.core ", block).sectionId());
        eq("habitat-1", new CompendiumEditorialSection("habitat-1", block).sectionId());
        throwsIllegal(() -> new CompendiumEditorialSection("Behavior Space", block));
        throwsIllegal(() -> new CompendiumEditorialSection(".invalid", block));
    }

    private static void availabilityRulesAreExplicit() {
        CompendiumEditorialContent runtime = content(WOLF_ID, EditorialAvailability.RUNTIME, null, List.of(TAIGA_ID));
        eq(null, runtime.availabilityReason());

        CompendiumEditorialContent optional = content(
            WOLF_ID,
            EditorialAvailability.OPTIONAL,
            "  Conteúdo opcional do pack.  ",
            List.of()
        );
        eq("Conteúdo opcional do pack.", optional.availabilityReason());

        throwsIllegal(() -> content(WOLF_ID, EditorialAvailability.OPTIONAL, " ", List.of()));
        throwsIllegal(() -> content(WOLF_ID, EditorialAvailability.LEGACY, null, List.of()));
        throwsIllegal(() -> content(WOLF_ID, EditorialAvailability.RUNTIME, "não permitido", List.of()));
    }

    private static void contentCopiesReferencesAndCanProjectThem() {
        ArrayList<CompendiumEntryId> references = new ArrayList<>();
        references.add(TAIGA_ID);
        CompendiumEditorialContent original = content(WOLF_ID, EditorialAvailability.RUNTIME, null, references);
        references.clear();
        eq(List.of(TAIGA_ID), original.references());

        CompendiumEditorialContent projected = original.withReferences(List.of());
        eq(List.of(TAIGA_ID), original.references());
        eq(List.of(), projected.references());
        eq(original.entryId(), projected.entryId());
        eq(original.title(), projected.title());
        eq(original.summary(), projected.summary());
        eq(original.sections(), projected.sections());
        eq(original.reviewStatus(), projected.reviewStatus());
        eq(original.availability(), projected.availability());
    }

    private static void snapshotIsDeterministicIndexedAndRejectsDuplicates() {
        CompendiumEditorialContent wolf = content(WOLF_ID, EditorialAvailability.RUNTIME, null, List.of());
        CompendiumEditorialContent taiga = content(
            TAIGA_ID,
            EditorialAvailability.RUNTIME,
            null,
            List.of(WOLF_ID)
        );

        CompendiumEditorialSnapshot snapshot = CompendiumEditorialSnapshot.fromEntries(List.of(wolf, taiga));
        eq(List.of(taiga, wolf), snapshot.entries());
        eq(wolf, snapshot.find(WOLF_ID).orElseThrow());
        isTrue(snapshot.find(CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:fox")).isEmpty());
        throwsUnsupported(() -> snapshot.entries().add(wolf));
        throwsIllegal(() -> CompendiumEditorialSnapshot.fromEntries(List.of(wolf, wolf)));
        eq(List.of(), CompendiumEditorialSnapshot.empty().entries());
    }

    private static CompendiumEditorialContent content(
        CompendiumEntryId id,
        EditorialAvailability availability,
        String availabilityReason,
        List<CompendiumEntryId> references
    ) {
        return new CompendiumEditorialContent(
            id,
            id.equals(WOLF_ID) ? "Lobo" : "Taiga",
            block("Resumo editorial."),
            List.of(new CompendiumEditorialSection("behavior", block("Descrição editorial."))),
            references,
            EditorialReviewStatus.REVIEWED,
            availability,
            availabilityReason
        );
    }

    private static CompendiumEditorialBlock block(String text) {
        return new CompendiumEditorialBlock(
            text,
            List.of(new CompendiumEditorialSource(EditorialSourceType.RUNTIME, "minecraft:test", null))
        );
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }

    private static void isTrue(boolean value) {
        if (!value) throw new AssertionError("expected true");
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
}
