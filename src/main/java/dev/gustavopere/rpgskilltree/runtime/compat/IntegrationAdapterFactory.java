package dev.gustavopere.rpgskilltree.runtime.compat;

/**
 * Lazy factory boundary for an optional-provider adapter.
 *
 * <p>The registry checks provider presence before invoking {@link #create()}, preventing
 * premature classloading of provider-specific implementation classes when the mod is absent.</p>
 */
public interface IntegrationAdapterFactory {
    SemanticActionId adapterId();

    OptionalIntegrations.Provider provider();

    IntegrationAdapter create();
}
