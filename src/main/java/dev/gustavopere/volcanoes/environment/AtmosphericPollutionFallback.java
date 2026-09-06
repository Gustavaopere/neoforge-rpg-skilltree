package dev.gustavopere.volcanoes.environment;

public final class AtmosphericPollutionFallback {
    private final double acidifyingToSulfurDioxidePpm;
    private final double particulateScale;
    private final double smogScale;

    public AtmosphericPollutionFallback(
            double acidifyingToSulfurDioxidePpm,
            double particulateScale,
            double smogScale
    ) {
        this.acidifyingToSulfurDioxidePpm = nonNegative("acidifyingToSulfurDioxidePpm", acidifyingToSulfurDioxidePpm);
        this.particulateScale = nonNegative("particulateScale", particulateScale);
        this.smogScale = nonNegative("smogScale", smogScale);
    }

    public AtmosphereContribution contributionFor(PollutionLoad load) {
        if (load == null) {
            throw new NullPointerException("load");
        }
        return new AtmosphereContribution(
                0.0,
                0.0,
                0.0,
                finiteMultiply(load.acidifyingLoad(), acidifyingToSulfurDioxidePpm),
                0.0,
                finiteMultiply(load.particulateLoad(), particulateScale),
                finiteMultiply(load.smogLoad(), smogScale),
                0.0,
                0.0,
                0.0);
    }

    private static double finiteMultiply(double left, double right) {
        double product = left * right;
        return Double.isFinite(product) ? product : Double.MAX_VALUE;
    }

    private static double nonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
        return value;
    }
}
