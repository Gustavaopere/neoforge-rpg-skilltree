package dev.gustavopere.rpgskilltree.compendium.discovery;

import java.util.Objects;

public enum DiscoveryState {
    UNKNOWN,
    SEEN,
    STUDIED,
    MASTERED;

    public boolean atLeast(DiscoveryState other) {
        Objects.requireNonNull(other, "other");
        return ordinal() >= other.ordinal();
    }

    public DiscoveryState max(DiscoveryState other) {
        Objects.requireNonNull(other, "other");
        return atLeast(other) ? this : other;
    }
}
