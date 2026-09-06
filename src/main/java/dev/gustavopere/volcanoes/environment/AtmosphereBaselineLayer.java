package dev.gustavopere.volcanoes.environment;

import java.util.Objects;

/** One configured atmosphere baseline selected from a minimum world altitude upward. */
public record AtmosphereBaselineLayer(double minimumY, AtmosphereState state) {
    public AtmosphereBaselineLayer {
        if (!Double.isFinite(minimumY)) {
            throw new IllegalArgumentException("minimumY must be finite");
        }
        state = Objects.requireNonNull(state, "state");
    }
}
