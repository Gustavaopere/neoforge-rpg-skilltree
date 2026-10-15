package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AttributeInvestmentUiModelJUnitTest {
    @Test
    void layoutPreservesSixRowsAndValidButtonCoordinatesAcrossViewports() {
        for (int[] viewport : new int[][] {{320, 240}, {400, 300}, {854, 480}, {220, 185}}) {
            var layout = AttributeInvestmentUiModel.layout(viewport[0], viewport[1]);
            assertTrue(layout.width() >= 130);
            assertTrue(layout.rowHeight() >= 18 && layout.rowHeight() <= 23);
            assertTrue(layout.left() >= 8);
            assertTrue(layout.purchaseX() > layout.left());
            assertTrue(layout.refundX() > layout.purchaseX());
            assertTrue(layout.labelWidth() >= 24);
            assertTrue(layout.backWidth() > 0);
            assertTrue(layout.statusX() >= layout.left());
            for (int index = 0; index < AttributeId.values().length; index++) {
                assertEquals(layout.firstRowY() + index * layout.rowHeight(), layout.rowY(index));
            }
            assertThrows(IllegalArgumentException.class, () -> layout.rowY(-1));
            assertThrows(IllegalArgumentException.class,
                () -> layout.rowY(AttributeId.values().length));
        }
    }

    @Test
    void rowsAlwaysProjectAllSixCanonicalAttributesWithoutInventingRanks() {
        var model = new AttributeInvestmentUiModel();
        var layout = AttributeInvestmentUiModel.layout(400, 240);
        var empty = model.rows(null, layout);
        assertEquals(AttributeId.values().length, empty.size());
        for (int i = 0; i < empty.size(); i++) {
            assertEquals(AttributeId.values()[i], empty.get(i).attribute());
            assertEquals(0L, empty.get(i).rank());
            assertFalse(empty.get(i).purchaseEnabled());
            assertFalse(empty.get(i).refundEnabled());
            assertEquals(layout.rowY(i), empty.get(i).y());
        }
        var state = state(4L, 3L, AttributeId.CHARISMA);
        var populated = model.rows(state, layout);
        assertEquals(3L, populated.get(populated.size() - 1).rank());
        assertTrue(populated.get(populated.size() - 1).purchaseEnabled());
        assertTrue(populated.get(populated.size() - 1).refundEnabled());
        assertEquals(0L, populated.get(0).rank());
        assertTrue(populated.get(0).purchaseEnabled());
        assertFalse(populated.get(0).refundEnabled());
        assertThrows(UnsupportedOperationException.class, () -> populated.clear());
        assertThrows(NullPointerException.class, () -> model.rows(state, null));
    }

    @Test
    void pendingRequestIsSingleFlightAndRequiresServerRankChange() {
        var model = new AttributeInvestmentUiModel();
        var baseline = state(5L, 10L, AttributeId.CHARISMA);
        assertEquals(AttributeInvestmentUiModel.Status.READY, model.status());
        assertFalse(model.isPending());
        assertTrue(model.canPurchase(baseline, AttributeId.CHARISMA));
        assertTrue(model.canRefund(baseline, AttributeId.CHARISMA));

        var action = model.request(baseline, AttributeId.CHARISMA, false, "attribute-ui-1").orElseThrow();
        assertEquals(AttributeId.CHARISMA, action.attribute());
        assertEquals("attribute-ui-1", action.transactionId());
        assertFalse(action.refund());
        assertEquals(AttributeInvestmentUiModel.Status.PENDING, model.status());
        assertEquals("screen.rpgskilltree.rank_pending", model.status().translationKey());
        assertTrue(model.isPending());
        assertFalse(model.canPurchase(baseline, AttributeId.STRENGTH));
        assertFalse(model.canRefund(baseline, AttributeId.CHARISMA));
        assertTrue(model.request(baseline, AttributeId.STRENGTH, false, "attribute-ui-2").isEmpty());
        for (int tick = 0; tick < 3; tick++) model.tick(baseline);
        assertTrue(model.isPending());
        model.tick(state(4L, 11L, AttributeId.CHARISMA));
        assertFalse(model.isPending());
        assertEquals(AttributeInvestmentUiModel.Status.UPDATED, model.status());
        assertTrue(model.canPurchase(baseline, AttributeId.STRENGTH));
        assertEquals("screen.rpgskilltree.rank_updated", model.status().translationKey());
        model.tick(baseline);
        assertEquals(AttributeInvestmentUiModel.Status.UPDATED, model.status());
    }

    @Test
    void refundAndTimeoutNeverClaimSuccessWithoutCorrectDirection() {
        var model = new AttributeInvestmentUiModel();
        var baseline = state(0L, 2L, AttributeId.CHARISMA);
        assertTrue(model.request(baseline, AttributeId.CHARISMA, false, "purchase").isEmpty());
        var action = model.request(baseline, AttributeId.CHARISMA, true, "refund").orElseThrow();
        assertTrue(action.refund());
        for (int t = 0; t < AttributeInvestmentUiModel.REQUEST_TIMEOUT_TICKS - 1; t++) {
            model.tick(state(0L, 3L, AttributeId.CHARISMA));
        }
        assertTrue(model.isPending());
        model.tick(null);
        assertFalse(model.isPending());
        assertEquals(AttributeInvestmentUiModel.Status.UNCONFIRMED, model.status());
        assertEquals("screen.rpgskilltree.rank_unconfirmed", model.status().translationKey());

        assertTrue(model.request(baseline, AttributeId.CHARISMA, true, "refund-new").isPresent());
        model.tick(state(1L, 1L, AttributeId.CHARISMA));
        assertFalse(model.isPending());
        assertEquals(AttributeInvestmentUiModel.Status.UPDATED, model.status());
    }

    @Test
    void invalidRequestsCannotEnterPendingStateOrSpendClientPoints() {
        var model = new AttributeInvestmentUiModel();
        assertTrue(model.request(null, AttributeId.CHARISMA, false, "id").isEmpty());
        assertTrue(model.request(state(0L, 0L, AttributeId.CHARISMA),
            AttributeId.CHARISMA, true, "id").isEmpty());
        assertThrows(NullPointerException.class,
            () -> model.request(null, null, false, "id"));
        assertThrows(NullPointerException.class,
            () -> model.request(null, AttributeId.CHARISMA, false, null));
        assertThrows(IllegalArgumentException.class,
            () -> model.request(null, AttributeId.CHARISMA, false, " "));
        assertFalse(model.isPending());
        assertEquals(AttributeInvestmentUiModel.Status.READY, model.status());
        assertEquals("screen.rpgskilltree.rank_ready", model.status().translationKey());
    }

    private static CoreProgressionSyncState state(long available, long rank, AttributeId attribute) {
        long allocated = rank;
        return new CoreProgressionSyncState(
            1L, 0L, BigInteger.TEN, Math.addExact(allocated, available),
            allocated, 0L, available, 0L,
            rank == 0L ? AttributeRanks.empty() : AttributeRanks.of(Map.of(attribute, rank)),
            1L, "0".repeat(64)
        );
    }
}
