package dev.gustavopere.rpgskilltree.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class TreeDisplayProjector {
    private TreeDisplayProjector() {}

    public static TreeDisplayState project(
        ProgressionState state,
        SkillGraph graph,
        Map<String, NodePurchaseDefinition> definitions
    ) {
        Map<String, NodeAccessRequirement> requirements = new HashMap<>();
        definitions.keySet().forEach(id -> requirements.put(id, NodeAccessRequirement.none()));
        return project(state, graph, definitions, requirements, CharacterLevelCurve.defaultCurve());
    }

    public static TreeDisplayState project(
        ProgressionState state,
        SkillGraph graph,
        Map<String, NodePurchaseDefinition> definitions,
        Map<String, NodeAccessRequirement> requirements,
        CharacterLevelCurve curve
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(graph);
        Objects.requireNonNull(definitions);
        Objects.requireNonNull(requirements);
        Objects.requireNonNull(curve);
        int available = state.passivePoints().available();
        Map<String, NodeDisplayState> projected = new HashMap<>();
        for (var entry : definitions.entrySet()) {
            String nodeId = entry.getKey();
            NodePurchaseDefinition definition = entry.getValue();
            NodeAccessRequirement requirement = requirements.getOrDefault(nodeId, NodeAccessRequirement.none());
            int rank = state.passiveNodes().rank(nodeId);
            boolean learned = rank > 0;
            boolean connected = definition.startingPoint()
                || learned
                || graph.neighbors(nodeId).stream().anyMatch(state.passiveNodes()::learned);
            boolean canPurchase = rank < definition.maxRank()
                && available >= definition.costPerRank()
                && connected
                && NodeAccessResolver.satisfied(state, requirement, curve);
            projected.put(nodeId, new NodeDisplayState(
                nodeId,
                rank,
                definition.maxRank(),
                definition.costPerRank(),
                learned,
                canPurchase,
                learned,
                definition.finalTriadNode()
            ));
        }
        return new TreeDisplayState(available, projected);
    }
}
