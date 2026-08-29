package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class CompendiumNotesModelTest {
    public static void main(String[] args) {
        storesPlainTextByCanonicalEntryId();
        enforcesUnicodeCodePointLimit();
        emptyTextRemovesNote();
        favoritesAreIndependentAndSnapshotsImmutable();
        recentHistoryIsBoundedDeduplicatedAndMostRecentFirst();
        System.out.println("CompendiumNotesModelTest: PASS");
    }

    private static void storesPlainTextByCanonicalEntryId() {
        CompendiumNotesModel model = new CompendiumNotesModel();
        CompendiumEntryId zombie = id("minecraft:zombie");
        CompendiumEntryId absentFromCurrentCatalog = id("removedmod:ancient_beast");
        String literal = "Linha 1\n/kill @e\n§c<click:run_command>continua texto</click>";

        model.setNote(zombie, literal);
        model.setNote(absentFromCurrentCatalog, "Preservar mesmo se o mod sair do pack");

        eq(literal, model.note(zombie).orElseThrow());
        eq("Preservar mesmo se o mod sair do pack", model.note(absentFromCurrentCatalog).orElseThrow());
        eq(2, model.notesSnapshot().size());
    }

    private static void enforcesUnicodeCodePointLimit() {
        CompendiumNotesModel model = new CompendiumNotesModel();
        CompendiumEntryId zombie = id("minecraft:zombie");
        String exactLimitEmoji = "😀".repeat(CompendiumNotesModel.MAX_NOTE_CODE_POINTS);
        model.setNote(zombie, exactLimitEmoji);
        eq(CompendiumNotesModel.MAX_NOTE_CODE_POINTS,
            model.note(zombie).orElseThrow().codePointCount(0, model.note(zombie).orElseThrow().length()));

        String overLimit = "a".repeat(CompendiumNotesModel.MAX_NOTE_CODE_POINTS + 1);
        throwsIllegal(() -> model.setNote(zombie, overLimit));
    }

    private static void emptyTextRemovesNote() {
        CompendiumNotesModel model = new CompendiumNotesModel();
        CompendiumEntryId zombie = id("minecraft:zombie");
        model.setNote(zombie, "anotação");
        model.setNote(zombie, "");
        isTrue(model.note(zombie).isEmpty());
        isTrue(model.notesSnapshot().isEmpty());
    }

    private static void favoritesAreIndependentAndSnapshotsImmutable() {
        CompendiumNotesModel model = new CompendiumNotesModel();
        CompendiumEntryId zombie = id("minecraft:zombie");
        CompendiumEntryId cow = id("minecraft:cow");

        model.setFavorite(zombie, true);
        model.setFavorite(cow, true);
        isTrue(model.isFavorite(zombie));
        eq(Set.of(zombie, cow), model.favoritesSnapshot());
        throwsUnsupported(() -> model.favoritesSnapshot().add(id("minecraft:pig")));

        model.setFavorite(zombie, false);
        isFalse(model.isFavorite(zombie));
        isTrue(model.isFavorite(cow));
    }

    private static void recentHistoryIsBoundedDeduplicatedAndMostRecentFirst() {
        CompendiumNotesModel model = new CompendiumNotesModel();
        List<CompendiumEntryId> inserted = new ArrayList<>();
        for (int i = 0; i < CompendiumNotesModel.MAX_RECENT_ENTRIES + 5; i++) {
            CompendiumEntryId id = id("example:entry_" + i);
            inserted.add(id);
            model.recordOpened(id);
        }

        eq(CompendiumNotesModel.MAX_RECENT_ENTRIES, model.recentEntries().size());
        eq(inserted.get(inserted.size() - 1), model.recentEntries().getFirst());
        eq(inserted.get(5), model.recentEntries().getLast());

        CompendiumEntryId existing = inserted.get(10);
        model.recordOpened(existing);
        eq(existing, model.recentEntries().getFirst());
        eq(1L, model.recentEntries().stream().filter(existing::equals).count());
        throwsUnsupported(() -> model.recentEntries().add(id("minecraft:pig")));
    }

    private static CompendiumEntryId id(String resourceLocation) {
        return CompendiumEntryId.of(CompendiumEntryKind.ENTITY, resourceLocation);
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

    private static void isTrue(boolean value) {
        if (!value) throw new AssertionError("expected true");
    }

    private static void isFalse(boolean value) {
        if (value) throw new AssertionError("expected false");
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
