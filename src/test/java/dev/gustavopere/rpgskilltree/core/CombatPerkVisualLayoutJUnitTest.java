package dev.gustavopere.rpgskilltree.core;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CombatPerkVisualLayoutJUnitTest {
    @Test
    void projectionMatchesCanonicalCombatModelExactly() {
        CombatPerkVisualLayout.Layout layout = CombatPerkVisualLayout.project();
        Set<String> expectedIds = CombatPerkTreeModel.all().stream()
            .map(CombatPerkTreeModel.Node::nodeId)
            .collect(Collectors.toSet());
        Set<String> actualIds = layout.nodes().stream()
            .map(CombatPerkVisualLayout.Node::id)
            .collect(Collectors.toSet());

        assertEquals(100, layout.nodes().size());
        assertEquals(100, actualIds.size());
        assertEquals(expectedIds, actualIds);
    }

    @Test
    void projectionUsesOnlyCanonicalCombatAdjacency() {
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

        assertEquals(layout.edges().size(), actualEdges.size());
        assertEquals(expectedEdges, actualEdges);
    }

    @Test
    void projectionIsDeterministicFiniteAndNonOverlapping() {
        CombatPerkVisualLayout.Layout first = CombatPerkVisualLayout.project();
        assertEquals(first, CombatPerkVisualLayout.project());

        Set<Coordinate> coordinates = new HashSet<>();
        for (CombatPerkVisualLayout.Node node : first.nodes()) {
            assertTrue(Double.isFinite(node.x()));
            assertTrue(Double.isFinite(node.y()));
            assertTrue(coordinates.add(Coordinate.of(node.x(), node.y())));
        }
    }

    @Test
    void visualNodeCanonicalizesSignedZeroCoordinates() {
        CombatPerkVisualLayout.Node node = new CombatPerkVisualLayout.Node("test", -0.0D, -0.0D);

        assertEquals(Double.doubleToRawLongBits(0.0D), Double.doubleToRawLongBits(node.x()));
        assertEquals(Double.doubleToRawLongBits(0.0D), Double.doubleToRawLongBits(node.y()));
    }

    private static String edgeKey(String first, String second) {
        return first.compareTo(second) <= 0 ? first + "|" + second : second + "|" + first;
    }

    private record Coordinate(long xBits, long yBits) {
        private static Coordinate of(double x, double y) {
            return new Coordinate(
                Double.doubleToLongBits(normalizeZero(x)),
                Double.doubleToLongBits(normalizeZero(y))
            );
        }

        private static double normalizeZero(double value) {
            return value == 0.0D ? 0.0D : value;
        }
    }
}
