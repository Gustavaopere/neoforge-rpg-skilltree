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
    void diagnosticGateEmitsOnlyOncePerFailureClass() {
        assertTrue(ColdSweatFrenzyBridge.shouldEmitDiagnostic("test-version"));
        assertFalse(ColdSweatFrenzyBridge.shouldEmitDiagnostic("test-version"));
        assertTrue(ColdSweatFrenzyBridge.shouldEmitDiagnostic("test-api"));
        assertFalse(ColdSweatFrenzyBridge.shouldEmitDiagnostic("test-api"));
    }
}
