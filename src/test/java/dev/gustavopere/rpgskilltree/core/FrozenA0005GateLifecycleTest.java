package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Frozen P-0016 contract for A0005 Abertura de Guarda access and invalidation. */
public final class FrozenA0005GateLifecycleTest {
    private static final CharacterLevelCurve CURVE = CharacterLevelCurve.defaultCurve();
    private static final SkillGraph GRAPH = graph();
    private static final Map<String, NodePurchaseDefinition> DEFINITIONS = definitions();
    private static final Map<String, NodeAccessRequirement> REQUIREMENTS = requirements();

    public static void main(String[] args) {
        exactGateAllowsValidPurchase();
        missingEpicSwordBlocksPurchase();
        a0002RankOneBlocksPurchase();
        missingA0004BlocksPurchase();
        lateralConnectivityCannotReplaceRequiredDependency();
        respecOfA0004InvalidatesA0005ThroughReconciliation();
        gatewayLossReconciliationInvalidatesAndRefundsCascade();
        System.out.println("FrozenA0005GateLifecycleTest: PASS");
    }

    private static void exactGateAllowsValidPurchase() {
        ProgressionState state = fullPrerequisites();
        NodeAccessRequirement requirement = REQUIREMENTS.get(id("A0005"));
        require(requirement.requiredSpecializationIds().equals(Set.of("epic_sword")),
            "A0005 inherits exactly the epic_sword specialization gate");
        require(requirement.requiredNodeRanks().equals(Map.of(id("A0002"), 2, id("A0004"), 1)),
            "A0005 requires exactly A0002 rank two plus A0004");
        require(NodeAccessResolver.satisfied(state, requirement, CURVE),
            "epic_sword + A0002 rank2 + A0004 satisfies frozen A0005 gate");
        ProgressionState purchased = purchase(state, "A0005");
        require(purchased.passiveNodes().rank(id("A0005")) == 1, "valid A0005 purchase succeeds");
        require(purchased.passivePoints().spent() == state.passivePoints().spent() + 1,
            "A0005 purchase spends exactly its frozen one-point cost");
    }

    private static void missingEpicSwordBlocksPurchase() {
        ProgressionState state = fullPrerequisites().withSpecializations(SpecializationProgressionState.empty());
        requireBlocked(state, "missing epic_sword must block A0005");
    }

    private static void a0002RankOneBlocksPurchase() {
        ProgressionState state = baseEligible();
        state = purchaseTimes(state, "A0001", 2);
        state = purchaseTimes(state, "A0002", 1);
        state = purchaseTimes(state, "A0003", 2);
        state = purchaseTimes(state, "A0004", 1);
        require(state.passiveNodes().rank(id("A0002")) == 1, "fixture keeps A0002 at rank one");
        requireBlocked(state, "A0002 rank one must not satisfy the required rank two gate");
    }

    private static void missingA0004BlocksPurchase() {
        ProgressionState state = baseEligible();
        state = purchaseTimes(state, "A0001", 2);
        state = purchaseTimes(state, "A0002", 2);
        state = purchaseTimes(state, "A0003", 2);
        require(!state.passiveNodes().learned(id("A0004")), "fixture omits A0004");
        requireBlocked(state, "A0004 is mandatory even when A0005 is graph-connected through A0002");
    }

    private static void lateralConnectivityCannotReplaceRequiredDependency() {
        ProgressionState state = baseEligible();
        state = purchaseTimes(state, "A0001", 2);
        state = purchaseTimes(state, "A0002", 2);
        state = purchaseTimes(state, "A0003", 2);
        Map<String, Integer> malicious = new HashMap<>(state.passiveNodes().ranks());
        malicious.put(id("A0006"), 1);
        state = state.withPassiveNodes(PassiveNodeProgress.of(malicious));
        require(GRAPH.neighbors(id("A0005")).stream().anyMatch(state.passiveNodes()::learned),
            "fixture proves a learned lateral neighbor exists");
        requireBlocked(state, "lateral graph connectivity cannot substitute missing A0004");
    }

    private static void respecOfA0004InvalidatesA0005ThroughReconciliation() {
        ProgressionState learned = purchase(fullPrerequisites(), "A0005");
        int spentBefore = learned.passivePoints().spent();
        NodeRespecResult respec = ProgressionService.respecNode(learned, GRAPH, DEFINITIONS, id("A0004"));
        require(respec.pointsRefunded() == 1, "direct A0004 respec refunds exactly one point");
        require(respec.state().passiveNodes().learned(id("A0005")),
            "topology alone leaves A0005 reachable, so semantic reconciliation is required");
        require(!NodeAccessResolver.satisfied(respec.state(), REQUIREMENTS.get(id("A0005")), CURVE),
            "A0005 becomes semantically invalid immediately after A0004 respec");

        NodeAccessReconcileResult reconciled = ProgressionService.reconcileInvalidNodes(
            respec.state(), GRAPH, DEFINITIONS, REQUIREMENTS, CURVE);
        require(!reconciled.state().passiveNodes().learned(id("A0005")),
            "reconciliation removes A0005 after its required A0004 is lost");
        require(reconciled.removedRanks().getOrDefault(id("A0005"), 0) == 1,
            "reconciliation reports the exact invalidated A0005 rank");
        require(reconciled.pointsRefunded() == 1, "A0005 reconciliation refunds its exact one-point cost");
        require(reconciled.state().passivePoints().spent() == spentBefore - 2,
            "A0004 respec plus A0005 invalidation refunds exactly two points total");
    }

    private static void gatewayLossReconciliationInvalidatesAndRefundsCascade() {
        ProgressionState learned = purchase(fullPrerequisites(), "A0005");
        int spentBefore = learned.passivePoints().spent();
        ProgressionState lostGateway = learned.withSpecializations(SpecializationProgressionState.empty());
        require(!NodeAccessResolver.satisfied(lostGateway, REQUIREMENTS.get(id("A0005")), CURVE),
            "gateway loss invalidates A0005 before reconciliation");
        NodeAccessReconcileResult result = ProgressionService.reconcileInvalidNodes(
            lostGateway, GRAPH, DEFINITIONS, REQUIREMENTS, CURVE);
        require(result.state().passiveNodes().rank(id("A0005")) == 0,
            "reconciliation removes A0005 when epic_sword is lost");
        require(result.state().passiveNodes().learnedNodeIds().stream()
                .noneMatch(nodeId -> nodeId.startsWith("rpgskilltree:combat/a000")),
            "loss of epic_sword cascades through the learned sword subtree");
        require(result.pointsRefunded() == spentBefore,
            "gateway-loss cascade refunds every known purchased sword rank exactly once");
        require(result.state().passivePoints().spent() == 0,
            "gateway-loss reconciliation leaves the point ledger coherent");
    }

    private static void requireBlocked(ProgressionState state, String message) {
        boolean access = NodeAccessResolver.satisfied(state, REQUIREMENTS.get(id("A0005")), CURVE);
        require(!access, message + " (resolver)");
        boolean threw = false;
        try {
            ProgressionService.purchaseNode(state, GRAPH, DEFINITIONS.get(id("A0005")), access);
        } catch (IllegalArgumentException expected) {
            threw = true;
        }
        require(threw, message + " (purchase)");
    }

    private static ProgressionState fullPrerequisites() {
        ProgressionState state = baseEligible();
        state = purchaseTimes(state, "A0001", 2);
        state = purchaseTimes(state, "A0002", 2);
        state = purchaseTimes(state, "A0003", 2);
        state = purchaseTimes(state, "A0004", 1);
        return state;
    }

    private static ProgressionState purchaseTimes(ProgressionState state, String code, int times) {
        ProgressionState current = state;
        for (int i = 0; i < times; i++) current = purchase(current, code);
        return current;
    }

    private static ProgressionState purchase(ProgressionState state, String code) {
        String nodeId = id(code);
        boolean access = NodeAccessResolver.satisfied(state, REQUIREMENTS.get(nodeId), CURVE);
        return ProgressionService.purchaseNode(state, GRAPH, DEFINITIONS.get(nodeId), access);
    }

    private static ProgressionState baseEligible() {
        return new ProgressionState(
            CURVE.xpRequiredForLevel(100),
            PassivePointLedger.of(Map.of(PassivePointSource.ADMIN, 100), 0),
            BossProgress.empty(),
            ClassProgressionState.empty(),
            MasteryState.of(Map.of("epicfight:sword", 100)),
            ClassChoiceState.empty(),
            SpecializationProgressionState.of(Set.of("epic_sword")),
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
                Set.of(), Set.of(), node.requiredNodeRanks(), Set.of()));
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
