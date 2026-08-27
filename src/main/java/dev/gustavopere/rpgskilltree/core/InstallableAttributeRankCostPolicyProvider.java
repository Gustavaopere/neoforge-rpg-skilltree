package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/** Thread-safe runtime holder for an explicitly installed attribute-rank cost policy. */
public final class InstallableAttributeRankCostPolicyProvider implements AttributeRankCostPolicyProvider {
    private final AtomicReference<AttributeRankCostPolicy> current = new AtomicReference<>();

    @Override
    public Optional<AttributeRankCostPolicy> current() {
        return Optional.ofNullable(current.get());
    }

    /** Atomically installs {@code policy}, returning the previously active policy if any. */
    public Optional<AttributeRankCostPolicy> install(AttributeRankCostPolicy policy) {
        Objects.requireNonNull(policy, "policy");
        return Optional.ofNullable(current.getAndSet(policy));
    }

    /** Atomically removes the active policy and returns the previous value if any. */
    public Optional<AttributeRankCostPolicy> clear() {
        return Optional.ofNullable(current.getAndSet(null));
    }
}
