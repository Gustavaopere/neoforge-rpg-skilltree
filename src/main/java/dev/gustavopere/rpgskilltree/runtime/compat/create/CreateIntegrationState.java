package dev.gustavopere.rpgskilltree.runtime.compat.create;

/** Bootstrap state for the optional Create engineering-mastery adapter. */
public enum CreateIntegrationState {
    ABSENT_PROVIDER,
    UNSUPPORTED_VERSION,
    ACTIVE,
    FAILED_CLOSED
}
