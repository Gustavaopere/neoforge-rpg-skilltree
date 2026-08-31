package dev.gustavopere.volcanoes.environment;

public record RespirationThresholds(
        double minimumOxygenPartialPressureAtm,
        double severeOxygenPartialPressureAtm,
        double carbonDioxideFraction,
        double severeCarbonDioxideFraction,
        double acidGasPpm,
        double toxicGasPpm,
        double particulatesMgM3,
        double smokeMgM3,
        int refillAirAmount,
        int consumeAirAmount,
        int severeConsumeAirAmount
) {
    public RespirationThresholds {
        positive("minimumOxygenPartialPressureAtm", minimumOxygenPartialPressureAtm);
        nonNegative("severeOxygenPartialPressureAtm", severeOxygenPartialPressureAtm);
        if (severeOxygenPartialPressureAtm > minimumOxygenPartialPressureAtm) {
            throw new IllegalArgumentException("severe oxygen threshold cannot exceed minimum oxygen threshold");
        }
        fractionThreshold("carbonDioxideFraction", carbonDioxideFraction);
        fractionThreshold("severeCarbonDioxideFraction", severeCarbonDioxideFraction);
        if (severeCarbonDioxideFraction < carbonDioxideFraction) {
            throw new IllegalArgumentException("severe CO2 threshold cannot be below CO2 threshold");
        }
        positive("acidGasPpm", acidGasPpm);
        positive("toxicGasPpm", toxicGasPpm);
        positive("particulatesMgM3", particulatesMgM3);
        positive("smokeMgM3", smokeMgM3);
        if (refillAirAmount <= 0 || consumeAirAmount <= 0 || severeConsumeAirAmount < consumeAirAmount) {
            throw new IllegalArgumentException("air amounts must be positive and severe consumption must be >= normal");
        }
    }

    public static RespirationThresholds defaults() {
        return new RespirationThresholds(
                0.16,
                0.10,
                0.02,
                0.10,
                10.0,
                25.0,
                5.0,
                5.0,
                4,
                1,
                3);
    }

    private static void positive(String name, double value) {
        if (!Double.isFinite(value) || value <= 0.0) {
            throw new IllegalArgumentException(name + " must be finite and positive");
        }
    }

    private static void fractionThreshold(String name, double value) {
        if (!Double.isFinite(value) || value <= 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within (0, 1]");
        }
    }

    private static void nonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
    }
}
