package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Canonical lifecycle classification for transient A0001-A0050 runtime state. */
public final class CombatPerkLifecyclePolicy {
    private CombatPerkLifecyclePolicy() {}

    public enum Boundary {
        DEATH,
        RESPAWN,
        PLAYER_RECREATION,
        DIMENSION_CHANGE,
        LOGIN,
        LOGOUT
    }

    public enum CleanupMode {
        TRANSIENT_PRESERVE_GUARDS,
        FULL_SESSION
    }

    public static CleanupMode cleanupMode(Boundary boundary) {
        Objects.requireNonNull(boundary);
        return switch (boundary) {
            case DEATH, RESPAWN, PLAYER_RECREATION, DIMENSION_CHANGE -> CleanupMode.TRANSIENT_PRESERVE_GUARDS;
            case LOGIN, LOGOUT -> CleanupMode.FULL_SESSION;
        };
    }
}
