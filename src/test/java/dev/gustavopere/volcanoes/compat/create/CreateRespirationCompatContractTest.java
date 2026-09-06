package dev.gustavopere.volcanoes.compat.create;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CreateRespirationCompatContractTest {
    @Test
    void adapterIsPinnedToVerifiedCreateArtifact() {
        assertEquals("create", CreateRespirationCompat.MOD_ID);
        assertEquals("6.0.10", CreateRespirationCompat.VERIFIED_ARTIFACT_VERSION);
    }

    @Test
    void absentOrMismatchedHostNeverResolvesInstaller() {
        AtomicInteger installs = new AtomicInteger();
        assertFalse(CreateRespirationCompat.installForState(false, false, installs::incrementAndGet));
        assertFalse(CreateRespirationCompat.installForState(true, false, installs::incrementAndGet));
        assertEquals(0, installs.get());
    }

    @Test
    void exactHostInstallsOnceAndOptionalFailuresFailClosed() {
        AtomicInteger installs = new AtomicInteger();
        assertTrue(CreateRespirationCompat.installForState(true, true, installs::incrementAndGet));
        assertEquals(1, installs.get());

        assertFalse(CreateRespirationCompat.installForState(true, true, () -> {
            throw new IllegalStateException("simulated host runtime failure");
        }));
        assertFalse(CreateRespirationCompat.installForState(true, true, () -> {
            throw new NoClassDefFoundError("simulated host linkage failure");
        }));
    }

    @Test
    void verifiedHostBridgeUsesCreateNativeAirSurfaceAndCanonicalProtectionTransaction() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/compat/create/CreateRespirationIntegration.java"));

        assertTrue(source.contains("DivingHelmetItem.isWornBy("));
        assertTrue(source.contains("BacktankUtil.getAllWithAir("));
        assertTrue(source.contains("BacktankUtil.consumeAir("));
        assertTrue(source.contains("ProtectionCapability.OXYGEN_SUPPLY"));
        assertTrue(source.contains("resourceDebitKey"));
        assertTrue(source.contains("ProtectionUseRegistry"));
        assertFalse(source.contains("ProtectionCapability.PARTICULATE_FILTER"));
        assertFalse(source.contains("ProtectionCapability.ACID_GAS_FILTER"));
        assertFalse(source.contains("ProtectionCapability.TOXIC_GAS_FILTER"));
    }

    @Test
    void atmosphereBreathingRunsAfterCreateNativeNormalPriorityListener() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java"));

        assertTrue(source.contains("EventPriority.LOWEST"));
        assertTrue(source.contains(
                "NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, AtmosphereRuntime::onLivingBreathe);"),
                "Atmosphere must run after Create's default NORMAL LivingBreatheEvent listener so "
                        + "Create sees normal above-water breathing before Atmosphere applies hypoxia, preventing "
                        + "the native Create listener and the Atmosphere transaction from both debiting backtank air");
    }
}
