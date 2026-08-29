package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Pure interaction state for the Compendium screen.
 *
 * <p>The NeoForge screen owns rendering and input translation only. Search, filters, virtual
 * scrolling, keyboard selection, entry opening and list/detail navigation are kept here so they
 * remain deterministic and testable without constructing a Minecraft client.</p>
 */
public final class CompendiumScreenSession {
    private final CompendiumClientSnapshot snapshot;
    private final CompendiumBrowserModel browser;
    private final CompendiumNotesModel notes;
    private CompendiumPersonalView personalView = CompendiumPersonalView.ALL;

    public CompendiumScreenSession(CompendiumClientSnapshot snapshot) {
        this(snapshot, new CompendiumNotesModel());
    }

    public CompendiumScreenSession(CompendiumClientSnapshot snapshot, CompendiumNotesModel notes) {
        this.snapshot = Objects.requireNonNull(snapshot, "snapshot");
        this.browser = snapshot.newBrowserModel();
        this.notes = Objects.requireNonNull(notes, "notes");
    }

    public String query() {
        return browser.query();
    }

    public CompendiumFilterState filter() {
        return browser.filter();
    }

    public CompendiumPersonalView personalView() {
        return personalView;
    }

    public int totalMatches() {
        return browser.totalMatches();
    }

    public boolean showingDetail() {
        return browser.openEntry().isPresent();
    }

    public Optional<CompendiumClientEntry> selectedEntry() {
        return browser.selectedEntry();
    }

    public CompendiumBrowserModel.Viewport viewport(int rowCapacity) {
        return browser.viewport(rowCapacity);
    }

    public void setQuery(String query) {
        browser.setQuery(query);
    }

    public void setFilter(CompendiumFilterState filter) {
        browser.setFilter(Objects.requireNonNull(filter, "filter"));
    }

    public void setPersonalView(CompendiumPersonalView personalView) {
        this.personalView = Objects.requireNonNull(personalView, "personalView");
        refreshPersonalScope();
    }

    public void scrollRows(int delta) {
        long requested = (long) browser.firstVisibleRow() + delta;
        int clampedRequest = requested < Integer.MIN_VALUE
            ? Integer.MIN_VALUE
            : requested > Integer.MAX_VALUE
                ? Integer.MAX_VALUE
                : (int) requested;
        browser.scrollToRow(clampedRequest);
    }

    public void moveSelection(int delta, int rowCapacity) {
        browser.moveSelection(delta, rowCapacity);
    }

    public void openSelectedEntry() {
        browser.openSelectedEntry();
        recordCurrentEntryOpened();
    }

    public void openVisibleRow(int visibleRow, int rowCapacity) {
        browser.openVisibleRow(visibleRow, rowCapacity);
        recordCurrentEntryOpened();
    }

    public Optional<CompendiumClientEntry> currentEntry() {
        Optional<CompendiumEntryId> openId = browser.openEntry();
        if (openId.isEmpty()) return Optional.empty();
        CompendiumEntryId id = openId.orElseThrow();
        return snapshot.entries().stream().filter(entry -> entry.id().equals(id)).findFirst();
    }

    public Optional<CompendiumPageModel> currentPage() {
        return browser.openEntry().flatMap(snapshot::page);
    }

    public boolean isCurrentEntryFavorite() {
        return currentEntry().map(entry -> notes.isFavorite(entry.id())).orElse(false);
    }

    public void toggleCurrentEntryFavorite() {
        currentEntry().ifPresent(entry -> {
            CompendiumEntryId id = entry.id();
            notes.setFavorite(id, !notes.isFavorite(id));
            if (personalView == CompendiumPersonalView.FAVORITES) refreshPersonalScope();
        });
    }

    public void backToList() {
        browser.backToList();
    }

    private void recordCurrentEntryOpened() {
        browser.openEntry().ifPresent(id -> {
            notes.recordOpened(id);
            if (personalView == CompendiumPersonalView.RECENT) refreshPersonalScope();
        });
    }

    private void refreshPersonalScope() {
        switch (personalView) {
            case ALL -> browser.clearEntryScope();
            case FAVORITES -> browser.setEntryScope(
                snapshot.entries().stream()
                    .map(CompendiumClientEntry::id)
                    .filter(notes::isFavorite)
                    .toList()
            );
            case RECENT -> browser.setEntryScope(notes.recentEntries());
        }
    }
}
