package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalAtmosphereRuntimeWiringContractTest {
    @Test
    void atmosphereRuntimeOwnsPerLevelGeothermalLifecycleBridge() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/environment/AtmosphereRuntime.java"));

        assertTrue(source.contains("GeothermalSourceRegistry"),
                "Atmosphere runtime must consume canonical Stage03 geothermal lifecycle authority");
        assertTrue(source.contains("GeothermalAtmosphereBridge"),
                "Atmosphere runtime must own the derived gas bridge");
        assertTrue(source.contains("registerLifecycleSink"),
                "per-level bridge creation must replay/register the canonical geothermal source lifecycle");
        assertTrue(source.contains("unregisterLifecycleSink"),
                "level unload must unregister the transient observer");
        assertTrue(source.contains("geothermalBridgeFor(level).flush(MAX_SOURCE_UPDATES_PER_INTERVAL)"),
                "Atmosphere cadence must drain geothermal gas work under the same bounded source-update budget");
    }
}
