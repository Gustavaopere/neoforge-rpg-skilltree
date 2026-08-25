package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FrozenSurvivalTreeProgressionTest {
    public static void main(String[] args) {
        modelBindsExactlyTheThirdFrozenBatch();
        modelUsesExistingDomainRootsAndRankedDependencies();
        structuralOrAndCountGatesUseCanonicalNodeIds();
        purchaseAndRespecUseTheSameProjection();
        requirementLossCascadesAndRefunds();
        System.out.println("FrozenSurvivalTreeProgressionTest: PASS");
    }

    private static void modelBindsExactlyTheThirdFrozenBatch() {
        require(FrozenA0101A0150TreeModel.all().size() == 50, "third batch tree size");
        require(FrozenA0101A0150TreeModel.node("A0100").isEmpty(), "prior checkpoint separate");
        require(FrozenA0101A0150TreeModel.node("A0150").isPresent(), "A0150 present");
        require(FrozenSurvivalPerkNodeBinding.nodeId("A0101").equals("rpgskilltree:frozen/a0101"),
            "stable third-batch node id");
        require(FrozenSurvivalPerkNodeBinding.catalogCode("rpgskilltree:frozen/a0150")
            .orElseThrow().equals("A0150"), "reverse binding");
        require(FrozenSurvivalPerkNodeBinding.ranks(PassiveNodeProgress.of(Map.of(
            "rpgskilltree:frozen/a0144", 3))).rank("A0144") == 3, "rank projection");
    }

    private static void modelUsesExistingDomainRootsAndRankedDependencies() {
        var a101 = FrozenA0101A0150TreeModel.node("A0101").orElseThrow();
        require(a101.requiredNodes().equals(Set.of("rpgskilltree:vitality_000")),
            "VITALITY Gate is existing root node");
        require(a101.requiredNodeRanks().equals(Map.of("rpgskilltree:combat/a0089", 1)),
            "cross-batch ranked dependency preserved");
        var a102 = FrozenA0101A0150TreeModel.node("A0102").orElseThrow();
        require(a102.requiredNodes().equals(Set.of("rpgskilltree:vitality_000", "rpgskilltree:arcane_000")),
            "bridge requires both canonical domain roots");
        var a112 = FrozenA0101A0150TreeModel.node("A0112").orElseThrow();
        require(a112.requiredNodeRanks().equals(Map.of("rpgskilltree:frozen/a0111", 2)),
            "local ranked dependency preserved");
    }

    private static void structuralOrAndCountGatesUseCanonicalNodeIds() {
        var arcaneLeft = PassiveNodeProgress.of(Map.of("rpgskilltree:frozen/a0144", 1));
        require(FrozenSurvivalAccessPolicy.satisfied(
            FrozenSurvivalPerkDefinition.SpecialGate.ARCANE_RESERVE_OR, arcaneLeft, Set.of()),
            "A0146 accepts A0144 route");
        var arcaneRight = PassiveNodeProgress.of(Map.of("rpgskilltree:frozen/a0145", 1));
        require(FrozenSurvivalAccessPolicy.satisfied(
            FrozenSurvivalPerkDefinition.SpecialGate.ARCANE_RESERVE_OR, arcaneRight, Set.of()),
            "A0146 accepts A0145 route");
        require(!FrozenSurvivalAccessPolicy.satisfied(
            FrozenSurvivalPerkDefinition.SpecialGate.ARCANE_RESERVE_OR,
            PassiveNodeProgress.empty(), Set.of()), "A0146 has no implicit route");

        var threeNoProfession = PassiveNodeProgress.of(Map.of(
            "rpgskilltree:frozen/a0115", 1, "rpgskilltree:frozen/a0117", 1,
            "rpgskilltree:frozen/a0119", 1));
        require(!FrozenSurvivalAccessPolicy.satisfied(
            FrozenSurvivalPerkDefinition.SpecialGate.THREE_DISTINCT_METABOLIC,
            threeNoProfession, Set.of()), "A0139 requires professional or climatic member");
        var valid = PassiveNodeProgress.of(Map.of(
            "rpgskilltree:frozen/a0115", 1, "rpgskilltree:frozen/a0117", 1,
            "rpgskilltree:frozen/a0123", 1));
        require(FrozenSurvivalAccessPolicy.satisfied(
            FrozenSurvivalPerkDefinition.SpecialGate.THREE_DISTINCT_METABOLIC, valid, Set.of()),
            "A0139 counts canonical node ids once");
        require(!FrozenSurvivalAccessPolicy.satisfied(
            FrozenSurvivalPerkDefinition.SpecialGate.ATTUNEMENT_SOCKET, valid, Set.of()),
            "A0114 fails closed without Attunement Socket identity");
        require(FrozenSurvivalAccessPolicy.satisfied(
            FrozenSurvivalPerkDefinition.SpecialGate.ATTUNEMENT_SOCKET, valid, Set.of("attunement_socket")),
            "A0114 consumes the existing structural identity when present");
    }

    private static void purchaseAndRespecUseTheSameProjection() {
        var node = FrozenA0101A0150TreeModel.node("A0110").orElseThrow();
        PassiveNodeProgress learned = PassiveNodeProgress.of(Map.of("rpgskilltree:survival_000", 1));
        ProgressionState state = state(learned, 10, 1);
        require(NodeAccessResolver.satisfied(state, requirement(node), CharacterLevelCurve.defaultCurve()),
            "SURVIVAL root opens A0110");
        ProgressionState purchased = ProgressionService.purchaseNode(
            state, graph(), purchase(node), true);
        require(purchased.passiveNodes().rank(node.nodeId()) == 1, "purchase writes third-batch binding");
        var definitions = definitionsForLearned(purchased.passiveNodes());
        var result = ProgressionService.respecNode(purchased, graph(), definitions, node.nodeId());
        require(result.pointsRefunded() == 1 && result.state().passiveNodes().rank(node.nodeId()) == 0,
            "respec refunds through same definition");
    }

    private static void requirementLossCascadesAndRefunds() {
        var a144 = FrozenA0101A0150TreeModel.node("A0144").orElseThrow();
        var a148 = FrozenA0101A0150TreeModel.node("A0148").orElseThrow();
        PassiveNodeProgress learned = PassiveNodeProgress.of(Map.of(
            "rpgskilltree:arcane_000", 1,
            a144.nodeId(), 2,
            FrozenSurvivalPerkNodeBinding.nodeId("A0145"), 2,
            a148.nodeId(), 1));
        ProgressionState state = state(learned, 20, 5);
        Map<String, NodePurchaseDefinition> definitions = definitionsForLearned(learned);
        Map<String, NodeAccessRequirement> requirements = requirementsForLearned(learned);
        ProgressionState lostGateway = state.withPassiveNodes(learned.without(Set.of("rpgskilltree:arcane_000")));
        var reconciled = ProgressionService.reconcileInvalidNodes(
            lostGateway, graph(), definitions, requirements, CharacterLevelCurve.defaultCurve());
        require(reconciled.state().passiveNodes().rank(a144.nodeId()) == 0
            && reconciled.state().passiveNodes().rank(a148.nodeId()) == 0,
            "gateway loss cascades through arcane branch");
        require(reconciled.pointsRefunded() == 5, "all invalid frozen ranks refund");
    }

    private static SkillGraph graph() {
        List<SkillGraph.Edge> edges = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (var node : FrozenA0101A0150TreeModel.all()) {
            for (String neighbor : node.neighbors()) {
                String a = node.nodeId().compareTo(neighbor) <= 0 ? node.nodeId() : neighbor;
                String b = node.nodeId().compareTo(neighbor) <= 0 ? neighbor : node.nodeId();
                if (seen.add(a + "\0" + b)) edges.add(new SkillGraph.Edge(a, b));
            }
        }
        return SkillGraph.undirected(edges);
    }

    private static Map<String, NodePurchaseDefinition> definitionsForLearned(PassiveNodeProgress learned) {
        Map<String, NodePurchaseDefinition> values = new HashMap<>();
        for (String id : learned.learnedNodeIds()) {
            var code = FrozenSurvivalPerkNodeBinding.catalogCode(id);
            if (code.isPresent()) values.put(id, purchase(FrozenA0101A0150TreeModel.node(code.get()).orElseThrow()));
            else values.put(id, new NodePurchaseDefinition(id, Math.max(1, learned.rank(id)), 1, true));
        }
        return values;
    }

    private static Map<String, NodeAccessRequirement> requirementsForLearned(PassiveNodeProgress learned) {
        Map<String, NodeAccessRequirement> values = new HashMap<>();
        for (String id : learned.learnedNodeIds()) {
            var code = FrozenSurvivalPerkNodeBinding.catalogCode(id);
            values.put(id, code.isPresent()
                ? requirement(FrozenA0101A0150TreeModel.node(code.get()).orElseThrow())
                : NodeAccessRequirement.none());
        }
        return values;
    }

    private static NodeAccessRequirement requirement(FrozenA0101A0150TreeModel.Node node) {
        return new NodeAccessRequirement(1, Set.of(), Map.of(), node.requiredSpecializations(),
            Set.of(), node.requiredNodes(), node.requiredNodeRanks(), Set.of());
    }

    private static NodePurchaseDefinition purchase(FrozenA0101A0150TreeModel.Node node) {
        return new NodePurchaseDefinition(node.nodeId(), node.maxRank(), node.costPerRank(), node.startingPoint());
    }

    private static ProgressionState state(PassiveNodeProgress learned, int earned, int spent) {
        return new ProgressionState(0L, PassivePointLedger.of(Map.of(PassivePointSource.LEVEL, earned), spent),
            BossProgress.empty(), ClassProgressionState.empty(), MasteryState.empty(), ClassChoiceState.empty(),
            SpecializationProgressionState.empty(), FinalTriadProgress.empty(), learned, DiscoveryProgress.empty());
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
