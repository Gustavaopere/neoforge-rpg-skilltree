package dev.gustavopere.volcanoes.tectonics;

import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class TectonicRuntimeContractTest {
    @Test
    void runtimeUsesLongCadenceAndExposesLevelTickHook() throws Exception {
        assertFalse(TectonicRuntime.shouldProcess(599L));
        assertTrue(TectonicRuntime.shouldProcess(600L));
        assertFalse(TectonicRuntime.shouldProcess(601L));
        assertEquals(
                void.class,
                TectonicRuntime.class
                        .getMethod("onLevelTick", LevelTickEvent.Post.class)
                        .getReturnType());
    }

    @Test
    void perturbationSinksCanBeRegisteredAndRemovedWithoutOwningTectonics() throws Exception {
        AtomicInteger calls = new AtomicInteger();
        SeismicEvent event = new SeismicEvent(
                0.0,
                0.0,
                5.0,
                800.0,
                1.5,
                SeismicDamagePolicy.safeDefaults());

        AutoCloseable registration = TectonicRuntime.registerPerturbationSink(ignored -> calls.incrementAndGet());
        assertEquals(1, TectonicRuntime.dispatchPerturbations(event));
        assertEquals(1, calls.get());

        registration.close();
        assertEquals(0, TectonicRuntime.dispatchPerturbations(event));
        assertEquals(1, calls.get());
    }
}
