package dev.gustavopere.rpgskilltree.compendium.catalog;

import java.util.Objects;

public record CoverageDecision(CoverageState state, String reason) {
    public CoverageDecision {
        Objects.requireNonNull(state, "state");
        reason = reason == null ? "" : reason.trim();
    }
}
