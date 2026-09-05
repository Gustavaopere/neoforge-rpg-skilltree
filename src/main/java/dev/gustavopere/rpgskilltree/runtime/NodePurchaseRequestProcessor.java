package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.NodePurchaseDefinition;
import dev.gustavopere.rpgskilltree.core.NodePurchaseMutationService;
import dev.gustavopere.rpgskilltree.core.NodePurchaseResult;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.SkillGraph;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;

/** Composes bounded replay protection with the pure atomic purchase mutation. */
public final class NodePurchaseRequestProcessor {
    private final NodePurchaseRequestTracker tracker;

    public NodePurchaseRequestProcessor(int maxRequestsPerPlayer) {
        this.tracker = new NodePurchaseRequestTracker(maxRequestsPerPlayer);
    }

    public NodePurchaseResult purchase(
        UUID playerId,
        String requestId,
        ResourceLocation nodeId,
        ProgressionState current,
        SkillGraph graph,
        NodePurchaseDefinition definition,
        boolean requirementsSatisfied
    ) {
        Objects.requireNonNull(playerId, "playerId");
        Objects.requireNonNull(requestId, "requestId");
        Objects.requireNonNull(nodeId, "nodeId");
        Objects.requireNonNull(current, "current");
        Objects.requireNonNull(graph, "graph");
        Objects.requireNonNull(definition, "definition");

        // Mandatory provider/binding availability is evaluated before reserving the idempotency
        // key and before any point/rank mutation. Unavailable nodes cannot become ghost ranks.
        if (!CombatPerkAvailabilityRuntime.isAvailable(nodeId)) {
            return NodePurchaseResult.rejected(current, NodePurchaseResult.Status.UNAVAILABLE_NODE);
        }

        NodePurchaseRequestTracker.Decision decision = tracker.checkAndRecord(
            playerId,
            requestId,
            nodeId
        );
        if (decision == NodePurchaseRequestTracker.Decision.REPLAY) {
            return NodePurchaseResult.rejected(current, NodePurchaseResult.Status.DUPLICATE_REQUEST);
        }
        if (decision == NodePurchaseRequestTracker.Decision.CONFLICT) {
            return NodePurchaseResult.rejected(current, NodePurchaseResult.Status.REQUEST_ID_CONFLICT);
        }
        return NodePurchaseMutationService.purchase(
            current,
            graph,
            definition,
            requirementsSatisfied
        );
    }

    public void clear(UUID playerId) {
        tracker.clear(playerId);
    }

    public void clearAll() {
        tracker.clearAll();
    }
}
