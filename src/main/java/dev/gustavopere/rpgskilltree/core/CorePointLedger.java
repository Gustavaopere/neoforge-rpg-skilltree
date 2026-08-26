package dev.gustavopere.rpgskilltree.core;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable Core Progression Point ledger.
 *
 * <p>Transaction ids provide replay/idempotency safety. Credits are retained by
 * provenance while current allocation is tracked independently for attributes
 * and the finite main perk tree.</p>
 */
public final class CorePointLedger {
    private final List<CorePointTransaction> transactions;
    private final Map<String, CorePointTransaction> transactionsById;
    private final long totalCredits;
    private final Map<CorePointAllocation, Long> allocated;

    private CorePointLedger(
        List<CorePointTransaction> transactions,
        Map<String, CorePointTransaction> transactionsById,
        long totalCredits,
        Map<CorePointAllocation, Long> allocated
    ) {
        this.transactions = List.copyOf(transactions);
        this.transactionsById = Map.copyOf(transactionsById);
        this.totalCredits = totalCredits;
        EnumMap<CorePointAllocation, Long> allocationCopy = new EnumMap<>(CorePointAllocation.class);
        allocationCopy.putAll(allocated);
        this.allocated = Map.copyOf(allocationCopy);
    }

    public static CorePointLedger empty() {
        return new CorePointLedger(List.of(), Map.of(), 0L, Map.of());
    }

    public CorePointLedger apply(CorePointTransaction transaction) {
        Objects.requireNonNull(transaction);
        CorePointTransaction existing = transactionsById.get(transaction.transactionId());
        if (existing != null) {
            if (existing.equals(transaction)) return this;
            throw new IllegalArgumentException("transaction id already used with different payload: " + transaction.transactionId());
        }

        long nextCredits = totalCredits;
        EnumMap<CorePointAllocation, Long> nextAllocated = new EnumMap<>(CorePointAllocation.class);
        nextAllocated.putAll(allocated);

        switch (transaction.kind()) {
            case EARN, MIGRATION -> nextCredits = Math.addExact(totalCredits, transaction.amount());
            case SPEND -> {
                if (transaction.amount() > available()) {
                    throw new IllegalArgumentException("insufficient Core Progression Points");
                }
                long current = allocated(transaction.allocation());
                nextAllocated.put(transaction.allocation(), Math.addExact(current, transaction.amount()));
            }
            case REFUND -> {
                long current = allocated(transaction.allocation());
                if (transaction.amount() > current) {
                    throw new IllegalArgumentException("refund exceeds current allocation");
                }
                long remainder = current - transaction.amount();
                if (remainder == 0L) nextAllocated.remove(transaction.allocation());
                else nextAllocated.put(transaction.allocation(), remainder);
            }
        }

        List<CorePointTransaction> nextTransactions = new ArrayList<>(transactions);
        nextTransactions.add(transaction);
        Map<String, CorePointTransaction> nextById = new HashMap<>(transactionsById);
        nextById.put(transaction.transactionId(), transaction);
        return new CorePointLedger(nextTransactions, nextById, nextCredits, nextAllocated);
    }

    public List<CorePointTransaction> transactions() {
        return transactions;
    }

    public long totalCredits() {
        return totalCredits;
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
        return totalCredits - totalAllocated();
    }
}
