package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class GeothermalSurfaceAnchorTest {
    @Test
    void plannedPlacementIsReanchoredToSampledSurfaceWithoutChangingPhysicalProfile() {
        GeothermalFeaturePlacement planned = new GeothermalFeaturePlacement(
                GeothermalFeatureType.FUMAROLE,
                new BlockPos(120, 0, -72),
                3,
                0.75,
                0.65,
                0.25);
        BlockPos sampledSurface = new BlockPos(120, 91, -72);

        GeothermalFeaturePlacement anchored = GeothermalSurfaceAnchor.anchor(planned, sampledSurface);

        assertNotSame(planned, anchored);
        assertEquals(sampledSurface, anchored.center());
        assertEquals(planned.type(), anchored.type());
        assertEquals(planned.radiusBlocks(), anchored.radiusBlocks());
        assertEquals(planned.heatSeverity(), anchored.heatSeverity());
        assertEquals(planned.gasSeverity(), anchored.gasSeverity());
        assertEquals(planned.hydrothermalDepositChance(), anchored.hydrothermalDepositChance());
    }

    @Test
    void anchorRejectsSampleThatWouldMovePlannerOwnedXZ() {
        GeothermalFeaturePlacement planned = GeothermalFeaturePlacement.fromProfile(
                new BlockPos(8, 0, 8),
                GeothermalFeatureProfile.defaults(GeothermalFeatureType.HOT_SPRING));

        assertThrows(IllegalArgumentException.class, () -> GeothermalSurfaceAnchor.anchor(
                planned,
                new BlockPos(9, 73, 8)));
    }
}
