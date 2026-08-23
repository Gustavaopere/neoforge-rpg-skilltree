package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.core.CharacterLevelCurve;
import dev.gustavopere.rpgskilltree.core.NodeDisplayState;
import dev.gustavopere.rpgskilltree.core.TreeDisplayProjector;
import dev.gustavopere.rpgskilltree.core.TreeDisplayState;
import dev.gustavopere.rpgskilltree.runtime.network.PurchaseNodePayload;
import dev.gustavopere.rpgskilltree.runtime.network.RespecNodePayload;
import dev.gustavopere.rpgskilltree.runtime.network.UnlockClassPayload;
import dev.gustavopere.rpgskilltree.runtime.network.SelectClassChoicePayload;
import dev.gustavopere.rpgskilltree.runtime.network.ClearClassChoicePayload;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

public final class RpgSkillTreeScreen extends Screen {
    private static final double MIN_ZOOM = 0.14;
    private static final double MAX_ZOOM = 1.60;

    private ClientTreeLayout layout;
    private double panX;
    private double panY;
    private double zoom = 0.28;
    private boolean draggingCanvas;
    private List<PaidClassButton> paidClassButtons = List.of();
    private List<ClassChoiceButton> choiceButtons = List.of();

    public RpgSkillTreeScreen() {
        this(ClientTreeLayout.main());
    }

    private RpgSkillTreeScreen(ClientTreeLayout layout) {
        super(Component.translatable("screen.rpgskilltree.title"));
        this.layout = layout;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, width, height, 0xFF090C12);
        TreeDisplayState display = displayState();
        renderEdges(graphics, display);
        renderNodes(graphics, display, mouseX, mouseY);
        renderHud(graphics, display);
        renderPaidClasses(graphics, mouseX, mouseY);
        renderClassChoices(graphics, mouseX, mouseY);
        renderHoveredNode(graphics, display, mouseX, mouseY);
    }

    private TreeDisplayState displayState() {
        return TreeDisplayProjector.project(
            ClientProgressionState.get(),
            layout.graph(),
            layout.definitions(),
            layout.requirements(),
            CharacterLevelCurve.defaultCurve()
        );
    }

    private void renderEdges(GuiGraphics graphics, TreeDisplayState display) {
        for (ClientTreeLayout.Edge edge : layout.edges()) {
            ClientTreeLayout.Node first = layout.node(edge.from());
            ClientTreeLayout.Node second = layout.node(edge.to());
            if (first == null || second == null) continue;
            NodeDisplayState firstState = display.nodes().get(first.id());
            NodeDisplayState secondState = display.nodes().get(second.id());
            boolean active = firstState != null && secondState != null
                && firstState.learned() && secondState.learned();
            int color = active ? 0xFFBBAE6A : 0xFF303744;
            drawLine(
                graphics,
                screenX(first.x()), screenY(first.y()),
                screenX(second.x()), screenY(second.y()),
                color
            );
        }
    }

    private void renderNodes(GuiGraphics graphics, TreeDisplayState display, int mouseX, int mouseY) {
        ClientTreeLayout.Node hovered = nodeAt(mouseX, mouseY);
        for (ClientTreeLayout.Node node : layout.nodes()) {
            NodeDisplayState state = display.nodes().get(node.id());
            if (state == null) continue;
            int radius = nodeRadius(node);
            int x = screenX(node.x());
            int y = screenY(node.y());
            int color = nodeColor(node, state);
            graphics.fill(x - radius, y - radius, x + radius + 1, y + radius + 1, color);

            if (node == hovered) {
                graphics.fill(x - radius - 2, y - radius - 2, x + radius + 3, y - radius, 0xFFFFFFFF);
                graphics.fill(x - radius - 2, y + radius + 1, x + radius + 3, y + radius + 3, 0xFFFFFFFF);
                graphics.fill(x - radius - 2, y - radius, x - radius, y + radius + 1, 0xFFFFFFFF);
                graphics.fill(x + radius + 1, y - radius, x + radius + 3, y + radius + 1, 0xFFFFFFFF);
            }

            if (state.maxRank() > 1 && (zoom >= 0.38 || node.finalTriad())) {
                String rank = state.rank() + "/" + state.maxRank();
                graphics.drawString(font, rank, x - font.width(rank) / 2, y - 4, 0xFFFFFFFF);
            }
        }
    }

    private void renderHud(GuiGraphics graphics, TreeDisplayState display) {
        var state = ClientProgressionState.get();
        var progress = state.characterProgress(CharacterLevelCurve.defaultCurve());
        var classes = state.classProgression().unlockedClassIds().stream().sorted().toList();
        String classSummary = classes.isEmpty() ? "-" : String.join(", ", classes);
        if (classSummary.length() > 44) classSummary = classSummary.substring(0, 41) + "...";

        graphics.fill(8, 8, 360, 102, 0xCC111722);
        graphics.drawString(font, title, 16, 15, 0xFFF2E7B6);
        graphics.drawString(font, Component.translatable(layout.displayKey()), 16, 29, 0xFFD9C47C);
        graphics.drawString(
            font,
            Component.translatable("screen.rpgskilltree.level", progress.level(), progress.xpIntoLevel(), progress.xpToNextLevel()),
            16,
            43,
            0xFFFFFFFF
        );
        graphics.drawString(
            font,
            Component.translatable("screen.rpgskilltree.points", display.availablePoints()),
            16,
            57,
            0xFFFFFFFF
        );
        graphics.drawString(
            font,
            Component.translatable("screen.rpgskilltree.classes", classSummary),
            16,
            71,
            0xFFD0D6E0
        );
        graphics.drawString(font, Component.translatable("screen.rpgskilltree.controls"), 16, 85, 0xFF9EA9B8);
    }

    private void renderPaidClasses(GuiGraphics graphics, int mouseX, int mouseY) {
        var state = ClientProgressionState.get();
        var views = ClientClassCatalog.visibleFor(state);
        if (views.isEmpty()) {
            paidClassButtons = List.of();
            return;
        }

        int panelWidth = 220;
        int x = width - panelWidth - 8;
        int y = 8;
        int rowHeight = 28;
        graphics.fill(x, y, width - 8, y + 24 + rowHeight * views.size(), 0xCC111722);
        graphics.drawString(font, Component.translatable("screen.rpgskilltree.confluences"), x + 8, y + 8, 0xFFF2E7B6);

        List<PaidClassButton> nextButtons = new ArrayList<>();
        int rowY = y + 22;
        for (var view : views) {
            var entry = view.entry();
            var result = view.result();
            int rowX = x + 6;
            int rowW = panelWidth - 12;
            boolean hovered = mouseX >= rowX && mouseX < rowX + rowW && mouseY >= rowY && mouseY < rowY + rowHeight - 2;
            int background = result.unlockable() ? (hovered ? 0xFF426248 : 0xFF334C39) : 0xFF2A303A;
            graphics.fill(rowX, rowY, rowX + rowW, rowY + rowHeight - 2, background);

            String name = Component.translatable(entry.displayKey()).getString();
            graphics.drawString(font, name, rowX + 6, rowY + 4, result.unlockable() ? 0xFFFFFFFF : 0xFFB7BEC8);
            String status = result.unlockable()
                ? Component.translatable("screen.rpgskilltree.bridge_ready", result.bridgeCost()).getString()
                : Component.translatable("screen.rpgskilltree.bridge_missing_points", result.missingBridgePoints()).getString();
            graphics.drawString(font, status, rowX + 6, rowY + 15, result.unlockable() ? 0xFFD7C978 : 0xFF8F98A5);

            nextButtons.add(new PaidClassButton(
                rowX, rowY, rowW, rowHeight - 2,
                entry.definition().classId(),
                result.unlockable()
            ));
            rowY += rowHeight;
        }
        paidClassButtons = List.copyOf(nextButtons);
    }

    private void renderClassChoices(GuiGraphics graphics, int mouseX, int mouseY) {
        var views = ClientChoiceCatalog.visibleFor(ClientProgressionState.get());
        if (views.isEmpty()) {
            choiceButtons = List.of();
            return;
        }
        int panelWidth = 220;
        int rowHeight = 28;
        int panelHeight = 24 + rowHeight * views.size();
        int x = width - panelWidth - 8;
        int y = height - panelHeight - 8;
        graphics.fill(x, y, width - 8, y + panelHeight, 0xCC111722);
        graphics.drawString(font, Component.translatable("screen.rpgskilltree.class_choices"), x + 8, y + 8, 0xFFF2E7B6);

        List<ClassChoiceButton> next = new ArrayList<>();
        int rowY = y + 22;
        for (var view : views) {
            var entry = view.entry();
            int rowX = x + 6;
            int rowW = panelWidth - 12;
            boolean hovered = mouseX >= rowX && mouseX < rowX + rowW && mouseY >= rowY && mouseY < rowY + rowHeight - 2;
            int background = view.selected() ? (hovered ? 0xFF6D5733 : 0xFF5C482A)
                : view.canSelect() ? (hovered ? 0xFF426248 : 0xFF334C39) : 0xFF2A303A;
            graphics.fill(rowX, rowY, rowX + rowW, rowY + rowHeight - 2, background);
            String name = Component.translatable(entry.displayKey()).getString();
            graphics.drawString(font, name, rowX + 6, rowY + 4, 0xFFFFFFFF);
            String status = view.selected()
                ? Component.translatable("screen.rpgskilltree.choice_selected").getString()
                : view.canSelect()
                    ? Component.translatable("screen.rpgskilltree.choice_select").getString()
                    : Component.translatable("screen.rpgskilltree.choice_locked").getString();
            graphics.drawString(font, status, rowX + 6, rowY + 15, view.selected() ? 0xFFE0C67C : 0xFF9EA9B8);
            next.add(new ClassChoiceButton(rowX, rowY, rowW, rowHeight - 2, entry.definition().choiceId(), view.selected(), view.canSelect()));
            rowY += rowHeight;
        }
        choiceButtons = List.copyOf(next);
    }

    private void renderHoveredNode(GuiGraphics graphics, TreeDisplayState display, int mouseX, int mouseY) {
        ClientTreeLayout.Node node = nodeAt(mouseX, mouseY);
        if (node == null) return;
        NodeDisplayState state = display.nodes().get(node.id());
        if (state == null) return;

        List<String> lines = new ArrayList<>();
        lines.add(nodeDisplayName(node));
        String description = nodeDescription(node);
        if (!description.isBlank()) lines.add(description);
        lines.add(node.groupLabel() + "  •  Rank " + state.rank() + "/" + state.maxRank() + "  •  Cost " + state.costPerRank());
        lines.add(state.canPurchase() ? "LMB: purchase" : state.learned() ? "Purchased" : "Locked by path or requirements");
        if (state.canRespec()) lines.add("RMB: respec");
        int boxWidth = 0;
        for (String line : lines) boxWidth = Math.max(boxWidth, font.width(line));
        int x = Math.min(mouseX + 12, width - boxWidth - 18);
        int boxHeight = lines.size() * 11 + 10;
        int y = Math.min(mouseY + 12, height - boxHeight - 4);
        graphics.fill(x - 5, y - 5, x + boxWidth + 7, y + boxHeight, 0xEE10151D);
        int lineY = y;
        for (String line : lines) {
            if (!line.isEmpty()) {
                graphics.drawString(font, line, x, lineY, 0xFFFFFFFF);
            }
            lineY += 11;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        ClassChoiceButton classChoice = classChoiceAt(mouseX, mouseY);
        if (classChoice != null) {
            ResourceLocation choiceId = ResourceLocation.parse(classChoice.choiceId());
            if (button == 0 && !classChoice.selected() && classChoice.canSelect()) {
                PacketDistributor.sendToServer(new SelectClassChoicePayload(choiceId));
                return true;
            }
            if (button == 1 && classChoice.selected()) {
                PacketDistributor.sendToServer(new ClearClassChoicePayload(choiceId));
                return true;
            }
            return true;
        }
        if (button == 0) {
            PaidClassButton paidClass = paidClassAt(mouseX, mouseY);
            if (paidClass != null) {
                if (paidClass.unlockable()) {
                    PacketDistributor.sendToServer(new UnlockClassPayload(
                        ResourceLocation.fromNamespaceAndPath("rpgskilltree", paidClass.classId())
                    ));
                }
                return true;
            }
        }
        ClientTreeLayout.Node node = nodeAt(mouseX, mouseY);
        if (node != null) {
            NodeDisplayState state = displayState().nodes().get(node.id());
            ResourceLocation nodeId = ResourceLocation.parse(node.id());
            if (button == 0 && state != null && state.canPurchase()) {
                PacketDistributor.sendToServer(new PurchaseNodePayload(nodeId));
                return true;
            }
            if (button == 1 && state != null && state.canRespec()) {
                PacketDistributor.sendToServer(new RespecNodePayload(nodeId));
                return true;
            }
        }
        if (button == 0) {
            draggingCanvas = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && draggingCanvas) {
            draggingCanvas = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button == 0 && draggingCanvas) {
            panX += dragX;
            panY += dragY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY == 0) return false;
        double oldZoom = zoom;
        double multiplier = scrollY > 0 ? 1.12 : 1.0 / 1.12;
        double nextZoom = clamp(oldZoom * multiplier, MIN_ZOOM, MAX_ZOOM);
        if (nextZoom == oldZoom) return true;

        double worldX = (mouseX - width / 2.0 - panX) / oldZoom;
        double worldY = (mouseY - height / 2.0 - panY) / oldZoom;
        zoom = nextZoom;
        panX = mouseX - width / 2.0 - worldX * zoom;
        panY = mouseY - height / 2.0 - worldY * zoom;
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_TAB) {
            List<ClientTreeLayout> available = ClientTreeLayout.availableFor(ClientProgressionState.get());
            int current = 0;
            for (int i = 0; i < available.size(); i++) {
                if (available.get(i).id().equals(layout.id())) { current = i; break; }
            }
            layout = available.get((current + 1) % available.size());
            panX = 0;
            panY = 0;
            zoom = layout == ClientTreeLayout.main() ? 0.28 : 0.72;
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private String nodeDisplayName(ClientTreeLayout.Node node) {
        String key = nodeTranslationKey(node) + ".name";
        String translated = Component.translatable(key).getString();
        return translated.equals(key) ? node.id() : translated;
    }

    private String nodeDescription(ClientTreeLayout.Node node) {
        String key = nodeTranslationKey(node) + ".description";
        String translated = Component.translatable(key).getString();
        return translated.equals(key) ? "" : translated;
    }

    private String nodeTranslationKey(ClientTreeLayout.Node node) {
        ResourceLocation id = ResourceLocation.parse(node.id());
        return "node." + id.getNamespace() + "." + id.getPath().replace('/', '.');
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private ClassChoiceButton classChoiceAt(double mouseX, double mouseY) {
        for (ClassChoiceButton button : choiceButtons) {
            if (mouseX >= button.x() && mouseX < button.x() + button.width()
                && mouseY >= button.y() && mouseY < button.y() + button.height()) {
                return button;
            }
        }
        return null;
    }

    private PaidClassButton paidClassAt(double mouseX, double mouseY) {
        for (PaidClassButton button : paidClassButtons) {
            if (mouseX >= button.x() && mouseX < button.x() + button.width()
                && mouseY >= button.y() && mouseY < button.y() + button.height()) {
                return button;
            }
        }
        return null;
    }

    private ClientTreeLayout.Node nodeAt(double mouseX, double mouseY) {
        ClientTreeLayout.Node best = null;
        double bestDistance = Double.MAX_VALUE;
        for (ClientTreeLayout.Node node : layout.nodes()) {
            double dx = mouseX - screenX(node.x());
            double dy = mouseY - screenY(node.y());
            double distance = dx * dx + dy * dy;
            int radius = nodeRadius(node) + 3;
            if (distance <= radius * radius && distance < bestDistance) {
                best = node;
                bestDistance = distance;
            }
        }
        return best;
    }

    private int nodeRadius(ClientTreeLayout.Node node) {
        if ("keystone".equals(node.kind())) return 7;
        if (node.finalTriad()) return 7;
        if ("hybrid".equals(node.kind())) return 5;
        if ("core".equals(node.kind())) return 6;
        return zoom >= 0.65 ? 4 : 3;
    }

    private int nodeColor(ClientTreeLayout.Node node, NodeDisplayState state) {
        if (state.learned()) return node.finalTriad() ? 0xFFE3BA55 : 0xFF89B56A;
        if (state.canPurchase()) return node.finalTriad() ? 0xFFD49847 : 0xFF5E936B;
        if (node.finalTriad()) return 0xFF72552C;
        if ("hybrid".equals(node.kind())) return 0xFF4C4565;
        if ("keystone".equals(node.kind())) return 0xFF62463C;
        return 0xFF333A46;
    }

    private int screenX(double worldX) {
        return (int) Math.round(width / 2.0 + panX + worldX * zoom);
    }

    private int screenY(double worldY) {
        return (int) Math.round(height / 2.0 + panY + worldY * zoom);
    }

    private void drawLine(GuiGraphics graphics, int x1, int y1, int x2, int y2, int color) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        if (steps == 0) {
            graphics.fill(x1, y1, x1 + 1, y1 + 1, color);
            return;
        }
        int stride = Math.max(1, steps / 36);
        for (int step = 0; step <= steps; step += stride) {
            int x = x1 + dx * step / steps;
            int y = y1 + dy * step / steps;
            graphics.fill(x, y, x + 2, y + 2, color);
        }
    }

    private record ClassChoiceButton(int x, int y, int width, int height, String choiceId, boolean selected, boolean canSelect) {}

    private record PaidClassButton(int x, int y, int width, int height, String classId, boolean unlockable) {}

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
