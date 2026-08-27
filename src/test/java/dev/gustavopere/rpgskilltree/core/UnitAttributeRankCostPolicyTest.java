package dev.gustavopere.rpgskilltree.core;

import java.util.Objects;

public final class UnitAttributeRankCostPolicyTest {
    public static void main(String[] args) {
        oneCorePointBuysOneRankAcrossTheUncappedRange();
        invalidRangesFailClosed();
        System.out.println("UnitAttributeRankCostPolicyTest: PASS");
    }

    private static void oneCorePointBuysOneRankAcrossTheUncappedRange() {
        AttributeRankCostPolicy policy = UnitAttributeRankCostPolicy.INSTANCE;

        eq(1L, policy.cost(AttributeId.INTELLIGENCE, 0L, 1L));
        eq(7L, policy.cost(AttributeId.STRENGTH, 900L, 7L));
        eq(1_024L, policy.cost(AttributeId.DETERMINATION, 5_000_000_000L, 1_024L));
    }

    private static void invalidRangesFailClosed() {
        AttributeRankCostPolicy policy = UnitAttributeRankCostPolicy.INSTANCE;
        expect(NullPointerException.class, () -> policy.cost(null, 0L, 1L));
        expect(IllegalArgumentException.class, () -> policy.cost(AttributeId.CHARISMA, -1L, 1L));
        expect(IllegalArgumentException.class, () -> policy.cost(AttributeId.CHARISMA, 0L, 0L));
        expect(IllegalArgumentException.class, () -> policy.cost(AttributeId.CHARISMA, 0L, -1L));
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
