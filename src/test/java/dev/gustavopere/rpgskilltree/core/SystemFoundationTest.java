package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class SystemFoundationTest {
    public static void main(String[] args) {
        moreSpecificArchetypeBeatsDisplayPriority();
        strongerSatisfiedRequirementsBreakSpecificityTies();
        classlessSpecializationCanUnlock();
        classGatedSpecializationStillRequiresEligibleClass();
        System.out.println("SystemFoundationTest: PASS");
    }

    static void moreSpecificArchetypeBeatsDisplayPriority() {
        var state = InvestmentState.of(List.of(
            new NodeInvestment("arcane", Map.of(ProgressionDomain.ARCANE, 12), Set.of()),
            new NodeInvestment("martial", Map.of(ProgressionDomain.MARTIAL, 10), Set.of())
        ));
        var generic = new ArchetypeDefinition(
            "mage", 100,
            Map.of(ProgressionDomain.ARCANE, 8),
            Set.of(), Set.of()
        );
        var hybrid = new ArchetypeDefinition(
            "spellblade", 1,
            Map.of(ProgressionDomain.ARCANE, 6, ProgressionDomain.MARTIAL, 6),
            Set.of(), Set.of()
        );

        eq(List.of("spellblade", "mage"), ids(ArchetypeResolver.resolve(state, List.of(generic, hybrid))));
    }

    static void strongerSatisfiedRequirementsBreakSpecificityTies() {
        var state = InvestmentState.of(List.of(
            new NodeInvestment("arcane", Map.of(ProgressionDomain.ARCANE, 14), Set.of()),
            new NodeInvestment("martial", Map.of(ProgressionDomain.MARTIAL, 9), Set.of()),
            new NodeInvestment("agility", Map.of(ProgressionDomain.AGILITY, 7), Set.of())
        ));
        var spellblade = new ArchetypeDefinition(
            "spellblade", 1,
            Map.of(ProgressionDomain.ARCANE, 6, ProgressionDomain.MARTIAL, 6),
            Set.of(), Set.of()
        );
        var arcaneArcher = new ArchetypeDefinition(
            "arcane_archer", 100,
            Map.of(ProgressionDomain.ARCANE, 6, ProgressionDomain.AGILITY, 6),
            Set.of(), Set.of()
        );

        eq(List.of("spellblade", "arcane_archer"), ids(ArchetypeResolver.resolve(state, List.of(arcaneArcher, spellblade))));
    }

    static void classlessSpecializationCanUnlock() {
        var definition = new SpecializationDefinition(
            "create_kinetics",
            Set.of(),
            Map.of("create:kinetics", 100),
            Set.of("gateway:create_kinetics")
        );
        var mastery = MasteryState.of(Map.of("create:kinetics", 100));
        var investment = InvestmentState.of(List.of(
            new NodeInvestment("engineering_gate", Map.of(ProgressionDomain.ENGINEERING, 5), Set.of("gateway:create_kinetics"))
        ));

        var result = SpecializationResolver.evaluate(Set.of(), mastery, investment, definition);
        eq(true, result.unlockable());
        eq(false, result.missingEligibleClass());
    }

    static void classGatedSpecializationStillRequiresEligibleClass() {
        var definition = new SpecializationDefinition(
            "battlemage_training",
            Set.of("mage", "spellblade"),
            Map.of(),
            Set.of()
        );
        var result = SpecializationResolver.evaluate(
            Set.of("warrior"),
            MasteryState.of(Map.of()),
            InvestmentState.of(List.of()),
            definition
        );
        eq(false, result.unlockable());
        eq(true, result.missingEligibleClass());
    }

    private static List<String> ids(List<ArchetypeMatch> matches) {
        return matches.stream().map(ArchetypeMatch::archetypeId).toList();
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
