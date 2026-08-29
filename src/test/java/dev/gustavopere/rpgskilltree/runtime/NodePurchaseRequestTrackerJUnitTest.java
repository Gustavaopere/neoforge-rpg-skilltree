package dev.gustavopere.rpgskilltree.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class NodePurchaseRequestTrackerJUnitTest {
    private static final UUID PLAYER = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final ResourceLocation NODE_A = ResourceLocation.parse("rpgskilltree:test_a");
    private static final ResourceLocation NODE_B = ResourceLocation.parse("rpgskilltree:test_b");

    @Test
    void sameRequestAndNodeIsRecognizedAsReplay() {
        NodePurchaseRequestTracker tracker = new NodePurchaseRequestTracker(4);
        assertEquals(NodePurchaseRequestTracker.Decision.NEW, tracker.checkAndRecord(PLAYER, "req-1", NODE_A));
        assertEquals(NodePurchaseRequestTracker.Decision.REPLAY, tracker.checkAndRecord(PLAYER, "req-1", NODE_A));
    }

    @Test
    void reusingRequestIdForDifferentNodeFailsClosed() {
        NodePurchaseRequestTracker tracker = new NodePurchaseRequestTracker(4);
        assertEquals(NodePurchaseRequestTracker.Decision.NEW, tracker.checkAndRecord(PLAYER, "req-1", NODE_A));
        assertEquals(NodePurchaseRequestTracker.Decision.CONFLICT, tracker.checkAndRecord(PLAYER, "req-1", NODE_B));
    }

    @Test
    void boundedWindowEvictsOldestRequest() {
        NodePurchaseRequestTracker tracker = new NodePurchaseRequestTracker(2);
        tracker.checkAndRecord(PLAYER, "req-1", NODE_A);
        tracker.checkAndRecord(PLAYER, "req-2", NODE_A);
        tracker.checkAndRecord(PLAYER, "req-3", NODE_A);
        assertEquals(NodePurchaseRequestTracker.Decision.NEW, tracker.checkAndRecord(PLAYER, "req-1", NODE_A));
    }

    @Test
    void clearDropsSessionHistory() {
        NodePurchaseRequestTracker tracker = new NodePurchaseRequestTracker(4);
        tracker.checkAndRecord(PLAYER, "req-1", NODE_A);
        tracker.clear(PLAYER);
        assertEquals(NodePurchaseRequestTracker.Decision.NEW, tracker.checkAndRecord(PLAYER, "req-1", NODE_A));
    }
}
