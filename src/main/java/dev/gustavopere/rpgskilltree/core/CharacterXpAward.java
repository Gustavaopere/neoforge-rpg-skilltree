package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;
import java.util.Set;

public record CharacterXpAward(String sourceId, long amount, Set<ProgressionDomain> attributedDomains) {
    public CharacterXpAward {
        Objects.requireNonNull(sourceId);
        Objects.requireNonNull(attributedDomains);
        if (sourceId.isBlank()) throw new IllegalArgumentException("sourceId must not be blank");
        if (amount <= 0) throw new IllegalArgumentException("XP award must be positive");
        attributedDomains = Set.copyOf(attributedDomains);
    }
}
