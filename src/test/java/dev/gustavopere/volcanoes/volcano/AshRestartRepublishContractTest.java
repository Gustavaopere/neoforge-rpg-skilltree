package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Cross-stage restart contract for the authoritative Stage-03 ash lifecycle.
 *
 * <p>Atmosphere must be able to rebuild its derived, non-persistent ash source after a server
 * restart without registering a second eruption consumer. The persisted eruption remains the
 * authority; discovery/reload republishes ash metadata only and must not redispatch eruption work.</p>
 */
final class AshRestartRepublishContractTest {
    private static final UUID VOLCANO_ID = UUID.fromString("d336f7f2-a96d-4b8a-a423-f51191913e91");

    @Test
    void restoredActiveEruptionRepublishesAshWithoutRedispatchingEruptionWork() throws Exception {
        VolcanoSavedData original = new VolcanoSavedData();
        VolcanoSite site = site();
        MagmaChamber chamber = chamber();
        original.register(site);
        original.updateLifecycle(VOLCANO_ID, VolcanoState.ERUPTING, chamber);

        EruptionController controller = new EruptionController();
        EruptionEvent started = controller.begin(VOLCANO_ID, chamber, 80_000L);
        EruptionEvent active = controller.advance(
                started,
                started.profile().durationTicks(EruptionPhase.PRECURSORS)
                        + started.profile().durationTicks(EruptionPhase.OPENING) / 2L);
        assertEquals(EruptionPhase.OPENING, active.phase());
        assertTrue(original.updateEruption(active));

        // This round trip is the unit-level restart boundary: only authoritative SavedData survives.
        VolcanoSavedData restored = VolcanoSavedData.fromTag(original.toTag());
        VolcanoSite restoredSite = restored.get(VOLCANO_ID).orElseThrow();
        MagmaChamber restoredChamber = restored.chamber(VOLCANO_ID).orElseThrow();
        EruptionEvent restoredEvent = restored.eruption(VOLCANO_ID).orElseThrow();
        AshPlumeEmission expected = AshPlumeEmission.from(
                EruptionSignal.from(restoredSite, restoredChamber, restoredEvent));
        assertTrue(expected.active(), "fixture must reconstruct an active ash plume");

        UUID sourceId = expected.sourceId();
        AshEmissionIndex index = VolcanicHazardWorldRuntime.ashIndex();
        index.remove(sourceId);

        Method registerAshObserver = VolcanicHazardWorldRuntime.class.getMethod(
                "registerAshEmissionLifecycleSink",
                AshEmissionLifecycleSink.class);
        Method unregisterAshObserver = VolcanicHazardWorldRuntime.class.getMethod(
                "unregisterAshEmissionLifecycleSink",
                AshEmissionLifecycleSink.class);

        RecordingAshSink observer = new RecordingAshSink();
        List<EruptionSignal> redispatchedSignals = new ArrayList<>();
        EruptionSink eruptionProbe = (signal, workGrant) -> redispatchedSignals.add(signal);
        boolean observerRegistered = false;
        boolean eruptionProbeRegistered = false;
        try {
            observerRegistered = (boolean) registerAshObserver.invoke(null, observer);
            assertTrue(observerRegistered, "restart observer must register exactly once");
            eruptionProbeRegistered = VolcanoLifecycleRuntime.registerEruptionSink(eruptionProbe);
            assertTrue(eruptionProbeRegistered, "generic eruption probe must register for the isolation assertion");

            VolcanoLifecycleRuntime.RuntimeState runtime = new VolcanoLifecycleRuntime.RuntimeState(
                    restored,
                    new VolcanoManager(restored, TectonicService.fallback()));
            runtime.discoverSites(80_000L);

            assertEquals(
                    expected,
                    index.bySourceId(sourceId).orElseThrow(),
                    "restored active eruption must rebuild the authoritative in-memory ash plume");
            assertEquals(
                    expected,
                    observer.upserts.get(sourceId),
                    "restart rebuild must republish the same stable ash identity to transient observers");
            assertTrue(
                    redispatchedSignals.isEmpty(),
                    "ash restart replay must not redispatch eruption work or consume a second work grant");
        } finally {
            index.remove(sourceId);
            if (observerRegistered) {
                unregisterAshObserver.invoke(null, observer);
            }
            if (eruptionProbeRegistered) {
                VolcanoLifecycleRuntime.unregisterEruptionSink(eruptionProbe);
            }
        }
    }

    private static VolcanoSite site() {
        return new VolcanoSite(
                VOLCANO_ID,
                new BlockPos(224, 94, -448),
                VolcanoType.STRATOVOLCANO,
                VolcanoState.ERUPTING,
                TectonicContext.CONVERGENT,
                31L,
                32L,
                0.92);
    }

    private static MagmaChamber chamber() {
        return new MagmaChamber(
                MagmaComposition.forType(VolcanoType.STRATOVOLCANO),
                9.5,
                336.0,
                0.24,
                1_245.0,
                0.43);
    }

    private static final class RecordingAshSink implements AshEmissionLifecycleSink {
        private final Map<UUID, AshPlumeEmission> upserts = new HashMap<>();

        @Override
        public void upsert(AshPlumeEmission emission) {
            upserts.put(emission.sourceId(), emission);
        }

        @Override
        public void remove(UUID sourceId) {
            upserts.remove(sourceId);
        }
    }
}
