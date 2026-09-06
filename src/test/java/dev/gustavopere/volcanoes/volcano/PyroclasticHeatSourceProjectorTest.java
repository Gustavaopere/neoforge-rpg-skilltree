package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

final class PyroclasticHeatSourceProjectorTest {
    @Test
    void flowHeadProjectsStableNamespacedDynamicHeatSource() {
        UUID volcanoId = UUID.fromString("12345678-1234-1234-1234-123456789abc");
        PyroclasticFlowState flow = new PyroclasticFlowState(
                volcanoId,
                new Vec3(12.5, 81.0, -6.25),
                new Vec3(0.1, 0.0, 0.2),
                8.0,
                0.72,
                0.81,
                10L,
                200L);

        VolcanicHeatSource first = PyroclasticHeatSourceProjector.fromFlow(flow, 400L, 20L);
        VolcanicHeatSource second = PyroclasticHeatSourceProjector.fromFlow(flow, 400L, 20L);

        assertEquals(first, second);
        assertEquals(PyroclasticHeatSourceProjector.sourceId(volcanoId), first.sourceId());
        assertNotEquals(volcanoId, first.sourceId(), "heat source identity must be namespaced from volcano identity");
        assertEquals(VolcanicHeatSource.Kind.PYROCLASTIC, first.kind());
        assertEquals(BlockPos.containing(flow.position()), first.center());
        assertEquals(flow.radiusBlocks(), first.radiusBlocks());
        assertEquals(flow.heatSeverity(), first.severity());
        assertEquals(420L, first.expiresAtTick());
    }
}
