package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicHeatSourceIndexTest {
    @Test
    void boundedIndexRejectsOverflowWithoutEvictingAcceptedSources() {
        VolcanicHeatSourceIndex index = new VolcanicHeatSourceIndex(32, 64.0, 128.0, 2);
        VolcanicHeatSource first = source(1, VolcanicHeatSource.Kind.GEOTHERMAL, new BlockPos(0, 64, 0), 12.0, 0.6, Long.MAX_VALUE);
        VolcanicHeatSource second = source(2, VolcanicHeatSource.Kind.LAVA, new BlockPos(80, 64, 0), 8.0, 1.0, Long.MAX_VALUE);
        VolcanicHeatSource overflow = source(3, VolcanicHeatSource.Kind.PYROCLASTIC, new BlockPos(160, 64, 0), 24.0, 0.9, 200L);

        assertTrue(index.upsert(first));
        assertTrue(index.upsert(second));
        assertFalse(index.upsert(overflow));
        assertEquals(2, index.size());
        assertEquals(List.of(first), index.nearby(new BlockPos(0, 64, 0), 1.0, 8, 0L));
        assertEquals(List.of(second), index.nearby(new BlockPos(80, 64, 0), 1.0, 8, 0L));
    }

    @Test
    void sourceRadiusParticipatesInSpatialMembershipAndQueryDistance() {
        VolcanicHeatSourceIndex index = new VolcanicHeatSourceIndex(16, 64.0, 64.0, 16);
        VolcanicHeatSource source = source(4, VolcanicHeatSource.Kind.GEOTHERMAL, new BlockPos(48, 64, 0), 32.0, 0.75, Long.MAX_VALUE);
        assertTrue(index.upsert(source));

        assertEquals(List.of(source), index.nearby(new BlockPos(8, 64, 0), 8.0, 8, 0L));
        assertTrue(index.nearby(new BlockPos(-32, 64, 0), 8.0, 8, 0L).isEmpty());
    }

    @Test
    void updatingMovesMembershipAndExplicitRemovalClearsSource() {
        VolcanicHeatSourceIndex index = new VolcanicHeatSourceIndex(16, 64.0, 64.0, 16);
        UUID id = id(5);
        VolcanicHeatSource first = new VolcanicHeatSource(id, VolcanicHeatSource.Kind.PYROCLASTIC, new BlockPos(0, 70, 0), 10.0, 0.5, 100L);
        VolcanicHeatSource moved = new VolcanicHeatSource(id, VolcanicHeatSource.Kind.PYROCLASTIC, new BlockPos(96, 70, 0), 10.0, 0.8, 120L);

        assertTrue(index.upsert(first));
        assertTrue(index.upsert(moved));
        assertTrue(index.nearby(BlockPos.ZERO, 16.0, 8, 0L).isEmpty());
        assertEquals(List.of(moved), index.nearby(new BlockPos(96, 70, 0), 16.0, 8, 0L));
        assertTrue(index.remove(id));
        assertFalse(index.remove(id));
        assertEquals(0, index.size());
        assertEquals(0, index.scheduledExpiryCount());
    }

    @Test
    void expiryRemovalIsBoundedAndPermanentSourcesRemain() {
        VolcanicHeatSourceIndex index = new VolcanicHeatSourceIndex(16, 64.0, 64.0, 16);
        VolcanicHeatSource expiredOne = source(6, VolcanicHeatSource.Kind.PYROCLASTIC, BlockPos.ZERO, 8.0, 0.7, 10L);
        VolcanicHeatSource expiredTwo = source(7, VolcanicHeatSource.Kind.LAVA, new BlockPos(16, 64, 0), 8.0, 0.9, 10L);
        VolcanicHeatSource permanent = source(8, VolcanicHeatSource.Kind.GEOTHERMAL, new BlockPos(32, 64, 0), 8.0, 0.5, Long.MAX_VALUE);
        index.upsert(expiredOne);
        index.upsert(expiredTwo);
        index.upsert(permanent);

        assertEquals(2, index.scheduledExpiryCount(),
                "permanent sources must not participate in per-tick expiry scheduling");
        assertEquals(1, index.expire(10L, 1));
        assertEquals(2, index.size());
        assertEquals(1, index.scheduledExpiryCount());
        assertEquals(1, index.expire(10L, 1));
        assertEquals(1, index.size());
        assertEquals(0, index.scheduledExpiryCount());
        assertEquals(List.of(permanent), index.nearby(new BlockPos(32, 64, 0), 32.0, 8, 10L));
    }

    @Test
    void expiryScheduleTracksReplacementDeadlineWithoutStaleEntries() {
        VolcanicHeatSourceIndex index = new VolcanicHeatSourceIndex(16, 64.0, 64.0, 16);
        UUID sourceId = id(11);
        index.upsert(new VolcanicHeatSource(
                sourceId, VolcanicHeatSource.Kind.PYROCLASTIC, BlockPos.ZERO, 8.0, 0.7, 10L));
        index.upsert(new VolcanicHeatSource(
                sourceId, VolcanicHeatSource.Kind.PYROCLASTIC, BlockPos.ZERO, 8.0, 0.8, 50L));

        assertEquals(1, index.scheduledExpiryCount());
        assertEquals(0, index.expire(10L, 1));
        assertEquals(1, index.size());
        assertEquals(1, index.expire(50L, 1));
        assertEquals(0, index.size());
        assertEquals(0, index.scheduledExpiryCount());
    }

    @Test
    void queriesAreDeterministicAndRespectResultBudget() {
        VolcanicHeatSourceIndex index = new VolcanicHeatSourceIndex(16, 64.0, 64.0, 16);
        VolcanicHeatSource near = source(9, VolcanicHeatSource.Kind.LAVA, new BlockPos(4, 64, 0), 4.0, 0.9, Long.MAX_VALUE);
        VolcanicHeatSource far = source(10, VolcanicHeatSource.Kind.GEOTHERMAL, new BlockPos(20, 64, 0), 4.0, 0.5, Long.MAX_VALUE);
        index.upsert(far);
        index.upsert(near);

        assertEquals(List.of(near), index.nearby(BlockPos.ZERO, 32.0, 1, 0L));
        assertEquals(List.of(near, far), index.nearby(BlockPos.ZERO, 32.0, 2, 0L));
    }

    private static VolcanicHeatSource source(int value, VolcanicHeatSource.Kind kind, BlockPos center, double radius, double severity, long expiresAt) {
        return new VolcanicHeatSource(id(value), kind, center, radius, severity, expiresAt);
    }

    private static UUID id(int value) {
        return new UUID(0L, value);
    }
}
