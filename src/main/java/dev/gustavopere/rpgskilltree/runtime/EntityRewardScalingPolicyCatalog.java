package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CappedEntityRewardScalingPolicy;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/** Runtime holder for the explicitly installed server-authoritative entity reward policy. */
public final class EntityRewardScalingPolicyCatalog {
    private static final AtomicReference<CappedEntityRewardScalingPolicy> CURRENT = new AtomicReference<>();

    private EntityRewardScalingPolicyCatalog() {}

    public static Optional<CappedEntityRewardScalingPolicy> current() {
        return Optional.ofNullable(CURRENT.get());
    }

    public static void install(CappedEntityRewardScalingPolicy policy) {
        CURRENT.set(Objects.requireNonNull(policy, "policy"));
    }

    public static void clear() {
        CURRENT.set(null);
    }
}
