package dev.gustavopere.volcanoes.environment;

import java.util.Objects;

public record AtmosphereContribution(
        double pressureDeltaAtm,
        double oxygenFractionDelta,
        double carbonDioxideFraction,
        double sulfurDioxidePpm,
        double toxicGasPpm,
        double particulatesMgM3,
        double smokeMgM3,
        double relativeHumidityDelta,
        double thermalModifierDeltaC,
        double oxygenDisplacementFraction
) {
    public AtmosphereContribution {
        requireFinite("pressureDeltaAtm", pressureDeltaAtm);
        requireFinite("oxygenFractionDelta", oxygenFractionDelta);
        carbonDioxideFraction = nonNegative("carbonDioxideFraction", carbonDioxideFraction);
        sulfurDioxidePpm = nonNegative("sulfurDioxidePpm", sulfurDioxidePpm);
        toxicGasPpm = nonNegative("toxicGasPpm", toxicGasPpm);
        particulatesMgM3 = nonNegative("particulatesMgM3", particulatesMgM3);
        smokeMgM3 = nonNegative("smokeMgM3", smokeMgM3);
        requireFinite("relativeHumidityDelta", relativeHumidityDelta);
        requireFinite("thermalModifierDeltaC", thermalModifierDeltaC);
        oxygenDisplacementFraction = unit("oxygenDisplacementFraction", oxygenDisplacementFraction);
    }

    public static AtmosphereContribution none() {
        return new AtmosphereContribution(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public AtmosphereContribution scaled(double factor) {
        if (!Double.isFinite(factor) || factor < 0.0) {
            throw new IllegalArgumentException("factor must be finite and non-negative");
        }
        return new AtmosphereContribution(
                finiteMultiply(pressureDeltaAtm, factor),
                finiteMultiply(oxygenFractionDelta, factor),
                finiteMultiply(carbonDioxideFraction, factor),
                finiteMultiply(sulfurDioxidePpm, factor),
                finiteMultiply(toxicGasPpm, factor),
                finiteMultiply(particulatesMgM3, factor),
                finiteMultiply(smokeMgM3, factor),
                finiteMultiply(relativeHumidityDelta, factor),
                finiteMultiply(thermalModifierDeltaC, factor),
                Math.min(1.0, finiteMultiply(oxygenDisplacementFraction, factor)));
    }

    /**
     * Composes independent local contributions before applying normalization to a baseline.
     * Additive quantities are summed; independent oxygen displacement is combined by multiplying
     * the remaining breathable fraction, making the operation commutative and avoiding
     * source-order-dependent intermediate clamps.
     */
    public AtmosphereContribution combine(AtmosphereContribution other) {
        AtmosphereContribution value = Objects.requireNonNull(other, "other");
        double remainingOxygenFraction =
                (1.0 - oxygenDisplacementFraction) * (1.0 - value.oxygenDisplacementFraction);
        double combinedDisplacement = Math.max(0.0, Math.min(1.0, 1.0 - remainingOxygenFraction));
        return new AtmosphereContribution(
                finiteAdd(pressureDeltaAtm, value.pressureDeltaAtm),
                finiteAdd(oxygenFractionDelta, value.oxygenFractionDelta),
                finiteAdd(carbonDioxideFraction, value.carbonDioxideFraction),
                finiteAdd(sulfurDioxidePpm, value.sulfurDioxidePpm),
                finiteAdd(toxicGasPpm, value.toxicGasPpm),
                finiteAdd(particulatesMgM3, value.particulatesMgM3),
                finiteAdd(smokeMgM3, value.smokeMgM3),
                finiteAdd(relativeHumidityDelta, value.relativeHumidityDelta),
                finiteAdd(thermalModifierDeltaC, value.thermalModifierDeltaC),
                combinedDisplacement);
    }

    public AtmosphereState applyTo(AtmosphereState baseline) {
        AtmosphereState value = Objects.requireNonNull(baseline, "baseline");
        double oxygen = finiteAdd(value.oxygenFraction(), oxygenFractionDelta)
                * (1.0 - oxygenDisplacementFraction);
        return new AtmosphereState(
                finiteAdd(value.totalPressureAtm(), pressureDeltaAtm),
                oxygen,
                finiteAdd(value.carbonDioxideFraction(), carbonDioxideFraction),
                finiteAdd(value.sulfurDioxidePpm(), sulfurDioxidePpm),
                finiteAdd(value.toxicGasPpm(), toxicGasPpm),
                finiteAdd(value.particulatesMgM3(), particulatesMgM3),
                finiteAdd(value.smokeMgM3(), smokeMgM3),
                finiteAdd(value.relativeHumidity(), relativeHumidityDelta),
                finiteAdd(value.thermalModifierC(), thermalModifierDeltaC));
    }

    private static double finiteAdd(double left, double right) {
        double sum = left + right;
        if (Double.isFinite(sum)) {
            return sum;
        }
        return sum > 0.0 ? Double.MAX_VALUE : -Double.MAX_VALUE;
    }

    private static double finiteMultiply(double left, double right) {
        double product = left * right;
        if (Double.isFinite(product)) {
            return product;
        }
        return product > 0.0 ? Double.MAX_VALUE : -Double.MAX_VALUE;
    }

    private static void requireFinite(String name, double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
    }

    private static double nonNegative(String name, double value) {
        requireFinite(name, value);
        if (value < 0.0) {
            throw new IllegalArgumentException(name + " must be non-negative");
        }
        return value;
    }

    private static double unit(String name, double value) {
        requireFinite(name, value);
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException(name + " must be within [0, 1]");
        }
        return value;
    }
}
