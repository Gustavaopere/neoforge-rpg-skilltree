package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.core.CombatPerkTreeModel;
import dev.gustavopere.rpgskilltree.core.CombatPerkVisualLayout;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CombatPerkClientTreeJUnitTest {
    @Test
    void clientCombatTreeMirrorsServerAuthoritativeDefinitionsAndRequirements() {
        ClientTreeLayout layout = ClientTreeLayout.combatPerks();
        Map<String, CombatPerkTreeModel.Node> canonical = CombatPerkTreeModel.all().stream()
            .collect(Collectors.toMap(CombatPerkTreeModel.Node::nodeId, node -> node));

        assertEquals("rpgskilltree:runtime/combat_perks", layout.id());
        assertEquals(100, layout.nodes().size());
        assertEquals(canonical.keySet(), layout.nodes().stream().map(ClientTreeLayout.Node::id).collect(Collectors.toSet()));

        for (ClientTreeLayout.Node client : layout.nodes()) {
            CombatPerkTreeModel.Node server = canonical.get(client.id());
            assertEquals(server.maxRank(), client.maxRank(), client.id());
            assertEquals(server.costPerRank(), client.costPerRank(), client.id());
            assertEquals(server.startingPoint(), client.startingPoint(), client.id());
            assertEquals(server.minCharacterLevel(), client.requirement().minCharacterLevel(), client.id());
            assertEquals(server.requiredMastery(), client.requirement().requiredMastery(), client.id());
            assertEquals(server.requiredNodeRanks(), client.requirement().requiredNodeRanks(), client.id());
            assertEquals(Set.of(), client.requirement().requiredNodeIds(), client.id());
        }
    }

    @Test
    void clientCombatTreeUsesDeterministicCanonicalVisualProjection() {
        ClientTreeLayout layout = ClientTreeLayout.combatPerks();
        CombatPerkVisualLayout.Layout projected = CombatPerkVisualLayout.project();

        assertEquals(
            projected.nodes().stream().collect(Collectors.toMap(CombatPerkVisualLayout.Node::id, node -> node.x() + ":" + node.y())),
            layout.nodes().stream().collect(Collectors.toMap(ClientTreeLayout.Node::id, node -> node.x() + ":" + node.y()))
        );
        assertEquals(
            projected.edges().stream().map(edge -> edge.from() + "|" + edge.to()).collect(Collectors.toSet()),
            layout.edges().stream().map(edge -> edge.from() + "|" + edge.to()).collect(Collectors.toSet())
        );
    }

    @Test
    void canonicalCombatTreeIsAlwaysReachableFromTreeNavigation() {
        assertTrue(
            ClientTreeLayout.availableFor(ProgressionState.empty()).stream()
                .anyMatch(layout -> layout == ClientTreeLayout.combatPerks()),
            "canonical combat perks must be reachable without unlocking an unrelated paid class"
        );
    }
}
