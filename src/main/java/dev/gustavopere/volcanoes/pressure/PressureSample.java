package dev.gustavopere.volcanoes.pressure;

/** Pressure components expressed in atmospheres. */
public record PressureSample(double atmosphericAtm, double hydrostaticAtm) {
    public PressureSample {
        if (!Double.isFinite(atmosphericAtm) || atmosphericAtm < 0.0) {
            throw new IllegalArgumentException("atmosphericAtm must be finite and non-negative");
        }
        if (!Double.isFinite(hydrostaticAtm) || hydrostaticAtm < 0.0) {
            throw new IllegalArgumentException("hydrostaticAtm must be finite and non-negative");
        }
    }

    public double totalExternalAtm() {
        double total = atmosphericAtm + hydrostaticAtm;
        if (!Double.isFinite(total)) {
            throw new IllegalStateException("total external pressure overflowed finite atm range");
        }
        return total;
    }
}
