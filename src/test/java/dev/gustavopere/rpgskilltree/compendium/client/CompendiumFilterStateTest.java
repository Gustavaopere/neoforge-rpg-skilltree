package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class CompendiumFilterStateTest {
    public static void main(String[] args) {
        emptyFilterPreservesInputOrder();
        combinesIdentityLocationBehaviorAndCoverageFilters();
        supportsExplicitFalseBooleanFilters();
        System.out.println("CompendiumFilterStateTest: PASS");
    }

    private static void emptyFilterPreservesInputOrder() {
        CompendiumClientEntry wolf = wolf();
        CompendiumClientEntry boss = boss();
        CompendiumClientEntry oak = oak();
        eq(List.of(wolf, boss, oak), CompendiumFilterState.all().filter(List.of(wolf, boss, oak)));
    }

    private static void combinesIdentityLocationBehaviorAndCoverageFilters() {
        CompendiumClientEntry wolf = wolf();
        CompendiumClientEntry boss = boss();
        CompendiumClientEntry oak = oak();
        CompendiumFilterState filter = new CompendiumFilterState(
            Set.of(CompendiumEntryKind.ENTITY),
            Set.of("cataclysm"),
            Set.of("cataclysm"),
            Set.of("boss"),
            Set.of("minecraft:the_nether"),
            Set.of("minecraft:nether_wastes"),
            CompendiumFilterState.BooleanFilter.TRUE,
            CompendiumFilterState.BooleanFilter.TRUE,
            CompendiumFilterState.BooleanFilter.FALSE,
            CompendiumFilterState.BooleanFilter.FALSE,
            CompendiumFilterState.BooleanFilter.TRUE,
            Set.of(CoverageState.ADAPTER)
        );

        eq(List.of(boss), filter.filter(List.of(wolf, boss, oak)));
        isTrue(filter.matches(boss));
        isFalse(filter.matches(wolf));
        isFalse(filter.matches(oak));
    }

    private static void supportsExplicitFalseBooleanFilters() {
        CompendiumClientEntry wolf = wolf();
        CompendiumClientEntry boss = boss();
        CompendiumFilterState filter = new CompendiumFilterState(
            Set.of(CompendiumEntryKind.ENTITY),
            Set.of(),
            Set.of(),
            Set.of("fauna"),
            Set.of("minecraft:overworld"),
            Set.of(),
            CompendiumFilterState.BooleanFilter.FALSE,
            CompendiumFilterState.BooleanFilter.FALSE,
            CompendiumFilterState.BooleanFilter.TRUE,
            CompendiumFilterState.BooleanFilter.TRUE,
            CompendiumFilterState.BooleanFilter.FALSE,
            Set.of()
        );

        eq(List.of(wolf), filter.filter(List.of(wolf, boss)));
    }

    private static CompendiumClientEntry wolf() {
        return entry(
            CompendiumEntryKind.ENTITY,
            "minecraft:wolf",
            "Lobo",
            "minecraft",
            Set.of("fauna"),
            Set.of("minecraft:overworld"),
            Set.of("minecraft:taiga", "minecraft:snowy_plains"),
            false,
            false,
            true,
            true,
            false,
            CoverageState.AUTO
        );
    }

    private static CompendiumClientEntry boss() {
        return entry(
            CompendiumEntryKind.ENTITY,
            "cataclysm:ignis",
            "Ignis",
            "cataclysm",
            Set.of("fauna", "boss"),
            Set.of("minecraft:the_nether"),
            Set.of("minecraft:nether_wastes"),
            true,
            true,
            false,
            false,
            true,
            CoverageState.ADAPTER
        );
    }

    private static CompendiumClientEntry oak() {
        return entry(
            CompendiumEntryKind.TREE,
            "minecraft:oak",
            "Carvalho",
            "minecraft",
            Set.of("arvores"),
            Set.of("minecraft:overworld"),
            Set.of("minecraft:forest"),
            true,
            false,
            false,
            false,
            false,
            CoverageState.AUTO
        );
    }

    private static CompendiumClientEntry entry(
        CompendiumEntryKind kind,
        String resourceLocation,
        String displayName,
        String sourceMod,
        Set<String> categories,
        Set<String> dimensions,
        Set<String> biomes,
        boolean discovered,
        boolean hostile,
        boolean tameable,
        boolean breedable,
        boolean boss,
        CoverageState coverage
    ) {
        return new CompendiumClientEntry(
            CompendiumEntryId.of(kind, resourceLocation),
            displayName,
            sourceMod,
            Set.of(),
            categories,
            dimensions,
            biomes,
            discovered,
            hostile,
            tameable,
            breedable,
            boss,
            coverage
        );
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }

    private static void isTrue(boolean value) {
        if (!value) throw new AssertionError("expected true");
    }

    private static void isFalse(boolean value) {
        if (value) throw new AssertionError("expected false");
    }
}
