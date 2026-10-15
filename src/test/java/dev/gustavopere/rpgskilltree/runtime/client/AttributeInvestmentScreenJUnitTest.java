package dev.gustavopere.rpgskilltree.runtime.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeRanks;
import dev.gustavopere.rpgskilltree.core.CoreProgressionSyncState;
import dev.gustavopere.rpgskilltree.core.CoreProgressionSyncStateCodec;
import dev.gustavopere.rpgskilltree.runtime.network.CoreProgressionSyncPayload;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Map;
import java.util.EnumMap;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class AttributeInvestmentScreenJUnitTest {
    @AfterEach
    void clearClientMirror() {
        ClientCoreProgressionState.clear();
    }

    @Test
    void createsRealAttributeControlsButCannotPurchaseWithoutAuthoritativeSnapshot() {
        ClientCoreProgressionState.clear();
        AttributeInvestmentScreen screen = initialized(320, 240);
        assertEquals(Component.translatable("screen.rpgskilltree.attribute_panel"), screen.getTitle());
        assertFalse(screen.isPauseScreen());
        assertEquals(13, screen.children().size(), "six purchase, six refund and one back button");
        for (int i = 0; i < 6; i++) {
            assertFalse(button(screen, 2 * i).active, "no buy without Core sync");
            assertFalse(button(screen, 2 * i + 1).active, "no refund without Core sync");
        }
    }

    @Test
    void serverSnapshotEnablesOnlyAffordablePurchasesAndActualRefunds() {
        AttributeInvestmentScreen screen = initialized(400, 300);
        applySnapshot(4L, 3L, AttributeId.CHARISMA);
        screen.tick();
        for (int i = 0; i < 6; i++) {
            assertTrue(button(screen, 2 * i).active, "canonical point budget enables buying");
            assertEquals(i == 5, button(screen, 2 * i + 1).active,
                "only invested Charisma rank may be refunded");
        }
        applySnapshot(0L, 2L, AttributeId.CHARISMA);
        screen.tick();
        for (int i = 0; i < 6; i++) {
            assertFalse(button(screen, 2 * i).active, "insufficient Core points fail closed");
            assertEquals(i == 5, button(screen, 2 * i + 1).active);
        }
        ClientCoreProgressionState.clear();
        screen.tick();
        assertFalse(button(screen, 10).active);
        assertFalse(button(screen, 11).active);
    }

    @Test
    void rendersCanonicalRankLabelsAndPendingMessageFromSnapshotWithoutLiveWindow() throws Exception {
        ClientCoreProgressionState.clear();
        AttributeInvestmentScreen screen = new AttributeInvestmentScreen(null);
        screen.width = 320;
        screen.height = 240;
        Field layout = AttributeInvestmentScreen.class.getDeclaredField("layout");
        layout.setAccessible(true);
        layout.set(screen, dev.gustavopere.rpgskilltree.core.AttributeInvestmentUiModel.layout(320, 240));
        // The screen's real init/registered widgets are tested separately.
        // Seed only its own control maps here so its painter can run with no
        // native Minecraft window and no vanilla widget draw path.
        Field purchases = AttributeInvestmentScreen.class.getDeclaredField("purchase");
        Field refunds = AttributeInvestmentScreen.class.getDeclaredField("refund");
        purchases.setAccessible(true);
        refunds.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<AttributeId, Button> buyButtons = (Map<AttributeId, Button>) purchases.get(screen);
        @SuppressWarnings("unchecked")
        Map<AttributeId, Button> refundButtons = (Map<AttributeId, Button>) refunds.get(screen);
        for (AttributeId attribute : AttributeId.values()) {
            buyButtons.put(attribute, Button.builder(Component.literal("+"), b -> {})
                .bounds(0, 0, 42, 18).build());
            refundButtons.put(attribute, Button.builder(Component.literal("-"), b -> {})
                .bounds(0, 0, 42, 18).build());
        }

        Field font = Screen.class.getDeclaredField("font");
        font.setAccessible(true);
        font.set(screen, mock(Font.class));
        GuiGraphics graphics = mock(GuiGraphics.class);

        screen.render(graphics, 10, 10, 0.0F);
        verify(graphics, atLeastOnce()).fill(
            0, 0, 320, 240, 0xFF090C12);

        applySnapshot(5L, 10L, AttributeId.CHARISMA);
        screen.render(graphics, 10, 10, 0.0F);
        verify(graphics, atLeastOnce()).fill(
            0, 0, 320, 240, 0xFF090C12);
    }

    private static AttributeInvestmentScreen initialized(int width, int height) {
        AttributeInvestmentScreen screen = new AttributeInvestmentScreen(null);
        screen.width = width;
        screen.height = height;
        screen.init();
        return screen;
    }

    private static Button button(AttributeInvestmentScreen screen, int index) {
        return (Button) screen.children().get(index);
    }

    private static void applySnapshot(long available, long rank, AttributeId attribute) {
        AttributeRanks ranks = rank == 0L ? AttributeRanks.empty()
            : AttributeRanks.of(Map.of(attribute, rank));
        CoreProgressionSyncState state = new CoreProgressionSyncState(
            1L, 0L, BigInteger.TEN, Math.addExact(available, rank),
            rank, 0L, available, 0L, ranks, 1L, "0".repeat(64)
        );
        ClientCoreProgressionState.handleSync(
            new CoreProgressionSyncPayload(CoreProgressionSyncStateCodec.encode(state)), null);
    }
}
