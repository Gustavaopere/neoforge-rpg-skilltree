package dev.gustavopere.rpgskilltree.compendium.editorial;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.List;
import java.util.Objects;

public record CompendiumEditorialContent(
    CompendiumEntryId entryId,
    String title,
    CompendiumEditorialBlock summary,
    List<CompendiumEditorialSection> sections,
    List<CompendiumEntryId> references,
    EditorialReviewStatus reviewStatus,
    EditorialAvailability availability,
    String availabilityReason
) {
    public CompendiumEditorialContent {
        Objects.requireNonNull(entryId, "entryId");
        title = requireText(title, "title");
        Objects.requireNonNull(summary, "summary");
        sections = List.copyOf(Objects.requireNonNull(sections, "sections"));
        references = List.copyOf(Objects.requireNonNull(references, "references"));
        for (CompendiumEditorialSection section : sections) Objects.requireNonNull(section, "section");
        for (CompendiumEntryId reference : references) Objects.requireNonNull(reference, "reference");
        Objects.requireNonNull(reviewStatus, "reviewStatus");
        Objects.requireNonNull(availability, "availability");

        if (availability == EditorialAvailability.RUNTIME) {
            if (availabilityReason != null) {
                throw new IllegalArgumentException("availabilityReason is only valid for OPTIONAL/LEGACY content");
            }
        } else {
            availabilityReason = requireText(availabilityReason, "availabilityReason");
        }
    }

    public CompendiumEditorialContent withReferences(List<CompendiumEntryId> replacementReferences) {
        return new CompendiumEditorialContent(
            entryId,
            title,
            summary,
            sections,
            replacementReferences,
            reviewStatus,
            availability,
            availabilityReason
        );
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }
}
