package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalPendingQueueTurnBudgetTest {
    @Test
    void onlyCommittedNonQuarantinedEntriesCountAsProcessableWork() {
        GeothermalPendingQueue queue = new GeothermalPendingQueue(8, 8);
        GeothermalPendingQueue.Reservation reservation = queue.reserve(11L, placement(0)).orElseThrow();

        assertFalse(queue.hasProcessableCommittedWork(),
                "an uncommitted worldgen reservation must not steal recovery budget");
        assertTrue(queue.commit(reservation));
        assertTrue(queue.hasProcessableCommittedWork());

        for (int failure = 0; failure < GeothermalPendingQueue.MAX_PROCESSING_FAILURES; failure++) {
            queue.processCommitted(pending -> {
                throw new IllegalStateException("deterministic poison");
            }, 1);
        }

        assertEquals(1, queue.quarantinedCount());
        assertFalse(queue.hasProcessableCommittedWork(),
                "quarantined poison remains bounded in capacity but must not steal future recovery turns");
    }

    @Test
    void dynamicTurnLimitCapsAttemptsWithoutDroppingLaterCommittedWork() {
        GeothermalPendingQueue queue = new GeothermalPendingQueue(8, 8);
        queue.enqueue(21L, placement(0));
        queue.enqueue(22L, placement(16));
        queue.enqueue(23L, placement(32));

        AtomicInteger attempts = new AtomicInteger();
        int acknowledged = queue.processCommitted(pending -> {
            attempts.incrementAndGet();
            return true;
        }, 1);

        assertEquals(1, attempts.get());
        assertEquals(1, acknowledged);
        assertEquals(2, queue.size());
        assertTrue(queue.hasProcessableCommittedWork());
    }

    private static GeothermalFeaturePlacement placement(int x) {
        return new GeothermalFeaturePlacement(
                GeothermalFeatureType.FUMAROLE,
                new BlockPos(x, 80, 0),
                3,
                0.7,
                0.8,
                0.0);
    }
}
