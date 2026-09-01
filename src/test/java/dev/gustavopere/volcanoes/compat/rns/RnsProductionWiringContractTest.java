package dev.gustavopere.volcanoes.compat.rns;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class RnsProductionWiringContractTest {
    @Test
    void optionalBootstrapRegistersThePerLevelRnsRuntime() throws Exception {
        String bootstrap = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/compat/OptionalIntegrationBootstrap.java"));
        assertTrue(bootstrap.contains("RnsIntegrationRuntime.register();"),
                "normal production startup must register the RNS lifecycle runtime");
    }

    @Test
    void runtimeOwnsInstallRetryAndUnloadLifecycle() throws Exception {
        Path runtimePath = Path.of(
                "src/main/java/dev/gustavopere/volcanoes/compat/rns/RnsIntegrationRuntime.java");
        assertTrue(Files.exists(runtimePath),
                "RNS production integration requires an explicit per-level runtime");
        String runtime = Files.readString(runtimePath);
        assertTrue(runtime.contains("LevelTickEvent.Post"),
                "RNS runtime must install against a live ServerLevel after startup");
        assertTrue(runtime.contains("LevelEvent.Unload"),
                "RNS runtime must release transient lifecycle sinks on level unload");
        assertTrue(runtime.contains("DepositRegistry.get(level)"),
                "RNS runtime must attach to the authoritative per-level geological registry");
        assertTrue(runtime.contains("MAX_INSTALL_ATTEMPTS"),
                "ownership-not-ready retries must be explicitly bounded");
    }
}
