package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PyroclasticFlowPlannerTest {
    @Test
    void oneGrantedFlowTokenCreatesOneDeterministicFiniteHead() {
        EruptionSignal signal = signal();
        PyroclasticFlowState first = PyroclasticFlowPlanner.seed(signal, 1, 7_000L).orElseThrow();
        PyroclasticFlowState second = PyroclasticFlowPlanner.seed(signal, 1, 7_000L).orElseThrow();

        assertEquals(first, second);
        assertEquals(signal.volcanoId(), first.volcanoId());
        assertEquals(signal.source().getCenter(), first.position());
        assertTrue(first.velocity().horizontalDistance() > 0.0);
        assertTrue(first.velocity().horizontalDistance() <= PyroclasticFlowDynamics.MAX_HORIZONTAL_SPEED);
        assertTrue(first.radiusBlocks() > 0.0);
        assertTrue(first.heatSeverity() > 0.0);
        assertTrue(first.particulateSeverity() > 0.0);
        assertTrue(first.maxLifetimeTicks() > 0L);
    }

    @Test
    void zeroFlowBudgetCreatesNothing() {
        assertTrue(PyroclasticFlowPlanner.seed(signal(), 0, 7_000L).isEmpty());
    }

    private static EruptionSignal signal() {
        return new EruptionSignal(
                UUID.fromString("eb04ed7f-f42e-471f-a21e-f73dad2f2935"),
                new BlockPos(32, 110, -16),
                EruptionPhase.SUSTAINED,
                new EruptionProfile(0.92, 72, 420, 500L, 200L, 2_000L, 700L),
                new MagmaChamber(new MagmaComposition(0.72, 0.81), 9.0, 345.0, 0.36, 1_245.0, 0.3),
                0.5,
                0.84);
    }
}
