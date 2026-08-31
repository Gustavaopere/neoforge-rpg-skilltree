package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class ClassResolutionQueryServiceTest {
    public static void main(String[] args) {
        sameBuildResolvesIdenticallyRegardlessOfDefinitionOrder();
        multipleEligibleIdentitiesRemainDeterministicallyOrdered();
        emptyDefinitionsYieldNoIdentity();
        duplicateArchetypeIdsAreRejectedAtTheQueryBoundary();
        System.out.println("ClassResolutionQueryServiceTest: PASS");
    }

    private static void sameBuildResolvesIdenticallyRegardlessOfDefinitionOrder() {
        InvestmentState state = InvestmentState.of(List.of(
            new NodeInvestment("arcane", Map.of(ProgressionDomain.ARCANE, 12), Set.of("provider:irons")),
            new NodeInvestment("martial", Map.of(ProgressionDomain.MARTIAL, 10), Set.of())
        ));
        List<ArchetypeDefinition> definitions = definitions();
        List<ArchetypeDefinition> reversed = new ArrayList<>(definitions);
        java.util.Collections.reverse(reversed);

        EmergentClassResolution forward = ClassResolutionQueryService.resolve(state, definitions);
        EmergentClassResolution backward = ClassResolutionQueryService.resolve(state, reversed);

        eq(forward, backward);
        eq("rpgskilltree:spellblade", forward.primaryClassId().orElseThrow());
        eq(List.of("rpgskilltree:mage"), forward.secondaryClassIds());
    }

    private static void multipleEligibleIdentitiesRemainDeterministicallyOrdered() {
        InvestmentState state = InvestmentState.of(List.of(
            new NodeInvestment("arcane", Map.of(ProgressionDomain.ARCANE, 12), Set.of("provider:irons")),
            new NodeInvestment("martial", Map.of(ProgressionDomain.MARTIAL, 10), Set.of())
        ));

        EmergentClassResolution resolution = ClassResolutionQueryService.resolve(state, definitions());

        eq(List.of("rpgskilltree:spellblade", "rpgskilltree:mage"),
            resolution.orderedMatches().stream().map(ArchetypeMatch::archetypeId).toList());
    }

    private static void emptyDefinitionsYieldNoIdentity() {
        InvestmentState state = InvestmentState.of(List.of());
        EmergentClassResolution resolution = ClassResolutionQueryService.resolve(state, List.of());

        eq(false, resolution.primaryClassId().isPresent());
        eq(List.of(), resolution.secondaryClassIds());
        eq(List.of(), resolution.orderedMatches());
    }

    private static void duplicateArchetypeIdsAreRejectedAtTheQueryBoundary() {
        InvestmentState state = InvestmentState.of(List.of(
            new NodeInvestment("arcane", Map.of(ProgressionDomain.ARCANE, 12), Set.of())
        ));
        ArchetypeDefinition first = new ArchetypeDefinition(
            "rpgskilltree:mage", 10, 1,
            Map.of(ProgressionDomain.ARCANE, 8), Set.of(), Set.of()
        );
        ArchetypeDefinition conflicting = new ArchetypeDefinition(
            "rpgskilltree:mage", 99, 2,
            Map.of(ProgressionDomain.ARCANE, 10), Set.of(), Set.of()
        );

        expect(IllegalArgumentException.class,
            () -> ClassResolutionQueryService.resolve(state, List.of(first, conflicting)));
    }

    private static List<ArchetypeDefinition> definitions() {
        return List.of(
            new ArchetypeDefinition(
                "rpgskilltree:mage", 10, 1,
                Map.of(ProgressionDomain.ARCANE, 8), Set.of(), Set.of()
            ),
            new ArchetypeDefinition(
                "rpgskilltree:spellblade", 30, 2,
                Map.of(ProgressionDomain.ARCANE, 8, ProgressionDomain.MARTIAL, 8), Set.of(), Set.of()
            ),
            new ArchetypeDefinition(
                "rpgskilltree:technomancer", 40, 2,
                Map.of(ProgressionDomain.ARCANE, 8, ProgressionDomain.ENGINEERING, 8), Set.of(), Set.of()
            )
        );
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
