package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ArchetypeSpecificityScoreTest {
    public static void main(String[] args) {
        explicitSpecificityScoreControlsPrimaryOrdering();
        System.out.println("ArchetypeSpecificityScoreTest: PASS");
    }

    static void explicitSpecificityScoreControlsPrimaryOrdering() {
        var state = InvestmentState.of(List.of(
            new NodeInvestment("arcane", Map.of(ProgressionDomain.ARCANE, 14), Set.of()),
            new NodeInvestment("martial", Map.of(ProgressionDomain.MARTIAL, 14), Set.of())
        ));

        var lowerSpecificity = new ArchetypeDefinition(
            "lower_specificity",
            100,
            20,
            Map.of(ProgressionDomain.ARCANE, 8, ProgressionDomain.MARTIAL, 8),
            Set.of(),
            Set.of()
        );
        var higherSpecificity = new ArchetypeDefinition(
            "higher_specificity",
            1,
            40,
            Map.of(ProgressionDomain.ARCANE, 8, ProgressionDomain.MARTIAL, 8),
            Set.of(),
            Set.of()
        );

        var hierarchy = ArchetypeResolver.resolveHierarchy(state, List.of(lowerSpecificity, higherSpecificity));

        eq("higher_specificity", hierarchy.primaryClassId().orElseThrow());
        eq(List.of("lower_specificity"), hierarchy.secondaryClassIds());
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
