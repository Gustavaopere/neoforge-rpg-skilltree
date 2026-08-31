package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalHeatIndexSinkTest {
    @Test
    void lifecycleReplayAndRemovalKeepHeatIndexSynchronized() {
        GeothermalSourceRegistry sources = new GeothermalSourceRegistry(8);
        GeothermalSource first = GeothermalSource.fromPlacement(1L, placement(GeothermalFeatureType.HOT_SPRING, 0));
        sources.register(first);

        VolcanicHeatSourceIndex index = new VolcanicHeatSourceIndex(32, 128.0, 256.0, 16);
        GeothermalHeatIndexSink sink = new GeothermalHeatIndexSink(index);
        assertTrue(sources.registerLifecycleSink(sink));
        assertEquals(List.of(first.toHeatSource()), index.nearby(first.center(), 1.0, 8, 0L));

        GeothermalSource second = GeothermalSource.fromPlacement(2L, placement(GeothermalFeatureType.FUMAROLE, 64));
        sources.register(second);
        assertEquals(List.of(second.toHeatSource()), index.nearby(second.center(), 1.0, 8, 0L));

        sources.remove(first.persistenceId());
        assertTrue(index.nearby(first.center(), 1.0, 8, 0L).isEmpty());
    }

    private static GeothermalFeaturePlacement placement(GeothermalFeatureType type, int x) {
        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(type);
        return new GeothermalFeaturePlacement(
                type,
                new BlockPos(x, 80, 8),
                profile.radiusBlocks(),
                profile.heatSeverity(),
                profile.gasSeverity(),
                profile.hydrothermalDepositChance());
    }
}
