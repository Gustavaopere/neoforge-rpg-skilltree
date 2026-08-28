package dev.gustavopere.rpgskilltree.compendium.catalog;

import dev.gustavopere.rpgskilltree.compendium.api.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class CompendiumCatalogBuilderTest {
    public static void main(String[] args) {
        kindsSharingResourceLocationCoexist();
        indexesAreQueryable();
        aliasesStayExplicit();
        invalidPublishRetainsPreviousSnapshot();
        System.out.println("CompendiumCatalogBuilderTest: PASS");
    }

    private static void kindsSharingResourceLocationCoexist() {
        CompendiumCatalogSnapshot snapshot = new CompendiumCatalogBuilder()
            .add(entry(CompendiumEntryKind.ENTITY, "example:same", "example", Set.of("fauna")))
            .add(entry(CompendiumEntryKind.FLORA, "example:same", "example", Set.of("flora")))
            .build();
        eq(2, snapshot.entries().size());
    }

    private static void indexesAreQueryable() {
        CompendiumEntry first = entry(CompendiumEntryKind.ENTITY, "minecraft:zombie", "minecraft", Set.of("fauna"));
        CompendiumEntry second = entry(CompendiumEntryKind.ENTITY, "example:bear", "example", Set.of("fauna"));
        CompendiumCatalogSnapshot snapshot = new CompendiumCatalogBuilder().add(first).add(second).build();
        eq(first, snapshot.require(first.id()));
        eq(List.of(first), snapshot.byNamespace("minecraft"));
        eq(List.of(second), snapshot.bySourceMod("example"));
        eq(2, snapshot.byCategory("fauna").size());
    }

    private static void aliasesStayExplicit() {
        CompendiumEntry first = entry(CompendiumEntryKind.ENTITY, "minecraft:zombie", "minecraft", Set.of());
        CompendiumEntryId alias = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "legacy:zombie");
        CompendiumCatalogSnapshot snapshot = new CompendiumCatalogBuilder().add(first).addAlias(alias, first.id()).build();
        falsity(snapshot.find(alias).isPresent());
        eq(first.id(), snapshot.resolveAlias(alias).orElseThrow());
    }

    private static void invalidPublishRetainsPreviousSnapshot() {
        CompendiumCatalog catalog = new CompendiumCatalog();
        CompendiumEntry first = entry(CompendiumEntryKind.ENTITY, "minecraft:zombie", "minecraft", Set.of());
        catalog.publish(new CompendiumCatalogBuilder().add(first));
        CompendiumCatalogSnapshot previous = catalog.snapshot();
        try {
            catalog.publish(new CompendiumCatalogBuilder().add(first).add(first));
            throw new AssertionError("expected duplicate id failure");
        } catch (IllegalArgumentException expected) {
            if (catalog.snapshot() != previous) throw new AssertionError("snapshot changed after failed publish");
        }
    }

    private static CompendiumEntry entry(CompendiumEntryKind kind, String id, String mod, Set<String> categories) {
        return new CompendiumEntry(
            CompendiumEntryId.of(kind, id), mod, "compendium." + id.replace(':', '.'), categories,
            List.of(), List.of(), DiscoveryPolicy.OBSERVATION,
            VisibilityPolicy.HIDE_DETAILS_UNTIL_DISCOVERED,
            new CompendiumProvenance(FactSource.REGISTRY, id), 1
        );
    }

    private static void falsity(boolean value) { if (value) throw new AssertionError("expected false"); }
    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
