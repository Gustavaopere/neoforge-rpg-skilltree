package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class VolcanicHazardWorldRuntimeWiringTest {
    @Test
    void modRegistersHazardSinkAndServerTickRuntime() throws IOException {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java"));

        assertTrue(
                source.contains("VolcanicHazardWorldRuntime.register();"),
                "hazard sink must be attached to the canonical eruption dispatcher");
        assertTrue(
                source.contains("NeoForge.EVENT_BUS.addListener(VolcanicHazardWorldRuntime::onLevelTick);"),
                "level-aware hazard queue and active states must tick on the server event bus");
    }
}
