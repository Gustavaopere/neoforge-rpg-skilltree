package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class NodeAccessRankRequirementTest {
    public static void main(String[] args) {
        rankedPrerequisiteRequiresExactMinimumRank();
        anyOfRankGroupImplementsNotionOrGate();
        System.out.println("NodeAccessRankRequirementTest: PASS");
    }

    private static void rankedPrerequisiteRequiresExactMinimumRank() {
        var requirement = new NodeAccessRequirement(
            1,
            Set.of(),
            Map.of(),
            Set.of(),
            Set.of(),
            Set.of(),
            Map.of("A0001", 2),
            List.of(),
            Set.of()
        );
        var curve = CharacterLevelCurve.defaultCurve();
        var rankOne = ProgressionState.empty().withPassiveNodes(PassiveNodeProgress.of(Map.of("A0001", 1)));
        var rankTwo = ProgressionState.empty().withPassiveNodes(PassiveNodeProgress.of(Map.of("A0001", 2)));

        eq(false, NodeAccessResolver.satisfied(rankOne, requirement, curve));
        eq(true, NodeAccessResolver.satisfied(rankTwo, requirement, curve));
    }

    private static void anyOfRankGroupImplementsNotionOrGate() {
        var requirement = new NodeAccessRequirement(
            1,
            Set.of(),
            Map.of(),
            Set.of(),
            Set.of(),
            Set.of(),
            Map.of(),
            List.of(Map.of("A0004", 2, "A0005", 3)),
            Set.of()
        );
        var curve = CharacterLevelCurve.defaultCurve();
        var neither = ProgressionState.empty().withPassiveNodes(PassiveNodeProgress.of(Map.of("A0004", 1, "A0005", 2)));
        var precise = ProgressionState.empty().withPassiveNodes(PassiveNodeProgress.of(Map.of("A0004", 2)));
        var penetrating = ProgressionState.empty().withPassiveNodes(PassiveNodeProgress.of(Map.of("A0005", 3)));

        eq(false, NodeAccessResolver.satisfied(neither, requirement, curve));
        eq(true, NodeAccessResolver.satisfied(precise, requirement, curve));
        eq(true, NodeAccessResolver.satisfied(penetrating, requirement, curve));
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
