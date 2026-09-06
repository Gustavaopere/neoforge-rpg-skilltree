package dev.gustavopere.volcanoes.volcano;

import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class PyroclasticTrailExposureTest {
    @Test
    void stationaryTrailUsesTheSameExplicitHeatAndParticulateExposureContract() {
        PyroclasticTrailState trail = new PyroclasticTrailState(
                UUID.fromString("68c6e7e8-2f8e-4602-b939-33026dad57b1"),
                new Vec3(4.0, 70.0, -3.0),
                3.5,
                0.45,
                0.60,
                5L,
                80L);

        PyroclasticExposure exposure = PyroclasticExposure.from(trail);

        assertEquals(trail.radiusBlocks(), exposure.radiusBlocks());
        assertEquals(trail.heatSeverity(), exposure.heatSeverity());
        assertEquals(trail.particulateSeverity(), exposure.particulateSeverity());
    }
}
