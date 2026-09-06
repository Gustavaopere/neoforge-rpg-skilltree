package dev.gustavopere.rpgskilltree.runtime.compat.goety;

/** Bootstrap state for the optional Goety mastery adapter. */
public enum GoetyIntegrationState {
    ABSENT_PROVIDER,
    UNSUPPORTED_VERSION,
    ACTIVE,
    FAILED_CLOSED
}
