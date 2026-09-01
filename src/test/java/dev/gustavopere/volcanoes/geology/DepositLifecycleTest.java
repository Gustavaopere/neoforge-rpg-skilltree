package dev.gustavopere.volcanoes.geology;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class DepositLifecycleTest {
    @Test
    void registeringSinkReplaysExistingDepositsDeterministicallyThenTracksChanges() {
        DepositRegistry registry = new DepositRegistry();
        GeologicalDeposit later = deposit(2, new BlockPos(64, 20, 64));
        GeologicalDeposit earlier = deposit(1, new BlockPos(0, 20, 0));
        registry.register(later);
        registry.register(earlier);

        RecordingSink sink = new RecordingSink();
        assertTrue(registry.registerLifecycleSink(sink));
        assertFalse(registry.registerLifecycleSink(sink));
        assertEquals(List.of("upsert:1", "upsert:2"), sink.events);

        GeologicalDeposit added = deposit(3, new BlockPos(128, 20, 128));
        assertTrue(registry.register(added));
        assertTrue(registry.remove(earlier.persistenceId()));
        assertFalse(registry.remove(earlier.persistenceId()));
        assertEquals(List.of("upsert:1", "upsert:2", "upsert:3", "remove:1"), sink.events);

        assertTrue(registry.unregisterLifecycleSink(sink));
        assertFalse(registry.unregisterLifecycleSink(sink));
        registry.register(deposit(4, new BlockPos(192, 20, 192)));
        assertEquals(List.of("upsert:1", "upsert:2", "upsert:3", "remove:1"), sink.events);
    }

    @Test
    void failingSinkCannotRollbackCoreOrBlockHealthySink() {
        DepositRegistry registry = new DepositRegistry();
        DepositLifecycleSink failing = new DepositLifecycleSink() {
            @Override
            public void upsert(GeologicalDeposit deposit) {
                throw new IllegalStateException("optional adapter failed");
            }

            @Override
            public void remove(UUID persistenceId) {
                throw new IllegalStateException("optional adapter failed");
            }
        };
        RecordingSink healthy = new RecordingSink();
        registry.registerLifecycleSink(failing);
        registry.registerLifecycleSink(healthy);

        GeologicalDeposit deposit = deposit(5, BlockPos.ZERO);
        assertTrue(registry.register(deposit));
        assertEquals(deposit, registry.get(deposit.persistenceId()).orElseThrow());
        assertEquals(List.of("upsert:5"), healthy.events);

        assertTrue(registry.remove(deposit.persistenceId()));
        assertTrue(registry.get(deposit.persistenceId()).isEmpty());
        assertEquals(List.of("upsert:5", "remove:5"), healthy.events);
    }

    @Test
    void restoredRegistryCanReplayFreshSinkWithoutPersistingTransientSubscription() {
        DepositRegistry original = new DepositRegistry();
        original.register(deposit(6, new BlockPos(16, 24, 16)));
        RecordingSink originalSink = new RecordingSink();
        original.registerLifecycleSink(originalSink);

        DepositRegistry restored = DepositRegistry.fromTag(original.toTag());
        RecordingSink freshSink = new RecordingSink();
        assertTrue(restored.registerLifecycleSink(freshSink));
        assertEquals(List.of("upsert:6"), freshSink.events);

        restored.register(deposit(7, new BlockPos(32, 24, 32)));
        assertEquals(List.of("upsert:6"), originalSink.events, "lifecycle sinks must remain transient across SavedData reload");
        assertEquals(List.of("upsert:6", "upsert:7"), freshSink.events);
    }

    private static GeologicalDeposit deposit(int value, BlockPos center) {
        return new GeologicalDeposit(
                id(value),
                ResourceLocation.parse("c:ores/copper"),
                center,
                12.0,
                0.65,
                DepositOrigin.HYDROTHERMAL);
    }

    private static UUID id(int value) {
        return new UUID(0L, value);
    }

    private static final class RecordingSink implements DepositLifecycleSink {
        private final List<String> events = new ArrayList<>();

        @Override
        public void upsert(GeologicalDeposit deposit) {
            events.add("upsert:" + deposit.persistenceId().getLeastSignificantBits());
        }

        @Override
        public void remove(UUID persistenceId) {
            events.add("remove:" + persistenceId.getLeastSignificantBits());
        }
    }
}
