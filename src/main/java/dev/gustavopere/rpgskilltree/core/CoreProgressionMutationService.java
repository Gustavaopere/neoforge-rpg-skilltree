package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/**
 * Pure mutation boundary for the authoritative uncapped Core progression state.
 *
 * <p>This service does not decide reward amounts. Callers provide already-quantified
 * XP grants or Core Point transactions; this boundary validates the rules snapshot,
 * delegates arithmetic/economy policy, and preserves migration audit metadata.</p>
 */
public final class CoreProgressionMutationService {
    private CoreProgressionMutationService() {}

    public static CoreProgressionState grantXp(
        CoreProgressionState state,
        long amount,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(rules);
        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        CharacterXpGrantResult result = CharacterProgressionService.grantXp(
            validated.characterProgression(),
            amount,
            rules.levelCurve()
        );
        return withCharacterProgression(validated, result.after());
    }

    public static CoreProgressionState applyCorePointTransaction(
        CoreProgressionState state,
        CorePointTransaction transaction,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(transaction);
        Objects.requireNonNull(rules);
        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        if (transaction.rulesVersion() != rules.version()) {
            throw new IllegalArgumentException(
                "Core Point transaction rules version does not match current rules snapshot"
            );
        }
        CorePointLedger nextLedger = CorePointEconomyService.apply(
            validated.corePoints(),
            rules.mainPerkBudget(),
            transaction
        );
        return withCorePoints(validated, nextLedger);
    }

    /**
     * Purchases exactly one attribute rank as one atomic Core mutation.
     *
     * <p>The price is supplied by a balance policy. This foundation therefore stores
     * no implicit 1:1 rank-to-point rule. A recent identical transaction replay returns
     * the already-mutated state without charging or incrementing twice.</p>
     */
    public static CoreProgressionState purchaseAttributeRank(
        CoreProgressionState state,
        AttributeId attribute,
        String transactionId,
        AttributeRankCostPolicy costPolicy,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(transactionId);
        Objects.requireNonNull(costPolicy);
        Objects.requireNonNull(rules);
        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        String sourceId = "attribute:" + attribute.serializedId();

        for (CorePointTransaction recent : validated.corePoints().transactions()) {
            if (!recent.transactionId().equals(transactionId)) continue;
            if (recent.kind() != CorePointTransactionKind.SPEND
                || recent.allocation() != CorePointAllocation.ATTRIBUTE
                || recent.rulesVersion() != rules.version()
                || !recent.sourceId().equals(sourceId)) {
                throw new IllegalArgumentException(
                    "transaction id already used for a different Core mutation: " + transactionId
                );
            }
            return validated;
        }

        long currentRank = validated.attributeRanks().rank(attribute);
        long cost = costPolicy.cost(attribute, currentRank, 1L);
        if (cost <= 0L) {
            throw new IllegalArgumentException("attribute rank cost must be positive");
        }

        CorePointTransaction spend = CorePointTransaction.allocate(
            transactionId,
            CorePointTransactionKind.SPEND,
            cost,
            sourceId,
            CorePointAllocation.ATTRIBUTE,
            rules.version()
        );
        CorePointLedger nextLedger = CorePointEconomyService.apply(
            validated.corePoints(),
            rules.mainPerkBudget(),
            spend
        );
        AttributeRanks nextRanks = validated.attributeRanks().increase(attribute, 1L);
        return withCorePointsAndAttributeRanks(validated, nextLedger, nextRanks);
    }

    private static CoreProgressionState withCharacterProgression(
        CoreProgressionState state,
        CharacterProgressionState characterProgression
    ) {
        return new CoreProgressionState(
            characterProgression,
            state.corePoints(),
            state.attributeRanks(),
            state.rulesVersion(),
            state.rulesFingerprint(),
            state.migrationSourceFormatVersion(),
            state.discardedLegacyCapXp()
        );
    }

    private static CoreProgressionState withCorePoints(
        CoreProgressionState state,
        CorePointLedger corePoints
    ) {
        return new CoreProgressionState(
            state.characterProgression(),
            corePoints,
            state.attributeRanks(),
            state.rulesVersion(),
            state.rulesFingerprint(),
            state.migrationSourceFormatVersion(),
            state.discardedLegacyCapXp()
        );
    }

    private static CoreProgressionState withCorePointsAndAttributeRanks(
        CoreProgressionState state,
        CorePointLedger corePoints,
        AttributeRanks attributeRanks
    ) {
        return new CoreProgressionState(
            state.characterProgression(),
            corePoints,
            attributeRanks,
            state.rulesVersion(),
            state.rulesFingerprint(),
            state.migrationSourceFormatVersion(),
            state.discardedLegacyCapXp()
        );
    }
}
