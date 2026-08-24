package dev.gustavopere.rpgskilltree.runtime.data;

import dev.gustavopere.rpgskilltree.core.NodeAccessRequirement;
import dev.gustavopere.rpgskilltree.core.NodePurchaseDefinition;
import dev.gustavopere.rpgskilltree.core.NodeSpecializationGrant;
import dev.gustavopere.rpgskilltree.core.SkillGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public final class TreeRuleCatalog {
    public record NodeRule(
        ResourceLocation id,
        NodePurchaseDefinition definition,
        NodeAccessRequirement requirement,
        NodeSpecializationGrant specializationGrant,
        Set<ResourceLocation> neighbors
    ) {
        public NodeRule {
            Objects.requireNonNull(id);
            Objects.requireNonNull(definition);
            Objects.requireNonNull(requirement);
            Objects.requireNonNull(neighbors);
            if (!definition.nodeId().equals(id.toString())) {
                throw new IllegalArgumentException("node rule definition id mismatch: " + id);
            }
            neighbors = Set.copyOf(neighbors);
        }
    }

    private static volatile Map<ResourceLocation, NodePurchaseDefinition> definitions = Map.of();
    private static volatile Map<ResourceLocation, NodeAccessRequirement> requirements = Map.of();
    private static volatile List<NodeSpecializationGrant> specializationGrants = List.of();
    private static volatile SkillGraph graph = SkillGraph.undirected(List.of());

    private TreeRuleCatalog() {}

    public static synchronized void replace(List<NodeRule> rules) {
        Objects.requireNonNull(rules);
        Map<ResourceLocation, NodePurchaseDefinition> nextDefinitions = new HashMap<>();
        Map<ResourceLocation, NodeAccessRequirement> nextRequirements = new HashMap<>();
        Set<SkillGraph.Edge> nextEdges = new HashSet<>();
        List<NodeSpecializationGrant> nextSpecializationGrants = new ArrayList<>();
        Set<ResourceLocation> knownIds = new HashSet<>();
        rules.forEach(rule -> knownIds.add(rule.id()));

        for (NodeRule rule : rules) {
            if (nextDefinitions.put(rule.id(), rule.definition()) != null) {
                throw new IllegalArgumentException("duplicate node rule: " + rule.id());
            }
            nextRequirements.put(rule.id(), rule.requirement());
            if (rule.specializationGrant() != null) {
                nextSpecializationGrants.add(rule.specializationGrant());
            }
            for (String requiredNode : rule.requirement().requiredNodeIds()) {
                validateRequiredNode(rule.id(), requiredNode, knownIds);
            }
            for (String requiredNode : rule.requirement().requiredNodeRanks().keySet()) {
                validateRequiredNode(rule.id(), requiredNode, knownIds);
            }
            for (ResourceLocation neighbor : rule.neighbors()) {
                if (!knownIds.contains(neighbor)) {
                    throw new IllegalArgumentException("unknown node rule neighbor: " + rule.id() + " -> " + neighbor);
                }
                String a = rule.id().toString();
                String b = neighbor.toString();
                if (a.compareTo(b) <= 0) nextEdges.add(new SkillGraph.Edge(a, b));
                else nextEdges.add(new SkillGraph.Edge(b, a));
            }
        }

        definitions = Map.copyOf(nextDefinitions);
        requirements = Map.copyOf(nextRequirements);
        specializationGrants = List.copyOf(nextSpecializationGrants);
        graph = SkillGraph.undirected(new ArrayList<>(nextEdges));
    }

    private static void validateRequiredNode(
        ResourceLocation owner,
        String requiredNode,
        Set<ResourceLocation> knownIds
    ) {
        ResourceLocation requiredId = ResourceLocation.parse(requiredNode);
        if (!knownIds.contains(requiredId)) {
            throw new IllegalArgumentException("unknown required node: " + owner + " -> " + requiredId);
        }
        if (requiredId.equals(owner)) {
            throw new IllegalArgumentException("node cannot require itself: " + owner);
        }
    }

    public static Optional<NodePurchaseDefinition> definition(ResourceLocation nodeId) {
        return Optional.ofNullable(definitions.get(nodeId));
    }

    public static NodeAccessRequirement requirement(ResourceLocation nodeId) {
        return requirements.getOrDefault(nodeId, NodeAccessRequirement.none());
    }

    public static SkillGraph graph() {
        return graph;
    }

    public static List<NodeSpecializationGrant> specializationGrants() {
        return specializationGrants;
    }

    public static Map<String, NodePurchaseDefinition> definitions() {
        Map<String, NodePurchaseDefinition> byId = new HashMap<>();
        definitions.forEach((id, definition) -> byId.put(id.toString(), definition));
        return Map.copyOf(byId);
    }

    public static Map<String, NodeAccessRequirement> requirements() {
        Map<String, NodeAccessRequirement> byId = new HashMap<>();
        requirements.forEach((id, requirement) -> byId.put(id.toString(), requirement));
        return Map.copyOf(byId);
    }

    public static int size() {
        return definitions.size();
    }
}
