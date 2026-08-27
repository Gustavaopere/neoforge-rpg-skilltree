package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Atomic pure mutations coupling attribute ranks to the shared Core Point ledger. */
public final class AttributeRankMutationService {
    private AttributeRankMutationService() {}

    public static CoreProgressionState purchase(
        CoreProgressionState state,
        AttributeId attribute,
        long rankCount,
        String transactionId,
        String sourceId,
        AttributeRankCostPolicy costPolicy,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(costPolicy);
        Objects.requireNonNull(rules);
        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        validateRequest(rankCount, transactionId, sourceId);

        String ledgerSourceId = mutationSource(sourceId, attribute, rankCount);
        CorePointTransaction existing = recentTransaction(validated.corePoints(), transactionId);
        if (existing != null) {
            requireMatchingReplay(existing, CorePointTransactionKind.SPEND, ledgerSourceId, rules.version());
            return validated;
        }

        long startRank = validated.attributeRanks().rank(attribute);
        long cost = requirePositiveCost(costPolicy.cost(attribute, startRank, rankCount));
        CorePointTransaction transaction = CorePointTransaction.allocate(
            transactionId,
            CorePointTransactionKind.SPEND,
            cost,
            ledgerSourceId,
            CorePointAllocation.ATTRIBUTE,
            rules.version()
        );
        CorePointLedger nextLedger = CorePointEconomyService.apply(
            validated.corePoints(),
            rules.mainPerkBudget(),
            transaction
        );
        AttributeRanks nextRanks = validated.attributeRanks().increase(attribute, rankCount);
        return withAttributeMutation(validated, nextLedger, nextRanks);
    }

    public static CoreProgressionState refund(
        CoreProgressionState state,
        AttributeId attribute,
        long rankCount,
        String transactionId,
        String sourceId,
        AttributeRankCostPolicy costPolicy,
        ProgressionRulesSnapshot rules
    ) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(costPolicy);
        Objects.requireNonNull(rules);
        CoreProgressionState validated = CoreProgressionBootstrap.resume(state, rules);
        validateRequest(rankCount, transactionId, sourceId);

        String ledgerSourceId = mutationSource(sourceId, attribute, rankCount);
        CorePointTransaction existing = recentTransaction(validated.corePoints(), transactionId);
        if (existing != null) {
            requireMatchingReplay(existing, CorePointTransactionKind.REFUND, ledgerSourceId, rules.version());
            return validated;
        }

        long currentRank = validated.attributeRanks().rank(attribute);
        if (rankCount > currentRank) {
            throw new IllegalArgumentException("cannot refund more attribute ranks than allocated");
        }
        long startRank = currentRank - rankCount;
        long refund = requirePositiveCost(costPolicy.cost(attribute, startRank, rankCount));
        CorePointTransaction transaction = CorePointTransaction.allocate(
            transactionId,
            CorePointTransactionKind.REFUND,
            refund,
            ledgerSourceId,
            CorePointAllocation.ATTRIBUTE,
            rules.version()
        );
        CorePointLedger nextLedger = CorePointEconomyService.apply(
            validated.corePoints(),
            rules.mainPerkBudget(),
            transaction
        );
        AttributeRanks nextRanks = validated.attributeRanks().decrease(attribute, rankCount);
        return withAttributeMutation(validated, nextLedger, nextRanks);
    }

    private static void validateRequest(long rankCount, String transactionId, String sourceId) {
        if (rankCount <= 0L) throw new IllegalArgumentException("rankCount must be positive");
        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("transactionId must not be blank");
        }
        if (sourceId == null || sourceId.isBlank()) {
            throw new IllegalArgumentException("sourceId must not be blank");
        }
    }

    private static long requirePositiveCost(long cost) {
        if (cost <= 0L) throw new IllegalArgumentException("attribute rank cost must be positive");
        return cost;
    }

    private static String mutationSource(String sourceId, AttributeId attribute, long rankCount) {
        return sourceId + "|attribute=" + attribute.serializedId() + "|ranks=" + rankCount;
    }

    private static CorePointTransaction recentTransaction(CorePointLedger ledger, String transactionId) {
        for (CorePointTransaction transaction : ledger.transactions()) {
            if (transaction.transactionId().equals(transactionId)) return transaction;
        }
        return null;
    }

    private static void requireMatchingReplay(
        CorePointTransaction existing,
        CorePointTransactionKind expectedKind,
        String expectedSourceId,
        long expectedRulesVersion
    ) {
        if (existing.kind() != expectedKind
            || existing.allocation() != CorePointAllocation.ATTRIBUTE
            || !existing.sourceId().equals(expectedSourceId)
            || existing.rulesVersion() != expectedRulesVersion) {
            throw new IllegalArgumentException(
                "transaction id already used for a different attribute rank mutation: "
                    + existing.transactionId()
            );
        }
    }

    private static CoreProgressionState withAttributeMutation(
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
