package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.AshEmissionLifecycleSink;
import dev.gustavopere.volcanoes.volcano.EruptionSink;
import dev.gustavopere.volcanoes.volcano.VolcanicHazardWorldRuntime;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class AshAtmosphereBridgeContractTest {
    @Test
    void stage03ExposesNeutralAshObserverRegistrationWithoutSecondEruptionSink() throws Exception {
        Method register = VolcanicHazardWorldRuntime.class.getMethod(
                "registerAshEmissionLifecycleSink",
                AshEmissionLifecycleSink.class);
        Method unregister = VolcanicHazardWorldRuntime.class.getMethod(
                "unregisterAshEmissionLifecycleSink",
                AshEmissionLifecycleSink.class);

        assertTrue(Modifier.isStatic(register.getModifiers()));
        assertTrue(Modifier.isStatic(unregister.getModifiers()));
        assertEquals(boolean.class, register.getReturnType());
        assertEquals(boolean.class, unregister.getReturnType());
    }

    @Test
    void atmosphereOwnsAnExplicitConfigurableAshProjectionPolicy() throws Exception {
        Class<?> policy = Class.forName(
                "dev.gustavopere.volcanoes.environment.AshAtmosphereProjectionPolicy");
        Object defaults = policy.getMethod("defaults").invoke(null);

        double particulates = (double) policy.getMethod("maxParticulatesMgM3").invoke(defaults);
        double smoke = (double) policy.getMethod("maxSmokeMgM3").invoke(defaults);

        assertTrue(Double.isFinite(particulates) && particulates > 0.0);
        assertTrue(Double.isFinite(smoke) && smoke > 0.0);
    }

    @Test
    void bridgeIsAStage03LifecycleObserverWithBoundedRetrySurface() throws Exception {
        Class<?> bridge = Class.forName(
                "dev.gustavopere.volcanoes.environment.AshAtmosphereBridge");

        assertTrue(AshEmissionLifecycleSink.class.isAssignableFrom(bridge));
        assertFalse(EruptionSink.class.isAssignableFrom(bridge),
                "Atmosphere bridge must observe canonical ash lifecycle, never register as a second eruption consumer");
        assertNotNull(bridge.getMethod("flush", net.minecraft.server.level.ServerLevel.class, int.class));
        assertNotNull(bridge.getMethod("pendingCount"));
        assertNotNull(bridge.getMethod("maxPending"));
        assertFalse(Modifier.isAbstract(bridge.getModifiers()));
    }

    @Test
    void projectedAshSourceUsesExternalNonPersistentOwnership() throws Exception {
        Class<?> projection = Class.forName(
                "dev.gustavopere.volcanoes.environment.AshAtmosphereProjection");
        Method method = projection.getMethod(
                "project",
                String.class,
                dev.gustavopere.volcanoes.volcano.AshPlumeEmission.class,
                Class.forName("dev.gustavopere.volcanoes.environment.AshAtmosphereProjectionPolicy"));

        assertEquals(AtmosphericSource.class, method.getReturnType());
    }
}
