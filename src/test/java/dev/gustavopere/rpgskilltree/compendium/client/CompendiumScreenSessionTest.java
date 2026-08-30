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
        keyboardSelectionDelegatesToBrowserAndPreservesDetailContext();
        filterStateDrivesMatchesAndSurvivesDetailNavigation();
        pointerOpenSelectsAndPreservesBrowserContext();
        openingEntriesRecordsRecentHistory();
        currentEntryFavoriteTogglesInPersonalState();
        currentEntryNoteReadsAndWritesPersonalState();
        notesRemainAttachedToEntryAcrossNavigation();
        personalNavigationScopesRespectStateAndPreserveSearchFilters();
        recentNavigationUsesMruOrderAndIgnoresUnavailableIds();
        favoriteScopeRefreshesAfterToggleWithoutClosingDetail();
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
        isTrue(session.selectedEntry().isEmpty());
        isTrue(session.currentEntry().isEmpty());
        isTrue(session.currentPage().isEmpty());
        isTrue(session.currentNote().isEmpty());
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
        eq(5, viewport.entries().size());

        session.scrollRows(-100);
        eq(0, session.viewport(5).firstIndex());
        session.scrollRows(10_000);
        eq(19, session.viewport(5).firstIndex());
    }

    private static void keyboardSelectionDelegatesToBrowserAndPreservesDetailContext() {
        ArrayList<CompendiumClientEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) entries.add(entry("example:entry_" + i, "Entrada " + i));
        CompendiumClientSnapshot snapshot = new CompendiumClientSnapshot(
            entries,
            entries.stream().map(CompendiumScreenSessionTest::page).toList()
        );
        CompendiumScreenSession session = new CompendiumScreenSession(snapshot);
        List<CompendiumClientEntry> ordered = session.viewport(12).entries();

        session.moveSelection(1, 4);
        eq(ordered.get(0), session.selectedEntry().orElseThrow());
        session.moveSelection(5, 4);
        eq(ordered.get(5), session.selectedEntry().orElseThrow());
        eq(2, session.viewport(4).firstIndex());

        session.openSelectedEntry();
        isTrue(session.showingDetail());
        eq(ordered.get(5), session.currentEntry().orElseThrow());

        session.backToList();
        isFalse(session.showingDetail());
        eq(ordered.get(5), session.selectedEntry().orElseThrow());

        session.setQuery("Entrada 1");
        isTrue(session.selectedEntry().isEmpty());
    }

    private static void filterStateDrivesMatchesAndSurvivesDetailNavigation() {
        CompendiumClientEntry wolf = entry("minecraft:wolf", "Lobo");
        CompendiumClientEntry griffin = entry("example:griffin", "Grifo");
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(List.of(wolf, griffin), List.of(page(wolf), page(griffin)))
        );
        CompendiumFilterState filter = new CompendiumFilterState(
            Set.of(),
            Set.of(),
            Set.of("example"),
            Set.of(),
            Set.of(),
            Set.of(),
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            Set.of()
        );

        session.setFilter(filter);

        eq(filter, session.filter());
        eq(1, session.totalMatches());
        eq(griffin.id(), session.viewport(5).entries().getFirst().id());

        session.openVisibleRow(0, 5);
        eq(griffin, session.selectedEntry().orElseThrow());
        session.backToList();

        eq(filter, session.filter());
        eq(1, session.totalMatches());
        eq(griffin, session.selectedEntry().orElseThrow());
        eq(griffin.id(), session.viewport(5).entries().getFirst().id());
    }

    private static void pointerOpenSelectsAndPreservesBrowserContext() {
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
        eq(fox, session.selectedEntry().orElseThrow());
        eq(fox.id(), session.currentEntry().orElseThrow().id());
        eq(fox.id(), session.currentPage().orElseThrow().id());
        eq(1, session.viewport(1).firstIndex());

        session.backToList();
        isFalse(session.showingDetail());
        eq(fox, session.selectedEntry().orElseThrow());
        eq("", session.query());
        eq(1, session.viewport(1).firstIndex());
    }

    private static void openingEntriesRecordsRecentHistory() {
        CompendiumClientEntry wolf = entry("minecraft:wolf", "Lobo");
        CompendiumClientEntry fox = entry("minecraft:fox", "Raposa");
        CompendiumClientSnapshot snapshot = new CompendiumClientSnapshot(
            List.of(wolf, fox),
            List.of(page(wolf), page(fox))
        );
        CompendiumNotesModel notes = new CompendiumNotesModel();
        CompendiumScreenSession session = new CompendiumScreenSession(snapshot, notes);
        List<CompendiumClientEntry> ordered = session.viewport(2).entries();

        session.openVisibleRow(1, 2);
        eq(List.of(ordered.get(1).id()), notes.recentEntries());

        session.backToList();
        session.moveSelection(-1, 2);
        session.openSelectedEntry();
        eq(List.of(ordered.get(0).id(), ordered.get(1).id()), notes.recentEntries());
    }

    private static void currentEntryFavoriteTogglesInPersonalState() {
        CompendiumClientEntry wolf = entry("minecraft:wolf", "Lobo");
        CompendiumNotesModel notes = new CompendiumNotesModel();
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(List.of(wolf), List.of(page(wolf))),
            notes
        );

        isFalse(session.isCurrentEntryFavorite());
        session.openVisibleRow(0, 4);
        isFalse(session.isCurrentEntryFavorite());

        session.toggleCurrentEntryFavorite();
        isTrue(session.isCurrentEntryFavorite());
        isTrue(notes.isFavorite(wolf.id()));

        session.toggleCurrentEntryFavorite();
        isFalse(session.isCurrentEntryFavorite());
        isFalse(notes.isFavorite(wolf.id()));
    }

    private static void currentEntryNoteReadsAndWritesPersonalState() {
        CompendiumClientEntry wolf = entry("minecraft:wolf", "Lobo");
        CompendiumNotesModel notes = new CompendiumNotesModel();
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(List.of(wolf), List.of(page(wolf))),
            notes
        );

        isTrue(session.currentNote().isEmpty());
        session.openVisibleRow(0, 4);
        isTrue(session.currentNote().isEmpty());

        String text = "/say não executar §ltexto literal\nHabitat perto do rio.";
        session.setCurrentNote(text);
        eq(text, session.currentNote().orElseThrow());
        eq(text, notes.note(wolf.id()).orElseThrow());

        session.setCurrentNote("");
        isTrue(session.currentNote().isEmpty());
        isTrue(notes.note(wolf.id()).isEmpty());
    }

    private static void notesRemainAttachedToEntryAcrossNavigation() {
        CompendiumClientEntry wolf = entry("minecraft:wolf", "Lobo");
        CompendiumClientEntry fox = entry("minecraft:fox", "Raposa");
        CompendiumNotesModel notes = new CompendiumNotesModel();
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(List.of(wolf, fox), List.of(page(wolf), page(fox))),
            notes
        );
        List<CompendiumClientEntry> ordered = session.viewport(2).entries();

        session.openVisibleRow(0, 2);
        CompendiumEntryId first = ordered.get(0).id();
        session.setCurrentNote("nota da primeira entrada");
        session.backToList();

        session.openVisibleRow(1, 2);
        CompendiumEntryId second = ordered.get(1).id();
        isTrue(session.currentNote().isEmpty());
        session.setCurrentNote("nota da segunda entrada");
        session.backToList();

        session.openVisibleRow(0, 2);
        eq(first, session.currentEntry().orElseThrow().id());
        eq("nota da primeira entrada", session.currentNote().orElseThrow());
        eq("nota da segunda entrada", notes.note(second).orElseThrow());
    }

    private static void personalNavigationScopesRespectStateAndPreserveSearchFilters() {
        CompendiumClientEntry wolf = entry("minecraft:wolf", "Lobo");
        CompendiumClientEntry fox = entry("minecraft:fox", "Raposa");
        CompendiumClientEntry griffin = entry("example:griffin", "Grifo");
        CompendiumNotesModel notes = new CompendiumNotesModel();
        notes.setFavorite(fox.id(), true);
        notes.setFavorite(griffin.id(), true);
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(List.of(wolf, fox, griffin), List.of()),
            notes
        );
        CompendiumFilterState minecraftOnly = new CompendiumFilterState(
            Set.of(),
            Set.of(),
            Set.of("minecraft"),
            Set.of(),
            Set.of(),
            Set.of(),
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            Set.of()
        );
        session.setQuery("o");
        session.setFilter(minecraftOnly);

        session.setPersonalView(CompendiumPersonalView.FAVORITES);

        eq(CompendiumPersonalView.FAVORITES, session.personalView());
        eq("o", session.query());
        eq(minecraftOnly, session.filter());
        eq(List.of(fox.id()), session.viewport(10).entries().stream().map(CompendiumClientEntry::id).toList());

        session.setPersonalView(CompendiumPersonalView.ALL);
        eq(List.of(wolf.id(), fox.id()), session.viewport(10).entries().stream().map(CompendiumClientEntry::id).toList());
    }

    private static void recentNavigationUsesMruOrderAndIgnoresUnavailableIds() {
        CompendiumClientEntry wolf = entry("minecraft:wolf", "Lobo");
        CompendiumClientEntry fox = entry("minecraft:fox", "Raposa");
        CompendiumEntryId missing = CompendiumEntryId.of(CompendiumEntryKind.ENTITY, "removed:missing");
        CompendiumNotesModel notes = new CompendiumNotesModel();
        notes.recordOpened(wolf.id());
        notes.recordOpened(missing);
        notes.recordOpened(fox.id());
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(List.of(wolf, fox), List.of(page(wolf), page(fox))),
            notes
        );

        session.setPersonalView(CompendiumPersonalView.RECENT);
        eq(List.of(fox.id(), wolf.id()), session.viewport(10).entries().stream().map(CompendiumClientEntry::id).toList());

        session.openVisibleRow(1, 10);
        isTrue(session.showingDetail());
        eq(wolf.id(), session.currentEntry().orElseThrow().id());
        eq(List.of(wolf.id(), fox.id()), session.viewport(10).entries().stream().map(CompendiumClientEntry::id).toList());
        eq(List.of(wolf.id(), fox.id(), missing), notes.recentEntries());
    }

    private static void favoriteScopeRefreshesAfterToggleWithoutClosingDetail() {
        CompendiumClientEntry wolf = entry("minecraft:wolf", "Lobo");
        CompendiumNotesModel notes = new CompendiumNotesModel();
        notes.setFavorite(wolf.id(), true);
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(List.of(wolf), List.of(page(wolf))),
            notes
        );

        session.setPersonalView(CompendiumPersonalView.FAVORITES);
        session.openVisibleRow(0, 4);
        session.toggleCurrentEntryFavorite();

        isTrue(session.showingDetail());
        eq(wolf.id(), session.currentEntry().orElseThrow().id());
        eq(0, session.totalMatches());
        eq(List.of(), session.viewport(4).entries());
        isFalse(notes.isFavorite(wolf.id()));
    }

    private static void entryWithoutPageStillOpensAsShell() {
        CompendiumClientEntry hidden = entry("example:hidden", "Entrada misteriosa");
        CompendiumScreenSession session = new CompendiumScreenSession(
            new CompendiumClientSnapshot(List.of(hidden), List.of())
        );

        session.openVisibleRow(0, 4);

        isTrue(session.showingDetail());
        eq(hidden, session.selectedEntry().orElseThrow());
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
