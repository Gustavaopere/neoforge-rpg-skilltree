package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class A0079StationaryStateServiceJUnitTest {
    @Test
    void becomesStationaryExactlyOnThirtiethConsecutiveSample() {
        StationaryStateService service = new StationaryStateService();
        String actor = "player";
        assertFalse(service.sample(actor, 0.0D, 64.0D, 0.0D, false));
        for (int sample = 2; sample <= 29; sample++) {
            assertFalse(service.sample(actor, 0.0D, 64.0D, 0.0D, false), "sample=" + sample);
        }
        assertTrue(service.sample(actor, 0.0D, 64.0D, 0.0D, false));
        assertTrue(service.isStationary(actor));
    }

    @Test
    void exactPointTenPathIsAcceptedButAnyExcessResetsProgress() {
        StationaryStateService service = new StationaryStateService();
        String actor = "player";
        assertFalse(service.sample(actor, 0.0D, 0.0D, 0.0D, false));
        assertFalse(service.sample(actor, 0.10D, 0.0D, 0.0D, false));
        for (int sample = 3; sample <= 30; sample++) {
            boolean stationary = service.sample(actor, 0.10D, 0.0D, 0.0D, false);
            if (sample < 30) assertFalse(stationary, "sample=" + sample);
            else assertTrue(stationary);
        }

        assertFalse(service.sample(actor, 0.1000001D, 0.0D, 0.0D, false));
        assertFalse(service.isStationary(actor));
    }

    @Test
    void forcedTransitionInvalidatesImmediatelyAndRestartsTheWindow() {
        StationaryStateService service = new StationaryStateService();
        String actor = "player";
        for (int sample = 1; sample <= 30; sample++) {
            service.sample(actor, 1.0D, 2.0D, 3.0D, false);
        }
        assertTrue(service.isStationary(actor));

        assertFalse(service.sample(actor, 1.0D, 2.0D, 3.0D, true));
        assertFalse(service.isStationary(actor));
        for (int sample = 1; sample <= 29; sample++) {
            assertFalse(service.sample(actor, 1.0D, 2.0D, 3.0D, false), "restart sample=" + sample);
        }
        assertTrue(service.sample(actor, 1.0D, 2.0D, 3.0D, false));
    }
}
