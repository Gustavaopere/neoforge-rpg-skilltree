package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Pure client-side state model for the Compendium browser.
 *
 * <p>The model composes the existing search index and filter contract, then exposes only a bounded
 * viewport to rendering code. Opening an entry never mutates the list query/filter/scroll/selection
 * state, so returning to the browser restores the exact previous context.</p>
 */
public final class CompendiumBrowserModel {
    private final List<CompendiumClientEntry> snapshot;
    private final CompendiumSearchIndex searchIndex;

    private String query = "";
    private CompendiumFilterState filter = CompendiumFilterState.all();
    private List<CompendiumClientEntry> matches;
    private int firstVisibleRow;
    private int selectedRow = -1;
    private CompendiumEntryId openEntry;

    public CompendiumBrowserModel(List<CompendiumClientEntry> snapshot) {
        this.snapshot = snapshot == null ? List.of() : List.copyOf(snapshot);
        this.searchIndex = new CompendiumSearchIndex(this.snapshot);
        this.matches = resolveMatches();
    }

    public String query() {
        return query;
    }

    public CompendiumFilterState filter() {
        return filter;
    }

    public int firstVisibleRow() {
        return firstVisibleRow;
    }

    public int totalMatches() {
        return matches.size();
    }

    public Optional<CompendiumEntryId> openEntry() {
        return Optional.ofNullable(openEntry);
    }

    public Optional<CompendiumClientEntry> selectedEntry() {
        if (selectedRow < 0 || selectedRow >= matches.size()) return Optional.empty();
        return Optional.of(matches.get(selectedRow));
    }

    public void setQuery(String query) {
        this.query = query == null ? "" : query;
        refreshFromTop();
    }

    public void setFilter(CompendiumFilterState filter) {
        this.filter = Objects.requireNonNull(filter, "filter");
        refreshFromTop();
    }

    /**
     * Moves the first visible row while clamping to the current result set.
     */
    public void scrollToRow(int requestedRow) {
        if (matches.isEmpty()) {
            firstVisibleRow = 0;
            return;
        }
        firstVisibleRow = Math.max(0, Math.min(requestedRow, matches.size() - 1));
    }

    /**
     * Moves the keyboard selection and keeps it inside the current viewport.
     */
    public void moveSelection(int delta, int rowCapacity) {
        if (rowCapacity <= 0) throw new IllegalArgumentException("rowCapacity must be positive");
        if (matches.isEmpty()) {
            selectedRow = -1;
            firstVisibleRow = 0;
            return;
        }
        if (delta == 0) return;

        long requested;
        if (selectedRow < 0) {
            requested = delta > 0 ? 0 : matches.size() - 1L;
        } else {
            requested = (long) selectedRow + delta;
        }
        selectedRow = (int) Math.max(0L, Math.min(requested, matches.size() - 1L));

        if (selectedRow < firstVisibleRow) {
            firstVisibleRow = selectedRow;
        } else if (selectedRow >= firstVisibleRow + rowCapacity) {
            firstVisibleRow = selectedRow - rowCapacity + 1;
        }
    }

    /**
     * Opens the keyboard-selected entry when one exists.
     */
    public void openSelectedEntry() {
        selectedEntry().ifPresent(entry -> openEntry(entry.id()));
    }

    /**
     * Selects and opens one row from the current visible viewport without moving that viewport.
     */
    public void openVisibleRow(int visibleRow, int rowCapacity) {
        Viewport viewport = viewport(rowCapacity);
        if (visibleRow < 0 || visibleRow >= viewport.entries().size()) {
            throw new IllegalArgumentException("visible row is outside the current viewport: " + visibleRow);
        }
        selectedRow = viewport.firstIndex() + visibleRow;
        openEntry(matches.get(selectedRow).id());
    }

    /**
     * Returns at most {@code rowCapacity} entries for the renderer.
     */
    public Viewport viewport(int rowCapacity) {
        if (rowCapacity <= 0) throw new IllegalArgumentException("rowCapacity must be positive");
        if (matches.isEmpty()) return new Viewport(0, 0, List.of(), false, false);

        int start = Math.max(0, Math.min(firstVisibleRow, matches.size() - 1));
        int end = Math.min(matches.size(), start + rowCapacity);
        return new Viewport(
            start,
            matches.size(),
            matches.subList(start, end),
            start > 0,
            end < matches.size()
        );
    }

    public void openEntry(CompendiumEntryId id) {
        CompendiumEntryId required = Objects.requireNonNull(id, "id");
        boolean exists = snapshot.stream().anyMatch(entry -> entry.id().equals(required));
        if (!exists) throw new IllegalArgumentException("entry is not present in the client snapshot: " + required);
        openEntry = required;
    }

    public void backToList() {
        openEntry = null;
    }

    private void refreshFromTop() {
        matches = resolveMatches();
        firstVisibleRow = 0;
        selectedRow = -1;
    }

    private List<CompendiumClientEntry> resolveMatches() {
        int searchLimit = Math.max(1, snapshot.size());
        return filter.filter(searchIndex.search(query, searchLimit));
    }

    public record Viewport(
        int firstIndex,
        int totalMatches,
        List<CompendiumClientEntry> entries,
        boolean hasPrevious,
        boolean hasNext
    ) {
        public Viewport {
            if (firstIndex < 0) throw new IllegalArgumentException("firstIndex must not be negative");
            if (totalMatches < 0) throw new IllegalArgumentException("totalMatches must not be negative");
            entries = List.copyOf(Objects.requireNonNull(entries, "entries"));
        }
    }
}
