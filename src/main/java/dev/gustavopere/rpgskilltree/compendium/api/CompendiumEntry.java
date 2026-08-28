package dev.gustavopere.rpgskilltree.compendium.api;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public record CompendiumEntry(
    CompendiumEntryId id,
    String sourceModId,
    String translationKey,
    Set<String> categoryIds,
    List<CompendiumSection> sections,
    List<CompendiumRelation> relations,
    DiscoveryPolicy discoveryPolicy,
    VisibilityPolicy visibilityPolicy,
    CompendiumProvenance provenance,
    int contentVersion
) {
    private static final Pattern MOD_ID = Pattern.compile("[a-z0-9_.-]+");

    public CompendiumEntry {
        Objects.requireNonNull(id, "id");
        sourceModId = requireText(sourceModId, "sourceModId");
        if (!MOD_ID.matcher(sourceModId).matches()) {
            throw new IllegalArgumentException("invalid sourceModId: " + sourceModId);
        }
        translationKey = requireText(translationKey, "translationKey");
        categoryIds = normalizeCategoryIds(categoryIds);
        sections = List.copyOf(sections == null ? List.of() : sections);
        relations = List.copyOf(relations == null ? List.of() : relations);
        Objects.requireNonNull(discoveryPolicy, "discoveryPolicy");
        Objects.requireNonNull(visibilityPolicy, "visibilityPolicy");
        Objects.requireNonNull(provenance, "provenance");
        if (contentVersion <= 0) throw new IllegalArgumentException("contentVersion must be positive");

        HashSet<String> sectionIds = new HashSet<>();
        for (CompendiumSection section : sections) {
            if (section == null) throw new IllegalArgumentException("section must not be null");
            if (!sectionIds.add(section.sectionId())) {
                throw new IllegalArgumentException("duplicate section id: " + section.sectionId());
            }
        }
        for (CompendiumRelation relation : relations) {
            if (relation == null) throw new IllegalArgumentException("relation must not be null");
        }
    }

    public CompendiumEntry withSections(List<CompendiumSection> replacementSections) {
        return new CompendiumEntry(
            id, sourceModId, translationKey, categoryIds, replacementSections, relations,
            discoveryPolicy, visibilityPolicy, provenance, contentVersion
        );
    }

    public CompendiumEntry withCategoriesAndRelations(
        Set<String> replacementCategories,
        List<CompendiumRelation> replacementRelations
    ) {
        return new CompendiumEntry(
            id, sourceModId, translationKey, replacementCategories, sections, replacementRelations,
            discoveryPolicy, visibilityPolicy, provenance, contentVersion
        );
    }

    private static Set<String> normalizeCategoryIds(Set<String> values) {
        if (values == null || values.isEmpty()) return Set.of();
        LinkedHashSet<String> normalized = new LinkedHashSet<>();
        for (String value : values) {
            normalized.add(requireText(value, "categoryId"));
        }
        return Set.copyOf(normalized);
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
}
