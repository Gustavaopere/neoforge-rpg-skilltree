package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumRelation;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumSection;
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
    List<CompendiumRelation> entryRelations
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
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }
}
