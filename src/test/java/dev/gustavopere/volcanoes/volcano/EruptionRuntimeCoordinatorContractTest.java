package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EruptionRuntimeCoordinatorContractTest {
    private static final UUID VOLCANO_ID = UUID.fromString("54a203e3-c566-4a33-904e-c4e84ce872ac");

    @Test
    void persistedEruptionResumesAfterReloadCompletesOnceAndClearsAfterCoarseExit() {
        VolcanoSavedData data = new VolcanoSavedData();
        MagmaChamber chamber = chamber();
        data.register(site(VolcanoState.ERUPTING));
        data.updateLifecycle(VOLCANO_ID, VolcanoState.ERUPTING, chamber);
        EruptionRuntimeCoordinator coordinator = new EruptionRuntimeCoordinator();

        Optional<EruptionSignal> begun = coordinator.update(
                data,
                data.get(VOLCANO_ID).orElseThrow(),
                chamber,
                10_000L,
                0L);
        assertTrue(begun.isPresent());
        assertEquals(EruptionPhase.PRECURSORS, begun.orElseThrow().phase());

        EruptionEvent started = data.eruption(VOLCANO_ID).orElseThrow();
        assertEquals(10_000L, started.startedTick());
        assertEquals(0L, started.elapsedTicks());

        long firstDelta = started.profile().precursorsTicks() + started.profile().openingTicks() / 2L;
        Optional<EruptionSignal> opening = coordinator.update(
                data,
                data.get(VOLCANO_ID).orElseThrow(),
                chamber,
                10_000L + firstDelta,
                firstDelta);
        assertTrue(opening.isPresent());
        assertEquals(EruptionPhase.OPENING, opening.orElseThrow().phase());
        EruptionEvent beforeReload = data.eruption(VOLCANO_ID).orElseThrow();

        VolcanoSavedData restored = VolcanoSavedData.fromTag(data.toTag());
        EruptionRuntimeCoordinator afterRestart = new EruptionRuntimeCoordinator();
        long resumedTick = 10_000L + firstDelta + 200L;
        Optional<EruptionSignal> resumedSignal = afterRestart.update(
                restored,
                restored.get(VOLCANO_ID).orElseThrow(),
                restored.chamber(VOLCANO_ID).orElseThrow(),
                resumedTick,
                200L);
        assertTrue(resumedSignal.isPresent());

        EruptionEvent resumed = restored.eruption(VOLCANO_ID).orElseThrow();
        assertEquals(beforeReload.startedTick(), resumed.startedTick(),
                "reload must resume the existing eruption instead of creating a new one");
        assertEquals(beforeReload.elapsedTicks() + 200L, resumed.elapsedTicks());

        long remaining = resumed.profile().totalDurationTicks() - resumed.elapsedTicks();
        Optional<EruptionSignal> completedSignal = afterRestart.update(
                restored,
                restored.get(VOLCANO_ID).orElseThrow(),
                restored.chamber(VOLCANO_ID).orElseThrow(),
                resumedTick + remaining,
                remaining);
        assertTrue(completedSignal.isPresent());
        assertEquals(EruptionPhase.DORMANT, completedSignal.orElseThrow().phase());
        EruptionEvent completed = restored.eruption(VOLCANO_ID).orElseThrow();
        assertTrue(completed.isComplete());

        Optional<EruptionSignal> noRestart = afterRestart.update(
                restored,
                restored.get(VOLCANO_ID).orElseThrow(),
                restored.chamber(VOLCANO_ID).orElseThrow(),
                resumedTick + remaining + 200L,
                200L);
        assertTrue(noRestart.isEmpty(),
                "a completed detailed eruption must not restart while coarse state is still ERUPTING");
        assertEquals(completed, restored.eruption(VOLCANO_ID).orElseThrow());

        MagmaChamber restoredChamber = restored.chamber(VOLCANO_ID).orElseThrow();
        restored.updateLifecycle(VOLCANO_ID, VolcanoState.ACTIVE, restoredChamber);
        Optional<EruptionSignal> afterCoarseExit = afterRestart.update(
                restored,
                restored.get(VOLCANO_ID).orElseThrow(),
                restoredChamber,
                resumedTick + remaining + 400L,
                200L);
        assertTrue(afterCoarseExit.isEmpty());
        assertTrue(restored.eruption(VOLCANO_ID).isEmpty(),
                "leaving coarse ERUPTING state must retire the completed detailed event");
    }

    private static VolcanoSite site(VolcanoState state) {
        return new VolcanoSite(
                VOLCANO_ID,
                new BlockPos(320, 92, -640),
                VolcanoType.CALDERA,
                state,
                TectonicContext.CONVERGENT,
                31L,
                32L,
                0.94);
    }

    private static MagmaChamber chamber() {
        return new MagmaChamber(
                MagmaComposition.forType(VolcanoType.CALDERA),
                12.0,
                350.0,
                0.24,
                1_245.0,
                0.42);
    }
}
