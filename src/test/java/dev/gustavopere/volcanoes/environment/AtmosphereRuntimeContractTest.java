package dev.gustavopere.volcanoes.environment;

import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class AtmosphereRuntimeContractTest {
    @Test
    void runtimeExposesServerBreathingHookAndBoundedDiffusionCadence() throws Exception {
        assertFalse(AtmosphereRuntime.shouldProcessDiffusion(19L));
        assertTrue(AtmosphereRuntime.shouldProcessDiffusion(20L));
        assertFalse(AtmosphereRuntime.shouldProcessDiffusion(21L));
        assertEquals(void.class,
                AtmosphereRuntime.class.getMethod("onLivingBreathe", LivingBreatheEvent.class).getReturnType());
        assertEquals(void.class,
                AtmosphereRuntime.class.getMethod("onLevelTick", LevelTickEvent.Post.class).getReturnType());
        assertEquals(void.class,
                AtmosphereRuntime.class.getMethod("onLevelUnload", LevelEvent.Unload.class).getReturnType());
    }

    @Test
    void runtimeExposesNeutralStableSourceSinkWithoutLeakingFieldImplementation() throws Exception {
        assertTrue(AtmosphericSourceSink.class.isAssignableFrom(AtmosphereField.class));
        assertEquals(
                AtmosphericSourceSink.class,
                AtmosphereRuntime.class.getMethod("sourceSinkFor", ServerLevel.class).getReturnType());
    }

    @Test
    void runtimeExposesNeutralExternalReadbackInstallationWithoutDestroyDependency() throws Exception {
        assertEquals(
                void.class,
                AtmosphereRuntime.class.getMethod(
                        "installExternalContributionProvider",
                        ServerLevel.class,
                        AtmosphereExternalContributionProvider.class).getReturnType());
    }
}
