package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeInvestmentUiPolicy;
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

/**
 * Compact control surface for six canonical attributes. The Core server
 * validates affordability, rank limits, refund safety and transaction IDs.
 */
public final class AttributeInvestmentScreen extends Screen {
    private static final int REQUEST_TIMEOUT_TICKS = 100;
    private final Screen parent;
    private final Map<AttributeId, Button> purchaseButtons = new EnumMap<>(AttributeId.class);
    private final Map<AttributeId, Button> refundButtons = new EnumMap<>(AttributeId.class);
    private Pending pending;
    private int pendingTicks;
    private String statusKey = "screen.rpgskilltree.rank_ready";
    private int left;
    private int panelWidth;
    private int rowHeight;
    private int firstRowY;
    private int footerY;

    public AttributeInvestmentScreen(Screen parent) {
        super(Component.translatable("screen.rpgskilltree.attribute_panel"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        purchaseButtons.clear();
        refundButtons.clear();
        panelWidth = Math.max(130, Math.min(336, width - 16));
        left = Math.max(8, (width - panelWidth) / 2);
        rowHeight = Math.max(18, Math.min(23, (height - 74) / AttributeId.values().length));
        firstRowY = 44;
        footerY = Math.min(height - 24, firstRowY + AttributeId.values().length * rowHeight + 8);

        int row = 0;
        for (AttributeId attribute : AttributeId.values()) {
            int y = firstRowY + row++ * rowHeight;
            Button purchase = Button.builder(Component.literal("+"), button -> request(attribute, false))
                .bounds(left + panelWidth - 94, y, 42, 18).build();
            Button refund = Button.builder(Component.literal("-"), button -> request(attribute, true))
                .bounds(left + panelWidth - 47, y, 42, 18).build();
            purchaseButtons.put(attribute, addRenderableWidget(purchase));
            refundButtons.put(attribute, addRenderableWidget(refund));
        }
        addRenderableWidget(Button.builder(
            Component.translatable("screen.rpgskilltree.rank_back"),
            button -> minecraft.setScreen(parent))
            .bounds(left, footerY, Math.min(86, panelWidth / 3), 20).build());
        updateButtons();
    }

    @Override
    public void tick() {
        CoreProgressionSyncState snapshot = ClientCoreProgressionState.get().orElse(null);
        if (pending != null) {
            if (AttributeInvestmentUiPolicy.rankChanged(
                snapshot, pending.attribute(), pending.previousRank(), pending.refund())) {
                pending = null;
                statusKey = "screen.rpgskilltree.rank_updated";
            } else if (++pendingTicks >= REQUEST_TIMEOUT_TICKS) {
                pending = null;
                statusKey = "screen.rpgskilltree.rank_unconfirmed";
            }
        }
        updateButtons();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.fill(0, 0, width, height, 0xFF090C12);
        CoreProgressionSyncState snapshot = ClientCoreProgressionState.get().orElse(null);
        updateButtons();
        graphics.drawCenteredString(font, title, width / 2, 12, 0xFFF2E7B6);
        graphics.drawCenteredString(font,
            snapshot == null ? Component.translatable("screen.rpgskilltree.rank_no_sync")
                : Component.translatable("screen.rpgskilltree.rank_balance", snapshot.availableCorePoints()),
            width / 2, 27, 0xFFD0D6E0);

        int row = 0;
        for (AttributeId attribute : AttributeId.values()) {
            int y = firstRowY + row++ * rowHeight;
            graphics.fill(left, y - 1, left + panelWidth, y + rowHeight - 2, 0xCC18202B);
            long rank = snapshot == null ? 0L : snapshot.attributeRanks().rank(attribute);
            String label = Component.translatable("screen.rpgskilltree.attribute." + attribute.name()).getString()
                + " (" + rank + ")";
            graphics.drawString(font,
                font.plainSubstrByWidth(label, Math.max(24, panelWidth - 108)),
                left + 6, y + 5, snapshot == null ? 0xFF81909D : 0xFFE3E8EF);
        }
        graphics.drawString(font, Component.translatable(statusKey),
            left + Math.min(94, panelWidth / 3 + 8), footerY + 6, 0xFF9EA9B8);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void request(AttributeId attribute, boolean refund) {
        CoreProgressionSyncState snapshot = ClientCoreProgressionState.get().orElse(null);
        boolean allowed = refund
            ? AttributeInvestmentUiPolicy.canRefund(snapshot, attribute, pending != null)
            : AttributeInvestmentUiPolicy.canPurchase(snapshot, attribute, pending != null);
        if (!allowed) return;

        long previousRank = snapshot.attributeRanks().rank(attribute);
        String transactionId = "attribute-ui-" + UUID.randomUUID();
        pending = new Pending(attribute, previousRank, refund);
        pendingTicks = 0;
        statusKey = "screen.rpgskilltree.rank_pending";
        updateButtons();
        if (refund) {
            PacketDistributor.sendToServer(new RefundAttributeRanksPayload(attribute, 1L, transactionId));
        } else {
            PacketDistributor.sendToServer(new PurchaseAttributeRanksPayload(attribute, 1L, transactionId));
        }
    }

    private void updateButtons() {
        CoreProgressionSyncState snapshot = ClientCoreProgressionState.get().orElse(null);
        for (AttributeId attribute : AttributeId.values()) {
            Button purchase = purchaseButtons.get(attribute);
            Button refund = refundButtons.get(attribute);
            if (purchase != null) {
                purchase.active = AttributeInvestmentUiPolicy.canPurchase(snapshot, attribute, pending != null);
            }
            if (refund != null) {
                refund.active = AttributeInvestmentUiPolicy.canRefund(snapshot, attribute, pending != null);
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private record Pending(AttributeId attribute, long previousRank, boolean refund) {}
}
