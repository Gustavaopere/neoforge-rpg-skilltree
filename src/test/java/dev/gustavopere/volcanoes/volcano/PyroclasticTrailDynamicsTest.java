package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PyroclasticTrailDynamicsTest {
    private static final UUID VOLCANO_ID = UUID.fromString("ed68c1d1-356b-4717-8d3c-e6405c0f0db0");

    @Test
    void trailSampleIsStationaryBoundedAndDecaysDeterministically() {
        PyroclasticFlowState head = new PyroclasticFlowState(
                VOLCANO_ID,
                new Vec3(12.5, 70.25, -8.5),
                new Vec3(0.8, 0.0, -0.3),
                6.0,
                0.8,
                0.7,
                20L,
                300L);

        PyroclasticTrailState trail = PyroclasticTrailState.fromHead(head, 80L);
        assertEquals(VOLCANO_ID, trail.volcanoId());
        assertEquals(head.position(), trail.position());
        assertEquals(0L, trail.ageTicks());
        assertEquals(80L, trail.maxLifetimeTicks());
        assertTrue(trail.radiusBlocks() > 0.0 && trail.radiusBlocks() <= head.radiusBlocks());
        assertTrue(trail.heatSeverity() > 0.0 && trail.heatSeverity() <= head.heatSeverity());
        assertTrue(trail.particulateSeverity() > 0.0
                && trail.particulateSeverity() <= head.particulateSeverity());

        PyroclasticTrailState first = PyroclasticTrailDynamics.step(trail);
        PyroclasticTrailState repeated = PyroclasticTrailDynamics.step(trail);
        assertEquals(first, repeated, "trail decay must be deterministic");
        assertEquals(trail.position(), first.position(), "trail samples must remain stationary");
        assertEquals(1L, first.ageTicks());
        assertTrue(first.radiusBlocks() < trail.radiusBlocks());
        assertTrue(first.heatSeverity() < trail.heatSeverity());
        assertTrue(first.particulateSeverity() < trail.particulateSeverity());
    }

    @Test
    void trailAlwaysTerminatesWithinConfiguredLifetime() {
        PyroclasticTrailState state = new PyroclasticTrailState(
                VOLCANO_ID,
                new Vec3(1.0, 64.0, 1.0),
                4.0,
                0.6,
                0.5,
                0L,
                12L);

        int steps = 0;
        while (state.active() && steps <= 12) {
            state = PyroclasticTrailDynamics.step(state);
            steps++;
        }

        assertFalse(state.active());
        assertTrue(steps <= 12, "trail must not outlive its configured lifetime");
        assertEquals(0.0, state.radiusBlocks());
        assertEquals(0.0, state.heatSeverity());
        assertEquals(0.0, state.particulateSeverity());
    }
}
