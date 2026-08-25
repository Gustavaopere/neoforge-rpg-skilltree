package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Builds the semantic investment view used by specialization and archetype resolvers. */
public final class NodeInvestmentProjection {
    private NodeInvestmentProjection() {}

    public static InvestmentState from(
        PassiveNodeProgress progress,
        Map<String, Set<String>> tagsByNode
    ) {
        Objects.requireNonNull(progress);
        Objects.requireNonNull(tagsByNode);

        var investments = new ArrayList<NodeInvestment>();
        progress.learnedNodeIds().stream().sorted().forEach(nodeId -> {
            Set<String> tags = tagsByNode.getOrDefault(nodeId, Set.of());
            if (!tags.isEmpty()) {
                investments.add(new NodeInvestment(nodeId, Map.of(), tags));
            }
        });
        return InvestmentState.of(investments);
    }
}
