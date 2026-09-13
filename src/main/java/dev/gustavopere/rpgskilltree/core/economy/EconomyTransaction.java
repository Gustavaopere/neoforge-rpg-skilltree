package dev.gustavopere.rpgskilltree.core.economy;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** Immutable audit entry for one successfully applied monetary mutation. */
public record EconomyTransaction(
    UUID transactionId,
    EconomyColonyKey colonyKey,
    String causalKey,
    EconomyTransactionKind kind,
    long amount,
    String source,
    String counterparty,
    long gameTime,
    long resultingIssuedSupply,
    long resultingRetiredSupply,
    long resultingEffectiveSupply,
    long resultingTreasuryBalance,
    Map<String, String> metadata
) {
    public EconomyTransaction {
        Objects.requireNonNull(transactionId, "transactionId");
        Objects.requireNonNull(colonyKey, "colonyKey");
        Objects.requireNonNull(kind, "kind");
        causalKey = requireNonBlank(causalKey, "causalKey");
        source = requireNonBlank(source, "source");
        counterparty = requireNonBlank(counterparty, "counterparty");
        metadata = Map.copyOf(Objects.requireNonNull(metadata, "metadata"));
        if (amount <= 0L) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (resultingIssuedSupply < 0L
            || resultingRetiredSupply < 0L
            || resultingEffectiveSupply < 0L
            || resultingTreasuryBalance < 0L) {
            throw new IllegalArgumentException("resulting monetary values must be non-negative");
        }
        if (resultingRetiredSupply > resultingIssuedSupply) {
            throw new IllegalArgumentException("retired supply must not exceed issued supply");
        }
        long expectedEffective = Math.subtractExact(resultingIssuedSupply, resultingRetiredSupply);
        if (resultingEffectiveSupply != expectedEffective) {
            throw new IllegalArgumentException("resultingEffectiveSupply does not reconcile");
        }
        if (resultingTreasuryBalance > resultingEffectiveSupply) {
            throw new IllegalArgumentException("treasury must not exceed effective supply");
        }
    }

    private static String requireNonBlank(String value, String name) {
        String normalized = Objects.requireNonNull(value, name).trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return normalized;
    }
}
