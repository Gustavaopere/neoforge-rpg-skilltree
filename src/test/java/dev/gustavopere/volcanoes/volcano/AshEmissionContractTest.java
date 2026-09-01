package dev.gustavopere.volcanoes.volcano;

import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class AshEmissionContractTest {
    private static final UUID VOLCANO_ID = UUID.fromString("06ca4ea1-6a57-4d71-a73f-7170cb55b230");
    private static final BlockPos VENT = new BlockPos(128, 96, -256);
    private static final EruptionScheduler.WorkGrant NO_WORLD_WORK =
            new EruptionScheduler.WorkGrant(0, 0, 0, 0, 0, 0);

    @Test
    void oneVolcanoKeepsOneStableAtmosphericAshSourceAcrossActivePhasesAndRemovesItAtDormancy() {
        RecordingSink sink = new RecordingSink();
        AshEmissionRuntime runtime = new AshEmissionRuntime(sink);

        runtime.onEruption(signal(EruptionPhase.OPENING, chamber(0.18, 0.58, 0.48), 0.42), NO_WORLD_WORK);
        runtime.onEruption(signal(EruptionPhase.SUSTAINED, chamber(0.18, 0.58, 0.48), 0.76), NO_WORLD_WORK);

        assertEquals(2, sink.upserts.size());
        AshPlumeEmission opening = sink.upserts.get(0);
        AshPlumeEmission sustained = sink.upserts.get(1);
        assertEquals(AshPlumeEmission.sourceIdFor(VOLCANO_ID), opening.sourceId());
        assertEquals(opening.sourceId(), sustained.sourceId());
        assertEquals(VOLCANO_ID, sustained.volcanoId());
        assertEquals(VENT, sustained.source());
        assertTrue(sustained.particulateStrength() > opening.particulateStrength());
        assertTrue(sustained.smokeStrength() > opening.smokeStrength());
        assertTrue(sustained.plumeRadiusBlocks() >= opening.plumeRadiusBlocks());
        assertTrue(sustained.lifetimeTicks() >= opening.lifetimeTicks());
        assertTrue(sink.removed.isEmpty());

        runtime.onEruption(signal(EruptionPhase.DORMANT, chamber(0.18, 0.58, 0.48), 0.0), NO_WORLD_WORK);

        assertEquals(List.of(opening.sourceId()), sink.removed);
        assertEquals(2, sink.upserts.size(), "dormancy retires the source instead of publishing a zero-strength duplicate");
    }

    @Test
    void ashStrengthUsesCanonicalEruptionAndMagmaChemistryWithoutAtmosphereImplementationTypes() {
        RecordingSink sink = new RecordingSink();
        AshEmissionRuntime runtime = new AshEmissionRuntime(sink);

        runtime.onEruption(signal(EruptionPhase.SUSTAINED, chamber(0.06, 0.47, 0.22), 0.70), NO_WORLD_WORK);
        runtime.onEruption(signal(EruptionPhase.SUSTAINED, chamber(0.28, 0.72, 0.80), 0.70), NO_WORLD_WORK);

        AshPlumeEmission lean = sink.upserts.get(0);
        AshPlumeEmission explosive = sink.upserts.get(1);
        assertTrue(explosive.particulateStrength() > lean.particulateStrength());
        assertTrue(explosive.smokeStrength() > lean.smokeStrength());
        assertTrue(explosive.particulateStrength() <= 1.0);
        assertTrue(explosive.smokeStrength() <= 1.0);
        assertTrue(explosive.plumeRadiusBlocks() <= signalProfile().outerRadiusBlocks());
        assertFalse(explosive.sourceId().equals(VOLCANO_ID), "ash needs a namespaced identity distinct from future gas sources");
    }

    private static EruptionSignal signal(EruptionPhase phase, MagmaChamber chamber, double intensity) {
        EruptionProfile profile = signalProfile();
        return new EruptionSignal(
                VOLCANO_ID,
                VENT,
                phase,
                profile,
                chamber,
                phase == EruptionPhase.DORMANT ? 1.0 : 0.5,
                intensity);
    }

    private static EruptionProfile signalProfile() {
        return new EruptionProfile(0.90, 96, 640, 800L, 300L, 3_600L, 1_200L);
    }

    private static MagmaChamber chamber(double gasFraction, double silicaFraction, double volatileRichness) {
        return new MagmaChamber(
                new MagmaComposition(silicaFraction, volatileRichness),
                8.0,
                320.0,
                gasFraction,
                1_220.0,
                0.25);
    }

    private static final class RecordingSink implements AshEmissionLifecycleSink {
        private final List<AshPlumeEmission> upserts = new ArrayList<>();
        private final List<UUID> removed = new ArrayList<>();

        @Override
        public void upsert(AshPlumeEmission emission) {
            upserts.add(emission);
        }

        @Override
        public void remove(UUID sourceId) {
            removed.add(sourceId);
        }
    }
}
