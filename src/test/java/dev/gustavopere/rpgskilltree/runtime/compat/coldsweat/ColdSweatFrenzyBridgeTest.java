package dev.gustavopere.rpgskilltree.runtime.compat.coldsweat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ColdSweatFrenzyBridgeTest {
    @Test
    void supportsOnlyTheExactAuditedColdSweatVersion() {
        assertTrue(ColdSweatFrenzyBridge.supportsVersion("2.4.2"));
        assertFalse(ColdSweatFrenzyBridge.supportsVersion("2.4.20"));
        assertFalse(ColdSweatFrenzyBridge.supportsVersion("2.4.2.1"));
        assertFalse(ColdSweatFrenzyBridge.supportsVersion("2.4.2-beta"));
        assertFalse(ColdSweatFrenzyBridge.supportsVersion("2.4.1"));
        assertFalse(ColdSweatFrenzyBridge.supportsVersion(null));
    }

    @Test
    void diagnosticGateEmitsOnlyOncePerKey() {
        String key = "test-" + System.nanoTime();
        assertTrue(ColdSweatFrenzyBridge.shouldEmitDiagnostic(key));
        assertFalse(ColdSweatFrenzyBridge.shouldEmitDiagnostic(key));
        assertTrue(ColdSweatFrenzyBridge.shouldEmitDiagnostic(key + "-other"));
    }
}
