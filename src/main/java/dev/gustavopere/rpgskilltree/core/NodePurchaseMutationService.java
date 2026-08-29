package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Pure atomic node-purchase mutation with explicit rejection reasons. */
public final class NodePurchaseMutationService {
    private NodePurchaseMutationService() {}

    public static NodePurchaseResult purchase(
        ProgressionState state,
        SkillGraph graph,
        NodePurchaseDefinition definition,
        boolean requirementsSatisfied
    ) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(graph, "graph");
        Objects.requireNonNull(definition, "definition");

        if (!requirementsSatisfied) {
            return NodePurchaseResult.rejected(
                state,
                NodePurchaseResult.Status.REQUIREMENTS_NOT_SATISFIED
            );
        }

        int currentRank = state.passiveNodes().rank(definition.nodeId());
        if (currentRank >= definition.maxRank()) {
            return NodePurchaseResult.rejected(state, NodePurchaseResult.Status.MAX_RANK_REACHED);
        }

        if (currentRank == 0 && !definition.startingPoint()) {
            boolean connected = graph.neighbors(definition.nodeId()).stream()
                .anyMatch(state.passiveNodes()::learned);
            if (!connected) {
                return NodePurchaseResult.rejected(state, NodePurchaseResult.Status.NOT_CONNECTED);
            }
        }

        if (state.passivePoints().available() < definition.costPerRank()) {
            return NodePurchaseResult.rejected(state, NodePurchaseResult.Status.INSUFFICIENT_POINTS);
        }

        PassivePointLedger ledger = state.passivePoints().spend(definition.costPerRank());
        PassiveNodeProgress nodes = state.passiveNodes().increase(
            definition.nodeId(),
            definition.maxRank()
        );
        FinalTriadProgress triads = state.finalTriads();
        if (definition.finalTriadNode()) {
            triads = triads.increase(definition.finalTriadDomain(), definition.finalTriadSlot());
        }

        return NodePurchaseResult.accepted(new ProgressionState(
            state.totalCharacterXp(),
            ledger,
            state.bossProgress(),
            state.classProgression(),
            state.mastery(),
            state.classChoices(),
            state.specializations(),
            triads,
            nodes,
            state.discoveries()
        ));
    }
}
