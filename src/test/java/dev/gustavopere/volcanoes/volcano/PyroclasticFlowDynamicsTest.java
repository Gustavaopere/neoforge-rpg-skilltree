package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PyroclasticFlowDynamicsTest {
    private static final UUID VOLCANO_ID = UUID.fromString("6c77c1b8-ec34-466a-9ce2-e445bd11a919");

    @Test
    void flowMovesTowardLowerTerrainWithBoundedSpeedAndFiniteDecay() {
        PyroclasticFlowState state = new PyroclasticFlowState(
                VOLCANO_ID,
                new Vec3(0.0, 100.0, 0.0),
                new Vec3(0.20, 0.0, 0.0),
                10.0,
                0.90,
                0.82,
                0L,
                80L);
        PyroclasticSlopeSample slope = new PyroclasticSlopeSample(101.0, 92.0, 100.0, 100.0);

        PyroclasticFlowState next = PyroclasticFlowDynamics.step(state, slope, false);

        assertTrue(next.position().x > state.position().x, "lower east terrain must bias the flow eastward");
        assertTrue(next.velocity().horizontalDistance() <= PyroclasticFlowDynamics.MAX_HORIZONTAL_SPEED + 1.0e-9);
        assertEquals(1L, next.ageTicks());
        assertTrue(next.radiusBlocks() <= state.radiusBlocks());
        assertTrue(next.heatSeverity() < state.heatSeverity());
        assertTrue(next.particulateSeverity() < state.particulateSeverity());
        assertTrue(next.active());
    }

    @Test
    void blockedOrExpiredFlowTerminatesWithoutRecursiveTerrainWork() {
        PyroclasticFlowState state = new PyroclasticFlowState(
                VOLCANO_ID,
                Vec3.ZERO,
                new Vec3(0.3, 0.0, 0.1),
                4.0,
                0.55,
                0.60,
                9L,
                10L);
        PyroclasticSlopeSample flat = new PyroclasticSlopeSample(64.0, 64.0, 64.0, 64.0);

        assertFalse(PyroclasticFlowDynamics.step(state, flat, true).active());
        assertFalse(PyroclasticFlowDynamics.step(state, flat, false).active());
    }

    @Test
    void exposureAndTerrainMutationStayExplicitAndFailClosed() {
        PyroclasticFlowState state = new PyroclasticFlowState(
                VOLCANO_ID,
                Vec3.ZERO,
                new Vec3(0.2, 0.0, 0.0),
                6.0,
                0.72,
                0.88,
                4L,
                60L);
        PyroclasticExposure exposure = PyroclasticExposure.from(state);
        PyroclasticTerrainPolicy policy = PyroclasticTerrainPolicy.safeDefaults();

        assertEquals(6.0, exposure.radiusBlocks());
        assertEquals(0.72, exposure.heatSeverity());
        assertEquals(0.88, exposure.particulateSeverity());
        assertTrue(policy.canMutate(true, true, true, false, false));
        assertFalse(policy.canMutate(false, true, true, false, false));
        assertFalse(policy.canMutate(true, false, true, false, false));
        assertFalse(policy.canMutate(true, true, false, false, false));
        assertFalse(policy.canMutate(true, true, true, true, false));
        assertFalse(policy.canMutate(true, true, true, false, true));
    }
}
