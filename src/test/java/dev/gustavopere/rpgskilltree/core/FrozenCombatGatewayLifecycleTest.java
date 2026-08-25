package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Frozen P-0020 contract for the nine A0001-A0050 combat gateways. */
public final class FrozenCombatGatewayLifecycleTest {
    private record Gateway(
        String specialization,
        ProgressionDomain domain,
        int minimumLevel,
        String masteryLane,
        int mastery,
        String tag,
        String rootCode
    ) {}

    private static final List<Gateway> GATEWAYS = List.of(
        new Gateway("epic_sword", ProgressionDomain.MARTIAL, 8, "epicfight:sword", 60, "gateway:epic_sword", "A0001"),
        new Gateway("epic_axe", ProgressionDomain.MARTIAL, 8, "epicfight:axe", 60, "gateway:epic_axe", "A0007"),
        new Gateway("epic_spear", ProgressionDomain.MARTIAL, 8, "epicfight:spear", 60, "gateway:epic_spear", "A0013"),
        new Gateway("epic_dagger", ProgressionDomain.MARTIAL, 8, "epicfight:dagger", 60, "gateway:epic_dagger", "A0019"),
        new Gateway("epic_hammer", ProgressionDomain.MARTIAL, 10, "epicfight:heavy", 70, "gateway:epic_hammer", "A0025"),
        new Gateway("combat_mace", ProgressionDomain.MARTIAL, 8, "combat:mace", 60, "gateway:combat_mace", "A0031"),
        new Gateway("combat_scythe", ProgressionDomain.MARTIAL, 8, "combat:scythe", 60, "gateway:combat_scythe", "A0037"),
        new Gateway("epic_bow", ProgressionDomain.AGILITY, 8, "combat:bow", 60, "gateway:epic_bow", "A0043"),
        new Gateway("epic_crossbow", ProgressionDomain.AGILITY, 8, "combat:crossbow", 60, "gateway:epic_crossbow", "A0047")
    );

    public static void main(String[] args) {
        allNineRequireEveryEligibilityDimension();
        learnedNodeProjectionCarriesDomainInvestmentAndGatewayTags();
        dependentPurchaseRequiresLiveGateway();
        gatewayLossRevokesDerivedSpecializationAndCascadesRefund();
        reloadReconciliationIsDeterministicAndPreservesUnmanagedState();
        explicitNodeGrantProvenanceRemainsSeparateFromDynamicEligibility();
        System.out.println("FrozenCombatGatewayLifecycleTest: PASS");
    }

    static void allNineRequireEveryEligibilityDimension() {
        for (Gateway gateway : GATEWAYS) {
            SpecializationDefinition definition = definition(gateway);
            InvestmentState validInvestment = investment(gateway, 8, true);
            MasteryState validMastery = MasteryState.of(Map.of(gateway.masteryLane(), gateway.mastery()));

            no(gateway.specialization() + " domain<8", SpecializationResolver.evaluate(
                Set.of(), validMastery, investment(gateway, 7, true), gateway.minimumLevel(), definition).unlockable());
            no(gateway.specialization() + " level", SpecializationResolver.evaluate(
                Set.of(), validMastery, validInvestment, gateway.minimumLevel() - 1, definition).unlockable());
            no(gateway.specialization() + " mastery", SpecializationResolver.evaluate(
                Set.of(), MasteryState.of(Map.of(gateway.masteryLane(), gateway.mastery() - 1)), validInvestment,
                gateway.minimumLevel(), definition).unlockable());
            no(gateway.specialization() + " tag", SpecializationResolver.evaluate(
                Set.of(), validMastery, investment(gateway, 8, false), gateway.minimumLevel(), definition).unlockable());
            yes(gateway.specialization() + " all", SpecializationResolver.evaluate(
                Set.of(), validMastery, validInvestment, gateway.minimumLevel(), definition).unlockable());
        }
    }

    static void learnedNodeProjectionCarriesDomainInvestmentAndGatewayTags() {
        Gateway sword = GATEWAYS.getFirst();
        Map<String, Integer> ranks = new LinkedHashMap<>();
        Map<String, Set<String>> tags = new LinkedHashMap<>();
        for (int i = 0; i < 8; i++) {
            String id = "rpgskilltree:martial_" + String.format("%03d", i);
            ranks.put(id, 1);
            tags.put(id, i == 0
                ? Set.of("rpgskilltree:domain/martial", sword.tag())
                : Set.of("rpgskilltree:domain/martial"));
        }
        InvestmentState projected = NodeInvestmentProjection.from(PassiveNodeProgress.of(ranks), tags);
        eq(8, projected.domainScore(ProgressionDomain.MARTIAL));
        yes("projected gateway tag", projected.hasTag(sword.tag()));
    }

    static void dependentPurchaseRequiresLiveGateway() {
        for (Gateway gateway : GATEWAYS) {
            CombatPerkTreeModel.Node root = CombatPerkTreeModel.node(gateway.rootCode()).orElseThrow();
            NodePurchaseDefinition purchase = new NodePurchaseDefinition(
                root.nodeId(), root.maxRank(), root.costPerRank(), root.startingPoint());
            NodeAccessRequirement access = new NodeAccessRequirement(
                root.minCharacterLevel(), Set.of(), root.requiredMastery(), root.requiredSpecializations(),
                Set.of(), Set.of(), root.requiredNodeRanks(), Set.of());

            ProgressionState eligible = stateAtLevel(gateway.minimumLevel())
                .withMastery(MasteryState.of(Map.of(gateway.masteryLane(), gateway.mastery())))
                .withSpecializations(SpecializationProgressionState.of(Set.of(gateway.specialization())))
                .withPassivePoints(PassivePointLedger.empty().award(PassivePointSource.ADMIN, 4));
            yes(gateway.specialization() + " root access", NodeAccessResolver.satisfied(
                eligible, access, CharacterLevelCurve.defaultCurve()));
            ProgressionState bought = ProgressionService.purchaseNode(
                eligible, SkillGraph.undirected(List.of()), purchase, true);
            eq(1, bought.passiveNodes().rank(root.nodeId()));

            ProgressionState withoutGateway = eligible.withSpecializations(SpecializationProgressionState.empty());
            no(gateway.specialization() + " root blocked", NodeAccessResolver.satisfied(
                withoutGateway, access, CharacterLevelCurve.defaultCurve()));
            rejected(() -> ProgressionService.purchaseNode(
                withoutGateway, SkillGraph.undirected(List.of()), purchase, false));
        }
    }

    static void gatewayLossRevokesDerivedSpecializationAndCascadesRefund() {
        for (Gateway gateway : GATEWAYS) {
            String prefix = gateway.domain() == ProgressionDomain.MARTIAL ? "martial_" : "agility_";
            String domainRoot = "rpgskilltree:" + prefix + "000";
            List<String> domainNodes = new ArrayList<>();
            Map<String, Set<String>> tags = new LinkedHashMap<>();
            for (int i = 0; i < 8; i++) {
                String id = "rpgskilltree:" + prefix + String.format("%03d", i);
                domainNodes.add(id);
                tags.put(id, i == 0
                    ? Set.of("rpgskilltree:domain/" + gateway.domain().name().toLowerCase(), gateway.tag())
                    : Set.of("rpgskilltree:domain/" + gateway.domain().name().toLowerCase()));
            }

            CombatPerkTreeModel.Node combatRoot = CombatPerkTreeModel.node(gateway.rootCode()).orElseThrow();
            Map<String, NodePurchaseDefinition> definitions = new LinkedHashMap<>();
            Map<String, NodeAccessRequirement> requirements = new LinkedHashMap<>();
            List<SkillGraph.Edge> edges = new ArrayList<>();
            String core = "rpgskilltree:core_test_" + gateway.specialization();
            definitions.put(core, new NodePurchaseDefinition(core, 1, 1, true));
            requirements.put(core, NodeAccessRequirement.none());
            for (int i = 0; i < domainNodes.size(); i++) {
                String id = domainNodes.get(i);
                definitions.put(id, new NodePurchaseDefinition(id, 1, 1, false));
                requirements.put(id, NodeAccessRequirement.none());
                edges.add(new SkillGraph.Edge(i == 0 ? core : domainNodes.get(i - 1), id));
            }
            definitions.put(combatRoot.nodeId(), new NodePurchaseDefinition(
                combatRoot.nodeId(), combatRoot.maxRank(), combatRoot.costPerRank(), combatRoot.startingPoint()));
            requirements.put(combatRoot.nodeId(), new NodeAccessRequirement(
                combatRoot.minCharacterLevel(), Set.of(), combatRoot.requiredMastery(), combatRoot.requiredSpecializations(),
                Set.of(), Set.of(), combatRoot.requiredNodeRanks(), Set.of()));
            SkillGraph graph = SkillGraph.undirected(edges);

            Map<String, Integer> learned = new LinkedHashMap<>();
            learned.put(core, 1);
            domainNodes.forEach(id -> learned.put(id, 1));
            learned.put(combatRoot.nodeId(), 1);
            ProgressionState state = stateAtLevel(gateway.minimumLevel())
                .withMastery(MasteryState.of(Map.of(gateway.masteryLane(), gateway.mastery())))
                .withSpecializations(SpecializationProgressionState.of(Set.of("unmanaged_keep", gateway.specialization())))
                .withPassiveNodes(PassiveNodeProgress.of(learned))
                .withPassivePoints(PassivePointLedger.of(Map.of(PassivePointSource.ADMIN, 9), 9));

            NodeRespecResult topology = ProgressionService.respecNode(state, graph, definitions, domainRoot);
            eq(8, topology.pointsRefunded());
            for (String id : domainNodes) eq(0, topology.state().passiveNodes().rank(id));
            eq(1, topology.state().passiveNodes().rank(combatRoot.nodeId()));

            ProgressionState noGateway = ProgressionService.reconcileEligibleSpecializationsFromNodes(
                topology.state(), List.of(definition(gateway)), tags, CharacterLevelCurve.defaultCurve());
            no(gateway.specialization() + " revoked", noGateway.specializations().isUnlocked(gateway.specialization()));
            yes("unmanaged preserved", noGateway.specializations().isUnlocked("unmanaged_keep"));

            NodeAccessReconcileResult semantic = ProgressionService.reconcileInvalidNodes(
                noGateway, graph, definitions, requirements, CharacterLevelCurve.defaultCurve());
            eq(1, semantic.pointsRefunded());
            eq(0, semantic.state().passiveNodes().rank(combatRoot.nodeId()));
            eq(9, topology.pointsRefunded() + semantic.pointsRefunded());
            eq(9, semantic.state().passivePoints().available());
        }
    }

    static void reloadReconciliationIsDeterministicAndPreservesUnmanagedState() {
        for (Gateway gateway : GATEWAYS) {
            ProgressionState stale = stateAtLevel(gateway.minimumLevel())
                .withMastery(MasteryState.of(Map.of(gateway.masteryLane(), gateway.mastery())))
                .withSpecializations(SpecializationProgressionState.of(Set.of("unmanaged_keep", gateway.specialization())));
            Map<String, Set<String>> noGatewayTags = Map.of(
                "rpgskilltree:other", Set.of("rpgskilltree:domain/" + gateway.domain().name().toLowerCase()));
            ProgressionState first = ProgressionService.reconcileEligibleSpecializationsFromNodes(
                stale, List.of(definition(gateway)), noGatewayTags, CharacterLevelCurve.defaultCurve());
            ProgressionState reloaded = ProgressionService.reconcileEligibleSpecializationsFromNodes(
                first, List.of(definition(gateway)), noGatewayTags, CharacterLevelCurve.defaultCurve());
            eq(first.specializations().unlockedSpecializationIds(), reloaded.specializations().unlockedSpecializationIds());
            eq(Set.of("unmanaged_keep"), reloaded.specializations().unlockedSpecializationIds());
        }
    }

    static void explicitNodeGrantProvenanceRemainsSeparateFromDynamicEligibility() {
        ProgressionState state = ProgressionState.empty()
            .withPassiveNodes(PassiveNodeProgress.of(Map.of("rpgskilltree:explicit_gateway", 1)))
            .withSpecializations(SpecializationProgressionState.of(Set.of("unmanaged_keep", "epic_sword")));
        NodeSpecializationGrant grant = new NodeSpecializationGrant(
            "rpgskilltree:explicit_gateway", "explicit_owned", 1);

        ProgressionState withGrant = ProgressionService.reconcileNodeSpecializations(state, List.of(grant));
        yes("explicit grant active", withGrant.specializations().isUnlocked("explicit_owned"));
        ProgressionState dynamic = ProgressionService.reconcileEligibleSpecializations(
            withGrant, List.of(definition(GATEWAYS.getFirst())), InvestmentState.of(List.of()), CharacterLevelCurve.defaultCurve());
        no("stale dynamic removed", dynamic.specializations().isUnlocked("epic_sword"));
        yes("explicit grant untouched by unrelated dynamic catalog", dynamic.specializations().isUnlocked("explicit_owned"));
        yes("unmanaged untouched", dynamic.specializations().isUnlocked("unmanaged_keep"));

        ProgressionState sourceGone = dynamic.withPassiveNodes(PassiveNodeProgress.empty());
        ProgressionState withoutGrant = ProgressionService.reconcileNodeSpecializations(sourceGone, List.of(grant));
        no("explicit grant revoked only by provenance", withoutGrant.specializations().isUnlocked("explicit_owned"));
        yes("unmanaged survives explicit reconciliation", withoutGrant.specializations().isUnlocked("unmanaged_keep"));
    }

    private static SpecializationDefinition definition(Gateway gateway) {
        return new SpecializationDefinition(
            gateway.specialization(),
            Set.of(),
            Map.of(gateway.masteryLane(), gateway.mastery()),
            Set.of(gateway.tag()),
            gateway.minimumLevel(),
            Map.of(gateway.domain(), 8)
        );
    }

    private static InvestmentState investment(Gateway gateway, int domainPoints, boolean includeGatewayTag) {
        List<NodeInvestment> nodes = new ArrayList<>();
        for (int i = 0; i < domainPoints; i++) {
            nodes.add(new NodeInvestment(
                "test:" + gateway.specialization() + ":" + i,
                Map.of(gateway.domain(), 1),
                includeGatewayTag && i == 0 ? Set.of(gateway.tag()) : Set.of()
            ));
        }
        return InvestmentState.of(nodes);
    }

    private static ProgressionState stateAtLevel(int level) {
        ProgressionState empty = ProgressionState.empty();
        return new ProgressionState(
            CharacterLevelCurve.defaultCurve().xpRequiredForLevel(level),
            empty.passivePoints(), empty.bossProgress(), empty.classProgression(), empty.mastery(), empty.classChoices(),
            empty.specializations(), empty.finalTriads(), empty.passiveNodes(), empty.discoveries());
    }

    private static void rejected(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void yes(String label, boolean value) {
        if (!value) throw new AssertionError(label + " expected true");
    }

    private static void no(String label, boolean value) {
        if (value) throw new AssertionError(label + " expected false");
    }

    private static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
