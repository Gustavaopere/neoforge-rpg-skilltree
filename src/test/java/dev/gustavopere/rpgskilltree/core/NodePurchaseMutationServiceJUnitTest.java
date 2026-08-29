package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

final class NodePurchaseMutationServiceJUnitTest {
    private static final String ROOT = "rpgskilltree:test_root";
    private static final String CHILD = "rpgskilltree:test_child";
    private static final SkillGraph GRAPH = SkillGraph.undirected(List.of(new SkillGraph.Edge(ROOT, CHILD)));

    @Test
    void acceptedPurchaseChargesExactlyOnceAndRaisesExactlyOneRank() {
        ProgressionState before = withPoints(3);
        NodePurchaseDefinition definition = new NodePurchaseDefinition(ROOT, 3, 1, true);

        NodePurchaseResult result = NodePurchaseMutationService.purchase(
            before,
            GRAPH,
            definition,
            true
        );

        assertTrue(result.accepted());
        assertEquals(NodePurchaseResult.Status.ACCEPTED, result.status());
        assertEquals(1, result.state().passiveNodes().rank(ROOT));
        assertEquals(1, result.state().passivePoints().spent());
        assertEquals(2, result.state().passivePoints().available());
    }

    @Test
    void insufficientPointsRejectsWithoutMutatingState() {
        ProgressionState before = ProgressionState.empty();
        NodePurchaseDefinition definition = new NodePurchaseDefinition(ROOT, 3, 1, true);

        NodePurchaseResult result = NodePurchaseMutationService.purchase(before, GRAPH, definition, true);

        assertFalse(result.accepted());
        assertEquals(NodePurchaseResult.Status.INSUFFICIENT_POINTS, result.status());
        assertSame(before, result.state());
    }

    @Test
    void unmetRequirementsRejectBeforeCharging() {
        ProgressionState before = withPoints(3);
        NodePurchaseDefinition definition = new NodePurchaseDefinition(ROOT, 3, 1, true);

        NodePurchaseResult result = NodePurchaseMutationService.purchase(before, GRAPH, definition, false);

        assertEquals(NodePurchaseResult.Status.REQUIREMENTS_NOT_SATISFIED, result.status());
        assertSame(before, result.state());
        assertEquals(0, before.passivePoints().spent());
    }

    @Test
    void disconnectedNodeRejectsBeforeCharging() {
        ProgressionState before = withPoints(3);
        NodePurchaseDefinition definition = new NodePurchaseDefinition(CHILD, 3, 1, false);

        NodePurchaseResult result = NodePurchaseMutationService.purchase(before, GRAPH, definition, true);

        assertEquals(NodePurchaseResult.Status.NOT_CONNECTED, result.status());
        assertSame(before, result.state());
    }

    @Test
    void maxRankRejectsWithoutExtraCharge() {
        ProgressionState state = withPoints(3);
        NodePurchaseDefinition definition = new NodePurchaseDefinition(ROOT, 1, 1, true);
        state = NodePurchaseMutationService.purchase(state, GRAPH, definition, true).state();

        NodePurchaseResult rejected = NodePurchaseMutationService.purchase(state, GRAPH, definition, true);

        assertEquals(NodePurchaseResult.Status.MAX_RANK_REACHED, rejected.status());
        assertSame(state, rejected.state());
        assertEquals(1, rejected.state().passivePoints().spent());
    }

    @Test
    void resultExposesStableReadableReasonKey() {
        NodePurchaseResult result = NodePurchaseMutationService.purchase(
            ProgressionState.empty(),
            GRAPH,
            new NodePurchaseDefinition(ROOT, 1, 1, true),
            true
        );
        assertEquals("purchase.rpgskilltree.insufficient_points", result.messageKey());
    }

    private static ProgressionState withPoints(int points) {
        return ProgressionState.empty().withPassivePoints(
            PassivePointLedger.empty().award(PassivePointSource.LEVEL, points)
        );
    }
}
