package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import java.util.Objects;
import java.util.Set;

/**
 * Pure transition helpers for the compact Compendium filter controls.
 *
 * <p>Each transition changes one user-facing filter dimension and preserves every unrelated
 * canonical filter value.</p>
 */
public final class CompendiumFilterControls {
    private CompendiumFilterControls() {}

    public static CompendiumFilterState cycleKind(CompendiumFilterState current) {
        Objects.requireNonNull(current, "current");
        CompendiumEntryKind[] kinds = CompendiumEntryKind.values();
        Set<CompendiumEntryKind> selected = current.kinds();

        Set<CompendiumEntryKind> next;
        if (selected.size() != 1) {
            next = Set.of(kinds[0]);
        } else {
            CompendiumEntryKind selectedKind = selected.iterator().next();
            int index = selectedKind.ordinal();
            next = index + 1 < kinds.length ? Set.of(kinds[index + 1]) : Set.of();
        }
        return withKindAndDiscovered(current, next, current.discovered());
    }

    public static CompendiumFilterState cycleDiscovered(CompendiumFilterState current) {
        Objects.requireNonNull(current, "current");
        CompendiumFilterState.BooleanFilter next = switch (current.discovered()) {
            case ANY -> CompendiumFilterState.BooleanFilter.TRUE;
            case TRUE -> CompendiumFilterState.BooleanFilter.FALSE;
            case FALSE -> CompendiumFilterState.BooleanFilter.ANY;
        };
        return withKindAndDiscovered(current, current.kinds(), next);
    }

    private static CompendiumFilterState withKindAndDiscovered(
        CompendiumFilterState current,
        Set<CompendiumEntryKind> kinds,
        CompendiumFilterState.BooleanFilter discovered
    ) {
        return new CompendiumFilterState(
            kinds,
            current.namespaces(),
            current.sourceModIds(),
            current.categoryIds(),
            current.dimensionIds(),
            current.biomeIds(),
            discovered,
            current.hostile(),
            current.tameable(),
            current.breedable(),
            current.boss(),
            current.coverageStates()
        );
    }
}
