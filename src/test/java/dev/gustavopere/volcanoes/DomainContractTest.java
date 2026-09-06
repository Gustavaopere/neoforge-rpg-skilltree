package dev.gustavopere.volcanoes;

import dev.gustavopere.volcanoes.environment.AtmosphereService;
import dev.gustavopere.volcanoes.environment.AtmosphereState;
import dev.gustavopere.volcanoes.geology.RockCategory;
import dev.gustavopere.volcanoes.geology.RockProfile;
import dev.gustavopere.volcanoes.geology.RockProfileResolver;
import dev.gustavopere.volcanoes.pressure.PressureSample;
import dev.gustavopere.volcanoes.pressure.PressureService;
import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicSample;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import dev.gustavopere.volcanoes.volcano.VolcanoService;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

final class DomainContractTest {
    @Test
    void rockProfileValidatesRangesAndProvidesGenericFallback() {
        assertThrows(IllegalArgumentException.class,
                () -> new RockProfile("bad", RockCategory.GENERIC, -0.01, 1.0, 1.0, 0.5));
        assertThrows(IllegalArgumentException.class,
                () -> new RockProfile("bad", RockCategory.GENERIC, 1.01, 1.0, 1.0, 0.5));
        assertThrows(IllegalArgumentException.class,
                () -> new RockProfile("bad", RockCategory.GENERIC, 0.5, -0.01, 1.0, 0.5));
        assertEquals("generic", RockProfile.GENERIC.id());
        assertEquals(RockCategory.GENERIC, RockProfile.GENERIC.category());
    }

    @Test
    void tectonicSampleRepresentsEveryRequiredContextWithoutWorldAccess() {
        EnumSet<TectonicContext> expected = EnumSet.of(
                TectonicContext.INTERIOR,
                TectonicContext.CONVERGENT,
                TectonicContext.DIVERGENT,
                TectonicContext.TRANSFORM,
                TectonicContext.HOTSPOT);
        assertEquals(expected, EnumSet.allOf(TectonicContext.class));
        for (TectonicContext context : expected) {
            TectonicSample sample = new TectonicSample(1L, 2L, context, 0.4, 0.6, 128.0, 0.1, -0.2);
            assertEquals(context, sample.context());
        }
    }

    @Test
    void atmosphereClampsInputsAndDerivesOxygenPartialPressure() {
        AtmosphereState state = AtmosphereState.of(0.8, 1.2, -0.1, -5.0, -2.0, 1.4, 3.0);
        assertEquals(1.0, state.oxygenFraction(), 1.0e-9);
        assertEquals(0.0, state.carbonDioxideFraction(), 1.0e-9);
        assertEquals(0.0, state.sulfurDioxidePpm(), 1.0e-9);
        assertEquals(0.0, state.particulatesMgM3(), 1.0e-9);
        assertEquals(1.0, state.relativeHumidity(), 1.0e-9);
        assertEquals(0.8, state.oxygenPartialPressureAtm(), 1.0e-9);
    }

    @Test
    void pressureSampleSeparatesAtmosphericHydrostaticAndTotalPressure() {
        PressureSample sample = new PressureSample(0.9, 2.4);
        assertEquals(0.9, sample.atmosphericAtm(), 1.0e-9);
        assertEquals(2.4, sample.hydrostaticAtm(), 1.0e-9);
        assertEquals(3.3, sample.totalExternalAtm(), 1.0e-9);
        assertThrows(IllegalArgumentException.class, () -> new PressureSample(-0.1, 0.0));
    }

    @Test
    void baseServicesAreDeterministicAndRequireNoOptionalMods() {
        RockProfileResolver rocks = RockProfileResolver.fallback();
        assertEquals(rocks.resolve(77L, 10, 20, 30), rocks.resolve(77L, 10, 20, 30));
        assertEquals(RockProfile.GENERIC, rocks.resolve(77L, 10, 20, 30));

        TectonicService tectonics = TectonicService.fallback();
        assertEquals(tectonics.sample(77L, 100.0, -200.0), tectonics.sample(77L, 100.0, -200.0));

        AtmosphereService atmosphere = AtmosphereService.fallback();
        assertEquals(atmosphere.sample("minecraft:overworld", 77L, 0.0, 63.0, 0.0),
                atmosphere.sample("minecraft:overworld", 77L, 0.0, 63.0, 0.0));

        PressureService pressure = PressureService.fallback();
        assertEquals(pressure.sample(1.0, 25.0, 1000.0), pressure.sample(1.0, 25.0, 1000.0));

        assertTrue(VolcanoService.fallback().nearest(77L, 0.0, 0.0, 10_000.0).isEmpty());
    }
}
