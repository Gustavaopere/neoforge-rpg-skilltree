package dev.gustavopere.rpgskilltree.core;

import java.util.concurrent.atomic.AtomicInteger;

public final class CanonicalCriticalServiceTest {
    public static void main(String[] args) {
        additionalChanceRollsExactlyOncePerAction();
        providerCriticalNeedsNoAdditionalRoll();
        meleeAndProjectileUseTheSameDecisionContract();
        ineligibleAndDerivedActionsCannotRoll();
        System.out.println("CanonicalCriticalServiceTest: PASS");
    }

    private static void additionalChanceRollsExactlyOncePerAction() {
        var rolls = new AtomicInteger();
        var service = new CanonicalCriticalService(() -> {
            rolls.incrementAndGet();
            return 0.02D;
        }, 30_000L, 64);
        var action = CanonicalActionIdentity.root("player-a", "attack-17", "neoforge:critical_hit");
        var request = request(action, false, 0.03D);

        require(service.resolve(request, 1_000L), "bonus chance should make the canonical hit critical");
        require(service.resolve(request.withAction(action.withSource("epicfight:damage_pre")), 1_001L),
            "all callbacks must observe the existing decision");
        require(service.resolve(request.withAction(action.withSource("epicfight:damage_post")), 1_002L),
            "all consumers must receive only the existing boolean");
        require(rolls.get() == 1, "one attack must never perform a second critical roll");
    }

    private static void providerCriticalNeedsNoAdditionalRoll() {
        var rolls = new AtomicInteger();
        var service = new CanonicalCriticalService(() -> {
            rolls.incrementAndGet();
            return 0.99D;
        }, 30_000L, 64);
        var action = CanonicalActionIdentity.root("player-a", "attack-18", "neoforge:critical_hit");

        require(service.resolve(request(action, true, 0.03D), 1_000L), "provider-confirmed critical remains critical");
        require(rolls.get() == 0, "provider-confirmed critical must not trigger a redundant roll");
    }

    private static void meleeAndProjectileUseTheSameDecisionContract() {
        var service = new CanonicalCriticalService(() -> 0.01D, 30_000L, 64);
        var melee = CanonicalActionIdentity.root("player-a", "melee-1", "neoforge:critical_hit");
        var projectile = CanonicalActionIdentity.root("player-a", "shot-1", "neoforge:arrow_spawn");

        require(service.resolve(request(melee, false, 0.03D), 1_000L), "melee decision");
        require(service.resolve(request(projectile, false, 0.03D), 1_000L), "projectile decision");
        require(service.decision(melee, 1_001L).orElseThrow(), "melee consumer boolean");
        require(service.decision(projectile, 1_001L).orElseThrow(), "projectile consumer boolean");
    }

    private static void ineligibleAndDerivedActionsCannotRoll() {
        var rolls = new AtomicInteger();
        var service = new CanonicalCriticalService(() -> {
            rolls.incrementAndGet();
            return 0.0D;
        }, 30_000L, 64);
        var root = CanonicalActionIdentity.root("player-a", "attack-17", "neoforge:critical_hit");

        require(!service.resolve(new CanonicalCriticalRequest(root, false, true, true, false, 1.0D), 1_000L),
            "client-side requests cannot decide criticals");
        require(!service.resolve(new CanonicalCriticalRequest(root, true, false, true, false, 1.0D), 1_000L),
            "fake or otherwise ineligible actors cannot decide criticals");
        require(!service.resolve(new CanonicalCriticalRequest(root, true, true, false, false, 1.0D), 1_000L),
            "indirect actions cannot decide root criticals");
        require(!service.resolve(request(root.child("rpgskilltree:proc"), false, 1.0D), 1_000L),
            "secondary procs cannot roll criticals");
        require(rolls.get() == 0, "rejected requests must not consume RNG");
    }

    private static CanonicalCriticalRequest request(
        CanonicalActionIdentity action,
        boolean providerCritical,
        double bonusChance
    ) {
        return new CanonicalCriticalRequest(action, true, true, true, providerCritical, bonusChance);
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
