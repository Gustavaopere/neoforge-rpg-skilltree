package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PressureAtmosphereBaselineProviderContractTest {
    @Test
    void canonicalPressureReplacesOnlyTotalPressureAndIsSampledExactlyOnce() {
        AtmosphereState chemistry = new AtmosphereState(
                0.25,
                0.19,
                0.03,
                17.0,
                11.0,
                2.0,
                3.0,
                0.7,
                4.5);
        AtomicInteger calls = new AtomicInteger();
        PressureAtmosphereBaselineProvider provider = new PressureAtmosphereBaselineProvider(
                (dimensionId, y) -> chemistry,
                (dimensionId, y) -> {
                    assertEquals("minecraft:the_end", dimensionId);
                    assertEquals(192.0, y, 0.0);
                    calls.incrementAndGet();
                    return 7.25;
                });

        AtmosphereState sampled = provider.sample("minecraft:the_end", 192.0);

        assertEquals(1, calls.get());
        assertEquals(7.25, sampled.totalPressureAtm(), 0.0);
        assertEquals(chemistry.oxygenFraction(), sampled.oxygenFraction(), 0.0);
        assertEquals(chemistry.carbonDioxideFraction(), sampled.carbonDioxideFraction(), 0.0);
        assertEquals(chemistry.sulfurDioxidePpm(), sampled.sulfurDioxidePpm(), 0.0);
        assertEquals(chemistry.toxicGasPpm(), sampled.toxicGasPpm(), 0.0);
        assertEquals(chemistry.particulatesMgM3(), sampled.particulatesMgM3(), 0.0);
        assertEquals(chemistry.smokeMgM3(), sampled.smokeMgM3(), 0.0);
        assertEquals(chemistry.relativeHumidity(), sampled.relativeHumidity(), 0.0);
        assertEquals(chemistry.thermalModifierC(), sampled.thermalModifierC(), 0.0);
    }

    @Test
    void atmosphereRuntimeUsesTheCanonicalPressureBaselineWrapper() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/environment/AtmosphereRuntime.java"));
        assertTrue(source.contains(
                "PressureAtmosphereBaselineProvider.canonical(LayeredAtmosphereBaselineProvider.standard())"));
    }
}
