package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EruptionSignalContractTest {
    private static final UUID VOLCANO_ID = UUID.fromString("2b9202c6-c910-4f86-ab97-32837078f65d");

    @Test
    void consumerSignalCarriesSourcePhysicsAndBoundedPhaseEnvelope() {
        VolcanoSite site = new VolcanoSite(
                VOLCANO_ID,
                new BlockPos(96, 84, -144),
                VolcanoType.STRATOVOLCANO,
                VolcanoState.ERUPTING,
                TectonicContext.CONVERGENT,
                11L,
                12L,
                0.82);
        MagmaChamber chamber = new MagmaChamber(
                new MagmaComposition(0.72, 0.80),
                9.0,
                360.0,
                0.28,
                1_250.0,
                0.35);
        EruptionController controller = new EruptionController();
        EruptionEvent started = controller.begin(VOLCANO_ID, chamber, 5_000L);

        long openingMidpoint = started.profile().precursorsTicks() + started.profile().openingTicks() / 2L;
        EruptionSignal opening = EruptionSignal.from(
                site,
                chamber,
                controller.advance(started, openingMidpoint));

        assertEquals(VOLCANO_ID, opening.volcanoId());
        assertEquals(site.center(), opening.source());
        assertEquals(chamber, opening.chamber());
        assertEquals(EruptionPhase.OPENING, opening.phase());
        assertTrue(opening.phaseProgress() > 0.0 && opening.phaseProgress() < 1.0);
        assertTrue(opening.intensity() > 0.0 && opening.intensity() < opening.profile().peakIntensity());

        long sustainedMidpoint = started.profile().precursorsTicks()
                + started.profile().openingTicks()
                + started.profile().sustainedTicks() / 2L;
        EruptionSignal sustained = EruptionSignal.from(
                site,
                chamber,
                controller.advance(started, sustainedMidpoint));
        assertEquals(EruptionPhase.SUSTAINED, sustained.phase());
        assertEquals(sustained.profile().peakIntensity(), sustained.intensity(), 1.0e-12);

        long waningMidpoint = started.profile().precursorsTicks()
                + started.profile().openingTicks()
                + started.profile().sustainedTicks()
                + started.profile().waningTicks() / 2L;
        EruptionSignal waning = EruptionSignal.from(
                site,
                chamber,
                controller.advance(started, waningMidpoint));
        assertEquals(EruptionPhase.WANING, waning.phase());
        assertTrue(waning.intensity() > 0.0 && waning.intensity() < waning.profile().peakIntensity());

        EruptionSignal complete = EruptionSignal.from(
                site,
                chamber,
                controller.advance(started, started.profile().totalDurationTicks()));
        assertEquals(EruptionPhase.DORMANT, complete.phase());
        assertEquals(1.0, complete.phaseProgress(), 1.0e-12);
        assertEquals(0.0, complete.intensity(), 1.0e-12);
    }

    @Test
    void mismatchedVolcanoIdentityFailsClosed() {
        VolcanoSite site = new VolcanoSite(
                UUID.fromString("8d8f8e32-0bde-44a3-b0d1-a0b080a52e74"),
                BlockPos.ZERO,
                VolcanoType.SHIELD,
                VolcanoState.ERUPTING,
                TectonicContext.HOTSPOT,
                1L,
                2L,
                0.75);
        MagmaChamber chamber = new MagmaChamber(
                MagmaComposition.forType(VolcanoType.SHIELD),
                8.0,
                310.0,
                0.18,
                1_230.0,
                0.30);
        EruptionEvent event = new EruptionController().begin(VOLCANO_ID, chamber, 1L);

        assertThrows(IllegalArgumentException.class, () -> EruptionSignal.from(site, chamber, event));
    }
}
