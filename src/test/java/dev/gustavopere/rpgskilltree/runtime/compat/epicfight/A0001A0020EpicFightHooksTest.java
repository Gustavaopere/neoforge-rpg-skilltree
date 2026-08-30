package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class A0001A0020EpicFightHooksTest {
    @Test
    void supportsOnlyTheExactAuditedEpicFightVersion() {
        assertTrue(EpicFightVersionContract.supportsVersion("21.17.3.1"));
        assertFalse(EpicFightVersionContract.supportsVersion("21.17.3.10"));
        assertFalse(EpicFightVersionContract.supportsVersion("21.17.3.1.1"));
        assertFalse(EpicFightVersionContract.supportsVersion("21.17.3.1-beta"));
        assertFalse(EpicFightVersionContract.supportsVersion("21.17.3.2"));
        assertFalse(EpicFightVersionContract.supportsVersion(null));
    }
}
