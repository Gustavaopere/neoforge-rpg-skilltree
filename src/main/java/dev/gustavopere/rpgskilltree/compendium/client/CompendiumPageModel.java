package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
import dev.gustavopere.rpgskilltree.compendium.api.FactSource;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import java.util.List;
import java.util.Objects;

/** Immutable, visibility-filtered page data ready for a client UI to render. */
public record CompendiumPageModel(
    CompendiumEntryId id,
    String displayName,
    String sourceModId,
    boolean discovered,
    boolean detailsVisible,
    List<CompendiumSection> sections,
    List<CompendiumRelation> entryRelations,
    CompendiumDebugInfo debugInfo
) {
    public CompendiumPageModel {
        Objects.requireNonNull(id, "id");
        displayName = requireText(displayName, "displayName");
        sourceModId = requireText(sourceModId, "sourceModId");
        sections = List.copyOf(sections == null ? List.of() : sections);
        entryRelations = List.copyOf(entryRelations == null ? List.of() : entryRelations);
        for (CompendiumSection section : sections) {
            Objects.requireNonNull(section, "section");
        }
        for (CompendiumRelation relation : entryRelations) {
            Objects.requireNonNull(relation, "entryRelation");
            if (relation.target().entryId() == null) {
                throw new IllegalArgumentException("entry relation must target a Compendium entry");
            }
        }
        Objects.requireNonNull(debugInfo, "debugInfo");
    }

    /**
     * Compatibility constructor for protocol-agnostic/headless callers that do not yet project canonical provenance.
     * Production page creation uses {@link CompendiumPageModelFactory} and therefore supplies exact debug metadata.
     */
    public CompendiumPageModel(
        CompendiumEntryId id,
        String displayName,
        String sourceModId,
        boolean discovered,
        boolean detailsVisible,
        List<CompendiumSection> sections,
        List<CompendiumRelation> entryRelations
    ) {
        this(
            id,
            displayName,
            sourceModId,
            discovered,
            detailsVisible,
            sections,
            entryRelations,
            new CompendiumDebugInfo(
                Objects.requireNonNull(id, "id").resourceLocation(),
                sourceModId,
                FactSource.UNKNOWN,
                "unknown",
                CoverageState.ERROR
            )
        );
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }
}
