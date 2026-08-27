package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;
import java.util.OptionalLong;

public final class NativeAreaThreatCompositionTest {
    public static void main(String[] args) {
        contributionSourcesAreStableNamespacedIds();
        compositionIsDeterministicAndAuditable();
        signedContributionsClampAtZero();
        explicitOverrideWinsAfterValidatedComposition();
        overflowFailsExplicitly();
        System.out.println("NativeAreaThreatCompositionTest: PASS");
    }

    private static void contributionSourcesAreStableNamespacedIds() {
        NativeAreaLevelContribution distance = NativeAreaLevelContribution.of("rpgskilltree:distance", 12L);
        eq("rpgskilltree:distance", distance.sourceId());
        eq(12L, distance.delta());

        expect(IllegalArgumentException.class, () -> NativeAreaLevelContribution.of("distance", 1L));
        expect(IllegalArgumentException.class, () -> NativeAreaLevelContribution.of("RpgSkillTree:distance", 1L));
        expect(IllegalArgumentException.class, () -> NativeAreaLevelContribution.of("rpgskilltree:bad source", 1L));
    }

    private static void compositionIsDeterministicAndAuditable() {
        TerritoryKey key = TerritoryKey.of("minecraft:overworld", 10L, -3L);
        NativeAreaLevelPlan first = NativeAreaLevelPlan.of(
            5L,
            List.of(
                NativeAreaLevelContribution.of("rpgskilltree:structure", 20L),
                NativeAreaLevelContribution.of("rpgskilltree:distance", 4L),
                NativeAreaLevelContribution.of("rpgskilltree:biome", 3L)
            )
        );
        NativeAreaLevelPlan second = NativeAreaLevelPlan.of(
            5L,
            List.of(
                NativeAreaLevelContribution.of("rpgskilltree:biome", 3L),
                NativeAreaLevelContribution.of("rpgskilltree:structure", 20L),
                NativeAreaLevelContribution.of("rpgskilltree:distance", 4L)
            )
        );

        NativeAreaLevelBreakdown a = NativeAreaThreatResolver.resolve(key, first);
        NativeAreaLevelBreakdown b = NativeAreaThreatResolver.resolve(key, second);

        eq(a, b);
        eq(32L, a.resolvedLevel());
        eq(List.of(
            NativeAreaLevelContribution.of("rpgskilltree:biome", 3L),
            NativeAreaLevelContribution.of("rpgskilltree:distance", 4L),
            NativeAreaLevelContribution.of("rpgskilltree:structure", 20L)
        ), a.contributions());
        eq(OptionalLong.empty(), a.overrideLevel());

        expect(IllegalArgumentException.class, () -> NativeAreaLevelPlan.of(
            1L,
            List.of(
                NativeAreaLevelContribution.of("rpgskilltree:biome", 1L),
                NativeAreaLevelContribution.of("rpgskilltree:biome", 2L)
            )
        ));
    }

    private static void signedContributionsClampAtZero() {
        TerritoryKey key = TerritoryKey.of("minecraft:the_nether", -2L, 4L);
        NativeAreaLevelBreakdown result = NativeAreaThreatResolver.resolve(
            key,
            NativeAreaLevelPlan.of(
                3L,
                List.of(
                    NativeAreaLevelContribution.of("rpgskilltree:dimension", 5L),
                    NativeAreaLevelContribution.of("rpgskilltree:milestone", -20L)
                )
            )
        );
        eq(-12L, result.rawLevelBeforeClamp());
        eq(0L, result.levelBeforeOverride());
        eq(0L, result.resolvedLevel());
    }

    private static void explicitOverrideWinsAfterValidatedComposition() {
        TerritoryKey key = TerritoryKey.of("minecraft:overworld", 0L, 0L);
        NativeAreaLevelPlan plan = NativeAreaLevelPlan.withOverride(
            2L,
            List.of(
                NativeAreaLevelContribution.of("rpgskilltree:distance", 3L),
                NativeAreaLevelContribution.of("rpgskilltree:danger_tag", 10L)
            ),
            70L
        );
        NativeAreaLevelBreakdown result = NativeAreaThreatResolver.resolve(key, plan);
        eq(15L, result.rawLevelBeforeClamp());
        eq(15L, result.levelBeforeOverride());
        eq(OptionalLong.of(70L), result.overrideLevel());
        eq(70L, result.resolvedLevel());

        expect(IllegalArgumentException.class, () -> NativeAreaLevelPlan.withOverride(0L, List.of(), -1L));
    }

    private static void overflowFailsExplicitly() {
        TerritoryKey key = TerritoryKey.of("minecraft:overworld", 0L, 0L);
        expect(ArithmeticException.class, () -> NativeAreaThreatResolver.resolve(
            key,
            NativeAreaLevelPlan.of(
                Long.MAX_VALUE,
                List.of(NativeAreaLevelContribution.of("rpgskilltree:structure", 1L))
            )
        ));
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
