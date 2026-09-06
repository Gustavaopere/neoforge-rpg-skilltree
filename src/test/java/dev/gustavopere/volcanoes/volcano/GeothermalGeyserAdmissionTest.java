package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class GeothermalGeyserAdmissionTest {
    @Test
    void alreadyEmittedNearbyGeysersDoNotStarveNinthPendingPulse() {
        GeothermalSourceRegistry sources = new GeothermalSourceRegistry(32);
        VolcanicHeatSourceIndex index = new VolcanicHeatSourceIndex(32, 64.0, 64.0, 64);
        sources.registerLifecycleSink(new GeothermalHeatIndexSink(index));

        List<GeothermalSource> geysers = java.util.stream.IntStream.range(0, 9)
                .mapToObj(i -> source(1_000L + i, new BlockPos(i + 1, 80, 0)))
                .toList();
        geysers.forEach(sources::register);

        Set<UUID> alreadyEmitted = Set.copyOf(geysers.subList(0, 8).stream()
                .map(GeothermalSource::persistenceId)
                .toList());
        GeothermalSource ninth = geysers.get(8);

        List<GeothermalSource> due = GeothermalNativeEffects.dueGeysers(
                index,
                sources,
                List.of(new BlockPos(0, 80, 0)),
                10_000L,
                1,
                1,
                1_201L,
                source -> !alreadyEmitted.contains(source.persistenceId()));

        assertEquals(List.of(ninth), due,
                "already emitted geysers must be filtered before the per-observer result cap");
    }

    private static GeothermalSource source(long seed, BlockPos center) {
        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(GeothermalFeatureType.GEYSER);
        GeothermalFeaturePlacement placement = new GeothermalFeaturePlacement(
                GeothermalFeatureType.GEYSER,
                center,
                profile.radiusBlocks(),
                profile.heatSeverity(),
                profile.gasSeverity(),
                0.0);
        return GeothermalSource.fromPlacement(seed, placement);
    }
}
