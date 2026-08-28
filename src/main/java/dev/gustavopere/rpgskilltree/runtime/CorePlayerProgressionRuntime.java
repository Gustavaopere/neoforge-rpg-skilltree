package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.AntiFarmService;
import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeRankCostPolicy;
import dev.gustavopere.rpgskilltree.core.AttributeRankMutationService;
import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;
import dev.gustavopere.rpgskilltree.core.CorePointTransaction;
import dev.gustavopere.rpgskilltree.core.CoreProgressionAttachmentData;
import dev.gustavopere.rpgskilltree.core.CoreProgressionBootstrap;
import dev.gustavopere.rpgskilltree.core.CoreProgressionMutationService;
import dev.gustavopere.rpgskilltree.core.CoreProgressionQueryService;
import dev.gustavopere.rpgskilltree.core.CoreProgressionQuerySnapshot;
import dev.gustavopere.rpgskilltree.core.CoreProgressionState;
import dev.gustavopere.rpgskilltree.core.ProgressionReward;
import dev.gustavopere.rpgskilltree.core.ProgressionRewardService;
import dev.gustavopere.rpgskilltree.core.ProgressionRulesSnapshot;
import dev.gustavopere.rpgskilltree.core.SemanticAction;
import dev.gustavopere.rpgskilltree.core.SemanticProgressionResult;
import dev.gustavopere.rpgskilltree.core.SemanticProgressionService;
import dev.gustavopere.rpgskilltree.core.XpPolicy;
import dev.gustavopere.rpgskilltree.runtime.data.AttributeRankCostPolicyCatalog;
import dev.gustavopere.rpgskilltree.runtime.data.CoreProgressionRulesCatalog;
import dev.gustavopere.rpgskilltree.runtime.network.ModNetworking;
import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;

/** NeoForge boundary for the uncapped Core section of the canonical player RPG state. */
public final class CorePlayerProgressionRuntime {
    private CorePlayerProgressionRuntime() {}

    public static CoreProgressionState bootstrap(
        ServerPlayer player,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(rules);

        CanonicalPlayerAttachmentData current = CanonicalPlayerAttachmentRuntime.readOrMigrate(player);
        CanonicalPlayerAttachmentData initialized = current.initializeCore(rules);
        if (initialized != current) {
            CanonicalPlayerAttachmentRuntime.write(player, initialized);
        }
        return initialized.coreProgression().state().orElseThrow();
    }

    public static CoreProgressionState grantXp(
        ServerPlayer player,
        long amount,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(rules);
        CoreProgressionState current = bootstrap(player, rules);
        CoreProgressionState next = CoreProgressionMutationService.grantXp(current, amount, rules);
        set(player, next, rules);
        return next;
    }

    public static CoreProgressionState applyCorePointTransaction(
        ServerPlayer player,
        CorePointTransaction transaction,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(transaction);
        Objects.requireNonNull(rules);
        CoreProgressionState current = bootstrap(player, rules);
        CoreProgressionState next = CoreProgressionMutationService.applyCorePointTransaction(
            current,
            transaction,
            rules
        );
        set(player, next, rules);
        return next;
    }

    /** Server-authoritative entry point: neither rules nor price policy are accepted from the client. */
    public static CoreProgressionState purchaseAttributeRanks(
        ServerPlayer player,
        AttributeId attribute,
        long rankCount,
        String transactionId,
        String sourceId
    ) {
        ProgressionRulesSnapshot rules = CoreProgressionRulesCatalog.provider().requireCurrent();
        AttributeRankCostPolicy costPolicy = AttributeRankCostPolicyCatalog.provider().requireCurrent();
        return purchaseAttributeRanks(player, attribute, rankCount, transactionId, sourceId, costPolicy, rules);
    }

    public static CoreProgressionState purchaseAttributeRanks(
        ServerPlayer player,
        AttributeId attribute,
        long rankCount,
        String transactionId,
        String sourceId,
        AttributeRankCostPolicy costPolicy,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(costPolicy);
        Objects.requireNonNull(rules);
        CoreProgressionState current = bootstrap(player, rules);
        CoreProgressionState next = AttributeRankMutationService.purchase(
            current,
            attribute,
            rankCount,
            transactionId,
            sourceId,
            costPolicy,
            rules
        );
        if (next != current) {
            set(player, next, rules);
        }
        return next;
    }

    /** Server-authoritative entry point: neither rules nor refund policy are accepted from the client. */
    public static CoreProgressionState refundAttributeRanks(
        ServerPlayer player,
        AttributeId attribute,
        long rankCount,
        String transactionId,
        String sourceId
    ) {
        ProgressionRulesSnapshot rules = CoreProgressionRulesCatalog.provider().requireCurrent();
        AttributeRankCostPolicy costPolicy = AttributeRankCostPolicyCatalog.provider().requireCurrent();
        return refundAttributeRanks(player, attribute, rankCount, transactionId, sourceId, costPolicy, rules);
    }

    public static CoreProgressionState refundAttributeRanks(
        ServerPlayer player,
        AttributeId attribute,
        long rankCount,
        String transactionId,
        String sourceId,
        AttributeRankCostPolicy costPolicy,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(costPolicy);
        Objects.requireNonNull(rules);
        CoreProgressionState current = bootstrap(player, rules);
        CoreProgressionState next = AttributeRankMutationService.refund(
            current,
            attribute,
            rankCount,
            transactionId,
            sourceId,
            costPolicy,
            rules
        );
        if (next != current) {
            set(player, next, rules);
        }
        return next;
    }

    /**
     * Server-authoritative typed reward entry point for quest, boss and milestone adapters.
     * Reward identity/type/amount come from the trusted server adapter; balance rules do not.
     */
    public static CoreProgressionState applyProgressionReward(
        ServerPlayer player,
        ProgressionReward reward
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(reward);
        ProgressionRulesSnapshot rules = CoreProgressionRulesCatalog.provider().requireCurrent();
        CoreProgressionState current = bootstrap(player, rules);
        CoreProgressionState next = ProgressionRewardService.apply(current, reward, rules);
        if (next != current) {
            set(player, next, rules);
        }
        return next;
    }

    /**
     * Stable observational query for quest/provider integrations.
     *
     * <p>The query resolves authoritative server rules but deliberately does not bootstrap,
     * persist, migrate attachments or synchronize the client merely because an adapter
     * asked for progression data. Legacy and new-player states are projected in memory.</p>
     */
    public static CoreProgressionQuerySnapshot queryProgression(
        ServerPlayer player
    ) {
        Objects.requireNonNull(player);
        ProgressionRulesSnapshot rules = CoreProgressionRulesCatalog.provider().requireCurrent();
        CoreProgressionState state = readOnlyState(player, rules);
        return CoreProgressionQueryService.snapshot(state, rules);
    }

    public static SemanticProgressionResult applySemanticAction(
        ServerPlayer player,
        SemanticAction action,
        AntiFarmService antiFarmService,
        XpPolicy xpPolicy,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(action);
        Objects.requireNonNull(antiFarmService);
        Objects.requireNonNull(xpPolicy);
        Objects.requireNonNull(rules);

        CoreProgressionState current = bootstrap(player, rules);
        SemanticProgressionResult result = SemanticProgressionService.apply(
            current,
            action,
            antiFarmService,
            xpPolicy,
            rules
        );
        if (result.state() != current) {
            set(player, result.state(), rules);
        }
        return result;
    }

    private static CoreProgressionState readOnlyState(
        ServerPlayer player,
        ProgressionRulesSnapshot rules
    ) {
        CanonicalPlayerAttachmentData observed = CanonicalPlayerAttachmentRuntime.observe(player);
        CanonicalPlayerAttachmentData initialized = observed.initializeCore(rules);
        return initialized.coreProgression().state().orElseThrow();
    }

    public static void set(
        ServerPlayer player,
        CoreProgressionState state,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(state);
        Objects.requireNonNull(rules);
        CoreProgressionBootstrap.resume(state, rules);
        CanonicalPlayerAttachmentData current = CanonicalPlayerAttachmentRuntime.readOrMigrate(player);
        CanonicalPlayerAttachmentData next = current.withCoreProgression(
            CoreProgressionAttachmentData.initialized(state)
        );
        CanonicalPlayerAttachmentRuntime.write(player, next);
        ModNetworking.syncCoreToOwner(player, state, rules);
    }
}
