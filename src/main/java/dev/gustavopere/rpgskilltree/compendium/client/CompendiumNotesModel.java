package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * In-memory personal annotations for the Compendium UI.
 *
 * <p>This model intentionally defines no persistence or network contract. Stage 10.13 owns that
 * decision. Notes are opaque plain text: strings that resemble commands, formatting codes or
 * markup are stored literally and are never interpreted by this model.</p>
 */
public final class CompendiumNotesModel {
    public static final int MAX_NOTE_CODE_POINTS = 4096;
    public static final int MAX_RECENT_ENTRIES = 32;

    private final Map<CompendiumEntryId, String> notes = new LinkedHashMap<>();
    private final Set<CompendiumEntryId> favorites = new LinkedHashSet<>();
    private final Deque<CompendiumEntryId> recent = new ArrayDeque<>();

    public Optional<String> note(CompendiumEntryId id) {
        return Optional.ofNullable(notes.get(requireId(id)));
    }

    /**
     * Stores a note exactly as supplied. An empty string removes the note.
     */
    public void setNote(CompendiumEntryId id, String text) {
        requireId(id);
        Objects.requireNonNull(text, "text");
        int codePoints = text.codePointCount(0, text.length());
        if (codePoints > MAX_NOTE_CODE_POINTS) {
            throw new IllegalArgumentException(
                "note exceeds " + MAX_NOTE_CODE_POINTS + " Unicode code points: " + codePoints
            );
        }
        if (text.isEmpty()) {
            notes.remove(id);
            return;
        }
        notes.put(id, text);
    }

    public Map<CompendiumEntryId, String> notesSnapshot() {
        return Map.copyOf(notes);
    }

    public boolean isFavorite(CompendiumEntryId id) {
        return favorites.contains(requireId(id));
    }

    public void setFavorite(CompendiumEntryId id, boolean favorite) {
        requireId(id);
        if (favorite) favorites.add(id);
        else favorites.remove(id);
    }

    public Set<CompendiumEntryId> favoritesSnapshot() {
        return Set.copyOf(favorites);
    }

    public void recordOpened(CompendiumEntryId id) {
        requireId(id);
        recent.remove(id);
        recent.addFirst(id);
        while (recent.size() > MAX_RECENT_ENTRIES) {
            recent.removeLast();
        }
    }

    public List<CompendiumEntryId> recentEntries() {
        return List.copyOf(recent);
    }

    private static CompendiumEntryId requireId(CompendiumEntryId id) {
        return Objects.requireNonNull(id, "id");
    }
}
