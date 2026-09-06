package dev.gustavopere.rpgskilltree.runtime.compat;

/**
 * Provider-neutral semantic capability owned by exactly one enabled adapter at runtime.
 */
@FunctionalInterface
public interface IntegrationCapability {
    SemanticActionId actionId();
}
