package dev.gustavopere.volcanoes.volcano;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeothermalHeatCapacityContractTest {
    @Test
    void sharedHeatIndexReservesCapacityBeyondPersistentGeothermalAuthority() {
        int worstCaseLiveGeyserPulses = GeothermalWorldgenRuntime.MAX_GEYSER_PULSES_PER_TICK
                * Math.toIntExact(GeothermalWorldgenRuntime.GEYSER_PULSE_HEAT_TTL_TICKS);

        assertEquals(
                worstCaseLiveGeyserPulses + GeothermalWorldgenRuntime.MAX_OTHER_DYNAMIC_HEAT_SOURCES,
                GeothermalWorldgenRuntime.MAX_DYNAMIC_HEAT_SOURCES,
                "dynamic heat reserve must cover every live geyser pulse plus bounded non-geyser producers");
        assertTrue(GeothermalWorldgenRuntime.MAX_OTHER_DYNAMIC_HEAT_SOURCES >= 32,
                "non-geyser reserve must leave explicit bounded headroom for pyroclastic and future transient producers");
        assertEquals(
                GeothermalSourceRegistry.DEFAULT_MAX_SOURCES + GeothermalWorldgenRuntime.MAX_DYNAMIC_HEAT_SOURCES,
                GeothermalWorldgenRuntime.MAX_HEAT_SOURCES,
                "heat index capacity must cover all persistent geothermal sources plus transient reserve");
    }
}
