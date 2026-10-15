package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentUiModel;
import dev.gustavopere.rpgskilltree.core.CoreProgressionSyncState;
import dev.gustavopere.rpgskilltree.runtime.network.PurchaseAttributeRanksPayload;
import dev.gustavopere.rpgskilltree.runtime.network.RefundAttributeRanksPayload;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

/** Physical controls, with all state transitions delegated to the tested Core UI model. */
public final class AttributeInvestmentScreen extends Screen {
    private final Screen parent;
    private final AttributeInvestmentUiModel model = new AttributeInvestmentUiModel();
    private final Map<AttributeId, Button> purchase = new EnumMap<>(AttributeId.class);
    private final Map<AttributeId, Button> refund = new EnumMap<>(AttributeId.class);
    private AttributeInvestmentUiModel.Layout layout;

    public AttributeInvestmentScreen(Screen parent) {
        super(Component.translatable("screen.rpgskilltree.attribute_panel"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        purchase.clear();
        refund.clear();
        layout = AttributeInvestmentUiModel.layout(width, height);
        for (var row : model.rows(snapshot(), layout)) {
            AttributeId attribute = row.attribute();
            purchase.put(attribute, addRenderableWidget(Button.builder(Component.literal("+"),
                button -> request(attribute, false))
                .bounds(layout.purchaseX(), row.y(), 42, 18).build()));
            refund.put(attribute, addRenderableWidget(Button.builder(Component.literal("-"),
                button -> request(attribute, true))
                .bounds(layout.refundX(), row.y(), 42, 18).build()));
        }
        addRenderableWidget(Button.builder(Component.translatable("screen.rpgskilltree.rank_back"),
            button -> minecraft.setScreen(parent))
            .bounds(layout.left(), layout.footerY(), layout.backWidth(), 20).build());
        updateButtons();
    }

    @Override
    public void tick() {
        ClientAttributeMutationResultState.take().ifPresent(
            result -> model.acknowledge(result.transactionId(), result.accepted()));
        model.tick(snapshot());
        updateButtons();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderPanel(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    // Separating the RPG panel paint from vanilla widget rendering permits
    // headless checks of the text and price presentation without requiring
    // a native Minecraft window or OpenGL state.
    void renderPanel(GuiGraphics graphics) {
        graphics.fill(0, 0, width, height, 0xFF090C12);
        CoreProgressionSyncState state = snapshot();
        updateButtons();
        graphics.drawCenteredString(font, title, width / 2, 12, 0xFFF2E7B6);
        graphics.drawCenteredString(font,
            state == null ? Component.translatable("screen.rpgskilltree.rank_no_sync")
                : Component.translatable("screen.rpgskilltree.rank_balance", state.availableCorePoints()),
            width / 2, 27, 0xFFD0D6E0);
        for (var row : model.rows(state, layout)) {
            graphics.fill(layout.left(), row.y() - 1, layout.left() + layout.width(),
                row.y() + layout.rowHeight() - 2, 0xCC18202B);
            String label = Component.translatable(
                "screen.rpgskilltree.attribute." + row.attribute().name()).getString()
                + " (" + row.rank() + ")";
            graphics.drawString(font, font.plainSubstrByWidth(label, layout.labelWidth()),
                layout.left() + 6, row.y() + 5, state == null ? 0xFF81909D : 0xFFE3E8EF);
        }
        graphics.drawString(font, Component.translatable(model.status().translationKey()),
            layout.statusX(), layout.footerY() + 6, 0xFF9EA9B8);
    }

    private CoreProgressionSyncState snapshot() {
        return ClientCoreProgressionState.get().orElse(null);
    }

    private void request(AttributeId attribute, boolean refundAction) {
        model.request(snapshot(), attribute, refundAction, "attribute-ui-" + UUID.randomUUID())
            .ifPresent(request -> {
                if (request.refund()) {
                    PacketDistributor.sendToServer(new RefundAttributeRanksPayload(
                        request.attribute(), 1L, request.transactionId()));
                } else {
                    PacketDistributor.sendToServer(new PurchaseAttributeRanksPayload(
                        request.attribute(), 1L, request.transactionId()));
                }
            });
        updateButtons();
    }

    private void updateButtons() {
        CoreProgressionSyncState state = snapshot();
        for (AttributeId attribute : AttributeId.values()) {
            purchase.get(attribute).active = model.canPurchase(state, attribute);
            refund.get(attribute).active = model.canRefund(state, attribute);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
