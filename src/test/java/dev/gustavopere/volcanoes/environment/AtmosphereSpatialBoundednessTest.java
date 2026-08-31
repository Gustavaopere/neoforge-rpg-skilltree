package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Regression coverage for bounded source diffusion and atomic spatial indexing. */
final class AtmosphereSpatialBoundednessTest {
    private static final AtmosphereContribution ASH = new AtmosphereContribution(
            0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0);

    @Test
    void diffusionRadiusCannotGrowBeyondConfiguredMaximumEvenWithPerfectRetention() {
        AtmosphereDynamics dynamics = new AtmosphereDynamics(1.0, 32.0, 0.0, 96.0);
        AtmosphericSource source = source(
                UUID.fromString("00000000-0000-0000-0000-000000000801"),
                80.0);

        AtmosphericSource first = source.evolve(dynamics).orElseThrow();
        AtmosphericSource second = first.evolve(dynamics).orElseThrow();
        AtmosphericSource third = second.evolve(dynamics).orElseThrow();

        assertEquals(96.0, first.radiusBlocks(), 1.0e-12);
        assertEquals(96.0, second.radiusBlocks(), 1.0e-12);
        assertEquals(96.0, third.radiusBlocks(), 1.0e-12);
        assertEquals(96.0, dynamics.maximumRadiusBlocks(), 1.0e-12);
    }

    @Test
    void oversizedSourceIsRejectedBeforeAnyIndexMutation() {
        AtmosphericSourceIndex index = new AtmosphericSourceIndex(64, 16);
        AtmosphericSource oversized = source(
                UUID.fromString("00000000-0000-0000-0000-000000000802"),
                512.0);

        assertThrows(IllegalArgumentException.class, () -> index.register(oversized));
        assertEquals(0, index.size());
        assertEquals(0, index.candidatesAt("minecraft:overworld", 0.0, 0.0).size());
    }

    @Test
    void invalidReplacementLeavesPreviouslyIndexedSourceIntact() {
        AtmosphericSourceIndex index = new AtmosphericSourceIndex(64, 16);
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000803");
        AtmosphericSource original = source(id, 20.0);
        index.register(original);

        AtmosphericSource oversized = source(id, 512.0);
        assertThrows(IllegalArgumentException.class, () -> index.replace(oversized));

        assertEquals(original, index.source(id).orElseThrow());
        assertEquals(1, index.size());
        assertEquals(1, index.candidatesAt("minecraft:overworld", 0.0, 0.0).size());
    }

    @Test
    void activeSourceLimitBoundsTheUpdateQueuePopulationWhileAllowingReplacement() {
        AtmosphericSourceIndex index = new AtmosphericSourceIndex(64, 16, 2);
        UUID firstId = UUID.fromString("00000000-0000-0000-0000-000000000804");
        AtmosphericSource first = source(firstId, 20.0);
        AtmosphericSource second = source(
                UUID.fromString("00000000-0000-0000-0000-000000000805"), 20.0);
        AtmosphericSource third = source(
                UUID.fromString("00000000-0000-0000-0000-000000000806"), 20.0);
        index.register(first);
        index.register(second);

        assertThrows(IllegalStateException.class, () -> index.register(third));
        assertEquals(2, index.size());

        AtmosphericSource replacement = source(firstId, 30.0);
        index.replace(replacement);
        assertEquals(replacement, index.source(firstId).orElseThrow());
        assertEquals(2, index.size());
    }

    @Test
    void invalidPersistedSourceIsPurgedInsteadOfCrashingRestore() {
        List<UUID> removals = new ArrayList<>();
        AtmosphericSourceLifecycleSink sink = new AtmosphericSourceLifecycleSink() {
            @Override
            public void upsert(AtmosphericSource source) {
            }

            @Override
            public void remove(UUID id) {
                removals.add(id);
            }
        };
        AtmosphereField field = new AtmosphereField(
                AtmosphereBaselineProvider.standard(),
                new AtmosphericSourceIndex(64, 4, 8),
                AtmosphereDynamics.defaults(),
                AtmosphereTransportProvider.stillAir(),
                sink);
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000807");

        field.restore(source(id, 512.0));

        assertEquals(0, field.sourceCount());
        assertEquals(List.of(id), removals);
    }

    private static AtmosphericSource source(UUID id, double radiusBlocks) {
        return new AtmosphericSource(
                id,
                "minecraft:overworld",
                0.0,
                64.0,
                0.0,
                radiusBlocks,
                ASH,
                1.0,
                false);
    }
}
