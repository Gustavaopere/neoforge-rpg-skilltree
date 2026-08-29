package dev.gustavopere.rpgskilltree.runtime.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class PurchaseNodePayloadJUnitTest {
    private static final ResourceLocation NODE = ResourceLocation.parse("rpgskilltree:test_node");

    @Test
    void requestCarriesStableIdentityWithNode() {
        PurchaseNodePayload payload = new PurchaseNodePayload(NODE, "node:123e4567-e89b-12d3-a456-426614174000");
        assertEquals(NODE, payload.nodeId());
        assertEquals("node:123e4567-e89b-12d3-a456-426614174000", payload.requestId());
    }

    @Test
    void blankRequestIdIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new PurchaseNodePayload(NODE, " "));
    }

    @Test
    void oversizedRequestIdIsRejected() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new PurchaseNodePayload(NODE, "x".repeat(PurchaseNodePayload.MAX_REQUEST_ID_LENGTH + 1))
        );
    }

    @Test
    void unsafeRequestIdCharactersAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new PurchaseNodePayload(NODE, "node request id"));
    }
}
