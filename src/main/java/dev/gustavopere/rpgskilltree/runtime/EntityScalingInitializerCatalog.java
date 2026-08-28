package dev.gustavopere.rpgskilltree.runtime;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/** Runtime holder for the explicitly installed server-authoritative entity scaling initializer. */
public final class EntityScalingInitializerCatalog {
    private static final AtomicReference<EntityScalingInitializer> CURRENT = new AtomicReference<>();

    private EntityScalingInitializerCatalog() {}

    public static Optional<EntityScalingInitializer> current() {
        return Optional.ofNullable(CURRENT.get());
    }

    public static void install(EntityScalingInitializer initializer) {
        CURRENT.set(Objects.requireNonNull(initializer, "initializer"));
    }

    public static void clear() {
        CURRENT.set(null);
    }
}
