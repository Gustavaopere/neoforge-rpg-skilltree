package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Objects;

public final class MainPerkBudgetProgressionTest {
    public static void main(String[] args) {
        grantsAreIdempotentAndProvenanced();
        mutationBoundaryUsesEffectivePlayerBudget();
        codecAndSyncPreserveEffectiveBudget();
        invalidAndOverflowingGrantsAreRejected();
        System.out.println("MainPerkBudgetProgressionTest: PASS");
    }

    private static ProgressionRulesSnapshot rules() {
        return new ProgressionRulesSnapshot(
            11L,
            "rpgskilltree:perk_budget_test",
            List.of(new LevelCurveBand(0L, 100L, 0L)),
            new MainPerkBudget(1L)
        );
    }

    private static CoreProgressionState fundedState(ProgressionRulesSnapshot rules) {
        CorePointLedger ledger = CorePointLedger.empty().apply(
            CorePointTransaction.credit(
                "seed:budget-test",
                CorePointTransactionKind.EARN,
                10L,
                "test",
                rules.version()
            )
        );
        return CoreProgressionState.nativeState(CharacterProgressionState.empty(), ledger, rules);
    }

    private static void grantsAreIdempotentAndProvenanced() {
        MainPerkBudgetProgression empty = MainPerkBudgetProgression.empty();
        MainPerkBudgetProgression first = empty.grant("quest:first_steps", 2L);
        MainPerkBudgetProgression duplicate = first.grant("quest:first_steps", 2L);
        MainPerkBudgetProgression second = duplicate.grant("boss:ancient_guardian", 3L);

        eq(0L, empty.bonus());
        eq(2L, first.bonus());
        same(first, duplicate);
        eq(5L, second.bonus());
        eq(2L, second.grants().get("quest:first_steps"));
        eq(3L, second.grants().get("boss:ancient_guardian"));
        eq(6L, second.effectiveBudget(new MainPerkBudget(1L)).total());

        expect(IllegalArgumentException.class, () -> first.grant("quest:first_steps", 3L));
    }

    private static void mutationBoundaryUsesEffectivePlayerBudget() {
        ProgressionRulesSnapshot rules = rules();
        CoreProgressionState state = fundedState(rules);
        CoreProgressionState expanded = CoreProgressionMutationService.grantMainPerkBudget(
            state,
            "quest:main-tree-expansion",
            2L,
            rules
        );
        CoreProgressionState duplicate = CoreProgressionMutationService.grantMainPerkBudget(
            expanded,
            "quest:main-tree-expansion",
            2L,
            rules
        );

        eq(2L, expanded.mainPerkBudgetProgression().bonus());
        eq(expanded.mainPerkBudgetProgression(), duplicate.mainPerkBudgetProgression());

        CoreProgressionState spent = CoreProgressionMutationService.applyCorePointTransaction(
            duplicate,
            CorePointTransaction.allocate(
                "perk:three-points",
                CorePointTransactionKind.SPEND,
                3L,
                "perk:test",
                CorePointAllocation.MAIN_PERK,
                rules.version()
            ),
            rules
        );
        eq(3L, spent.corePoints().allocated(CorePointAllocation.MAIN_PERK));

        expect(IllegalArgumentException.class, () -> CoreProgressionMutationService.applyCorePointTransaction(
            spent,
            CorePointTransaction.allocate(
                "perk:fourth-point",
                CorePointTransactionKind.SPEND,
                1L,
                "perk:test:fourth",
                CorePointAllocation.MAIN_PERK,
                rules.version()
            ),
            rules
        ));
    }

    private static void codecAndSyncPreserveEffectiveBudget() {
        ProgressionRulesSnapshot rules = rules();
        CoreProgressionState state = CoreProgressionMutationService.grantMainPerkBudget(
            fundedState(rules),
            "milestone:chapter_one",
            4L,
            rules
        );

        CoreProgressionState decoded = CoreProgressionStateCodec.decode(CoreProgressionStateCodec.encode(state));
        eq(state.mainPerkBudgetProgression(), decoded.mainPerkBudgetProgression());
        eq(4L, decoded.mainPerkBudgetProgression().bonus());

        CoreProgressionSyncState sync = CoreProgressionSyncState.from(decoded, rules);
        eq(5L, sync.mainPerkBudget());
    }

    private static void invalidAndOverflowingGrantsAreRejected() {
        MainPerkBudgetProgression empty = MainPerkBudgetProgression.empty();
        expect(IllegalArgumentException.class, () -> empty.grant("", 1L));
        expect(IllegalArgumentException.class, () -> empty.grant("quest:bad", 0L));
        expect(IllegalArgumentException.class, () -> empty.grant("quest:bad", -1L));

        MainPerkBudgetProgression huge = empty.grant("admin:huge", Long.MAX_VALUE);
        expect(ArithmeticException.class, () -> huge.grant("admin:overflow", 1L));
        expect(ArithmeticException.class, () -> huge.effectiveBudget(new MainPerkBudget(1L)));
    }

    private static void same(Object expected, Object actual) {
        if (expected != actual) throw new AssertionError("expected identical instance");
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
