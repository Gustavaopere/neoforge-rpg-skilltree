package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

/** Immutable, idempotency-addressable Core Progression Point transaction. */
public record CorePointTransaction(
    String transactionId,
    CorePointTransactionKind kind,
    long amount,
    String sourceId,
    CorePointAllocation allocation,
    long rulesVersion
) {
    public CorePointTransaction {
        Objects.requireNonNull(transactionId);
        Objects.requireNonNull(kind);
        Objects.requireNonNull(sourceId);
        Objects.requireNonNull(allocation);
        if (transactionId.isBlank()) throw new IllegalArgumentException("transactionId must not be blank");
        if (sourceId.isBlank()) throw new IllegalArgumentException("sourceId must not be blank");
        if (amount <= 0L) throw new IllegalArgumentException("transaction amount must be positive");
        if (rulesVersion <= 0L) throw new IllegalArgumentException("rulesVersion must be positive");

        boolean credit = kind == CorePointTransactionKind.EARN || kind == CorePointTransactionKind.MIGRATION;
        if (credit && allocation != CorePointAllocation.NONE) {
            throw new IllegalArgumentException("credit transactions must use NONE allocation");
        }
        if (!credit && allocation == CorePointAllocation.NONE) {
            throw new IllegalArgumentException("spend/refund transactions require an allocation");
        }
    }

    public static CorePointTransaction credit(
        String transactionId,
        CorePointTransactionKind kind,
        long amount,
        String sourceId,
        long rulesVersion
    ) {
        if (kind != CorePointTransactionKind.EARN && kind != CorePointTransactionKind.MIGRATION) {
            throw new IllegalArgumentException("credit kind must be EARN or MIGRATION");
        }
        return new CorePointTransaction(transactionId, kind, amount, sourceId, CorePointAllocation.NONE, rulesVersion);
    }

    public static CorePointTransaction allocate(
        String transactionId,
        CorePointTransactionKind kind,
        long amount,
        String sourceId,
        CorePointAllocation allocation,
        long rulesVersion
    ) {
        if (kind != CorePointTransactionKind.SPEND && kind != CorePointTransactionKind.REFUND) {
            throw new IllegalArgumentException("allocation kind must be SPEND or REFUND");
        }
        return new CorePointTransaction(transactionId, kind, amount, sourceId, allocation, rulesVersion);
    }
}
