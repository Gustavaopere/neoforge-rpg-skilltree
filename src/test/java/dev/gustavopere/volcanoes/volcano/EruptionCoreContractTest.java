package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EruptionCoreContractTest {
    private static final UUID VOLCANO_ID = UUID.fromString("318e94e8-1c7b-4b27-8774-bcf45f2ea680");

    @Test
    void eruptionProfileIsDeterministicAndDrivenByChamberPhysics() {
        MagmaChamber mild = chamber(
                new MagmaComposition(0.48, 0.20),
                280.0,
                0.13,
                1_180.0);
        MagmaChamber explosive = chamber(
                new MagmaComposition(0.72, 0.80),
                360.0,
                0.28,
                1_250.0);

        EruptionProfile mildProfile = EruptionProfile.fromChamber(mild);
        EruptionProfile explosiveProfile = EruptionProfile.fromChamber(explosive);

        assertEquals(mildProfile, EruptionProfile.fromChamber(mild));
        assertTrue(mildProfile.peakIntensity() >= 0.0 && mildProfile.peakIntensity() <= 1.0);
        assertTrue(explosiveProfile.peakIntensity() > mildProfile.peakIntensity());
        assertTrue(explosiveProfile.outerRadiusBlocks() > mildProfile.outerRadiusBlocks());
        assertTrue(mildProfile.outerRadiusBlocks() > mildProfile.innerRadiusBlocks());
        assertTrue(mildProfile.durationTicks(EruptionPhase.PRECURSORS) > 0L);
        assertTrue(mildProfile.durationTicks(EruptionPhase.OPENING) > 0L);
        assertTrue(mildProfile.durationTicks(EruptionPhase.SUSTAINED) > 0L);
        assertTrue(mildProfile.durationTicks(EruptionPhase.WANING) > 0L);
        assertEquals(0L, mildProfile.durationTicks(EruptionPhase.DORMANT));
    }

    @Test
    void controllerAdvancesThroughCanonicalPhasesAndCompletesDormant() {
        EruptionController controller = new EruptionController();
        EruptionEvent event = controller.begin(VOLCANO_ID, chamber(
                MagmaComposition.forType(VolcanoType.STRATOVOLCANO),
                320.0,
                0.20,
                1_230.0), 12_000L);

        assertEquals(EruptionPhase.PRECURSORS, event.phase());
        assertEquals(0L, event.elapsedTicks());

        event = controller.advance(event, event.profile().durationTicks(EruptionPhase.PRECURSORS));
        assertEquals(EruptionPhase.OPENING, event.phase());

        event = controller.advance(event, event.profile().durationTicks(EruptionPhase.OPENING));
        assertEquals(EruptionPhase.SUSTAINED, event.phase());

        event = controller.advance(event, event.profile().durationTicks(EruptionPhase.SUSTAINED));
        assertEquals(EruptionPhase.WANING, event.phase());

        event = controller.advance(event, event.profile().durationTicks(EruptionPhase.WANING));
        assertEquals(EruptionPhase.DORMANT, event.phase());
        assertTrue(event.isComplete());
    }

    @Test
    void midEruptionRoundTripPreservesIdentityProfilePhaseAndProgress() {
        EruptionController controller = new EruptionController();
        MagmaChamber chamber = chamber(
                MagmaComposition.forType(VolcanoType.CALDERA),
                355.0,
                0.25,
                1_240.0);
        EruptionEvent started = controller.begin(VOLCANO_ID, chamber, 44_000L);
        long intoOpening = started.profile().durationTicks(EruptionPhase.PRECURSORS)
                + started.profile().durationTicks(EruptionPhase.OPENING) / 2L;

        EruptionEvent midEruption = controller.advance(started, intoOpening);
        assertEquals(EruptionPhase.OPENING, midEruption.phase());
        assertTrue(midEruption.elapsedTicks() > started.profile().durationTicks(EruptionPhase.PRECURSORS));

        EruptionEvent restored = EruptionEvent.fromTag(midEruption.toTag());
        assertEquals(midEruption, restored);
        assertEquals(VOLCANO_ID, restored.volcanoId());
        assertEquals(44_000L, restored.startedTick());
    }

    private static MagmaChamber chamber(
            MagmaComposition composition,
            double pressure,
            double gas,
            double temperature
    ) {
        return new MagmaChamber(composition, 9.0, pressure, gas, temperature, 0.35);
    }
}
