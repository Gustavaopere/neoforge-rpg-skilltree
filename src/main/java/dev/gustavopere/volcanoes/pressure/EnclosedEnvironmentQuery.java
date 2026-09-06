package dev.gustavopere.volcanoes.pressure;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** Stable provider input without any dependency on a concrete vehicle mod. */
public record EnclosedEnvironmentQuery(
        UUID entityId,
        Optional<UUID> vehicleId,
        String dimensionId,
        double x,
        double y,
        double z
) {
    public EnclosedEnvironmentQuery {
        entityId = Objects.requireNonNull(entityId, "entityId");
        vehicleId = Objects.requireNonNull(vehicleId, "vehicleId");
        dimensionId = Objects.requireNonNull(dimensionId, "dimensionId");
        if (dimensionId.isBlank()) {
            throw new IllegalArgumentException("dimensionId must not be blank");
        }
        if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z)) {
            throw new IllegalArgumentException("coordinates must be finite");
        }
    }
}
