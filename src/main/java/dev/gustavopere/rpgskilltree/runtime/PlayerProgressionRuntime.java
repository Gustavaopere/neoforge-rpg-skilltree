package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.BossIdentity;
import dev.gustavopere.rpgskilltree.core.BossProgressionResult;
import dev.gustavopere.rpgskilltree.core.BossRewardDefinition;
import dev.gustavopere.rpgskilltree.core.BossRewardKeyPolicy;
import dev.gustavopere.rpgskilltree.core.CharacterLevelCurve;
import dev.gustavopere.rpgskilltree.core.CharacterXpAward;
import dev.gustavopere.rpgskilltree.core.DiscoveryProgressionResult;
import dev.gustavopere.rpgskilltree.core.FrozenA0051A0100TreeModel;
import dev.gustavopere.rpgskilltree.core.FrozenA0101A0150TreeModel;
import dev.gustavopere.rpgskilltree.core.FrozenBatchAccessPolicy;
import dev.gustavopere.rpgskilltree.core.FrozenCombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.FrozenSurvivalAccessPolicy;
import dev.gustavopere.rpgskilltree.core.FrozenSurvivalPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.MasteryAward;
import dev.gustavopere.rpgskilltree.core.MasteryAwardService;
import dev.gustavopere.rpgskilltree.core.NodeAccessResolver;
import dev.gustavopere.rpgskilltree.core.ProgressionService;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.data.ClassRuleCatalog;
import dev.gustavopere.rpgskilltree.runtime.data.ClassChoiceCatalog;
import dev.gustavopere.rpgskilltree.runtime.data.SpecializationCatalog;
import dev.gustavopere.rpgskilltree.runtime.data.TreeRuleCatalog;
import dev.gustavopere.rpgskilltree.runtime.effects.AttributeNodeEffectRuntime;
import dev.gustavopere.rpgskilltree.runtime.network.ModNetworking;
import java.util.Collection;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class PlayerProgressionRuntime {
    private PlayerProgressionRuntime() {}

    public static ProgressionState get(ServerPlayer player) {
        Objects.requireNonNull(player);
        return player.getData(ModAttachments.PROGRESSION);
    }

    public static ProgressionState applyXp(ServerPlayer player, CharacterXpAward award) {
        ProgressionState next = ProgressionService.applyXp(get(player), award, CharacterLevelCurve.defaultCurve());
        set(player, next);
        return next;
    }

    public static ProgressionState awardMastery(ServerPlayer player, Collection<MasteryAward> awards) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(awards);
        ProgressionState current = get(player);
        if (awards.isEmpty()) return current;

        ProgressionState next = current.withMastery(MasteryAwardService.apply(current.mastery(), awards));
        next = reconcileDerivedState(next);
        set(player, next);
        return next;
    }

    public static DiscoveryProgressionResult creditDiscovery(
        ServerPlayer player,
        String discoveryKey,
        CharacterXpAward award
    ) {
        Objects.requireNonNull(player);
        DiscoveryProgressionResult result = ProgressionService.creditDiscovery(
            get(player), discoveryKey, award, CharacterLevelCurve.defaultCurve());
        if (result.firstDiscovery()) set(player, result.state());
        return result;
    }

    public static BossProgressionResult creditBoss(ServerPlayer player, BossIdentity identity, BossRewardDefinition definition) {
        String rewardKey = BossRewardKeyPolicy.resolve(identity);
        BossProgressionResult result = ProgressionService.creditBoss(get(player), rewardKey, definition);
        if (result.firstDefeat()) set(player, result.state());
        return result;
    }

    public static boolean purchaseNode(ServerPlayer player, ResourceLocation nodeId) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(nodeId);
        var definition = TreeRuleCatalog.definition(nodeId);
        if (definition.isEmpty()) {
            ModNetworking.syncToOwner(player, get(player));
            return false;
        }
        try {
            ProgressionState current = get(player);
            boolean requirementsSatisfied = NodeAccessResolver.satisfied(
                current,
                TreeRuleCatalog.requirement(nodeId),
                CharacterLevelCurve.defaultCurve()
            ) && frozenRuntimeRequirementsSatisfied(current, nodeId.toString());
            ProgressionState next = ProgressionService.purchaseNode(
                current,
                TreeRuleCatalog.graph(),
                definition.get(),
                requirementsSatisfied
            );
            next = reconcileDerivedState(next);
            set(player, next);
            return true;
        } catch (IllegalArgumentException rejectedPurchase) {
            ModNetworking.syncToOwner(player, get(player));
            return false;
        }
    }

    public static boolean selectClassChoice(ServerPlayer player, ResourceLocation choiceId) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(choiceId);
        var definition = ClassChoiceCatalog.definition(choiceId.toString());
        if (definition.isEmpty()) {
            ModNetworking.syncToOwner(player, get(player));
            return false;
        }
        ProgressionState current = get(player);
        if (current.classChoices().selectedInGroup(definition.get().groupId()).contains(definition.get().choiceId())) {
            ModNetworking.syncToOwner(player, current);
            return false;
        }
        try {
            var choices = dev.gustavopere.rpgskilltree.core.ClassChoicePolicy.select(
                current.classChoices(),
                definition.get(),
                current.classProgression().unlockedClassIds(),
                definition.get().defaultGroupCapacity()
            );
            ProgressionState next = reconcileDerivedState(current.withClassChoices(choices));
            set(player, next);
            return true;
        } catch (IllegalArgumentException rejectedChoice) {
            ModNetworking.syncToOwner(player, current);
            return false;
        }
    }

    public static boolean clearClassChoice(ServerPlayer player, ResourceLocation choiceId) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(choiceId);
        var definition = ClassChoiceCatalog.definition(choiceId.toString());
        if (definition.isEmpty()) {
            ModNetworking.syncToOwner(player, get(player));
            return false;
        }
        ProgressionState current = get(player);
        if (!current.classChoices().selectedInGroup(definition.get().groupId()).contains(definition.get().choiceId())) {
            ModNetworking.syncToOwner(player, current);
            return false;
        }
        var choices = current.classChoices().withoutSelection(definition.get().groupId(), definition.get().choiceId());
        ProgressionState next = reconcileDerivedState(current.withClassChoices(choices));
        set(player, next);
        return true;
    }

    public static boolean unlockPaidClass(ServerPlayer player, ResourceLocation classId) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(classId);
        if (!classId.getNamespace().equals("rpgskilltree")) {
            ModNetworking.syncToOwner(player, get(player));
            return false;
        }
        var definition = ClassRuleCatalog.definition(classId.getPath());
        if (definition.isEmpty() || definition.get().nonAdjacentBridgeCost() <= 0) {
            ModNetworking.syncToOwner(player, get(player));
            return false;
        }
        try {
            var result = ProgressionService.unlockClass(get(player), definition.get());
            ProgressionState next = reconcileDerivedState(result.state());
            set(player, next);
            return result.unlockedNow();
        } catch (IllegalArgumentException rejectedUnlock) {
            ModNetworking.syncToOwner(player, get(player));
            return false;
        }
    }

    public static boolean respecNode(ServerPlayer player, ResourceLocation nodeId) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(nodeId);
        try {
            var result = ProgressionService.respecNode(
                get(player),
                TreeRuleCatalog.graph(),
                TreeRuleCatalog.definitions(),
                nodeId.toString()
            );
            ProgressionState reconciled = reconcileDerivedState(result.state());
            set(player, reconciled);
            return true;
        } catch (IllegalArgumentException rejectedRespec) {
            ModNetworking.syncToOwner(player, get(player));
            return false;
        }
    }

    /** Revalidates persisted progression against the currently loaded datapack rules. */
    public static ProgressionState reconcilePlayerState(ServerPlayer player) {
        Objects.requireNonNull(player);
        ProgressionState reconciled = reconcileDerivedState(get(player));
        set(player, reconciled);
        return reconciled;
    }

    private static ProgressionState reconcileDerivedState(ProgressionState initial) {
        ProgressionState current = initial;
        for (int iteration = 0; iteration < 32; iteration++) {
            var beforeNodes = current.passiveNodes().learnedNodeIds();
            var beforeClasses = current.classProgression().unlockedClassIds();
            var beforeSpecializations = current.specializations().unlockedSpecializationIds();

            current = ProgressionService.reconcileAutomaticClasses(
                current, ClassRuleCatalog.definitions()).state();
            current = ProgressionService.reconcileNodeSpecializations(
                current, TreeRuleCatalog.specializationGrants());
            current = ProgressionService.reconcileEligibleSpecializationsFromNodes(
                current,
                SpecializationCatalog.definitions(),
                TreeRuleCatalog.tagsByNode()
            );
            current = ProgressionService.reconcileInvalidNodes(
                current,
                TreeRuleCatalog.graph(),
                TreeRuleCatalog.definitions(),
                TreeRuleCatalog.requirements(),
                CharacterLevelCurve.defaultCurve()
            ).state();
            current = reconcileFrozenRuntimeRequirements(current);

            boolean stable = beforeNodes.equals(current.passiveNodes().learnedNodeIds())
                && beforeClasses.equals(current.classProgression().unlockedClassIds())
                && beforeSpecializations.equals(current.specializations().unlockedSpecializationIds());
            if (stable) return current;
        }
        throw new IllegalStateException("progression reconciliation did not stabilize");
    }

    private static boolean frozenRuntimeRequirementsSatisfied(ProgressionState state, String nodeId) {
        var combatCode = FrozenCombatPerkNodeBinding.catalogCode(nodeId);
        if (combatCode.isPresent()) {
            var node = FrozenA0051A0100TreeModel.node(combatCode.get()).orElseThrow();
            boolean registryPresent = node.requiredSpecializations().stream()
                .allMatch(id -> SpecializationCatalog.definition(id).isPresent());
            return registryPresent && FrozenBatchAccessPolicy.satisfied(
                node.specialGate(),
                state.specializations().unlockedSpecializationIds(),
                state.passiveNodes().learnedNodeIds()
            );
        }
        var survivalCode = FrozenSurvivalPerkNodeBinding.catalogCode(nodeId);
        if (survivalCode.isEmpty()) return true;
        var node = FrozenA0101A0150TreeModel.node(survivalCode.get()).orElseThrow();
        boolean registryPresent = node.requiredSpecializations().stream()
            .allMatch(id -> SpecializationCatalog.definition(id).isPresent());
        return registryPresent && FrozenSurvivalAccessPolicy.satisfied(
            node.specialGate(),
            state.passiveNodes(),
            state.specializations().unlockedSpecializationIds()
        );
    }

    private static ProgressionState reconcileFrozenRuntimeRequirements(ProgressionState initial) {
        ProgressionState current = initial;
        while (true) {
            ProgressionState snapshot = current;
            String invalid = snapshot.passiveNodes().learnedNodeIds().stream()
                .sorted()
                .filter(id -> FrozenCombatPerkNodeBinding.catalogCode(id).isPresent()
                    || FrozenSurvivalPerkNodeBinding.catalogCode(id).isPresent())
                .filter(id -> !frozenRuntimeRequirementsSatisfied(snapshot, id))
                .findFirst()
                .orElse(null);
            if (invalid == null) return current;
            while (current.passiveNodes().rank(invalid) > 0) {
                current = ProgressionService.respecNode(
                    current, TreeRuleCatalog.graph(), TreeRuleCatalog.definitions(), invalid).state();
            }
        }
    }

    public static void set(ServerPlayer player, ProgressionState state) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(state);
        player.setData(ModAttachments.PROGRESSION, state);
        AttributeNodeEffectRuntime.refresh(player, state);
        FrozenSurvivalRuntimeState.revalidate(player, state);
        ModNetworking.syncToOwner(player, state);
    }
}
