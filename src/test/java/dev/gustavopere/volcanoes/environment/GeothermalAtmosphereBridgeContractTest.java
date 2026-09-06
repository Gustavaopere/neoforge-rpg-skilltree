package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.GeothermalFeatureType;
import dev.gustavopere.volcanoes.volcano.GeothermalSource;
import dev.gustavopere.volcanoes.volcano.GeothermalSourceLifecycleSink;
import dev.gustavopere.volcanoes.volcano.GeothermalSourceRegistry;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalAtmosphereBridgeContractTest {
    private static final String DIMENSION = "minecraft:overworld";

    @Test
    void bridgeIsOnlyAGeothermalLifecycleObserver() {
        assertTrue(GeothermalSourceLifecycleSink.class.isAssignableFrom(GeothermalAtmosphereBridge.class));
        assertInstanceOf(GeothermalSourceLifecycleSink.class,
                new GeothermalAtmosphereBridge(DIMENSION, new RecordingSink(), GeothermalAtmosphereProjectionPolicy.defaults(), 16));
    }

    @Test
    void registryReplayAndLiveRemovalUseTheSameStableIdentity() {
        GeothermalSourceRegistry registry = new GeothermalSourceRegistry();
        GeothermalSource source = source(GeothermalFeatureType.FUMAROLE, 0.52, "replay");
        assertTrue(registry.register(source));

        RecordingSink sink = new RecordingSink();
        GeothermalAtmosphereBridge bridge = new GeothermalAtmosphereBridge(
                DIMENSION, sink, GeothermalAtmosphereProjectionPolicy.defaults(), 16);
        assertTrue(registry.registerLifecycleSink(bridge));
        assertEquals(1, bridge.pendingCount());

        assertEquals(1, bridge.flush(8));
        assertEquals(source.persistenceId(), sink.upserts.get(source.persistenceId()).id());
        assertEquals(0, bridge.pendingCount());

        assertTrue(registry.remove(source.persistenceId()));
        assertFalse(sink.upserts.containsKey(source.persistenceId()));
        assertEquals(List.of(source.persistenceId()), sink.removes);
        assertTrue(registry.unregisterLifecycleSink(bridge));
    }

    @Test
    void capacityRejectionRotatesAndDoesNotStarveLaterSources() {
        RecordingSink sink = new RecordingSink();
        sink.rejectFirstId = source(GeothermalFeatureType.FUMAROLE, 0.4, "first").persistenceId();
        GeothermalAtmosphereBridge bridge = new GeothermalAtmosphereBridge(
                DIMENSION, sink, GeothermalAtmosphereProjectionPolicy.defaults(), 16);
        GeothermalSource first = sourceWithId(GeothermalFeatureType.FUMAROLE, 0.4, sink.rejectFirstId);
        GeothermalSource second = source(GeothermalFeatureType.SULFUROUS_VENT, 0.7, "second");

        bridge.upsert(first);
        bridge.upsert(second);
        assertEquals(2, bridge.flush(2));

        assertFalse(sink.upserts.containsKey(first.persistenceId()));
        assertTrue(sink.upserts.containsKey(second.persistenceId()));
        assertEquals(1, bridge.pendingCount());

        assertEquals(1, bridge.flush(1));
        assertTrue(sink.upserts.containsKey(first.persistenceId()));
        assertEquals(0, bridge.pendingCount());
    }

    @Test
    void latestWinsAndNonGasReplacementRetiresPreviouslyAdmittedSource() {
        RecordingSink sink = new RecordingSink();
        GeothermalAtmosphereBridge bridge = new GeothermalAtmosphereBridge(
                DIMENSION, sink, GeothermalAtmosphereProjectionPolicy.defaults(), 16);
        UUID id = UUID.nameUUIDFromBytes("latest".getBytes());
        GeothermalSource first = sourceWithId(GeothermalFeatureType.FUMAROLE, 0.2, id);
        GeothermalSource latest = sourceWithId(GeothermalFeatureType.FUMAROLE, 0.8, id);

        bridge.upsert(first);
        bridge.upsert(latest);
        assertEquals(1, bridge.pendingCount());
        bridge.flush(1);
        assertEquals(
                GeothermalAtmosphereProjectionPolicy.defaults().maxToxicGasPpm() * 0.8,
                sink.upserts.get(id).contribution().toxicGasPpm());

        bridge.upsert(sourceWithId(GeothermalFeatureType.HOT_SPRING, 0.1, id));
        assertFalse(sink.upserts.containsKey(id));
        assertEquals(id, sink.removes.getLast());
    }

    private static GeothermalSource source(GeothermalFeatureType type, double gasSeverity, String key) {
        return sourceWithId(type, gasSeverity, UUID.nameUUIDFromBytes(key.getBytes()));
    }

    private static GeothermalSource sourceWithId(GeothermalFeatureType type, double gasSeverity, UUID id) {
        return new GeothermalSource(id, type, new BlockPos(160, 68, 224), 10, 0.5, gasSeverity);
    }

    private static final class RecordingSink implements AtmosphericSourceSink {
        private final Map<UUID, AtmosphericSource> upserts = new HashMap<>();
        private final List<UUID> removes = new ArrayList<>();
        private UUID rejectFirstId;
        private boolean rejected;

        @Override
        public AtmosphericSourceAdmission tryUpsert(AtmosphericSource source) {
            if (!rejected && source.id().equals(rejectFirstId)) {
                rejected = true;
                return AtmosphericSourceAdmission.REJECTED_CAPACITY;
            }
            upserts.put(source.id(), source);
            return AtmosphericSourceAdmission.ACCEPTED;
        }

        @Override
        public void upsert(AtmosphericSource source) {
            upserts.put(source.id(), source);
        }

        @Override
        public boolean remove(UUID id) {
            removes.add(id);
            return upserts.remove(id) != null;
        }
    }
}
