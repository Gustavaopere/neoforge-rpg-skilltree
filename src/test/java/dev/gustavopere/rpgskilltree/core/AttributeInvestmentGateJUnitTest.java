package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AttributeInvestmentGateJUnitTest {
    private static final String NODE = "rpgskilltree:threshold_test";
    private static final SkillGraph GRAPH = SkillGraph.undirected(List.of());

    private static NodeAccessRequirement requirement() {
        return new NodeAccessRequirement(1, Set.of(), Map.of(), Set.of(), Set.of(), Set.of(),
            Map.of(), Set.of(), Map.of(AttributeId.INTELLIGENCE, 20L, AttributeId.AGILITY, 15L));
    }

    @Test
    void bothCanonicalThresholdsMustHoldWithoutConsumingRanks() {
        NodeAccessRequirement gate = requirement();
        ProgressionState progression = ProgressionState.empty();
        AttributeRanks below = AttributeRanks.of(Map.of(AttributeId.INTELLIGENCE, 20L, AttributeId.AGILITY, 14L));
        AttributeRanks exact = below.increase(AttributeId.AGILITY, 1L);
        CharacterLevelCurve curve = CharacterLevelCurve.defaultCurve();
        assertFalse(NodeAccessResolver.satisfied(progression, gate, curve, below));
        assertFalse(NodeAccessResolver.satisfied(progression, gate, curve));
        assertTrue(NodeAccessResolver.satisfied(progression, gate, curve, exact));
        assertEquals(20L, exact.rank(AttributeId.INTELLIGENCE));
        assertEquals(15L, exact.rank(AttributeId.AGILITY));
        assertThrows(IllegalArgumentException.class, () ->
            new NodeAccessRequirement(1, Set.of(), Map.of(), Set.of(), Set.of(), Set.of(),
                Map.of(), Set.of(), Map.of(AttributeId.AGILITY, -1L)));
    }

    @Test
    void unmetAttributeGateRejectsPurchaseBeforePassiveLedgerDebit() {
        ProgressionState before = ProgressionState.empty().withPassivePoints(
            PassivePointLedger.empty().award(PassivePointSource.LEVEL, 4));
        var definition = new NodePurchaseDefinition(NODE, 1, 2, true);
        var gate = requirement();
        var ranks = AttributeRanks.of(Map.of(AttributeId.INTELLIGENCE, 20L, AttributeId.AGILITY, 14L));

        var rejected = NodePurchaseMutationService.purchase(before, GRAPH, definition,
            NodeAccessResolver.satisfied(before, gate, CharacterLevelCurve.defaultCurve(), ranks));
        assertEquals(NodePurchaseResult.Status.REQUIREMENTS_NOT_SATISFIED, rejected.status());
        assertSame(before, rejected.state());
        assertEquals(4, before.passivePoints().available());

        var eligibleRanks = ranks.increase(AttributeId.AGILITY, 1L);
        var accepted = NodePurchaseMutationService.purchase(before, GRAPH, definition,
            NodeAccessResolver.satisfied(before, gate, CharacterLevelCurve.defaultCurve(), eligibleRanks));
        assertTrue(accepted.accepted());
        assertEquals(2, accepted.state().passivePoints().available());
        assertEquals(20L, eligibleRanks.rank(AttributeId.INTELLIGENCE));
        assertEquals(15L, eligibleRanks.rank(AttributeId.AGILITY));
    }

    @Test
    void losingThresholdReconcilesPurchasedNodeAndRefundsPassivePoints() {
        ProgressionState initial = ProgressionState.empty().withPassivePoints(
            PassivePointLedger.empty().award(PassivePointSource.LEVEL, 5));
        var definition = new NodePurchaseDefinition(NODE, 1, 2, true);
        ProgressionState owned = NodePurchaseMutationService.purchase(initial, GRAPH, definition, true).state();
        var allRequirements = Map.of(NODE, requirement());
        var definitions = Map.of(NODE, definition);
        AttributeRanks eligible = AttributeRanks.of(Map.of(AttributeId.INTELLIGENCE, 20L, AttributeId.AGILITY, 15L));
        var curve = CharacterLevelCurve.defaultCurve();
        var retained = ProgressionService.reconcileInvalidNodes(owned, GRAPH, definitions, allRequirements, curve, eligible);
        assertEquals(1, retained.state().passiveNodes().rank(NODE));

        var lost = ProgressionService.reconcileInvalidNodes(owned, GRAPH, definitions, allRequirements,
            curve, eligible.decrease(AttributeId.AGILITY, 1L));
        assertEquals(0, lost.state().passiveNodes().rank(NODE));
        assertEquals(2, lost.pointsRefunded());
        assertEquals(5, lost.state().passivePoints().available());
    }
}
