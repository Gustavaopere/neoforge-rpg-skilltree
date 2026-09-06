package dev.gustavopere.volcanoes.compat.destroy;

import dev.gustavopere.volcanoes.environment.PollutionLoad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class DestroyPollutionProjectionContractTest {
    @Test
    void exactFourDestroyChannelsAreMappedWithoutInventingParticulateSupport() {
        PollutionLoad load = new PollutionLoad(0.25, 0.75, 0.5, 0.4, 0.3);
        DestroyPollutionProjection projection = DestroyPollutionProjection.from(load);

        assertEquals(0.25, projection.acidRain(), 1.0e-12);
        assertEquals(0.5, projection.smog(), 1.0e-12);
        assertEquals(0.4, projection.greenhouse(), 1.0e-12);
        assertEquals(0.3, projection.ozoneDepletion(), 1.0e-12);
        assertTrue(projection.hasSupportedLoad());
        assertFalse(projection.mapsParticulates());
    }

    @Test
    void particulateOnlyLoadIsExplicitlyUnsupportedRatherThanFoldedIntoSmog() {
        DestroyPollutionProjection projection = DestroyPollutionProjection.from(
                new PollutionLoad(0.0, 1.0, 0.0, 0.0, 0.0));

        assertFalse(projection.hasSupportedLoad());
        assertEquals(0.0, projection.smog(), 1.0e-12);
        assertFalse(projection.mapsParticulates());
    }

    @Test
    void zeroLoadProjectsToZeroOnEveryDestroyChannel() {
        DestroyPollutionProjection projection = DestroyPollutionProjection.from(PollutionLoad.none());

        assertEquals(0.0, projection.acidRain(), 1.0e-12);
        assertEquals(0.0, projection.smog(), 1.0e-12);
        assertEquals(0.0, projection.greenhouse(), 1.0e-12);
        assertEquals(0.0, projection.ozoneDepletion(), 1.0e-12);
        assertFalse(projection.hasSupportedLoad());
    }
}
