package dev.gustavopere.volcanoes.compat.sable;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SablePressureCompatContractTest {
    @Test
    void adapterIsPinnedToVerifiedTargetPackArtifacts() {
        assertEquals("sable", SablePressureCompat.MOD_ID);
        assertEquals("2.0.5", SablePressureCompat.VERIFIED_ARTIFACT_VERSION);
        assertEquals("aeronautics_bundled", SablePressureCompat.AERONAUTICS_MOD_ID);
        assertEquals("1.3.1", SablePressureCompat.VERIFIED_AERONAUTICS_VERSION);
    }

    @Test
    void absentOrMismatchedSableNeverInstallsAdapter() {
        AtomicInteger installs = new AtomicInteger();
        assertFalse(SablePressureCompat.installForState(false, false, installs::incrementAndGet));
        assertFalse(SablePressureCompat.installForState(true, false, installs::incrementAndGet));
        assertEquals(0, installs.get());
    }

    @Test
    void exactSableInstallsAndOptionalFailureFailsClosed() {
        AtomicInteger installs = new AtomicInteger();
        assertTrue(SablePressureCompat.installForState(true, true, installs::incrementAndGet));
        assertEquals(1, installs.get());
        assertFalse(SablePressureCompat.installForState(true, true, () -> {
            throw new NoClassDefFoundError("simulated Sable API drift");
        }));
    }

    @Test
    void bridgeUsesPublishedSableContainmentProjectionAndPressureApi() throws Exception {
        String source = Files.readString(Path.of(
                "src/main/java/dev/gustavopere/volcanoes/compat/sable/SablePressureIntegration.java"));

        assertTrue(source.contains("Sable.HELPER.getContaining("));
        assertTrue(source.contains("Sable.HELPER.projectOutOfSubLevel("));
        assertTrue(source.contains("DimensionPhysicsData.getAirPressure("));
        assertTrue(source.contains("ContextualAtmosphericPressureRuntime.register("));
        assertFalse(source.contains("java.lang.reflect"));
        assertFalse(source.contains("EnclosedEnvironment.protectedDry"));
    }

    @Test
    void aeronautics131DoesNotPretendToExposeGenericCabinSealState() {
        assertFalse(SablePressureCompat.verifiedAeronauticsHasGenericCabinSealApi());
    }
}
