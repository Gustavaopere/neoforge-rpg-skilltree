package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CoreProgressionQueryServiceTest {
    public static void main(String[] args) {
        newPlayerQueryExposesStableReadOnlyState();
        populatedQueryCarriesEconomyBudgetAndAttributeState();
        grandfatheredBudgetOverageIsExplicitAndNonNegative();
        hugeLevelRetainsBigIntegerNextLevelCost();
        technicalCeilingHasNoNextLevelCost();
        rulesMismatchIsRejected();
        QuestProgressionHooksFoundationTest.main(new String[0]);
        System.out.println("CoreProgressionQueryServiceTest: PASS");
    }

    private static ProgressionRulesSnapshot rules(long version, long budget) {
        return new ProgressionRulesSnapshot(
            version,
            "rpgskilltree:query_test",
            List.of(
                new LevelCurveBand(0L, 100L, 2L),
                new LevelCurveBand(100L, 500L, 5L)
            ),
            new MainPerkBudget(budget)
        );
    }

    private static void newPlayerQueryExposesStableReadOnlyState() {
        ProgressionRulesSnapshot rules = rules(31L, 3L);
        CoreProgressionState state = CoreProgressionBootstrap.newPlayer(rules);
        CoreProgressionQuerySnapshot query = CoreProgressionQueryService.snapshot(state, rules);

        eq(0L, query.level());
        eq(0L, query.xpIntoLevel());
        eq(BigInteger.valueOf(100L), query.xpToNextLevel());
        eq(0L, query.totalCorePoints());
        eq(0L, query.availableCorePoints());
        eq(0L, query.attributeAllocatedCorePoints());
        eq(0L, query.mainPerkAllocatedCorePoints());
        eq(3L, query.mainPerkBudgetTotal());
        eq(3L, query.mainPerkBudgetAvailable());
        eq(0L, query.mainPerkBudgetOverage());
        eq(AttributeRanks.empty(), query.attributeRanks());
        eq(rules.version(), query.rulesVersion());
        eq(rules.fingerprint(), query.rulesFingerprint());
        same(state, CoreProgressionBootstrap.resume(state, rules));
    }

    private static void populatedQueryCarriesEconomyBudgetAndAttributeState() {
        ProgressionRulesSnapshot rules = rules(31L, 3L);
        CorePointLedger ledger = CorePointLedger.empty()
            .apply(CorePointTransaction.credit(
                "query:credit",
                CorePointTransactionKind.EARN,
                20L,
                "quest:chapter_one",
                rules.version()
            ))
            .apply(CorePointTransaction.allocate(
                "query:attribute",
                CorePointTransactionKind.SPEND,
                5L,
                "attribute:test",
                CorePointAllocation.ATTRIBUTE,
                rules.version()
            ))
            .apply(CorePointTransaction.allocate(
                "query:main",
                CorePointTransactionKind.SPEND,
                3L,
                "perk:test",
                CorePointAllocation.MAIN_PERK,
                rules.version()
            ));
        AttributeRanks attributes = AttributeRanks.of(Map.of(
            AttributeId.STRENGTH, 7L,
            AttributeId.DETERMINATION, 5_000_000_000L
        ));
        MainPerkBudgetProgression budgetProgression = MainPerkBudgetProgression.empty()
            .grant("quest:budget-expansion", 2L);
        CoreProgressionState state = new CoreProgressionState(
            new CharacterProgressionState(150L, 17L),
            ledger,
            attributes,
            budgetProgression,
            ProgressionRewardClaims.empty(),
            rules.version(),
            rules.fingerprint(),
            0,
            0L
        );

        CoreProgressionQuerySnapshot query = CoreProgressionQueryService.snapshot(state, rules);
        eq(150L, query.level());
        eq(17L, query.xpIntoLevel());
        eq(rules.levelCurve().xpToNextLevel(150L), query.xpToNextLevel());
        eq(20L, query.totalCorePoints());
        eq(12L, query.availableCorePoints());
        eq(5L, query.attributeAllocatedCorePoints());
        eq(3L, query.mainPerkAllocatedCorePoints());
        eq(5L, query.mainPerkBudgetTotal());
        eq(2L, query.mainPerkBudgetAvailable());
        eq(0L, query.mainPerkBudgetOverage());
        eq(attributes, query.attributeRanks());
        eq(7L, query.attributeRanks().rank(AttributeId.STRENGTH));
        eq(5_000_000_000L, query.attributeRanks().rank(AttributeId.DETERMINATION));
    }

    private static void grandfatheredBudgetOverageIsExplicitAndNonNegative() {
        ProgressionRulesSnapshot rules = rules(31L, 3L);
        CorePointLedger ledger = CorePointLedger.empty()
            .apply(CorePointTransaction.credit(
                "query:legacy-credit",
                CorePointTransactionKind.MIGRATION,
                10L,
                "legacy",
                rules.version()
            ))
            .apply(CorePointTransaction.allocate(
                "query:legacy-main",
                CorePointTransactionKind.SPEND,
                5L,
                "legacy:main",
                CorePointAllocation.MAIN_PERK,
                rules.version()
            ));
        CoreProgressionState state = CoreProgressionState.nativeState(
            CharacterProgressionState.empty(), ledger, rules);

        CoreProgressionQuerySnapshot query = CoreProgressionQueryService.snapshot(state, rules);
        eq(5L, query.mainPerkAllocatedCorePoints());
        eq(3L, query.mainPerkBudgetTotal());
        eq(0L, query.mainPerkBudgetAvailable());
        eq(2L, query.mainPerkBudgetOverage());
    }

    private static void hugeLevelRetainsBigIntegerNextLevelCost() {
        ProgressionRulesSnapshot hugeRules = new ProgressionRulesSnapshot(
            32L,
            "rpgskilltree:query_huge",
            List.of(new LevelCurveBand(0L, 1L, Long.MAX_VALUE)),
            new MainPerkBudget(0L)
        );
        long level = 5_000_000_000L;
        CoreProgressionState state = CoreProgressionState.nativeState(
            new CharacterProgressionState(level, 0L),
            CorePointLedger.empty(),
            hugeRules
        );

        CoreProgressionQuerySnapshot query = CoreProgressionQueryService.snapshot(state, hugeRules);
        BigInteger expected = BigInteger.ONE.add(
            BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.valueOf(level))
        );
        eq(expected, query.xpToNextLevel());
        if (query.xpToNextLevel().compareTo(BigInteger.valueOf(Long.MAX_VALUE)) <= 0) {
            throw new AssertionError("query narrowed an uncapped next-level cost into long range");
        }
    }

    private static void technicalCeilingHasNoNextLevelCost() {
        ProgressionRulesSnapshot rules = rules(31L, 3L);
        CoreProgressionState state = CoreProgressionState.nativeState(
            new CharacterProgressionState(Long.MAX_VALUE, 0L),
            CorePointLedger.empty(),
            rules
        );
        CoreProgressionQuerySnapshot query = CoreProgressionQueryService.snapshot(state, rules);
        eq(BigInteger.ZERO, query.xpToNextLevel());
    }

    private static void rulesMismatchIsRejected() {
        ProgressionRulesSnapshot original = rules(31L, 3L);
        CoreProgressionState state = CoreProgressionBootstrap.newPlayer(original);
        expect(IllegalStateException.class, () -> CoreProgressionQueryService.snapshot(
            state,
            rules(32L, 3L)
        ));
        expect(IllegalStateException.class, () -> CoreProgressionQueryService.snapshot(
            state,
            rules(31L, 4L)
        ));
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
