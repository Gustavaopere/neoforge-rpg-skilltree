package dev.gustavopere.rpgskilltree.compendium.entity;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.List;
import java.util.Set;

public final class EntityCatalogCoverageTest {
    public static void main(String[] args) {
        completeCoverageHasNoMissingIds();
        missingModdedEntityIsReported();
        unexpectedEntityIsAuditable();
        System.out.println("EntityCatalogCoverageTest: PASS");
    }

    private static void completeCoverageHasNoMissingIds() {
        EntityCatalogCoverage coverage = EntityCatalogCoverage.compare(
            Set.of("minecraft:zombie", "example:odd_creature"),
            List.of(
                CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:zombie"),
                CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "example:odd_creature")
            )
        );
        check(coverage.complete(), "coverage should be complete");
        check(coverage.missingRegistryIds().isEmpty(), "nothing missing");
        check(coverage.unexpectedCatalogIds().isEmpty(), "nothing unexpected");
    }

    private static void missingModdedEntityIsReported() {
        EntityCatalogCoverage coverage = EntityCatalogCoverage.compare(
            Set.of("minecraft:zombie", "optionalmod:beast"),
            List.of(CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:zombie"))
        );
        check(!coverage.complete(), "missing entity must make coverage incomplete");
        check(coverage.missingRegistryIds().equals(Set.of("optionalmod:beast")), "modded entity missing set");
    }

    private static void unexpectedEntityIsAuditable() {
        EntityCatalogCoverage coverage = EntityCatalogCoverage.compare(
            Set.of("minecraft:zombie"),
            List.of(
                CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "minecraft:zombie"),
                CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "example:stale")
            )
        );
        check(coverage.complete(), "unexpected catalog entry does not hide registry coverage");
        check(coverage.unexpectedCatalogIds().equals(Set.of("example:stale")), "unexpected entry reported");
    }

    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
    }
}
