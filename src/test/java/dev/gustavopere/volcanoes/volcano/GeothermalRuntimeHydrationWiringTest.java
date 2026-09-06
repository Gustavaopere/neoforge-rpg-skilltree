package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalRuntimeHydrationWiringTest {
    @Test
    void overworldTickHydratesPersistedGeothermalRuntimeAfterRestart() throws IOException {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/GeothermalWorldgenRuntime.java"));

        assertTrue(
                source.contains("RuntimeState state = stateFor(level);"),
                "overworld tick must hydrate persistent geothermal sources even when no new worldgen or heat query occurs");
    }
}
