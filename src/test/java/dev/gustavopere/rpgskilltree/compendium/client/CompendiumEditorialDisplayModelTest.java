package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialBlock;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialContent;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSection;
import dev.gustavopere.rpgskilltree.compendium.editorial.CompendiumEditorialSource;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialAvailability;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialReviewStatus;
import dev.gustavopere.rpgskilltree.compendium.editorial.EditorialSourceType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class CompendiumEditorialDisplayModelTest {
    private static final CompendiumEntryId WOLF_ID =
        CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:wolf");
    private static final CompendiumEntryId TAIGA_ID =
        CompendiumEntryId.of(CompendiumEntryKind.BIOME, "minecraft:taiga");

    public static void main(String[] args) {
        fallbackPageUsesTechnicalTitleAndNoEditorialBlocks();
        editorialPageUsesEditorialTitleSummaryFirstAndPreservesSectionOrder();
        displayBlocksExposeSourceRefsWithoutMutatingThePage();
        displayCollectionsAreImmutable();
        System.out.println("CompendiumEditorialDisplayModelTest: PASS");
    }

    private static void fallbackPageUsesTechnicalTitleAndNoEditorialBlocks() {
        CompendiumEditorialDisplayModel display = CompendiumEditorialDisplayModel.from(page(Optional.empty()));
        eq("Lobo", display.title());
        eq(List.of(), display.blocks());
    }

    private static void editorialPageUsesEditorialTitleSummaryFirstAndPreservesSectionOrder() {
        CompendiumEditorialDisplayModel display = CompendiumEditorialDisplayModel.from(page(Optional.of(editorial())));

        eq("Lobo cinzento", display.title());
        eq(List.of("summary", "behavior", "habitat"), display.blocks().stream()
            .map(CompendiumEditorialDisplayModel.DisplayBlock::sectionId)
            .toList());
        eq("Canídeo registrado no catálogo.", display.blocks().get(0).text());
        eq("Comportamento confirmado.", display.blocks().get(1).text());
        eq("Habitat confirmado.", display.blocks().get(2).text());
    }

    private static void displayBlocksExposeSourceRefsWithoutMutatingThePage() {
        CompendiumPageModel page = page(Optional.of(editorial()));
        CompendiumEditorialDisplayModel display = CompendiumEditorialDisplayModel.from(page);

        eq(
            List.of("minecraft:entity_type/minecraft:wolf", "docs:wolf"),
            display.blocks().getFirst().sourceRefs()
        );
        eq(
            List.of("minecraft:entity_type/minecraft:wolf"),
            display.blocks().get(1).sourceRefs()
        );
        eq(List.of(TAIGA_ID), page.editorialContent().orElseThrow().references());
        eq(List.of(), page.sections());
    }

    private static void displayCollectionsAreImmutable() {
        CompendiumEditorialDisplayModel display = CompendiumEditorialDisplayModel.from(page(Optional.of(editorial())));
        throwsUnsupported(() -> display.blocks().clear());
        throwsUnsupported(() -> display.blocks().getFirst().sourceRefs().clear());
    }

    private static CompendiumPageModel page(Optional<CompendiumEditorialContent> editorial) {
        return new CompendiumPageModel(
            WOLF_ID,
            "Lobo",
            "minecraft",
            true,
            true,
            List.of(),
            List.of(),
            new CompendiumDebugInfo(
                WOLF_ID.resourceLocation(),
                "minecraft",
                FactSource.REGISTRY,
                "minecraft:entity_type",
                CoverageState.CURATED
            ),
            editorial
        );
    }

    private static CompendiumEditorialContent editorial() {
        CompendiumEditorialSource runtime = new CompendiumEditorialSource(
            EditorialSourceType.RUNTIME,
            "minecraft:entity_type/minecraft:wolf",
            null
        );
        CompendiumEditorialSource docs = new CompendiumEditorialSource(
            EditorialSourceType.OFFICIAL_DOCS,
            "docs:wolf",
            "fonte secundária"
        );
        return new CompendiumEditorialContent(
            WOLF_ID,
            "Lobo cinzento",
            new CompendiumEditorialBlock(
                "Canídeo registrado no catálogo.",
                List.of(runtime, docs)
            ),
            List.of(
                new CompendiumEditorialSection(
                    "behavior",
                    new CompendiumEditorialBlock("Comportamento confirmado.", List.of(runtime))
                ),
                new CompendiumEditorialSection(
                    "habitat",
                    new CompendiumEditorialBlock("Habitat confirmado.", List.of(docs))
                )
            ),
            List.of(TAIGA_ID),
            EditorialReviewStatus.REVIEWED,
            EditorialAvailability.RUNTIME,
            null
        );
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
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
