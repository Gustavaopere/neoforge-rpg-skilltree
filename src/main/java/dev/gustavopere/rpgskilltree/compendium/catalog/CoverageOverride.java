package dev.gustavopere.rpgskilltree.compendium.catalog;

import java.util.Objects;

public record CoverageOverride(CoverageState state, String reason) {
    public CoverageOverride {
        Objects.requireNonNull(state, "state");
        reason = reason == null ? "" : reason.trim();
    }
}
