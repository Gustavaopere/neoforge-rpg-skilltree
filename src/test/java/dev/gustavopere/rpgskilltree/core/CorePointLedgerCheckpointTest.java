package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CorePointLedgerCheckpointTest {
    public static void main(String[] args) {
        checkpointAggregatesProvenanceAndAllocation();
        restoredCheckpointPreservesBoundedReplayGuard();
        recentTransactionWindowIsBounded();
        invalidCheckpointsAreRejected();
        System.out.println("CorePointLedgerCheckpointTest: PASS");
    }

    private static void checkpointAggregatesProvenanceAndAllocation() {
        CorePointLedger ledger = CorePointLedger.empty()
            .apply(CorePointTransaction.credit("earn:1", CorePointTransactionKind.EARN, 5L, "level", 1L))
            .apply(CorePointTransaction.credit("earn:2", CorePointTransactionKind.EARN, 7L, "level", 1L))
            .apply(CorePointTransaction.credit("earn:3", CorePointTransactionKind.MIGRATION, 3L, "legacy:boss", 1L))
            .apply(CorePointTransaction.allocate("spend:a", CorePointTransactionKind.SPEND, 4L, "attribute:strength", CorePointAllocation.ATTRIBUTE, 1L))
            .apply(CorePointTransaction.allocate("spend:p", CorePointTransactionKind.SPEND, 6L, "perk:tree", CorePointAllocation.MAIN_PERK, 1L))
            .apply(CorePointTransaction.allocate("refund:p", CorePointTransactionKind.REFUND, 2L, "perk:respec", CorePointAllocation.MAIN_PERK, 1L));

        CorePointLedgerCheckpoint checkpoint = ledger.checkpoint();
        eq(Map.of("level", 12L, "legacy:boss", 3L), checkpoint.creditTotalsBySource());
        eq(15L, checkpoint.totalCredits());
        eq(4L, checkpoint.allocated(CorePointAllocation.ATTRIBUTE));
        eq(4L, checkpoint.allocated(CorePointAllocation.MAIN_PERK));
        eq(7L, checkpoint.available());
        eq(ledger.transactions(), checkpoint.recentTransactions());
    }

    private static void restoredCheckpointPreservesBoundedReplayGuard() {
        CorePointTransaction earned = CorePointTransaction.credit(
            "earn:stable",
            CorePointTransactionKind.EARN,
            9L,
            "quest",
            2L
        );
        CorePointLedger original = CorePointLedger.empty().apply(earned);
        CorePointLedger restored = CorePointLedger.restore(original.checkpoint());

        eq(original.totalCredits(), restored.totalCredits());
        eq(original.creditTotalsBySource(), restored.creditTotalsBySource());
        eq(original.transactions(), restored.transactions());
        eq(true, restored == restored.apply(earned));

        CorePointLedger spent = restored.apply(CorePointTransaction.allocate(
            "spend:after_restore",
            CorePointTransactionKind.SPEND,
            3L,
            "attribute:vitality",
            CorePointAllocation.ATTRIBUTE,
            2L
        ));
        eq(6L, spent.available());
    }

    private static void recentTransactionWindowIsBounded() {
        CorePointLedger ledger = CorePointLedger.empty();
        int operations = CorePointLedger.RECENT_TRANSACTION_LIMIT + 76;
        CorePointTransaction last = null;
        for (int i = 0; i < operations; i++) {
            last = CorePointTransaction.credit(
                "bulk:" + i,
                CorePointTransactionKind.EARN,
                1L,
                "level",
                3L
            );
            ledger = ledger.apply(last);
        }

        eq((long) operations, ledger.totalCredits());
        eq((long) operations, ledger.creditTotalsBySource().get("level"));
        eq(CorePointLedger.RECENT_TRANSACTION_LIMIT, ledger.transactions().size());
        eq(true, ledger == ledger.apply(last));
        eq(CorePointLedger.RECENT_TRANSACTION_LIMIT, ledger.checkpoint().recentTransactions().size());
    }

    private static void invalidCheckpointsAreRejected() {
        expect(IllegalArgumentException.class, () -> new CorePointLedgerCheckpoint(
            Map.of("", 1L),
            Map.of(),
            List.of()
        ));
        expect(IllegalArgumentException.class, () -> new CorePointLedgerCheckpoint(
            Map.of("level", 5L),
            Map.of(CorePointAllocation.MAIN_PERK, 6L),
            List.of()
        ));
        CorePointTransaction duplicate = CorePointTransaction.credit(
            "same:id", CorePointTransactionKind.EARN, 1L, "level", 1L);
        expect(IllegalArgumentException.class, () -> new CorePointLedgerCheckpoint(
            Map.of("level", 2L),
            Map.of(),
            List.of(duplicate, duplicate)
        ));
    }

    private static void expect(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable thrown) {
            if (type.isInstance(thrown)) return;
            throw new AssertionError("expected " + type.getSimpleName() + " but got " + thrown, thrown);
        }
        throw new AssertionError("expected " + type.getSimpleName());
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
