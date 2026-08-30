package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.Objects;
import java.util.Optional;

/** Pure state for the client relation panel, independent from Minecraft rendering. */
public final class CompendiumRelationPanelState {
    private CompendiumEntryId owner;
    private boolean open;
    private int firstVisibleRow;

    public boolean isOpen() {
        return open;
    }

    public Optional<CompendiumEntryId> owner() {
        return Optional.ofNullable(owner);
    }

    public int firstVisibleRow() {
        return firstVisibleRow;
    }

    public void toggle(CompendiumEntryId entryId, int relationCount) {
        Objects.requireNonNull(entryId, "entryId");
        requireCount(relationCount);
        if (relationCount == 0) {
            close();
            return;
        }
        if (!entryId.equals(owner)) {
            owner = entryId;
            firstVisibleRow = 0;
            open = true;
            return;
        }
        open = !open;
        if (!open) firstVisibleRow = 0;
    }

    /**
     * Keeps an open panel attached only to its original entry. Navigation to another entry, or
     * losing all safe relation links for the same entry, closes the panel and resets its scroll.
     */
    public void sync(CompendiumEntryId entryId, int relationCount) {
        Objects.requireNonNull(entryId, "entryId");
        requireCount(relationCount);
        if (!open) return;
        if (!entryId.equals(owner) || relationCount == 0) {
            close();
            return;
        }
        firstVisibleRow = Math.min(firstVisibleRow, Math.max(0, relationCount - 1));
    }

    public void close() {
        open = false;
        owner = null;
        firstVisibleRow = 0;
    }

    public void scrollRows(int delta, int totalRows, int rowCapacity) {
        requireViewport(totalRows, rowCapacity);
        int maxFirst = Math.max(0, totalRows - rowCapacity);
        long requested = (long) firstVisibleRow + delta;
        firstVisibleRow = (int) Math.max(0L, Math.min(requested, maxFirst));
    }

    public Viewport viewport(int totalRows, int rowCapacity) {
        requireViewport(totalRows, rowCapacity);
        int maxFirst = Math.max(0, totalRows - rowCapacity);
        int first = Math.min(firstVisibleRow, maxFirst);
        int visibleCount = Math.min(rowCapacity, Math.max(0, totalRows - first));
        return new Viewport(first, visibleCount, first > 0, first + visibleCount < totalRows);
    }

    private static void requireViewport(int totalRows, int rowCapacity) {
        requireCount(totalRows);
        if (rowCapacity <= 0) throw new IllegalArgumentException("rowCapacity must be positive");
    }

    private static void requireCount(int relationCount) {
        if (relationCount < 0) throw new IllegalArgumentException("relationCount must not be negative");
    }

    public record Viewport(int firstIndex, int visibleCount, boolean hasPrevious, boolean hasNext) {
        public Viewport {
            if (firstIndex < 0) throw new IllegalArgumentException("firstIndex must not be negative");
            if (visibleCount < 0) throw new IllegalArgumentException("visibleCount must not be negative");
        }
    }
}
