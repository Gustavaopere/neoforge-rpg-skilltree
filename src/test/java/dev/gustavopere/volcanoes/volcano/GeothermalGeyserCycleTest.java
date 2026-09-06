package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalGeyserCycleTest {
    @Test
    void geyserPulseScheduleIsStableStaggeredAndFiniteFrequency() {
        GeothermalSource geyser = source(
                UUID.fromString("12345678-1234-5678-9abc-def012345678"),
                GeothermalFeatureType.GEYSER);
        GeothermalGeyserCycle cycle = GeothermalGeyserCycle.forSource(geyser);

        assertTrue(cycle.periodTicks() >= 600L && cycle.periodTicks() <= 1_200L,
                "geyser period must remain within the bounded native gameplay cadence");
        assertTrue(cycle.phaseTicks() >= 0L && cycle.phaseTicks() < cycle.periodTicks());
        assertTrue(cycle.pulsesAt(cycle.phaseTicks()));
        assertTrue(cycle.pulsesAt(cycle.phaseTicks() + cycle.periodTicks()));
        assertFalse(cycle.pulsesAt(cycle.phaseTicks() + 1L));
        assertTrue(cycle.equals(GeothermalGeyserCycle.forSource(geyser)),
                "same persistent source must always recover the same pulse schedule");
    }

    @Test
    void boundedDetectionWindowCanRecoverOneMissedPulseWithoutCrossingIntoPreviousCycle() {
        GeothermalSource geyser = source(
                UUID.fromString("12345678-1234-5678-9abc-def012345678"),
                GeothermalFeatureType.GEYSER);
        GeothermalGeyserCycle cycle = GeothermalGeyserCycle.forSource(geyser);
        long pulse = cycle.phaseTicks() + cycle.periodTicks();

        assertEquals(pulse, cycle.latestPulseTickAtOrBefore(pulse));
        assertEquals(pulse, cycle.latestPulseTickAtOrBefore(pulse + 4L));
        assertTrue(cycle.pulsesWithin(pulse + 4L, 5L));
        assertFalse(cycle.pulsesWithin(pulse + 5L, 5L));
        assertFalse(cycle.pulsesWithin(Math.max(0L, cycle.phaseTicks() - 1L), 5L));
    }

    @Test
    void nonGeyserSourceCannotProduceGeyserCycle() {
        GeothermalSource spring = source(
                UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee"),
                GeothermalFeatureType.HOT_SPRING);
        boolean rejected = false;
        try {
            GeothermalGeyserCycle.forSource(spring);
        } catch (IllegalArgumentException expected) {
            rejected = true;
        }
        assertTrue(rejected, "only GEYSER sources may create native geyser cycles");
    }

    private static GeothermalSource source(UUID id, GeothermalFeatureType type) {
        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(type);
        return new GeothermalSource(
                id,
                type,
                new BlockPos(8, 80, 8),
                profile.radiusBlocks(),
                profile.heatSeverity(),
                profile.gasSeverity());
    }
}
