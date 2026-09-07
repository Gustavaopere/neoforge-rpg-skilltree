package dev.gustavopere.rpgskilltree.runtime.compat.eidolon;

/** Bootstrap state for the optional Eidolon Repraised mastery adapter. */
public enum EidolonIntegrationState {
    ABSENT_PROVIDER,
    UNSUPPORTED_VERSION,
    ACTIVE,
    FAILED_CLOSED
}
