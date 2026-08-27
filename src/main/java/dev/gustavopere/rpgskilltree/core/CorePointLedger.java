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
 * <p>Long-lived balances and credit provenance are aggregated. Only a bounded
 * window of recent transaction payloads is retained for replay/idempotency
 * protection, preventing runtime and save growth from becoming proportional to
 * the entire history of an uncapped character.</p>
 */
public final class CorePointLedger {
    public static final int RECENT_TRANSACTION_LIMIT = 1_024;

    private final List<CorePointTransaction> transactions;
    private final Map<String, CorePointTransaction> transactionsById;
    private final Map<String, Long> creditTotalsBySource;
    private final long totalCredits;
    private final Map<CorePointAllocation, Long> allocated;

    private CorePointLedger(
        List<CorePointTransaction> transactions,
        Map<String, CorePointTransaction> transactionsById,
        Map<String, Long> creditTotalsBySource,
        long totalCredits,
        Map<CorePointAllocation, Long> allocated
    ) {
        this.transactions = List.copyOf(transactions);
        this.transactionsById = Map.copyOf(transactionsById);
        this.creditTotalsBySource = Map.copyOf(creditTotalsBySource);
        this.totalCredits = totalCredits;
        EnumMap<CorePointAllocation, Long> allocationCopy = new EnumMap<>(CorePointAllocation.class);
        allocationCopy.putAll(allocated);
        this.allocated = Map.copyOf(allocationCopy);
    }

    public static CorePointLedger empty() {
        return new CorePointLedger(List.of(), Map.of(), Map.of(), 0L, Map.of());
    }

    public static CorePointLedger restore(CorePointLedgerCheckpoint checkpoint) {
        Objects.requireNonNull(checkpoint);
        Map<String, CorePointTransaction> recentById = new HashMap<>();
        for (CorePointTransaction transaction : checkpoint.recentTransactions()) {
            recentById.put(transaction.transactionId(), transaction);
        }
        return new CorePointLedger(
            checkpoint.recentTransactions(),
            recentById,
            checkpoint.creditTotalsBySource(),
            checkpoint.totalCredits(),
            checkpoint.allocated()
        );
    }

    public CorePointLedgerCheckpoint checkpoint() {
        return new CorePointLedgerCheckpoint(creditTotalsBySource, allocated, transactions);
    }

    public CorePointLedger apply(CorePointTransaction transaction) {
        Objects.requireNonNull(transaction);
        CorePointTransaction existing = transactionsById.get(transaction.transactionId());
        if (existing != null) {
            if (existing.equals(transaction)) return this;
            throw new IllegalArgumentException("transaction id already used with different payload: " + transaction.transactionId());
        }

        long nextCredits = totalCredits;
        Map<String, Long> nextCreditTotals = new HashMap<>(creditTotalsBySource);
        EnumMap<CorePointAllocation, Long> nextAllocated = new EnumMap<>(CorePointAllocation.class);
        nextAllocated.putAll(allocated);

        switch (transaction.kind()) {
            case EARN, MIGRATION -> {
                nextCredits = Math.addExact(totalCredits, transaction.amount());
                long currentSourceTotal = nextCreditTotals.getOrDefault(transaction.sourceId(), 0L);
                nextCreditTotals.put(
                    transaction.sourceId(),
                    Math.addExact(currentSourceTotal, transaction.amount())
                );
            }
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
        Map<String, CorePointTransaction> nextById = new HashMap<>(transactionsById);
        nextTransactions.add(transaction);
        nextById.put(transaction.transactionId(), transaction);
        if (nextTransactions.size() > RECENT_TRANSACTION_LIMIT) {
            CorePointTransaction expired = nextTransactions.removeFirst();
            nextById.remove(expired.transactionId());
        }

        return new CorePointLedger(
            nextTransactions,
            nextById,
            nextCreditTotals,
            nextCredits,
            nextAllocated
        );
    }

    /** Bounded recent transaction window retained for replay protection. */
    public List<CorePointTransaction> transactions() {
        return transactions;
    }

    /** Aggregate credit provenance retained independently from transaction history. */
    public Map<String, Long> creditTotalsBySource() {
        return creditTotalsBySource;
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
