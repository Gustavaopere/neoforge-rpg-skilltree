package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class ProgressionService {
    private ProgressionService() {}

    public static ProgressionState applyXp(ProgressionState state, CharacterXpAward award, CharacterLevelCurve curve) {
        int beforeLevel = curve.levelForTotalXp(state.totalCharacterXp());
        long nextTotal = Math.addExact(state.totalCharacterXp(), award.amount());
        int afterLevel = curve.levelForTotalXp(nextTotal);
        int gainedLevels = Math.max(0, afterLevel - beforeLevel);
        PassivePointLedger ledger = state.passivePoints();
        if (gainedLevels > 0) ledger = ledger.award(PassivePointSource.LEVEL, gainedLevels);
        return new ProgressionState(nextTotal, ledger, state.bossProgress(), state.classProgression(), state.mastery(), state.classChoices(), state.specializations(), state.finalTriads(), state.passiveNodes(), state.discoveries());
    }

    public static BossProgressionResult creditBoss(ProgressionState state, String rewardKey, BossRewardDefinition definition) {
        BossRewardResult reward = state.bossProgress().creditFirstDefeat(rewardKey, definition);
        PassivePointLedger ledger = state.passivePoints();
        if (reward.pointsAwarded() > 0) ledger = ledger.award(PassivePointSource.BOSS, reward.pointsAwarded());
        ProgressionState next = new ProgressionState(state.totalCharacterXp(), ledger, reward.progress(), state.classProgression(), state.mastery(), state.classChoices(), state.specializations(), state.finalTriads(), state.passiveNodes(), state.discoveries());
        return new BossProgressionResult(next, reward.pointsAwarded(), reward.firstDefeat());
    }

    public static DiscoveryProgressionResult creditDiscovery(
        ProgressionState state,
        String discoveryKey,
        CharacterXpAward award,
        CharacterLevelCurve curve
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(award);
        Objects.requireNonNull(curve);
        if (discoveryKey == null || discoveryKey.isBlank()) {
            throw new IllegalArgumentException("discoveryKey must not be blank");
        }
        if (state.discoveries().contains(discoveryKey)) {
            return new DiscoveryProgressionResult(state, 0, false);
        }
        ProgressionState discovered = state.withDiscoveries(state.discoveries().add(discoveryKey));
        ProgressionState next = applyXp(discovered, award, curve);
        return new DiscoveryProgressionResult(next, award.amount(), true);
    }

    public static AutomaticClassReconcileResult reconcileAutomaticClasses(
        ProgressionState state,
        java.util.Collection<ClassUnlockDefinition> definitions
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(definitions);
        ProgressionState current = state;
        Set<String> newlyUnlocked = new HashSet<>();
        Set<String> removed = new HashSet<>();
        for (ClassUnlockDefinition definition : definitions.stream()
            .sorted(java.util.Comparator.comparing(ClassUnlockDefinition::classId))
            .toList()) {
            if (definition.nonAdjacentBridgeCost() != 0) continue;
            ClassUnlockResult eligibility = ClassUnlockResolver.evaluate(
                current.finalTriads(), definition, current.passivePoints().available());
            boolean contextualRequirementsMet = ClassRequirementPolicy.satisfied(current, definition);
            boolean unlocked = current.classProgression().isUnlocked(definition.classId());
            if (eligibility.unlockable() && contextualRequirementsMet && !unlocked) {
                ClassUnlockMutationResult mutation = unlockClass(current, definition);
                current = mutation.state();
                if (mutation.unlockedNow()) newlyUnlocked.add(definition.classId());
            } else if ((!eligibility.unlockable() || !contextualRequirementsMet) && unlocked) {
                current = current.withClassProgression(
                    current.classProgression().without(definition.classId()));
                removed.add(definition.classId());
            }
        }
        return new AutomaticClassReconcileResult(current, newlyUnlocked, removed);
    }

    public static AutomaticClassUnlockResult unlockAutomaticClasses(
        ProgressionState state,
        java.util.Collection<ClassUnlockDefinition> definitions
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(definitions);
        ProgressionState current = state;
        Set<String> unlocked = new HashSet<>();
        for (ClassUnlockDefinition definition : definitions.stream()
            .sorted(java.util.Comparator.comparing(ClassUnlockDefinition::classId))
            .toList()) {
            if (definition.nonAdjacentBridgeCost() != 0) continue;
            if (current.classProgression().isUnlocked(definition.classId())) continue;
            ClassUnlockResult eligibility = ClassUnlockResolver.evaluate(
                current.finalTriads(), definition, current.passivePoints().available());
            if (!eligibility.unlockable() || !ClassRequirementPolicy.satisfied(current, definition)) continue;
            ClassUnlockMutationResult mutation = unlockClass(current, definition);
            current = mutation.state();
            if (mutation.unlockedNow()) unlocked.add(definition.classId());
        }
        return new AutomaticClassUnlockResult(current, unlocked);
    }

    public static ClassUnlockMutationResult unlockClass(ProgressionState state, ClassUnlockDefinition definition) {
        return unlockClass(state, state.finalTriads(), definition);
    }

    public static ClassUnlockMutationResult unlockClass(ProgressionState state, FinalTriadProgress triads, ClassUnlockDefinition definition) {
        if (state.classProgression().isUnlocked(definition.classId())) {
            return new ClassUnlockMutationResult(state, false, 0);
        }
        if (!ClassRequirementPolicy.satisfied(state, definition)) {
            throw new IllegalArgumentException("class contextual requirements are not satisfied: " + definition.classId());
        }
        ClassUnlockResult result = ClassUnlockResolver.evaluate(triads, definition, state.passivePoints().available());
        if (!result.unlockable()) {
            throw new IllegalArgumentException("class cannot be unlocked: " + definition.classId());
        }
        PassivePointLedger ledger = state.passivePoints();
        if (result.bridgeCost() > 0) ledger = ledger.spend(result.bridgeCost());
        ClassProgressionState classes = state.classProgression().unlock(definition.classId());
        ProgressionState next = new ProgressionState(state.totalCharacterXp(), ledger, state.bossProgress(), classes, state.mastery(), state.classChoices(), state.specializations(), state.finalTriads(), state.passiveNodes(), state.discoveries());
        return new ClassUnlockMutationResult(next, true, result.bridgeCost());
    }

    public static ProgressionState purchaseNode(
        ProgressionState state,
        SkillGraph graph,
        NodePurchaseDefinition definition,
        boolean requirementsSatisfied
    ) {
        if (!requirementsSatisfied) throw new IllegalArgumentException("node requirements are not satisfied: " + definition.nodeId());
        int currentRank = state.passiveNodes().rank(definition.nodeId());
        if (currentRank >= definition.maxRank()) throw new IllegalArgumentException("node rank is already capped: " + definition.nodeId());
        if (currentRank == 0 && !definition.startingPoint()) {
            boolean connected = graph.neighbors(definition.nodeId()).stream().anyMatch(state.passiveNodes()::learned);
            if (!connected) throw new IllegalArgumentException("node is not connected to the learned tree: " + definition.nodeId());
        }
        PassivePointLedger ledger = state.passivePoints().spend(definition.costPerRank());
        PassiveNodeProgress nodes = state.passiveNodes().increase(definition.nodeId(), definition.maxRank());
        FinalTriadProgress triads = state.finalTriads();
        if (definition.finalTriadNode()) {
            triads = triads.increase(definition.finalTriadDomain(), definition.finalTriadSlot());
        }
        return new ProgressionState(
            state.totalCharacterXp(),
            ledger,
            state.bossProgress(),
            state.classProgression(),
            state.mastery(),
            state.classChoices(),
            state.specializations(),
            triads,
            nodes,
            state.discoveries()
        );
    }

    public static NodeRespecResult respecNode(
        ProgressionState state,
        SkillGraph graph,
        Map<String, NodePurchaseDefinition> definitions,
        String nodeId
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(graph);
        Objects.requireNonNull(definitions);
        Objects.requireNonNull(nodeId);
        int currentRank = state.passiveNodes().rank(nodeId);
        if (currentRank <= 0) throw new IllegalArgumentException("node is not learned: " + nodeId);

        for (String learnedId : state.passiveNodes().learnedNodeIds()) {
            if (!definitions.containsKey(learnedId)) {
                throw new IllegalArgumentException("missing node definition for learned node: " + learnedId);
            }
        }
        NodePurchaseDefinition targetDefinition = definitions.get(nodeId);
        if (targetDefinition == null) throw new IllegalArgumentException("missing node definition: " + nodeId);

        if (currentRank > 1) {
            PassiveNodeProgress nodes = state.passiveNodes().decrease(nodeId);
            int refund = targetDefinition.costPerRank();
            FinalTriadProgress triads = state.finalTriads();
            if (targetDefinition.finalTriadNode()) {
                triads = triads.decrease(targetDefinition.finalTriadDomain(), targetDefinition.finalTriadSlot(), 1);
            }
            ProgressionState next = state.withPassiveNodes(nodes)
                .withPassivePoints(state.passivePoints().refund(refund))
                .withFinalTriads(triads);
            return new NodeRespecResult(next, Map.of(nodeId, 1), refund);
        }

        PassiveNodeProgress afterTargetRemoval = state.passiveNodes().decrease(nodeId);
        Set<String> remaining = new HashSet<>(afterTargetRemoval.learnedNodeIds());
        Set<String> reachable = new HashSet<>();
        ArrayDeque<String> frontier = new ArrayDeque<>();
        for (String learnedId : remaining) {
            NodePurchaseDefinition definition = definitions.get(learnedId);
            if (definition.startingPoint()) frontier.add(learnedId);
        }
        while (!frontier.isEmpty()) {
            String current = frontier.removeFirst();
            if (!remaining.contains(current) || !reachable.add(current)) continue;
            for (String neighbor : graph.neighbors(current)) {
                if (remaining.contains(neighbor) && !reachable.contains(neighbor)) frontier.addLast(neighbor);
            }
        }

        Set<String> orphaned = new HashSet<>(remaining);
        orphaned.removeAll(reachable);
        Set<String> removedIds = new HashSet<>(orphaned);
        removedIds.add(nodeId);

        Map<String, Integer> removedRanks = new HashMap<>();
        int refund = 0;
        for (String removedId : removedIds) {
            int ranks = state.passiveNodes().rank(removedId);
            if (ranks <= 0) continue;
            NodePurchaseDefinition definition = definitions.get(removedId);
            if (definition == null) throw new IllegalArgumentException("missing node definition: " + removedId);
            removedRanks.put(removedId, ranks);
            refund = Math.addExact(refund, Math.multiplyExact(ranks, definition.costPerRank()));
        }

        PassiveNodeProgress nodes = afterTargetRemoval.without(orphaned);
        FinalTriadProgress triads = state.finalTriads();
        for (Map.Entry<String, Integer> removed : removedRanks.entrySet()) {
            NodePurchaseDefinition definition = definitions.get(removed.getKey());
            if (definition.finalTriadNode()) {
                triads = triads.decrease(
                    definition.finalTriadDomain(),
                    definition.finalTriadSlot(),
                    removed.getValue()
                );
            }
        }
        ProgressionState next = state.withPassiveNodes(nodes)
            .withPassivePoints(state.passivePoints().refund(refund))
            .withFinalTriads(triads);
        return new NodeRespecResult(next, removedRanks, refund);
    }

    public static ProgressionState investFinalTriadRank(ProgressionState state, ProgressionDomain domain, int slot) {
        if (slot < 0 || slot >= 3) throw new IllegalArgumentException("final triad slot must be in 0..2");
        FinalTriadProgress nextTriads = state.finalTriads().increase(domain, slot);
        PassivePointLedger nextLedger = state.passivePoints().spend(1);
        return new ProgressionState(
            state.totalCharacterXp(),
            nextLedger,
            state.bossProgress(),
            state.classProgression(),
            state.mastery(),
            state.classChoices(),
            state.specializations(),
            nextTriads,
            state.passiveNodes(),
            state.discoveries()
        );
    }

    /**
     * Administrative inspection path for learned nodes whose current rule definition is absent.
     * Normal gameplay reconciliation must not invent a historical cost/refund for these nodes.
     */
    public static Set<String> unknownLearnedNodes(
        ProgressionState state,
        Map<String, NodePurchaseDefinition> definitions
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(definitions);
        Set<String> unknown = new HashSet<>();
        for (String learnedId : state.passiveNodes().learnedNodeIds()) {
            if (!definitions.containsKey(learnedId)) unknown.add(learnedId);
        }
        return Set.copyOf(unknown);
    }

    public static NodeAccessReconcileResult reconcileInvalidNodes(
        ProgressionState state,
        SkillGraph graph,
        Map<String, NodePurchaseDefinition> definitions,
        Map<String, NodeAccessRequirement> requirements,
        CharacterLevelCurve curve
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(graph);
        Objects.requireNonNull(definitions);
        Objects.requireNonNull(requirements);
        Objects.requireNonNull(curve);

        // A missing definition has no trustworthy current cost or migration semantics. Fail closed:
        // report it through unknownLearnedNodes() and leave the entire gameplay state untouched for
        // an explicit administrative migration rather than partially mutating/refunding around it.
        if (!unknownLearnedNodes(state, definitions).isEmpty()) {
            return new NodeAccessReconcileResult(state, Map.of(), 0);
        }

        ProgressionState current = state;
        Map<String, Integer> removedRanks = new HashMap<>();
        int refunded = 0;

        boolean changed;
        do {
            changed = false;
            ProgressionState snapshot = current;
            String invalid = snapshot.passiveNodes().learnedNodeIds().stream()
                .sorted()
                .filter(nodeId -> {
                    NodeAccessRequirement requirement = requirements.getOrDefault(nodeId, NodeAccessRequirement.none());
                    return !NodeAccessResolver.satisfied(snapshot, requirement, curve);
                })
                .findFirst()
                .orElse(null);
            if (invalid == null) continue;

            while (current.passiveNodes().rank(invalid) > 0) {
                NodeRespecResult result = respecNode(current, graph, definitions, invalid);
                current = result.state();
                refunded = Math.addExact(refunded, result.pointsRefunded());
                result.removedRanks().forEach((id, rank) -> removedRanks.merge(id, rank, Integer::sum));
            }
            changed = true;
        } while (changed);

        return new NodeAccessReconcileResult(current, removedRanks, refunded);
    }

    public static ProgressionState reconcileNodeSpecializations(
        ProgressionState state,
        java.util.Collection<NodeSpecializationGrant> grants
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(grants);

        // Without persisted provenance, the only specializations we can safely revoke here are
        // IDs explicitly owned by the current node-grant catalog. Every unmanaged/current ID is
        // preserved; active node-owned IDs are then reconstructed from learned ranks.
        Set<String> nodeOwnedIds = new HashSet<>();
        for (NodeSpecializationGrant grant : grants) nodeOwnedIds.add(grant.specializationId());

        SpecializationProgressionState specializations = SpecializationProgressionState.empty();
        for (String currentId : state.specializations().unlockedSpecializationIds()) {
            if (!nodeOwnedIds.contains(currentId)) specializations = specializations.unlock(currentId);
        }
        for (NodeSpecializationGrant grant : grants) {
            if (state.passiveNodes().rank(grant.nodeId()) >= grant.requiredRank()) {
                specializations = specializations.unlock(grant.specializationId());
            }
        }
        return state.withSpecializations(specializations);
    }

    public static ProgressionState reconcileEligibleSpecializations(
        ProgressionState state,
        java.util.Collection<SpecializationDefinition> definitions,
        InvestmentState investment
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(definitions);
        Objects.requireNonNull(investment);

        SpecializationProgressionState specializations = state.specializations();
        for (SpecializationDefinition definition : definitions.stream()
            .sorted(java.util.Comparator.comparing(SpecializationDefinition::specializationId))
            .toList()) {
            boolean unlockable = SpecializationResolver.evaluate(
                state.classProgression().unlockedClassIds(),
                state.mastery(),
                investment,
                definition
            ).unlockable();
            specializations = unlockable
                ? specializations.unlock(definition.specializationId())
                : specializations.without(definition.specializationId());
        }
        return state.withSpecializations(specializations);
    }

    public static ProgressionState reconcileEligibleSpecializationsFromNodes(
        ProgressionState state,
        java.util.Collection<SpecializationDefinition> definitions,
        Map<String, Set<String>> tagsByNode
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(definitions);
        Objects.requireNonNull(tagsByNode);
        InvestmentState investment = NodeInvestmentProjection.from(state.passiveNodes(), tagsByNode);
        return reconcileEligibleSpecializations(state, definitions, investment);
    }
}
