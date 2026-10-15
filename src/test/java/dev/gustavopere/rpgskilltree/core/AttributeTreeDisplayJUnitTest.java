package dev.gustavopere.rpgskilltree.core;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AttributeTreeDisplayJUnitTest {
    private static final String NODE = "rpgskilltree:attribute_test";
    private static final SkillGraph GRAPH = SkillGraph.undirected(List.of());
    private static final Map<String, NodePurchaseDefinition> DEFINITIONS =
        Map.of(NODE, new NodePurchaseDefinition(NODE, 1, 1, true));
    private static final Map<String, NodeAccessRequirement> REQUIREMENTS = Map.of(
        NODE, new NodeAccessRequirement(1, Set.of(), Map.of(), Set.of(), Set.of(), Set.of(),
            Map.of(), Set.of(), Map.of(AttributeId.INTELLIGENCE, 20L, AttributeId.AGILITY, 15L))
    );

    @Test
    void previewUsesInvestedRanksWithoutConsumingThem() {
        ProgressionState state = ProgressionState.empty().withPassivePoints(
            PassivePointLedger.empty().award(PassivePointSource.LEVEL, 3));
        AttributeRanks below = AttributeRanks.of(Map.of(AttributeId.INTELLIGENCE, 20L, AttributeId.AGILITY, 14L));
        AttributeRanks exact = below.increase(AttributeId.AGILITY, 1L);
        assertFalse(TreeDisplayProjector.project(state, GRAPH, DEFINITIONS, REQUIREMENTS,
            CharacterLevelCurve.defaultCurve(), below).nodes().get(NODE).canPurchase());
        assertTrue(TreeDisplayProjector.project(state, GRAPH, DEFINITIONS, REQUIREMENTS,
            CharacterLevelCurve.defaultCurve(), exact).nodes().get(NODE).canPurchase());
        assertFalse(TreeDisplayProjector.project(state, GRAPH, DEFINITIONS, REQUIREMENTS,
            CharacterLevelCurve.defaultCurve()).nodes().get(NODE).canPurchase(),
            "a client without a Core snapshot must fail closed");
        assertEquals(20L, exact.rank(AttributeId.INTELLIGENCE));
        assertEquals(3, state.passivePoints().available());
    }
}
