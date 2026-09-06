package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

/** Server-authoritative outcome for one administrative economy intent. */
public enum MineColoniesEconomyIntentStatus {
    ACCEPTED,
    APPLIED,
    DISABLED,
    WRONG_COLONY,
    PERMISSION_DENIED,
    INVALID_AMOUNT,
    PROTOCOL_LIMIT_EXCEEDED,
    POLICY_LIMIT_EXCEEDED,
    PROVIDER_READ_FAILED,
    DUPLICATE,
    INSUFFICIENT_TREASURY,
    UNSUPPORTED_OPERATION,
    RETENTION_LIMIT_REACHED,
    OVERFLOW
}
