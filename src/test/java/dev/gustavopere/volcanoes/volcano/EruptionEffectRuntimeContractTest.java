package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EruptionEffectRuntimeContractTest {
    private static final UUID VOLCANO_ID = UUID.fromString("aa0aef09-b11c-40de-b51a-8e7e22f31965");

    @Test
    void coordinatorBudgetAndDispatcherFormOneBoundedEmissionPath() {
        VolcanoSavedData data = new VolcanoSavedData();
        VolcanoSite site = site(VolcanoState.ERUPTING);
        MagmaChamber chamber = chamber();
        data.register(site);
        data.updateLifecycle(VOLCANO_ID, VolcanoState.ERUPTING, chamber);

        EruptionScheduler scheduler = new EruptionScheduler(8, 2, 16, 4);
        EruptionDispatcher dispatcher = new EruptionDispatcher();
        List<EruptionScheduler.WorkGrant> grants = new ArrayList<>();
        List<EruptionSignal> signals = new ArrayList<>();
        dispatcher.register((signal, grant) -> {
            signals.add(signal);
            grants.add(grant);
        });
        EruptionEffectRuntime runtime = new EruptionEffectRuntime(
                new EruptionRuntimeCoordinator(), scheduler, dispatcher);

        EruptionEffectRuntime.EmissionResult begun = runtime.update(data, site, chamber, 1_000L, 0L);
        assertTrue(begun.signalEmitted());
        assertEquals(1, begun.dispatch().delivered());
        assertEquals(EruptionPhase.PRECURSORS, signals.getFirst().phase());
        assertTrue(grants.getFirst().immediateBlocks() <= 8);
        assertTrue(grants.getFirst().immediateEntities() <= 2);

        EruptionEvent event = data.eruption(VOLCANO_ID).orElseThrow();
        long openingDelta = event.profile().precursorsTicks() + event.profile().openingTicks() / 2L;
        EruptionEffectRuntime.EmissionResult opening = runtime.update(
                data, site, chamber, 1_000L + openingDelta, openingDelta);
        assertTrue(opening.signalEmitted());
        assertEquals(EruptionPhase.OPENING, signals.getLast().phase());
        assertTrue(scheduler.queuedBlocks(VOLCANO_ID) <= 16);
        assertTrue(scheduler.queuedEntities(VOLCANO_ID) <= 4);
    }

    @Test
    void retiringDetailedEventClearsAnyNumericBacklog() {
        VolcanoSavedData data = new VolcanoSavedData();
        VolcanoSite erupting = site(VolcanoState.ERUPTING);
        MagmaChamber chamber = chamber();
        data.register(erupting);
        data.updateLifecycle(VOLCANO_ID, VolcanoState.ERUPTING, chamber);

        EruptionScheduler scheduler = new EruptionScheduler(1, 1, 64, 8);
        EruptionEffectRuntime runtime = new EruptionEffectRuntime(
                new EruptionRuntimeCoordinator(), scheduler, new EruptionDispatcher());
        runtime.update(data, erupting, chamber, 2_000L, 0L);
        EruptionEvent started = data.eruption(VOLCANO_ID).orElseThrow();
        runtime.update(data, erupting, chamber, 2_001L, started.profile().openingTicks());
        assertTrue(scheduler.queuedBlocks(VOLCANO_ID) > 0);

        long remaining = data.eruption(VOLCANO_ID).orElseThrow().profile().totalDurationTicks()
                - data.eruption(VOLCANO_ID).orElseThrow().elapsedTicks();
        runtime.update(data, erupting, chamber, 2_001L + remaining, remaining);
        MagmaChamber persistedChamber = data.chamber(VOLCANO_ID).orElseThrow();
        data.updateLifecycle(VOLCANO_ID, VolcanoState.ACTIVE, persistedChamber);

        EruptionEffectRuntime.EmissionResult retired = runtime.update(
                data, data.get(VOLCANO_ID).orElseThrow(), persistedChamber, 2_002L + remaining, 1L);
        assertTrue(!retired.signalEmitted());
        assertEquals(0, scheduler.queuedBlocks(VOLCANO_ID));
        assertEquals(0, scheduler.queuedEntities(VOLCANO_ID));
        assertTrue(data.eruption(VOLCANO_ID).isEmpty());
    }

    private static VolcanoSite site(VolcanoState state) {
        return new VolcanoSite(
                VOLCANO_ID,
                new BlockPos(480, 101, -720),
                VolcanoType.STRATOVOLCANO,
                state,
                TectonicContext.CONVERGENT,
                41L,
                42L,
                0.91);
    }

    private static MagmaChamber chamber() {
        return new MagmaChamber(
                MagmaComposition.forType(VolcanoType.STRATOVOLCANO),
                8.0,
                325.0,
                0.21,
                1_240.0,
                0.38);
    }
}
