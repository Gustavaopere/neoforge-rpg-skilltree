package dev.gustavopere.volcanoes.pressure;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PressurePlayerLifecycleContractTest {
    @Test
    void exposesCloneAndDimensionChangeResetHooks() throws Exception {
        assertEquals(
                void.class,
                PressureNeoForgeRuntime.class
                        .getMethod("onPlayerClone", PlayerEvent.Clone.class)
                        .getReturnType());
        assertEquals(
                void.class,
                PressureNeoForgeRuntime.class
                        .getMethod("onPlayerChangedDimension", PlayerEvent.PlayerChangedDimensionEvent.class)
                        .getReturnType());
    }

    @Test
    void modRegistersDiscontinuousPlayerLifecycleHooks() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java"));

        assertTrue(source.contains("PressureNeoForgeRuntime::onPlayerClone"));
        assertTrue(source.contains("PressureNeoForgeRuntime::onPlayerChangedDimension"));
    }
}
