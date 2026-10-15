package dev.gustavopere.rpgskilltree.runtime.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.runtime.client.ClientAttributeMutationResultState;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class AttributeRankMutationResultPayloadJUnitTest {
    @AfterEach
    void resetMailbox() {
        ClientAttributeMutationResultState.clear();
    }

    @Test
    void acceptedAndRejectedResponsesRoundTripAndRetainExactTransactionIdentity() {
        for (boolean accepted : new boolean[] {true, false}) {
            var original = new AttributeRankMutationResultPayload(
                "attribute-ui-123e4567-e89b-12d3-a456-426614174000", accepted);
            ByteBuf buffer = Unpooled.buffer();
            try {
                AttributeRankMutationResultPayload.STREAM_CODEC.encode(buffer, original);
                var decoded = AttributeRankMutationResultPayload.STREAM_CODEC.decode(buffer);
                assertEquals(original, decoded);
                assertEquals(AttributeRankMutationResultPayload.TYPE, decoded.type());
                assertEquals(accepted, decoded.accepted());
            } finally {
                buffer.release();
            }
        }
    }

    @Test
    void rejectsMalformedAndOversizedTransactionResults() {
        assertThrows(NullPointerException.class,
            () -> new AttributeRankMutationResultPayload(null, false));
        for (String invalid : new String[] {"", " ", "two words", "\n",
                "a".repeat(PurchaseAttributeRanksPayload.MAX_TRANSACTION_ID_LENGTH + 1)}) {
            assertThrows(IllegalArgumentException.class,
                () -> new AttributeRankMutationResultPayload(invalid, true), invalid);
        }
    }

    @Test
    void lateResponseCannotOverwriteAnEarlierPendingTransactionResult() {
        var active = new AttributeRankMutationResultPayload("active-transaction", true);
        var stale = new AttributeRankMutationResultPayload("stale-transaction", false);

        // Both network callbacks may run before one client tick consumes receipts.
        AttributeRankMutationResultPayload.handle(active, null);
        AttributeRankMutationResultPayload.handle(stale, null);

        assertEquals(active, ClientAttributeMutationResultState.take().orElseThrow(),
            "an unrelated later receipt must not erase the pending transaction verdict");
        assertEquals(stale, ClientAttributeMutationResultState.take().orElseThrow(),
            "each verdict should remain readable once");
        assertTrue(ClientAttributeMutationResultState.take().isEmpty());
    }

    @Test
    void burstOfRepliesIsBoundedAndDropsOnlyOldestReceipts() {
        int capacity = ClientAttributeMutationResultState.MAX_PENDING_RESULTS;
        for (int i = 0; i < capacity + 4; i++) {
            ClientAttributeMutationResultState.handle(
                new AttributeRankMutationResultPayload("reply-" + i, true), null);
        }
        for (int i = 4; i < capacity + 4; i++) {
            assertEquals("reply-" + i,
                ClientAttributeMutationResultState.take().orElseThrow().transactionId());
        }
        assertTrue(ClientAttributeMutationResultState.take().isEmpty());
    }

    @Test
    void clientReceiptMailboxIsReadOnceAndResetOnDisconnect() {
        var first = new AttributeRankMutationResultPayload("first", true);
        var second = new AttributeRankMutationResultPayload("second", false);
        assertFalse(ClientAttributeMutationResultState.take().isPresent());
        AttributeRankMutationResultPayload.handle(first, null);
        assertEquals(first, ClientAttributeMutationResultState.take().orElseThrow());
        assertFalse(ClientAttributeMutationResultState.take().isPresent());

        ClientAttributeMutationResultState.handle(second, null);
        assertEquals(second, ClientAttributeMutationResultState.take().orElseThrow());
        ClientAttributeMutationResultState.handle(first, null);
        ClientAttributeMutationResultState.clear();
        assertTrue(ClientAttributeMutationResultState.take().isEmpty());
    }
}
