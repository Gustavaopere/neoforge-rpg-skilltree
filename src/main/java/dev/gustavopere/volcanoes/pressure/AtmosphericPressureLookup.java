package dev.gustavopere.volcanoes.pressure;

/** Loader-neutral atmospheric pressure lookup used by entity pressure coordination. */
@FunctionalInterface
public interface AtmosphericPressureLookup {
    double pressureAtm(String dimensionId, double altitudeY);
}
