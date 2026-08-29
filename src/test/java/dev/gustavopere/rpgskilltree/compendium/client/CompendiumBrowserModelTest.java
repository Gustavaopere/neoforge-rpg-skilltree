package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class CompendiumBrowserModelTest {
    public static void main(String[] args) {
        virtualizesLargeResultSets();
        composesSearchAndFilters();
        keyboardSelectionTracksViewportAndOpensEntry();
        pointerOpenSelectsTheSameVisibleEntry();
        queryAndFilterResetKeyboardSelection();
        openingAndBackPreserveListState();
        queryAndFilterChangesResetScroll();
        System.out.println("CompendiumBrowserModelTest: PASS");
    }

    private static void virtualizesLargeResultSets() {
        List<CompendiumClientEntry> entries = new ArrayList<>();
        for (int i = 0; i < 1_505; i++) entries.add(entry(i, "minecraft", true, false));

        CompendiumBrowserModel model = new CompendiumBrowserModel(entries);
        CompendiumBrowserModel.Viewport first = model.viewport(24);
        eq(1_505, first.totalMatches());
        eq(0, first.firstIndex());
        eq(24, first.entries().size());
        isFalse(first.hasPrevious());
        isTrue(first.hasNext());

        model.scrollToRow(1_500);
        CompendiumBrowserModel.Viewport tail = model.viewport(24);
        eq(1_500, tail.firstIndex());
        eq(5, tail.entries().size());
        isTrue(tail.hasPrevious());
        isFalse(tail.hasNext());
        throwsUnsupported(() -> tail.entries().add(entry(9_999, "minecraft", true, false)));
    }

    private static void composesSearchAndFilters() {
        CompendiumClientEntry vanillaEagle = namedEntry("example:eagle", "Águia-real", "minecraft", true, false);
        CompendiumClientEntry moddedEagle = namedEntry("wildlife:storm_eagle", "Águia da Tempestade", "wildlife", true, true);
        CompendiumClientEntry wolf = namedEntry("minecraft:wolf", "Lobo", "minecraft", true, false);
        CompendiumBrowserModel model = new CompendiumBrowserModel(List.of(vanillaEagle, moddedEagle, wolf));

        model.setQuery("aguia");
        model.setFilter(new CompendiumFilterState(
            Set.of(CompendiumEntryKind.ENTITY),
            Set.of("wildlife"),
            Set.of("wildlife"),
            Set.of(), Set.of(), Set.of(),
            CompendiumFilterState.BooleanFilter.TRUE,
            CompendiumFilterState.BooleanFilter.TRUE,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            Set.of(CoverageState.AUTO)
        ));

        eq(List.of(moddedEagle), model.viewport(10).entries());
        eq(1, model.totalMatches());
    }

    private static void keyboardSelectionTracksViewportAndOpensEntry() {
        List<CompendiumClientEntry> entries = new ArrayList<>();
        for (int i = 0; i < 20; i++) entries.add(entry(i, "minecraft", true, false));
        CompendiumBrowserModel model = new CompendiumBrowserModel(entries);
        List<CompendiumClientEntry> orderedMatches = model.viewport(20).entries();

        isTrue(model.selectedEntry().isEmpty());
        model.moveSelection(1, 5);
        eq(orderedMatches.get(0), model.selectedEntry().orElseThrow());
        eq(0, model.viewport(5).firstIndex());

        for (int i = 0; i < 8; i++) model.moveSelection(1, 5);
        eq(orderedMatches.get(8), model.selectedEntry().orElseThrow());
        eq(4, model.viewport(5).firstIndex());

        model.moveSelection(-2, 5);
        eq(orderedMatches.get(6), model.selectedEntry().orElseThrow());
        eq(4, model.viewport(5).firstIndex());

        model.openSelectedEntry();
        eq(orderedMatches.get(6).id(), model.openEntry().orElseThrow());
        model.backToList();
        eq(orderedMatches.get(6), model.selectedEntry().orElseThrow());
    }

    private static void pointerOpenSelectsTheSameVisibleEntry() {
        List<CompendiumClientEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) entries.add(entry(i, "minecraft", true, false));
        CompendiumBrowserModel model = new CompendiumBrowserModel(entries);

        model.moveSelection(1, 4);
        CompendiumClientEntry keyboardSelected = model.selectedEntry().orElseThrow();
        CompendiumBrowserModel.Viewport viewport = model.viewport(4);
        CompendiumClientEntry clicked = viewport.entries().get(2);
        isFalse(clicked.equals(keyboardSelected));

        model.openVisibleRow(2, 4);

        eq(clicked, model.selectedEntry().orElseThrow());
        eq(clicked.id(), model.openEntry().orElseThrow());
        model.backToList();
        eq(clicked, model.selectedEntry().orElseThrow());
        eq(viewport.firstIndex(), model.viewport(4).firstIndex());
    }

    private static void queryAndFilterResetKeyboardSelection() {
        List<CompendiumClientEntry> entries = List.of(
            namedEntry("minecraft:wolf", "Lobo", "minecraft", true, false),
            namedEntry("example:fox", "Raposa", "example", true, false)
        );
        CompendiumBrowserModel model = new CompendiumBrowserModel(entries);

        model.moveSelection(1, 5);
        isTrue(model.selectedEntry().isPresent());
        model.setQuery("Raposa");
        isTrue(model.selectedEntry().isEmpty());

        model.moveSelection(1, 5);
        isTrue(model.selectedEntry().isPresent());
        model.setFilter(new CompendiumFilterState(
            Set.of(), Set.of(), Set.of("example"), Set.of(), Set.of(), Set.of(),
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            Set.of()
        ));
        isTrue(model.selectedEntry().isEmpty());
    }

    private static void openingAndBackPreserveListState() {
        List<CompendiumClientEntry> entries = new ArrayList<>();
        for (int i = 0; i < 30; i++) entries.add(entry(i, "minecraft", true, false));
        CompendiumBrowserModel model = new CompendiumBrowserModel(entries);
        CompendiumFilterState filter = CompendiumFilterState.all();

        model.setQuery("Entrada");
        model.setFilter(filter);
        model.scrollToRow(12);
        CompendiumEntryId opened = model.viewport(5).entries().getFirst().id();
        model.openEntry(opened);

        eq(opened, model.openEntry().orElseThrow());
        eq("Entrada", model.query());
        eq(filter, model.filter());
        eq(12, model.firstVisibleRow());

        model.backToList();
        isTrue(model.openEntry().isEmpty());
        eq("Entrada", model.query());
        eq(filter, model.filter());
        eq(12, model.firstVisibleRow());
    }

    private static void queryAndFilterChangesResetScroll() {
        List<CompendiumClientEntry> entries = new ArrayList<>();
        for (int i = 0; i < 40; i++) entries.add(entry(i, i % 2 == 0 ? "minecraft" : "example", true, false));
        CompendiumBrowserModel model = new CompendiumBrowserModel(entries);
        model.scrollToRow(20);
        model.setQuery("Entrada");
        eq(0, model.firstVisibleRow());

        model.scrollToRow(15);
        model.setFilter(new CompendiumFilterState(
            Set.of(), Set.of(), Set.of("example"), Set.of(), Set.of(), Set.of(),
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.ANY,
            Set.of()
        ));
        eq(0, model.firstVisibleRow());
        eq(20, model.totalMatches());
    }

    private static CompendiumClientEntry entry(int index, String sourceMod, boolean discovered, boolean hostile) {
        return namedEntry(sourceMod + ":entry_" + index, "Entrada " + index, sourceMod, discovered, hostile);
    }

    private static CompendiumClientEntry namedEntry(
        String resourceLocation,
        String displayName,
        String sourceMod,
        boolean discovered,
        boolean hostile
    ) {
        return new CompendiumClientEntry(
            CompendiumEntryId.of(CompendiumEntryKind.ENTITY, resourceLocation),
            displayName,
            sourceMod,
            Set.of(),
            Set.of("fauna"),
            Set.of("minecraft:overworld"),
            Set.of("minecraft:forest"),
            discovered,
            hostile,
            false,
            false,
            false,
            CoverageState.AUTO
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

    private static void throwsUnsupported(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
