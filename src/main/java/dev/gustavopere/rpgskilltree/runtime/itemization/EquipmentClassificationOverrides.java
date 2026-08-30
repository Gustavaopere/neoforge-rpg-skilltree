package dev.gustavopere.rpgskilltree.runtime.itemization;

import dev.gustavopere.rpgskilltree.itemization.classification.EquipmentOverrideCatalog;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Process-local publication point for the currently active data-driven equipment overrides.
 * Readers always observe one complete immutable catalog snapshot.
 */
public final class EquipmentClassificationOverrides {
    private static final AtomicReference<EquipmentOverrideCatalog> ACTIVE =
        new AtomicReference<>(EquipmentOverrideCatalog.empty());

    private EquipmentClassificationOverrides() {}

    public static EquipmentOverrideCatalog snapshot() {
        return ACTIVE.get();
    }

    public static void replace(EquipmentOverrideCatalog catalog) {
        ACTIVE.set(Objects.requireNonNull(catalog, "catalog"));
    }
}
