package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.AntiFarmService;
import dev.gustavopere.rpgskilltree.core.AttributeId;
import dev.gustavopere.rpgskilltree.core.AttributeRankCostPolicy;
import dev.gustavopere.rpgskilltree.core.AttributeRankMutationService;
import dev.gustavopere.rpgskilltree.core.CorePointTransaction;
import dev.gustavopere.rpgskilltree.core.CoreProgressionAttachmentData;
import dev.gustavopere.rpgskilltree.core.CoreProgressionBootstrap;
import dev.gustavopere.rpgskilltree.core.CoreProgressionMutationService;
import dev.gustavopere.rpgskilltree.core.CoreProgressionState;
import dev.gustavopere.rpgskilltree.core.ProgressionRulesSnapshot;
import dev.gustavopere.rpgskilltree.core.SemanticAction;
import dev.gustavopere.rpgskilltree.core.SemanticProgressionResult;
import dev.gustavopere.rpgskilltree.core.SemanticProgressionService;
import dev.gustavopere.rpgskilltree.core.XpPolicy;
import dev.gustavopere.rpgskilltree.runtime.network.ModNetworking;
import java.util.Objects;
import net.minecraft.server.level.ServerPlayer;

/**
 * NeoForge boundary for the uncapped Core progression beside the legacy runtime.
 *
 * <p>This class deliberately requires an explicit rules snapshot. Automatic login
 * bootstrap is not enabled until the runtime has an authoritative rules provider.</p>
 */
public final class CorePlayerProgressionRuntime {
    private CorePlayerProgressionRuntime() {}

    public static CoreProgressionState bootstrap(
        ServerPlayer player,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(rules);

        if (player.hasData(ModAttachments.CORE_PROGRESSION)) {
            CoreProgressionAttachmentData existing = player.getData(ModAttachments.CORE_PROGRESSION);
            if (existing.isInitialized()) {
                return CoreProgressionBootstrap.resume(existing.state().orElseThrow(), rules);
            }
        }

        final CoreProgressionState initialized;
        if (player.hasData(ModAttachments.PROGRESSION)) {
            initialized = CoreProgressionBootstrap.migrateDecodedLegacy(
                player.getData(ModAttachments.PROGRESSION),
                rules
            );
        } else {
            initialized = CoreProgressionBootstrap.newPlayer(rules);
        }

        player.setData(
            ModAttachments.CORE_PROGRESSION,
            CoreProgressionAttachmentData.initialized(initialized)
        );
        return initialized;
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

    public static void set(
        ServerPlayer player,
        CoreProgressionState state,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(state);
        Objects.requireNonNull(rules);
        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        player.setData(
            ModAttachments.CORE_PROGRESSION,
            CoreProgressionAttachmentData.initialized(validated)
        );
        ModNetworking.syncCoreToOwner(player, validated, rules);
    }
}
