package dev.gustavopere.volcanoes.pressure;

import net.neoforged.neoforge.event.level.ChunkEvent;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PressureNeoForgeCacheLifecycleContractTest {
    @Test
    void exposesChunkUnloadInvalidationHook() throws Exception {
        assertEquals(
                void.class,
                PressureNeoForgeRuntime.class
                        .getMethod("onChunkUnload", ChunkEvent.Unload.class)
                        .getReturnType());
    }

    @Test
    void modRegistersPressureRuntimeLifecycleHooks() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java"));

        assertTrue(source.contains("PressureNeoForgeRuntime::onPlayerTick"));
        assertTrue(source.contains("PressureNeoForgeRuntime::onPlayerLoggedOut"));
        assertTrue(source.contains("PressureNeoForgeRuntime::onChunkUnload"));
        assertTrue(source.contains("PressureNeoForgeRuntime::onLevelUnload"));
    }
}
