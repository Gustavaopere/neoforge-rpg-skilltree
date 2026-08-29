package dev.gustavopere.rpgskilltree.compendium.api;

import java.util.Objects;

public record CompendiumRelation(
    CompendiumRelationType type,
    CompendiumRelationTarget target,
    FactSource source,
    FactConfidence confidence,
    String evidenceId
) {
    public CompendiumRelation {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(target, "target");
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(confidence, "confidence");
        if (confidence == FactConfidence.UNAVAILABLE) {
            throw new IllegalArgumentException("relation requires available evidence");
        }
        if (evidenceId != null) {
            evidenceId = evidenceId.trim();
            if (evidenceId.isEmpty()) evidenceId = null;
        }
        if (source == FactSource.CURATED_EDITORIAL && confidence == FactConfidence.EXACT && evidenceId == null) {
            throw new IllegalArgumentException("curated EXACT relation requires evidenceId");
        }
    }

    public CompendiumRelation(
        CompendiumRelationType type,
        CompendiumRelationTarget target,
        FactSource source,
        FactConfidence confidence
    ) {
        this(type, target, source, confidence, null);
    }

    public CompendiumRelation(
        CompendiumRelationType type,
        CompendiumEntryId target,
        FactSource source,
        FactConfidence confidence
    ) {
        this(type, CompendiumRelationTarget.entry(target), source, confidence, null);
    }

    public CompendiumRelation(
        CompendiumRelationType type,
        CompendiumEntryId target,
        FactSource source,
        FactConfidence confidence,
        String evidenceId
    ) {
        this(type, CompendiumRelationTarget.entry(target), source, confidence, evidenceId);
    }
}
