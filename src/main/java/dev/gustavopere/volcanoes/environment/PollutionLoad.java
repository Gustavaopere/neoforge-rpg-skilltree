package dev.gustavopere.volcanoes.environment;

import java.util.Objects;

public record PollutionLoad(
        double acidifyingLoad,
        double particulateLoad,
        double smogLoad,
        double greenhouseLoad,
        double ozoneAffectingLoad
) {
    public PollutionLoad {
        acidifyingLoad = nonNegative("acidifyingLoad", acidifyingLoad);
        particulateLoad = nonNegative("particulateLoad", particulateLoad);
        smogLoad = nonNegative("smogLoad", smogLoad);
        greenhouseLoad = nonNegative("greenhouseLoad", greenhouseLoad);
        ozoneAffectingLoad = nonNegative("ozoneAffectingLoad", ozoneAffectingLoad);
    }

    public PollutionLoad(double acidifyingLoad, double particulateLoad, double smogLoad) {
        this(acidifyingLoad, particulateLoad, smogLoad, 0.0, 0.0);
    }

    public static PollutionLoad none() {
        return new PollutionLoad(0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public PollutionLoad plus(PollutionLoad other) {
        PollutionLoad value = Objects.requireNonNull(other, "other");
        return new PollutionLoad(
                finiteAdd(acidifyingLoad, value.acidifyingLoad),
                finiteAdd(particulateLoad, value.particulateLoad),
                finiteAdd(smogLoad, value.smogLoad),
                finiteAdd(greenhouseLoad, value.greenhouseLoad),
                finiteAdd(ozoneAffectingLoad, value.ozoneAffectingLoad));
    }

    private static double finiteAdd(double left, double right) {
        double sum = left + right;
        return Double.isFinite(sum) ? sum : Double.MAX_VALUE;
    }

    private static double nonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
        return value;
    }
}
