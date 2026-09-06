package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class AshDepositionPlannerTest {
    private static final UUID VOLCANO_ID = UUID.fromString("c91464b8-28e0-4c29-bb3f-7b2f6991147c");
    private static final BlockPos VENT = new BlockPos(64, 110, -96);

    @Test
    void candidatesAreDeterministicBoundedBySharedBlockGrantAndRemainInsidePlume() {
        AshPlumeEmission emission = AshPlumeEmission.from(signal(EruptionPhase.SUSTAINED, 0.72));
        EruptionScheduler.WorkGrant grant = new EruptionScheduler.WorkGrant(7, 0, 9, 0, 0, 0);
        AshDepositionPlanner planner = new AshDepositionPlanner();

        List<BlockPos> first = planner.candidates(emission, grant, 12_400L);
        List<BlockPos> second = planner.candidates(emission, grant, 12_400L);

        assertEquals(first, second);
        assertFalse(first.isEmpty());
        assertTrue(first.size() <= grant.immediateBlocks());
        assertEquals(first.size(), new HashSet<>(first).size(), "one tick must not spend two work tokens on one column");
        double radiusSquared = emission.plumeRadiusBlocks() * emission.plumeRadiusBlocks();
        for (BlockPos candidate : first) {
            assertEquals(VENT.getY(), candidate.getY());
            long dx = candidate.getX() - VENT.getX();
            long dz = candidate.getZ() - VENT.getZ();
            assertTrue(dx * dx + dz * dz <= radiusSquared);
        }
    }

    @Test
    void inactivePlumeOrZeroImmediateBlockBudgetProducesNoCandidates() {
        AshDepositionPlanner planner = new AshDepositionPlanner();
        AshPlumeEmission dormant = AshPlumeEmission.from(signal(EruptionPhase.DORMANT, 0.0));
        AshPlumeEmission active = AshPlumeEmission.from(signal(EruptionPhase.OPENING, 0.35));

        assertTrue(planner.candidates(dormant, grant(8), 50L).isEmpty());
        assertTrue(planner.candidates(active, grant(0), 50L).isEmpty());
    }

    @Test
    void depositionPolicyFailsClosedForUnloadedProtectedOrNonTaggedSurfaces() {
        AshDepositionPolicy policy = AshDepositionPolicy.safeDefaults();

        assertTrue(policy.canDeposit(true, true, false, false, true));
        assertFalse(policy.canDeposit(false, true, false, false, true), "unloaded chunks must never be force-loaded");
        assertFalse(policy.canDeposit(true, false, false, false, true), "surface must be in the replaceable-surface tag");
        assertFalse(policy.canDeposit(true, true, true, false, true), "claims/player structures are protected");
        assertFalse(policy.canDeposit(true, true, false, true, true), "block entities are never deposition targets");
        assertFalse(policy.canDeposit(true, true, false, false, false), "ash needs a free/replaceable target above the surface");
    }

    private static EruptionScheduler.WorkGrant grant(int immediateBlocks) {
        return new EruptionScheduler.WorkGrant(immediateBlocks, 0, 0, 0, 0, 0);
    }

    private static EruptionSignal signal(EruptionPhase phase, double intensity) {
        EruptionProfile profile = new EruptionProfile(0.85, 72, 320, 600L, 300L, 2_400L, 900L);
        MagmaChamber chamber = new MagmaChamber(
                new MagmaComposition(0.66, 0.70),
                7.5,
                315.0,
                0.22,
                1_210.0,
                0.20);
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
