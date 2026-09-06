package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicBombPlannerTest {
    private static final UUID VOLCANO_ID = UUID.fromString("e8c0f259-5442-4d65-9d6e-51148a9fe9e8");
    private static final BlockPos VENT = new BlockPos(48, 112, -80);

    @Test
    void launchPlanningIsDeterministicAndNeverExceedsGrantedEntityWork() {
        VolcanicBombPlanner planner = new VolcanicBombPlanner();
        EruptionSignal signal = signal(EruptionPhase.SUSTAINED, 0.84);
        EruptionScheduler.WorkGrant grant = new EruptionScheduler.WorkGrant(20, 3, 0, 0, 0, 0);

        List<VolcanicBombLaunch> first = planner.launches(signal, grant, 12_345L);
        List<VolcanicBombLaunch> second = planner.launches(signal, grant, 12_345L);

        assertEquals(first, second);
        assertEquals(3, first.size());
        for (VolcanicBombLaunch launch : first) {
            assertEquals(VOLCANO_ID, launch.volcanoId());
            assertEquals(VENT.getCenter(), launch.origin());
            assertTrue(launch.velocity().y > 0.0, "bombs must leave the vent on an upward ballistic arc");
            assertTrue(launch.velocity().horizontalDistance() > 0.0);
            assertTrue(launch.velocity().length() <= VolcanicBombPlanner.MAX_LAUNCH_SPEED + 1.0e-9);
            assertTrue(launch.maxLifetimeTicks() > 0L);
        }
    }

    @Test
    void nonExplosivePhasesAndZeroEntityBudgetDoNotLaunchBombs() {
        VolcanicBombPlanner planner = new VolcanicBombPlanner();
        EruptionScheduler.WorkGrant noEntities = new EruptionScheduler.WorkGrant(8, 0, 0, 0, 0, 0);
        EruptionScheduler.WorkGrant entities = new EruptionScheduler.WorkGrant(0, 4, 0, 0, 0, 0);

        assertTrue(planner.launches(signal(EruptionPhase.SUSTAINED, 0.80), noEntities, 1L).isEmpty());
        assertTrue(planner.launches(signal(EruptionPhase.PRECURSORS, 0.18), entities, 1L).isEmpty());
        assertTrue(planner.launches(signal(EruptionPhase.DORMANT, 0.0), entities, 1L).isEmpty());
    }

    @Test
    void terrainImpactRequiresAuthorityLoadedNaturalUnprotectedTargetWithoutBlockEntity() {
        VolcanicBombImpactPolicy policy = VolcanicBombImpactPolicy.safeDefaults();

        assertTrue(policy.canMutate(true, true, true, false, false));
        assertFalse(policy.canMutate(false, true, true, false, false));
        assertFalse(policy.canMutate(true, false, true, false, false));
        assertFalse(policy.canMutate(true, true, false, false, false));
        assertFalse(policy.canMutate(true, true, true, true, false));
        assertFalse(policy.canMutate(true, true, true, false, true));
    }

    private static EruptionSignal signal(EruptionPhase phase, double intensity) {
        EruptionProfile profile = new EruptionProfile(0.90, 96, 640, 800L, 300L, 3_600L, 1_200L);
        MagmaChamber chamber = new MagmaChamber(
                new MagmaComposition(0.69, 0.74),
                9.0,
                340.0,
                0.31,
                1_240.0,
                0.28);
        return new EruptionSignal(
                VOLCANO_ID,
                VENT,
                phase,
                profile,
                chamber,
                phase == EruptionPhase.DORMANT ? 1.0 : 0.5,
                intensity);
    }
}
