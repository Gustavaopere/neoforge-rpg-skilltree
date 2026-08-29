package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class CompendiumClientSnapshotTest {
    private static final CompendiumEntryId WOLF_ID = id("minecraft:wolf");
    private static final CompendiumEntryId FOX_ID = id("minecraft:fox");

    public static void main(String[] args) {
        snapshotCopiesAndIndexesVisibleClientData();
        browserModelsAreIndependentViewsOfTheSameSnapshot();
        entryWithoutPageIsAllowedForHiddenDetails();
        duplicateEntriesAreRejected();
        pageWithoutClientEntryIsRejected();
        System.out.println("CompendiumClientSnapshotTest: PASS");
    }

    private static void snapshotCopiesAndIndexesVisibleClientData() {
        ArrayList<CompendiumClientEntry> entries = new ArrayList<>(List.of(entry(WOLF_ID, "Lobo")));
        ArrayList<CompendiumPageModel> pages = new ArrayList<>(List.of(page(WOLF_ID, "Lobo")));

        CompendiumClientSnapshot snapshot = new CompendiumClientSnapshot(entries, pages);
        entries.clear();
        pages.clear();

        eq(1, snapshot.entries().size());
        eq("Lobo", snapshot.entries().getFirst().displayName());
        eq(WOLF_ID, snapshot.page(WOLF_ID).orElseThrow().id());
        isTrue(snapshot.page(FOX_ID).isEmpty());
        throwsUnsupported(() -> snapshot.entries().add(entry(FOX_ID, "Raposa")));
    }

    private static void browserModelsAreIndependentViewsOfTheSameSnapshot() {
        CompendiumClientSnapshot snapshot = new CompendiumClientSnapshot(
            List.of(entry(WOLF_ID, "Lobo"), entry(FOX_ID, "Raposa")),
            List.of(page(WOLF_ID, "Lobo"), page(FOX_ID, "Raposa"))
        );

        CompendiumBrowserModel first = snapshot.newBrowserModel();
        CompendiumBrowserModel second = snapshot.newBrowserModel();
        first.setQuery("lobo");

        eq(1, first.totalMatches());
        eq(2, second.totalMatches());
        eq("", second.query());
    }

    private static void entryWithoutPageIsAllowedForHiddenDetails() {
        CompendiumClientSnapshot snapshot = new CompendiumClientSnapshot(
            List.of(entry(WOLF_ID, "Lobo")),
            List.of()
        );
        eq(1, snapshot.entries().size());
        isTrue(snapshot.page(WOLF_ID).isEmpty());
    }

    private static void duplicateEntriesAreRejected() {
        CompendiumClientEntry wolf = entry(WOLF_ID, "Lobo");
        throwsIllegal(() -> new CompendiumClientSnapshot(List.of(wolf, wolf), List.of()));
    }

    private static void pageWithoutClientEntryIsRejected() {
        throwsIllegal(() -> new CompendiumClientSnapshot(
            List.of(entry(WOLF_ID, "Lobo")),
            List.of(page(FOX_ID, "Raposa"))
        ));
    }

    private static CompendiumEntryId id(String value) {
        return CompendiumEntryId.of(CompendiumEntryKind.ENTITY, value);
    }

    private static CompendiumClientEntry entry(CompendiumEntryId id, String name) {
        return new CompendiumClientEntry(
            id,
            name,
            "minecraft",
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

    private static CompendiumPageModel page(CompendiumEntryId id, String name) {
        return new CompendiumPageModel(id, name, "minecraft", true, true, List.of(), List.of());
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
