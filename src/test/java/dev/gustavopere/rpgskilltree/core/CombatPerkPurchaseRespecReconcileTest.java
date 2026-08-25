package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** P-0014 regression matrix for A0001-A0050 purchase/respec/reconciliation. */
public final class CombatPerkPurchaseRespecReconcileTest {
    private static final CharacterLevelCurve CURVE = CharacterLevelCurve.defaultCurve();
    private static final SkillGraph GRAPH = graph();
    private static final Map<String, NodePurchaseDefinition> DEFINITIONS = definitions();
    private static final Map<String, NodeAccessRequirement> REQUIREMENTS = requirements();

    public static void main(String[] args) {
        rootAndRankedDependencyGatesAreAuthoritative();
        cascadeRespecRefundsExactKnownCosts();
        reloadInvalidatesKnownSubtreeWithoutCorruption();
        nodeSpecializationReconcilePreservesExternalSpecializations();
        missingDefinitionFailsClosedForAdministrativeReconciliation();
        System.out.println("CombatPerkPurchaseRespecReconcileTest: PASS");
    }

    private static void rootAndRankedDependencyGatesAreAuthoritative() {
        ProgressionState noGate = baseState(MasteryState.empty(), SpecializationProgressionState.empty());
        require(!NodeAccessResolver.satisfied(noGate, REQUIREMENTS.get(id("A0001")), CURVE),
            "A0001 root must reject missing specialization/mastery gate");

        ProgressionState state = swordEligibleState();
        require(NodeAccessResolver.satisfied(state, REQUIREMENTS.get(id("A0001")), CURVE),
            "A0001 root must accept frozen level/specialization/mastery gate");
        state = purchase(state, "A0001");
        require(!NodeAccessResolver.satisfied(state, REQUIREMENTS.get(id("A0002")), CURVE),
            "A0002 must reject A0001 rank 1 when rank 2 is required");
        state = purchase(state, "A0001");
        require(NodeAccessResolver.satisfied(state, REQUIREMENTS.get(id("A0002")), CURVE),
            "A0002 must unlock exactly when A0001 reaches required rank 2");
        state = purchase(state, "A0002");
        require(state.passiveNodes().rank(id("A0002")) == 1, "ranked dependent purchase applies exactly once");
        require(state.passivePoints().spent() == 3, "known purchases spend exact frozen per-rank costs");
    }

    private static void cascadeRespecRefundsExactKnownCosts() {
        ProgressionState state = swordEligibleState();
        state = purchase(state, "A0001");
        state = purchase(state, "A0001");
        state = purchase(state, "A0002");

        NodeRespecResult first = ProgressionService.respecNode(state, GRAPH, DEFINITIONS, id("A0001"));
        require(first.state().passiveNodes().rank(id("A0001")) == 1,
            "rank>1 respec removes only one rank before topology changes");
        require(first.pointsRefunded() == 1, "single known rank refunds its exact current cost");

        NodeRespecResult cascade = ProgressionService.respecNode(first.state(), GRAPH, DEFINITIONS, id("A0001"));
        require(cascade.state().passiveNodes().rank(id("A0001")) == 0, "root leaves tree on final-rank respec");
        require(cascade.state().passiveNodes().rank(id("A0002")) == 0, "orphaned dependent subtree is removed");
        require(cascade.removedRanks().equals(Map.of(id("A0001"), 1, id("A0002"), 1)),
            "cascade reports every known removed rank");
        require(cascade.pointsRefunded() == 2, "cascade refunds exact known costs without duplication");
        require(cascade.state().passivePoints().spent() == 0, "complete cascade restores all three spent points");
    }

    private static void reloadInvalidatesKnownSubtreeWithoutCorruption() {
        ProgressionState state = swordEligibleState();
        state = purchase(state, "A0001");
        state = purchase(state, "A0001");
        state = purchase(state, "A0002");

        Map<String, NodeAccessRequirement> reloaded = new HashMap<>(REQUIREMENTS);
        NodeAccessRequirement root = reloaded.get(id("A0001"));
        reloaded.put(id("A0001"), new NodeAccessRequirement(
            root.minCharacterLevel(), root.requiredClassIds(), Map.of("epicfight:sword", 101),
            root.requiredSpecializationIds(), root.requiredClassChoiceIds(), root.requiredNodeIds(),
            root.requiredNodeRanks(), root.requiredDiscoveryKeys()
        ));

        NodeAccessReconcileResult result = ProgressionService.reconcileInvalidNodes(
            state, GRAPH, DEFINITIONS, Map.copyOf(reloaded), CURVE);
        require(result.state().passiveNodes().rank(id("A0001")) == 0, "reload removes newly invalid root");
        require(result.state().passiveNodes().rank(id("A0002")) == 0, "reload removes the invalid/orphaned subtree");
        require(result.pointsRefunded() == 3, "known invalid subtree refunds all known paid ranks");
        require(result.state().passivePoints().spent() == 0, "reload reconcile leaves point ledger coherent");
    }

    private static void nodeSpecializationReconcilePreservesExternalSpecializations() {
        NodeSpecializationGrant grant = new NodeSpecializationGrant(id("A0001"), "combat:test_node_grant", 1);
        ProgressionState state = swordEligibleState().withSpecializations(
            SpecializationProgressionState.of(Set.of("external:kept", "combat:test_node_grant"))
        );

        ProgressionState withoutNode = ProgressionService.reconcileNodeSpecializations(state, List.of(grant));
        require(withoutNode.specializations().isUnlocked("external:kept"),
            "node reconcile must never delete an external specialization");
        require(!withoutNode.specializations().isUnlocked("combat:test_node_grant"),
            "a specialization owned by a node grant must disappear when the granting rank is absent");

        ProgressionState learned = state.withPassiveNodes(PassiveNodeProgress.of(Map.of(id("A0001"), 1)));
        ProgressionState withNode = ProgressionService.reconcileNodeSpecializations(learned, List.of(grant));
        require(withNode.specializations().isUnlocked("external:kept"), "external specialization remains with live node grants too");
        require(withNode.specializations().isUnlocked("combat:test_node_grant"), "live node grant is reconstructed");
    }

    private static void missingDefinitionFailsClosedForAdministrativeReconciliation() {
        ProgressionState purchased = purchase(swordEligibleState(), "A0001");
        Map<String, NodePurchaseDefinition> afterRemoval = new HashMap<>(DEFINITIONS);
        afterRemoval.remove(id("A0001"));

        require(ProgressionService.unknownLearnedNodes(purchased, afterRemoval).equals(Set.of(id("A0001"))),
            "administrative path must surface the exact removed/renamed learned node");

        NodeAccessReconcileResult result = ProgressionService.reconcileInvalidNodes(
            purchased, GRAPH, Map.copyOf(afterRemoval), REQUIREMENTS, CURVE);
        require(result.state().passiveNodes().rank(id("A0001")) == 1,
            "normal reload reconcile must retain an unknown learned node when refund history is unavailable");
        require(result.pointsRefunded() == 0 && result.removedRanks().isEmpty(),
            "normal reconcile must not invent a refund for a missing definition");
        require(result.state().passivePoints().spent() == purchased.passivePoints().spent(),
            "fail-closed unknown-node handling must not corrupt the point ledger");
    }

    private static ProgressionState purchase(ProgressionState state, String code) {
        String nodeId = id(code);
        NodePurchaseDefinition definition = DEFINITIONS.get(nodeId);
        boolean access = NodeAccessResolver.satisfied(state, REQUIREMENTS.get(nodeId), CURVE);
        return ProgressionService.purchaseNode(state, GRAPH, definition, access);
    }

    private static ProgressionState swordEligibleState() {
        return baseState(
            MasteryState.of(Map.of("epicfight:sword", 100)),
            SpecializationProgressionState.of(Set.of("epic_sword"))
        );
    }

    private static ProgressionState baseState(MasteryState mastery, SpecializationProgressionState specializations) {
        return new ProgressionState(
            CURVE.xpRequiredForLevel(100),
            PassivePointLedger.of(Map.of(PassivePointSource.LEVEL, 100), 0),
            BossProgress.empty(),
            ClassProgressionState.empty(),
            mastery,
            ClassChoiceState.empty(),
            specializations,
            FinalTriadProgress.empty(),
            PassiveNodeProgress.empty(),
            DiscoveryProgress.empty()
        );
    }

    private static Map<String, NodePurchaseDefinition> definitions() {
        Map<String, NodePurchaseDefinition> result = new HashMap<>();
        for (CombatPerkTreeModel.Node node : CombatPerkTreeModel.all()) {
            result.put(node.nodeId(), new NodePurchaseDefinition(
                node.nodeId(), node.maxRank(), node.costPerRank(), node.startingPoint()));
        }
        return Map.copyOf(result);
    }

    private static Map<String, NodeAccessRequirement> requirements() {
        Map<String, NodeAccessRequirement> result = new HashMap<>();
        for (CombatPerkTreeModel.Node node : CombatPerkTreeModel.all()) {
            result.put(node.nodeId(), new NodeAccessRequirement(
                node.minCharacterLevel(), Set.of(), node.requiredMastery(), node.requiredSpecializations(),
                Set.of(), Set.of(), node.requiredNodeRanks(), Set.of()
            ));
        }
        return Map.copyOf(result);
    }

    private static SkillGraph graph() {
        Set<SkillGraph.Edge> edges = new HashSet<>();
        for (CombatPerkTreeModel.Node node : CombatPerkTreeModel.all()) {
            for (String neighbor : node.neighbors()) {
                String a = node.nodeId();
                String b = neighbor;
                edges.add(a.compareTo(b) <= 0 ? new SkillGraph.Edge(a, b) : new SkillGraph.Edge(b, a));
            }
        }
        return SkillGraph.undirected(new ArrayList<>(edges));
    }

    private static String id(String code) {
        return CombatPerkNodeBinding.nodeId(code);
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
