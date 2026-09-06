package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class HydrostaticPressureTest {
    @Test
    void surfaceModerateAndDeepWaterUseConfiguredGravityAndDensity() {
        PressureService earth = PressureService.withGravity(9.80665);

        PressureSample surface = earth.sample(1.0, 0.0, 1000.0);
        PressureSample moderate = earth.sample(1.0, 10.0, 1000.0);
        PressureSample deep = earth.sample(1.0, 100.0, 1000.0);

        assertEquals(0.0, surface.hydrostaticAtm(), 1.0e-12);
        assertEquals(1000.0 * 9.80665 * 10.0 / PressureService.PASCALS_PER_ATMOSPHERE,
                moderate.hydrostaticAtm(), 1.0e-12);
        assertTrue(deep.totalExternalAtm() > moderate.totalExternalAtm());
    }

    @Test
    void configuredDensityAndGravityChangeHydrostaticPressureMonotonically() {
        double depth = 30.0;
        double lowDensity = PressureService.withGravity(9.80665).sample(1.0, depth, 900.0).hydrostaticAtm();
        double highDensity = PressureService.withGravity(9.80665).sample(1.0, depth, 1200.0).hydrostaticAtm();
        double lowGravity = PressureService.withGravity(4.0).sample(1.0, depth, 1000.0).hydrostaticAtm();
        double highGravity = PressureService.withGravity(12.0).sample(1.0, depth, 1000.0).hydrostaticAtm();

        assertTrue(highDensity > lowDensity);
        assertTrue(highGravity > lowGravity);
        assertThrows(IllegalArgumentException.class, () -> PressureService.withGravity(-1.0));
    }
}
