package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Immutable client-facing projection of a canonical Compendium entry.
 *
 * <p>This record never replaces {@link CompendiumEntryId} as identity. It only carries presentation
 * and filter metadata that a client snapshot can safely expose.</p>
 */
public record CompendiumClientEntry(
    CompendiumEntryId id,
    String displayName,
    String sourceModId,
    Set<String> aliases,
    Set<String> categoryIds,
    Set<String> dimensionIds,
    Set<String> biomeIds,
    boolean discovered,
    boolean hostile,
    boolean tameable,
    boolean breedable,
    boolean boss,
    CoverageState coverageState
) {
    public CompendiumClientEntry {
        Objects.requireNonNull(id, "id");
        displayName = requireText(displayName, "displayName");
        sourceModId = requireText(sourceModId, "sourceModId");
        aliases = normalizedSet(aliases);
        categoryIds = normalizedSet(categoryIds);
        dimensionIds = normalizedSet(dimensionIds);
        biomeIds = normalizedSet(biomeIds);
        Objects.requireNonNull(coverageState, "coverageState");
    }

    private static Set<String> normalizedSet(Set<String> values) {
        if (values == null || values.isEmpty()) return Set.of();
        LinkedHashSet<String> copy = new LinkedHashSet<>();
        for (String value : values) copy.add(requireText(value, "set value"));
        return Set.copyOf(copy);
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }
}
