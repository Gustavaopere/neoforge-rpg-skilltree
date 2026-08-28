package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public final class CharacterXpRollbackTest {
    public static void main(String[] args) {
        rollbackWithinCurrentLevel();
        rollbackMayCrossSeveralLevels();
        rollbackSupportsHugeLevelsWithoutPerLevelIteration();
        zeroRollbackPreservesExactStateInstance();
        invalidRollbackFailsClosed();
        coreRollbackDoesNotReverseEarnedCorePoints();
        System.out.println("CharacterXpRollbackTest: PASS");
    }

    private static void rollbackWithinCurrentLevel() {
        InfiniteLevelCurve curve = constantCost(100L);
        CharacterProgressionState before = new CharacterProgressionState(3L, 50L);
        CharacterXpRollbackResult result = CharacterProgressionService.rollbackXp(before, 25L, curve);

        same(before, result.before());
        eq(new CharacterProgressionState(3L, 25L), result.after());
        eq(25L, result.xpRemoved());
        eq(0L, result.levelsLost());
    }

    private static void rollbackMayCrossSeveralLevels() {
        InfiniteLevelCurve curve = constantCost(100L);
        CharacterProgressionState before = new CharacterProgressionState(3L, 50L);
        CharacterXpRollbackResult result = CharacterProgressionService.rollbackXp(before, 275L, curve);

        eq(new CharacterProgressionState(0L, 75L), result.after());
        eq(275L, result.xpRemoved());
        eq(3L, result.levelsLost());
    }

    private static void rollbackSupportsHugeLevelsWithoutPerLevelIteration() {
        InfiniteLevelCurve curve = constantCost(100L);
        CharacterProgressionState before = new CharacterProgressionState(5_000_000_000L, 50L);
        CharacterXpRollbackResult result = CharacterProgressionService.rollbackXp(before, 250L, curve);

        eq(new CharacterProgressionState(4_999_999_998L, 0L), result.after());
        eq(2L, result.levelsLost());
    }

    private static void zeroRollbackPreservesExactStateInstance() {
        InfiniteLevelCurve curve = constantCost(100L);
        CharacterProgressionState before = new CharacterProgressionState(7L, 13L);
        CharacterXpRollbackResult result = CharacterProgressionService.rollbackXp(before, 0L, curve);

        same(before, result.after());
        eq(0L, result.xpRemoved());
        eq(0L, result.levelsLost());
    }

    private static void invalidRollbackFailsClosed() {
        InfiniteLevelCurve curve = constantCost(100L);
        CharacterProgressionState before = new CharacterProgressionState(3L, 50L);

        expect(IllegalArgumentException.class, () -> CharacterProgressionService.rollbackXp(before, -1L, curve));
        expect(IllegalArgumentException.class, () -> CharacterProgressionService.rollbackXp(before, 351L, curve));
        expect(IllegalArgumentException.class, () ->
            CharacterProgressionService.rollbackXp(new CharacterProgressionState(2L, 100L), 0L, curve));
    }

    private static void coreRollbackDoesNotReverseEarnedCorePoints() {
        ProgressionRulesSnapshot rules = new ProgressionRulesSnapshot(
            19L,
            "rpgskilltree:rollback_test",
            List.of(new LevelCurveBand(0L, 100L, 0L)),
            new MainPerkBudget(8L),
            new PeriodicLevelCorePointAwardPolicy(1L, 1L, 1L)
        );
        CorePointLedger ledger = CorePointLedger.empty().apply(
            CorePointTransaction.credit(
                "reward:historical",
                CorePointTransactionKind.EARN,
                10L,
                "character_level",
                rules.version()
            )
        );
        CoreProgressionState before = new CoreProgressionState(
            new CharacterProgressionState(3L, 50L),
            ledger,
            AttributeRanks.empty(),
            rules.version(),
            rules.fingerprint(),
            0,
            0L
        );

        CoreProgressionState after = CoreProgressionMutationService.rollbackXp(before, 250L, rules);

        eq(new CharacterProgressionState(1L, 0L), after.characterProgression());
        eq(before.corePoints().checkpoint(), after.corePoints().checkpoint());
        eq(before.attributeRanks(), after.attributeRanks());
        eq(before.mainPerkBudgetProgression(), after.mainPerkBudgetProgression());
        eq(before.progressionRewardClaims(), after.progressionRewardClaims());
        eq(before.rulesVersion(), after.rulesVersion());
        eq(before.rulesFingerprint(), after.rulesFingerprint());
        eq(before.migrationSourceFormatVersion(), after.migrationSourceFormatVersion());
        eq(before.discardedLegacyCapXp(), after.discardedLegacyCapXp());
    }

    private static InfiniteLevelCurve constantCost(long xpPerLevel) {
        BigInteger cost = BigInteger.valueOf(xpPerLevel);
        return level -> {
            if (level < 0L) throw new IllegalArgumentException("level must be non-negative");
            return cost.multiply(BigInteger.valueOf(level));
        };
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

    private static void same(Object expected, Object actual) {
        if (expected != actual) throw new AssertionError("expected same instance");
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
