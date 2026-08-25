package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FrozenCombatTreeProgressionTest {
    public static void main(String[] args) {
        physicalModelContainsExactlyTheFrozenBatch();
        combatFistPurchaseUsesTheFrozenGate();
        requirementLossCascadesAndRefunds();
        System.out.println("FrozenCombatTreeProgressionTest: PASS");
    }

    private static void physicalModelContainsExactlyTheFrozenBatch() {
        require(FrozenA0051A0100TreeModel.all().size() == 50, "physical batch size");
        require(FrozenA0051A0100TreeModel.node("A0050").isEmpty(), "old batch stays separate");
        require(FrozenA0051A0100TreeModel.node("A0100").isPresent(), "A0100 present");
        require(FrozenCombatPerkNodeBinding.nodeId("A0055").equals("rpgskilltree:combat/a0055"), "A0055 id");
        require(FrozenCombatPerkNodeBinding.catalogCode("rpgskilltree:combat/a0055").orElseThrow().equals("A0055"),
            "A0055 reverse binding");
    }

    private static void combatFistPurchaseUsesTheFrozenGate() {
        var model = FrozenA0051A0100TreeModel.node("A0055").orElseThrow();
        var requirement = requirement(model);
        var curve = CharacterLevelCurve.defaultCurve();
        var valid = state(8, 60, Set.of(CombatFistPolicy.SPECIALIZATION_ID), PassiveNodeProgress.empty(), 20, 0);
        require(NodeAccessResolver.satisfied(valid, requirement, curve), "exact combat_fist gate");
        require(!NodeAccessResolver.satisfied(state(7, 60, Set.of(CombatFistPolicy.SPECIALIZATION_ID),
            PassiveNodeProgress.empty(), 20, 0), requirement, curve), "level gate");
        require(!NodeAccessResolver.satisfied(state(8, 59, Set.of(CombatFistPolicy.SPECIALIZATION_ID),
            PassiveNodeProgress.empty(), 20, 0), requirement, curve), "mastery gate");
        require(!NodeAccessResolver.satisfied(state(8, 60, Set.of(), PassiveNodeProgress.empty(), 20, 0),
            requirement, curve), "specialization gate");

        ProgressionState purchased = ProgressionService.purchaseNode(
            valid, fistGraph(), purchase(model), true);
        require(purchased.passiveNodes().rank(model.nodeId()) == 1, "purchase increments A0055");
        require(purchased.passivePoints().spent() == 1, "purchase cost");
    }

    private static void requirementLossCascadesAndRefunds() {
        Map<String, Integer> learned = Map.of(
            FrozenCombatPerkNodeBinding.nodeId("A0055"), 2,
            FrozenCombatPerkNodeBinding.nodeId("A0056"), 2,
            FrozenCombatPerkNodeBinding.nodeId("A0057"), 2,
            FrozenCombatPerkNodeBinding.nodeId("A0058"), 1
        );
        ProgressionState state = state(8, 59, Set.of(CombatFistPolicy.SPECIALIZATION_ID),
            PassiveNodeProgress.of(learned), 20, 7);
        Map<String, NodePurchaseDefinition> definitions = new HashMap<>();
        Map<String, NodeAccessRequirement> requirements = new HashMap<>();
        for (int i = 55; i <= 60; i++) {
            var node = FrozenA0051A0100TreeModel.node("A%04d".formatted(i)).orElseThrow();
            definitions.put(node.nodeId(), purchase(node));
            requirements.put(node.nodeId(), requirement(node));
        }
        var result = ProgressionService.reconcileInvalidNodes(
            state, fistGraph(), definitions, requirements, CharacterLevelCurve.defaultCurve());
        require(result.state().passiveNodes().learnedNodeIds().isEmpty(), "mastery loss removes fist branch");
        require(result.pointsRefunded() == 7, "all spent fist ranks refunded");
        require(result.state().passivePoints().spent() == 0, "refund ledger reconciled");
    }

    private static SkillGraph fistGraph() {
        List<SkillGraph.Edge> edges = new ArrayList<>();
        Set<String> seen = new java.util.HashSet<>();
        for (int i = 55; i <= 60; i++) {
            var node = FrozenA0051A0100TreeModel.node("A%04d".formatted(i)).orElseThrow();
            for (String neighbor : node.neighbors()) {
                if (FrozenCombatPerkNodeBinding.catalogCode(neighbor).isEmpty()) continue;
                String a = node.nodeId().compareTo(neighbor) <= 0 ? node.nodeId() : neighbor;
                String b = node.nodeId().compareTo(neighbor) <= 0 ? neighbor : node.nodeId();
                if (seen.add(a + b)) edges.add(new SkillGraph.Edge(a, b));
            }
        }
        return SkillGraph.undirected(edges);
    }

    private static NodeAccessRequirement requirement(FrozenA0051A0100TreeModel.Node node) {
        return new NodeAccessRequirement(node.minCharacterLevel(), Set.of(), node.requiredMastery(),
            node.requiredSpecializations(), Set.of(), node.requiredNodes(), node.requiredNodeRanks(), Set.of());
    }

    private static NodePurchaseDefinition purchase(FrozenA0051A0100TreeModel.Node node) {
        return new NodePurchaseDefinition(node.nodeId(), node.maxRank(), node.costPerRank(), node.startingPoint());
    }

    private static ProgressionState state(
        int level, int mastery, Set<String> specializations, PassiveNodeProgress nodes, int earned, int spent
    ) {
        return new ProgressionState(
            CharacterLevelCurve.defaultCurve().xpRequiredForLevel(level),
            PassivePointLedger.of(Map.of(PassivePointSource.LEVEL, earned), spent),
            BossProgress.empty(), ClassProgressionState.empty(), MasteryState.of(Map.of(CombatFistPolicy.MASTERY_ID, mastery)),
            ClassChoiceState.empty(), SpecializationProgressionState.of(specializations), FinalTriadProgress.empty(),
            nodes, DiscoveryProgress.empty()
        );
    }

    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
