package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicHazardAllocationTest {
    @Test
    void bombAndFlowBudgetsPreserveBothBlockAndEntityGrants() {
        EruptionSignal explosive = signal(0.86, 0.74, 0.82, 0.34);
        EruptionScheduler.WorkGrant grant = new EruptionScheduler.WorkGrant(11, 4, 0, 0, 0, 0);

        VolcanicHazardAllocation allocation = VolcanicHazardAllocation.from(explosive, grant);

        assertEquals(
                grant.immediateBlocks(),
                allocation.ashBlockWork() + allocation.bombTerrainWork() + allocation.flowTerrainWork());
        assertEquals(9, allocation.ashBlockWork());
        assertEquals(1, allocation.bombTerrainWork());
        assertEquals(1, allocation.flowTerrainWork());
        assertTrue(allocation.bombEntityWork() >= 0);
        assertTrue(allocation.flowSpawnWork() >= 0);
        assertEquals(4, allocation.bombEntityWork() + allocation.flowSpawnWork());
        assertTrue(allocation.flowSpawnWork() <= 1, "one signal may seed at most one flow head");
        assertTrue(allocation.bombTerrainWork() <= 1, "one signal may authorize at most one bomb terrain mutation");
        assertTrue(allocation.flowTerrainWork() <= 1, "one signal may authorize at most one flow terrain mutation");
    }

    @Test
    void modestEruptionsReserveOnlyBombTerrainAndKeepAtLeastOneAshToken() {
        EruptionSignal modest = signal(0.38, 0.52, 0.30, 0.12);
        EruptionScheduler.WorkGrant grant = new EruptionScheduler.WorkGrant(5, 3, 0, 0, 0, 0);

        VolcanicHazardAllocation allocation = VolcanicHazardAllocation.from(modest, grant);

        assertEquals(0, allocation.flowSpawnWork());
        assertEquals(0, allocation.flowTerrainWork());
        assertEquals(3, allocation.bombEntityWork());
        assertEquals(1, allocation.bombTerrainWork());
        assertEquals(4, allocation.ashBlockWork());
        assertEquals(
                grant.immediateBlocks(),
                allocation.ashBlockWork() + allocation.bombTerrainWork() + allocation.flowTerrainWork());
    }

    private static EruptionSignal signal(double intensity, double silica, double volatiles, double gas) {
        EruptionProfile profile = new EruptionProfile(0.9, 80, 480, 600L, 240L, 2_400L, 800L);
        return new EruptionSignal(
                UUID.fromString("d47493bd-d717-42ee-b9d4-7014d764682d"),
                new BlockPos(0, 100, 0),
                EruptionPhase.SUSTAINED,
                profile,
                new MagmaChamber(new MagmaComposition(silica, volatiles), 8.0, 330.0, gas, 1_230.0, 0.25),
                0.5,
                intensity);
    }
}
