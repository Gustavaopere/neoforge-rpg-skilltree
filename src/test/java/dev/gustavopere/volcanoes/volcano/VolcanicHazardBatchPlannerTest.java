package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicHazardBatchPlannerTest {
    @Test
    void oneHazardWorkIsPartitionedOnceAcrossAshBombsAndFlow() {
        VolcanicHazardBatchPlanner planner = new VolcanicHazardBatchPlanner();
        EruptionSignal signal = explosiveSignal(EruptionPhase.SUSTAINED, 0.84);
        EruptionScheduler.WorkGrant grant = new EruptionScheduler.WorkGrant(6, 3, 0, 0, 0, 0);
        VolcanicHazardQueue.HazardWork work = new VolcanicHazardQueue.HazardWork(signal, grant);

        VolcanicHazardBatchPlanner.Plan first = planner.plan(work, 7_000L);
        VolcanicHazardBatchPlanner.Plan second = planner.plan(work, 7_000L);

        assertEquals(first, second, "same authoritative work snapshot and tick must produce the same batch");
        assertEquals(4, first.allocation().ashBlockWork());
        assertEquals(1, first.allocation().bombTerrainWork());
        assertEquals(1, first.allocation().flowTerrainWork());
        assertEquals(2, first.allocation().bombEntityWork());
        assertEquals(1, first.allocation().flowSpawnWork());
        assertTrue(first.ashCandidates().size() <= first.allocation().ashBlockWork());
        assertFalse(first.ashCandidates().isEmpty());
        assertEquals(2, first.bombLaunches().size());
        assertTrue(first.flowSeed().isPresent());
        assertEquals(
                grant.immediateBlocks(),
                first.allocation().ashBlockWork()
                        + first.allocation().bombTerrainWork()
                        + first.allocation().flowTerrainWork());
        assertEquals(
                grant.immediateEntities(),
                first.bombLaunches().size() + first.flowSeed().stream().mapToInt(ignored -> 1).sum(),
                "bomb and flow planning must never duplicate the granted entity tokens");
    }

    @Test
    void dormantCleanupSnapshotProducesNoConcreteWorldWork() {
        VolcanicHazardBatchPlanner planner = new VolcanicHazardBatchPlanner();
        EruptionSignal dormant = explosiveSignal(EruptionPhase.DORMANT, 0.0);
        EruptionScheduler.WorkGrant noWork = new EruptionScheduler.WorkGrant(0, 0, 0, 0, 0, 0);

        VolcanicHazardBatchPlanner.Plan plan = planner.plan(
                new VolcanicHazardQueue.HazardWork(dormant, noWork),
                7_000L);

        assertTrue(plan.ashCandidates().isEmpty());
        assertTrue(plan.bombLaunches().isEmpty());
        assertTrue(plan.flowSeed().isEmpty());
    }

    private static EruptionSignal explosiveSignal(EruptionPhase phase, double intensity) {
        return new EruptionSignal(
                UUID.fromString("4d735746-fb66-41c8-9b64-4c6720695923"),
                new BlockPos(48, 108, -32),
                phase,
                new EruptionProfile(0.92, 72, 420, 500L, 200L, 2_000L, 700L),
                new MagmaChamber(new MagmaComposition(0.72, 0.81), 9.0, 345.0, 0.36, 1_245.0, 0.3),
                phase == EruptionPhase.DORMANT ? 1.0 : 0.5,
                intensity);
    }
}
