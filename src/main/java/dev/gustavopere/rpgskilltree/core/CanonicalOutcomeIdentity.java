package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Stable outcome lane inside one canonical action. */
public record CanonicalOutcomeIdentity(CanonicalActionIdentity action, String outcomeId) {
    public CanonicalOutcomeIdentity {
        Objects.requireNonNull(action);
        Objects.requireNonNull(outcomeId);
        if (outcomeId.isBlank()) throw new IllegalArgumentException("outcomeId must not be blank");
    }
}
