package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class AtmosphereFieldUpsertTest {
    @Test
    void upsertCreatesThenReplacesOneStableSourceWithoutDuplicatingQueuedWork() {
        AtmosphereField field = field(AtmosphericSourceLifecycleSink.none());
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000701");
        AtmosphericSource first = source(id, 1.0);
        AtmosphericSource updated = source(id, 0.8);

        field.upsert(first);
        field.upsert(updated);

        assertEquals(1, field.sourceCount());
        assertEquals(updated, field.source(id).orElseThrow());
        assertEquals(1, field.tick(1), "stable-source upsert must not duplicate queued work");
    }

    @Test
    void upsertRelocatesStableSourceWithoutLeavingOldSpatialContribution() {
        AtmosphereField field = field(AtmosphericSourceLifecycleSink.none());
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000703");
        AtmosphericSource original = sourceAt(id, 1.0, 0.0);
        AtmosphericSource moved = sourceAt(id, 1.0, 256.0);

        field.upsert(original);
        assertEquals(2.0, field.sample("minecraft:overworld", 0.0, 64.0, 0.0).particulatesMgM3(), 1.0e-9);

        field.upsert(moved);

        assertEquals(0.0, field.sample("minecraft:overworld", 0.0, 64.0, 0.0).particulatesMgM3(), 1.0e-9,
                "stable-source relocation must remove the old spatial membership");
        assertEquals(2.0, field.sample("minecraft:overworld", 256.0, 64.0, 0.0).particulatesMgM3(), 1.0e-9,
                "the same stable identity must contribute only from its new position");
        assertEquals(1, field.sourceCount());
    }

    @Test
    void failedReplacementUpsertRollsBackToPreviousAuthoritativeSource() {
        AtomicInteger calls = new AtomicInteger();
        AtmosphericSourceLifecycleSink sink = new AtmosphericSourceLifecycleSink() {
            @Override
            public void upsert(AtmosphericSource source) {
                if (calls.incrementAndGet() == 2) {
                    throw new IllegalStateException("simulated lifecycle rejection");
                }
            }

            @Override
            public void remove(UUID id) {
            }
        };
        AtmosphereField field = field(sink);
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000702");
        AtmosphericSource first = source(id, 1.0);
        AtmosphericSource rejected = source(id, 0.7);

        field.upsert(first);
        assertThrows(IllegalStateException.class, () -> field.upsert(rejected));

        assertEquals(1, field.sourceCount());
        assertEquals(first, field.source(id).orElseThrow());
        assertEquals(1, field.tick(1), "failed replacement must preserve the existing update slot");
    }

    private static AtmosphereField field(AtmosphericSourceLifecycleSink sink) {
        return new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64),
                new AtmosphereDynamics(0.50, 2.0, 0.01),
                AtmosphereTransportProvider.stillAir(),
                sink);
    }

    private static AtmosphericSource source(UUID id, double strength) {
        return sourceAt(id, strength, 0.0);
    }

    private static AtmosphericSource sourceAt(UUID id, double strength, double x) {
        return new AtmosphericSource(
                id,
                "minecraft:overworld",
                x,
                64.0,
                0.0,
                16.0,
                VolcanicSourceProfiles.ash(2.0, 0.5).contribution(),
                strength,
                true);
    }
}
