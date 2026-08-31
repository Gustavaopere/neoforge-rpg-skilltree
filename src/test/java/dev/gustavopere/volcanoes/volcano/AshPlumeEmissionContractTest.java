package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class AshPlumeEmissionContractTest {
    private static final UUID VOLCANO_ID = UUID.fromString("46f7c6d5-6a72-47c2-9020-5cc11a3395ce");
    private static final BlockPos SOURCE = new BlockPos(320, 96, -144);
    private static final EruptionProfile PROFILE = new EruptionProfile(
            0.80,
            80,
            400,
            600L,
            400L,
            2_400L,
            800L);
    private static final MagmaChamber CHAMBER = new MagmaChamber(
            new MagmaComposition(0.68, 0.72),
            10.0,
            340.0,
            0.20,
            1_230.0,
            0.35);

    @Test
    void sustainedEruptionCreatesStableAuthoritativeAshSource() {
        AshPlumeEmission emission = AshPlumeEmission.from(signal(EruptionPhase.SUSTAINED, 0.50, 0.80));

        assertEquals(AshPlumeEmission.sourceIdFor(VOLCANO_ID), emission.sourceId());
        assertEquals(VOLCANO_ID, emission.volcanoId());
        assertFalse(emission.sourceId().equals(VOLCANO_ID));
        assertEquals(SOURCE, emission.source());
        assertEquals(EruptionPhase.SUSTAINED, emission.phase());
        assertTrue(emission.active());
        assertEquals(0.80, emission.normalizedLoad(), 1.0e-9);
        assertEquals(400.0, emission.radiusBlocks(), 1.0e-9);
    }

    @Test
    void openingAndWaningScalePlumeRadiusFromRelativeEruptionIntensity() {
        AshPlumeEmission opening = AshPlumeEmission.from(signal(EruptionPhase.OPENING, 0.50, 0.40));
        AshPlumeEmission waning = AshPlumeEmission.from(signal(EruptionPhase.WANING, 0.50, 0.20));

        assertTrue(opening.active());
        assertEquals(0.40, opening.normalizedLoad(), 1.0e-9);
        assertEquals(240.0, opening.radiusBlocks(), 1.0e-9);

        assertTrue(waning.active());
        assertEquals(0.20, waning.normalizedLoad(), 1.0e-9);
        assertEquals(160.0, waning.radiusBlocks(), 1.0e-9);
    }

    @Test
    void plumeRadiusUsesIntensityRelativeToProfilePeakRatherThanAbsoluteLoad() {
        EruptionProfile lowerPeak = new EruptionProfile(
                0.50,
                100,
                500,
                600L,
                400L,
                2_400L,
                800L);
        EruptionSignal halfPeak = new EruptionSignal(
                VOLCANO_ID,
                SOURCE,
                EruptionPhase.SUSTAINED,
                lowerPeak,
                CHAMBER,
                0.50,
                0.25);

        AshPlumeEmission emission = AshPlumeEmission.from(halfPeak);

        assertEquals(0.25, emission.normalizedLoad(), 1.0e-9);
        assertEquals(300.0, emission.radiusBlocks(), 1.0e-9);
    }

    @Test
    void precursorAndDormantSignalsKeepSourceIdentityButDoNotEmitAsh() {
        AshPlumeEmission precursor = AshPlumeEmission.from(signal(EruptionPhase.PRECURSORS, 0.25, 0.20));
        AshPlumeEmission dormant = AshPlumeEmission.from(signal(EruptionPhase.DORMANT, 1.0, 0.0));

        assertEquals(AshPlumeEmission.sourceIdFor(VOLCANO_ID), precursor.sourceId());
        assertEquals(precursor.sourceId(), dormant.sourceId());
        assertFalse(precursor.active());
        assertFalse(dormant.active());
        assertEquals(0.0, precursor.normalizedLoad(), 1.0e-9);
        assertEquals(0.0, dormant.normalizedLoad(), 1.0e-9);
        assertEquals(0.0, precursor.radiusBlocks(), 1.0e-9);
        assertEquals(0.0, dormant.radiusBlocks(), 1.0e-9);
    }

    private static EruptionSignal signal(EruptionPhase phase, double phaseProgress, double intensity) {
        return new EruptionSignal(
                VOLCANO_ID,
                SOURCE,
                phase,
                PROFILE,
                CHAMBER,
                phaseProgress,
                intensity);
    }
}
