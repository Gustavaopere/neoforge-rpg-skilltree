package dev.gustavopere.rpgskilltree.core;

import java.util.concurrent.atomic.AtomicInteger;

public final class A0001A0020CriticalServiceTest {
    public static void main(String[] args) {
        oneRootActionGetsOneRoll();
        providerDecisionWinsWithoutRolling();
        System.out.println("A0001A0020CriticalServiceTest: PASS");
    }

    private static void oneRootActionGetsOneRoll() {
        AtomicInteger draws = new AtomicInteger();
        var service = new A0001A0020CriticalService(() -> {
            draws.incrementAndGet();
            return 0.05D;
        }, 30_000L, 32);
        boolean first = service.resolve("player", "root-1", false, 0.09D, 1_000L);
        boolean duplicate = service.resolve("player", "root-1", false, 0.09D, 1_001L);
        require(first && duplicate, "cached canonical decision must be reused");
        require(draws.get() == 1, "duplicate callbacks must not reroll critical chance");
    }

    private static void providerDecisionWinsWithoutRolling() {
        AtomicInteger draws = new AtomicInteger();
        var service = new A0001A0020CriticalService(() -> {
            draws.incrementAndGet();
            return 0.99D;
        }, 30_000L, 32);
        require(service.resolve("player", "root-2", true, 0.09D, 2_000L), "provider critical must remain critical");
        require(draws.get() == 0, "existing provider critical must not create a second roll");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
