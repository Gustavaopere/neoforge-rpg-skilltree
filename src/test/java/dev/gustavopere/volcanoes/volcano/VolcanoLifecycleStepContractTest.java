package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanoLifecycleStepContractTest {
    private static final UUID VOLCANO_ID = UUID.fromString("62023726-a640-43b5-a30f-9c0cb7db6f16");

    @Test
    void macroLifecycleAndDetailedEruptionAdvanceThroughOneStep() {
        VolcanoSavedData data = new VolcanoSavedData();
        VolcanoSite site = site(VolcanoState.ERUPTING);
        MagmaChamber chamber = chamber();
        data.register(site);
        data.updateLifecycle(VOLCANO_ID, VolcanoState.ERUPTING, chamber);

        VolcanoManager manager = new VolcanoManager(data, (seed, x, z) -> tectonicSample());
        EruptionDispatcher dispatcher = new EruptionDispatcher();
        List<EruptionSignal> signals = new ArrayList<>();
        dispatcher.register((signal, work) -> signals.add(signal));
        EruptionEffectRuntime effects = new EruptionEffectRuntime(
                new EruptionRuntimeCoordinator(),
                new EruptionScheduler(64, 4, 256, 16),
                dispatcher);
        VolcanoLifecycleStep step = new VolcanoLifecycleStep(manager, effects);

        VolcanoLifecycleStep.StepResult first = step.advance(
                91L, data, VOLCANO_ID, 10_000L, 200L, 0.0);

        assertEquals(VolcanoState.ERUPTING, first.state());
        assertTrue(first.emission().signalEmitted());
        assertEquals(1, signals.size());
        assertEquals(EruptionPhase.PRECURSORS, signals.getFirst().phase());
        assertTrue(data.eruption(VOLCANO_ID).isPresent());

        EruptionEvent persisted = data.eruption(VOLCANO_ID).orElseThrow();
        long delta = persisted.profile().precursorsTicks() + persisted.profile().openingTicks() / 2L;
        VolcanoLifecycleStep.StepResult second = step.advance(
                91L, data, VOLCANO_ID, 10_000L + delta, delta, 0.0);

        assertEquals(VolcanoState.ERUPTING, second.state());
        assertTrue(second.emission().signalEmitted());
        assertEquals(EruptionPhase.OPENING, signals.getLast().phase());
    }

    @Test
    void nonEruptingMacroStateRetiresDetailedEventThroughSameStep() {
        VolcanoSavedData data = new VolcanoSavedData();
        VolcanoSite site = site(VolcanoState.ACTIVE);
        MagmaChamber chamber = new MagmaChamber(
                MagmaComposition.forType(VolcanoType.STRATOVOLCANO),
                8.0,
                150.0,
                0.05,
                1_230.0,
                0.20);
        data.register(site);
        data.updateLifecycle(VOLCANO_ID, VolcanoState.ACTIVE, chamber);
        EruptionEvent stale = new EruptionController().begin(VOLCANO_ID, chamber(), 5_000L);
        data.updateEruption(stale);

        VolcanoManager manager = new VolcanoManager(data, (seed, x, z) -> tectonicSample());
        EruptionEffectRuntime effects = new EruptionEffectRuntime(
                new EruptionRuntimeCoordinator(),
                new EruptionScheduler(64, 4, 256, 16),
                new EruptionDispatcher());
        VolcanoLifecycleStep step = new VolcanoLifecycleStep(manager, effects);

        VolcanoLifecycleStep.StepResult result = step.advance(
                91L, data, VOLCANO_ID, 12_000L, 200L, 0.0);

        assertEquals(VolcanoState.ACTIVE, result.state());
        assertTrue(!result.emission().signalEmitted());
        assertTrue(data.eruption(VOLCANO_ID).isEmpty());
    }

    private static VolcanoSite site(VolcanoState state) {
        return new VolcanoSite(
                VOLCANO_ID,
                new BlockPos(640, 108, -920),
                VolcanoType.STRATOVOLCANO,
                state,
                TectonicContext.CONVERGENT,
                61L,
                62L,
                0.93);
    }

    private static MagmaChamber chamber() {
        return new MagmaChamber(
                MagmaComposition.forType(VolcanoType.STRATOVOLCANO),
                9.0,
                335.0,
                0.22,
                1_250.0,
                0.42);
    }

    private static TectonicSample tectonicSample() {
        return new TectonicSample(
                61L,
                62L,
                TectonicContext.CONVERGENT,
                0.75,
                0.93,
                64.0,
                0.2,
                -0.1);
    }
}
