package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import java.util.Objects;

/** Immutable administrative provenance projected for optional client debug presentation. */
public record CompendiumDebugInfo(
    String resourceLocation,
    String sourceModId,
    FactSource factSource,
    String providerId,
    CoverageState coverageState
) {
    public CompendiumDebugInfo {
        resourceLocation = requireText(resourceLocation, "resourceLocation");
        sourceModId = requireText(sourceModId, "sourceModId");
        Objects.requireNonNull(factSource, "factSource");
        providerId = requireText(providerId, "providerId");
        Objects.requireNonNull(coverageState, "coverageState");
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }
}
