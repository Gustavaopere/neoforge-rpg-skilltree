package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.geology.DepositRegistry;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalSourceRegistryTest {
    @Test
    void sourceIdentityAndHeatProjectionAreStable() {
        GeothermalFeaturePlacement placement = placement(GeothermalFeatureType.HOT_SPRING, 1.0);
        GeothermalSource first = GeothermalSource.fromPlacement(91L, placement);
        GeothermalSource second = GeothermalSource.fromPlacement(91L, placement);

        assertEquals(first, second);
        assertEquals(placement.type(), first.type());
        assertEquals(placement.center(), first.center());
        assertEquals(placement.gasSeverity(), first.gasSeverity());
        VolcanicHeatSource heat = first.toHeatSource();
        assertEquals(first.persistenceId(), heat.sourceId());
        assertEquals(VolcanicHeatSource.Kind.GEOTHERMAL, heat.kind());
        assertEquals(first.center(), heat.center());
        assertEquals(first.radiusBlocks(), heat.radiusBlocks());
        assertEquals(first.heatSeverity(), heat.severity());
        assertEquals(Long.MAX_VALUE, heat.expiresAtTick());
    }

    @Test
    void registryPersistsAndReplaysTransientLifecycle() {
        GeothermalSourceRegistry registry = new GeothermalSourceRegistry(4);
        GeothermalSource first = GeothermalSource.fromPlacement(11L, placement(GeothermalFeatureType.FUMAROLE, 0.0));
        GeothermalSource second = GeothermalSource.fromPlacement(12L, placement(GeothermalFeatureType.GEYSER, 0.0));
        assertTrue(registry.register(second));
        assertTrue(registry.register(first));
        assertFalse(registry.register(first));

        RecordingSink sink = new RecordingSink();
        assertTrue(registry.registerLifecycleSink(sink));
        assertFalse(registry.registerLifecycleSink(sink));
        assertEquals(registry.all().stream().map(source -> "upsert:" + source.persistenceId()).toList(), sink.events);

        GeothermalSourceRegistry restored = GeothermalSourceRegistry.fromTag(registry.toTag());
        assertEquals(registry.all(), restored.all());
        RecordingSink restoredSink = new RecordingSink();
        restored.registerLifecycleSink(restoredSink);
        assertEquals(restored.all().stream().map(source -> "upsert:" + source.persistenceId()).toList(), restoredSink.events);
    }

    @Test
    void capacityIsFailClosedWithoutEvictingExistingSource() {
        GeothermalSourceRegistry registry = new GeothermalSourceRegistry(1);
        GeothermalSource first = GeothermalSource.fromPlacement(21L, placement(GeothermalFeatureType.MUD_POT, 0.0));
        GeothermalSource overflow = GeothermalSource.fromPlacement(22L, placement(GeothermalFeatureType.SULFUROUS_VENT, 0.0));

        assertTrue(registry.register(first));
        assertFalse(registry.register(overflow));
        assertEquals(List.of(first), registry.all());
    }

    @Test
    void generatedPlacementReconcilesSourceAndHydrothermalDepositIdempotently() {
        GeothermalSourceRegistry sources = new GeothermalSourceRegistry(8);
        DepositRegistry deposits = new DepositRegistry();
        GeothermalFeaturePlacement placement = placement(GeothermalFeatureType.HOT_SPRING, 1.0);

        GeothermalWorldgenRuntime.PersistResult first = GeothermalWorldgenRuntime.persistGenerated(
                sources,
                deposits,
                new HydrothermalDepositProjector(),
                77L,
                placement);
        GeothermalWorldgenRuntime.PersistResult second = GeothermalWorldgenRuntime.persistGenerated(
                sources,
                deposits,
                new HydrothermalDepositProjector(),
                77L,
                placement);

        assertTrue(first.sourceRegistered());
        assertTrue(first.sourceAuthoritative());
        assertTrue(first.depositRegistered());
        assertFalse(second.sourceRegistered());
        assertTrue(second.sourceAuthoritative());
        assertFalse(second.depositRegistered());
        assertEquals(1, sources.size());
        assertEquals(1, deposits.size());
    }

    @Test
    void pendingMetadataRetriesAfterPersistentCapacityFrees() {
        GeothermalPendingQueue queue = new GeothermalPendingQueue(2, 1);
        GeothermalSourceRegistry sources = new GeothermalSourceRegistry(1);
        DepositRegistry deposits = new DepositRegistry();
        HydrothermalDepositProjector projector = new HydrothermalDepositProjector();

        GeothermalFeaturePlacement occupiedPlacement = placement(GeothermalFeatureType.MUD_POT, 0.0);
        GeothermalSource occupied = GeothermalSource.fromPlacement(1L, occupiedPlacement);
        assertTrue(sources.register(occupied));

        GeothermalFeaturePlacement pendingPlacement = new GeothermalFeaturePlacement(
                GeothermalFeatureType.HOT_SPRING,
                new BlockPos(256, 90, -128),
                3,
                0.8,
                0.2,
                1.0);
        assertTrue(queue.enqueue(2L, pendingPlacement));

        assertEquals(0, GeothermalWorldgenRuntime.persistPending(queue, sources, deposits, projector));
        assertEquals(1, queue.size(), "failed authority admission must retain committed metadata work");
        assertEquals(0, deposits.size(), "deposit must not exist without authoritative geothermal source");

        assertTrue(sources.remove(occupied.persistenceId()));
        assertEquals(1, GeothermalWorldgenRuntime.persistPending(queue, sources, deposits, projector));
        assertTrue(queue.isEmpty());
        assertEquals(1, sources.size());
        assertEquals(1, deposits.size());
    }

    @Test
    void removalNotifiesLifecycleSink() {
        GeothermalSourceRegistry registry = new GeothermalSourceRegistry(4);
        GeothermalSource source = GeothermalSource.fromPlacement(31L, placement(GeothermalFeatureType.FUMAROLE, 0.0));
        RecordingSink sink = new RecordingSink();
        registry.registerLifecycleSink(sink);
        registry.register(source);

        assertTrue(registry.remove(source.persistenceId()));
        assertFalse(registry.remove(source.persistenceId()));
        assertEquals(List.of("upsert:" + source.persistenceId(), "remove:" + source.persistenceId()), sink.events);
        assertTrue(registry.unregisterLifecycleSink(sink));
        assertFalse(registry.unregisterLifecycleSink(sink));
    }

    private static GeothermalFeaturePlacement placement(GeothermalFeatureType type, double depositChance) {
        return new GeothermalFeaturePlacement(
                type,
                new BlockPos(120, 84, -72),
                Math.max(1, GeothermalFeatureProfile.defaults(type).radiusBlocks()),
                GeothermalFeatureProfile.defaults(type).heatSeverity(),
                GeothermalFeatureProfile.defaults(type).gasSeverity(),
                depositChance);
    }

    private static final class RecordingSink implements GeothermalSourceLifecycleSink {
        private final List<String> events = new ArrayList<>();

        @Override
        public void upsert(GeothermalSource source) {
            events.add("upsert:" + source.persistenceId());
        }

        @Override
        public void remove(UUID persistenceId) {
            events.add("remove:" + persistenceId);
        }
    }
}
