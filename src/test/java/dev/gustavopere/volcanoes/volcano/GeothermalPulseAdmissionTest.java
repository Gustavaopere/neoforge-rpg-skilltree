package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalPulseAdmissionTest {
    @Test
    void runtimeActivationPreventsReplayOfPulseFromBeforeRestart() {
        GeothermalSource source = source();
        GeothermalGeyserCycle cycle = GeothermalGeyserCycle.forSource(source);
        long pulseTick = cycle.phaseTicks();
        Map<UUID, Long> history = new HashMap<>();

        assertFalse(GeothermalPulseAdmission.isPending(source, pulseTick + 1L, pulseTick + 1L, history),
                "a pulse scheduled before runtime activation must not replay after restart");
        assertTrue(GeothermalPulseAdmission.isPending(source, pulseTick, pulseTick, history),
                "a pulse scheduled exactly at runtime activation remains eligible");

        history.put(source.persistenceId(), pulseTick);
        assertFalse(GeothermalPulseAdmission.isPending(source, pulseTick, pulseTick, history),
                "already emitted pulse must remain deduplicated");
        assertTrue(GeothermalPulseAdmission.isPending(
                        source,
                        pulseTick + cycle.periodTicks(),
                        pulseTick,
                        history),
                "the next scheduled cycle must become eligible normally");
    }

    private static GeothermalSource source() {
        GeothermalFeatureProfile profile = GeothermalFeatureProfile.defaults(GeothermalFeatureType.GEYSER);
        return new GeothermalSource(
                UUID.fromString("1f4ca861-2861-4a40-b928-09870a8b78ca"),
                GeothermalFeatureType.GEYSER,
                new BlockPos(8, 80, 8),
                profile.radiusBlocks(),
                profile.heatSeverity(),
                profile.gasSeverity());
    }
}
