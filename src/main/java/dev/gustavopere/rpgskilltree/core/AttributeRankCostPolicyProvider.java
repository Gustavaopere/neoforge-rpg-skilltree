package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Optional;

/** Explicit source of the authoritative attribute-rank cost policy. */
@FunctionalInterface
public interface AttributeRankCostPolicyProvider {
    Optional<AttributeRankCostPolicy> current();

    static AttributeRankCostPolicyProvider unconfigured() {
        return Optional::empty;
    }

    static AttributeRankCostPolicyProvider fixed(AttributeRankCostPolicy policy) {
        Objects.requireNonNull(policy, "policy");
        return () -> Optional.of(policy);
    }

    default AttributeRankCostPolicy requireCurrent() {
        Optional<AttributeRankCostPolicy> value = current();
        if (value == null) {
            throw new IllegalStateException("attribute rank cost policy provider returned null instead of Optional");
        }
        return value.orElseThrow(() -> new IllegalStateException("attribute rank cost policy is not configured"));
    }
}
