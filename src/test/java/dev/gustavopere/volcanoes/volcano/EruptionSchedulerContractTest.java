package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class EruptionSchedulerContractTest {
    private static final UUID FIRST = UUID.fromString("8b16240c-8b53-41ac-85e4-c4b8ba4ba5fa");
    private static final UUID SECOND = UUID.fromString("752a83f7-9222-40d7-b73b-205f986528cf");

    @Test
    void perTickBudgetIsGlobalAcrossEruptionsAndOverflowIsDeferred() {
        EruptionScheduler scheduler = new EruptionScheduler(64, 4, 128, 8);

        EruptionScheduler.WorkGrant first = scheduler.submit(FIRST, 100L, 100, 7);
        assertEquals(64, first.immediateBlocks());
        assertEquals(4, first.immediateEntities());
        assertEquals(36, first.queuedBlocks());
        assertEquals(3, first.queuedEntities());
        assertEquals(0, first.droppedBlocks());
        assertEquals(0, first.droppedEntities());

        EruptionScheduler.WorkGrant second = scheduler.submit(SECOND, 100L, 20, 2);
        assertEquals(0, second.immediateBlocks());
        assertEquals(0, second.immediateEntities());
        assertEquals(20, second.queuedBlocks());
        assertEquals(2, second.queuedEntities());

        assertEquals(36, scheduler.queuedBlocks(FIRST));
        assertEquals(3, scheduler.queuedEntities(FIRST));
        assertEquals(20, scheduler.queuedBlocks(SECOND));
        assertEquals(2, scheduler.queuedEntities(SECOND));
    }

    @Test
    void deferredWorkDrainsOnLaterTicksWithoutExceedingGlobalBudget() {
        EruptionScheduler scheduler = new EruptionScheduler(64, 4, 128, 8);
        scheduler.submit(FIRST, 100L, 100, 7);
        scheduler.submit(SECOND, 100L, 20, 2);

        EruptionScheduler.WorkGrant firstDrain = scheduler.drain(FIRST, 101L);
        assertEquals(36, firstDrain.immediateBlocks());
        assertEquals(3, firstDrain.immediateEntities());
        assertEquals(0, firstDrain.queuedBlocks());
        assertEquals(0, firstDrain.queuedEntities());

        EruptionScheduler.WorkGrant secondDrain = scheduler.drain(SECOND, 101L);
        assertEquals(20, secondDrain.immediateBlocks());
        assertEquals(1, secondDrain.immediateEntities());
        assertEquals(0, scheduler.queuedBlocks(SECOND));
        assertEquals(1, scheduler.queuedEntities(SECOND));

        EruptionScheduler.WorkGrant finalDrain = scheduler.drain(SECOND, 102L);
        assertEquals(0, finalDrain.immediateBlocks());
        assertEquals(1, finalDrain.immediateEntities());
        assertEquals(0, scheduler.queuedEntities(SECOND));
    }

    @Test
    void perEruptionBacklogIsBoundedAndReportsDroppedWork() {
        EruptionScheduler scheduler = new EruptionScheduler(64, 4, 128, 8);

        EruptionScheduler.WorkGrant grant = scheduler.submit(FIRST, 200L, 400, 30);

        assertEquals(64, grant.immediateBlocks());
        assertEquals(4, grant.immediateEntities());
        assertEquals(128, grant.queuedBlocks());
        assertEquals(8, grant.queuedEntities());
        assertEquals(208, grant.droppedBlocks());
        assertEquals(18, grant.droppedEntities());
        assertEquals(128, scheduler.queuedBlocks(FIRST));
        assertEquals(8, scheduler.queuedEntities(FIRST));
    }

    @Test
    void invalidWorkAndBackwardTicksFailClosed() {
        EruptionScheduler scheduler = new EruptionScheduler(64, 4, 128, 8);
        scheduler.submit(FIRST, 10L, 1, 1);

        assertThrows(IllegalArgumentException.class, () -> scheduler.submit(FIRST, 9L, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> scheduler.submit(FIRST, 11L, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> scheduler.submit(FIRST, 11L, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> new EruptionScheduler(0, 4, 128, 8));
    }
}
