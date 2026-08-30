package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumBrowserModel;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumClientEntry;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumClientSnapshot;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumDebugField;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumDebugPanelModel;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumEditorialDisplayModel;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumFilterControls;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumFilterState;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumNotesModel;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumPageModel;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumPersonalView;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumRelationLink;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumRelationPanelState;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumScreenLayout;
import dev.gustavopere.rpgskilltree.compendium.client.CompendiumScreenSession;
import dev.gustavopere.rpgskilltree.compendium.client.render.CompendiumStaticPreviewPolicy;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

/**
 * Physical-client Compendium browser screen backed exclusively by the visibility-filtered client snapshot.
 * Stage 10.13 owns transport, snapshot installation and persistence of personal notes.
 */
public final class CompendiumScreen extends Screen {
    private static final int BACKGROUND = 0xFF090C12;
    private static final int PANEL = 0xE6111722;
    private static final int PANEL_ALT = 0xE6171E2A;
    private static final int ROW = 0xFF1B2430;
    private static final int ROW_HOVER = 0xFF2A3948;
    private static final int ROW_SELECTED = 0xFF40546A;
    private static final int TEXT = 0xFFF0F3F7;
    private static final int MUTED = 0xFF9EA9B8;
    private static final int ACCENT = 0xFFD9C47C;
    private static final int POSITIVE = 0xFF9BC58E;
    private static final int BACK_BUTTON_WIDTH = 78;
    private static final int BACK_BUTTON_HEIGHT = 18;
    private static final int FAVORITE_BUTTON_WIDTH = 88;
    private static final int FAVORITE_BUTTON_HEIGHT = 18;
    private static final int NOTES_BUTTON_WIDTH = 74;
    private static final int NOTES_BUTTON_HEIGHT = 18;
    private static final int RELATIONS_BUTTON_WIDTH = 84;
    private static final int RELATIONS_BUTTON_HEIGHT = 18;
    private static final int RELATION_ROW_HEIGHT = 24;
    private static final int HEADER_BUTTON_GAP = 4;
    private static final int LIST_PADDING = 6;
    private static final int FILTER_BUTTON_GAP = 4;
    private static final int SCROLL_ROWS_PER_NOTCH = 3;
    private static final int PREVIEW_MIN_SIZE = 72;
    private static final int PREVIEW_MAX_SIZE = 116;
    private static final int PREVIEW_TEXT_GAP = 4;

    private final CompendiumScreenSession session;
    private final CompendiumEntityPreviewRenderer entityPreview = new CompendiumEntityPreviewRenderer();
    private final CompendiumRelationPanelState relationPanel = new CompendiumRelationPanelState();
    private CompendiumScreenLayout layout;
    private EditBox searchBox;
    private Button allViewButton;
    private Button favoritesViewButton;
    private Button recentViewButton;
    private Button kindFilterButton;
    private Button discoveredFilterButton;
    private Button favoriteButton;
    private Button notesButton;
    private Button relationsButton;
    private CompendiumNotesEditBox notesEditor;
    private boolean debugDetailsEnabled;
    private boolean previewDragging;

    public CompendiumScreen(CompendiumClientSnapshot snapshot) {
        this(snapshot, ClientCompendiumState.personalState());
    }

    public CompendiumScreen(CompendiumClientSnapshot snapshot, CompendiumNotesModel notes) {
        super(Component.translatable("screen.rpgskilltree.compendium.title"));
        this.session = new CompendiumScreenSession(
            Objects.requireNonNull(snapshot, "snapshot"),
            Objects.requireNonNull(notes, "notes")
        );
    }

    @Override
    protected void init() {
        super.init();
        searchBox = null;
        allViewButton = null;
        favoritesViewButton = null;
        recentViewButton = null;
        kindFilterButton = null;
        discoveredFilterButton = null;
        favoriteButton = null;
        notesButton = null;
        relationsButton = null;
        notesEditor = null;
        relationPanel.close();
        previewDragging = false;
        if (width < CompendiumScreenLayout.MIN_SCREEN_WIDTH || height < CompendiumScreenLayout.MIN_SCREEN_HEIGHT) {
            layout = null;
            entityPreview.clear();
            return;
        }

        layout = CompendiumScreenLayout.calculate(width, height);
        CompendiumScreenLayout.Rect search = layout.search();
        searchBox = new EditBox(
            font,
            search.x(),
            search.y(),
            search.width(),
            search.height(),
            Component.translatable("screen.rpgskilltree.compendium.search")
        );
        searchBox.setMaxLength(160);
        searchBox.setValue(session.query());
        searchBox.setHint(Component.translatable("screen.rpgskilltree.compendium.search_hint"));
        searchBox.setResponder(session::setQuery);
        addRenderableWidget(searchBox);

        CompendiumScreenLayout.Rect personalToolbar = layout.personalToolbar();
        int personalUsable = Math.max(3, personalToolbar.width() - FILTER_BUTTON_GAP * 2);
        int allWidth = Math.max(1, personalUsable / 3);
        int favoritesWidth = Math.max(1, (personalUsable - allWidth) / 2);
        int recentWidth = Math.max(1, personalUsable - allWidth - favoritesWidth);
        allViewButton = Button.builder(
            Component.translatable("screen.rpgskilltree.compendium.view.all"),
            button -> selectPersonalView(CompendiumPersonalView.ALL)
        ).bounds(personalToolbar.x(), personalToolbar.y(), allWidth, personalToolbar.height()).build();
        favoritesViewButton = Button.builder(
            Component.translatable("screen.rpgskilltree.compendium.view.favorites"),
            button -> selectPersonalView(CompendiumPersonalView.FAVORITES)
        ).bounds(
            personalToolbar.x() + allWidth + FILTER_BUTTON_GAP,
            personalToolbar.y(),
            favoritesWidth,
            personalToolbar.height()
        ).build();
        recentViewButton = Button.builder(
            Component.translatable("screen.rpgskilltree.compendium.view.recent"),
            button -> selectPersonalView(CompendiumPersonalView.RECENT)
        ).bounds(
            personalToolbar.right() - recentWidth,
            personalToolbar.y(),
            recentWidth,
            personalToolbar.height()
        ).build();
        addRenderableWidget(allViewButton);
        addRenderableWidget(favoritesViewButton);
        addRenderableWidget(recentViewButton);
        refreshPersonalViewButtons();

        CompendiumScreenLayout.Rect toolbar = layout.toolbar();
        int firstWidth = Math.max(1, (toolbar.width() - FILTER_BUTTON_GAP) / 2);
        int secondWidth = Math.max(1, toolbar.width() - FILTER_BUTTON_GAP - firstWidth);
        kindFilterButton = Button.builder(kindFilterLabel(), button -> {
            session.setFilter(CompendiumFilterControls.cycleKind(session.filter()));
            refreshFilterButtonLabels();
        }).bounds(toolbar.x(), toolbar.y(), firstWidth, toolbar.height()).build();
        discoveredFilterButton = Button.builder(discoveredFilterLabel(), button -> {
            session.setFilter(CompendiumFilterControls.cycleDiscovered(session.filter()));
            refreshFilterButtonLabels();
        }).bounds(
            toolbar.x() + firstWidth + FILTER_BUTTON_GAP,
            toolbar.y(),
            secondWidth,
            toolbar.height()
        ).build();
        addRenderableWidget(kindFilterButton);
        addRenderableWidget(discoveredFilterButton);

        CompendiumScreenLayout.Rect detail = layout.detailBody();
        int headerButtonY = detail.y() + 10;
        favoriteButton = Button.builder(favoriteLabel(), button -> {
            session.toggleCurrentEntryFavorite();
            refreshFavoriteButton();
        }).bounds(
            detail.right() - FAVORITE_BUTTON_WIDTH - 10,
            headerButtonY,
            FAVORITE_BUTTON_WIDTH,
            FAVORITE_BUTTON_HEIGHT
        ).build();
        favoriteButton.visible = false;
        addRenderableWidget(favoriteButton);

        int relationsButtonX = layout.splitPanes()
            ? detail.right() - FAVORITE_BUTTON_WIDTH - NOTES_BUTTON_WIDTH - RELATIONS_BUTTON_WIDTH
                - HEADER_BUTTON_GAP * 2 - 10
            : detail.x() + 10;
        int relationsButtonY = layout.splitPanes()
            ? headerButtonY
            : headerButtonY + BACK_BUTTON_HEIGHT + HEADER_BUTTON_GAP;
        relationsButton = Button.builder(relationsButtonLabel(), button -> {
            CompendiumClientEntry current = session.currentEntry().orElse(null);
            if (current == null) return;
            List<CompendiumRelationLink> links = session.currentRelationLinks();
            closeNotesPanel();
            relationPanel.toggle(current.id(), links.size());
            entityPreview.clear();
            refreshRelationsButton();
        }).bounds(
            relationsButtonX,
            relationsButtonY,
            RELATIONS_BUTTON_WIDTH,
            RELATIONS_BUTTON_HEIGHT
        ).build();
        relationsButton.visible = false;
        addRenderableWidget(relationsButton);

        int notesButtonX = layout.splitPanes()
            ? detail.right() - FAVORITE_BUTTON_WIDTH - NOTES_BUTTON_WIDTH - HEADER_BUTTON_GAP - 10
            : relationsButtonX + RELATIONS_BUTTON_WIDTH + HEADER_BUTTON_GAP;
        int notesButtonY = relationsButtonY;
        notesButton = Button.builder(notesButtonLabel(), button -> {
            if (notesEditor == null) return;
            closeRelationsPanel();
            notesEditor.setPanelOpen(!notesEditor.panelOpen());
            entityPreview.clear();
            refreshNotesButton();
        }).bounds(
            notesButtonX,
            notesButtonY,
            NOTES_BUTTON_WIDTH,
            NOTES_BUTTON_HEIGHT
        ).build();
        notesButton.visible = false;
        addRenderableWidget(notesButton);

        int editorTopOffset = layout.splitPanes()
            ? 38
            : 10 + BACK_BUTTON_HEIGHT + HEADER_BUTTON_GAP + NOTES_BUTTON_HEIGHT + 8;
        int editorY = detail.y() + editorTopOffset;
        int editorHeight = Math.max(18, detail.bottom() - editorY - 10);
        notesEditor = new CompendiumNotesEditBox(
            font,
            detail.x() + 10,
            editorY,
            Math.max(20, detail.width() - 20),
            editorHeight,
            session
        );
        addRenderableWidget(notesEditor);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, width, height, BACKGROUND);
        if (layout == null) {
            renderTooSmall(graphics);
            super.render(graphics, mouseX, mouseY, partialTick);
            return;
        }

        if (notesEditor != null) notesEditor.sync();
        syncRelationPanel();
        refreshFavoriteButton();
        refreshNotesButton();
        refreshRelationsButton();
        refreshPersonalViewButtons();
        renderHeader(graphics);
        renderToolbar(graphics);
        if (layout.splitPanes()) {
            renderList(graphics, mouseX, mouseY, layout.listBody());
            renderDetail(graphics, mouseX, mouseY, layout.detailBody(), false);
        } else if (session.showingDetail()) {
            renderDetail(graphics, mouseX, mouseY, layout.detailBody(), true);
        } else {
            entityPreview.clear();
            renderList(graphics, mouseX, mouseY, layout.listBody());
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void renderTooSmall(GuiGraphics graphics) {
        String titleText = title.getString();
        graphics.drawString(font, titleText, Math.max(8, (width - font.width(titleText)) / 2), 20, ACCENT);
        Component message = Component.translatable(
            "screen.rpgskilltree.compendium.too_small",
            CompendiumScreenLayout.MIN_SCREEN_WIDTH,
            CompendiumScreenLayout.MIN_SCREEN_HEIGHT
        );
        graphics.drawString(font, message, 8, 40, TEXT);
    }

    private void renderHeader(GuiGraphics graphics) {
        CompendiumScreenLayout.Rect header = layout.header();
        graphics.fill(header.x(), header.y(), header.right(), header.bottom(), PANEL);
        graphics.drawString(font, title, header.x() + 8, header.y() + 8, ACCENT);
        Component results = Component.translatable("screen.rpgskilltree.compendium.results", session.totalMatches());
        graphics.drawString(font, results, header.right() - font.width(results) - 8, header.y() + 8, MUTED);
    }

    private void renderToolbar(GuiGraphics graphics) {
        CompendiumScreenLayout.Rect personalToolbar = layout.personalToolbar();
        graphics.fill(
            personalToolbar.x(),
            personalToolbar.y(),
            personalToolbar.right(),
            personalToolbar.bottom(),
            PANEL
        );
        CompendiumScreenLayout.Rect toolbar = layout.toolbar();
        graphics.fill(toolbar.x(), toolbar.y(), toolbar.right(), toolbar.bottom(), PANEL);
    }

    private void renderList(GuiGraphics graphics, int mouseX, int mouseY, CompendiumScreenLayout.Rect body) {
        graphics.fill(body.x(), body.y(), body.right(), body.bottom(), PANEL);
        CompendiumBrowserModel.Viewport viewport = session.viewport(layout.visibleRows());
        if (viewport.entries().isEmpty()) {
            Component empty = Component.translatable(
                session.query().isBlank()
                    ? emptyPersonalViewKey()
                    : "screen.rpgskilltree.compendium.no_results"
            );
            graphics.drawString(font, empty, body.x() + 10, body.y() + 10, MUTED);
            return;
        }

        CompendiumClientEntry selected = session.selectedEntry().orElse(null);
        int y = body.y();
        for (CompendiumClientEntry entry : viewport.entries()) {
            int bottom = Math.min(body.bottom(), y + CompendiumScreenLayout.ROW_HEIGHT);
            boolean hovered = mouseX >= body.x() && mouseX < body.right() && mouseY >= y && mouseY < bottom;
            int rowColor = entry.equals(selected) ? ROW_SELECTED : hovered ? ROW_HOVER : ROW;
            graphics.fill(body.x(), y, body.right(), bottom, rowColor);
            int available = Math.max(20, body.width() - LIST_PADDING * 2);
            graphics.drawString(
                font,
                fitToWidth(entry.displayName(), available),
                body.x() + LIST_PADDING,
                y + 3,
                entry.discovered() ? TEXT : MUTED
            );
            graphics.drawString(
                font,
                fitToWidth(entry.sourceModId(), available),
                body.x() + LIST_PADDING,
                y + 13,
                MUTED
            );
            y += CompendiumScreenLayout.ROW_HEIGHT;
            if (y >= body.bottom()) break;
        }
        if (viewport.hasPrevious()) graphics.drawString(font, "▲", body.right() - 12, body.y() + 3, MUTED);
        if (viewport.hasNext()) graphics.drawString(font, "▼", body.right() - 12, body.bottom() - 11, MUTED);
    }

    private void renderDetail(
        GuiGraphics graphics,
        int mouseX,
        int mouseY,
        CompendiumScreenLayout.Rect body,
        boolean compactBack
    ) {
        graphics.fill(body.x(), body.y(), body.right(), body.bottom(), PANEL_ALT);
        int x = body.x() + 10;
        int y = body.y() + 10;

        if (compactBack) {
            boolean hovered = mouseX >= x && mouseX < x + BACK_BUTTON_WIDTH
                && mouseY >= y && mouseY < y + BACK_BUTTON_HEIGHT;
            graphics.fill(x, y, x + BACK_BUTTON_WIDTH, y + BACK_BUTTON_HEIGHT, hovered ? ROW_HOVER : ROW);
            graphics.drawString(font, Component.translatable("screen.rpgskilltree.compendium.back"), x + 6, y + 5, TEXT);
            y += BACK_BUTTON_HEIGHT + HEADER_BUTTON_GAP + NOTES_BUTTON_HEIGHT + 8;
        }

        var entry = session.currentEntry();
        if (entry.isEmpty()) {
            entityPreview.clear();
            graphics.drawString(
                font,
                Component.translatable("screen.rpgskilltree.compendium.select_entry"),
                x,
                y,
                MUTED
            );
            return;
        }

        CompendiumClientEntry current = entry.orElseThrow();
        if (notesEditor != null && notesEditor.panelOpen()) {
            entityPreview.clear();
            renderNotesPanelHeader(graphics, current, x, y, body, compactBack);
            return;
        }
        if (relationPanel.isOpen()) {
            entityPreview.clear();
            renderRelationsPanel(graphics, current, mouseX, mouseY, x, y, body, compactBack);
            return;
        }

        var page = session.currentPage();
        String detailTitle = page
            .filter(CompendiumPageModel::detailsVisible)
            .map(CompendiumEditorialDisplayModel::from)
            .map(CompendiumEditorialDisplayModel::title)
            .orElse(current.displayName());

        int titleWidth = body.width() - 20;
        if (!compactBack) {
            titleWidth = Math.max(
                20,
                titleWidth - FAVORITE_BUTTON_WIDTH - NOTES_BUTTON_WIDTH - RELATIONS_BUTTON_WIDTH - HEADER_BUTTON_GAP * 3
            );
        }
        graphics.drawString(font, fitToWidth(detailTitle, titleWidth), x, y, ACCENT);
        y += 13;
        graphics.drawString(
            font,
            Component.translatable("screen.rpgskilltree.compendium.source", current.sourceModId()),
            x,
            y,
            MUTED
        );
        y += 13;
        graphics.drawString(
            font,
            Component.translatable(
                current.discovered()
                    ? "screen.rpgskilltree.compendium.discovered"
                    : "screen.rpgskilltree.compendium.undiscovered"
            ),
            x,
            y,
            current.discovered() ? POSITIVE : MUTED
        );
        y += 16;

        if (page.isEmpty()) {
            entityPreview.clear();
            graphics.drawString(font, Component.translatable("screen.rpgskilltree.compendium.shell"), x, y, MUTED);
            return;
        }

        CompendiumPageModel model = page.orElseThrow();
        y = renderDebugProvenance(graphics, model, x, y, body.width() - 24, body.bottom());
        if (!model.detailsVisible()) {
            entityPreview.clear();
            if (y + 11 < body.bottom()) {
                graphics.drawString(
                    font,
                    Component.translatable("screen.rpgskilltree.compendium.details_hidden"),
                    x,
                    y,
                    MUTED
                );
            }
            return;
        }

        y = renderEntityPreview(graphics, current, x, y, body.width() - 20, body.bottom());
        y = renderStaticPreview(graphics, current, x, y, body.width() - 20, body.bottom());
        y = renderEditorial(
            graphics,
            CompendiumEditorialDisplayModel.from(model),
            x,
            y,
            body.width() - 20,
            body.bottom()
        );

        for (CompendiumSection section : model.sections()) {
            if (section.facts().isEmpty()) continue;
            if (y + 12 >= body.bottom()) break;
            graphics.drawString(font, humanize(section.sectionId()), x, y, ACCENT);
            y += 12;
            for (CompendiumFact<?> fact : section.facts()) {
                if (!fact.isConfirmed()) continue;
                if (y + 11 >= body.bottom()) {
                    graphics.drawString(font, "…", x, body.bottom() - 11, MUTED);
                    return;
                }
                String value = String.valueOf(fact.value());
                if (fact.unit() != null) value = value + " " + fact.unit();
                graphics.drawString(
                    font,
                    fitToWidth(humanize(fact.factKey()) + ": " + value, body.width() - 24),
                    x + 6,
                    y,
                    TEXT
                );
                y += 11;
            }
            y += 4;
        }
    }

    private void renderNotesPanelHeader(
        GuiGraphics graphics,
        CompendiumClientEntry current,
        int x,
        int y,
        CompendiumScreenLayout.Rect body,
        boolean compactBack
    ) {
        if (!compactBack) {
            int titleWidth = Math.max(
                20,
                body.width() - 20 - FAVORITE_BUTTON_WIDTH - NOTES_BUTTON_WIDTH - RELATIONS_BUTTON_WIDTH
                    - HEADER_BUTTON_GAP * 3
            );
            graphics.drawString(font, fitToWidth(current.displayName(), titleWidth), x, y, ACCENT);
            y += 14;
        }
        Component title = Component.translatable("screen.rpgskilltree.compendium.notes.title");
        if (y + font.lineHeight < (notesEditor == null ? body.bottom() : notesEditor.getY())) {
            graphics.drawString(font, fitToWidth(title.getString(), body.width() - 20), x, y, MUTED);
        }
    }

    private void renderRelationsPanel(
        GuiGraphics graphics,
        CompendiumClientEntry current,
        int mouseX,
        int mouseY,
        int x,
        int y,
        CompendiumScreenLayout.Rect body,
        boolean compactBack
    ) {
        if (!compactBack) {
            int titleWidth = Math.max(
                20,
                body.width() - 20 - FAVORITE_BUTTON_WIDTH - NOTES_BUTTON_WIDTH - RELATIONS_BUTTON_WIDTH
                    - HEADER_BUTTON_GAP * 3
            );
            graphics.drawString(font, fitToWidth(current.displayName(), titleWidth), x, y, ACCENT);
            y += 14;
        }

        Component title = Component.translatable("screen.rpgskilltree.compendium.relations.title");
        graphics.drawString(font, fitToWidth(title.getString(), body.width() - 20), x, y, MUTED);
        y += 14;

        List<CompendiumRelationLink> links = session.currentRelationLinks();
        if (links.isEmpty() || y + RELATION_ROW_HEIGHT > body.bottom()) return;

        int capacity = relationRowCapacity(body, compactBack);
        CompendiumRelationPanelState.Viewport viewport = relationPanel.viewport(links.size(), capacity);
        int rowY = y;
        int end = viewport.firstIndex() + viewport.visibleCount();
        for (int index = viewport.firstIndex(); index < end; index++) {
            CompendiumRelationLink link = links.get(index);
            int rowBottom = Math.min(body.bottom(), rowY + RELATION_ROW_HEIGHT);
            boolean hovered = mouseX >= x && mouseX < body.right() - 10
                && mouseY >= rowY && mouseY < rowBottom;
            graphics.fill(x, rowY, body.right() - 10, rowBottom, hovered ? ROW_HOVER : ROW);
            Component type = Component.translatable(relationTypeTranslationKey(link));
            int available = Math.max(20, body.width() - 32);
            graphics.drawString(font, fitToWidth(type.getString(), available), x + 6, rowY + 3, ACCENT);
            graphics.drawString(
                font,
                fitToWidth(link.target().displayName(), available),
                x + 6,
                rowY + 13,
                link.target().discovered() ? TEXT : MUTED
            );
            rowY += RELATION_ROW_HEIGHT;
        }
        if (viewport.hasPrevious()) graphics.drawString(font, "▲", body.right() - 20, y + 3, MUTED);
        if (viewport.hasNext()) graphics.drawString(font, "▼", body.right() - 20, body.bottom() - 11, MUTED);
    }

    private int renderEntityPreview(
        GuiGraphics graphics,
        CompendiumClientEntry current,
        int x,
        int y,
        int maxWidth,
        int bottom
    ) {
        if (current.id().kind() != CompendiumEntryKind.ENTITY) {
            entityPreview.clear();
            return y;
        }

        int availableHeight = bottom - y - 14;
        int size = Math.min(PREVIEW_MAX_SIZE, Math.min(maxWidth, availableHeight));
        if (size < PREVIEW_MIN_SIZE) {
            entityPreview.clear();
            return y;
        }

        entityPreview.sync(current, minecraft == null ? null : minecraft.level);
        int left = x + Math.max(0, (maxWidth - size) / 2);
        int top = y;
        int right = left + size;
        int previewBottom = top + size;
        graphics.fill(left, top, right, previewBottom, ROW);
        boolean rendered = entityPreview.render(graphics, left, top, right, previewBottom);
        if (!rendered) {
            Component fallback = Component.translatable("screen.rpgskilltree.compendium.preview.unavailable");
            String text = fitToWidth(fallback.getString(), Math.max(20, size - 12));
            graphics.drawString(
                font,
                text,
                left + Math.max(6, (size - font.width(text)) / 2),
                top + Math.max(6, (size - font.lineHeight) / 2),
                MUTED
            );
        }

        y = previewBottom + PREVIEW_TEXT_GAP;
        if (y + font.lineHeight < bottom) {
            Component controls = Component.translatable("screen.rpgskilltree.compendium.preview.controls");
            String text = fitToWidth(controls.getString(), maxWidth);
            graphics.drawString(font, text, x + Math.max(0, (maxWidth - font.width(text)) / 2), y, MUTED);
            y += font.lineHeight + PREVIEW_TEXT_GAP;
        }
        return y;
    }

    private int renderStaticPreview(
        GuiGraphics graphics,
        CompendiumClientEntry current,
        int x,
        int y,
        int maxWidth,
        int bottom
    ) {
        CompendiumStaticPreviewPolicy.Request request = CompendiumStaticPreviewPolicy.requestFor(current.id());
        if (request.mode() == CompendiumStaticPreviewPolicy.Mode.NONE) return y;

        if (request.mode() == CompendiumStaticPreviewPolicy.Mode.METADATA_ONLY) {
            if (y + font.lineHeight < bottom) {
                Component message = Component.translatable("screen.rpgskilltree.compendium.preview.metadata_only");
                graphics.drawString(font, fitToWidth(message.getString(), maxWidth), x, y, MUTED);
                return y + font.lineHeight + PREVIEW_TEXT_GAP;
            }
            return y;
        }

        int availableHeight = bottom - y - 14;
        int size = Math.min(PREVIEW_MIN_SIZE, Math.min(maxWidth, availableHeight));
        if (size < 32) return y;

        int left = x + Math.max(0, (maxWidth - size) / 2);
        int top = y;
        int right = left + size;
        int previewBottom = top + size;
        graphics.fill(left, top, right, previewBottom, ROW);

        CompendiumStaticPreviewRenderer.RenderResult result = CompendiumStaticPreviewRenderer.render(
            graphics,
            request,
            left,
            top,
            right,
            previewBottom
        );
        if (result == CompendiumStaticPreviewRenderer.RenderResult.FALLBACK) {
            Component fallback = Component.translatable("screen.rpgskilltree.compendium.preview.static_unavailable");
            String text = fitToWidth(fallback.getString(), Math.max(20, size - 12));
            graphics.drawString(
                font,
                text,
                left + Math.max(6, (size - font.width(text)) / 2),
                top + Math.max(6, (size - font.lineHeight) / 2),
                MUTED
            );
        }

        y = previewBottom + PREVIEW_TEXT_GAP;
        if (result == CompendiumStaticPreviewRenderer.RenderResult.RENDERED_ITEM && y + font.lineHeight < bottom) {
            Component caption = Component.translatable("screen.rpgskilltree.compendium.preview.registry_item");
            String text = fitToWidth(caption.getString(), maxWidth);
            graphics.drawString(font, text, x + Math.max(0, (maxWidth - font.width(text)) / 2), y, MUTED);
            y += font.lineHeight + PREVIEW_TEXT_GAP;
        }
        return y;
    }

    private int renderEditorial(
        GuiGraphics graphics,
        CompendiumEditorialDisplayModel editorial,
        int x,
        int y,
        int maxWidth,
        int bottom
    ) {
        if (editorial.blocks().isEmpty()) return y;
        int textWidth = Math.max(20, maxWidth - 6);
        for (CompendiumEditorialDisplayModel.DisplayBlock block : editorial.blocks()) {
            if (!"summary".equals(block.sectionId())) {
                if (y + font.lineHeight >= bottom) return bottom;
                graphics.drawString(font, fitToWidth(block.sectionId(), maxWidth), x, y, ACCENT);
                y += font.lineHeight + 2;
            }

            for (var line : font.split(Component.literal(block.text()), textWidth)) {
                if (y + font.lineHeight >= bottom) return bottom;
                graphics.drawString(font, line, x + 6, y, TEXT);
                y += font.lineHeight + 1;
            }

            if (y + font.lineHeight >= bottom) return bottom;
            Component sources = Component.translatable(
                "screen.rpgskilltree.compendium.editorial.sources",
                String.join(", ", block.sourceRefs())
            );
            graphics.drawString(font, fitToWidth(sources.getString(), textWidth), x + 6, y, MUTED);
            y += font.lineHeight + 4;
        }
        return y;
    }

    private int renderDebugProvenance(
        GuiGraphics graphics,
        CompendiumPageModel model,
        int x,
        int y,
        int maxWidth,
        int bottom
    ) {
        var fields = CompendiumDebugPanelModel.fields(model, debugDetailsEnabled);
        if (fields.isEmpty()) return y;
        if (y + 12 >= bottom) return y;

        graphics.drawString(
            font,
            Component.translatable("screen.rpgskilltree.compendium.debug.title"),
            x,
            y,
            ACCENT
        );
        y += 12;
        for (CompendiumDebugField field : fields) {
            if (y + 11 >= bottom) {
                graphics.drawString(font, "…", x, bottom - 11, MUTED);
                return bottom;
            }
            Component line = Component.translatable(field.translationKey(), field.value());
            graphics.drawString(font, fitToWidth(line.getString(), maxWidth), x + 6, y, MUTED);
            y += 11;
        }
        return y + 4;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) return true;
        if (button != GLFW.GLFW_MOUSE_BUTTON_LEFT || layout == null) return false;
        if (previewInteractive() && entityPreview.contains(mouseX, mouseY)) {
            previewDragging = true;
            return true;
        }
        if (!layout.splitPanes() && session.showingDetail()) {
            int backX = layout.detailBody().x() + 10;
            int backY = layout.detailBody().y() + 10;
            if (mouseX >= backX && mouseX < backX + BACK_BUTTON_WIDTH
                && mouseY >= backY && mouseY < backY + BACK_BUTTON_HEIGHT) {
                session.backToList();
                closeNotesPanel();
                closeRelationsPanel();
                entityPreview.clear();
                return true;
            }
        }
        if (relationPanel.isOpen() && clickRelationRow(mouseX, mouseY)) {
            entityPreview.clear();
            return true;
        }
        if (!layout.splitPanes() && session.showingDetail()) return false;

        CompendiumScreenLayout.Rect body = layout.listBody();
        if (!contains(body, mouseX, mouseY)) return false;
        int visibleRow = (int) ((mouseY - body.y()) / CompendiumScreenLayout.ROW_HEIGHT);
        CompendiumBrowserModel.Viewport viewport = session.viewport(layout.visibleRows());
        if (visibleRow < 0 || visibleRow >= viewport.entries().size()) return false;
        session.openVisibleRow(visibleRow, layout.visibleRows());
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && previewDragging) {
            previewDragging = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && previewDragging && previewInteractive()) {
            entityPreview.drag(dragX, dragY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (layout != null && scrollY != 0 && relationPanel.isOpen() && contains(layout.detailBody(), mouseX, mouseY)) {
            List<CompendiumRelationLink> links = session.currentRelationLinks();
            int capacity = relationRowCapacity(layout.detailBody(), !layout.splitPanes());
            relationPanel.scrollRows(
                scrollY > 0 ? -SCROLL_ROWS_PER_NOTCH : SCROLL_ROWS_PER_NOTCH,
                links.size(),
                capacity
            );
            return true;
        }
        if (layout != null && scrollY != 0 && previewInteractive() && entityPreview.contains(mouseX, mouseY)) {
            entityPreview.zoom(scrollY);
            return true;
        }
        if (layout != null && scrollY != 0 && (layout.splitPanes() || !session.showingDetail())
            && contains(layout.listBody(), mouseX, mouseY)) {
            session.scrollRows(scrollY > 0 ? -SCROLL_ROWS_PER_NOTCH : SCROLL_ROWS_PER_NOTCH);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_F3) {
            debugDetailsEnabled = !debugDetailsEnabled;
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && notesEditor != null && notesEditor.panelOpen()) {
            closeNotesPanel();
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && relationPanel.isOpen()) {
            closeRelationsPanel();
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && layout != null && !layout.splitPanes() && session.showingDetail()) {
            session.backToList();
            entityPreview.clear();
            return true;
        }

        boolean textInputFocused = (searchBox != null && searchBox.isFocused())
            || (notesEditor != null && notesEditor.isFocused());
        boolean listActive = layout != null && (layout.splitPanes() || !session.showingDetail());
        if (listActive && !textInputFocused && keyCode == GLFW.GLFW_KEY_UP) {
            session.moveSelection(-1, layout.visibleRows());
            return true;
        }
        if (listActive && !textInputFocused && keyCode == GLFW.GLFW_KEY_DOWN) {
            session.moveSelection(1, layout.visibleRows());
            return true;
        }
        if (listActive
            && !textInputFocused
            && (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER)
            && !(getFocused() instanceof Button)
            && session.selectedEntry().isPresent()) {
            session.openSelectedEntry();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    boolean debugDetailsEnabled() {
        return debugDetailsEnabled;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void removed() {
        previewDragging = false;
        closeNotesPanel();
        closeRelationsPanel();
        entityPreview.clear();
        super.removed();
    }

    private boolean previewInteractive() {
        return layout != null
            && (notesEditor == null || !notesEditor.panelOpen())
            && !relationPanel.isOpen()
            && (layout.splitPanes() || session.showingDetail());
    }

    private void closeNotesPanel() {
        if (notesEditor != null) notesEditor.setPanelOpen(false);
        refreshNotesButton();
    }

    private void closeRelationsPanel() {
        relationPanel.close();
        refreshRelationsButton();
    }

    private void syncRelationPanel() {
        CompendiumClientEntry current = session.currentEntry().orElse(null);
        if (current == null) {
            relationPanel.close();
            return;
        }
        relationPanel.sync(current.id(), session.currentRelationLinks().size());
    }

    private boolean clickRelationRow(double mouseX, double mouseY) {
        if (layout == null || !contains(layout.detailBody(), mouseX, mouseY)) return false;
        List<CompendiumRelationLink> links = session.currentRelationLinks();
        if (links.isEmpty()) return false;

        CompendiumScreenLayout.Rect body = layout.detailBody();
        boolean compactBack = !layout.splitPanes();
        int top = relationRowsTop(body, compactBack);
        if (mouseY < top || mouseX < body.x() + 10 || mouseX >= body.right() - 10) return false;
        int visibleRow = (int) ((mouseY - top) / RELATION_ROW_HEIGHT);
        int capacity = relationRowCapacity(body, compactBack);
        CompendiumRelationPanelState.Viewport viewport = relationPanel.viewport(links.size(), capacity);
        if (visibleRow < 0 || visibleRow >= viewport.visibleCount()) return false;
        session.openCurrentRelation(viewport.firstIndex() + visibleRow);
        syncRelationPanel();
        return true;
    }

    private int relationRowsTop(CompendiumScreenLayout.Rect body, boolean compactBack) {
        int y = body.y() + 10;
        if (compactBack) {
            y += BACK_BUTTON_HEIGHT + HEADER_BUTTON_GAP + NOTES_BUTTON_HEIGHT + 8;
        } else {
            y += 14;
        }
        return y + 14;
    }

    private int relationRowCapacity(CompendiumScreenLayout.Rect body, boolean compactBack) {
        int available = body.bottom() - relationRowsTop(body, compactBack);
        return Math.max(1, available / RELATION_ROW_HEIGHT);
    }

    private void selectPersonalView(CompendiumPersonalView view) {
        session.setPersonalView(view);
        refreshPersonalViewButtons();
    }

    private void refreshPersonalViewButtons() {
        CompendiumPersonalView current = session.personalView();
        if (allViewButton != null) allViewButton.active = current != CompendiumPersonalView.ALL;
        if (favoritesViewButton != null) favoritesViewButton.active = current != CompendiumPersonalView.FAVORITES;
        if (recentViewButton != null) recentViewButton.active = current != CompendiumPersonalView.RECENT;
    }

    private void refreshFilterButtonLabels() {
        if (kindFilterButton != null) kindFilterButton.setMessage(kindFilterLabel());
        if (discoveredFilterButton != null) discoveredFilterButton.setMessage(discoveredFilterLabel());
    }

    private void refreshFavoriteButton() {
        if (favoriteButton == null) return;
        boolean hasCurrentEntry = session.currentEntry().isPresent();
        favoriteButton.visible = hasCurrentEntry;
        favoriteButton.active = hasCurrentEntry;
        favoriteButton.setMessage(favoriteLabel());
    }

    private void refreshNotesButton() {
        if (notesButton == null) return;
        boolean hasCurrentEntry = session.currentEntry().isPresent();
        notesButton.visible = hasCurrentEntry;
        notesButton.active = hasCurrentEntry;
        if (!hasCurrentEntry && notesEditor != null && notesEditor.panelOpen()) {
            notesEditor.setPanelOpen(false);
        }
        notesButton.setMessage(notesButtonLabel());
    }

    private void refreshRelationsButton() {
        if (relationsButton == null) return;
        CompendiumClientEntry current = session.currentEntry().orElse(null);
        int relationCount = current == null ? 0 : session.currentRelationLinks().size();
        boolean available = current != null && relationCount > 0;
        relationsButton.visible = available;
        relationsButton.active = available;
        if (!available && relationPanel.isOpen()) relationPanel.close();
        relationsButton.setMessage(relationsButtonLabel());
    }

    private Component favoriteLabel() {
        return Component.translatable(
            session.isCurrentEntryFavorite()
                ? "screen.rpgskilltree.compendium.favorite.remove"
                : "screen.rpgskilltree.compendium.favorite.add"
        );
    }

    private Component notesButtonLabel() {
        return Component.translatable(
            notesEditor != null && notesEditor.panelOpen()
                ? "screen.rpgskilltree.compendium.notes.close"
                : "screen.rpgskilltree.compendium.notes.open"
        );
    }

    private Component relationsButtonLabel() {
        return Component.translatable(
            relationPanel.isOpen()
                ? "screen.rpgskilltree.compendium.relations.close"
                : "screen.rpgskilltree.compendium.relations.open"
        );
    }

    private String relationTypeTranslationKey(CompendiumRelationLink link) {
        return "screen.rpgskilltree.compendium.relations.type."
            + link.type().name().toLowerCase(Locale.ROOT);
    }

    private Component kindFilterLabel() {
        CompendiumFilterState filter = session.filter();
        if (filter.kinds().size() != 1) {
            return Component.translatable("screen.rpgskilltree.compendium.filter.kind.all");
        }
        CompendiumEntryKind kind = filter.kinds().iterator().next();
        return Component.translatable(
            "screen.rpgskilltree.compendium.filter.kind." + kind.name().toLowerCase(Locale.ROOT)
        );
    }

    private Component discoveredFilterLabel() {
        String suffix = switch (session.filter().discovered()) {
            case ANY -> "any";
            case TRUE -> "true";
            case FALSE -> "false";
        };
        return Component.translatable("screen.rpgskilltree.compendium.filter.discovered." + suffix);
    }

    private String emptyPersonalViewKey() {
        return switch (session.personalView()) {
            case ALL -> "screen.rpgskilltree.compendium.empty";
            case FAVORITES -> "screen.rpgskilltree.compendium.empty_favorites";
            case RECENT -> "screen.rpgskilltree.compendium.empty_recent";
        };
    }

    private String fitToWidth(String value, int maxWidth) {
        if (font.width(value) <= maxWidth) return value;
        String suffix = "…";
        int suffixWidth = font.width(suffix);
        if (suffixWidth >= maxWidth) return suffix;
        int end = value.length();
        while (end > 0 && font.width(value.substring(0, end)) + suffixWidth > maxWidth) end--;
        return value.substring(0, end) + suffix;
    }

    private static boolean contains(CompendiumScreenLayout.Rect rect, double x, double y) {
        return x >= rect.x() && x < rect.right() && y >= rect.y() && y < rect.bottom();
    }

    private static String humanize(String key) {
        String normalized = key.replace('_', ' ').replace('.', ' ').trim();
        if (normalized.isEmpty()) return key;
        return normalized.substring(0, 1).toUpperCase(Locale.ROOT) + normalized.substring(1);
    }
}
