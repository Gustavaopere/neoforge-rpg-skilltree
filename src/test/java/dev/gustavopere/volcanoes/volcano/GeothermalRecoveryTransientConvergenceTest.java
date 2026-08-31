package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Contract for convergence between durable chunk recovery and the transient worldgen handoff.
 *
 * <p>Once durable recovery proves complete metadata authority for a source, any matching committed
 * transient retry is obsolete and must stop occupying bounded queue capacity. Uncommitted worldgen
 * reservations remain protected from recovery cleanup because their physical mutation has not yet
 * reached the committed handoff state.</p>
 */
final class GeothermalRecoveryTransientConvergenceTest {
    @Test
    void durableAuthorityCanPruneMatchingQuarantinedTransientWork() {
        long worldSeed = 91L;
        GeothermalFeaturePlacement placement = placement(0);
        GeothermalPendingQueue queue = new GeothermalPendingQueue(2, 1);
        UUID sourceId = GeothermalSource.fromPlacement(worldSeed, placement).persistenceId();

        assertTrue(queue.enqueue(worldSeed, placement));
        for (int failure = 0; failure < GeothermalPendingQueue.MAX_PROCESSING_FAILURES; failure++) {
            queue.processCommitted(pending -> {
                throw new IllegalStateException("deterministic poison");
            }, 1);
        }

        assertEquals(1, queue.quarantinedCount());
        assertEquals(1, queue.size());
        assertTrue(queue.acknowledgeResolved(sourceId),
                "durable recovery with complete metadata authority must remove the obsolete committed/quarantined retry");
        assertTrue(queue.isEmpty(),
                "resolved durable authority must release the transient queue slot instead of leaking historical quarantine");
        assertFalse(queue.acknowledgeResolved(sourceId),
                "resolved cleanup must be idempotent when no matching transient work remains");
    }

    @Test
    void durableAuthorityCannotEraseUncommittedWorldgenReservation() {
        long worldSeed = 92L;
        GeothermalFeaturePlacement placement = placement(64);
        GeothermalPendingQueue queue = new GeothermalPendingQueue(1, 1);
        GeothermalPendingQueue.Reservation reservation = queue.reserve(worldSeed, placement).orElseThrow();

        assertFalse(queue.acknowledgeResolved(reservation.sourceId()),
                "recovery cleanup must never erase a reservation whose world mutation has not committed");
        assertEquals(1, queue.size());
        assertTrue(queue.commit(reservation));
        assertTrue(queue.acknowledgeResolved(reservation.sourceId()),
                "once the reservation is committed, complete durable authority may retire the duplicate transient retry");
        assertTrue(queue.isEmpty());
    }

    private static GeothermalFeaturePlacement placement(int x) {
        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(GeothermalFeatureType.FUMAROLE);
        return new GeothermalFeaturePlacement(
                GeothermalFeatureType.FUMAROLE,
                new BlockPos(x, 80, 8),
                profile.radiusBlocks(),
                profile.heatSeverity(),
                profile.gasSeverity(),
                0.0);
    }
}
