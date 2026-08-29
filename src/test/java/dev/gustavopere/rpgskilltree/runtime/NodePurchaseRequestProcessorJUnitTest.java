package dev.gustavopere.rpgskilltree.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import dev.gustavopere.rpgskilltree.core.NodePurchaseDefinition;
import dev.gustavopere.rpgskilltree.core.NodePurchaseResult;
import dev.gustavopere.rpgskilltree.core.PassivePointLedger;
import dev.gustavopere.rpgskilltree.core.PassivePointSource;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.SkillGraph;
import java.util.List;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class NodePurchaseRequestProcessorJUnitTest {
    private static final UUID PLAYER = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final ResourceLocation ROOT = ResourceLocation.parse("rpgskilltree:test_root");
    private static final ResourceLocation OTHER = ResourceLocation.parse("rpgskilltree:test_other");
    private static final SkillGraph GRAPH = SkillGraph.undirected(List.of(new SkillGraph.Edge(ROOT.toString(), OTHER.toString())));

    @Test
    void exactReplayCannotChargeOrIncreaseRankTwice() {
        NodePurchaseRequestProcessor processor = new NodePurchaseRequestProcessor(8);
        ProgressionState initial = withPoints(2);
        NodePurchaseDefinition definition = new NodePurchaseDefinition(ROOT.toString(), 3, 1, true);

        NodePurchaseResult first = processor.purchase(
            PLAYER, "req-1", ROOT, initial, GRAPH, definition, true
        );
        NodePurchaseResult replay = processor.purchase(
            PLAYER, "req-1", ROOT, first.state(), GRAPH, definition, true
        );

        assertEquals(NodePurchaseResult.Status.ACCEPTED, first.status());
        assertEquals(NodePurchaseResult.Status.DUPLICATE_REQUEST, replay.status());
        assertSame(first.state(), replay.state());
        assertEquals(1, replay.state().passiveNodes().rank(ROOT.toString()));
        assertEquals(1, replay.state().passivePoints().spent());
    }

    @Test
    void requestIdConflictFailsClosedWithoutMutation() {
        NodePurchaseRequestProcessor processor = new NodePurchaseRequestProcessor(8);
        ProgressionState initial = withPoints(2);
        NodePurchaseDefinition root = new NodePurchaseDefinition(ROOT.toString(), 3, 1, true);
        NodePurchaseDefinition other = new NodePurchaseDefinition(OTHER.toString(), 3, 1, true);

        NodePurchaseResult first = processor.purchase(
            PLAYER, "req-1", ROOT, initial, GRAPH, root, true
        );
        NodePurchaseResult conflict = processor.purchase(
            PLAYER, "req-1", OTHER, first.state(), GRAPH, other, true
        );

        assertEquals(NodePurchaseResult.Status.REQUEST_ID_CONFLICT, conflict.status());
        assertSame(first.state(), conflict.state());
        assertEquals(0, conflict.state().passiveNodes().rank(OTHER.toString()));
        assertEquals(1, conflict.state().passivePoints().spent());
    }

    private static ProgressionState withPoints(int points) {
        return ProgressionState.empty().withPassivePoints(
            PassivePointLedger.empty().award(PassivePointSource.LEVEL, points)
        );
    }
}
