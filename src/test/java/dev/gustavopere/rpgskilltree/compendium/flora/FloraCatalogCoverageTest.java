package dev.gustavopere.rpgskilltree.compendium.flora;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.List;

public final class FloraCatalogCoverageTest {
    public static void main(String[] args) {
        completeWhenExpectedEntriesMatchCatalog();
        reportsMissingAndUnexpectedEntries();
        ambiguousBlocksRemainDiagnosticInsteadOfFakeEntries();
        System.out.println("FloraCatalogCoverageTest: PASS");
    }

    private static void completeWhenExpectedEntriesMatchCatalog() {
        FloraCatalogCoverage coverage = FloraCatalogCoverage.compare(
            List.of(id(CompendiumEntryKind.FLORA, "minecraft:dandelion"), id(CompendiumEntryKind.CROP, "minecraft:wheat")),
            List.of(id(CompendiumEntryKind.CROP, "minecraft:wheat"), id(CompendiumEntryKind.FLORA, "minecraft:dandelion")),
            List.of()
        );
        check(coverage.complete(), "matching catalog must be complete");
        check(coverage.missingEntryIds().isEmpty(), "no missing");
        check(coverage.unexpectedEntryIds().isEmpty(), "no unexpected");
    }

    private static void reportsMissingAndUnexpectedEntries() {
        FloraCatalogCoverage coverage = FloraCatalogCoverage.compare(
            List.of(id(CompendiumEntryKind.TREE, "minecraft:oak")),
            List.of(id(CompendiumEntryKind.TREE, "minecraft:birch")),
            List.of()
        );
        check(!coverage.complete(), "mismatch must fail coverage");
        check(coverage.missingEntryIds().contains(id(CompendiumEntryKind.TREE, "minecraft:oak")), "missing oak");
        check(coverage.unexpectedEntryIds().contains(id(CompendiumEntryKind.TREE, "minecraft:birch")), "unexpected birch");
    }

    private static void ambiguousBlocksRemainDiagnosticInsteadOfFakeEntries() {
        FloraCatalogCoverage coverage = FloraCatalogCoverage.compare(
            List.of(id(CompendiumEntryKind.FLORA, "example:known")),
            List.of(id(CompendiumEntryKind.FLORA, "example:known")),
            List.of("example:ambiguous_green_block")
        );
        check(coverage.complete(), "ambiguous source is not an invented expected entry");
        check(coverage.ambiguousBlockIds().contains("example:ambiguous_green_block"), "ambiguity diagnostic retained");
    }

    private static CompendiumEntryId id(CompendiumEntryKind kind, String id) {
        return CompendiumEntryId.of(kind, id);
    }

    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
    }
}
