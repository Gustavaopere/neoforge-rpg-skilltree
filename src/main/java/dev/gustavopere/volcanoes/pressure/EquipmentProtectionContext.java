package dev.gustavopere.volcanoes.pressure;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** Immutable equipment snapshot passed to protection adapters once per resolution. */
public record EquipmentProtectionContext(
        UUID entityId,
        List<EquippedItemView> equippedItems,
        Optional<UUID> vehicleId
) {
    public EquipmentProtectionContext {
        Objects.requireNonNull(entityId, "entityId");
        Objects.requireNonNull(equippedItems, "equippedItems");
        Objects.requireNonNull(vehicleId, "vehicleId");
        equippedItems = List.copyOf(equippedItems);
        if (equippedItems.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("equippedItems must not contain null");
        }
    }
}
