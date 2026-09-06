package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicHazardSinkTest {
    private static final UUID VOLCANO_ID = UUID.fromString("af116388-8e94-493e-9ec9-d79d10f58e3a");

    @Test
    void oneSinkPublishesAshMetadataAndQueuesTheSamePartitionedGrant() {
        AshEmissionIndex ash = new AshEmissionIndex();
        VolcanicHazardQueue queue = new VolcanicHazardQueue(8);
        VolcanicHazardSink sink = new VolcanicHazardSink(ash, queue);
        EruptionScheduler.WorkGrant grant = new EruptionScheduler.WorkGrant(7, 2, 0, 0, 0, 0);

        sink.onEruption(signal(EruptionPhase.SUSTAINED, 0.72), grant);

        assertTrue(ash.forVolcano(VOLCANO_ID).isPresent());
        VolcanicHazardQueue.HazardWork work = queue.drain(1).getFirst();
        assertEquals(grant, work.workGrant());
        assertEquals(VOLCANO_ID, work.signal().volcanoId());
    }

    @Test
    void dormancyRetiresAshAndStillQueuesCleanupSignal() {
        AshEmissionIndex ash = new AshEmissionIndex();
        VolcanicHazardQueue queue = new VolcanicHazardQueue(8);
        VolcanicHazardSink sink = new VolcanicHazardSink(ash, queue);
        EruptionScheduler.WorkGrant empty = new EruptionScheduler.WorkGrant(0, 0, 0, 0, 0, 0);

        sink.onEruption(signal(EruptionPhase.SUSTAINED, 0.72), empty);
        sink.onEruption(signal(EruptionPhase.DORMANT, 0.0), empty);

        assertTrue(ash.forVolcano(VOLCANO_ID).isEmpty());
        assertEquals(EruptionPhase.DORMANT, queue.drain(2).getLast().signal().phase());
    }

    private static EruptionSignal signal(EruptionPhase phase, double intensity) {
        EruptionProfile profile = new EruptionProfile(0.9, 64, 384, 500L, 200L, 2_000L, 700L);
        return new EruptionSignal(
                VOLCANO_ID,
                new BlockPos(16, 96, 16),
                phase,
                profile,
                new MagmaChamber(new MagmaComposition(0.66, 0.69), 6.0, 300.0, 0.26, 1_215.0, 0.22),
                phase == EruptionPhase.DORMANT ? 1.0 : 0.5,
                intensity);
    }
}
