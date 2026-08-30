package dev.gustavopere.rpgskilltree.runtime.events;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class A0081A0100CombatEventsTest {
    @Test
    void previousBatchStationarySamplerUsesExactEpicFightVersionContract() {
        assertTrue(A0081A0100CombatEvents.previousBatchSamplesStationaryVersion("21.17.3.1"));
        assertFalse(A0081A0100CombatEvents.previousBatchSamplesStationaryVersion("21.17.3.10"));
        assertFalse(A0081A0100CombatEvents.previousBatchSamplesStationaryVersion("21.17.3.1.1"));
        assertFalse(A0081A0100CombatEvents.previousBatchSamplesStationaryVersion("21.17.3.1-beta"));
        assertFalse(A0081A0100CombatEvents.previousBatchSamplesStationaryVersion("21.17.3.2"));
        assertFalse(A0081A0100CombatEvents.previousBatchSamplesStationaryVersion(null));
    }
}
