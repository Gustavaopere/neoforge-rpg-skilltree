package dev.gustavopere.rpgskilltree.core;

import java.math.BigInteger;
import java.util.Objects;

public final class InfiniteProgressionFoundationTest {
    public static void main(String[] args) {
        startsAtLevelZero();
        exactBoundaryLevelsCleanly();
        oneGrantMayCrossSeveralLevels();
        highLevelsDoNotRequireIntLevelState();
        invalidPartialXpIsRejectedAgainstCurve();
        negativeXpIsRejected();
        technicalLevelOverflowIsExplicit();
        System.out.println("InfiniteProgressionFoundationTest: PASS");
    }

    private static void startsAtLevelZero() {
        var state = CharacterProgressionState.empty();
        eq(0L, state.level());
        eq(0L, state.xpIntoLevel());
    }

    private static void exactBoundaryLevelsCleanly() {
        InfiniteLevelCurve curve = constantCost(100L);
        var first = CharacterProgressionService.grantXp(CharacterProgressionState.empty(), 99L, curve);
        eq(0L, first.after().level());
        eq(99L, first.after().xpIntoLevel());
        eq(0L, first.levelsGained());

        var boundary = CharacterProgressionService.grantXp(first.after(), 1L, curve);
        eq(1L, boundary.after().level());
        eq(0L, boundary.after().xpIntoLevel());
        eq(1L, boundary.levelsGained());
    }

    private static void oneGrantMayCrossSeveralLevels() {
        InfiniteLevelCurve curve = constantCost(100L);
        var result = CharacterProgressionService.grantXp(CharacterProgressionState.empty(), 350L, curve);
        eq(3L, result.after().level());
        eq(50L, result.after().xpIntoLevel());
        eq(3L, result.levelsGained());
        eq(350L, result.xpGranted());
    }

    private static void highLevelsDoNotRequireIntLevelState() {
        InfiniteLevelCurve curve = constantCost(100L);
        var state = new CharacterProgressionState(5_000_000_000L, 7L);
        var result = CharacterProgressionService.grantXp(state, 293L, curve);
        eq(5_000_000_003L, result.after().level());
        eq(0L, result.after().xpIntoLevel());
        eq(3L, result.levelsGained());
    }

    private static void invalidPartialXpIsRejectedAgainstCurve() {
        InfiniteLevelCurve curve = constantCost(100L);
        expect(IllegalArgumentException.class, () ->
            CharacterProgressionService.grantXp(new CharacterProgressionState(2L, 100L), 0L, curve));
    }

    private static void negativeXpIsRejected() {
        InfiniteLevelCurve curve = constantCost(100L);
        expect(IllegalArgumentException.class, () ->
            CharacterProgressionService.grantXp(CharacterProgressionState.empty(), -1L, curve));
    }

    private static void technicalLevelOverflowIsExplicit() {
        InfiniteLevelCurve curve = constantCost(1L);
        expect(ArithmeticException.class, () ->
            CharacterProgressionService.grantXp(new CharacterProgressionState(Long.MAX_VALUE, 0L), 1L, curve));
    }

    private static InfiniteLevelCurve constantCost(long xpPerLevel) {
        if (xpPerLevel <= 0) throw new IllegalArgumentException("xpPerLevel must be positive");
        BigInteger cost = BigInteger.valueOf(xpPerLevel);
        return level -> {
            if (level < 0) throw new IllegalArgumentException("level must be non-negative");
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

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
