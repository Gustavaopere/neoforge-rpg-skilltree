package dev.gustavopere.volcanoes.compat;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ExactModVersionGateTest {
    @Test
    void absentModFailsClosed() {
        assertFalse(ExactModVersionGate.matches(Optional.empty(), "1.2.3"));
    }

    @Test
    void versionMismatchFailsClosed() {
        assertFalse(ExactModVersionGate.matches(Optional.of("1.2.4"), "1.2.3"));
    }

    @Test
    void exactVersionIsAccepted() {
        assertTrue(ExactModVersionGate.matches(Optional.of("1.2.3"), "1.2.3"));
    }

    @Test
    void qualifiersAreComparedExactly() {
        assertTrue(ExactModVersionGate.matches(
                Optional.of("1.1.1374-1.21.1-snapshot"),
                "1.1.1374-1.21.1-snapshot"));
        assertFalse(ExactModVersionGate.matches(
                Optional.of("1.1.1375-1.21.1-snapshot"),
                "1.1.1374-1.21.1-snapshot"));
    }
}
