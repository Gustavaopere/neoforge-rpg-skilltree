package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalGeyserPulseProjectorTest {
    @Test
    void pulseUsesDistinctStableIdentityAndShortLivedHeatBoost() {
        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(GeothermalFeatureType.GEYSER);
        GeothermalSource source = GeothermalSource.fromPlacement(
                91L,
                new GeothermalFeaturePlacement(
                        GeothermalFeatureType.GEYSER,
                        new BlockPos(8, 80, 8),
                        profile.radiusBlocks(),
                        profile.heatSeverity(),
                        profile.gasSeverity(),
                        0.0));

        VolcanicHeatSource pulse = GeothermalGeyserPulseProjector.project(source, 400L, 40L);
        VolcanicHeatSource repeated = GeothermalGeyserPulseProjector.project(source, 400L, 40L);

        assertEquals(pulse, repeated);
        assertNotEquals(source.persistenceId(), pulse.sourceId());
        assertEquals(VolcanicHeatSource.Kind.GEOTHERMAL, pulse.kind());
        assertEquals(source.center(), pulse.center());
        assertEquals(440L, pulse.expiresAtTick());
        assertTrue(pulse.radiusBlocks() >= source.radiusBlocks());
        assertTrue(pulse.severity() >= source.heatSeverity() && pulse.severity() <= 1.0);
    }
}
