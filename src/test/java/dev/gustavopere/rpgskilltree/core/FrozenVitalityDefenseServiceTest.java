package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

/** Frozen A0092/A0096-A0099 defense pipeline and one-shot hostile preparation. */
public final class FrozenVitalityDefenseServiceTest {
    public static void main(String[] args) {
        physicalAndLowHealthAreIndependentMultipliers();
        openingDefenseRequiresTenSecondsAndConsumesOnce();
        movementAndSharedStationaryFactsAreExact();
        duplicateCallbackCannotApplyDefenseTwice();
        System.out.println("FrozenVitalityDefenseServiceTest: PASS");
    }

    private static void physicalAndLowHealthAreIndependentMultipliers() {
        var service = new FrozenVitalityDefenseService();
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0092", 4, "A0096", 3));
        var low = service.resolve(request("low", true, true, false, false, false, 0.299D), ranks, 0L);
        require(close(low.damageMultiplier(), 0.92D * 0.88D), "A0092 and A0096 multiply once each");
        var boundary = service.resolve(request("boundary", true, true, false, false, false, 0.30D), ranks, 1L);
        require(close(boundary.damageMultiplier(), 0.92D), "A0096 requires strictly below 30% pre-impact");
        var magic = service.resolve(request("magic", true, false, false, false, false, 0.10D), ranks, 2L);
        require(close(magic.damageMultiplier(), 1.0D), "physical nodes never reduce non-physical damage");
    }

    private static void openingDefenseRequiresTenSecondsAndConsumesOnce() {
        var service = new FrozenVitalityDefenseService();
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0097", 3));
        require(close(service.resolve(request("first", true, false, false, false, false, 1), ranks, 0L).damageMultiplier(), 1), "first observation only arms timer");
        require(close(service.resolve(request("environment", false, false, false, false, false, 1), ranks, 9_999L).damageMultiplier(), 1), "environment does not consume or reset");
        require(close(service.resolve(request("ready", true, false, false, false, false, 1), ranks, 10_000L).damageMultiplier(), 0.85D), "ten seconds arms opening defense");
        require(close(service.resolve(request("consumed", true, false, false, false, false, 1), ranks, 10_001L).damageMultiplier(), 1), "next hostile event consumes preparation once");
    }

    private static void movementAndSharedStationaryFactsAreExact() {
        var service = new FrozenVitalityDefenseService();
        var moving = FrozenCombatPerkRanks.of(Map.of("A0098", 3));
        require(close(service.resolve(request("run", true, false, true, false, false, 1), moving, 0L).damageMultiplier(), 0.91D), "self-propelled sprint receives A0098");
        require(close(service.resolve(request("forced", true, false, true, true, false, 1), moving, 1L).damageMultiplier(), 1), "forced displacement is not movement proof");
        var planted = FrozenCombatPerkRanks.of(Map.of("A0099", 3));
        require(close(service.resolve(request("stationary", true, false, false, false, true, 1), planted, 2L).damageMultiplier(), 0.88D), "A0099 consumes shared stationary boolean");
    }

    private static void duplicateCallbackCannotApplyDefenseTwice() {
        var service = new FrozenVitalityDefenseService();
        var ranks = FrozenCombatPerkRanks.of(Map.of("A0092", 4));
        CanonicalActionIdentity action = root("same");
        require(close(service.resolve(request(action, true, true, false, false, false, 1), ranks, 0L).damageMultiplier(), 0.92D), "first callback");
        require(service.resolve(request(action.withSource("epicfight"), true, true, false, false, false, 1), ranks, 1L).status()
            == FrozenVitalityDefenseService.Status.DUPLICATE, "same root event cannot resolve twice");
    }

    private static FrozenVitalityDefenseService.Request request(String id, boolean hostile, boolean physical,
        boolean sprinting, boolean forced, boolean stationary, double healthFraction) {
        return request(root(id), hostile, physical, sprinting, forced, stationary, healthFraction);
    }
    private static FrozenVitalityDefenseService.Request request(CanonicalActionIdentity action, boolean hostile,
        boolean physical, boolean sprinting, boolean forced, boolean stationary, double healthFraction) {
        return new FrozenVitalityDefenseService.Request(action, true, true, hostile, physical, true,
            sprinting, forced, stationary, healthFraction);
    }
    private static CanonicalActionIdentity root(String id) { return CanonicalActionIdentity.root("victim", id, "test"); }
    private static boolean close(double a, double b) { return Math.abs(a - b) < 0.000001D; }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
