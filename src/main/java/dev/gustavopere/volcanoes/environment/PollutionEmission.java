package dev.gustavopere.volcanoes.environment;

import java.util.Objects;
import java.util.UUID;

/**
 * One spatial pollution emission routed through exactly one authority.
 *
 * <p>The location remains loader-neutral so optional adapters can map the dimension/coordinates to
 * their own level/chunk APIs without making the Atmosphere core depend on those mods.</p>
 */
public record PollutionEmission(
        UUID id,
        String dimensionId,
        double x,
        double y,
        double z,
        PollutionLoad load
) {
    public PollutionEmission {
        id = Objects.requireNonNull(id, "id");
        dimensionId = Objects.requireNonNull(dimensionId, "dimensionId");
        if (dimensionId.isBlank()) {
            throw new IllegalArgumentException("dimensionId must not be blank");
        }
        x = finite("x", x);
        y = finite("y", y);
        z = finite("z", z);
        load = Objects.requireNonNull(load, "load");
    }

    private static double finite(String name, double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
        return value;
    }
}
