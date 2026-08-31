package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanoTickSchedulerHardeningTest {
    @Test
    void repeatedReschedulingKeepsOnePhysicalQueueNodePerVolcano() throws ReflectiveOperationException {
        VolcanoTickScheduler scheduler = new VolcanoTickScheduler();
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000404");
        MagmaChamber chamber = chamber();

        for (int tick = 0; tick < 10_000; tick++) {
            scheduler.schedule(id, VolcanoState.ACTIVE, chamber, tick);
        }

        assertEquals(1, scheduler.size(), "logical scheduler cardinality must remain one");
        assertEquals(1, physicalQueueSize(scheduler),
                "rescheduling one live volcano must not retain stale priority-queue nodes");
    }

    @Test
    void physicalQueueCardinalityTracksLiveVolcanoesNotRescheduleCount() throws ReflectiveOperationException {
        VolcanoTickScheduler scheduler = new VolcanoTickScheduler();
        MagmaChamber chamber = chamber();

        for (int cycle = 0; cycle < 100; cycle++) {
            for (int index = 0; index < 32; index++) {
                scheduler.schedule(new UUID(0L, 20_000L + index), VolcanoState.ACTIVE, chamber, cycle);
            }
        }

        assertEquals(32, scheduler.size(), "logical scheduler cardinality must equal live volcano count");
        assertEquals(32, physicalQueueSize(scheduler),
                "physical scheduler cardinality must remain bounded by live volcano count");
    }

    @Test
    void dormantSiteDoesNotEnterEveryTickHotPath() {
        VolcanoTickScheduler scheduler = new VolcanoTickScheduler();
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000405");
        scheduler.schedule(id, VolcanoState.DORMANT, quietChamber(), 100L);

        assertEquals(24_100L, scheduler.nextDueTick(id).orElseThrow());
        for (long tick = 101L; tick < 24_100L; tick++) {
            assertTrue(scheduler.pollDue(tick, 8).isEmpty(),
                    "dormant site must remain outside the per-tick update path before its cadence");
        }
        assertEquals(List.of(id), scheduler.pollDue(24_100L, 8));
    }

    private static MagmaChamber chamber() {
        return new MagmaChamber(
                new MagmaComposition(0.62, 0.55),
                5.0,
                165.0,
                0.07,
                1_200.0,
                0.25);
    }

    private static MagmaChamber quietChamber() {
        return new MagmaChamber(
                new MagmaComposition(0.62, 0.55),
                5.0,
                120.0,
                0.02,
                1_200.0,
                0.25);
    }

    private static int physicalQueueSize(VolcanoTickScheduler scheduler) throws ReflectiveOperationException {
        Field queueField = VolcanoTickScheduler.class.getDeclaredField("queue");
        queueField.setAccessible(true);
        return ((Collection<?>) queueField.get(scheduler)).size();
    }
}
