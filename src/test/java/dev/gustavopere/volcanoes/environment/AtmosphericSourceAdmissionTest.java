package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class AtmosphericSourceAdmissionTest {
    @Test
    void capacityRejectionIsExplicitRetryableAndDoesNotTouchLifecycleSink() {
        AtomicInteger lifecycleUpserts = new AtomicInteger();
        AtmosphericSourceLifecycleSink sink = new AtmosphericSourceLifecycleSink() {
            @Override
            public void upsert(AtmosphericSource source) {
                lifecycleUpserts.incrementAndGet();
            }

            @Override
            public void remove(UUID id) {
            }
        };
        AtmosphereField field = field(1, sink);
        AtmosphericSource dynamic = dynamic("00000000-0000-0000-0000-000000000801", 1.0);
        AtmosphericSource external = external("00000000-0000-0000-0000-000000000802", 1.0);

        field.upsert(dynamic);
        assertEquals(1, lifecycleUpserts.get());

        assertEquals(AtmosphericSourceAdmission.REJECTED_CAPACITY, field.tryUpsert(external));
        assertEquals(1, lifecycleUpserts.get(), "rejected admission must not publish lifecycle state");
        assertEquals(1, field.sourceCount());
        assertTrue(field.source(external.id()).isEmpty());

        field.remove(dynamic.id());
        assertEquals(AtmosphericSourceAdmission.ACCEPTED, field.tryUpsert(external));
        assertEquals(2, lifecycleUpserts.get());
        assertEquals(1, field.sourceCount());
        assertEquals(external, field.source(external.id()).orElseThrow());
    }

    @Test
    void replacementOfExistingExternalSourceSucceedsAtFullCapacity() {
        AtmosphereField field = field(1);
        AtmosphericSource original = external("00000000-0000-0000-0000-000000000803", 1.0);
        AtmosphericSource replacement = external("00000000-0000-0000-0000-000000000803", 0.5);

        assertEquals(AtmosphericSourceAdmission.ACCEPTED, field.tryUpsert(original));
        assertEquals(AtmosphericSourceAdmission.ACCEPTED, field.tryUpsert(replacement));

        assertEquals(1, field.sourceCount());
        assertEquals(replacement, field.source(original.id()).orElseThrow());
    }

    @Test
    void replacementOfExistingDynamicSourceSucceedsAtFullCapacity() {
        AtmosphereField field = field(1);
        AtmosphericSource original = dynamic("00000000-0000-0000-0000-000000000806", 1.0);
        AtmosphericSource replacement = dynamic("00000000-0000-0000-0000-000000000806", 0.5);

        assertEquals(AtmosphericSourceAdmission.ACCEPTED, field.tryUpsert(original));
        assertEquals(AtmosphericSourceAdmission.ACCEPTED, field.tryUpsert(replacement));

        assertEquals(1, field.sourceCount());
        assertEquals(replacement, field.source(original.id()).orElseThrow());
        assertEquals(1, field.tick(1), "replacement must retain exactly one dynamic update slot");
    }

    @Test
    void invalidSpatialFootprintIsStillRejectedAsInvalidWhenCapacityIsFull() {
        AtmosphereField field = field(1);
        AtmosphericSource first = dynamic("00000000-0000-0000-0000-000000000807", 1.0);
        AtmosphericSource invalid = new AtmosphericSource(
                UUID.fromString("00000000-0000-0000-0000-000000000808"),
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                10_000.0,
                VolcanicSourceProfiles.ash(2.0, 0.5).contribution(),
                1.0,
                false,
                AtmosphericSourceEvolution.EXTERNAL);

        field.upsert(first);

        assertThrows(IllegalArgumentException.class, () -> field.tryUpsert(invalid),
                "invalid source footprints must not be mislabeled as ordinary capacity backpressure");
        assertEquals(1, field.sourceCount());
        assertTrue(field.source(invalid.id()).isEmpty());
    }

    @Test
    void legacyVoidUpsertRemainsFailClosedWhenCapacityIsExhausted() {
        AtmosphereField field = field(1);
        AtmosphericSource first = dynamic("00000000-0000-0000-0000-000000000804", 1.0);
        AtmosphericSource second = external("00000000-0000-0000-0000-000000000805", 1.0);

        field.upsert(first);

        assertThrows(IllegalStateException.class, () -> field.upsert(second));
        assertEquals(1, field.sourceCount());
        assertTrue(field.source(second.id()).isEmpty());
    }

    private static AtmosphereField field(int maxSources) {
        return field(maxSources, AtmosphericSourceLifecycleSink.none());
    }

    private static AtmosphereField field(int maxSources, AtmosphericSourceLifecycleSink sink) {
        return new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64, 4_096, maxSources),
                AtmosphereDynamics.defaults(),
                AtmosphereTransportProvider.stillAir(),
                sink);
    }

    private static AtmosphericSource dynamic(String id, double strength) {
        return source(id, strength, AtmosphericSourceEvolution.DYNAMIC, true);
    }

    private static AtmosphericSource external(String id, double strength) {
        return source(id, strength, AtmosphericSourceEvolution.EXTERNAL, false);
    }

    private static AtmosphericSource source(
            String id,
            double strength,
            AtmosphericSourceEvolution evolution,
            boolean persistent
    ) {
        return new AtmosphericSource(
                UUID.fromString(id),
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                16.0,
                VolcanicSourceProfiles.ash(2.0, 0.5).contribution(),
                strength,
                persistent,
                evolution);
    }
}
