package dev.gustavopere.rpgskilltree.compendium.api;

import java.util.Objects;

public record CompendiumRelation(
    CompendiumRelationType type,
    CompendiumEntryId target,
    FactSource source,
    FactConfidence confidence
) {
    public CompendiumRelation {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(target, "target");
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(confidence, "confidence");
        if (confidence == FactConfidence.UNAVAILABLE) {
            throw new IllegalArgumentException("relation requires available evidence");
        }
    }
}
