package dev.gustavopere.volcanoes.pressure;

/** One altitude/pressure sample for a data-driven atmospheric curve. Pressure is expressed in atmospheres. */
public record PressureControlPoint(double altitudeY, double pressureAtm) {
    public PressureControlPoint {
        if (!Double.isFinite(altitudeY)) {
            throw new IllegalArgumentException("altitudeY must be finite");
        }
        if (!Double.isFinite(pressureAtm) || pressureAtm < 0.0) {
            throw new IllegalArgumentException("pressureAtm must be finite and non-negative");
        }
    }
}
