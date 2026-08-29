package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumFact;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

/**
 * Client-only Compendium browser screen.
 *
 * <p>The screen deliberately renders only the visibility-filtered client snapshot. It does not
 * reach into server catalogs or define synchronization semantics; Stage 10.13 owns transport and
 * snapshot installation.</p>
 */
public final class CompendiumScreen extends Screen {
    private static final int BACKGROUND = 0xFF090C12;
    private static final int PANEL = 0xE6111722;
    private static final int PANEL_ALT = 0xE6171E2A;
    private static final int ROW = 0xFF1B2430;
    private static final int ROW_HOVER = 0xFF2A3948;
    private static final int TEXT = 0xFFF0F3F7;
    private static final int MUTED = 0xFF9EA9B8;
    private static final int ACCENT = 0xFFD9C47C;
    private static final int POSITIVE = 0xFF9BC58E;
    private static final int BACK_BUTTON_WIDTH = 78;
    private static final int BACK_BUTTON_HEIGHT = 18;
    private static final int LIST_PADDING = 6;
    private static final int SCROLL_ROWS_PER_NOTCH = 3;

    private final CompendiumScreenSession session;
    private CompendiumScreenLayout layout;
    private EditBox searchBox;

    public CompendiumScreen(CompendiumClientSnapshot snapshot) {
        super(Component.translatable("screen.rpgskilltree.compendium.title"));
        this.session = new CompendiumScreenSession(Objects.requireNonNull(snapshot, "snapshot"));
    }

    @Override
    protected void init() {
        super.init();
        searchBox = null;
        if (width < CompendiumScreenLayout.MIN_SCREEN_WIDTH || height < CompendiumScreenLayout.MIN_SCREEN_HEIGHT) {
            layout = null;
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
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, width, height, BACKGROUND);
        if (layout == null) {
            renderTooSmall(graphics);
            super.render(graphics, mouseX, mouseY, partialTick);
            return;
        }

        renderHeader(graphics);
        renderToolbar(graphics);

        if (layout.splitPanes()) {
            renderList(graphics, mouseX, mouseY, layout.listBody());
            renderDetail(graphics, mouseX, mouseY, layout.detailBody(), false);
        } else if (session.showingDetail()) {
            renderDetail(graphics, mouseX, mouseY, layout.detailBody(), true);
        } else {
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

        Component results = Component.translatable(
            "screen.rpgskilltree.compendium.results",
            session.totalMatches()
        );
        int resultsWidth = font.width(results);
        graphics.drawString(font, results, header.right() - resultsWidth - 8, header.y() + 8, MUTED);
    }

    private void renderToolbar(GuiGraphics graphics) {
        CompendiumScreenLayout.Rect toolbar = layout.toolbar();
        graphics.fill(toolbar.x(), toolbar.y(), toolbar.right(), toolbar.bottom(), PANEL);
        graphics.drawString(
            font,
            Component.translatable("screen.rpgskilltree.compendium.controls"),
            toolbar.x() + 6,
            toolbar.y() + 6,
            MUTED
        );
    }

    private void renderList(
        GuiGraphics graphics,
        int mouseX,
        int mouseY,
        CompendiumScreenLayout.Rect body
    ) {
        graphics.fill(body.x(), body.y(), body.right(), body.bottom(), PANEL);
        CompendiumBrowserModel.Viewport viewport = session.viewport(layout.visibleRows());
        if (viewport.entries().isEmpty()) {
            Component empty = Component.translatable(
                session.query().isBlank()
                    ? "screen.rpgskilltree.compendium.empty"
                    : "screen.rpgskilltree.compendium.no_results"
            );
            graphics.drawString(font, empty, body.x() + 10, body.y() + 10, MUTED);
            return;
        }

        int y = body.y();
        for (CompendiumClientEntry entry : viewport.entries()) {
            int bottom = Math.min(body.bottom(), y + CompendiumScreenLayout.ROW_HEIGHT);
            boolean hovered = mouseX >= body.x() && mouseX < body.right() && mouseY >= y && mouseY < bottom;
            graphics.fill(body.x(), y, body.right(), bottom, hovered ? ROW_HOVER : ROW);

            int available = Math.max(20, body.width() - LIST_PADDING * 2);
            String name = fitToWidth(entry.displayName(), available);
            graphics.drawString(font, name, body.x() + LIST_PADDING, y + 3, entry.discovered() ? TEXT : MUTED);

            String source = fitToWidth(entry.sourceModId(), available);
            graphics.drawString(font, source, body.x() + LIST_PADDING, y + 13, MUTED);
            y += CompendiumScreenLayout.ROW_HEIGHT;
            if (y >= body.bottom()) break;
        }

        if (viewport.hasPrevious()) {
            graphics.drawString(font, "▲", body.right() - 12, body.y() + 3, MUTED);
        }
        if (viewport.hasNext()) {
            graphics.drawString(font, "▼", body.right() - 12, body.bottom() - 11, MUTED);
        }
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
            graphics.drawString(
                font,
                Component.translatable("screen.rpgskilltree.compendium.back"),
                x + 6,
                y + 5,
                TEXT
            );
            y += BACK_BUTTON_HEIGHT + 8;
        }

        var entry = session.currentEntry();
        if (entry.isEmpty()) {
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
        graphics.drawString(font, fitToWidth(current.displayName(), body.width() - 20), x, y, ACCENT);
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

        var page = session.currentPage();
        if (page.isEmpty()) {
            graphics.drawString(
                font,
                Component.translatable("screen.rpgskilltree.compendium.shell"),
                x,
                y,
                MUTED
            );
            return;
        }

        CompendiumPageModel model = page.orElseThrow();
        if (!model.detailsVisible()) {
            graphics.drawString(
                font,
                Component.translatable("screen.rpgskilltree.compendium.details_hidden"),
                x,
                y,
                MUTED
            );
            return;
        }

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
                String line = humanize(fact.factKey()) + ": " + value;
                graphics.drawString(font, fitToWidth(line, body.width() - 24), x + 6, y, TEXT);
                y += 11;
            }
            y += 4;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) return true;
        if (button != GLFW.GLFW_MOUSE_BUTTON_LEFT || layout == null) return false;

        if (!layout.splitPanes() && session.showingDetail()) {
            int backX = layout.detailBody().x() + 10;
            int backY = layout.detailBody().y() + 10;
            if (mouseX >= backX && mouseX < backX + BACK_BUTTON_WIDTH
                && mouseY >= backY && mouseY < backY + BACK_BUTTON_HEIGHT) {
                session.backToList();
                return true;
            }
            return false;
        }

        CompendiumScreenLayout.Rect body = layout.listBody();
        if (!contains(body, mouseX, mouseY)) return false;
        int visibleRow = (int) ((mouseY - body.y()) / CompendiumScreenLayout.ROW_HEIGHT);
        CompendiumBrowserModel.Viewport viewport = session.viewport(layout.visibleRows());
        if (visibleRow < 0 || visibleRow >= viewport.entries().size()) return false;
        session.openVisibleRow(visibleRow, layout.visibleRows());
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (layout != null && scrollY != 0 && (layout.splitPanes() || !session.showingDetail())
            && contains(layout.listBody(), mouseX, mouseY)) {
            session.scrollRows(scrollY > 0 ? -SCROLL_ROWS_PER_NOTCH : SCROLL_ROWS_PER_NOTCH);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && layout != null && !layout.splitPanes() && session.showingDetail()) {
            session.backToList();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private String fitToWidth(String value, int maxWidth) {
        if (font.width(value) <= maxWidth) return value;
        String suffix = "…";
        int suffixWidth = font.width(suffix);
        if (suffixWidth >= maxWidth) return suffix;

        int end = value.length();
        while (end > 0 && font.width(value.substring(0, end)) + suffixWidth > maxWidth) {
            end--;
        }
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
