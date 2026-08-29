package dev.gustavopere.rpgskilltree.runtime.data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

/** Structural validator for loaded skill-tree acquisition graphs. */
public final class SkillTreeTopologyValidator {
    private SkillTreeTopologyValidator() {}

    public static void validate(
        List<TreeRuleCatalog.NodeRule> rules,
        Map<ResourceLocation, ResourceLocation> treeIdsByNode,
        Map<ResourceLocation, ResourceLocation> sourceByNode
    ) {
        Objects.requireNonNull(rules, "rules");
        Objects.requireNonNull(treeIdsByNode, "treeIdsByNode");
        Objects.requireNonNull(sourceByNode, "sourceByNode");

        Map<ResourceLocation, TreeRuleCatalog.NodeRule> byId = new LinkedHashMap<>();
        for (TreeRuleCatalog.NodeRule rule : rules) {
            if (byId.put(rule.id(), rule) != null) {
                throw failure(source(rule.id(), sourceByNode), rule.id(), "id", "duplicate node id in topology candidate");
            }
            if (!treeIdsByNode.containsKey(rule.id())) {
                throw failure(source(rule.id(), sourceByNode), rule.id(), "treeId", "node is missing a tree assignment");
            }
        }

        validateReciprocalNeighbors(rules, byId, sourceByNode);
        validateReachability(rules, byId, treeIdsByNode, sourceByNode);
        validateRequirementAcyclicity(rules, byId, sourceByNode);
    }

    private static void validateReciprocalNeighbors(
        List<TreeRuleCatalog.NodeRule> rules,
        Map<ResourceLocation, TreeRuleCatalog.NodeRule> byId,
        Map<ResourceLocation, ResourceLocation> sourceByNode
    ) {
        for (TreeRuleCatalog.NodeRule rule : rules) {
            for (ResourceLocation neighborId : rule.neighbors()) {
                TreeRuleCatalog.NodeRule neighbor = byId.get(neighborId);
                if (neighbor == null) {
                    throw failure(source(rule.id(), sourceByNode), rule.id(), "neighbors",
                        "unknown neighbor " + neighborId);
                }
                if (!neighbor.neighbors().contains(rule.id())) {
                    throw failure(source(rule.id(), sourceByNode), rule.id(), "neighbors",
                        "neighbor relation must be reciprocal: " + rule.id() + " -> " + neighborId);
                }
            }
        }
    }

    private static void validateReachability(
        List<TreeRuleCatalog.NodeRule> rules,
        Map<ResourceLocation, TreeRuleCatalog.NodeRule> byId,
        Map<ResourceLocation, ResourceLocation> treeIdsByNode,
        Map<ResourceLocation, ResourceLocation> sourceByNode
    ) {
        Map<ResourceLocation, List<TreeRuleCatalog.NodeRule>> byTree = new LinkedHashMap<>();
        for (TreeRuleCatalog.NodeRule rule : rules) {
            byTree.computeIfAbsent(treeIdsByNode.get(rule.id()), ignored -> new ArrayList<>()).add(rule);
        }

        for (Map.Entry<ResourceLocation, List<TreeRuleCatalog.NodeRule>> entry : byTree.entrySet()) {
            ResourceLocation treeId = entry.getKey();
            List<TreeRuleCatalog.NodeRule> treeRules = entry.getValue();
            ArrayDeque<ResourceLocation> pending = new ArrayDeque<>();
            Set<ResourceLocation> reached = new HashSet<>();

            for (TreeRuleCatalog.NodeRule rule : treeRules) {
                if (rule.definition().startingPoint()) {
                    pending.add(rule.id());
                    reached.add(rule.id());
                }
            }

            if (pending.isEmpty() && !treeRules.isEmpty()) {
                TreeRuleCatalog.NodeRule first = treeRules.getFirst();
                throw failure(source(first.id(), sourceByNode), first.id(), "graph",
                    "tree " + treeId + " has no startingPoint root");
            }

            while (!pending.isEmpty()) {
                ResourceLocation current = pending.removeFirst();
                TreeRuleCatalog.NodeRule currentRule = byId.get(current);
                for (ResourceLocation neighbor : currentRule.neighbors()) {
                    if (!treeId.equals(treeIdsByNode.get(neighbor))) continue;
                    if (reached.add(neighbor)) pending.addLast(neighbor);
                }
            }

            for (TreeRuleCatalog.NodeRule rule : treeRules) {
                if (!reached.contains(rule.id())) {
                    throw failure(source(rule.id(), sourceByNode), rule.id(), "graph",
                        "node is unreachable from any startingPoint in tree " + treeId);
                }
            }
        }
    }

    private static void validateRequirementAcyclicity(
        List<TreeRuleCatalog.NodeRule> rules,
        Map<ResourceLocation, TreeRuleCatalog.NodeRule> byId,
        Map<ResourceLocation, ResourceLocation> sourceByNode
    ) {
        Map<ResourceLocation, Integer> state = new HashMap<>();
        ArrayDeque<ResourceLocation> path = new ArrayDeque<>();
        for (TreeRuleCatalog.NodeRule rule : rules) {
            if (state.getOrDefault(rule.id(), 0) == 0) {
                visitRequirements(rule.id(), byId, sourceByNode, state, path);
            }
        }
    }

    private static void visitRequirements(
        ResourceLocation nodeId,
        Map<ResourceLocation, TreeRuleCatalog.NodeRule> byId,
        Map<ResourceLocation, ResourceLocation> sourceByNode,
        Map<ResourceLocation, Integer> state,
        ArrayDeque<ResourceLocation> path
    ) {
        state.put(nodeId, 1);
        path.addLast(nodeId);
        TreeRuleCatalog.NodeRule rule = byId.get(nodeId);

        Set<ResourceLocation> dependencies = new HashSet<>();
        for (String required : rule.requirement().requiredNodeIds()) {
            dependencies.add(ResourceLocation.parse(required));
        }
        for (String required : rule.requirement().requiredNodeRanks().keySet()) {
            dependencies.add(ResourceLocation.parse(required));
        }

        for (ResourceLocation dependency : dependencies) {
            if (!byId.containsKey(dependency)) continue;
            int dependencyState = state.getOrDefault(dependency, 0);
            if (dependencyState == 1) {
                throw failure(source(nodeId, sourceByNode), nodeId, "requirements",
                    "requirement cycle detected: " + cyclePath(path, dependency));
            }
            if (dependencyState == 0) {
                visitRequirements(dependency, byId, sourceByNode, state, path);
            }
        }

        path.removeLast();
        state.put(nodeId, 2);
    }

    private static String cyclePath(ArrayDeque<ResourceLocation> path, ResourceLocation repeated) {
        List<ResourceLocation> cycle = new ArrayList<>();
        boolean copying = false;
        for (ResourceLocation node : path) {
            if (node.equals(repeated)) copying = true;
            if (copying) cycle.add(node);
        }
        cycle.add(repeated);
        return String.join(" -> ", cycle.stream().map(ResourceLocation::toString).toList());
    }

    private static ResourceLocation source(
        ResourceLocation nodeId,
        Map<ResourceLocation, ResourceLocation> sourceByNode
    ) {
        return sourceByNode.getOrDefault(nodeId, ResourceLocation.parse("rpgskilltree:runtime/topology"));
    }

    private static SkillTreeDataValidationException failure(
        ResourceLocation source,
        ResourceLocation nodeId,
        String field,
        String detail
    ) {
        return new SkillTreeDataValidationException(source, nodeId.toString(), field, detail);
    }
}
