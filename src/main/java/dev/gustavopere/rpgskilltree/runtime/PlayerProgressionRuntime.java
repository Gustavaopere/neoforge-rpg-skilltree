package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.BossIdentity;
import dev.gustavopere.rpgskilltree.core.BossProgressionResult;
import dev.gustavopere.rpgskilltree.core.BossRewardDefinition;
import dev.gustavopere.rpgskilltree.core.BossRewardKeyPolicy;
import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;
import dev.gustavopere.rpgskilltree.core.CharacterLevelCurve;
import dev.gustavopere.rpgskilltree.core.CharacterXpAward;
import dev.gustavopere.rpgskilltree.core.DiscoveryProgressionResult;
import dev.gustavopere.rpgskilltree.core.MasteryAward;
import dev.gustavopere.rpgskilltree.core.MasteryAwardService;
import dev.gustavopere.rpgskilltree.core.NodeAccessResolver;
import dev.gustavopere.rpgskilltree.core.NodePurchaseResult;
import dev.gustavopere.rpgskilltree.core.ProgressionService;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.data.ClassRuleCatalog;
import dev.gustavopere.rpgskilltree.runtime.data.ClassChoiceCatalog;
import dev.gustavopere.rpgskilltree.runtime.data.TreeRuleCatalog;
import dev.gustavopere.rpgskilltree.runtime.effects.AttributeNodeEffectRuntime;
import dev.gustavopere.rpgskilltree.runtime.network.ModNetworking;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class PlayerProgressionRuntime {
    private static final NodePurchaseRequestProcessor NODE_PURCHASE_PROCESSOR =
        new NodePurchaseRequestProcessor(256);

    // Legacy scaffold marker only. Confirmed mutation sync moved to ProgressionOwnerSyncRuntime:
    // ModNetworking.syncToOwner(player, state)
    private PlayerProgressionRuntime() {}

    public static ProgressionState get(ServerPlayer player) {
        Objects.requireNonNull(player);
        return CanonicalPlayerAttachmentRuntime.readOrMigrate(player).compatibilityProgression();
    }

    public static void syncToOwner(ServerPlayer player) {
        Objects.requireNonNull(player);
        CanonicalPlayerAttachmentData observed = CanonicalPlayerAttachmentRuntime.observe(player);
        ModNetworking.syncToOwner(player, observed.compatibilityProgression());
    }

    public static ProgressionState applyXp(ServerPlayer player, CharacterXpAward award) {
        ProgressionState current = get(player);
        ProgressionState next = ProgressionService.applyXp(current, award, CharacterLevelCurve.defaultCurve());
        set(player, next);
        return next;
    }

    public static ProgressionState awardMastery(ServerPlayer player, Collection<MasteryAward> awards) {
        return awardMasteryAndDiscoveries(player, awards, List.of());
    }

    /**
     * Applies mastery and discovery changes as one canonical compatibility mutation.
     * Provider adapters never need direct access to the persistence boundary.
     */
    public static ProgressionState awardMasteryAndDiscoveries(
        ServerPlayer player,
        Collection<MasteryAward> awards,
        Collection<String> discoveryKeys
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(awards);
        Objects.requireNonNull(discoveryKeys);
        ProgressionState current = get(player);
        if (awards.isEmpty() && discoveryKeys.isEmpty()) return current;

        var mastery = MasteryAwardService.apply(current.mastery(), awards);
        var discoveries = current.discoveries();
        for (String discoveryKey : discoveryKeys) {
            Objects.requireNonNull(discoveryKey, "discoveryKey");
            if (discoveryKey.isBlank()) {
                throw new IllegalArgumentException("discoveryKey must not be blank");
            }
            discoveries = discoveries.add(discoveryKey);
        }

        ProgressionState next = reconcileDerivedState(
            current.withMastery(mastery).withDiscoveries(discoveries)
        );
        set(player, next);
        return next;
    }

    public static DiscoveryProgressionResult creditDiscovery(
        ServerPlayer player,
        String discoveryKey,
        CharacterXpAward award
    ) {
        Objects.requireNonNull(player);
        ProgressionState current = get(player);
        DiscoveryProgressionResult result = ProgressionService.creditDiscovery(
            current, discoveryKey, award, CharacterLevelCurve.defaultCurve());
        if (result.firstDiscovery()) set(player, result.state());
        return result;
    }

    public static BossProgressionResult creditBoss(ServerPlayer player, BossIdentity identity, BossRewardDefinition definition) {
        ProgressionState current = get(player);
        String rewardKey = BossRewardKeyPolicy.resolve(identity);
        BossProgressionResult result = ProgressionService.creditBoss(current, rewardKey, definition);
        if (result.firstDefeat()) set(player, result.state());
        return result;
    }

    /** Compatibility entry point for trusted server callers that do not carry a client request id. */
    public static boolean purchaseNode(ServerPlayer player, ResourceLocation nodeId) {
        return purchaseNode(player, nodeId, "server:" + UUID.randomUUID()).accepted();
    }

    /**
     * Server-authoritative purchase boundary. The client supplies only node identity and
     * an idempotency key; costs, requirements, topology and ranks are resolved server-side.
     */
    public static NodePurchaseResult purchaseNode(
        ServerPlayer player,
        ResourceLocation nodeId,
        String requestId
    ) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(nodeId, "nodeId");
        Objects.requireNonNull(requestId, "requestId");

        ProgressionState current = get(player);
        var definition = TreeRuleCatalog.definition(nodeId);
        if (definition.isEmpty()) {
            return NodePurchaseResult.rejected(current, NodePurchaseResult.Status.UNKNOWN_NODE);
        }

        boolean requirementsSatisfied = NodeAccessResolver.satisfied(
            current,
            TreeRuleCatalog.requirement(nodeId),
            CharacterLevelCurve.defaultCurve()
        );
        NodePurchaseResult result = NODE_PURCHASE_PROCESSOR.purchase(
            player.getUUID(),
            requestId,
            nodeId,
            current,
            TreeRuleCatalog.graph(),
            definition.get(),
            requirementsSatisfied
        );
        if (!result.accepted()) {
            return result;
        }

        ProgressionState reconciled = reconcileDerivedState(result.state());
        set(player, reconciled);
        return NodePurchaseResult.accepted(reconciled);
    }

    public static void clearNodePurchaseRequests(UUID playerId) {
        NODE_PURCHASE_PROCESSOR.clear(playerId);
    }

    public static void clearAllNodePurchaseRequests() {
        NODE_PURCHASE_PROCESSOR.clearAll();
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
            ProgressionState current = get(player);
            var result = ProgressionService.unlockClass(current, definition.get());
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
            ProgressionState current = get(player);
            var result = ProgressionService.respecNode(
                current,
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
        if (!set(player, reconciled)) {
            AttributeNodeEffectRuntime.refresh(player, reconciled);
            ModNetworking.syncToOwner(player, reconciled);
        }
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
            current = ProgressionService.reconcileInvalidNodes(
                current,
                TreeRuleCatalog.graph(),
                TreeRuleCatalog.definitions(),
                TreeRuleCatalog.requirements(),
                CharacterLevelCurve.defaultCurve()
            ).state();

            boolean stable = beforeNodes.equals(current.passiveNodes().learnedNodeIds())
                && beforeClasses.equals(current.classProgression().unlockedClassIds())
                && beforeSpecializations.equals(current.specializations().unlockedSpecializationIds());
            if (stable) return current;
        }
        throw new IllegalStateException("progression reconciliation did not stabilize");
    }

    private static boolean set(ServerPlayer player, ProgressionState state) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(state);
        CanonicalPlayerAttachmentData current = CanonicalPlayerAttachmentRuntime.readOrMigrate(player);
        CanonicalPlayerAttachmentData next = current.withCompatibilityProgression(state);
        if (!CanonicalPlayerAttachmentRuntime.commitMutation(
            player,
            current,
            next,
            ProgressionMutationEvent.Section.COMPATIBILITY
        )) {
            return false;
        }
        AttributeNodeEffectRuntime.refresh(player, state);
        return true;
    }
}
