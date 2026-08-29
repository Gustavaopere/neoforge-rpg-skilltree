package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Immutable local filter state for a client-side Compendium snapshot.
 *
 * <p>Empty selection sets mean "any". Non-empty location/category sets use any-intersection
 * semantics so a single entry may satisfy a filter through any of its known dimensions or biomes.
 * Boolean properties are explicit tri-state filters rather than nullable flags.</p>
 */
public record CompendiumFilterState(
    Set<CompendiumEntryKind> kinds,
    Set<String> namespaces,
    Set<String> sourceModIds,
    Set<String> categoryIds,
    Set<String> dimensionIds,
    Set<String> biomeIds,
    BooleanFilter discovered,
    BooleanFilter hostile,
    BooleanFilter tameable,
    BooleanFilter breedable,
    BooleanFilter boss,
    Set<CoverageState> coverageStates
) {
    public CompendiumFilterState {
        kinds = immutableSet(kinds);
        namespaces = normalizedStrings(namespaces);
        sourceModIds = normalizedStrings(sourceModIds);
        categoryIds = normalizedStrings(categoryIds);
        dimensionIds = normalizedStrings(dimensionIds);
        biomeIds = normalizedStrings(biomeIds);
        discovered = Objects.requireNonNull(discovered, "discovered");
        hostile = Objects.requireNonNull(hostile, "hostile");
        tameable = Objects.requireNonNull(tameable, "tameable");
        breedable = Objects.requireNonNull(breedable, "breedable");
        boss = Objects.requireNonNull(boss, "boss");
        coverageStates = immutableSet(coverageStates);
    }

    public static CompendiumFilterState all() {
        return new CompendiumFilterState(
            Set.of(), Set.of(), Set.of(), Set.of(), Set.of(), Set.of(),
            BooleanFilter.ANY, BooleanFilter.ANY, BooleanFilter.ANY, BooleanFilter.ANY, BooleanFilter.ANY,
            Set.of()
        );
    }

    public boolean matches(CompendiumClientEntry entry) {
        Objects.requireNonNull(entry, "entry");
        if (!kinds.isEmpty() && !kinds.contains(entry.id().kind())) return false;
        if (!namespaces.isEmpty() && !namespaces.contains(entry.id().namespace())) return false;
        if (!sourceModIds.isEmpty() && !sourceModIds.contains(entry.sourceModId())) return false;
        if (!intersects(categoryIds, entry.categoryIds())) return false;
        if (!intersects(dimensionIds, entry.dimensionIds())) return false;
        if (!intersects(biomeIds, entry.biomeIds())) return false;
        if (!discovered.matches(entry.discovered())) return false;
        if (!hostile.matches(entry.hostile())) return false;
        if (!tameable.matches(entry.tameable())) return false;
        if (!breedable.matches(entry.breedable())) return false;
        if (!boss.matches(entry.boss())) return false;
        return coverageStates.isEmpty() || coverageStates.contains(entry.coverageState());
    }

    public List<CompendiumClientEntry> filter(List<CompendiumClientEntry> entries) {
        if (entries == null || entries.isEmpty()) return List.of();
        List<CompendiumClientEntry> result = new ArrayList<>();
        for (CompendiumClientEntry entry : entries) {
            if (matches(entry)) result.add(entry);
        }
        return List.copyOf(result);
    }

    private static boolean intersects(Set<String> selected, Set<String> values) {
        if (selected.isEmpty()) return true;
        for (String value : values) if (selected.contains(value)) return true;
        return false;
    }

    private static Set<String> normalizedStrings(Set<String> values) {
        if (values == null || values.isEmpty()) return Set.of();
        LinkedHashSet<String> normalized = new LinkedHashSet<>();
        for (String value : values) {
            if (value == null || value.trim().isEmpty()) {
                throw new IllegalArgumentException("filter value must not be blank");
            }
            normalized.add(value.trim());
        }
        return Set.copyOf(normalized);
    }

    private static <T> Set<T> immutableSet(Set<T> values) {
        if (values == null || values.isEmpty()) return Set.of();
        for (T value : values) Objects.requireNonNull(value, "filter value");
        return Set.copyOf(values);
    }

    public enum BooleanFilter {
        ANY,
        TRUE,
        FALSE;

        public boolean matches(boolean value) {
            return switch (this) {
                case ANY -> true;
                case TRUE -> value;
                case FALSE -> !value;
            };
        }
    }
}
