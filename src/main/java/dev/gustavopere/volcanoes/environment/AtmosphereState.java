package dev.gustavopere.volcanoes.environment;

/**
 * Immutable environmental gas/particle state. Fractions are normalized to [0,1];
 * concentrations are non-negative. Pressure is expressed in atmospheres.
 */
public record AtmosphereState(
        double totalPressureAtm,
        double oxygenFraction,
        double carbonDioxideFraction,
        double sulfurDioxidePpm,
        double toxicGasPpm,
        double particulatesMgM3,
        double smokeMgM3,
        double relativeHumidity,
        double thermalModifierC
) {
    public AtmosphereState {
        totalPressureAtm = nonNegative("totalPressureAtm", totalPressureAtm);
        oxygenFraction = unit(oxygenFraction);
        carbonDioxideFraction = unit(carbonDioxideFraction);
        sulfurDioxidePpm = nonNegative("sulfurDioxidePpm", sulfurDioxidePpm);
        toxicGasPpm = nonNegative("toxicGasPpm", toxicGasPpm);
        particulatesMgM3 = nonNegative("particulatesMgM3", particulatesMgM3);
        smokeMgM3 = nonNegative("smokeMgM3", smokeMgM3);
        relativeHumidity = unit(relativeHumidity);
        if (!Double.isFinite(thermalModifierC)) {
            throw new IllegalArgumentException("thermalModifierC must be finite");
        }
    }

    /** Convenience constructor used by the Foundation contract; toxic gas and smoke default to zero. */
    public static AtmosphereState of(
            double totalPressureAtm,
            double oxygenFraction,
            double carbonDioxideFraction,
            double sulfurDioxidePpm,
            double particulatesMgM3,
            double relativeHumidity,
            double thermalModifierC
    ) {
        return new AtmosphereState(
                totalPressureAtm,
                oxygenFraction,
                carbonDioxideFraction,
                sulfurDioxidePpm,
                0.0,
                particulatesMgM3,
                0.0,
                relativeHumidity,
                thermalModifierC);
    }

    public static AtmosphereState standardOverworld() {
        return new AtmosphereState(1.0, 0.2095, 0.00042, 0.0, 0.0, 0.0, 0.0, 0.5, 0.0);
    }

    public double oxygenPartialPressureAtm() {
        return totalPressureAtm * oxygenFraction;
    }

    private static double unit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("fraction must be finite");
        }
        return Math.max(0.0, Math.min(1.0, value));
    }

    private static double nonNegative(String name, double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
        return Math.max(0.0, value);
    }
}
