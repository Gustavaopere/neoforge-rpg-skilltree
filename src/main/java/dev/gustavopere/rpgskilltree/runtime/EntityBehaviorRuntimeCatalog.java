package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.EntityBehaviorKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/** Atomic runtime registry of explicitly installed behavior-package reconcilers. */
public final class EntityBehaviorRuntimeCatalog {
    private static final AtomicReference<Map<EntityBehaviorKey, EntityBehaviorReconciler>> CURRENT =
        new AtomicReference<>(Map.of());

    private EntityBehaviorRuntimeCatalog() {}

    public static void install(Map<EntityBehaviorKey, EntityBehaviorReconciler> reconcilers) {
        Objects.requireNonNull(reconcilers, "reconcilers");
        HashMap<EntityBehaviorKey, EntityBehaviorReconciler> copy = new HashMap<>();
        for (Map.Entry<EntityBehaviorKey, EntityBehaviorReconciler> entry : reconcilers.entrySet()) {
            copy.put(
                Objects.requireNonNull(entry.getKey(), "behavior key"),
                Objects.requireNonNull(entry.getValue(), "behavior reconciler")
            );
        }
        CURRENT.set(Map.copyOf(copy));
    }

    public static Optional<EntityBehaviorReconciler> current(EntityBehaviorKey key) {
        Objects.requireNonNull(key, "key");
        return Optional.ofNullable(CURRENT.get().get(key));
    }

    public static Map<EntityBehaviorKey, EntityBehaviorReconciler> snapshot() {
        return CURRENT.get();
    }

    public static void clear() {
        CURRENT.set(Map.of());
    }
}
