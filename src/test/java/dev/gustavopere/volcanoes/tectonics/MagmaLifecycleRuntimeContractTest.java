package dev.gustavopere.volcanoes.tectonics;

import dev.gustavopere.volcanoes.volcano.VolcanoLifecycleRuntime;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class MagmaLifecycleRuntimeContractTest {
    @Test
    void tectonicRuntimeOffersDimensionScopedSeismicPerturbationsWithoutBreakingLegacySink() throws Exception {
        AtomicInteger calls = new AtomicInteger();
        AtomicReference<ResourceKey<Level>> seenDimension = new AtomicReference<>();
        SeismicEvent event = new SeismicEvent(
                64.0,
                -32.0,
                6.0,
                1_200.0,
                1.5,
                SeismicDamagePolicy.safeDefaults());

        AutoCloseable registration = TectonicRuntime.registerDimensionalPerturbationSink((dimension, ignored) -> {
            seenDimension.set(dimension);
            calls.incrementAndGet();
        });
        try {
            assertEquals(1, TectonicRuntime.dispatchDimensionalPerturbations(Level.OVERWORLD, event));
            assertEquals(Level.OVERWORLD, seenDimension.get());
            assertEquals(1, calls.get());
        } finally {
            registration.close();
        }

        assertEquals(0, TectonicRuntime.dispatchDimensionalPerturbations(Level.NETHER, event));
        assertEquals(1, calls.get());
    }

    @Test
    void volcanoLifecycleRuntimeIsOverworldOnlyAndDiscoveryRescanUsesLongCadence() throws Exception {
        assertEquals(
                void.class,
                VolcanoLifecycleRuntime.class
                        .getMethod("onLevelTick", LevelTickEvent.Post.class)
                        .getReturnType());

        Method shouldRunIn = VolcanoLifecycleRuntime.class.getDeclaredMethod("shouldRunIn", ResourceKey.class);
        shouldRunIn.setAccessible(true);
        assertTrue((boolean) shouldRunIn.invoke(null, Level.OVERWORLD));
        assertFalse((boolean) shouldRunIn.invoke(null, Level.NETHER));
        assertFalse((boolean) shouldRunIn.invoke(null, Level.END));

        Method shouldRescanSites = VolcanoLifecycleRuntime.class.getDeclaredMethod("shouldRescanSites", long.class);
        shouldRescanSites.setAccessible(true);
        assertTrue((boolean) shouldRescanSites.invoke(null, 0L));
        assertFalse((boolean) shouldRescanSites.invoke(null, 23_999L));
        assertTrue((boolean) shouldRescanSites.invoke(null, 24_000L));
        assertFalse((boolean) shouldRescanSites.invoke(null, 24_001L));
    }
}
