package dev.gustavopere.rpgskilltree.runtime;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/** Runtime holder for the explicitly installed server-authoritative entity scaling state applier. */
public final class EntityScalingStateApplierCatalog {
    private static final AtomicReference<EntityScalingStateApplier> CURRENT = new AtomicReference<>();

    private EntityScalingStateApplierCatalog() {}

    public static Optional<EntityScalingStateApplier> current() {
        return Optional.ofNullable(CURRENT.get());
    }

    public static void install(EntityScalingStateApplier applier) {
        CURRENT.set(Objects.requireNonNull(applier, "applier"));
    }

    public static void clear() {
        CURRENT.set(null);
    }
}
