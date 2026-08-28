package dev.gustavopere.rpgskilltree.compendium.api;

import java.util.Objects;

public final class CompendiumFactTest {
    public static void main(String[] args) {
        exactFactIsConfirmed();
        unavailableFactIsNotConfirmed();
        unavailableFactCannotCarryValue();
        availableFactRequiresValue();
        System.out.println("CompendiumFactTest: PASS");
    }

    private static void exactFactIsConfirmed() {
        CompendiumFact<Double> fact = new CompendiumFact<>(
            "base_health", 20.0D, "hp", FactSource.REGISTRY, FactConfidence.EXACT,
            FactVisibility.DISCOVERED_ONLY, null
        );
        truth(fact.isConfirmed());
        eq(20.0D, fact.value());
    }

    private static void unavailableFactIsNotConfirmed() {
        CompendiumFact<Object> fact = CompendiumFact.unavailable(
            "weakness", FactSource.UNKNOWN, FactVisibility.DISCOVERED_ONLY
        );
        falsity(fact.isConfirmed());
        eq(FactConfidence.UNAVAILABLE, fact.confidence());
    }

    private static void unavailableFactCannotCarryValue() {
        throwsIllegal(() -> new CompendiumFact<>(
            "weakness", "fire", null, FactSource.ADAPTER, FactConfidence.UNAVAILABLE,
            FactVisibility.DISCOVERED_ONLY, null
        ));
    }

    private static void availableFactRequiresValue() {
        throwsIllegal(() -> new CompendiumFact<>(
            "speed", null, null, FactSource.RUNTIME_ENTITY, FactConfidence.CONTEXTUAL,
            FactVisibility.DISCOVERED_ONLY, 123L
        ));
    }

    private static void throwsIllegal(Runnable action) {
        try {
            action.run();
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }

    private static void truth(boolean value) {
        if (!value) throw new AssertionError("expected true");
    }

    private static void falsity(boolean value) {
        if (value) throw new AssertionError("expected false");
    }
}
