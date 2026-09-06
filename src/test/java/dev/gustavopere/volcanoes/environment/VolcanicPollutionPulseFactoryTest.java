package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.AshPlumeEmission;
import dev.gustavopere.volcanoes.volcano.EruptionPhase;
import dev.gustavopere.volcanoes.volcano.VolcanicGasEmission;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final class VolcanicPollutionPulseFactoryTest {
    private static final String DIMENSION = "minecraft:overworld";
    private static final UUID SOURCE_ID = UUID.fromString("00000000-0000-0000-0000-000000000301");
    private static final UUID VOLCANO_ID = UUID.fromString("00000000-0000-0000-0000-000000000302");

    @Test
    void retryWithinSameIntervalReusesPulseIdButNextIntervalIsFresh() {
        VolcanicGasEmission source = gas(0.75);

        PollutionEmission first = VolcanicPollutionPulseFactory.gasPulse(DIMENSION, source, 40L, 20).orElseThrow();
        PollutionEmission retry = VolcanicPollutionPulseFactory.gasPulse(DIMENSION, source, 59L, 20).orElseThrow();
        PollutionEmission next = VolcanicPollutionPulseFactory.gasPulse(DIMENSION, source, 60L, 20).orElseThrow();

        assertEquals(first.id(), retry.id());
        assertNotEquals(first.id(), next.id());
    }

    @Test
    void gasFeedsAcidRainAndGreenhouseWithoutInventingOzoneOrSmog() {
        PollutionLoad load = VolcanicPollutionPulseFactory.gasPulse(DIMENSION, gas(0.65), 20L, 20)
                .orElseThrow()
                .load();

        assertEquals(0.65, load.acidifyingLoad());
        assertEquals(0.0, load.particulateLoad());
        assertEquals(0.0, load.smogLoad());
        assertEquals(0.65, load.greenhouseLoad());
        assertEquals(0.0, load.ozoneAffectingLoad());
    }

    @Test
    void ashFeedsParticulateAndDestroySmogChannelsOnly() {
        AshPlumeEmission ash = new AshPlumeEmission(
                SOURCE_ID,
                VOLCANO_ID,
                new BlockPos(10, 80, -4),
                EruptionPhase.SUSTAINED,
                0.8,
                0.7,
                0.45,
                48.0,
                100L);

        PollutionLoad load = VolcanicPollutionPulseFactory.ashPulse(DIMENSION, ash, 20L, 20)
                .orElseThrow()
                .load();

        assertEquals(0.0, load.acidifyingLoad());
        assertEquals(0.7, load.particulateLoad());
        assertEquals(0.45, load.smogLoad());
        assertEquals(0.0, load.greenhouseLoad());
        assertEquals(0.0, load.ozoneAffectingLoad());
    }

    @Test
    void inactiveSourcesDoNotProducePulses() {
        assertTrue(VolcanicPollutionPulseFactory.gasPulse(DIMENSION, gas(0.0), 20L, 20).isEmpty());

        AshPlumeEmission ash = new AshPlumeEmission(
                SOURCE_ID,
                VOLCANO_ID,
                BlockPos.ZERO,
                EruptionPhase.WANING,
                0.0,
                0.0,
                0.0,
                0.0,
                0L);
        assertTrue(VolcanicPollutionPulseFactory.ashPulse(DIMENSION, ash, 20L, 20).isEmpty());
    }

    private static VolcanicGasEmission gas(double strength) {
        return new VolcanicGasEmission(
                SOURCE_ID,
                VOLCANO_ID,
                new BlockPos(10, 80, -4),
                EruptionPhase.SUSTAINED,
                strength,
                48.0,
                200L);
    }
}
