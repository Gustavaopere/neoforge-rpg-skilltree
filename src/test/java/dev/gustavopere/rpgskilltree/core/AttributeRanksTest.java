package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class AttributeRanksTest {
    public static void main(String[] args) {
        canonicalAttributesAreStable();
        emptyStartsAllRanksAtZero();
        ranksSupportValuesBeyondIntRange();
        increasesAndRefundsAreImmutable();
        invalidRanksAndOverflowFailClosed();
        System.out.println("AttributeRanksTest: PASS");
    }

    static void canonicalAttributesAreStable() {
        eq("strength", AttributeId.STRENGTH.serializedId());
        eq("constitution", AttributeId.CONSTITUTION.serializedId());
        eq("agility", AttributeId.AGILITY.serializedId());
        eq("intelligence", AttributeId.INTELLIGENCE.serializedId());
        eq("determination", AttributeId.DETERMINATION.serializedId());
        eq("charisma", AttributeId.CHARISMA.serializedId());
        eq(6, AttributeId.values().length);
    }

    static void emptyStartsAllRanksAtZero() {
        AttributeRanks ranks = AttributeRanks.empty();
        for (AttributeId attribute : AttributeId.values()) {
            eq(0L, ranks.rank(attribute));
        }
    }

    static void ranksSupportValuesBeyondIntRange() {
        long huge = 5_000_000_000L;
        AttributeRanks ranks = AttributeRanks.of(Map.of(AttributeId.INTELLIGENCE, huge));
        eq(huge, ranks.rank(AttributeId.INTELLIGENCE));
        eq(0L, ranks.rank(AttributeId.STRENGTH));
    }

    static void increasesAndRefundsAreImmutable() {
        AttributeRanks empty = AttributeRanks.empty();
        AttributeRanks raised = empty.increase(AttributeId.STRENGTH, 7L);
        AttributeRanks refunded = raised.decrease(AttributeId.STRENGTH, 2L);

        eq(0L, empty.rank(AttributeId.STRENGTH));
        eq(7L, raised.rank(AttributeId.STRENGTH));
        eq(5L, refunded.rank(AttributeId.STRENGTH));
        same(raised, raised.increase(AttributeId.AGILITY, 0L));
        same(refunded, refunded.decrease(AttributeId.AGILITY, 0L));
    }

    static void invalidRanksAndOverflowFailClosed() {
        expect(IllegalArgumentException.class,
            () -> AttributeRanks.of(Map.of(AttributeId.CHARISMA, -1L)));
        expect(IllegalArgumentException.class,
            () -> AttributeRanks.empty().increase(AttributeId.CHARISMA, -1L));
        expect(IllegalArgumentException.class,
            () -> AttributeRanks.empty().decrease(AttributeId.CHARISMA, -1L));
        expect(IllegalStateException.class,
            () -> AttributeRanks.empty().decrease(AttributeId.CONSTITUTION, 1L));

        AttributeRanks maxed = AttributeRanks.of(Map.of(AttributeId.DETERMINATION, Long.MAX_VALUE));
        expect(ArithmeticException.class,
            () -> maxed.increase(AttributeId.DETERMINATION, 1L));
        expect(NullPointerException.class, () -> AttributeRanks.empty().rank(null));
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
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(expected + " != " + actual);
        }
    }
}
