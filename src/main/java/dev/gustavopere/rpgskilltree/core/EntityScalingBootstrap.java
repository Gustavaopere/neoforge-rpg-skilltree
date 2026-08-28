package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/** Idempotent pure bootstrap boundary for one entity's scaling lifecycle state. */
public final class EntityScalingBootstrap {
    private EntityScalingBootstrap() {}

    public static EntityScalingState resumeOrInitialize(
        Optional<EntityScalingState> existing,
        Supplier<EntityScalingState> initializer
    ) {
        Objects.requireNonNull(existing, "existing");
        Objects.requireNonNull(initializer, "initializer");
        if (existing.isPresent()) {
            return existing.orElseThrow();
        }
        EntityScalingState initialized = initializer.get();
        if (initialized == null) {
            throw new IllegalStateException("entity scaling initializer returned null");
        }
        return initialized;
    }
}
