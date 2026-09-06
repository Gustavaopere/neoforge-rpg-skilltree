package dev.gustavopere.volcanoes.environment;

import java.util.Objects;

@FunctionalInterface
public interface AtmosphereBaselineProvider {
    AtmosphereState sample(String dimensionId, double y);

    static AtmosphereBaselineProvider standard() {
        return (dimensionId, y) -> {
            Objects.requireNonNull(dimensionId, "dimensionId");
            if (!Double.isFinite(y)) {
                throw new IllegalArgumentException("y must be finite");
            }
            return AtmosphereState.standardOverworld();
        };
    }
}
