package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalPendingQueueTest {
    @Test
    void queueIsBoundedDeduplicatedAndDrainsInInsertionOrder() {
        GeothermalPendingQueue queue = new GeothermalPendingQueue(2, 1);
        GeothermalFeaturePlacement first = placement(GeothermalFeatureType.HOT_SPRING, 0);
        GeothermalFeaturePlacement second = placement(GeothermalFeatureType.FUMAROLE, 64);
        GeothermalFeaturePlacement overflow = placement(GeothermalFeatureType.GEYSER, 128);

        assertTrue(queue.enqueue(91L, first));
        assertFalse(queue.enqueue(91L, first));
        assertTrue(queue.enqueue(91L, second));
        assertFalse(queue.enqueue(91L, overflow));
        assertEquals(2, queue.size());

        assertEquals(List.of(new GeothermalPendingQueue.Pending(91L, first)), queue.drain());
        assertEquals(1, queue.size());
        assertEquals(List.of(new GeothermalPendingQueue.Pending(91L, second)), queue.drain());
        assertTrue(queue.isEmpty());
    }

    @Test
    void reservationIsInvisibleUntilCommitAndCanBeCancelled() {
        GeothermalPendingQueue queue = new GeothermalPendingQueue(1, 1);
        GeothermalFeaturePlacement placement = placement(GeothermalFeatureType.HOT_SPRING, 0);

        GeothermalPendingQueue.Reservation reservation = queue.reserve(91L, placement).orElseThrow();
        assertEquals(1, queue.size(), "reserved work must consume bounded capacity");
        assertTrue(queue.drain().isEmpty(), "uncommitted worldgen work must never reach persistence");
        assertTrue(queue.commit(reservation));
        assertFalse(queue.commit(reservation));
        assertEquals(List.of(new GeothermalPendingQueue.Pending(91L, placement)), queue.drain());

        GeothermalPendingQueue.Reservation cancelled = queue.reserve(91L, placement).orElseThrow();
        assertTrue(queue.cancel(cancelled));
        assertFalse(queue.cancel(cancelled));
        assertTrue(queue.isEmpty());
    }

    @Test
    void reservationCapacityFailsClosedBeforeWorldMutation() {
        GeothermalPendingQueue queue = new GeothermalPendingQueue(1, 1);
        GeothermalFeaturePlacement first = placement(GeothermalFeatureType.HOT_SPRING, 0);
        GeothermalFeaturePlacement overflow = placement(GeothermalFeatureType.GEYSER, 64);

        assertTrue(queue.reserve(91L, first).isPresent());
        assertTrue(queue.reserve(91L, overflow).isEmpty());
        assertEquals(1, queue.size());
    }

    @Test
    void staleReservationCannotCommitOrCancelReplacementForSameSource() {
        GeothermalPendingQueue queue = new GeothermalPendingQueue(1, 1);
        GeothermalFeaturePlacement placement = placement(GeothermalFeatureType.HOT_SPRING, 0);

        GeothermalPendingQueue.Reservation stale = queue.reserve(91L, placement).orElseThrow();
        assertTrue(queue.cancel(stale));

        GeothermalPendingQueue.Reservation replacement = queue.reserve(91L, placement).orElseThrow();
        assertFalse(queue.commit(stale), "stale reservation must not commit its replacement");
        assertFalse(queue.cancel(stale), "stale reservation must not cancel its replacement");
        assertTrue(queue.drain().isEmpty(), "replacement must remain uncommitted after stale operations");

        assertTrue(queue.commit(replacement));
        assertEquals(List.of(new GeothermalPendingQueue.Pending(91L, placement)), queue.drain());
    }

    private static GeothermalFeaturePlacement placement(GeothermalFeatureType type, int x) {
        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(type);
        return new GeothermalFeaturePlacement(
                type,
                new BlockPos(x, 80, 8),
                profile.radiusBlocks(),
                profile.heatSeverity(),
                profile.gasSeverity(),
                profile.hydrothermalDepositChance());
    }
}
