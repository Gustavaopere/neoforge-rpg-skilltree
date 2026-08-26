package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public final class CorePointEconomyTest {
    public static void main(String[] args) {
        ledgerTracksOriginSpendRefundAndMigration();
        duplicateTransactionIsIdempotent();
        conflictingTransactionIdentityIsRejected();
        perkBudgetOnlyConstrainsMainPerkAllocation();
        refundRestoresPointsAndBudgetHeadroom();
        refundCannotExceedAllocation();
        arithmeticOverflowIsExplicit();
        System.out.println("CorePointEconomyTest: PASS");
    }

    private static void ledgerTracksOriginSpendRefundAndMigration() {
        CorePointLedger ledger = CorePointLedger.empty()
            .apply(CorePointTransaction.credit("level:1", CorePointTransactionKind.EARN, 5L, "level_up", 1L))
            .apply(CorePointTransaction.credit("migration:v5", CorePointTransactionKind.MIGRATION, 2L, "legacy_passive_points", 1L))
            .apply(CorePointTransaction.allocate("attribute:str:1", CorePointTransactionKind.SPEND, 3L, "strength", CorePointAllocation.ATTRIBUTE, 1L));

        eq(7L, ledger.totalCredits());
        eq(3L, ledger.allocated(CorePointAllocation.ATTRIBUTE));
        eq(0L, ledger.allocated(CorePointAllocation.MAIN_PERK));
        eq(4L, ledger.available());
        eq(3, ledger.transactions().size());
    }

    private static void duplicateTransactionIsIdempotent() {
        var earn = CorePointTransaction.credit("quest:first_steps", CorePointTransactionKind.EARN, 4L, "quest:first_steps", 2L);
        CorePointLedger once = CorePointLedger.empty().apply(earn);
        CorePointLedger twice = once.apply(earn);
        eq(4L, twice.available());
        eq(1, twice.transactions().size());
    }

    private static void conflictingTransactionIdentityIsRejected() {
        var first = CorePointTransaction.credit("quest:key", CorePointTransactionKind.EARN, 2L, "quest:a", 1L);
        var conflict = CorePointTransaction.credit("quest:key", CorePointTransactionKind.EARN, 3L, "quest:a", 1L);
        CorePointLedger ledger = CorePointLedger.empty().apply(first);
        expect(IllegalArgumentException.class, () -> ledger.apply(conflict));
    }

    private static void perkBudgetOnlyConstrainsMainPerkAllocation() {
        CorePointLedger ledger = CorePointLedger.empty()
            .apply(CorePointTransaction.credit("seed", CorePointTransactionKind.EARN, 10L, "test", 1L));
        MainPerkBudget budget = new MainPerkBudget(3L);

        ledger = CorePointEconomyService.apply(
            ledger,
            budget,
            CorePointTransaction.allocate("attr:1", CorePointTransactionKind.SPEND, 6L, "strength", CorePointAllocation.ATTRIBUTE, 1L)
        );
        eq(4L, ledger.available());
        eq(0L, ledger.allocated(CorePointAllocation.MAIN_PERK));

        ledger = CorePointEconomyService.apply(
            ledger,
            budget,
            CorePointTransaction.allocate("perk:1", CorePointTransactionKind.SPEND, 3L, "node:a0001", CorePointAllocation.MAIN_PERK, 1L)
        );
        eq(1L, ledger.available());
        eq(3L, ledger.allocated(CorePointAllocation.MAIN_PERK));

        CorePointLedger capped = ledger;
        expect(IllegalArgumentException.class, () -> CorePointEconomyService.apply(
            capped,
            budget,
            CorePointTransaction.allocate("perk:2", CorePointTransactionKind.SPEND, 1L, "node:a0002", CorePointAllocation.MAIN_PERK, 1L)
        ));
    }

    private static void refundRestoresPointsAndBudgetHeadroom() {
        MainPerkBudget budget = new MainPerkBudget(2L);
        CorePointLedger ledger = CorePointLedger.empty()
            .apply(CorePointTransaction.credit("seed", CorePointTransactionKind.EARN, 4L, "test", 1L));
        ledger = CorePointEconomyService.apply(
            ledger,
            budget,
            CorePointTransaction.allocate("perk:buy", CorePointTransactionKind.SPEND, 2L, "node:a", CorePointAllocation.MAIN_PERK, 1L)
        );
        ledger = CorePointEconomyService.apply(
            ledger,
            budget,
            CorePointTransaction.allocate("perk:refund", CorePointTransactionKind.REFUND, 1L, "respec:node:a", CorePointAllocation.MAIN_PERK, 1L)
        );
        eq(3L, ledger.available());
        eq(1L, ledger.allocated(CorePointAllocation.MAIN_PERK));

        ledger = CorePointEconomyService.apply(
            ledger,
            budget,
            CorePointTransaction.allocate("perk:new", CorePointTransactionKind.SPEND, 1L, "node:b", CorePointAllocation.MAIN_PERK, 1L)
        );
        eq(2L, ledger.allocated(CorePointAllocation.MAIN_PERK));
    }

    private static void refundCannotExceedAllocation() {
        CorePointLedger ledger = CorePointLedger.empty()
            .apply(CorePointTransaction.credit("seed", CorePointTransactionKind.EARN, 5L, "test", 1L));
        expect(IllegalArgumentException.class, () -> ledger.apply(
            CorePointTransaction.allocate("bad-refund", CorePointTransactionKind.REFUND, 1L, "respec:none", CorePointAllocation.ATTRIBUTE, 1L)
        ));
    }

    private static void arithmeticOverflowIsExplicit() {
        CorePointLedger ledger = CorePointLedger.empty().apply(
            CorePointTransaction.credit("max", CorePointTransactionKind.EARN, Long.MAX_VALUE, "test", 1L)
        );
        CorePointLedger full = ledger;
        expect(ArithmeticException.class, () -> full.apply(
            CorePointTransaction.credit("overflow", CorePointTransactionKind.EARN, 1L, "test", 1L)
        ));
        expect(ArithmeticException.class, () -> new MainPerkBudget(Long.MAX_VALUE).increase(1L));
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
