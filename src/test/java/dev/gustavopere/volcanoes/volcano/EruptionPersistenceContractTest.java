package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EruptionPersistenceContractTest {
    private static final UUID VOLCANO_ID = UUID.fromString("63410398-ae81-47bf-a455-a66f4a1aceac");

    @Test
    void activeEruptionPersistsUnderStableVolcanoIdAcrossSavedDataRoundTrip() {
        VolcanoSavedData data = new VolcanoSavedData();
        VolcanoSite site = site(VOLCANO_ID);
        MagmaChamber chamber = chamber();
        data.register(site);
        data.updateLifecycle(VOLCANO_ID, VolcanoState.ERUPTING, chamber);

        EruptionController controller = new EruptionController();
        EruptionEvent started = controller.begin(VOLCANO_ID, chamber, 80_000L);
        EruptionEvent midEruption = controller.advance(
                started,
                started.profile().durationTicks(EruptionPhase.PRECURSORS)
                        + started.profile().durationTicks(EruptionPhase.OPENING) / 2L);

        assertTrue(data.updateEruption(midEruption));
        assertFalse(data.updateEruption(midEruption));
        assertEquals(midEruption, data.eruption(VOLCANO_ID).orElseThrow());

        VolcanoSavedData restored = VolcanoSavedData.fromTag(data.toTag());
        assertEquals(site.persistenceId(), restored.get(VOLCANO_ID).orElseThrow().persistenceId());
        assertEquals(chamber, restored.chamber(VOLCANO_ID).orElseThrow());
        assertEquals(midEruption, restored.eruption(VOLCANO_ID).orElseThrow());

        assertTrue(restored.clearEruption(VOLCANO_ID));
        assertFalse(restored.clearEruption(VOLCANO_ID));
        assertTrue(restored.eruption(VOLCANO_ID).isEmpty());
    }

    @Test
    void eruptionPersistenceRejectsUnknownVolcanoIds() {
        VolcanoSavedData data = new VolcanoSavedData();
        data.register(site(VOLCANO_ID));
        UUID unknown = UUID.fromString("53876ca1-e33f-4a0d-b141-20b75b821c45");
        EruptionEvent foreign = new EruptionController().begin(unknown, chamber(), 1_000L);

        assertThrows(IllegalArgumentException.class, () -> data.updateEruption(foreign));
    }

    private static VolcanoSite site(UUID id) {
        return new VolcanoSite(
                id,
                new BlockPos(512, 96, -1_024),
                VolcanoType.STRATOVOLCANO,
                VolcanoState.ERUPTING,
                TectonicContext.CONVERGENT,
                17L,
                18L,
                0.91);
    }

    private static MagmaChamber chamber() {
        return new MagmaChamber(
                MagmaComposition.forType(VolcanoType.STRATOVOLCANO),
                10.0,
                330.0,
                0.21,
                1_235.0,
                0.40);
    }
}
