package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.RockCategory;
import dev.gustavopere.volcanoes.geology.RockProfile;
import dev.gustavopere.volcanoes.geology.RockProfileResolver;
import dev.gustavopere.volcanoes.performance.PerformanceConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicLavaControllerTest {
    private static final RockProfile BASALT = new RockProfile(
            "basalt",
            RockCategory.IGNEOUS_EXTRUSIVE,
            0.82,
            0.18,
            1.9,
            1.25,
            0.85,
            0.30);

    @Test
    void genericGeologyDelegatesCompletelyToVanillaLava() {
        VolcanicLavaController controller = controller(RockProfileResolver.fallback());
        VolcanicLavaController.FlowPlan plan = controller.planStep(1L, 0, 64, 0, 500, 0, 0, true);
        assertEquals(VolcanicLavaController.FlowMode.VANILLA, plan.mode());
        assertTrue(plan.environment().usesVanillaFallback());
        assertEquals(0, plan.immediateBlockBudget());
        assertEquals(0, plan.deferredBlockBudget());
    }

    @Test
    void configuredFactoryUsesServerLavaBudget() {
        VolcanicLavaController controller = VolcanicLavaController.configured(
                new LavaFlowResolver((seed, x, y, z) -> BASALT),
                PerformanceConfig.budgets(64, 12, 8));
        VolcanicLavaController.FlowPlan plan = controller.planStep(2L, 4, 70, 8, 500, 0, 0, true);
        assertEquals(12, plan.immediateBlockBudget());
    }

    @Test
    void specializedFlowIsCappedPerTickAndPerEruption() {
        VolcanicLavaController controller = controller((seed, x, y, z) -> BASALT);
        VolcanicLavaController.FlowPlan normal = controller.planStep(2L, 4, 70, 8, 500, 0, 20, true);
        assertEquals(VolcanicLavaController.FlowMode.SPECIALIZED, normal.mode());
        assertEquals(32, normal.immediateBlockBudget());
        assertEquals(0, normal.deferredBlockBudget());

        VolcanicLavaController.FlowPlan nearEruptionCap = controller.planStep(2L, 4, 70, 8, 500, 0, 90, true);
        assertEquals(6, nearEruptionCap.immediateBlockBudget());

        VolcanicLavaController.FlowPlan exhausted = controller.planStep(2L, 4, 70, 8, 500, 0, 96, true);
        assertEquals(0, exhausted.immediateBlockBudget());
    }

    @Test
    void multipleFlowStepsShareCallerReportedTickBudget() {
        VolcanicLavaController controller = controller((seed, x, y, z) -> BASALT);
        VolcanicLavaController.FlowPlan first = controller.planStep(4L, 0, 64, 0, 24, 0, 0, true);
        assertEquals(24, first.immediateBlockBudget());
        VolcanicLavaController.FlowPlan second = controller.planStep(
                4L, 1, 64, 0, 24, first.immediateBlockBudget(), first.immediateBlockBudget(), true);
        assertEquals(8, second.immediateBlockBudget());
        VolcanicLavaController.FlowPlan exhausted = controller.planStep(4L, 2, 64, 0, 24, 32, 32, true);
        assertEquals(0, exhausted.immediateBlockBudget());
    }

    @Test
    void unloadedTargetChunkProducesDeferredWorkInsteadOfImmediateMutation() {
        VolcanicLavaController controller = controller((seed, x, y, z) -> BASALT);
        VolcanicLavaController.FlowPlan plan = controller.planStep(3L, 31, 65, 31, 100, 0, 0, false);
        assertEquals(VolcanicLavaController.FlowMode.DEFERRED, plan.mode());
        assertEquals(0, plan.immediateBlockBudget());
        assertEquals(32, plan.deferredBlockBudget());
    }

    @Test
    void chunkBoundaryQueueIsBoundedAndDrainsWithoutWorldAccess() {
        VolcanicLavaController controller = controller((seed, x, y, z) -> BASALT);
        ChunkPos chunk = new ChunkPos(8, -3);
        for (int i = 0; i < 64; i++) {
            assertTrue(controller.enqueueDeferred(chunk, new BlockPos(128 + (i & 15), 64, -48 + (i >> 4))));
        }
        assertFalse(controller.enqueueDeferred(chunk, new BlockPos(129, 64, -49)));
        assertEquals(64, controller.queuedForChunk(chunk));
        List<BlockPos> first = controller.drainDeferred(chunk, 16);
        assertEquals(16, first.size());
        assertEquals(48, controller.queuedForChunk(chunk));
        List<BlockPos> rest = controller.drainDeferred(chunk, 64);
        assertEquals(48, rest.size());
        assertEquals(0, controller.queuedForChunk(chunk));
    }

    private static VolcanicLavaController controller(RockProfileResolver geology) {
        return new VolcanicLavaController(new LavaFlowResolver(geology), 32, 96, 64);
    }
}
