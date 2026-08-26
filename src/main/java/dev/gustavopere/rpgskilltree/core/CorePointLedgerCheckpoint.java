package dev.gustavopere.rpgskilltree.core;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Compact persisted form of the Core Progression Point ledger. */
public record CorePointLedgerCheckpoint(
    Map<String, Long> creditTotalsBySource,
    Map<CorePointAllocation, Long> allocated,
    List<CorePointTransaction> recentTransactions
) {
    public CorePointLedgerCheckpoint {
        Objects.requireNonNull(creditTotalsBySource);
        Objects.requireNonNull(allocated);
        Objects.requireNonNull(recentTransactions);

        Map<String, Long> creditCopy = new HashMap<>();
        long totalCredits = 0L;
        for (Map.Entry<String, Long> entry : creditTotalsBySource.entrySet()) {
            String sourceId = Objects.requireNonNull(entry.getKey());
            Long amount = Objects.requireNonNull(entry.getValue());
            if (sourceId.isBlank()) throw new IllegalArgumentException("credit source id must not be blank");
            if (amount <= 0L) throw new IllegalArgumentException("credit source total must be positive");
            totalCredits = Math.addExact(totalCredits, amount);
            creditCopy.put(sourceId, amount);
        }

        EnumMap<CorePointAllocation, Long> allocationCopy = new EnumMap<>(CorePointAllocation.class);
        long totalAllocated = 0L;
        for (Map.Entry<CorePointAllocation, Long> entry : allocated.entrySet()) {
            CorePointAllocation allocation = Objects.requireNonNull(entry.getKey());
            Long amount = Objects.requireNonNull(entry.getValue());
            if (allocation == CorePointAllocation.NONE) {
                throw new IllegalArgumentException("NONE cannot hold an allocation balance");
            }
            if (amount <= 0L) throw new IllegalArgumentException("allocation total must be positive");
            totalAllocated = Math.addExact(totalAllocated, amount);
            allocationCopy.put(allocation, amount);
        }
        if (totalAllocated > totalCredits) {
            throw new IllegalArgumentException("allocated Core Progression Points exceed total credits");
        }

        if (recentTransactions.size() > CorePointLedger.RECENT_TRANSACTION_LIMIT) {
            throw new IllegalArgumentException("recent transaction window exceeds technical limit");
        }
        Set<String> transactionIds = new HashSet<>();
        for (CorePointTransaction transaction : recentTransactions) {
            Objects.requireNonNull(transaction);
            if (!transactionIds.add(transaction.transactionId())) {
                throw new IllegalArgumentException("duplicate recent transaction id: " + transaction.transactionId());
            }
            if (transaction.kind() == CorePointTransactionKind.EARN
                || transaction.kind() == CorePointTransactionKind.MIGRATION) {
                long aggregate = creditCopy.getOrDefault(transaction.sourceId(), 0L);
                if (aggregate < transaction.amount()) {
                    throw new IllegalArgumentException("recent credit exceeds persisted source aggregate");
                }
            }
        }

        creditTotalsBySource = Map.copyOf(creditCopy);
        allocated = Map.copyOf(allocationCopy);
        recentTransactions = List.copyOf(recentTransactions);
    }

    public long totalCredits() {
        long total = 0L;
        for (long amount : creditTotalsBySource.values()) total = Math.addExact(total, amount);
        return total;
    }

    public long allocated(CorePointAllocation allocation) {
        Objects.requireNonNull(allocation);
        if (allocation == CorePointAllocation.NONE) return 0L;
        return allocated.getOrDefault(allocation, 0L);
    }

    public long totalAllocated() {
        return Math.addExact(
            allocated(CorePointAllocation.ATTRIBUTE),
            allocated(CorePointAllocation.MAIN_PERK)
        );
    }

    public long available() {
        return totalCredits() - totalAllocated();
    }
}
