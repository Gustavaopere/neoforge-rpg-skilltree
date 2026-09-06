package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanoLifecycleEruptionWiringContractTest {
    private static final UUID VOLCANO_ID = UUID.fromString("e4c42c3f-d26f-4d40-bc37-0b95204ce3dd");

    @Test
    void existingVolcanoTickSchedulerOwnsDetailedEruptionUpdates() {
        VolcanoSavedData data = new VolcanoSavedData();
        VolcanoSite site = site();
        MagmaChamber chamber = chamber();
        data.register(site);
        data.updateLifecycle(VOLCANO_ID, VolcanoState.ERUPTING, chamber);

        List<EruptionSignal> signals = new ArrayList<>();
        EruptionDispatcher dispatcher = new EruptionDispatcher();
        dispatcher.register((signal, work) -> signals.add(signal));
        EruptionEffectRuntime effects = new EruptionEffectRuntime(
                new EruptionRuntimeCoordinator(),
                new EruptionScheduler(32, 4, 128, 16),
                dispatcher);
        VolcanoLifecycleRuntime.RuntimeState runtime = new VolcanoLifecycleRuntime.RuntimeState(
                data,
                new VolcanoManager(data, TectonicService.fallback()),
                effects);

        runtime.discoverSites(0L);
        runtime.processDue(77L, 199L);
        assertTrue(signals.isEmpty(), "detailed eruption work must not bypass the existing due queue");
        assertTrue(data.eruption(VOLCANO_ID).isEmpty());

        runtime.processDue(77L, 200L);
        assertEquals(1, signals.size());
        assertEquals(EruptionPhase.PRECURSORS, signals.getFirst().phase());
        assertTrue(data.eruption(VOLCANO_ID).isPresent());

        long firstElapsed = data.eruption(VOLCANO_ID).orElseThrow().elapsedTicks();
        runtime.processDue(77L, 399L);
        assertEquals(1, signals.size(), "no detailed update is emitted between scheduled coarse ticks");
        assertEquals(firstElapsed, data.eruption(VOLCANO_ID).orElseThrow().elapsedTicks());

        runtime.processDue(77L, 400L);
        assertEquals(2, signals.size());
        assertEquals(firstElapsed + 200L, data.eruption(VOLCANO_ID).orElseThrow().elapsedTicks());
    }

    @Test
    void runtimeProducerExposesSharedSinkRegistration() {
        VolcanoSavedData data = new VolcanoSavedData();
        VolcanoSite site = site();
        MagmaChamber chamber = chamber();
        data.register(site);
        data.updateLifecycle(VOLCANO_ID, VolcanoState.ERUPTING, chamber);

        List<EruptionSignal> signals = new ArrayList<>();
        EruptionSink sink = (signal, work) -> signals.add(signal);
        assertTrue(VolcanoLifecycleRuntime.registerEruptionSink(sink));
        assertFalse(VolcanoLifecycleRuntime.registerEruptionSink(sink));
        try {
            VolcanoLifecycleRuntime.RuntimeState runtime = new VolcanoLifecycleRuntime.RuntimeState(
                    data,
                    new VolcanoManager(data, TectonicService.fallback()));
            runtime.discoverSites(0L);
            runtime.processDue(77L, 200L);

            assertEquals(1, signals.size());
            assertEquals(VOLCANO_ID, signals.getFirst().volcanoId());
            assertEquals(EruptionPhase.PRECURSORS, signals.getFirst().phase());
        } finally {
            assertTrue(VolcanoLifecycleRuntime.unregisterEruptionSink(sink));
        }
        assertFalse(VolcanoLifecycleRuntime.unregisterEruptionSink(sink));
    }

    private static VolcanoSite site() {
        return new VolcanoSite(
                VOLCANO_ID,
                new BlockPos(160, 90, -320),
                VolcanoType.STRATOVOLCANO,
                VolcanoState.ERUPTING,
                TectonicContext.CONVERGENT,
                21L,
                22L,
                0.90);
    }

    private static MagmaChamber chamber() {
        return new MagmaChamber(
                MagmaComposition.forType(VolcanoType.STRATOVOLCANO),
                9.0,
                330.0,
                0.22,
                1_240.0,
                0.35);
    }
}
