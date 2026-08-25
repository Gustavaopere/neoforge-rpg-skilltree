package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Shared server/client progression projection for the frozen A0101-A0150 batch. */
public final class FrozenA0101A0150TreeModel {
    public record Node(
        String code,
        String nodeId,
        String name,
        FrozenSurvivalPerkDefinition.Domain domain,
        FrozenSurvivalPerkDefinition.Family family,
        FrozenSurvivalPerkDefinition.Kind kind,
        int maxRank,
        int costPerRank,
        boolean startingPoint,
        Set<String> requiredSpecializations,
        Set<String> requiredNodes,
        Map<String, Integer> requiredNodeRanks,
        FrozenSurvivalPerkDefinition.SpecialGate specialGate,
        Set<String> neighbors,
        Set<String> domains
    ) {
        public Node {
            requiredSpecializations = Set.copyOf(requiredSpecializations);
            requiredNodes = Set.copyOf(requiredNodes);
            requiredNodeRanks = Map.copyOf(requiredNodeRanks);
            neighbors = Set.copyOf(neighbors);
            domains = Set.copyOf(domains);
        }
    }

    private static final List<Node> ALL = build();
    private static final Map<String, Node> BY_CODE = index();

    private FrozenA0101A0150TreeModel() {}

    public static List<Node> all() { return ALL; }

    public static Optional<Node> node(String code) { return Optional.ofNullable(BY_CODE.get(code)); }

    private static List<Node> build() {
        Map<String, Set<String>> localNeighbors = new HashMap<>();
        FrozenA0101A0150Catalog.all().forEach(definition -> localNeighbors.put(definition.code(), new HashSet<>()));
        FrozenA0101A0150Catalog.all().forEach(definition -> definition.dependencies().keySet().forEach(dependency -> {
            localNeighbors.get(definition.code()).add(dependency);
            Set<String> reverse = localNeighbors.get(dependency);
            if (reverse != null) reverse.add(definition.code());
        }));

        List<Node> result = new ArrayList<>();
        for (FrozenSurvivalPerkDefinition definition : FrozenA0101A0150Catalog.all()) {
            Map<String, Integer> ranked = new LinkedHashMap<>();
            definition.dependencies().forEach((code, rank) ->
                ranked.put(FrozenSurvivalPerkNodeBinding.anyBatchNodeId(code), rank));
            Set<String> neighbors = new HashSet<>();
            localNeighbors.get(definition.code()).forEach(code ->
                neighbors.add(FrozenSurvivalPerkNodeBinding.anyBatchNodeId(code)));
            Set<String> gatewayNodes = new HashSet<>();
            definition.requiredGateways().forEach(domain -> gatewayNodes.add(gatewayNode(domain)));
            Set<String> domains = new HashSet<>();
            definition.requiredGateways().forEach(domain -> domains.add(domain.name()));
            if (domains.isEmpty()) domains.add(definition.domain().name());

            result.add(new Node(
                definition.code(),
                FrozenSurvivalPerkNodeBinding.nodeId(definition.code()),
                definition.name(),
                definition.domain(),
                definition.family(),
                definition.kind(),
                definition.maxRank(),
                definition.rankCost(),
                definition.dependencies().isEmpty(),
                structuralSpecializations(definition.specialGate()),
                gatewayNodes,
                ranked,
                definition.specialGate(),
                neighbors,
                domains
            ));
        }
        return List.copyOf(result);
    }

    public static String gatewayNode(FrozenSurvivalPerkDefinition.Domain domain) {
        return "rpgskilltree:" + domain.name().toLowerCase(java.util.Locale.ROOT) + "_000";
    }

    private static Set<String> structuralSpecializations(FrozenSurvivalPerkDefinition.SpecialGate gate) {
        return switch (gate) {
            case ATTUNEMENT_SOCKET -> Set.of("attunement_socket");
            case FORESTRY_ACCESS -> Set.of("tfc_forestry");
            default -> Set.of();
        };
    }

    private static Map<String, Node> index() {
        LinkedHashMap<String, Node> result = new LinkedHashMap<>();
        ALL.forEach(node -> {
            if (result.put(node.code(), node) != null) throw new IllegalStateException("duplicate " + node.code());
        });
        if (result.size() != 50) throw new IllegalStateException("frozen tree must contain A0101-A0150");
        return Map.copyOf(result);
    }
}
