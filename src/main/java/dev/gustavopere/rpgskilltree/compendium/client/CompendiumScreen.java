package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.runtime.client.ClientCompendiumState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

/** First client-only Compendium surface backed exclusively by the synchronized client snapshot. */
public final class CompendiumScreen extends Screen {
    private static final int BACK_WIDTH = 92;

    private final CompendiumScreenSession session;
    private CompendiumScreenLayout layout;
    private EditBox searchBox;

    public CompendiumScreen() {
        super(Component.translatable("screen.rpgskilltree.compendium.title"));
        this.session = new CompendiumScreenSession(ClientCompendiumState.get());
    }

    @Override
    protected void init() {
        super.init();
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
        searchBox.setMaxLength(128);
        searchBox.setValue(session.query());
        searchBox.setHint(Component.translatable("screen.rpgskilltree.compendium.search"));
        searchBox.setResponder(session::setQuery);
        addRenderableWidget(searchBox);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, width, height, 0xFF090C12);
        if (layout == null) layout = CompendiumScreenLayout.calculate(width, height);

        renderHeader(graphics);
        renderToolbar(graphics);

        boolean listVisible = layout.splitPanes() || !session.showingDetail();
        if (listVisible) renderList(graphics, mouseX, mouseY);
        if (session.showingDetail()) renderDetail(graphics);

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void renderHeader(GuiGraphics graphics) {
        CompendiumScreenLayout.Rect header = layout.header();
        graphics.fill(header.x(), header.y(), header.right(), header.bottom(), 0xCC111722);
        graphics.drawString(font, title, header.x() + 6, header.y() + 8, 0xFFF2E7B6);
    }

    private void renderToolbar(GuiGraphics graphics) {
        CompendiumScreenLayout.Rect toolbar = layout.toolbar();
        graphics.fill(toolbar.x(), toolbar.y(), toolbar.right(), toolbar.bottom(), 0xCC111722);
        if (session.showingDetail()) {
            graphics.drawString(
                font,
                Component.translatable("screen.rpgskilltree.compendium.back"),
                toolbar.x() + 6,
                toolbar.y() + 6,
                0xFFD9C47C
            );
        }
        Component results = Component.translatable(
            "screen.rpgskilltree.compendium.results",
            session.totalMatches()
        );
        graphics.drawString(
            font,
            results,
            toolbar.right() - font.width(results) - 6,
            toolbar.y() + 6,
            0xFFB7BEC8
        );
    }

    private void renderList(GuiGraphics graphics, int mouseX, int mouseY) {
        CompendiumScreenLayout.Rect body = layout.listBody();
        graphics.fill(body.x(), body.y(), body.right(), body.bottom(), 0xBB0D121A);
        CompendiumBrowserModel.Viewport viewport = session.viewport(layout.visibleRows());
        if (viewport.entries().isEmpty()) {
            graphics.drawString(
                font,
                Component.translatable("screen.rpgskilltree.compendium.empty"),
                body.x() + 8,
                body.y() + 8,
                0xFF9EA9B8
            );
            return;
        }

        for (int index = 0; index < viewport.entries().size(); index++) {
            CompendiumClientEntry entry = viewport.entries().get(index);
            int rowY = body.y() + index * CompendiumScreenLayout.ROW_HEIGHT;
            int rowBottom = Math.min(rowY + CompendiumScreenLayout.ROW_HEIGHT - 1, body.bottom());
            boolean hovered = contains(body, mouseX, mouseY)
                && mouseY >= rowY
                && mouseY < rowBottom;
            graphics.fill(
                body.x() + 2,
                rowY + 1,
                body.right() - 2,
                rowBottom,
                hovered ? 0xFF293544 : 0xFF171F2A
            );
            graphics.drawString(font, entry.displayName(), body.x() + 7, rowY + 4, 0xFFFFFFFF);
            String source = entry.sourceModId();
            graphics.drawString(
                font,
                source,
                body.right() - font.width(source) - 7,
                rowY + 4,
                0xFF8F9BAD
            );
        }
    }

    private void renderDetail(GuiGraphics graphics) {
        CompendiumScreenLayout.Rect body = layout.detailBody();
        graphics.fill(body.x(), body.y(), body.right(), body.bottom(), 0xBB0D121A);
        CompendiumClientEntry entry = session.currentEntry().orElse(null);
        if (entry == null) return;

        int x = body.x() + 10;
        int y = body.y() + 10;
        graphics.drawString(font, entry.displayName(), x, y, 0xFFF2E7B6);
        y += 16;
        graphics.drawString(
            font,
            Component.translatable("screen.rpgskilltree.compendium.source_mod", entry.sourceModId()),
            x,
            y,
            0xFFB7BEC8
        );
        y += 14;
        graphics.drawString(
            font,
            Component.translatable(entry.discovered()
                ? "screen.rpgskilltree.compendium.discovered"
                : "screen.rpgskilltree.compendium.undiscovered"),
            x,
            y,
            entry.discovered() ? 0xFF9BCB99 : 0xFF9EA9B8
        );
        y += 20;

        CompendiumPageModel page = session.currentPage().orElse(null);
        if (page == null || page.sections().isEmpty()) {
            graphics.drawString(
                font,
                Component.translatable("screen.rpgskilltree.compendium.no_details"),
                x,
                y,
                0xFF9EA9B8
            );
            return;
        }

        graphics.drawString(
            font,
            Component.translatable("screen.rpgskilltree.compendium.sections", page.sections().size()),
            x,
            y,
            0xFFD0D6E0
        );
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) return true;
        if (button != 0 || layout == null) return false;

        CompendiumScreenLayout.Rect toolbar = layout.toolbar();
        if (session.showingDetail()
            && mouseX >= toolbar.x()
            && mouseX < toolbar.x() + BACK_WIDTH
            && mouseY >= toolbar.y()
            && mouseY < toolbar.bottom()) {
            session.backToList();
            return true;
        }

        boolean listVisible = layout.splitPanes() || !session.showingDetail();
        CompendiumScreenLayout.Rect body = layout.listBody();
        if (!listVisible || !contains(body, mouseX, mouseY)) return false;

        int visibleRow = ((int) mouseY - body.y()) / CompendiumScreenLayout.ROW_HEIGHT;
        CompendiumBrowserModel.Viewport viewport = session.viewport(layout.visibleRows());
        if (visibleRow < 0 || visibleRow >= viewport.entries().size()) return false;
        session.openVisibleRow(visibleRow, layout.visibleRows());
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (layout != null) {
            boolean listVisible = layout.splitPanes() || !session.showingDetail();
            if (listVisible && contains(layout.listBody(), mouseX, mouseY) && scrollY != 0.0) {
                session.scrollRows(scrollY > 0.0 ? -1 : 1);
                return true;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (searchBox != null && searchBox.isFocused()) {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
        if (session.showingDetail()
            && (keyCode == GLFW.GLFW_KEY_BACKSPACE || keyCode == GLFW.GLFW_KEY_LEFT)) {
            session.backToList();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private static boolean contains(CompendiumScreenLayout.Rect rect, double x, double y) {
        return x >= rect.x() && x < rect.right() && y >= rect.y() && y < rect.bottom();
    }
}
