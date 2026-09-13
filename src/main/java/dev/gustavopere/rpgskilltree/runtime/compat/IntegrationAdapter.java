package dev.gustavopere.rpgskilltree.runtime.compat;

import java.util.Set;

/**
 * Provider-neutral runtime view of one optional integration.
 *
 * <p>External provider API types must remain inside provider-specific compat packages and
 * must never leak through this contract.</p>
 */
public interface IntegrationAdapter {
    SemanticActionId adapterId();

    OptionalIntegrations.Provider provider();

    Set<? extends IntegrationCapability> capabilities();

    default boolean enabled() {
        return true;
    }

    default String disabledReason() {
        return "unspecified";
    }
}
