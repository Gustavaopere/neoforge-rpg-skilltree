package dev.gustavopere.rpgskilltree.compendium.flora;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Coverage compares canonical classified/grouped entries, while ambiguous raw blocks remain diagnostics. */
public record FloraCatalogCoverage(
    Set<CompendiumEntryId> missingEntryIds,
    Set<CompendiumEntryId> unexpectedEntryIds,
    List<String> ambiguousBlockIds
) {
    public FloraCatalogCoverage {
        Objects.requireNonNull(missingEntryIds, "missingEntryIds");
        Objects.requireNonNull(unexpectedEntryIds, "unexpectedEntryIds");
        Objects.requireNonNull(ambiguousBlockIds, "ambiguousBlockIds");
        missingEntryIds = Set.copyOf(missingEntryIds);
        unexpectedEntryIds = Set.copyOf(unexpectedEntryIds);
        ambiguousBlockIds = ambiguousBlockIds.stream()
            .map(FloraCatalogCoverage::requireResourceId)
            .distinct()
            .sorted()
            .toList();
    }

    public boolean complete() {
        return missingEntryIds.isEmpty() && unexpectedEntryIds.isEmpty();
    }

    public static FloraCatalogCoverage compare(
        Collection<CompendiumEntryId> expectedEntryIds,
        Collection<CompendiumEntryId> catalogEntryIds,
        Collection<String> ambiguousBlockIds
    ) {
        Objects.requireNonNull(expectedEntryIds, "expectedEntryIds");
        Objects.requireNonNull(catalogEntryIds, "catalogEntryIds");
        Objects.requireNonNull(ambiguousBlockIds, "ambiguousBlockIds");

        Set<CompendiumEntryId> expected = new LinkedHashSet<>(expectedEntryIds);
        Set<CompendiumEntryId> actual = new LinkedHashSet<>(catalogEntryIds);

        Set<CompendiumEntryId> missing = new LinkedHashSet<>(expected);
        missing.removeAll(actual);
        Set<CompendiumEntryId> unexpected = new LinkedHashSet<>(actual);
        unexpected.removeAll(expected);

        return new FloraCatalogCoverage(missing, unexpected, List.copyOf(ambiguousBlockIds));
    }

    private static String requireResourceId(String value) {
        Objects.requireNonNull(value, "ambiguous block id");
        String normalized = value.trim();
        int colon = normalized.indexOf(':');
        if (colon <= 0 || colon == normalized.length() - 1) {
            throw new IllegalArgumentException("invalid block id: " + value);
        }
        return normalized;
    }
}
