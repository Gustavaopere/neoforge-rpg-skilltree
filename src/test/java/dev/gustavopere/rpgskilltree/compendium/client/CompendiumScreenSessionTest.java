package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class CompendiumScreenSessionTest {
    public static void main(String[] args) {
        emptySnapshotProducesEmptyListState();
        queryAndScrollDriveTheVisibleViewport();
        openAndBackPreserveBrowserContext();
        entryWithoutPageStillOpensAsShell();
        invalidVisibleRowIsRejected();
        System.out.println("CompendiumScreenSessionTest: PASS");
    }

    private static void emptySnapshotProducesEmptyListState() {
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(List.of(), List.of())
        );

        eq(0, session.totalMatches());
        eq(List.of(), session.viewport(8).entries());
        isFalse(session.showingDetail());
        isTrue(session.currentEntry().isEmpty());
        isTrue(session.currentPage().isEmpty());
    }

    private static void queryAndScrollDriveTheVisibleViewport() {
        ArrayList<CompendiumClientEntry> entries = new ArrayList<>();
        for (int i = 0; i < 20; i++) entries.add(entry("example:entry_" + i, "Entrada " + i));
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(entries, List.of())
        );

        session.setQuery("Entrada");
        session.scrollRows(7);
        CompendiumBrowserModel.Viewport viewport = session.viewport(5);

        eq("Entrada", session.query());
        eq(20, session.totalMatches());
        eq(7, viewport.firstIndex());
        eq("Entrada 7", viewport.entries().getFirst().displayName());

        session.scrollRows(-100);
        eq(0, session.viewport(5).firstIndex());
        session.scrollRows(10_000);
        eq(19, session.viewport(5).firstIndex());
    }

    private static void openAndBackPreserveBrowserContext() {
        CompendiumClientEntry wolf = entry("minecraft:wolf", "Lobo");
        CompendiumClientEntry fox = entry("minecraft:fox", "Raposa");
        CompendiumClientSnapshot snapshot = new CompendiumClientSnapshot(
            List.of(wolf, fox),
            List.of(page(wolf), page(fox))
        );
        CompendiumScreenSession session = new CompendiumScreenSession(snapshot);

        session.setQuery("");
        session.scrollRows(1);
        session.openVisibleRow(0, 1);

        isTrue(session.showingDetail());
        eq(fox.id(), session.currentEntry().orElseThrow().id());
        eq(fox.id(), session.currentPage().orElseThrow().id());
        eq(1, session.viewport(1).firstIndex());

        session.backToList();
        isFalse(session.showingDetail());
        eq("", session.query());
        eq(1, session.viewport(1).firstIndex());
    }

    private static void entryWithoutPageStillOpensAsShell() {
        CompendiumClientEntry hidden = entry("example:hidden", "Entrada misteriosa");
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(List.of(hidden), List.of())
        );

        session.openVisibleRow(0, 4);

        isTrue(session.showingDetail());
        eq(hidden, session.currentEntry().orElseThrow());
        isTrue(session.currentPage().isEmpty());
    }

    private static void invalidVisibleRowIsRejected() {
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(List.of(entry("minecraft:wolf", "Lobo")), List.of())
        );
        throwsIllegal(() -> session.openVisibleRow(-1, 5));
        throwsIllegal(() -> session.openVisibleRow(1, 5));
        throwsIllegal(() -> session.openVisibleRow(0, 0));
    }

    private static CompendiumClientEntry entry(String id, String displayName) {
        return new CompendiumClientEntry(
            CompendiumEntryId.of(CompendiumEntryKind.ENTITY, id),
            displayName,
            id.substring(0, id.indexOf(':')),
            Set.of(),
            Set.of("fauna"),
            Set.of("minecraft:overworld"),
            Set.of("minecraft:forest"),
            true,
            false,
            true,
            true,
            false,
            CoverageState.AUTO
        );
    }

    private static CompendiumPageModel page(CompendiumClientEntry entry) {
        return new CompendiumPageModel(
            entry.id(),
            entry.displayName(),
            entry.sourceModId(),
            entry.discovered(),
            true,
            List.of(),
            List.of()
        );
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }

    private static void isTrue(boolean value) {
        if (!value) throw new AssertionError("expected true");
    }

    private static void isFalse(boolean value) {
        if (value) throw new AssertionError("expected false");
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
