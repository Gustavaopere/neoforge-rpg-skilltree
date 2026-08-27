package dev.gustavopere.rpgskilltree.core;

/** Describes whether a semantic action has a legitimate player credit path. */
public enum SemanticActionAuthorship {
    DIRECT_PLAYER(true),
    EXPLICIT_PLAYER_AUTOMATION(true),
    UNATTRIBUTED_AUTOMATION(false),
    NON_PLAYER(false),
    UNKNOWN(false);

    private final boolean creditable;

    SemanticActionAuthorship(boolean creditable) {
        this.creditable = creditable;
    }

    public boolean creditable() {
        return creditable;
    }
}
