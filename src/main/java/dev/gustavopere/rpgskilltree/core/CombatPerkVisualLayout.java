package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Deterministic visual projection of the canonical A0001-A0100 combat graph. */
public final class CombatPerkVisualLayout {
    private static final double X_SPACING = 180.0D;
    private static final double Y_SPACING = 110.0D;
    private static final double COMPONENT_GAP = 260.0D;

    private CombatPerkVisualLayout() {}

    public record Node(String id, double x, double y) {
        public Node {
            x = canonicalZero(x);
            y = canonicalZero(y);
        }
    }
    public record Edge(String from, String to) {}
    public record Layout(List<Node> nodes, List<Edge> edges) {
        public Layout {
            nodes = List.copyOf(nodes);
            edges = List.copyOf(edges);
        }
    }

    public static Layout project() {
        List<CombatPerkTreeModel.Node> canonical = CombatPerkTreeModel.all().stream()
            .sorted(Comparator.comparing(CombatPerkTreeModel.Node::nodeId))
            .toList();
        Map<String, CombatPerkTreeModel.Node> byId = new LinkedHashMap<>();
        canonical.forEach(node -> byId.put(node.nodeId(), node));
        Set<String> ids = Set.copyOf(byId.keySet());

        List<Set<String>> components = connectedComponents(canonical, ids);
        List<Node> projectedNodes = new ArrayList<>();
        double componentOffsetX = 0.0D;

        for (Set<String> component : components) {
            Map<String, Integer> depths = new HashMap<>();
            for (String nodeId : component) {
                depth(nodeId, byId, component, depths, new HashSet<>());
            }

            Map<Integer, List<String>> byDepth = new LinkedHashMap<>();
            component.stream().sorted().forEach(nodeId ->
                byDepth.computeIfAbsent(depths.get(nodeId), ignored -> new ArrayList<>()).add(nodeId)
            );

            int widestLevel = byDepth.values().stream().mapToInt(List::size).max().orElse(1);
            for (Map.Entry<Integer, List<String>> entry : byDepth.entrySet()) {
                List<String> level = entry.getValue();
                double levelWidth = Math.max(0, level.size() - 1) * X_SPACING;
                double componentWidth = Math.max(0, widestLevel - 1) * X_SPACING;
                double startX = componentOffsetX + (componentWidth - levelWidth) / 2.0D;
                for (int index = 0; index < level.size(); index++) {
                    projectedNodes.add(new Node(
                        level.get(index),
                        startX + index * X_SPACING,
                        entry.getKey() * Y_SPACING
                    ));
                }
            }

            componentOffsetX += Math.max(X_SPACING, widestLevel * X_SPACING) + COMPONENT_GAP;
        }

        projectedNodes.sort(Comparator.comparing(Node::id));
        return new Layout(projectedNodes, canonicalEdges(canonical, ids));
    }

    private static double canonicalZero(double value) {
        return value == 0.0D ? 0.0D : value;
    }

    private static List<Set<String>> connectedComponents(
        List<CombatPerkTreeModel.Node> nodes,
        Set<String> combatIds
    ) {
        Set<String> remaining = new LinkedHashSet<>();
        nodes.forEach(node -> remaining.add(node.nodeId()));
        List<Set<String>> components = new ArrayList<>();

        while (!remaining.isEmpty()) {
            String seed = remaining.iterator().next();
            ArrayDeque<String> queue = new ArrayDeque<>();
            LinkedHashSet<String> component = new LinkedHashSet<>();
            queue.add(seed);
            remaining.remove(seed);

            while (!queue.isEmpty()) {
                String current = queue.removeFirst();
                component.add(current);
                CombatPerkTreeModel.Node node = nodes.stream()
                    .filter(candidate -> candidate.nodeId().equals(current))
                    .findFirst()
                    .orElseThrow();
                node.neighbors().stream().filter(combatIds::contains).sorted().forEach(neighbor -> {
                    if (remaining.remove(neighbor)) queue.addLast(neighbor);
                });
            }
            components.add(Set.copyOf(component));
        }

        components.sort(Comparator.comparing(component -> component.stream().min(String::compareTo).orElseThrow()));
        return List.copyOf(components);
    }

    private static int depth(
        String nodeId,
        Map<String, CombatPerkTreeModel.Node> byId,
        Set<String> component,
        Map<String, Integer> memo,
        Set<String> visiting
    ) {
        Integer cached = memo.get(nodeId);
        if (cached != null) return cached;
        if (!visiting.add(nodeId)) {
            throw new IllegalStateException("cycle in canonical combat perk dependencies at " + nodeId);
        }

        CombatPerkTreeModel.Node node = byId.get(nodeId);
        if (node == null) throw new IllegalStateException("unknown canonical combat node " + nodeId);
        int result = 0;
        for (String requiredId : node.requiredNodeRanks().keySet()) {
            if (!component.contains(requiredId)) continue;
            result = Math.max(result, depth(requiredId, byId, component, memo, visiting) + 1);
        }
        visiting.remove(nodeId);
        memo.put(nodeId, result);
        return result;
    }

    private static List<Edge> canonicalEdges(
        List<CombatPerkTreeModel.Node> nodes,
        Set<String> combatIds
    ) {
        Map<String, Edge> edges = new LinkedHashMap<>();
        for (CombatPerkTreeModel.Node node : nodes) {
            for (String neighbor : node.neighbors()) {
                if (!combatIds.contains(neighbor)) continue;
                String first = node.nodeId().compareTo(neighbor) <= 0 ? node.nodeId() : neighbor;
                String second = node.nodeId().compareTo(neighbor) <= 0 ? neighbor : node.nodeId();
                edges.putIfAbsent(first + "|" + second, new Edge(first, second));
            }
        }
        return edges.values().stream()
            .sorted(Comparator.comparing(Edge::from).thenComparing(Edge::to))
            .toList();
    }
}
