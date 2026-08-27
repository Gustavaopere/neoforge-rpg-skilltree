package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.AttributeRankCostPolicy;
import dev.gustavopere.rpgskilltree.core.AttributeRankCostPolicyProvider;
import dev.gustavopere.rpgskilltree.core.InstallableAttributeRankCostPolicyProvider;
import java.util.Objects;
import java.util.Optional;

/** Runtime holder for the explicitly installed authoritative attribute-rank cost policy. */
public final class AttributeRankCostPolicyCatalog {
    private static final InstallableAttributeRankCostPolicyProvider PROVIDER =
        new InstallableAttributeRankCostPolicyProvider();

    private AttributeRankCostPolicyCatalog() {}

    public static AttributeRankCostPolicyProvider provider() {
        return PROVIDER;
    }

    public static Optional<AttributeRankCostPolicy> current() {
        return PROVIDER.current();
    }

    public static void install(AttributeRankCostPolicy policy) {
        Objects.requireNonNull(policy, "policy");
        PROVIDER.install(policy);
    }

    public static void clear() {
        PROVIDER.clear();
    }
}
