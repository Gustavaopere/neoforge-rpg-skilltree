package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import java.util.Objects;
import java.util.Set;

public final class CompendiumFilterControlsTest {
    public static void main(String[] args) {
        kindControlCyclesAllKindsAndBackToAll();
        discoveredControlCyclesTriState();
        controlsPreserveUnrelatedFilters();
        System.out.println("CompendiumFilterControlsTest: PASS");
    }

    private static void kindControlCyclesAllKindsAndBackToAll() {
        CompendiumFilterState state = CompendiumFilterState.all();
        for (CompendiumEntryKind kind : CompendiumEntryKind.values()) {
            state = CompendiumFilterControls.cycleKind(state);
            eq(Set.of(kind), state.kinds());
        }
        state = CompendiumFilterControls.cycleKind(state);
        eq(Set.of(), state.kinds());
    }

    private static void discoveredControlCyclesTriState() {
        CompendiumFilterState state = CompendiumFilterState.all();
        state = CompendiumFilterControls.cycleDiscovered(state);
        eq(CompendiumFilterState.BooleanFilter.TRUE, state.discovered());
        state = CompendiumFilterControls.cycleDiscovered(state);
        eq(CompendiumFilterState.BooleanFilter.FALSE, state.discovered());
        state = CompendiumFilterControls.cycleDiscovered(state);
        eq(CompendiumFilterState.BooleanFilter.ANY, state.discovered());
    }

    private static void controlsPreserveUnrelatedFilters() {
        CompendiumFilterState initial = new CompendiumFilterState(
            Set.of(),
            Set.of("minecraft"),
            Set.of("example"),
            Set.of("fauna"),
            Set.of("minecraft:overworld"),
            Set.of("minecraft:forest"),
            CompendiumFilterState.BooleanFilter.ANY,
            CompendiumFilterState.BooleanFilter.TRUE,
            CompendiumFilterState.BooleanFilter.FALSE,
            CompendiumFilterState.BooleanFilter.TRUE,
            CompendiumFilterState.BooleanFilter.FALSE,
            Set.of(CoverageState.CURATED)
        );

        CompendiumFilterState changed = CompendiumFilterControls.cycleDiscovered(
            CompendiumFilterControls.cycleKind(initial)
        );

        eq(Set.of(CompendiumEntryKind.ENTITY), changed.kinds());
        eq(CompendiumFilterState.BooleanFilter.TRUE, changed.discovered());
        eq(initial.namespaces(), changed.namespaces());
        eq(initial.sourceModIds(), changed.sourceModIds());
        eq(initial.categoryIds(), changed.categoryIds());
        eq(initial.dimensionIds(), changed.dimensionIds());
        eq(initial.biomeIds(), changed.biomeIds());
        eq(initial.hostile(), changed.hostile());
        eq(initial.tameable(), changed.tameable());
        eq(initial.breedable(), changed.breedable());
        eq(initial.boss(), changed.boss());
        eq(initial.coverageStates(), changed.coverageStates());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
