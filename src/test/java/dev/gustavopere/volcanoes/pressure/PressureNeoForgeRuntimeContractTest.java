package dev.gustavopere.volcanoes.pressure;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class PressureNeoForgeRuntimeContractTest {
    @Test
    void exposesServerPlayerTickLogoutChunkLifecycleAndLevelUnloadHooks() throws Exception {
        assertEquals(
                void.class,
                PressureNeoForgeRuntime.class
                        .getMethod("onPlayerTick", PlayerTickEvent.Post.class)
                        .getReturnType());
        assertEquals(
                void.class,
                PressureNeoForgeRuntime.class
                        .getMethod("onPlayerLoggedOut", PlayerEvent.PlayerLoggedOutEvent.class)
                        .getReturnType());
        assertEquals(
                void.class,
                PressureNeoForgeRuntime.class
                        .getMethod("onChunkLoad", ChunkEvent.Load.class)
                        .getReturnType());
        assertEquals(
                void.class,
                PressureNeoForgeRuntime.class
                        .getMethod("onChunkUnload", ChunkEvent.Unload.class)
                        .getReturnType());
        assertEquals(
                void.class,
                PressureNeoForgeRuntime.class
                        .getMethod("onLevelUnload", LevelEvent.Unload.class)
                        .getReturnType());
    }

    @Test
    void exposesSetupTimeIntegrationRegistrationPoints() throws Exception {
        assertEquals(
                void.class,
                PressureNeoForgeRuntime.class
                        .getMethod("registerAtmosphericPressureProvider", AtmosphericPressureProvider.class)
                        .getReturnType());
        assertEquals(
                void.class,
                PressureNeoForgeRuntime.class
                        .getMethod("registerEnclosedEnvironmentProvider", EnclosedEnvironmentProvider.class)
                        .getReturnType());
        assertEquals(
                void.class,
                PressureNeoForgeRuntime.class
                        .getMethod("registerEquipmentProtectionAdapter", EquipmentProtectionAdapter.class)
                        .getReturnType());
    }

    @Test
    void exposesExplicitEnclosedCacheInvalidationForHostStateChanges() throws Exception {
        assertEquals(
                void.class,
                PressureNeoForgeRuntime.class
                        .getMethod("invalidateEnclosedEnvironmentEntity", UUID.class)
                        .getReturnType());
        assertEquals(
                void.class,
                PressureNeoForgeRuntime.class
                        .getMethod("invalidateEnclosedEnvironmentVehicle", UUID.class)
                        .getReturnType());
    }
}
