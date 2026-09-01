package dev.gustavopere.volcanoes.compat.coldsweat;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ColdSweatCompatContractTest {
    @Test
    void adapterIsPinnedToTheVerifiedNeoForgeArtifact() {
        assertEquals("cold_sweat", ColdSweatCompat.MOD_ID);
        assertEquals("2.4.2", ColdSweatCompat.VERIFIED_ARTIFACT_VERSION);
    }

    @Test
    void absentOrMismatchedHostNeverResolvesTheHostInstaller() {
        AtomicInteger installations = new AtomicInteger();

        assertFalse(ColdSweatCompat.installForState(false, false, installations::incrementAndGet));
        assertFalse(ColdSweatCompat.installForState(true, false, installations::incrementAndGet));
        assertEquals(0, installations.get());
    }

    @Test
    void exactHostInstallsOnceAndOptionalFailuresFailClosed() {
        AtomicInteger installations = new AtomicInteger();
        assertTrue(ColdSweatCompat.installForState(true, true, installations::incrementAndGet));
        assertEquals(1, installations.get());

        assertFalse(ColdSweatCompat.installForState(true, true, () -> {
            throw new IllegalStateException("simulated host runtime failure");
        }));
        assertFalse(ColdSweatCompat.installForState(true, true, () -> {
            throw new NoClassDefFoundError("simulated host linkage failure");
        }));
    }
}
