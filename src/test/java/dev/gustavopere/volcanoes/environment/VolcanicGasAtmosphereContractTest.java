package dev.gustavopere.volcanoes.environment;

import dev.gustavopere.volcanoes.volcano.EruptionPhase;
import dev.gustavopere.volcanoes.volcano.EruptionSink;
import dev.gustavopere.volcanoes.volcano.VolcanicGasAuthority;
import dev.gustavopere.volcanoes.volcano.VolcanicGasEmission;
import dev.gustavopere.volcanoes.volcano.VolcanicGasEmissionLifecycleSink;
import net.minecraft.core.BlockPos;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

final class VolcanicGasAtmosphereContractTest {
    @Test
    void projectionMapsNormalizedGasSeverityIntoDistinctAtmosphereHazards() {
        VolcanicGasAtmosphereProjectionPolicy policy = VolcanicGasAtmosphereProjectionPolicy.defaults();
        UUID id = UUID.fromString("11111111-2222-3333-4444-555555555555");
        VolcanicGasEmission emission = new VolcanicGasEmission(
                id,
                UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee"),
                new BlockPos(8, 90, -12),
                EruptionPhase.SUSTAINED,
                0.8,
                160.0,
                2_000L);

        AtmosphericSource source = VolcanicGasAtmosphereProjection.project("minecraft:overworld", emission, policy);
        double expectedCarbonDioxide = policy.maxCarbonDioxideFraction() * 0.8;
        double expectedSulfurDioxide = policy.maxSulfurDioxidePpm() * 0.8;

        assertEquals(id, source.id());
        assertEquals(8.0, source.x());
        assertEquals(90.0, source.y());
        assertEquals(-12.0, source.z());
        assertEquals(160.0, source.radiusBlocks());
        assertFalse(source.persistent());
        assertEquals(AtmosphericSourceEvolution.EXTERNAL, source.evolution());
        assertEquals(1.0, source.strength());
        assertEquals(expectedCarbonDioxide, source.contribution().carbonDioxideFraction(), 1.0e-12);
        assertEquals(expectedSulfurDioxide, source.contribution().sulfurDioxidePpm(), 1.0e-12);
        assertEquals(expectedCarbonDioxide, source.contribution().oxygenDisplacementFraction(), 1.0e-12,
                "volcanic CO2 must displace oxygen as well as populate the CO2 hazard channel");
        assertEquals(0.0, source.contribution().toxicGasPpm(), 1.0e-12);
        assertEquals(0.0, source.contribution().particulatesMgM3(), 1.0e-12);
        assertEquals(0.0, source.contribution().smokeMgM3(), 1.0e-12);
        assertEquals(0.0, source.contribution().pressureDeltaAtm(), 1.0e-12,
                "gas composition must not masquerade as the Stage05 pressure system");
    }

    @Test
    void bridgeIsMetadataObserverAndNeverAnotherEruptionConsumer() {
        assertTrue(VolcanicGasEmissionLifecycleSink.class.isAssignableFrom(VolcanicGasAtmosphereBridge.class));
        assertFalse(EruptionSink.class.isAssignableFrom(VolcanicGasAtmosphereBridge.class));
        assertTrue(VolcanicGasAtmosphereBridge.class.getDeclaredMethods().length > 0);
    }

    @Test
    void canonicalGasAuthorityIsZeroWorkAndAtmosphereObservesItWithoutAnotherEruptionSink() throws Exception {
        assertEquals(boolean.class,
                VolcanicGasAuthority.class
                        .getMethod("registerLifecycleSink", VolcanicGasEmissionLifecycleSink.class)
                        .getReturnType());
        assertEquals(boolean.class,
                VolcanicGasAuthority.class
                        .getMethod("unregisterLifecycleSink", VolcanicGasEmissionLifecycleSink.class)
                        .getReturnType());

        String sink = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/VolcanicHazardSink.java"));
        String authority = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/volcano/VolcanicGasAuthority.java"));
        String atmosphere = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/environment/VolcanicGasAtmosphereRuntime.java"));
        String mod = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java"));

        assertTrue(sink.contains("VolcanicGasEmissionRuntime"));
        assertTrue(authority.contains("VolcanoSavedData.get"));
        assertTrue(authority.contains("registerLifecycleSink"));
        assertTrue(atmosphere.contains("VolcanicGasAuthority.registerLifecycleSink(BRIDGE)"));
        assertTrue(atmosphere.contains("BRIDGE.flush"));
        assertTrue(mod.contains("VolcanicGasAtmosphereRuntime.register()"));
        assertFalse(authority.contains("registerEruptionSink"));
        assertFalse(atmosphere.contains("registerEruptionSink"));
    }
}
