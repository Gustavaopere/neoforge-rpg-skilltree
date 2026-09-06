package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicHazardQueueTest {
    private static final UUID VOLCANO_ID = UUID.fromString("27d767b6-acde-4ba3-93a9-77a5d142d353");

    @Test
    void queueRetainsOnlyBoundedGrantedWorkAndDrainsInOrder() {
        VolcanicHazardQueue queue = new VolcanicHazardQueue(4);
        EruptionScheduler.WorkGrant grant = new EruptionScheduler.WorkGrant(2, 1, 0, 0, 0, 0);
        EruptionSignal signal = signal(EruptionPhase.SUSTAINED, 0.8);

        assertTrue(queue.offer(signal, grant));
        assertTrue(queue.offer(signal, grant));
        assertTrue(queue.offer(signal, grant));
        assertTrue(queue.offer(signal, grant));
        assertTrue(!queue.offer(signal, grant));
        assertEquals(4, queue.size());

        List<VolcanicHazardQueue.HazardWork> drained = queue.drain(3);
        assertEquals(3, drained.size());
        assertEquals(1, queue.size());
        assertEquals(grant, drained.get(0).workGrant());
        assertEquals(VOLCANO_ID, drained.get(0).signal().volcanoId());
    }

    @Test
    void dormantSignalRemainsDeliverableForLifecycleCleanup() {
        VolcanicHazardQueue queue = new VolcanicHazardQueue(2);
        EruptionScheduler.WorkGrant empty = new EruptionScheduler.WorkGrant(0, 0, 0, 0, 0, 0);

        assertTrue(queue.offer(signal(EruptionPhase.DORMANT, 0.0), empty));
        assertEquals(EruptionPhase.DORMANT, queue.drain(1).getFirst().signal().phase());
    }

    private static EruptionSignal signal(EruptionPhase phase, double intensity) {
        EruptionProfile profile = new EruptionProfile(0.9, 48, 320, 400L, 200L, 1_600L, 600L);
        return new EruptionSignal(
                VOLCANO_ID,
                new BlockPos(0, 90, 0),
                phase,
                profile,
                new MagmaChamber(new MagmaComposition(0.68, 0.72), 4.0, 280.0, 0.25, 1_210.0, 0.2),
                phase == EruptionPhase.DORMANT ? 1.0 : 0.5,
                intensity);
    }
}
