package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumClientEntry;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumNotesModel;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumScreenSession;
import java.util.Objects;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.network.chat.Component;

/**
 * Client-only binding between Minecraft's multiline editor and the Compendium's personal note model.
 * Persistence remains owned by Stage 10.13; this widget only edits the already-installed client-local state.
 */
final class CompendiumNotesEditBox extends MultiLineEditBox {
    private final CompendiumScreenSession session;
    private CompendiumEntryId loadedEntryId;
    private boolean syncing;
    private boolean panelOpen;

    CompendiumNotesEditBox(
        Font font,
        int x,
        int y,
        int width,
        int height,
        CompendiumScreenSession session
    ) {
        super(
            font,
            x,
            y,
            width,
            height,
            Component.translatable("screen.rpgskilltree.compendium.notes.placeholder"),
            Component.translatable("screen.rpgskilltree.compendium.notes.narration")
        );
        this.session = Objects.requireNonNull(session, "session");
        setCharacterLimit(CompendiumNotesModel.MAX_NOTE_CODE_POINTS);
        setValueListener(this::onValueChanged);
        visible = false;
        active = false;
    }

    void setPanelOpen(boolean panelOpen) {
        this.panelOpen = panelOpen;
        if (!panelOpen) setFocused(false);
        sync();
    }

    boolean panelOpen() {
        return panelOpen;
    }

    void sync() {
        CompendiumClientEntry current = session.currentEntry().orElse(null);
        boolean shouldShow = panelOpen && current != null;
        visible = shouldShow;
        active = shouldShow;

        if (!shouldShow) {
            if (current == null) loadedEntryId = null;
            return;
        }

        CompendiumEntryId currentId = current.id();
        if (currentId.equals(loadedEntryId)) return;

        loadedEntryId = currentId;
        syncing = true;
        try {
            setValue(session.currentNote().orElse(""));
        } finally {
            syncing = false;
        }
    }

    private void onValueChanged(String value) {
        if (syncing || loadedEntryId == null) return;
        CompendiumEntryId currentId = session.currentEntry().map(CompendiumClientEntry::id).orElse(null);
        if (!loadedEntryId.equals(currentId)) return;
        session.setCurrentNote(value);
    }
}
