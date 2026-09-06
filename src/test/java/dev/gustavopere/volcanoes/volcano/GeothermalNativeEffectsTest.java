package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalNativeEffectsTest {
    @Test
    void dueGeysersArePlayerLocalDeduplicatedAndTypeFiltered() {
        GeothermalSourceRegistry sources = new GeothermalSourceRegistry(8);
        VolcanicHeatSourceIndex index = new VolcanicHeatSourceIndex(32, 64.0, 64.0, 16);
        sources.registerLifecycleSink(new GeothermalHeatIndexSink(index));

        GeothermalSource geyser = source(1L, GeothermalFeatureType.GEYSER, new BlockPos(8, 80, 8));
        GeothermalSource spring = source(2L, GeothermalFeatureType.HOT_SPRING, new BlockPos(12, 80, 8));
        sources.register(geyser);
        sources.register(spring);

        long pulseTick = GeothermalGeyserCycle.forSource(geyser).phaseTicks();
        List<GeothermalSource> due = GeothermalNativeEffects.dueGeysers(
                index,
                sources,
                List.of(new BlockPos(8, 80, 8), new BlockPos(9, 80, 8)),
                pulseTick,
                32,
                16);

        assertEquals(List.of(geyser), due,
                "duplicate nearby observers must not duplicate one geyser pulse and non-geysers are excluded");
        assertTrue(GeothermalNativeEffects.dueGeysers(
                index,
                sources,
                List.of(new BlockPos(1_000, 80, 1_000)),
                pulseTick,
                32,
                16).isEmpty());
    }

    @Test
    void denseNonGeyserSourcesDoNotStarveNearbyGeyserDiscovery() {
        GeothermalSourceRegistry sources = new GeothermalSourceRegistry(32);
        VolcanicHeatSourceIndex index = new VolcanicHeatSourceIndex(32, 64.0, 64.0, 64);
        sources.registerLifecycleSink(new GeothermalHeatIndexSink(index));

        BlockPos observer = new BlockPos(0, 80, 0);
        for (int i = 0; i < GeothermalNativeEffects.MAX_HEAT_SOURCES_PER_OBSERVER; i++) {
            sources.register(source(
                    100L + i,
                    GeothermalFeatureType.HOT_SPRING,
                    new BlockPos(i + 1, 80, 0)));
        }
        GeothermalSource geyser = source(999L, GeothermalFeatureType.GEYSER, new BlockPos(20, 80, 0));
        sources.register(geyser);

        long pulseTick = GeothermalGeyserCycle.forSource(geyser).phaseTicks();
        List<GeothermalSource> due = GeothermalNativeEffects.dueGeysers(
                index,
                sources,
                List.of(observer),
                pulseTick,
                1,
                1);

        assertEquals(List.of(geyser), due,
                "bounded non-geyser heat results must not hide a due geyser inside the observer radius");
    }

    @Test
    void observerAndPulseCapsAreHardBounds() {
        GeothermalSourceRegistry sources = new GeothermalSourceRegistry(64);
        VolcanicHeatSourceIndex index = new VolcanicHeatSourceIndex(32, 64.0, 64.0, 128);
        sources.registerLifecycleSink(new GeothermalHeatIndexSink(index));

        GeothermalSource geyser = source(11L, GeothermalFeatureType.GEYSER, BlockPos.ZERO);
        sources.register(geyser);
        long pulseTick = GeothermalGeyserCycle.forSource(geyser).phaseTicks();

        List<BlockPos> observers = java.util.stream.IntStream.range(0, 128)
                .mapToObj(ignored -> BlockPos.ZERO)
                .toList();
        assertTrue(GeothermalNativeEffects.dueGeysers(index, sources, observers, pulseTick, 4, 1).size() <= 1);
    }

    private static GeothermalSource source(long seed, GeothermalFeatureType type, BlockPos center) {
        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(type);
        GeothermalFeaturePlacement placement = new GeothermalFeaturePlacement(
                type,
                center,
                profile.radiusBlocks(),
                profile.heatSeverity(),
                profile.gasSeverity(),
                0.0);
        return GeothermalSource.fromPlacement(seed, placement);
    }
}
