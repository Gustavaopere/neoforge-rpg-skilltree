package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class A0021A0040RespecReconcileTest {
    private static final String A0019 = "rpgskilltree:combat/a0019";
    private static final String A0021 = "rpgskilltree:combat/a0021";
    private static final String A0022 = "rpgskilltree:combat/a0022";

    private A0021A0040RespecReconcileTest() {}

    public static void main(String[] args) {
        ProgressionState state = ProgressionState.empty()
            .withPassivePoints(PassivePointLedger.of(Map.of(PassivePointSource.LEVEL, 3), 3))
            .withPassiveNodes(PassiveNodeProgress.of(Map.of(A0019, 1, A0021, 1, A0022, 1)));

        SkillGraph graph = SkillGraph.undirected(List.of(
            new SkillGraph.Edge(A0019, A0021),
            new SkillGraph.Edge(A0021, A0022)
        ));
        Map<String, NodePurchaseDefinition> definitions = Map.of(
            A0019, new NodePurchaseDefinition(A0019, 3, 1, true),
            A0021, new NodePurchaseDefinition(A0021, 3, 1, false),
            A0022, new NodePurchaseDefinition(A0022, 2, 1, false)
        );
        Map<String, NodeAccessRequirement> requirements = Map.of(
            A0022, new NodeAccessRequirement(
                1, Set.of(), Map.of(), Set.of(), Set.of(), Set.of(), Map.of(A0021, 2), Set.of()
            )
        );

        var result = ProgressionService.reconcileInvalidNodes(
            state, graph, definitions, requirements, CharacterLevelCurve.defaultCurve()
        );
        require(result.state().passiveNodes().rank(A0019) == 1, "family root must remain learned");
        require(result.state().passiveNodes().rank(A0021) == 1, "lower prerequisite rank must remain learned");
        require(result.state().passiveNodes().rank(A0022) == 0, "dependent node must be removed when rank-2 prerequisite is lost");
        require(result.removedRanks().equals(Map.of(A0022, 1)), "only the invalid dependent rank should be removed");
        require(result.pointsRefunded() == 1, "invalid dependent rank must refund its exact cost");
        require(result.state().passivePoints().spent() == 2, "ledger must remain consistent after reconciliation");
        System.out.println("A0021A0040RespecReconcileTest: PASS");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
