package dev.gustavopere.rpgskilltree.core;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class CombatPerkVisualLayoutTest {
    private CombatPerkVisualLayoutTest() {}

    public static void main(String[] args) {
        projectionContainsEveryCanonicalCombatNodeExactlyOnce();
        projectionPreservesCanonicalCombatAdjacencyOnly();
        projectionIsDeterministicAndNonOverlapping();
        System.out.println("CombatPerkVisualLayoutTest: PASS");
    }

    private static void projectionContainsEveryCanonicalCombatNodeExactlyOnce() {
        CombatPerkVisualLayout.Layout layout = CombatPerkVisualLayout.project();
        Set<String> expectedIds = CombatPerkTreeModel.all().stream()
            .map(CombatPerkTreeModel.Node::nodeId)
            .collect(Collectors.toSet());
        Set<String> actualIds = layout.nodes().stream()
            .map(CombatPerkVisualLayout.Node::id)
            .collect(Collectors.toSet());

        require(layout.nodes().size() == 100, "combat layout must contain exactly A0001-A0100");
        require(actualIds.size() == layout.nodes().size(), "combat layout node ids must be unique");
        require(actualIds.equals(expectedIds), "combat layout ids must match CombatPerkTreeModel exactly");
    }

    private static void projectionPreservesCanonicalCombatAdjacencyOnly() {
        CombatPerkVisualLayout.Layout layout = CombatPerkVisualLayout.project();
        Set<String> combatIds = CombatPerkTreeModel.all().stream()
            .map(CombatPerkTreeModel.Node::nodeId)
            .collect(Collectors.toSet());

        Set<String> expectedEdges = new HashSet<>();
        for (CombatPerkTreeModel.Node node : CombatPerkTreeModel.all()) {
            for (String neighbor : node.neighbors()) {
                if (combatIds.contains(neighbor)) expectedEdges.add(edgeKey(node.nodeId(), neighbor));
            }
        }
        Set<String> actualEdges = layout.edges().stream()
            .map(edge -> edgeKey(edge.from(), edge.to()))
            .collect(Collectors.toSet());

        require(actualEdges.size() == layout.edges().size(), "combat layout edges must be deduplicated");
        require(actualEdges.equals(expectedEdges), "combat layout must preserve only canonical combat-to-combat adjacency");
    }

    private static void projectionIsDeterministicAndNonOverlapping() {
        CombatPerkVisualLayout.Layout first = CombatPerkVisualLayout.project();
        CombatPerkVisualLayout.Layout second = CombatPerkVisualLayout.project();
        require(first.equals(second), "combat layout projection must be deterministic");

        Set<String> coordinates = new HashSet<>();
        for (CombatPerkVisualLayout.Node node : first.nodes()) {
            require(Double.isFinite(node.x()) && Double.isFinite(node.y()), "combat layout positions must be finite");
            require(coordinates.add(node.x() + ":" + node.y()), "combat layout nodes must not overlap exactly");
        }
    }

    private static String edgeKey(String first, String second) {
        return first.compareTo(second) <= 0 ? first + "|" + second : second + "|" + first;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
