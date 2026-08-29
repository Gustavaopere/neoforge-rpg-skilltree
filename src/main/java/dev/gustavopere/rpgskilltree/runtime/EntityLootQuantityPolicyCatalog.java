package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CappedEntityLootQuantityPolicy;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/** Runtime holder for the explicitly installed bounded loot-quantity policy. */
public final class EntityLootQuantityPolicyCatalog {
    private static final AtomicReference<CappedEntityLootQuantityPolicy> CURRENT = new AtomicReference<>();

    private EntityLootQuantityPolicyCatalog() {}

    public static Optional<CappedEntityLootQuantityPolicy> current() {
        return Optional.ofNullable(CURRENT.get());
    }

    public static void install(CappedEntityLootQuantityPolicy policy) {
        CURRENT.set(Objects.requireNonNull(policy, "policy"));
    }

    public static void clear() {
        CURRENT.set(null);
    }
}
