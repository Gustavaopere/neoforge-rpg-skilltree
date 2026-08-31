package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class AtmosphereFieldLifecycleFailureTest {
    @Test
    void failedLifecycleUpsertRollsBackAndRetriesTheLastValidSource() {
        AtomicInteger upsertCalls = new AtomicInteger();
        AtmosphericSourceLifecycleSink sink = new AtmosphericSourceLifecycleSink() {
            @Override
            public void upsert(AtmosphericSource source) {
                if (upsertCalls.incrementAndGet() == 2) {
                    throw new IllegalStateException("simulated persistence failure");
                }
            }

            @Override
            public void remove(UUID id) {
            }
        };

        AtmosphereField field = field(sink);
        UUID sourceId = UUID.fromString("00000000-0000-0000-0000-000000000601");
        AtmosphericSource source = source(sourceId);

        field.register(source);
        assertEquals(1, upsertCalls.get());

        assertDoesNotThrow(() -> assertEquals(1, field.tick(1)));
        assertEquals(1.0, field.source(sourceId).orElseThrow().strength(), 1.0e-9,
                "a failed lifecycle write must leave the last persisted runtime state authoritative");

        assertDoesNotThrow(() -> assertEquals(1, field.tick(1)));
        assertEquals(0.50, field.source(sourceId).orElseThrow().strength(), 1.0e-9,
                "the same update must be retried after the sink recovers");
        assertEquals(3, upsertCalls.get());
    }

    @Test
    void failedInitialUpsertRollsBackRegistrationAndQueuedWork() {
        AtomicInteger upsertCalls = new AtomicInteger();
        AtmosphericSourceLifecycleSink sink = new AtmosphericSourceLifecycleSink() {
            @Override
            public void upsert(AtmosphericSource source) {
                if (upsertCalls.incrementAndGet() == 1) {
                    throw new IllegalStateException("simulated registration persistence failure");
                }
            }

            @Override
            public void remove(UUID id) {
            }
        };

        AtmosphereField field = field(sink);
        UUID sourceId = UUID.fromString("00000000-0000-0000-0000-000000000602");
        AtmosphericSource source = source(sourceId);

        assertThrows(IllegalStateException.class, () -> field.register(source));
        assertEquals(0, field.sourceCount());
        assertTrue(field.source(sourceId).isEmpty());
        assertEquals(0, field.tick(1), "failed registration must not leave orphaned queued work");

        assertDoesNotThrow(() -> field.register(source));
        assertEquals(1, field.sourceCount());
        assertEquals(source, field.source(sourceId).orElseThrow());
        assertEquals(2, upsertCalls.get());
    }

    @Test
    void failedLifecycleRemovalRestoresSourceAndQueuedWorkUntilRetrySucceeds() {
        AtomicInteger removeCalls = new AtomicInteger();
        AtmosphericSourceLifecycleSink sink = new AtmosphericSourceLifecycleSink() {
            @Override
            public void upsert(AtmosphericSource source) {
            }

            @Override
            public void remove(UUID id) {
                if (removeCalls.incrementAndGet() == 1) {
                    throw new IllegalStateException("simulated removal persistence failure");
                }
            }
        };

        AtmosphereField field = field(sink);
        UUID sourceId = UUID.fromString("00000000-0000-0000-0000-000000000603");
        AtmosphericSource source = source(sourceId);
        field.register(source);

        assertThrows(IllegalStateException.class, () -> field.remove(sourceId));
        assertEquals(1, field.sourceCount(),
                "failed persisted removal must keep the runtime source authoritative");
        assertEquals(source, field.source(sourceId).orElseThrow());
        assertEquals(1, field.tick(1),
                "failed removal must restore the source's queued atmospheric update work");
        assertEquals(0.50, field.source(sourceId).orElseThrow().strength(), 1.0e-9);

        assertDoesNotThrow(() -> assertTrue(field.remove(sourceId)));
        assertEquals(2, removeCalls.get());
        assertEquals(0, field.sourceCount());
        assertTrue(field.source(sourceId).isEmpty());
        assertEquals(0, field.tick(1), "successful retry must retire the queued source completely");
    }

    @Test
    void removingAlreadyAbsentSourceStillPurgesLifecycleSink() {
        AtomicInteger removeCalls = new AtomicInteger();
        AtmosphericSourceLifecycleSink sink = new AtmosphericSourceLifecycleSink() {
            @Override
            public void upsert(AtmosphericSource source) {
            }

            @Override
            public void remove(UUID id) {
                removeCalls.incrementAndGet();
            }
        };
        AtmosphereField field = field(sink);
        UUID sourceId = UUID.fromString("00000000-0000-0000-0000-000000000605");

        assertFalse(field.remove(sourceId), "return value should still describe runtime membership");
        assertEquals(1, removeCalls.get(),
                "idempotent upstream removal must purge any stale lifecycle/persistence state even when runtime is empty");
        assertEquals(0, field.tick(1), "absent removal must not create queued work");
    }

    @Test
    void failedLifecycleRemovalDuringExpiryKeepsSourceAndRetriesExpiry() {
        AtomicInteger removeCalls = new AtomicInteger();
        AtmosphericSourceLifecycleSink sink = new AtmosphericSourceLifecycleSink() {
            @Override
            public void upsert(AtmosphericSource source) {
            }

            @Override
            public void remove(UUID id) {
                if (removeCalls.incrementAndGet() == 1) {
                    throw new IllegalStateException("simulated expiry persistence failure");
                }
            }
        };

        AtmosphereField field = field(sink);
        UUID sourceId = UUID.fromString("00000000-0000-0000-0000-000000000604");
        AtmosphericSource expiring = new AtmosphericSource(
                sourceId,
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                16.0,
                VolcanicSourceProfiles.ash(2.0, 0.5).contribution(),
                0.015,
                true);
        field.register(expiring);

        assertDoesNotThrow(() -> assertEquals(1, field.tick(1)),
                "failed persisted expiry must not abort the bounded update batch");
        assertEquals(expiring, field.source(sourceId).orElseThrow(),
                "failed persisted expiry must keep the last accepted source authoritative");
        assertEquals(1, field.sourceCount());

        assertDoesNotThrow(() -> assertEquals(1, field.tick(1)),
                "expiry must be retried after the lifecycle sink recovers");
        assertEquals(2, removeCalls.get());
        assertEquals(0, field.sourceCount());
        assertTrue(field.source(sourceId).isEmpty());
        assertEquals(0, field.tick(1), "successful expiry retry must retire queued work");
    }

    private static AtmosphereField field(AtmosphericSourceLifecycleSink sink) {
        return new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                new AtmosphereDynamics(0.50, 2.0, 0.01),
                AtmosphereTransportProvider.stillAir(),
                sink);
    }

    private static AtmosphericSource source(UUID id) {
        return new AtmosphericSource(
                id,
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                16.0,
                VolcanicSourceProfiles.ash(2.0, 0.5).contribution(),
                1.0,
                true);
    }
}
