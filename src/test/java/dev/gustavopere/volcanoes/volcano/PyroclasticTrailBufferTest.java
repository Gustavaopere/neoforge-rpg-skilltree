package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PyroclasticTrailBufferTest {
    private static final UUID VOLCANO_A = UUID.fromString("b82b0c89-15e7-45e6-9db2-bc7e56ee60e0");
    private static final UUID VOLCANO_B = UUID.fromString("0d58ab40-b726-4fd9-95b1-3c7a17f41a82");

    @Test
    void samplingCadenceAndCapacityRemainBounded() {
        PyroclasticTrailBuffer buffer = new PyroclasticTrailBuffer(3, 2L, 6L);

        assertFalse(buffer.record(head(VOLCANO_A, 1L, 1.0)));
        assertTrue(buffer.record(head(VOLCANO_A, 2L, 2.0)));
        assertTrue(buffer.record(head(VOLCANO_A, 4L, 4.0)));
        assertTrue(buffer.record(head(VOLCANO_B, 2L, 20.0)));
        assertEquals(3, buffer.size());

        assertTrue(buffer.record(head(VOLCANO_A, 6L, 6.0)));
        assertEquals(3, buffer.size(), "recording beyond capacity must evict the oldest trail sample");
        assertEquals(4.0, buffer.samples().get(0).position().x,
                "oldest sample must be evicted deterministically");
    }

    @Test
    void tickExpiresSamplesAndDormantClearOnlyRemovesOneVolcano() {
        PyroclasticTrailBuffer buffer = new PyroclasticTrailBuffer(8, 1L, 2L);
        buffer.record(head(VOLCANO_A, 1L, 1.0));
        buffer.record(head(VOLCANO_B, 1L, 2.0));

        buffer.clear(VOLCANO_A);
        assertEquals(1, buffer.size());
        assertEquals(VOLCANO_B, buffer.samples().get(0).volcanoId());

        buffer.tick();
        assertEquals(1, buffer.size());
        buffer.tick();
        assertEquals(0, buffer.size(), "expired trail samples must be removed eagerly");
    }

    private static PyroclasticFlowState head(UUID volcanoId, long ageTicks, double x) {
        return new PyroclasticFlowState(
                volcanoId,
                new Vec3(x, 70.0, 0.0),
                new Vec3(0.4, 0.0, 0.1),
                5.0,
                0.7,
                0.6,
                ageTicks,
                300L);
    }
}
