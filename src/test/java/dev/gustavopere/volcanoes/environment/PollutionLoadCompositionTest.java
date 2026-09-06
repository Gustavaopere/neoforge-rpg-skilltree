package dev.gustavopere.volcanoes.environment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PollutionLoadCompositionTest {
    @Test
    void finiteLoadsRemainFiniteWhenComposedAtNumericLimits() {
        PollutionLoad extreme = new PollutionLoad(
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE);

        PollutionLoad combined = assertDoesNotThrow(() -> extreme.plus(extreme),
                "two individually valid finite pollution loads must remain composable");

        assertEquals(Double.MAX_VALUE, combined.acidifyingLoad());
        assertEquals(Double.MAX_VALUE, combined.particulateLoad());
        assertEquals(Double.MAX_VALUE, combined.smogLoad());
        assertEquals(Double.MAX_VALUE, combined.greenhouseLoad());
        assertEquals(Double.MAX_VALUE, combined.ozoneAffectingLoad());
        assertTrue(Double.isFinite(combined.acidifyingLoad()));
        assertTrue(Double.isFinite(combined.particulateLoad()));
        assertTrue(Double.isFinite(combined.smogLoad()));
        assertTrue(Double.isFinite(combined.greenhouseLoad()));
        assertTrue(Double.isFinite(combined.ozoneAffectingLoad()));
    }
}
