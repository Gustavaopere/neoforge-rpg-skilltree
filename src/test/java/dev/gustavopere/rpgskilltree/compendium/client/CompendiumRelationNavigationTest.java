package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelationType;
import dev.gustavopere.rpgskilltree.compendium.api.FactConfidence;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class CompendiumRelationNavigationTest {
    public static void main(String[] args) {
        onlySnapshotBackedRelationsAreExposed();
        relationNavigationPreservesBrowserContextAndRecordsRecent();
        invalidRelationNavigationIsRejected();
        System.out.println("CompendiumRelationNavigationTest: PASS");
    }

    private static void onlySnapshotBackedRelationsAreExposed() {
        CompendiumClientEntry wolf = entry("minecraft:wolf", "Lobo");
        CompendiumClientEntry taiga = entry(CompendiumEntryKind.BIOME, "minecraft:taiga", "Taiga");
        CompendiumEntryId removed = CompendiumEntryId.of(CompendiumEntryKind.BIOME, "removed:old_forest");
        CompendiumPageModel wolfPage = page(
            wolf,
            List.of(
                relation(CompendiumRelationType.SPAWNS_IN, taiga.id()),
                relation(CompendiumRelationType.RELATED_ENTRY, removed)
            )
        );
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(List.of(wolf, taiga), List.of(wolfPage, page(taiga, List.of())))
        );

        session.setQuery("Lobo");
        session.openVisibleRow(0, 4);

        List<CompendiumRelationLink> links = session.currentRelationLinks();
        eq(1, links.size());
        eq(CompendiumRelationType.SPAWNS_IN, links.getFirst().type());
        eq(taiga, links.getFirst().target());
    }

    private static void relationNavigationPreservesBrowserContextAndRecordsRecent() {
        CompendiumClientEntry wolf = entry("minecraft:wolf", "Lobo");
        CompendiumClientEntry taiga = entry(CompendiumEntryKind.BIOME, "minecraft:taiga", "Taiga");
        CompendiumNotesModel notes = new CompendiumNotesModel();
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(
                List.of(wolf, taiga),
                List.of(
                    page(wolf, List.of(relation(CompendiumRelationType.SPAWNS_IN, taiga.id()))),
                    page(taiga, List.of())
                )
            ),
            notes
        );

        session.setQuery("Lobo");
        CompendiumFilterState beforeFilter = session.filter();
        session.openVisibleRow(0, 4);
        eq(List.of(wolf.id()), notes.recentEntries());

        session.openCurrentRelation(0);

        eq(taiga.id(), session.currentEntry().orElseThrow().id());
        eq("Lobo", session.query());
        eq(beforeFilter, session.filter());
        eq(0, session.viewport(4).firstIndex());
        eq(List.of(taiga.id(), wolf.id()), notes.recentEntries());
    }

    private static void invalidRelationNavigationIsRejected() {
        CompendiumClientEntry wolf = entry("minecraft:wolf", "Lobo");
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(List.of(wolf), List.of(page(wolf, List.of())))
        );

        eq(List.of(), session.currentRelationLinks());
        throwsIllegal(() -> session.openCurrentRelation(0));

        session.openVisibleRow(0, 4);
        eq(List.of(), session.currentRelationLinks());
        throwsIllegal(() -> session.openCurrentRelation(-1));
        throwsIllegal(() -> session.openCurrentRelation(0));
    }

    private static CompendiumRelation relation(CompendiumRelationType type, CompendiumEntryId target) {
        return new CompendiumRelation(type, target, FactSource.REGISTRY, FactConfidence.EXACT);
    }

    private static CompendiumClientEntry entry(String id, String displayName) {
        return entry(CompendiumEntryKind.ENTITY, id, displayName);
    }

    private static CompendiumClientEntry entry(CompendiumEntryKind kind, String id, String displayName) {
        return new CompendiumClientEntry(
            CompendiumEntryId.of(kind, id),
            displayName,
            id.substring(0, id.indexOf(':')),
            Set.of(),
            Set.of(kind.name().toLowerCase()),
            Set.of("minecraft:overworld"),
            Set.of(),
            true,
            false,
            false,
            false,
            false,
            CoverageState.AUTO
        );
    }

    private static CompendiumPageModel page(CompendiumClientEntry entry, List<CompendiumRelation> relations) {
        return new CompendiumPageModel(
            entry.id(),
            entry.displayName(),
            entry.sourceModId(),
            entry.discovered(),
            true,
            List.of(),
            relations
        );
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }

    private static void throwsIllegal(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
