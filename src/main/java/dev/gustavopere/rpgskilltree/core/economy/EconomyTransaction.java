package dev.gustavopere.rpgskilltree.core.economy;

import java.util.Objects;
import java.util.UUID;

/** Immutable audit entry for one successfully applied monetary mutation. */
public record EconomyTransaction(
    UUID transactionId,
    String causalKey,
    EconomyTransactionKind kind,
    long amount,
    long gameTime,
    long resultingIssuedSupply,
    long resultingRetiredSupply,
    long resultingTreasuryBalance
) {
    public EconomyTransaction {
        Objects.requireNonNull(transactionId, "transactionId");
        Objects.requireNonNull(kind, "kind");
        causalKey = Objects.requireNonNull(causalKey, "causalKey").trim();
        if (causalKey.isEmpty()) {
            throw new IllegalArgumentException("causalKey must not be blank");
        }
        if (amount <= 0L) {
            throw new IllegalArgumentException("amount must be positive");
        }
    }
}
