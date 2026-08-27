package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public final class LevelCorePointAwardPolicyTest {
    public static void main(String[] args) {
        periodicAwardsRespectConfiguredCadence();
        hugeLevelRangesResolveWithoutPerLevelIteration();
        invalidRangesAndOverflowAreRejected();
        canonicalFormIsStableAndContentSensitive();
        System.out.println("LevelCorePointAwardPolicyTest: PASS");
    }

    private static void periodicAwardsRespectConfiguredCadence() {
        LevelCorePointAwardPolicy policy = new PeriodicLevelCorePointAwardPolicy(2L, 3L, 4L);

        eq(0L, policy.pointsAwarded(0L, 1L));
        eq(4L, policy.pointsAwarded(1L, 2L));
        eq(0L, policy.pointsAwarded(2L, 4L));
        eq(4L, policy.pointsAwarded(4L, 5L));
        eq(12L, policy.pointsAwarded(1L, 8L));
        eq(0L, policy.pointsAwarded(8L, 8L));
    }

    private static void hugeLevelRangesResolveWithoutPerLevelIteration() {
        LevelCorePointAwardPolicy policy = new PeriodicLevelCorePointAwardPolicy(1L, 5L, 2L);
        eq(2_000_000_000L, policy.pointsAwarded(0L, 5_000_000_000L));
    }

    private static void invalidRangesAndOverflowAreRejected() {
        expect(IllegalArgumentException.class, () -> new PeriodicLevelCorePointAwardPolicy(0L, 1L, 1L));
        expect(IllegalArgumentException.class, () -> new PeriodicLevelCorePointAwardPolicy(1L, 0L, 1L));
        expect(IllegalArgumentException.class, () -> new PeriodicLevelCorePointAwardPolicy(1L, 1L, 0L));

        LevelCorePointAwardPolicy policy = new PeriodicLevelCorePointAwardPolicy(1L, 1L, Long.MAX_VALUE);
        expect(ArithmeticException.class, () -> policy.pointsAwarded(0L, 2L));
        expect(IllegalArgumentException.class, () -> policy.pointsAwarded(-1L, 2L));
        expect(IllegalArgumentException.class, () -> policy.pointsAwarded(3L, 2L));
    }

    private static void canonicalFormIsStableAndContentSensitive() {
        LevelCorePointAwardPolicy a = new PeriodicLevelCorePointAwardPolicy(1L, 2L, 3L);
        LevelCorePointAwardPolicy b = new PeriodicLevelCorePointAwardPolicy(1L, 2L, 3L);
        LevelCorePointAwardPolicy changed = new PeriodicLevelCorePointAwardPolicy(1L, 3L, 3L);

        eq(a.canonicalForm(), b.canonicalForm());
        eq(false, a.canonicalForm().equals(changed.canonicalForm()));
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
