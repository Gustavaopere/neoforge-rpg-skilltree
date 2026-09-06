package dev.gustavopere.volcanoes.pressure;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** Loader-neutral entity snapshot consumed once per pressure update. */
public record PressureEntityContext(
        UUID entityId,
        Optional<UUID> vehicleId,
        String dimensionId,
        double x,
        double y,
        double z,
        List<EquippedItemView> equippedItems
) {
    public PressureEntityContext {
        entityId = Objects.requireNonNull(entityId, "entityId");
        vehicleId = Objects.requireNonNull(vehicleId, "vehicleId");
        dimensionId = Objects.requireNonNull(dimensionId, "dimensionId");
        equippedItems = List.copyOf(Objects.requireNonNull(equippedItems, "equippedItems"));
        if (dimensionId.isBlank()) {
            throw new IllegalArgumentException("dimensionId must not be blank");
        }
        if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z)) {
            throw new IllegalArgumentException("coordinates must be finite");
        }
        if (equippedItems.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("equippedItems must not contain null");
        }
    }

    public EnclosedEnvironmentQuery enclosedQuery() {
        return new EnclosedEnvironmentQuery(entityId, vehicleId, dimensionId, x, y, z);
    }

    public EquipmentProtectionContext equipmentContext() {
        return new EquipmentProtectionContext(entityId, equippedItems, vehicleId);
    }
}
