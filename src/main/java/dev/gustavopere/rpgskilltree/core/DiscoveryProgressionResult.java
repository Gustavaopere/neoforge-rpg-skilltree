package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public record DiscoveryProgressionResult(
    ProgressionState state,
    long xpAwarded,
    boolean firstDiscovery
) {
    public DiscoveryProgressionResult {
        Objects.requireNonNull(state);
        if (xpAwarded < 0) throw new IllegalArgumentException("xpAwarded must be >= 0");
    }
}
