package dev.gustavopere.volcanoes.environment;

import java.util.Objects;

public record RespirationProtection(
        double particulateFilterEfficiency,
        double acidGasFilterEfficiency,
        double toxicGasFilterEfficiency,
        double oxygenSupplyPartialPressureAtm
) {
    public static final RespirationProtection NONE = new RespirationProtection(0.0, 0.0, 0.0, 0.0);

    public RespirationProtection {
        particulateFilterEfficiency = unit("particulateFilterEfficiency", particulateFilterEfficiency);
        acidGasFilterEfficiency = unit("acidGasFilterEfficiency", acidGasFilterEfficiency);
        toxicGasFilterEfficiency = unit("toxicGasFilterEfficiency", toxicGasFilterEfficiency);
        if (!Double.isFinite(oxygenSupplyPartialPressureAtm) || oxygenSupplyPartialPressureAtm < 0.0) {
            throw new IllegalArgumentException("oxygenSupplyPartialPressureAtm must be finite and non-negative");
        }
    }

    public static RespirationProtection of(
            double particulateFilterEfficiency,
            double acidGasFilterEfficiency,
            double toxicGasFilterEfficiency,
            double oxygenSupplyPartialPressureAtm
    ) {
        return new RespirationProtection(
                particulateFilterEfficiency,
                acidGasFilterEfficiency,
                toxicGasFilterEfficiency,
                oxygenSupplyPartialPressureAtm);
    }

    /**
     * Combines independent protection capabilities without double-counting overlapping equipment.
     * The strongest capability wins per channel; particulate filtration and oxygen supply remain
     * distinct dimensions and are never inferred from each other.
     */
    public RespirationProtection combine(RespirationProtection other) {
        RespirationProtection value = Objects.requireNonNull(other, "other");
        return new RespirationProtection(
                Math.max(particulateFilterEfficiency, value.particulateFilterEfficiency),
                Math.max(acidGasFilterEfficiency, value.acidGasFilterEfficiency),
                Math.max(toxicGasFilterEfficiency, value.toxicGasFilterEfficiency),
                Math.max(oxygenSupplyPartialPressureAtm, value.oxygenSupplyPartialPressureAtm));
    }

    private static double unit(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0, 1]");
        }
        return value;
    }
}
