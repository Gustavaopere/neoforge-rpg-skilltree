package dev.gustavopere.volcanoes.compat;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class OptionalIntegrationBootstrapArchitectureTest {
    @Test
    void modEntrypointInstallsOptionalIntegrationBootstrap() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java"));

        assertTrue(source.contains("OptionalIntegrationBootstrap.install()"));
    }
}
