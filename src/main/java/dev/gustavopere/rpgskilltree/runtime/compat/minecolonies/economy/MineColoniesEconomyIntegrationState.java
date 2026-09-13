package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

/** Bootstrap state for the optional MineColonies economy adapter. */
public enum MineColoniesEconomyIntegrationState {
    ABSENT_PROVIDER,
    UNSUPPORTED_VERSION,
    ACTIVE,
    FAILED_CLOSED
}
