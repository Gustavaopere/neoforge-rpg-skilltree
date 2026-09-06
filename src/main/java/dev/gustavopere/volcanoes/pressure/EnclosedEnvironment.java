package dev.gustavopere.volcanoes.pressure;

import dev.gustavopere.volcanoes.environment.AtmosphereState;

import java.util.Objects;
import java.util.Optional;

/** Provider-reported interior state. Only a reliable, sealed and dry state protects from outside pressure. */
public record EnclosedEnvironment(
        boolean sealed,
        boolean dry,
        boolean hostStateReliable,
        double internalPressureAtm,
        Optional<AtmosphereState> internalAtmosphere
) {
    public EnclosedEnvironment {
        if (!Double.isFinite(internalPressureAtm) || internalPressureAtm < 0.0) {
            throw new IllegalArgumentException("internalPressureAtm must be finite and non-negative");
        }
        internalAtmosphere = Objects.requireNonNull(internalAtmosphere, "internalAtmosphere");
    }

    public static EnclosedEnvironment protectedDry(
            double internalPressureAtm,
            Optional<AtmosphereState> internalAtmosphere
    ) {
        return new EnclosedEnvironment(true, true, true, internalPressureAtm, internalAtmosphere);
    }

    public boolean protectsFromExternalPressure() {
        return sealed && dry && hostStateReliable;
    }
}
