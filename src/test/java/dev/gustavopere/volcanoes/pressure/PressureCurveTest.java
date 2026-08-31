package dev.gustavopere.volcanoes.pressure;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PressureCurveTest {
    @Test
    void exactControlPointsAndLinearInterpolationUseAtmospheres() {
        PressureCurve curve = new PressureCurve(List.of(
                new PressureControlPoint(-64.0, 1.15),
                new PressureControlPoint(63.0, 1.00),
                new PressureControlPoint(128.0, 0.85),
                new PressureControlPoint(256.0, 0.60)));

        assertEquals(1.00, curve.pressureAtm(63.0), 1.0e-9);
        assertEquals(0.85, curve.pressureAtm(128.0), 1.0e-9);
        assertEquals(0.925, curve.pressureAtm(95.5), 1.0e-9);
    }

    @Test
    void curveIsMonotonicAndClampsOutsideConfiguredAltitudeRange() {
        PressureCurve curve = new PressureCurve(List.of(
                new PressureControlPoint(-64.0, 1.15),
                new PressureControlPoint(63.0, 1.00),
                new PressureControlPoint(128.0, 0.85),
                new PressureControlPoint(256.0, 0.60),
                new PressureControlPoint(512.0, 0.35)));

        double previous = curve.pressureAtm(-128.0);
        for (int y = -127; y <= 640; y++) {
            double current = curve.pressureAtm(y);
            assertTrue(current <= previous + 1.0e-12, "pressure increased at y=" + y);
            previous = current;
        }
        assertEquals(1.15, curve.pressureAtm(-10_000.0), 1.0e-9);
        assertEquals(0.35, curve.pressureAtm(10_000.0), 1.0e-9);
    }

    @Test
    void malformedCurvesFailClosedInsteadOfSilentlyReorderingOrIncreasing() {
        assertThrows(IllegalArgumentException.class, () -> new PressureCurve(List.of()));
        assertThrows(IllegalArgumentException.class, () -> new PressureCurve(List.of(
                new PressureControlPoint(63.0, 1.0),
                new PressureControlPoint(63.0, 0.9))));
        assertThrows(IllegalArgumentException.class, () -> new PressureCurve(List.of(
                new PressureControlPoint(63.0, 1.0),
                new PressureControlPoint(128.0, 1.1))));
        assertThrows(IllegalArgumentException.class, () -> new PressureCurve(List.of(
                new PressureControlPoint(63.0, 1.0),
                new PressureControlPoint(128.0, Math.nextUp(1.0)))));
        assertThrows(IllegalArgumentException.class,
                () -> new PressureControlPoint(Double.NaN, 1.0));
        assertThrows(IllegalArgumentException.class,
                () -> new PressureControlPoint(63.0, -0.1));
    }
}
