package dev.gustavopere.rpgskilltree.compendium.api;

import java.util.Objects;

public record CompendiumProvenance(FactSource source, String sourceId) {
    public CompendiumProvenance {
        Objects.requireNonNull(source, "source");
        if (sourceId == null || sourceId.trim().isEmpty()) {
            throw new IllegalArgumentException("sourceId must not be blank");
        }
        sourceId = sourceId.trim();
    }
}
